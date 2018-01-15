package Component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class PanelBackground extends JPanel{

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); 
        
        Graphics2D g2 = (Graphics2D) grphcs.create();
        
        Shape shape = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        //Paint paint = new Color(31, 34, 43);
        Paint paint = new GradientPaint(0,0,new Color(3,44,66), 0, getHeight(),
        new Color(43, 139, 155));
        g2.setPaint(paint);
        g2.fill(shape);
        g2.dispose();
        
    }   
}
