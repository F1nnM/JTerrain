import java.awt.Color;
public class Point2D
{
    private double x, y;
    private int color;
    public Point2D(double x, double y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color.getRGB();
    }
    
    public Point2D(double x, double y, int color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public int getColor() {
        return color;
    }
}
