/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

/**
 *
 * @author Depo2
 */
public class Tombol_Master extends JButton {
    
    private Paint gradien;
    private Paint glass;
    private boolean over;
    private Shape shape;
        
    public void setover (boolean over) {
        this.over = over;
        repaint();
    }
    
    public boolean isover () {
        return over;
    }

    public Tombol_Master() {
        
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {              
                setover(true); 
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setover(false);
            }
        
        });             
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        
        Graphics2D g2 = (Graphics2D) grphcs.create();
        
        gradien = new Color(3, 44, 64);
        shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                10, 10);
        
        if (isover()) {
            //warna ketika di sorot
            glass = new Color(3, 44, 64);
        } else {
            glass = new Color(43, 139, 155);
        }
        
        g2.setPaint(gradien);
        g2.fill(shape);
        
        g2.setPaint(glass);
        g2.fill(shape);
        g2.dispose();
        
        super.paintComponent(grphcs); 
    }
        
        
    
}
