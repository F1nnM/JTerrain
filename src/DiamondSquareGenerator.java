import java.awt.image.BufferedImage;
import java.util.Random;

public class DiamondSquareGenerator {

    static private int size, max;
    static private float[][] map;
    static private Random rnd;

    public static void generate(int detail, float roughness, int maxZ) {
        float[][] map = generateMap(detail, roughness);

        DrawingFrame dfMap = new DrawingFrame(map.length,map[0].length);
        BufferedImage img = dfMap.getCompatibleImage();

        Point3D[] points3D = new Point3D[map.length*map[0].length];
        Tools.mapToRGB(map, img);
        Tools.mapToPointCloud(map, points3D, img, maxZ);

        dfMap.show(img);
        new PointCloud().displayAsMap(points3D, Math.max(Math.max(map.length,map[0].length),maxZ),maxZ);
    }

    private static float[][] generateMap(int detail, float roughness){
        //initialize
        size = (int) Math.pow(2,detail)+1;
        max = size - 1;
        map = new float[size][size];
        if (rnd == null)
            rnd = new Random();

        //square diamond magic
        set(0,0, rnd.nextFloat()*max/2);
        set(max,0,rnd.nextFloat()*max/2);
        set(max, max, rnd.nextFloat()*max/2);
        set(0, max, rnd.nextFloat()*max/2);
        divide(max, roughness);

        //clamp map values TODO: cut edges // seamless transitions??
        for (int x = 0; x < max; x++)
            for (int y = 0; y < max; y++) {
                set(x,y,get(x,y)/((float)size));
            }
        return map;
    }

    private static void divide (int size, float roughness) {
        int x = size / 2;
        int y;
        int half = x;
        float scale = roughness * size;
        if (half < 1)
            return;

        for (y = half; y < max; y += size)
            for (x = half; x < max; x += size) {
                square(x,y,half, rnd.nextFloat() * scale * 2 - scale);
            }

        for (y = 0; y <= max; y += half)
            for (x = (y + half) % size; x <= max; x += size) {
                diamond(x, y, half, (float) rnd.nextFloat() * scale * 2 - scale);
            }
        divide(size/2, roughness);
    }

    private static void square(int x, int y, int size, float offset) {
        float avg = 0;
        avg += get(x - size, y - size); //oben links
        avg += get(x + size, y - size); //oben rechts
        avg += get(x + size, y + size); //unten rechts
        avg += get(x - size, y + size); //unten links
        avg = avg / 4;
        set(x, y, avg + offset);
    }

    private static void diamond(int x, int y, int size, float offset) {
        float avg = 0;
        avg += get(x, y - size); //oben
        avg += get(x + size, y); //rechts
        avg += get(x, y + size); //unten
        avg += get(x - size, y); //links
        avg = avg / 4;
        set(x, y, avg + offset);
    }

    private static float get(int x, int y) {
        if (x < 0 || x > max || y < 0 || y > max)
            return -1;
        return map[x][y];
    }

    private static void set(int x, int y, float value) {
        map[x][y] = value;
    }

    public static void setSeed(long seed) {
        rnd = new Random();
        rnd.setSeed(seed);
    }
}
