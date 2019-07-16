import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class DrawingFrame
{
    private JFrame frame;
    private DrawingPanel dp;
    
    private JLabel infoLabel;
    
    private int width, height;
    
    public DrawingFrame(int width, int height)
    {
        this.width = width;
        this.height = height;
        
        frame = new JFrame("Drawing Panel");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        
        infoLabel = new JLabel();
        frame.add(infoLabel);
        
        dp = new DrawingPanel();
        dp.setPreferredSize(new Dimension(width,height));
        frame.add(dp);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public BufferedImage getCompatibleImage(){
        return dp.getGraphicsConfiguration().createCompatibleImage(width,height);
    }
    
    public void show(BufferedImage img){
        dp.setImage(img);
    }
    
    public void updateInfo(String info){
        infoLabel.setText(info);
    }
    
    class DrawingPanel extends JPanel{
        BufferedImage img;
        void setImage(BufferedImage img){
            this.img = img;
            repaint();
        }
        
        @Override
        public void paint(Graphics g) {
            g.drawImage(img,0,0,this);
        }
    }
}
