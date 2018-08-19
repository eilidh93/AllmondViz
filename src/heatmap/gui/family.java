/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap.gui;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Eilidh Greig
 */
public class family{
    
    /*stores the information for each viral family within each sample
    created by file fam - each file is an array of family objects
    everything need to perform scaling calculations/info that is extracted for 
    heatmap purposes
    */
   
public ArrayList <Double> contigLength;
    private ArrayList <Double> eValue;
public ArrayList <String> contigNames, genusNames;
private ArrayList <Integer> ePow;
private String famName,o,g;
private int ypos, xPos;
private boolean isPhage,isInv;
private double pr,lpr,seg,gen;

    //make arraylists needed
    public family(){
        contigLength = new ArrayList();
        genusNames = new ArrayList();
        contigNames = new ArrayList();
        eValue = new ArrayList();
        ePow = new ArrayList();
    }
    
    //viral family name from file
    public void setFamName(String fam){
        this.famName = fam;
    }
    
    public String getFamName(){
        return famName;
    }
    
    //total length of all contigs within the family
    public Double getTotL(){
        Double totL = 0.0;
        for (int i=0;i<contigLength.size();i++){
            totL+=contigLength.get(i);
        }
        return totL;
     }
    
    //set if the family is a phage
    public void setPhage(boolean isPhage){
        this.isPhage=isPhage;
    }
    
    public Boolean getPhage(){
        return isPhage;
    }
    
    //set if the virus infects vertebrates
    public void setInv(boolean isInv){
        this.isInv=isInv;
    }
    
    public Boolean getInv(){
        return isInv;
    }
    
    //add the name of contig to list of contigs for that family
    public void setContigNames(String con){
        contigNames.add(con);
    }
    
    //add genus names to list within the family
    public void setGenNames(String gen){
        genusNames.add(gen);
    }
    
    //set e-Value
    public void setE(Double e){
        eValue.add(e);
    }
    
    //set contig length
    public void setL(Double l){
        contigLength.add(l);
    }
    
    //set the evalue power (used for scaling instead of actual e-Value
    public void setEP(int ep){
        ePow.add(ep);
    }
    
    //smallest e-Value power
    public int getMinEP(){
        int maxE = 0;
        for (int i=0;i<ePow.size();i++){
            if (ePow.get(i)>maxE){
                maxE=ePow.get(i);
            }
        }
        int minE=maxE;
        for(int j=ePow.size()-1;j>=0;j--){
            if(ePow.get(j)<minE){
                minE=ePow.get(j);
            }
        }
        return minE;
        
    }
    
    //Average e-Value power
    public Double getAvgEP(){
        Double tot = 0.0;
        for (int i=0; i<ePow.size(); i++){
            tot += ePow.get(i);
        }
        Double avg = tot/ePow.size();
        return avg;
    }

    
    //return a length from specified contig
    public Double getLength(int i){
        Double whatLength = contigLength.get(i);
        return whatLength;
    }
    
    //average contig length per family
     public Double avgConL(){
        Double tot = 0.0;
        for (int i=0; i<contigLength.size(); i++){
            tot += contigLength.get(i);
        }
        Double avg = tot/contigLength.size();
        return avg;
    }
     
     //total number of contigs in a family
     public int totCon(){
         int tot = contigNames.size();
         return tot;
     }
     
     //total contig length per family
     public Double getSumConL(){
        Double tot = 0.0;
        for (int i=0; i<contigLength.size(); i++){
            tot += contigLength.get(i);
        }
        return tot;
     }
     
     //maximum contig length
     public Double getMaxLength(){
        
        Double maxL = 0.0;
        for (int i=0;i<contigLength.size();i++){
            if (contigLength.get(i)>maxL){
                maxL=contigLength.get(i);
            }
        }
        return maxL;
        }
    
     //set order of family
    public void setOrd(String o){
        this.o=o;
    }
    
    public String getOrd(){
        return o;
    }
    
    //set what group fam belongs to e.g. ssRNA
    public void setGroup(String g){
        this.g=g;
    }
    
    public String getGroup(){
        return g;
    }
    
    public String getConName(int i){
        String whatName = (String)contigNames.get(i);
        return whatName;
    }
    
    public int getConNameLength(){
        int len = contigNames.size();
        return len;
    }
    
    
    public String getGenName(int i) {
        String whatGen = (String)genusNames.get(i);
        return whatGen;
    }
    

    
     public Double getEVal(int i){
        Double whatVal = eValue.get(i);
        return whatVal;
    }
    
    public Double getMinEV(){
        
        Double maxE = 0.0;
        for (int i=0;i<eValue.size();i++){
            if (eValue.get(i)>maxE){
                maxE=eValue.get(i);
            }
        }
        Double minE=maxE;
        for(int j=eValue.size()-1;j>=0;j--){
            if(eValue.get(j)<minE){
                minE=eValue.get(j);
            }
        }
        return minE;
        }
    
    public Double avgEV(){
        Double tot = 0.0;
        for (int i=0; i<eValue.size(); i++){
            tot += eValue.get(i);
        }
        Double avg = tot/eValue.size();
        return avg;
    }
    
    //yposition of the heatmap rectangle
    public int getY(){
        return ypos;
    }
    
    public void setY(int y){
        this.ypos=y;
    }
    
    //xposition of the heatmap rectangle
      public void setX(int x){
        this.xPos=x;
    }
    
    public int xPos(){
        return xPos;
    } 
    
    //proportion of contigs used for shannon entropy calc
      public double getProp(){
        return pr;
    }
    
    public void setProp(Double pr){
        this.pr=pr;
    }

    //log proportion - shannon entropy calc
    public void setLogProp(Double lpr){
        this.lpr=lpr;
    }
    
    public double getLogProp(){
        return lpr;
    }
    
    //fams average segment size
    public void setAvgSeg(double seg){
        this.seg=seg;
    }
    
    public double getAvgSeg(){
        return seg;
    }
    
    //fams average genome size
    public void setAvgGen(double gen){
        this.gen=gen;
    }
    
    public double getAvgGen(){
        return gen;
    }
    
    
}
