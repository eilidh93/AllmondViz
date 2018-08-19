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
public class Genus{
   
public ArrayList <Double> contigLength, eValue;
public ArrayList <String> contigNames;
private ArrayList <Integer> ePow;
private String genName,famName,g,o;
private int ypos, xPos;
private boolean isPhage,isInv;
private Double pr,lpr,ag,as;

    
    public Genus(){
        contigLength = new ArrayList();
        contigNames = new ArrayList();
        eValue = new ArrayList();
        ePow = new ArrayList();
    }
    
    public void setGenName(String gen){
        this.genName = gen;
    }
    
    public void setAvgGen(Double ag){
        this.ag=ag;
    }
    
    public Double getAvgGen(){
        return ag;
    }
    
    public void setAvgSeg(Double as){
        this.as=as;
    }
    
    public Double getAvgSeg(){
        return as;
    }
    
    public String getGenName(){
        return genName;
    }
    
    public void setFamName(String fam){
        this.famName=fam;
    }
    
    public String getFamName(){
        return famName;
    }
    
    public void setPhage(boolean isPhage){
        this.isPhage=isPhage;
    }
    
    public Boolean getPhage(){
        return isPhage;
    }
    
    public void setInv(boolean isInv){
        this.isInv=isInv;
    }
    
    public Boolean getInv(){
        return isInv;
    }
    
    public void setContigNames(String con){
        contigNames.add(con);
    }
    
    
    public void setE(Double e){
        eValue.add(e);
    }
    
    public void setL(Double l){
        contigLength.add(l);
    }
    
    public void setEP(int ep){
        ePow.add(ep);
    }
    
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
    
    public Double getAvgEP(){
        Double tot = 0.0;
        for (int i=0; i<ePow.size(); i++){
            tot += ePow.get(i);
        }
        Double avg = tot/ePow.size();
        return avg;
    }

    
    public Double getLength(int i){
        Double whatLength = contigLength.get(i);
        return whatLength;
    }
    
     public Double avgConL(){
        Double tot = 0.0;
        for (int i=0; i<contigLength.size(); i++){
            tot += contigLength.get(i);
        }
        Double avg = tot/contigLength.size();
        return avg;
    }
     
     public int totCon(){
         int tot = contigNames.size();
         return tot;
     }
     
     public Double getSumConL(){
        Double tot = 0.0;
        for (int i=0; i<contigLength.size(); i++){
            tot += contigLength.get(i);
        }
        return tot;
     }
     
     public Double getMaxLength(){
        
        Double maxL = 0.0;
        for (int i=0;i<contigLength.size();i++){
            if (contigLength.get(i)>maxL){
                maxL=contigLength.get(i);
            }
        }
        return maxL;
        }
     
     public Double getTotL(){
        Double totL = 0.0;
        for (int i=0;i<contigLength.size();i++){
            totL+=contigLength.get(i);
        }
        return totL;
     }
    
    
    public String getConName(int i){
        String whatName = (String)contigNames.get(i);
        return whatName;
    }
    
    public int getConNameLength(){
        int len = contigNames.size();
        return len;
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
    
    public int getY(){
        return ypos;
    }
    
    public void setY(int y){
        this.ypos=y;
    }
    
      public void setX(int x){
        this.xPos=x;
    }
    
    public int xPos(){
        return xPos;
    } 
    
    public double getProp(){
        return pr;
    }
    
    public void setProp(Double pr){
        this.pr=pr;
    }

    public void setLogProp(Double lpr){
        this.lpr=lpr;
    }
    
    public double getLogProp(){
        return lpr;
    }
    
    public void setOrd(String o){
        this.o=o;
    }
    
    public void setGroup(String g){
        this.g=g;
    }
    
    public String getOrd(){
        return o;
    }
    
    public String getGroup(){
        return g;
    }
}
