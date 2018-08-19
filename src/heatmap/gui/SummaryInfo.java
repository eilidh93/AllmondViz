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
public class SummaryInfo {
 
    private String sample;
    private int fn,gn;
    private double avgL,avgE,minE,maxL;
    
    public SummaryInfo(){
        
    }
    
    public void setSampleName(String sample){
       this.sample=sample; 
    }
    
    public String getSampleName(){
        return sample;
    }
    
    public void setFamNum(int fn){
        this.fn=fn;
    }
    
    public int getFamNum(){
        return fn;
    }
    
    public void setGenNum(int gn){
        this.gn=gn;
    }
    
    public int getGenNum(){
        return gn;
    }
    
    public void setAvgL(double avgL){
        this.avgL=avgL;
    }
    
    public double getAvgL(){
        return avgL;
    }
    
    public void setAvgE(double avgE){
        this.avgE=avgE;
    }
    
    public double getAvgE(){
        return avgE;
    }
    
    public void setMaxL(double maxL){
        this.maxL=maxL;
    }
    
    public double getMaxL(){
        return maxL;
    }
    
    public void setMinE(double minE){
        this.minE=minE;
    }
    
    public double getMinE(){
        return minE;
    }
    
    
    
    
    
}
