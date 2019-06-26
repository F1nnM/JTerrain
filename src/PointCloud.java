import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.*;
import java.util.concurrent.*;

public class PointCloud{
    public Point3D[] points3D;
    SortedList sortedPoints;
    
    private float rotation = 0.0F;
    DrawingFrame dr;
    
    public static int size = 200;
    static int canvasSize = 512;
    static float viewAngle = 20F;
    
    public PointCloud() {
        OptiMath.precalculate();
    }
    
    public void displayAsMap(Point3D[] points, int size, int maxSize) {
        points3D = points;
        this.size = size;
        float scaleFac = 1/(size*1.0F/canvasSize)*0.7F;
        System.out.println(scaleFac);
        for(int i = 0; i<points3D.length;i++){
            Point3D p = points3D[i];
            float x = p.getX()-(size/2);
            float y = p.getY()-(size/2);
            float z = p.getZ()-(maxSize/2);
            points3D[i] = new Point3D(x,y,z,p.getColor()).scale(scaleFac);
        }
        dr = new DrawingFrame(canvasSize,canvasSize);
        infinity();
    }
    
    public void randomCyl(int pointNum){
        int i = 0;
        points3D = new Point3D[pointNum];
        for(float theta = 0;i<pointNum; theta += (2*Math.PI)/pointNum, i++){
            float x = 0.4F*size * OptiMath.cos(theta);
            float y = 0.4F*size * OptiMath.sin(theta);
            points3D[i] = new Point3D(x,y);
        }

        for(i = 0; i<points3D.length;i++){
            points3D[i] = points3D[i].rotateY(viewAngle).scale(1.5F);
        }
    }
    
    public void infinity() {
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (true) {
                    dr.show(render());
                }
            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (true) {
                     rotation += 0.1F;
                     try { Thread.sleep(10); } catch (Exception e) {}
                }
            }
        }).start();
    }
     
    public BufferedImage render()
    {
        sortedPoints = new SortedList();
        FPSCounter.startFrame();
        BufferedImage img = dr.getCompatibleImage();
        Object[] avgs;
        synchronized(points3D){
            Point3D[] renderCache = new Point3D[points3D.length];
            
            //List<Callable<Object>> callables = new ArrayList<Callable<Object>>();

            for(int i = 0; i<points3D.length;i++){
                /*final int finalI = i;
                callables.add( Executors.callable(() -> {
                        renderCache[finalI] = points3D[finalI].rotateZ(rotation).rotateY(viewAngle);
                    } ));
            }
            ExecutorService pool = Executors.newFixedThreadPool(40);
            try{pool.invokeAll(callables);}catch(Exception e){e.printStackTrace();}
            pool.shutdown();*/
            renderCache[i] = points3D[i].rotateZ(rotation).rotateY(viewAngle);
        }
            
            //Arrays.sort(renderCache, new Point3D.SortByX());
            Quicksort.sort(renderCache,10);
            
            ListIterator it = sortedPoints.getIterator();
            for(int i = 0; i<renderCache.length;i++){
                Point2D p = renderCache[i].project2D();
                //int x = (int) p.getX()+(canvasSize/2);
                //int y = (int) p.getY()+(canvasSize/2);
                int x = (int) Math.round(p.getX()+(canvasSize/2));
                int y = (int)  Math.round(p.getY()+(canvasSize/2));
                if (!(x >= canvasSize || y >= canvasSize || x < 0 || y < 0))
                    img.setRGB(x,y,p.getColor());
            }
        }     

        avgs = FPSCounter.stopFrame();
        float avgTime = (float) avgs[0];
        int fps = (int) avgs[1];
        float currentTime = (float) avgs[2];
        dr.updateInfo((int) avgTime+" ms / "+fps+" fps possible");
        try { Thread.sleep((1000/45)-(long)currentTime); } catch (Exception e) {}
        return img;
    }
}
