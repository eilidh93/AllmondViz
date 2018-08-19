/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap.gui;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 *
 * @author Eilidh Greig
 */
public class fileH {
    
public ArrayList <fileFam> ff = new ArrayList();
public ArrayList <SortX> sx = new ArrayList();
private ArrayList <Rectangle> rect = new ArrayList();
public ArrayList <OrderFam> ord = new ArrayList();
public ArrayList <OrderFam> ordNew = new ArrayList();
public ArrayList <String> wrongFiles = new ArrayList();
ArrayList <String> phageList;
ArrayList <String> invertList;
public ArrayList <OrderFamOrder> ofo = new ArrayList();
public ArrayList <OrderGroupFam> ogf = new ArrayList();
public ArrayList <Integer> famCount = new ArrayList();

public ArrayList recList = new ArrayList();
private String famName,conName,genName,sr,input,ph,iv,col,e;
private Double conL, eVal;
private int fIndex,ep;
private Graphics g;
private double [][] dist;
private double [] famScore;
ArrayList <ClusterX> cx;
Clustering clust;
    
    public fileH(){
        phageList = new ArrayList(Arrays.asList("Myoviridae","Siphoviridae","Podoviridae","Lipothrixviridae","Rudiviridae","Ampullaviridae","Bicaudaviridae","Clavaviridae","Corticoviridae","Cystoviridae","Fuselloviridae","Globuloviridae","Guttaviridae","Inoviridae","Leviviridae","Microviridae","Plasmaviridae","Tectiviridae"));
        invertList = new ArrayList(Arrays.asList("Hantaviridae","Nairoviridae","Peribunyaviridae","Phenuiviridae","Alloherpesviridae","Herpesviridae","Bornaviridae","Filoviridae","Nyamiviridae","Paramyxoviridae","Pneumoviridae","Rhabdoviridae","Sunviridae","Arteriviridae","Coronaviridae","Picornaviridae","Adenoviridae","Anelloviridae","Arenaviridae","Asfarviridae","Astroviridae","Birnaviridae","Caliciviridae","Circoviridae","Flaviviridae","Genomoviridae","Hepadnaviridae","Hepeviridae","Iridoviridae","Nodaviridae","Orthomyxoviridae","Papillomaviridae","Parvoviridae","Picobirnaviridae","Polyomaviridae","Poxviridae","Reoviridae","Retroviridae","Togaviridae","Deltavirus","Tilapinevirus"));
        
    }
   //param is arraylist of the filepaths from the gui class
    
    
    public void readFile (ArrayList <String> fileList, String input,String ph,String iv){
        this.input=input;
        this.ph=ph;
        this.iv=iv;
        //loops through list of files
        for (int i=0;i<fileList.size();i++){
            ff.add(new fileFam());
            fIndex = ff.size()-1;
            ff.get(fIndex).setFileName(fileList.get(i));
        
            //tries to open
            try{
                FileReader read = new FileReader(fileList.get(i));
                Scanner scan = new Scanner(read);
                boolean correctFormat;
                String first = scan.nextLine();
                String [] colCheck = first.split("\t");
                
                
                /*
                check the file is in the right format
                if it is continue
                if not - if it is the only file being opened just display an error message - if multiple work except one then 
                */
                if (colCheck.length==16){
                    correctFormat=true;
                }
                else {
                    correctFormat=false;
                    wrongFiles.add(fileList.get(i));
                }
                //System.out.println("The file has " + colCheck.length + " columns");
                
                //skips if not in correct format
                //but need to change it so it also removes the filename from the list
                //create a list of files that dont meet the criteria use this list to check against the one in the GUI class
                
                //if it is in the correct format, then the file is read and the info is added to the filefam class
                if(correctFormat){
                     //add check for if contigs have to be over certain length
                        Double inp;
                        try{
                            inp=Double.valueOf(input);
                        }
                        catch(NumberFormatException ne){
                            inp=0.0;
                        }
                    //add the first line if in correct format    
                    famName = colCheck[14].trim();
                    conName =colCheck[0].trim();
                    conL=Double.parseDouble(colCheck[13].trim());
                    genName=colCheck[15].trim();
                    eVal=Double.parseDouble(colCheck[10].trim());
                    e = colCheck[10].substring(colCheck[10].lastIndexOf("e") + 1);
                    ep = Integer.parseInt(e.trim());  
                    boolean isPhage =false;
                    boolean isInv=false;
                    if (conL>inp){
                        ff.get(fIndex).addFam(famName);
                        ff.get(fIndex).famO.get(0).setEP(ep);
                        ff.get(fIndex).famO.get(0).setContigNames(conName);
                        ff.get(fIndex).famO.get(0).setL(conL);
                        ff.get(fIndex).famO.get(0).setGenNames(genName);
                        ff.get(fIndex).famO.get(0).setE(eVal);
                        ff.get(fIndex).famO.get(0).setPhage(false);
                        ff.get(fIndex).famO.get(0).setInv(false);
                        for (int phI=0;phI<phageList.size();phI++){
                            if(ff.get(fIndex).famO.get(0).getFamName().equalsIgnoreCase(phageList.get(phI))){
                                isPhage=true;
                                ff.get(fIndex).famO.get(0).setPhage(isPhage);
                                isPhage=false;
                            }
                        }
                        for (int inI=0;inI<invertList.size();inI++){
                            if(ff.get(fIndex).famO.get(0).getFamName().equalsIgnoreCase(invertList.get(inI))){
                                isInv=true;
                                ff.get(fIndex).famO.get(0).setInv(isInv);
                                isInv=false;
                            }
                        }
                    }
                    while (scan.hasNext()){
                        col = scan.nextLine();
                        String [] cols = col.split("\t");
                        famName = cols[14].trim();
                        conName =cols[0].trim();
                        conL=Double.parseDouble(cols[13].trim());
                        genName=cols[15].trim();
                        eVal=Double.parseDouble(cols[10].trim());
                        e = cols[10].substring(cols[10].lastIndexOf("e") + 1);
                        ep = Integer.parseInt(e.trim());


                        boolean present = false;
                        
                                //need to search the ff.findex.famo array for family name
                                //if it is there, the details should be added to the family
                                //after searching the full loop, if the family name is still not found, add a new fam to the famo array

                                //at this stage maybe add the summary stats?
                                //need to do before deletion of the 
                        if(conL>=inp){        
                                int k=0;
                                while(!present && (k<ff.get(fIndex).famO.size())){
                                    if (ff.get(fIndex).famO.get(k).getFamName().equalsIgnoreCase(famName)){
                                        isPhage =false;
                                        isInv=false;
                                        ff.get(fIndex).famO.get(k).setPhage(isPhage);
                                        ff.get(fIndex).famO.get(k).setContigNames(conName);
                                        ff.get(fIndex).famO.get(k).setE(eVal);
                                        ff.get(fIndex).famO.get(k).setL(conL);
                                        ff.get(fIndex).famO.get(k).setGenNames(genName);
                                        ff.get(fIndex).famO.get(k).setEP(ep);
                                        for (int phI=0;phI<phageList.size();phI++){
                                            if(ff.get(fIndex).famO.get(k).getFamName().equalsIgnoreCase(phageList.get(phI))){
                                                isPhage=true;
                                                ff.get(fIndex).famO.get(k).setPhage(isPhage);
                                                isPhage=false;
                                            }
                                        }
                                        for (int inI=0;inI<invertList.size();inI++){
                                            if(ff.get(fIndex).famO.get(k).getFamName().equalsIgnoreCase(invertList.get(inI))){
                                                isInv=true;
                                                ff.get(fIndex).famO.get(k).setInv(isInv);
                                                isInv=false;
                                            }
                                        }
                                        present = true;

                                    }
                                    else{
                                        k++;
                                    }
                                }
                                //if family is not already present, then adds that
                                if (!present){
                                     ff.get(fIndex).addFam(famName);
                                        //index where fam exists and add to array list of that index
                                        for (int j=0; j<ff.get(fIndex).famO.size();j++){
                                            if (ff.get(fIndex).famO.get(j).getFamName().equalsIgnoreCase(famName)){
                                                isPhage = false;
                                                isInv=false;
                                                ff.get(fIndex).famO.get(k).setPhage(isPhage);
                                                ff.get(fIndex).famO.get(j).setContigNames(conName);
                                                ff.get(fIndex).famO.get(j).setE(eVal);
                                                ff.get(fIndex).famO.get(j).setL(conL);
                                                ff.get(fIndex).famO.get(j).setGenNames(genName);
                                                ff.get(fIndex).famO.get(j).setEP(ep);
                                                for (int phI=0;phI<phageList.size();phI++){
                                                if(ff.get(fIndex).famO.get(k).getFamName().equalsIgnoreCase(phageList.get(phI))){
                                                    isPhage=true;
                                                    ff.get(fIndex).famO.get(k).setPhage(isPhage);
                                                    isPhage=false;
                                            }
                                        }
                                        for (int inI=0;inI<invertList.size();inI++){
                                            if(ff.get(fIndex).famO.get(k).getFamName().equalsIgnoreCase(invertList.get(inI))){
                                                isInv=true;
                                                ff.get(fIndex).famO.get(k).setInv(isInv);
                                                isInv=false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
                    
            catch(FileNotFoundException ex){
                System.out.println("File Not Found");
            }
                
        }
        
        //removes phages
        if(ph.equals("y")){
            for(int pi=0;pi<ff.size();pi++){
                for (int fi=ff.get(pi).famO.size()-1; fi>=0; fi--){
                    if(ff.get(pi).famO.get(fi).getPhage().equals(true)){
                        ff.get(pi).famO.remove(fi);
                    }
                    
                }
            }
        }
        
        //remove anything thats not a vertbrate infecting
        if(iv.equals("y")){
            for(int pi=0;pi<ff.size();pi++){
                for (int fi=ff.get(pi).famO.size()-1;fi>=0;fi--){
                        if(ff.get(pi).famO.get(fi).getInv().equals(false)){
                            ff.get(pi).famO.remove(fi);
                        }
                    
                }
            }
        }
        
        for (int fi=ff.size()-1;fi>=0;fi--){
             if(ff.get(fi).famO.size()<1){                
                ff.remove(fi);
            }
        }
        removeFiles();
        distanceMatrix();
        setOrd();
    }
    
    
    //method to remove the incorrect format files before any handling of data in gui class
    public void removeFiles(){
        //if names match, remove from filefam
        for (int i=0;i<ff.size();i++){
            for (int j=0;j<wrongFiles.size();j++){
                if(ff.get(i).getFileName().equalsIgnoreCase(wrongFiles.get(j))){
                    ff.remove(i);
                }
            }
        }
    }
    
    
    
   //sets the y positions for the families - will be used to create rectangles
    public void setYPos(String srt){
        this.sr=srt;
        //look through each file to see if family is alread present in orderfam object array
        //orderfam is the array used when ordering the families - contains all the family names from all the files
        boolean present = false;
        int i=0;
        int j;
        while(i<ff.size()){
            for(j=0;j<ff.get(i).famO.size();j++){
                for (int k=0;k<ord.size();k++){
                    if (ord.get(k).getFam().equalsIgnoreCase(ff.get(i).famO.get(j).getFamName())){
                        present = true;
                    }
                }
                if(!present){
                    ord.add(new OrderFam());
                    ord.get(ord.size()-1).setFam(ff.get(i).famO.get(j).getFamName());
                    ord.get(ord.size()-1).setOrder(ff.get(i).famO.get(j).getOrd());
                    ord.get(ord.size()-1).setGroup(ff.get(i).famO.get(j).getGroup());
                }
                present=false;
            }
            i++;
        }
        
        

        
        if (sr.equals("a")){
            //sort families alphabetically by using orderFam compare to method
            Collections.sort(ord);
        }
        if (sr.equals("g")){
            sortByGroup();
        }
        if(sr.equals("f")){
            
        }
        else if (sr.equals("o")){
            sortByOrder();
        }
        
        //assign the y positions for the order objects array
        
        BufferedImage bf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        g=bf.createGraphics();
        FontMetrics fmet = g.getFontMetrics(new Font("sansserif", Font.PLAIN, 12));
        ArrayList <Integer> samples = new ArrayList();
        for(int ni=0;ni<ff.size();ni++){
                int px = fmet.stringWidth(ff.get(ni).getFileName());
                samples.add(px);           
        }
        int mayp=0;
        for(int pidy=0;pidy<samples.size();pidy++){
            if(samples.get(pidy)>=mayp){
               mayp=samples.get(pidy);
            }
        }
        
        int ypos =mayp+5;
        
        for (int k=0;k<ord.size();k++){
            
            ord.get(k).setY(ypos);
            //System.out.println(ord.get(k).getFam() + " " + ord.get(k).getY());
            ypos+=16;
        }
        
        //match the y postions to the ord objects with the matching name
        
        for (int a=0;a<ff.size();a++){
            for(int b=0;b<ff.get(a).famO.size();b++){
                for (int c=0;c<ord.size();c++){
                    if(ord.get(c).getFam().equalsIgnoreCase(ff.get(a).famO.get(b).getFamName())){
                        ff.get(a).famO.get(b).setY(ord.get(c).getY());
                    }
                }
            }
        }



    //go through the 
    
    for (int a=0;a<ff.size();a++){
        for(int b=0;b<cx.size();b++){
            String fn = ff.get(a).getFileName().substring(ff.get(a).getFileName().lastIndexOf("\\") + 1,ff.get(a).getFileName().lastIndexOf("."));
            if (fn.equalsIgnoreCase(cx.get(b).getClust())){
                for (int c=0;c<ff.get(a).famO.size();c++){
                    ff.get(a).famO.get(c).setX(cx.get(b).getX());
                }
            }
        }
    }
        
}
    
    public void getSummaryInfo(){
      //just add info to filefam class instead and use that
        
    }
    
    public void distanceMatrix(){
        //2d array creation for distance matrix
        dist = new double[ff.size()][ff.size()];
        
        ArrayList <String> names = new ArrayList();
        int j=1;
        for (int i=0;i<ff.size();i++){
            names.add(ff.get(i).getFileName().substring(ff.get(i).getFileName().lastIndexOf("\\") + 1,ff.get(i).getFileName().lastIndexOf(".")));
        }
        
        ArrayList <Double> famScore = new ArrayList();
        for (int i=0;i<ff.size();i++){
            famScore.add((double)ff.get(i).famO.size());
    }
        
        clust = new Clustering(names,famScore);
        
        //create a list with x values to compare when adding x values to the families
        cx = new ArrayList();
        for (int i=0;i<clust.samNames.size();i++){
            cx.add(new ClusterX());
            cx.get(cx.size()-1).setClust(clust.samNames.get(i));
        }
        
        
        //get the length in pixels of the family names so can start drawing heatamp at the right x position and names arent being cut off
        BufferedImage bf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        g=bf.createGraphics();
        FontMetrics fm = g.getFontMetrics(new Font("sansserif", Font.PLAIN, 12));
        ArrayList <Integer> fnames = new ArrayList();
        for(int ni=0;ni<ff.size();ni++){
            for(int nj=0;nj<ff.get(ni).famO.size();nj++){
                int px = fm.stringWidth(ff.get(ni).famO.get(nj).getFamName());
                fnames.add(px);
            }
        }
        int maxp=0;
        for(int pidx=0;pidx<names.size();pidx++){
            if(fnames.get(pidx)>=maxp){
               maxp=fnames.get(pidx);
            }
        }
        int x=maxp+400;
        
        for (int i=0;i<cx.size();i++){
            cx.get(i).setX(x);
            x+=31;
        }
        
        
    }

    public void readLengths(){
        String sheet = "AvGenLens.txt";
        try{
            InputStream in = mapGUI.class.getResourceAsStream(sheet);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String ln;
            while((ln = bf.readLine())!= null){
                String [] splt = ln.split("\t");
                String fn = splt[0].trim();
                //System.out.println(fn);
                for (int i=0;i<ff.size();i++){
                    for (int j=0;j<ff.get(i).famO.size();j++){
                        if(ff.get(i).famO.get(j).getFamName().equalsIgnoreCase(fn)){
                            ff.get(i).famO.get(j).setAvgGen(Double.valueOf(splt[2].trim()));
                            ff.get(i).famO.get(j).setAvgSeg(Double.valueOf(splt[3].trim()));
                            //System.out.println(ff.get(i).famO.get(j).getFamName() + " " + ff.get(i).famO.get(j).getAvgGen() + " " + ff.get(i).famO.get(j).getAvgSeg());
                        }
                    }
                }
            }
        }  
        catch (IOException e){
            System.out.println("File not found");
        }
    }
    
    //read in the file for the orders - add value to family
    public void setOrd(){
        
        for (int i=0;i<ff.size();i++){
            for (int j=0;j<ff.get(i).famO.size();j++){
                ff.get(i).famO.get(j).setOrd("Unassigned");
                ff.get(i).famO.get(j).setGroup("Unassigned");
            }
        }    
        String sheet = "orders.txt";
        try{
            InputStream in = mapGUI.class.getResourceAsStream(sheet);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String ln;
            while((ln = bf.readLine())!= null){
                String [] splt = ln.split("\t");
                String fn = splt[0].trim();
                String o = splt[1].trim();
                String gr = splt[2].trim();
                //.out.println("Group found is " + gr + " and family found is " + splt[0] + " compared to family ") ;
                for (int i=0;i<ff.size();i++){
                    for (int j=0;j<ff.get(i).famO.size();j++){
                        if(ff.get(i).famO.get(j).getFamName().equalsIgnoreCase(fn)){
                            ff.get(i).famO.get(j).setOrd(o);
                            ff.get(i).famO.get(j).setGroup(gr);
                            
                        }
                    }
                }
            }
        }
        catch (IOException e){
            
        }

//        try{
//            FileReader rd = new FileReader(sheet);
//            Scanner scn = new Scanner(rd);
//            while(scn.hasNext()){
//                String ln = scn.nextLine();
//                String [] splt = ln.split("\t");
//                String fn = splt[0].trim();
//                String o = splt[1].trim();
//                String gr = splt[2].trim();
//                //.out.println("Group found is " + gr + " and family found is " + splt[0] + " compared to family ") ;
//                for (int i=0;i<ff.size();i++){
//                    for (int j=0;j<ff.get(i).famO.size();j++){
//                        if(ff.get(i).famO.get(j).getFamName().equalsIgnoreCase(fn)){
//                            ff.get(i).famO.get(j).setOrd(o);
//                            ff.get(i).famO.get(j).setGroup(gr);
//                            
//                        }
//                    }
//                }
//            }
//        }  
//        catch (FileNotFoundException e){
//            System.out.println("File not found");
//        }
    }
    
    //method to organise the families in alph within orders
    //still to be sorted
    public void sortByOrder(){
        Collections.sort(ord);  
        boolean present = false;
        int j;
        
        
        //add the first one
        ofo.add(new OrderFamOrder());
        ofo.get(0).setOrder(ord.get(0).getOrder());
        //ofo.get(0).setFamNames(ord.get(0).getFam());
        //System.out.println("order is " + ord.size() + " and ofo is " + ofo.size() + " the first ofo is " + ofo.get(0).getOrder());
        
        
        for (int i=0;i<ord.size();i++){
            for(j=0;j<ofo.size();j++){
                if(ord.get(i).getOrder().equalsIgnoreCase(ofo.get(j).getOrder())){
                    ofo.get(j).setFamNames(ord.get(i).getFam());
                    present = true;
                    
                }
            }
                if(!present){
                    ofo.add(new OrderFamOrder());
                    ofo.get(ofo.size()-1).setOrder(ord.get(i).getOrder());
                    ofo.get(ofo.size()-1).setFamNames(ord.get(i).getFam());
                    
                }
                present=false;
            }
        Collections.sort(ofo);
        ord.clear();
        
        for (int i=0;i<ofo.size();i++){
            for (int k=0;k<ofo.get(i).fams.size();k++){
                ord.add(new OrderFam());
                ord.get(ord.size()-1).setOrder(ofo.get(i).getOrder());
                ord.get(ord.size()-1).setFam(ofo.get(i).fams.get(k));
            }
        }
        int start=0;
        int end;
        int draw;
        
        for (int i=0;i<ofo.size();i++){
            famCount.add(ofo.get(i).fams.size());
            sx.add(new SortX());
            sx.get(sx.size()-1).setFam(ofo.get(i).getOrder());
            sx.get(sx.size()-1).setStart(start);
            end = (ofo.get(i).fams.size()*16) + start;
            sx.get(sx.size()-1).setEnd(end);
            draw = start + (end-start)/2;
            sx.get(sx.size()-1).setDraw(draw);
        }
        
        
      


        
        
    }
    
    public void sortByGroup(){
        Collections.sort(ord);  
        boolean present = false;
        int j;
        
        
        //add the first one
        ogf.add(new OrderGroupFam());
        ogf.get(0).setGroup(ord.get(0).getGroup());
        //ofo.get(0).setFamNames(ord.get(0).getFam());
        //System.out.println("order is " + ord.size() + " and ofo is " + ofo.size() + " the first ofo is " + ofo.get(0).getOrder());
        
        
        for (int i=0;i<ord.size();i++){
            for(j=0;j<ogf.size();j++){
                if(ord.get(i).getGroup().equalsIgnoreCase(ogf.get(j).getGroup())){
                    ogf.get(j).setFamNames(ord.get(i).getFam());
                    present = true;
                    
                }
            }
                if(!present){
                    ogf.add(new OrderGroupFam());
                    ogf.get(ogf.size()-1).setGroup(ord.get(i).getGroup());
                    ogf.get(ogf.size()-1).setFamNames(ord.get(i).getFam());
                    
                }
                present=false;
            }
        Collections.sort(ogf);
        ord.clear();
        
        for (int i=0;i<ogf.size();i++){
            for (int k=0;k<ogf.get(i).fams.size();k++){
                ord.add(new OrderFam());
                ord.get(ord.size()-1).setGroup(ogf.get(i).getGroup());
                ord.get(ord.size()-1).setFam(ogf.get(i).fams.get(k));
            }
        }
        int start=0;
        int end;
        int draw;
        
        for (int i=0;i<ogf.size();i++){
            famCount.add(ogf.get(i).fams.size());
            sx.add(new SortX());
            sx.get(sx.size()-1).setFam(ogf.get(i).getGroup());
            System.out.println(sx.get(sx.size()-1).getFam());
            sx.get(sx.size()-1).setStart(start);
            end = (ogf.get(i).fams.size()*16) + start;
            sx.get(sx.size()-1).setEnd(end);
            draw = start + (end-start)/2;
            sx.get(sx.size()-1).setDraw(draw);
        }
        
    }
    

}
    

    
    
    
    

    

