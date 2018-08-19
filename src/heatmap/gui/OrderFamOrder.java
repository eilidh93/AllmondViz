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
public class OrderFamOrder implements Comparable <OrderFamOrder>{
    
    public ArrayList <String> fams;
    private String order;
    
    
    public OrderFamOrder(){
        fams=new ArrayList();
    }
    
    public void setOrder(String order){
        this.order=order;
    }
    
    public String getOrder(){
        return order;
    }
    
    public void setFamNames(String fa){
        fams.add(fa);
    }
    
    @Override
    public int compareTo(OrderFamOrder orO){
        int fm = this.getOrder().compareTo(orO.getOrder());
        return fm;
    }
    
}
