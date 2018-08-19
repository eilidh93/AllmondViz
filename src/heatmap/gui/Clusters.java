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


/*
    class should hold data used for clustering 
    each cluster is one version of the class
    so for each stage the list of cluster objects gets smaller
    each cluster object contains a list - each with one sample/dist to begin with (corresponds to num of files)
    then gradually decrease until just once cluster in the correct order
    the list of names in cluster one should then be the order that the samples will be clustered in
    distance matrix needs recalculated inbetween

*/
public class Clusters {
    
    public ArrayList <String> chClust;
    public ArrayList <Double> chDist;
    
    public Clusters(){
        chClust = new ArrayList();
        chDist = new ArrayList();
    }
    
    public void setClusterName(){
        
    }
    
    public void addChildClust(String ch){
        chClust.add(ch);
    }
    
    public void addChildDist(Double dist){
        chDist.add(dist);
    }
    
    public int getDistSize(){
        return chDist.size();
    }
    
    public void addSample(){
        
    }
    
    public void addDist(){
       
    }
    
    public void lowDist(){
        
    }
    
    
    
}
