/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap.gui;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Eilidh Greig
 */
public class ContigFile {
    
    public static String newline = System.getProperty("line.separator");
    
    public ContigFile(){
        
    }
    
    public void readContigFile(String f,String c){

        //get the contigs starting after the ">"
        String cons = c.substring(c.lastIndexOf(">")+1).trim();
        String [] searchStrings = cons.split("\n");
        ArrayList <String> fl = new ArrayList();
        
        
        StringBuilder sb = new StringBuilder();
        
        //read the file and search for the contig and extrat the sequences
        Scanner scan = null;
        try{
            scan = new Scanner(new FileReader(f));
            while(scan.hasNext()){
                fl.add(scan.nextLine());
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        finally{
            try {
                scan.close();
            }
            catch(Exception e){
                
            }
        }
        
        //search for the contig names and append the sequences until line contains ">"
        boolean nextC = false;
        for (int i=0;i<fl.size();i++){
            for (int j=0;j<searchStrings.length;j++){
                if(fl.get(i).equals(">" + searchStrings[j].trim())){
                    sb.append(newline + ">" + searchStrings[j]+ newline);
                    int k=i+1;
                    while(!nextC){
                        if(fl.get(k).contains(">")){
                            nextC=true;
                        }
                        else{
                            sb.append(fl.get(k));
                            k++;
                        }
                    }
                    nextC=false;
                }
            }
        }
        
        //get the new filename from string c 1st line
        //e.g. sample_fam/genus_contigs.txt
        
        String fName=c.substring(c.indexOf("in")+3, c.indexOf(".txt"))+ "_" +c.substring(c.indexOf("for")+4,c.indexOf("in")-1) + "_contigs.txt";
        //System.out.println(fName);
        
        
        //System.out.println(sb);
        BufferedWriter buff = null;
        FileWriter fwrite = null;
            try {
                fwrite = new FileWriter(fName);
                buff = new BufferedWriter(fwrite);
                buff.write(sb.toString());
		} 
            catch (IOException e) {
		e.printStackTrace();
            } 
            finally {
                try {
                    if (buff != null){
                        buff.close();
                    }    
                    if (fwrite != null){
                        fwrite.close();
                    }    
                } 
                catch (IOException ex) {
                    ex.printStackTrace();
                }

		}

	}
        
        
    }


    

