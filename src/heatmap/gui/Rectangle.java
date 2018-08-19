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

import java.awt.Color;

public class Rectangle {
    
    private Color col;
    private int x, y;
    
    public Rectangle(){
        
    }
    
    public void setXP(int x){
        this.x=x;
    }
    
    public int getXP(){
        return x;
    }
    
    public void setYP(int y){
        this.y=y;
    }
    
    public int getYP(){
        return y;
    }
    
    public void setCol(Color c){
        this.col=c;
    }
    
    public Color getCol(){
        return col;
    }
    
    
    
    
}
