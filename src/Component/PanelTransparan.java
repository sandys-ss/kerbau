/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author SANDYS
 */
public class PanelTransparan extends JPanel{
    
    private Color warna;

    public PanelTransparan() {
        setOpaque(false);
        warna = new Color(getBackground().getRed(), getBackground().getGreen(), 
                getBackground().getBlue(), 20);
        
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); 
        
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setColor(warna);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        
    }
    
       
}
