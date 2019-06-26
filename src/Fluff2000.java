import java.awt.image.*;
import java.awt.*;
import java.util.Random;

public class Fluff2000
{
    public Fluff2000() {
        DrawingFrame dfMap = new DrawingFrame(600, 600);
        BufferedImage img = dfMap.getCompatibleImage();
        
        int size = 9;
        
        float[][] map = generateDiamondSquare(size); 
        map = Tools.simpleBlur(map,2);
        Tools.mapToRGB(map, img);
        Point3D[] points3D = Tools.RGBtoPointCloud(img);
        dfMap.show(img);
        new PointCloud().displayAsMap(points3D, 500,100);
    }
    
    public static float[][] generateDiamondSquare(int size)
    {
        size = (int) Math.pow(2,size)+1;
        float[][] map = new float[size][size];
        log(size);
        
        Square sq = new Square(map);
        sq.init();
        map = sq.squareDiamond();
        
        return map;
    }
    
    static class Square {
        Random r;
        int size;
        float[][] square;
        public Square(float[][] square) {
          r = new Random();
          this.square = square;
          size = square.length-1;
        }
        
        public void init() {
            square[0][0] = random();
            square[0][size-1] = random();
            square[size-1][0] = random();
            square[size-1][size-1] = random();   
        }
        
        //rekursive Funktion
        public float[][] squareDiamond() {
            diamond();
            square();
            
            if (size > 2) {
                Square[] sq = split();
                for (Square s : sq) {
                    s.squareDiamond();
                }
                merge(sq);
                return square;
            }
            return square;
        }
        
        public void diamond() {
            
            float avg = (square[0][0] + square[size][0] + square[0][size] + square[size][size] + random())/5;
            int pos = size/2-1;
            log ("Diamond avg="+avg+" ["+pos+","+pos+"] ["+size+","+size+"]");
            square[pos][pos] = avg;
        }
        
        public void square() {
            int pos = size/2-1;
            log ("Square ["+size+","+size+"]");
            square[0][pos] = (square[0][0]+square[pos][pos]+square[0][size]+random())/4;
            log ("   > ["+0+","+pos+"] avg="+square[0][pos]);
            square[pos][0] = (square[0][0]+square[pos][pos]+square[size][0]+random())/4;
            log ("   > ["+pos+","+0+"] avg="+square[pos][0]);
            square[pos][size] = (square[0][size]+square[pos][pos]+square[size][size]+random())/4;
            log ("   > ["+pos+","+size+"] avg="+square[pos][size]);
            square[size][pos] = (square[size][0]+square[pos][pos]+square[size][size]+random())/4;
            log ("   > ["+size+","+pos+"] avg="+square[size][pos]);
        }
        
        private float random() {
            return 0.8F-r.nextFloat();
        }
        
        public float[][] getSquare() {
            return square;
        }
        
        public void merge(Square[] sq) {
            log ("Merge ["+size+","+size+"]");
            int pos = sq[0].getSquare().length-1;
            for (int x = 0; x < pos; x++)
                for (int y = 0; y < pos; y++) {
                    square[x][y] = sq[0].getSquare()[x][y];
                    square[x+pos][y] = sq[1].getSquare()[x][y];
                    square[x][y+pos] = sq[2].getSquare()[x][y];
                    square[x+pos][y+pos] = sq[3].getSquare()[x][y];
                }       
        }
        
        public Square[] split() {
            int pos = size/2+1;
            log("Splitting to 4x "+pos+"x"+pos+" chunks.");
            float[][] ol = new float[pos][pos];
            float[][] or = new float[pos][pos];
            float[][] ul = new float[pos][pos];
            float[][] ur = new float[pos][pos];
            
            pos -= 1;
            for (int x = 0; x <= pos; x++)
                for (int y = 0; y <= pos; y++) {
                    ol[x][y] = square[x][y];
                    or[x][y] = square[x+pos][y];
                    ul[x][y] = square[x][y+pos];
                    ur[x][y] = square[x+pos][y+pos];
                }
            
            Square[] split = new Square[4];
            split[0] = new Square(ol);
            split[1] = new Square(or);
            split[2] = new Square(ul);
            split[3] = new Square(ur);
            
            return split;
        }
    }
    
    public static void log (Object t) {
        //System.out.println(t);
    }
}
