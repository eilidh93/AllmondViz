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
public class OrderGroupFam implements Comparable <OrderGroupFam> {
    public ArrayList <String> fams;
    private String gr;
    
    
    public OrderGroupFam(){
        fams=new ArrayList();
    }
    
    public void setGroup(String gr){
        this.gr=gr;
    }
    
    public String getGroup(){
        return gr;
    }
    
    public void setFamNames(String fa){
        fams.add(fa);
    }
    
    @Override
    public int compareTo(OrderGroupFam og){
        int fm = this.getGroup().compareTo(og.getGroup());
        return fm;
    }
}
