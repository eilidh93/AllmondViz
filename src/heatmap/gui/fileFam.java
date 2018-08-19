/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap.gui;

import java.util.ArrayList;
import java.awt.Color;

/**
 *
 * @author Eilidh Greig
 */

/*all info about each sample/file
array of family objects
*/
public class fileFam {
  
private String fileNam,tn;  
private double avL,avE,me,ml;
public ArrayList <family> famO;
//private int xPos;
    

    public fileFam(){
        famO = new ArrayList();
    }
    
    public void setFileName(String name){
        this.fileNam=name;
    }
    
    public String getFileName(){
        return fileNam;    
    }
    
   //check if fam exists
    public Boolean checkFam(String fam){
        boolean famExists = false;
        for (int i=0; i < famO.size(); i++){
            if (famO.get(i).getFamName().equals(fam)){
                famExists = true;
            }
        }
        return famExists;
    }
    
    //add a new family to famO list
    public void addFam(String famN){
        famO.add(new family());
       // System.out.println(famO.size());
        int add = famO.size()-1;
        famO.get(add).setFamName(famN);
    }
    
    //num contigs in file
    public Double conScore(){
        double totC=0.0;
        for (int i=0;i<famO.size();i++){
            totC+=famO.get(i).totCon();
        }
        return totC; 
    }
    
    //total of all the props x lnprops
    public double divScore(){
        double divL = 0.0;
        for (int i=0;i<famO.size();i++){
            divL+=famO.get(i).getLogProp();
        }
        return divL;
    }
    
    //trim file path to get just sample name
    public String getTrimmedName(){
        tn=fileNam.substring(fileNam.lastIndexOf("\\") + 1,fileNam.lastIndexOf("."));
        return tn;
    }
    

    public double getAvgLength(){
        //get total num of contigs for this file
        //sum all the lengths
        Double tot=0.0;
        Double tl=0.0;
        for (int i=0;i<famO.size();i++){
            tot+=famO.get(i).totCon();
            tl+=famO.get(i).getTotL();
        }
        //get average
        
        
        return avL;
    }
    
    public void setAvgE(double avE){
        this.avE=avE;
    }
    
    public double getAvgE(){
        return avE;
    }
    
    public void setMaxL(double ml){
        this.ml=ml;
    }
    
    public double getMaxL(){
        return ml;
    }
    
    public void setMinE(double me){
        this.me=me;
    }
    
    public double getMinE(){
        return me;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
    
