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
public class OrderGenFam implements Comparable <OrderGenFam>{
    
    public ArrayList <String> gens;
    private String fam;
    
    
    public OrderGenFam(){
        gens=new ArrayList();
    }
    
    public void setFam(String fam){
        this.fam=fam;
    }
    
    public String getFam(){
        return fam;
    }
    
    public void setGenNames(String gen){
        gens.add(gen);
    }
    
    @Override
    public int compareTo(OrderGenFam orG){
        int fm = this.getFam().compareTo(orG.getFam());
        return fm;
    }
    
}
