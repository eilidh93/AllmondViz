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

//info needed for ordering when user selects
public class OrderFam implements Comparable <OrderFam> {
    
    private String fa,o,g;
    private int yp;
    
    public OrderFam(){
        
    }
    
    public void setFam(String f){
        this.fa=f;
    }
    
    public void setOrder(String o){
        this.o=o;
    }
    
    public String getFam(){
        return fa;
    }
    
    public String getOrder(){
        return o;
    }
    
    public void setGroup (String g){
        this.g=g;
    }
    
    public String getGroup(){
        return g;
    }
    
    public void setY(int y){
        this.yp=y;
    }
    
    public int getY(){
        return yp;
    }
    
    //for alphabetical order in whatever ordering is chosen by user
    @Override
    public int compareTo(OrderFam orF){
        int fam = this.getFam().compareTo(orF.getFam());
        return fam;
    }
    
}
