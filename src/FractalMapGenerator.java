import java.awt.image.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FractalMapGenerator {
    
    public static void generate(int width, int height, int maxZ){       
        DrawingFrame dfMap = new DrawingFrame(width, height);
        BufferedImage img = dfMap.getCompatibleImage();
        float[][] map = generateMap(width,height);
        Point3D[] points3D = new Point3D[height*width];
        
        Tools.mapToRGB(map, img);   
        Tools.mapToPointCloud(map, points3D, img, maxZ); 
        
        dfMap.show(img);
        new PointCloud().displayAsMap(points3D, Math.max(Math.max(width, height),maxZ),maxZ);
    }
    
    private static float[][] generateMap(int width, int height){
        float[][] map = new float[width][height];
        //Tools.randomNoiseMap(map);
        //TODO
        for(int x = 0; x<width; x++)
           for(int y = 0; y<height; y++){
               map[x][y] = 1.0F;
           }
           
        ArrayList<FractalLine> fractal = new ArrayList();
        FractalLine init = new FractalLine(width/2, height/2, width/2, height/2, 40F, 10F,0F);
        fractal.add(init);
        FractalLine[] lastLines = {init};
        while(fractal.size()<1000){
            ArrayList<FractalLine> newLines = new ArrayList();
            for(int i = 0; i < lastLines.length; i++) {
                newLines.addAll(Arrays.asList(lastLines[i].extend(width, height)));
            }
            fractal.addAll(newLines);
            lastLines = newLines.toArray(new FractalLine[0]);
        }
        
        
        fractal.forEach((line)->{
            System.out.println(line);
            for(int x = Math.min(line.x1, line.x2); x <= Math.max(line.x1, line.x2); x++)
                for(int y = Math.min(line.y1, line.y2); y <= Math.max(line.y1, line.y2); y++) {
                    if(x>=0 && y>=0 && x<width && y<height)
                        if(line.isPointInLine(x,y)){
                            map[x][y] = 0F;
                        }
                }
        });
        
        return Tools.simpleBlur(map, 10);
    }
    
    static class FractalLine {
        int x1;
        int y1;
        int x2;
        int y2;
        float length;
        float width;
        float angle;
        
        FractalLine(int x1, int y1, int x2, int y2, float length, float width, float angle) {
            this.x1 = x1;
            this.y1 = y1;
            this.length = length;
            this.width = width;
            this.angle = angle;
            
        }
        
        FractalLine[] extend(int maxWidth, int maxHeight){
            int num = (int) Math.round(Math.random()*2F+1);
            FractalLine[] followers = new FractalLine[num];
            for(int i = 0; i < num; i++) {
                int newX1 = x2;
                int newY1 = y2;
                float newLength = (0.7F + (float) (Math.random()*0.6F))*length;
                float newWidth = (0.7F + (float) (Math.random()*0.6F))*length;
                float newAngle = (float)(((Math.random()*0.4+0.8)*2*Math.PI + angle)%(2*Math.PI));
                int newX2 = newX1 + (int) (Math.cos(angle)*length);
                int newY2 = newY1 + (int) (Math.sin(angle)*length);
                if(x2<0) x2=0;
                if(y2<0) y2=0;
                if(x2>=maxWidth) x2=maxWidth-1;
                if(y2>=maxHeight) y1=maxHeight-1;
                followers[i] = new FractalLine(newX1, newY1, newX2, newY2, newLength, newWidth, newAngle);
            }
            return followers;
        }
        
        boolean isPointInLine(int x3, int y3) {
            return (Math.abs((y2 - y1) * x3 - (x2 - x1) * y3 + x2 * y1 - y2 * x1 ) / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 -x1, 2)))<width;
        }
        
        public String toString() {
            return "FractalLine [x1="+x1+", y1="+y1+", x2="+x2+", y2="+y2+", length="+length+", width="+width+", angle="+angle+"]";
        }
    }
}
