/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Eilidh Greig
 */
public class FileHGen {
    
public ArrayList <SortX> sx = new ArrayList();
public ArrayList <FileGen> fg = new ArrayList();
private ArrayList <Rectangle> rect = new ArrayList();
public ArrayList <OrderGen> ord = new ArrayList();
public ArrayList <OrderFamOrder> ofo = new ArrayList();
public ArrayList <String> wrongFiles = new ArrayList();
public ArrayList <OrderGroupFam> og = new ArrayList();
public ArrayList recList = new ArrayList();
ArrayList <String> phageList,invertList;
private String famName,conName,genName,sr,input,col,e,iv,ph;
private Double conL, eVal;
private int fIndex,ep;
private Graphics g;
public ArrayList <Integer> genCount = new ArrayList();
public ArrayList <OrderGenFam> ogf = new ArrayList();
private double [][] dist;
private double [] famScore;
ArrayList <ClusterX> cx = new ArrayList();
Clustering clust;
    public FileHGen(){
        
        phageList = new ArrayList(Arrays.asList("Myoviridae","Siphoviridae","Podoviridae","Lipothrixviridae","Rudiviridae","Ampullaviridae","Bicaudaviridae","Clavaviridae","Corticoviridae","Cystoviridae","Fuselloviridae","Globuloviridae","Guttaviridae","Inoviridae","Leviviridae","Microviridae","Plasmaviridae","Tectiviridae"));
        invertList = new ArrayList(Arrays.asList("Hantaviridae","Nairoviridae","Peribunyaviridae","Phenuiviridae","Alloherpesviridae","Herpesviridae","Bornaviridae","Filoviridae","Nyamiviridae","Paramyxoviridae","Pneumoviridae","Rhabdoviridae","Sunviridae","Arteriviridae","Coronaviridae","Picornaviridae","Adenoviridae","Anelloviridae","Arenaviridae","Asfarviridae","Astroviridae","Birnaviridae","Caliciviridae","Circoviridae","Flaviviridae","Genomoviridae","Hepadnaviridae","Hepeviridae","Iridoviridae","Nodaviridae","Orthomyxoviridae","Papillomaviridae","Parvoviridae","Picobirnaviridae","Polyomaviridae","Poxviridae","Reoviridae","Retroviridae","Togaviridae","Deltavirus","Tilapinevirus"));
        
      
    }
    
    public void readFile(ArrayList <String> fileList,String ip,String ph, String iv){
        this.input=ip;
        this.ph=ph;
        this.iv=iv;
        //loops through list of files
        for (int i=0;i<fileList.size();i++){
            fg.add(new FileGen());
            fIndex = fg.size()-1;
            fg.get(fIndex).setFileName(fileList.get(i));
        
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
                    Double inp;
                    try{
                        inp=Double.valueOf(input);
                    }
                    catch(NumberFormatException ne){
                        inp=0.0;
                    }
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
                        fg.get(fIndex).addGen(genName);
                        fg.get(fIndex).genO.get(0).setEP(ep);
                        fg.get(fIndex).genO.get(0).setContigNames(conName);
                        fg.get(fIndex).genO.get(0).setL(conL);
                        fg.get(fIndex).genO.get(0).setFamName(famName);
                        fg.get(fIndex).genO.get(0).setE(eVal);
                        fg.get(fIndex).genO.get(0).setPhage(false);
                        fg.get(fIndex).genO.get(0).setInv(false);
                        for (int phI=0;phI<phageList.size();phI++){
                            if(fg.get(fIndex).genO.get(0).getFamName().equalsIgnoreCase(phageList.get(phI))){
                                isPhage=true;
                                fg.get(fIndex).genO.get(0).setPhage(isPhage);
                                isPhage=false;
                            }
                        }
                        for (int inI=0;inI<invertList.size();inI++){
                            if(fg.get(fIndex).genO.get(0).getFamName().equalsIgnoreCase(invertList.get(inI))){
                                isInv=true;
                                fg.get(fIndex).genO.get(0).setInv(isInv);
                                isInv=false;
                            }
                        }
                    
                    }
                    while (scan.hasNextLine()){
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
                    
                            
                    if(conL>=inp){
                            //need to search the fg.findex.geno array for genus name
                            //if it is there, the details should be added to the genus
                            //after searching the full loop, if the genus name is still not found, add a new fam to the famo array
                            int k=0;
                            while(!present && (k<fg.get(fIndex).genO.size())){
                                
                                if (fg.get(fIndex).genO.get(k).getGenName().equalsIgnoreCase(genName)){
                                    isPhage = false;
                                    isInv = false;
                                    fg.get(fIndex).genO.get(k).setContigNames(conName);
                                    fg.get(fIndex).genO.get(k).setE(eVal);
                                    fg.get(fIndex).genO.get(k).setL(conL);
                                    fg.get(fIndex).genO.get(k).setFamName(famName);
                                    fg.get(fIndex).genO.get(k).setEP(ep);
                                    for (int phI=0;phI<phageList.size();phI++){
                                        if(fg.get(fIndex).genO.get(k).getFamName().equalsIgnoreCase(phageList.get(phI))){
                                            isPhage=true;
                                            fg.get(fIndex).genO.get(k).setPhage(isPhage);
                                            isPhage=false;
                                        }
                                    }
                                    for (int inI=0;inI<invertList.size();inI++){
                                        if(fg.get(fIndex).genO.get(k).getFamName().equalsIgnoreCase(invertList.get(inI))){
                                            isInv=true;
                                            fg.get(fIndex).genO.get(k).setInv(isInv);
                                            isInv=false;
                                        }
                                    }
                                    present = true;

                                }
                                else{
                                    k++;
                                }
                            }
                            //if genus is not already present, then adds that
                            if (!present){
                                 fg.get(fIndex).addGen(genName);
                                    //index where fam exists and add to array list of that index
                                    for (int j=0; j<fg.get(fIndex).genO.size();j++){
                                        if (fg.get(fIndex).genO.get(j).getGenName().equalsIgnoreCase(genName)){
                                            isPhage = false;
                                            isInv = false;
                                            fg.get(fIndex).genO.get(j).setContigNames(conName);
                                            fg.get(fIndex).genO.get(j).setE(eVal);
                                            fg.get(fIndex).genO.get(j).setL(conL);
                                            fg.get(fIndex).genO.get(j).setFamName(famName);
                                            fg.get(fIndex).genO.get(j).setEP(ep);
                                            for (int phI=0;phI<phageList.size();phI++){
                                        if(fg.get(fIndex).genO.get(k).getFamName().equalsIgnoreCase(phageList.get(phI))){
                                            isPhage=true;
                                            fg.get(fIndex).genO.get(k).setPhage(isPhage);
                                            isPhage=false;
                                        }
                                    }
                                    for (int inI=0;inI<invertList.size();inI++){
                                        if(fg.get(fIndex).genO.get(k).getFamName().equalsIgnoreCase(invertList.get(inI))){
                                            isInv=true;
                                            fg.get(fIndex).genO.get(k).setInv(isInv);
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
        
    if(ph.equals("y")){
        for(int pi=0;pi<fg.size();pi++){
            for (int fi=fg.get(pi).genO.size()-1;fi>=0;fi--){
                if(fg.get(pi).genO.get(fi).getPhage().equals(true)){
                    fg.get(pi).genO.remove(fi);
                }

            }
        }
    }
        
        if(iv.equals("y")){
            for(int pi=0;pi<fg.size();pi++){
                for (int fi=fg.get(pi).genO.size()-1;fi>=0;fi--){
                    if(fg.get(pi).genO.get(fi).getInv().equals(false)){
                        fg.get(pi).genO.remove(fi);
                    }
                    
                }
            }
        }
        
        for (int fi=fg.size()-1;fi>=0;fi--){
             if(fg.get(fi).genO.size()<1){                
                fg.remove(fi);
            }
        }
         
         removeFiles();
         setOrd();
         distanceMatrix();
        
    }
    
        //method to remove the incorrect format files before any handling of data in gui class
    public void removeFiles(){
        //if names match, remove from filefam
        for (int i=0;i<fg.size();i++){
            for (int j=0;j<wrongFiles.size();j++){
                if(fg.get(i).getFileName().equalsIgnoreCase(wrongFiles.get(j))){
                    fg.remove(i);
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
        while(i<fg.size()){
            for(j=0;j<fg.get(i).genO.size();j++){
                for (int k=0;k<ord.size();k++){
                    if (ord.get(k).getGen().equalsIgnoreCase(fg.get(i).genO.get(j).getGenName())){
                        present = true;
                    }
                }
                if(!present){
                    ord.add(new OrderGen());
                    ord.get(ord.size()-1).setGen(fg.get(i).genO.get(j).getGenName());
                    
                    ord.get(ord.size()-1).setFam(fg.get(i).genO.get(j).getFamName());
                    ord.get(ord.size()-1).setOrder(fg.get(i).genO.get(j).getOrd());
                    ord.get(ord.size()-1).setGroup(fg.get(i).genO.get(j).getGroup());
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
        if (sr.equals("o")){
            sortByOrder();
        }
        else if (sr.equals("f")){
            sortByFam();
        }
        
        if (sr.equals("a")){
            //sort families alphabetically by using orderFam compare to method
            Collections.sort(ord);
        }
        

       
        
        //assign the y positions for the order objects array
        BufferedImage bf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        g=bf.createGraphics();
        FontMetrics fmet = g.getFontMetrics(new Font("sansserif", Font.PLAIN, 12));
        ArrayList <Integer> samples = new ArrayList();
        for(int ni=0;ni<fg.size();ni++){
            int px = fmet.stringWidth(fg.get(ni).getFileName());
            samples.add(px);
        }
        int mayp=0;
        for(int pidy=0;pidy<samples.size();pidy++){
            if(samples.get(pidy)>mayp){
               mayp=samples.get(pidy);
            }
        }
        
        int ypos =mayp+5;
        
        for (int k=0;k<ord.size();k++){
            
            ord.get(k).setY(ypos);
            
            ypos+=16;
        }
        
        //match the y postions to the ord objects with the matching name
        for (int a=0;a<fg.size();a++){
            for(int b=0;b<fg.get(a).genO.size();b++){
                for (int c=0;c<ord.size();c++){

                     if(ord.get(c).getGen().equalsIgnoreCase(fg.get(a).genO.get(b).getGenName())){
                        fg.get(a).genO.get(b).setY(ord.get(c).getY());
                    }
                    
                }
            }
        }
        
         for (int a=0;a<fg.size();a++){
        for(int b=0;b<cx.size();b++){
            String fn = fg.get(a).getFileName().substring(fg.get(a).getFileName().lastIndexOf("\\") + 1,fg.get(a).getFileName().lastIndexOf("."));
            if (fn.equalsIgnoreCase(cx.get(b).getClust())){
                for (int c=0;c<fg.get(a).genO.size();c++){
                    fg.get(a).genO.get(c).setX(cx.get(b).getX());
                }
            }
        }
    }
        
  
}
       
    public void sortByGroup(){
        Collections.sort(ord);  
        boolean present = false;
        int j;
        
        
        //add the first one
        og.add(new OrderGroupFam());
        og.get(0).setGroup(ord.get(0).getGroup());
        //og.get(0).setFamNames(ord.get(0).getFam());
        //System.out.println("order is " + ord.size() + " and ofo is " + ofo.size() + " the first ofo is " + ofo.get(0).getOrder());
        
        System.out.println("ord is " + ord.size() + " and og is " + og.size());
        for (int i=0;i<ord.size();i++){
            for(j=0;j<og.size();j++){
                //System.out.println("comparing " + ord.get(i).getGroup() + " at index " + i + " to " + og.get(j).getGroup() + " at index " + j);
                if(ord.get(i).getGroup().equalsIgnoreCase(og.get(j).getGroup())){
                    og.get(j).setFamNames(ord.get(i).getGen());
                    present = true;
                    
                }
            }
                if(!present){
                    og.add(new OrderGroupFam());
                    og.get(og.size()-1).setGroup(ord.get(i).getGroup());
                    og.get(og.size()-1).setFamNames(ord.get(i).getGen());
                    
                }
                present=false;
            }
        Collections.sort(og);
        ord.clear();
        
        for (int i=0;i<og.size();i++){
            for (int k=0;k<og.get(i).fams.size();k++){
                ord.add(new OrderGen());
                ord.get(ord.size()-1).setGroup(og.get(i).getGroup());
                ord.get(ord.size()-1).setGen(og.get(i).fams.get(k));
            }
        }
        int start=0;
        int end;
        int draw;
        
        for (int i=0;i<og.size();i++){
            genCount.add(og.get(i).fams.size());
            sx.add(new SortX());
            sx.get(sx.size()-1).setFam(og.get(i).getGroup());
            //System.out.println(sx.get(sx.size()-1).getFam());
            sx.get(sx.size()-1).setStart(start);
            end = (og.get(i).fams.size()*16) + start;
            sx.get(sx.size()-1).setEnd(end);
            draw = start + (end-start)/2;
            sx.get(sx.size()-1).setDraw(draw);
        }
        
    }
    
    public void setOrd(){
        
        for (int i=0;i<fg.size();i++){
            for (int j=0;j<fg.get(i).genO.size();j++){
                fg.get(i).genO.get(j).setOrd("Unassigned");
                fg.get(i).genO.get(j).setGroup("Unassigned");
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

                for (int i=0;i<fg.size();i++){
                    for (int j=0;j<fg.get(i).genO.size();j++){
                        if(fg.get(i).genO.get(j).getFamName().equalsIgnoreCase(fn)){
                            fg.get(i).genO.get(j).setOrd(o);
                            fg.get(i).genO.get(j).setGroup(gr);
                            //System.out.println("added " + fg.get(i).genO.get(j).getGenName() + o + gr);
                            
                        }
                    }
                }
            }
        }  
        catch (IOException e){
            System.out.println("File not found");
        }
    }
    
    public void sortByOrder(){
        Collections.sort(ord);  
        boolean present = false;
        int j;
        
        
        //add the first one
        ofo.add(new OrderFamOrder());
        ofo.get(0).setOrder(ord.get(0).getOrder());

        
        
        for (int i=0;i<ord.size();i++){
            for(j=0;j<ofo.size();j++){
                if(ord.get(i).getOrder().equalsIgnoreCase(ofo.get(j).getOrder())){
                    ofo.get(j).setFamNames(ord.get(i).getGen());
                    present = true;
                    
                }
            }
                if(!present){
                    ofo.add(new OrderFamOrder());
                    ofo.get(ofo.size()-1).setOrder(ord.get(i).getOrder());
                    ofo.get(ofo.size()-1).setFamNames(ord.get(i).getGen());
                    
                }
                present=false;
            }
        Collections.sort(ofo);
        ord.clear();
        
        for (int i=0;i<ofo.size();i++){
            for (int k=0;k<ofo.get(i).fams.size();k++){
                ord.add(new OrderGen());
                ord.get(ord.size()-1).setOrder(ofo.get(i).getOrder());
                ord.get(ord.size()-1).setGen(ofo.get(i).fams.get(k));
            }
        }
        int start=0;
        int end;
        int draw;
        
        for (int i=0;i<ofo.size();i++){
            genCount.add(ofo.get(i).fams.size());
            sx.add(new SortX());
            sx.get(sx.size()-1).setFam(ofo.get(i).getOrder());
            sx.get(sx.size()-1).setStart(start);
            end = (ofo.get(i).fams.size()*16) + start;
            sx.get(sx.size()-1).setEnd(end);
            draw = start + (end-start)/2;
            sx.get(sx.size()-1).setDraw(draw);
        }
        
        
      


    }
    //if user clicks to sort by viral family
    //put in alphabetical order first
    //create hashmap to group genus by family - iterate through ordergen list and add
    //maybe keep track of number for each family and use number for spacing later along with list of families present
    
    public void sortByFam(){
        Collections.sort(ord);

        
        boolean present = false;
        int j;
        for (int i=0;i<ord.size();i++){
            for(j=0;j<ogf.size();j++){
                if(ord.get(i).getFam().equals(ogf.get(j).getFam())){
                    ogf.get(j).setGenNames(ord.get(i).getGen());
                    present = true;
                }
            }
                if(!present){
                    ogf.add(new OrderGenFam());
                    ogf.get(ogf.size()-1).setFam(ord.get(i).getFam());
                    ogf.get(ogf.size()-1).setGenNames(ord.get(i).getGen());
                }
                present=false;
            }
        Collections.sort(ogf);
        ord.clear();
        
        for (int i=0;i<ogf.size();i++){
            for (int k=0;k<ogf.get(i).gens.size();k++){
                ord.add(new OrderGen());
                ord.get(ord.size()-1).setFam(ogf.get(i).getFam());
                ord.get(ord.size()-1).setGen(ogf.get(i).gens.get(k));
            }
        }
        int start=0;
        int end;
        int draw;
        
        for (int i=0;i<ogf.size();i++){
            genCount.add(ogf.get(i).gens.size());
            sx.add(new SortX());
            sx.get(sx.size()-1).setFam(ogf.get(i).getFam());
            sx.get(sx.size()-1).setStart(start);
            end = (ogf.get(i).gens.size()*16) + start;
            sx.get(sx.size()-1).setEnd(end);
            draw = start + (end-start)/2;
            sx.get(sx.size()-1).setDraw(draw);
        }
        
        
      


        
        
    }
    
    public void distanceMatrix(){
        //2d array creation for distance matrix
        dist = new double[fg.size()][fg.size()];
        
        ArrayList <String> names = new ArrayList();
        int j=1;
        for (int i=0;i<fg.size();i++){
            names.add(fg.get(i).getFileName().substring(fg.get(i).getFileName().lastIndexOf("\\") + 1,fg.get(i).getFileName().lastIndexOf(".")));
        }
        
        ArrayList <Double> famScore = new ArrayList();
        for (int i=0;i<fg.size();i++){
            famScore.add((double)fg.get(i).genO.size());
    }
        
        clust = new Clustering(names,famScore);
        
        //create a list with x values to compare when adding x values to the families
        //cx = new ArrayList();
        for (int i=0;i<clust.samNames.size();i++){
            cx.add(new ClusterX());
            cx.get(cx.size()-1).setClust(clust.samNames.get(i));
        }
        
        
        //get the length in pixels of the family names so can start drawing heatamp at the right x position and names arent being cut off
        BufferedImage bf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        g=bf.createGraphics();
        FontMetrics fm = g.getFontMetrics(new Font("sansserif", Font.PLAIN, 12));
        ArrayList <Integer> fnames = new ArrayList();
        for(int ni=0;ni<fg.size();ni++){
            for(int nj=0;nj<fg.get(ni).genO.size();nj++){
                int px = fm.stringWidth(fg.get(ni).genO.get(nj).getFamName());
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
                for (int i=0;i<fg.size();i++){
                    for (int j=0;j<fg.get(i).genO.size();j++){
                        if(fg.get(i).genO.get(j).getFamName().equalsIgnoreCase(fn)){
                            fg.get(i).genO.get(j).setAvgGen(Double.valueOf(splt[2].trim()));
                            fg.get(i).genO.get(j).setAvgSeg(Double.valueOf(splt[3].trim()));
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
    
    
}
