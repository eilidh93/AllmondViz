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
public class ClusterX {
    
    private String c;
    private int x;
    
    public ClusterX(){
        
    }
    
    public void setClust(String c){
        this.c=c;
    }
    
    public String getClust(){
        return c;
    }
    
    public void setX(int x){
        this.x=x;
    }
    
    public int getX(){
        return x;
    }
}
