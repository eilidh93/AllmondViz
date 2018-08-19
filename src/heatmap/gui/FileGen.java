/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap.gui;

import java.util.ArrayList;

/**
 *
 * @author Eilidh Greig
 */

 
public class FileGen {
    
private String fileNam;  
public ArrayList <Genus> genO;
//private int xPos;
    

    public FileGen(){
        genO = new ArrayList();
    }
    
    public void setFileName(String name){
        this.fileNam=name;
    }
    
    public String getFileName(){
        return fileNam;    
    }
    
  
    public Boolean checkGen(String gen){
        boolean genExists = false;
        for (int i=0; i < genO.size(); i++){
            if (genO.get(i).getGenName().equals(gen)){
                genExists = true;
            }
        }
        return genExists;
    }
    
    public void addGen(String genN){
        genO.add(new Genus());
       // System.out.println(famO.size());
        int add = genO.size()-1;
        genO.get(add).setGenName(genN);
    }
    
    //method for diversity score 

    /**
     *
     * @return
     */
    public double conScore(){
        double totC=0.0;
        for (int i=0;i<genO.size();i++){
            totC+=genO.get(i).totCon();   
        }
        return totC; 
    }
    
    //total of all the props x lnprops
    public double divScore(){
        double divL = 0.0;
        for (int i=0;i<genO.size();i++){
            divL+=genO.get(i).getLogProp();
        }
        return divL;
    }
}
