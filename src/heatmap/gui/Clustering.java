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
public class Clustering {
    
    private double [][] dist;
    public ArrayList <String> samNames;
    public ArrayList <Clusters> cl;
    public ArrayList <Dendrogram> dr;
    
    public Clustering(ArrayList <String> names, ArrayList <Double> fams){
        
        //need to keep track of the clusters as they form so can draw the dendrogram
        
        dr = new ArrayList();
        //first create the list of clusters with just one child per cluster
        //add cluster name and score?
        cl = new ArrayList();
        for (int i=0;i<names.size();i++){
            cl.add(new Clusters());
            cl.get(cl.size()-1).addChildClust(names.get(i));
            cl.get(cl.size()-1).addChildDist(fams.get(i));
        }
        
        
        //create temp distance matrix now
        //find smallest exluding zero (index) then then match the index to the names index
        //not except zero, just except the zero that corresponds to  the names matching up with themself
        //add the smallest distance
        if(cl.size()>1){
            while(cl.size()>2){

                int mink=0;
                int minl=0;
                dist = new double [fams.size()][fams.size()];
                for (int i=0;i<cl.size();i++){
                    for (int j=0;j<cl.size();j++){
                        double mindist=1000;
                        for (int k=0;k<cl.get(i).getDistSize();k++){
                            for (int l=0;l<cl.get(j).getDistSize();l++){
                                double distance=Math.abs(cl.get(i).chDist.get(k)-cl.get(j).chDist.get(l));
                                if(distance<mindist){
                                    mink=k;
                                    minl=l;
                                }
                            }
                        }

                        dist[i][j]=Math.abs(cl.get(i).chDist.get(mink)-cl.get(j).chDist.get(minl));
                    }
                }

                int mini=0;
                int minj=0;
                double max=0.0;

                //find max for starting point
                for(int i=0;i<cl.size();i++){
                    for(int j=0;j<cl.size();j++){
                        if (dist[i][j]>max){
                            max = dist[i][j];
                        }
                    }
                }

                //find the minimum
                //if more have the same min distance we'll just take the first one found
                double min = max;
                for(int i=0;i<cl.size();i++){
                    for(int j=0;j<cl.size();j++){
                        if((dist[i][j]<min)&&(i!=j)){
                            mini=i;
                            minj=j;
                            min=dist[i][j];
                        }
                    }
                }
                //take note of the position of the cluster staying in the same position
                dr.add(new Dendrogram());
                dr.get(dr.size()-1).setOne(mini);
                //get size of cluster to see where adding to
                int cls = cl.get(mini).getDistSize();
                dr.get(dr.size()-1).setTwo(mini+cls);
                //merge the shortest distance clusters
                for (int i=0;i<cl.get(minj).chClust.size();i++){
                    cl.get(mini).addChildClust(cl.get(minj).chClust.get(i));
                }

                for (int i=0;i<cl.get(minj).chDist.size();i++){
                    cl.get(mini).addChildDist(cl.get(minj).chDist.get(i));
                }

                //delete j index
                cl.remove(minj);
                names.remove(minj);
                fams.remove(minj);


            }

            //merge the final 2 clusters
            for (int i=0;i<cl.get(1).chClust.size();i++){
                cl.get(0).addChildClust(cl.get(1).chClust.get(i));
                cl.get(0).addChildDist(cl.get(1).chDist.get(i));
            }
            cl.remove(1);

            


            }
        
            samNames=new ArrayList();
            for(int i=0;i<cl.get(0).chClust.size();i++){
                samNames.add(cl.get(0).chClust.get(i));
            }
   
        
        }
    
}



















