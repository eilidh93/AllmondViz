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
public class SortX {
    
    private String fam;
    private int startLine, endLine, drawFam;
    
    public SortX(){
        
    }
    
    public String getFam(){
        return fam;
    }
    
    public void setFam(String f){
        this.fam=f;
    }
    
    public int getStart(){
        return startLine;
    }
    
    public void setStart(int s){
        this.startLine=s;
    }
    
    public int getEnd(){
        return endLine;
    }
    
    public void setEnd(int e){
        this.endLine=e;
    }
    
    public void setDraw(int d){
        this.drawFam=d;
    }
    
    public int getDraw(){
        return drawFam;
    }
    
    
}
