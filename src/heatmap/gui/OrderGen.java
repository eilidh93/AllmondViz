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
public class OrderGen implements Comparable <OrderGen>{
    
    private String gen,fam,o,gr;
    private int yp;
    
    public OrderGen(){
        
    }
   
    public void setGen(String g){
        this.gen=g;
    }
    
    public String getGen(){
        return gen;
    }
    
    public void setFam(String fam){
        this.fam=fam;
    }
    
    public String getFam(){
        return fam;
    }
    
    public void setOrder(String o){
        this.o=o;
    }
    
    public String getOrder(){
        return o;
    }
    
    public void setGroup(String gr){
        this.gr=gr;
    }
    
    public String getGroup(){
        return gr;
    }
    
    public void setY(int y){
        this.yp=y;
    }
    
    public int getY(){
        return yp;
    }
    
    @Override
    public int compareTo(OrderGen orG){
        int gn = this.getGen().compareTo(orG.getGen());
        return gn;
    }
     
}
