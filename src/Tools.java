import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Random;

public class Tools
{
    public static void mapToRGB(float[][] map, BufferedImage img) {
        for(int x = 0; x<map.length; x++)
            for(int y = 0; y<map[0].length; y++){ 
                int val = (int) (map[x][y]*255);
                if (val < 0) 
                    val = 0;
                img.setRGB(x,y,new Color(val,val,val).getRGB());
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
}
