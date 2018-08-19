/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap.gui;

/**
 *
 * @author Eilidh Greig
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.geom.AffineTransform;

public class InfoPane extends JPanel{
    
    public InfoPane(){
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(0,0);
    }
    
    private Image sumImage(){
        BufferedImage im = new BufferedImage(0,0,BufferedImage.TYPE_INT_ARGB);
        return im;
    }
    
    
    
}
