import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;

public class HeightMapLoader
{
    public static void load(String path, int maxZ, int f){
        BufferedImage img;
        try{
            img = ImageIO.read(new File(path));
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        
        int height = img.getHeight();
        int width = img.getHeight();
        
        ArrayList<Point3D> list = new ArrayList();
        int i = 0;
        for(int x = 0; x<width; x+=f) 
            for(int y=0;y<height;y+=f){
                Color col = new Color(img.getRGB(x,y));
                float pointHeight = (col.getRed()/255.0F)*maxZ;
                list.add(new Point3D(x,y, pointHeight, col));
                i++;
            }       
        new PointCloud().displayAsMap(list.toArray(new Point3D[0]), Math.max(width, height), maxZ);
    }

    public static void loadDefault(){
        load("C:\\Users\\Paul\\Desktop\\3 Height Map (Merged).png", 100, 4);
    }
    
}
