import java.awt.Color;
import java.util.*;

public class Point3D
{
    private float x,y,z;
    private int color;
    
    public Point3D(float x, float y, float z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color.getRGB();
    }
    
    public Point3D(float x, float y, float z, int color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }
    
    public Point3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        z += (PointCloud.size/2);
        System.out.println(z);
        this.color = new Color (255,  255-(int)(z/PointCloud.size*255.0),255-(int)(z/PointCloud.size*255.0)).getRGB();
        z -= (PointCloud.size/2);
    }
    
    public Point3D(float x, float y) {
        Random r = new Random();
        this.x = x;
        this.y = y;
        this.z = r.nextFloat()*PointCloud.size;       
        color = Color.getHSBColor((float) z/PointCloud.size,1,1).getRGB();        
        //color = new Color (255,  255-(int)(z/PointCloud.size*255.0),255-(int)(z/PointCloud.size*255.0));        
        this.z -= (PointCloud.size/2);
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getZ() {
        return z;
    }
    
    public Point3D scale(float i) {
        return new Point3D(x*i, y*i, z*i, color);
    }
    
    public Point3D rotateZ(float r) {
        return new Point3D((OptiMath.cos(r)*x - OptiMath.sin(r)*y), (OptiMath.sin(r)*x + OptiMath.cos(r)*y), z, color);
    }
    
    public Point3D rotateX(float r) {
        return new Point3D(x, (OptiMath.cos(r)*y - OptiMath.sin(r)*z), (OptiMath.sin(r)*y + OptiMath.cos(r)*z), color);
    }
    
    public Point3D rotateY(float r) {
        return new Point3D((OptiMath.cos(r)*x+OptiMath.sin(r)*z), y, (-OptiMath.sin(r)*x+OptiMath.cos(r)*z),color);
    }
    
    public Point3D rotateDeg(float xr, float yr, float zr) {        
        return rotateX(xr).rotateY(yr).rotateZ(zr);
    }
    
    public Point2D project2D() {
        float xn = y;
        float yn = -z;
        return new Point2D(xn, yn, color);
    }
    
    public int getColor() {
        return color;
    }
    
    static class SortByX implements Comparator<Point3D> {
        public int compare(Point3D a, Point3D b) {
            return (int) Math.signum(a.getX()-b.getX());
        }
    }
}
