import java.awt.image.BufferedImage;
public class NoiseMapGenerator
{
    public NoiseMapGenerator()
    {
        generate(512);
    }
    
    public void generate(int size) {
        int maxZ = 100;
        DrawingFrame dfMap = new DrawingFrame(size,size);
        BufferedImage img = dfMap.getCompatibleImage();
        
        float[][] map = new float[size][size];
        Tools.randomNoiseMap(map);
        map = Tools.simpleBlur(map, 8);
        map = Tools.simpleBlur(map, 8);
        map = Tools.simpleBlur(map, 8);
        Tools.mapToRGB(map, img);   
        
        Point3D[] points3D = new Point3D[size*size];
        Tools.mapToPointCloud(map, points3D, img, 100); 
        
        dfMap.show(img);
        new PointCloud().displayAsMap(points3D, Math.max(size ,maxZ),maxZ);
    }
}
