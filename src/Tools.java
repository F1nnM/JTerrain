import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.util.Random;

public class Tools
{
    public static void mapToRGB(float[][] map, BufferedImage img) {
        for(int x = 0; x<map.length; x++)
            for(int y = 0; y<map[0].length; y++){ 
                int val = floatToBW(map[x][y]);
                img.setRGB(x,y,new Color(val,val,val).getRGB());
            }
    }

    private static int floatToBW(float f) {
        return (f < 0f)?0:Math.min((int) (f*255f), 255);
    }

    public static void mapToRGB(float[][] map, BufferedImage img, BufferedImage colorRamp) {
        for(int x = 0; x<map.length; x++)
            for(int y = 0; y<map[0].length; y++){
                int val = floatToBW(map[x][y]);
                img.setRGB(x,y,new Color(colorRamp.getRGB(0,255-val)).getRGB());
            }
    }

    public static void mapToPointCloud(float[][] map, Point3D[] points3D, BufferedImage img,  int maxZ) {
        int width = map.length;
        int height = map[0].length;
        for(int x = 0; x<width; x++)
            for(int y = 0; y<height; y++){
                points3D[x*width+y] = new Point3D(x,y,maxZ*map[x][y],new Color(img.getRGB(x,y)));
            }
    }
    
    public static Point3D[] RGBtoPointCloud(BufferedImage img) {
        Point3D[] points3D = new Point3D[img.getWidth()*img.getHeight()];
        int i = 0;
        for(int x = 0; x<img.getWidth(); x++)
            for(int y = 0; y<img.getHeight(); y++){
                points3D[i] = new Point3D(x,y,new Color(img.getRGB(x,y)).getRed(), img.getRGB(x,y));
                i++;
            }
        return points3D;
    }
    
    public static void randomNoiseMap(float[][] map) {
        Random r = new Random();
        int width = map.length;
        int height = map[0].length;
        for(int x = 0; x<width; x++)
           for(int y = 0; y<height; y++){
               map[x][y] = r.nextFloat();
           }
    }
    
    public static float[][] simpleBlur(float[][] map, int r) {
        int width = map.length;
        int height = map[0].length;
        float[][] newMap = new float[width][height];
        for(int x = 0; x<width; x++)
            for(int y = 0; y<height; y++){
                float avg = 0;
                int pixelNum = 0;
                for(int hOff = -r; hOff<=r; hOff++)
                    for(int vOff = -r; vOff<=r;vOff++){
                        if(!(x+hOff<0 || x+hOff>=width || y+vOff<0 || y+vOff>=height)){
                            avg+= map[x+hOff][y+vOff];
                            pixelNum++;
                        }
                    }
                avg /= pixelNum;
                newMap[x][y] = avg;
            }
        return newMap;
    }

    public static float perlin(float x, float y) {
        return 1f; //yay
    }

    public static BufferedImage loadColorRamp(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return new BufferedImage(10, 255, BufferedImage.TYPE_INT_RGB);
        }
    }

}
