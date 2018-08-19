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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.geom.AffineTransform;

/*uses Java graphics to draw the heatmap
takes rectangle array with x and ypositions, family/genus names, sample names,
ordering criteria and line positions, shannon bar, and colours for rectangles
*/


public class heatCanvas extends JPanel {
    
private final int horz = 30;
private final int vert = 15;
private ArrayList <Rectangle> r;
private ArrayList <String> vName,fName,fp;
private ArrayList <Integer> ges;
private ArrayList <Color> cd;
private Image im;
private int maxScale,minScale;
private Color topGrad,botGrad,bk;
private Double max, min;
private int maxY;

       public heatCanvas(ArrayList <Rectangle> rec, ArrayList <String> n, ArrayList <String> fn,  Color tg, Color bg,Double max,Double min,ArrayList<Integer>ges,ArrayList<String>fp,ArrayList<Color>cd,Color bk){
        this.r=rec;
        this.vName=n;
        this.fName=fn;
        this.topGrad=tg;
        this.botGrad=bg;
        this.max=max;
        this.min=min;
        this.ges=ges;
        this.fp=fp;
        this.cd=cd;
        this.bk=bk;
    }
 
@Override
    public void paintComponent(Graphics g){
       super.paintComponent(g);
       setBackground(Color.WHITE);
       setOpaque(true);
       im = buffImage();
       g.drawImage(im,0,0,this);  
   }
    
@Override
    public Dimension getPreferredSize(){
        return new Dimension(buffImage().getWidth(this), buffImage().getHeight(this));
    }
   
   //pass the recrangle arraylist
   //create list in actionperformed gui
   //pass in the list as parameter - draw as loop
   //then create new heatCanvas in GUI class when after file handling
   //repaint
   private Image buffImage(){
       
        
//        
//        want to create a background rectangle with a border to make map look nicer
//        need to find start and final rectangle positions to do this
        maxY=0;
        
        int startX=0;
        int h=0;
        int w=0;
        
        //get the max y position for background boundary
        for (int i=0;i<r.size();i++){
            if (r.get(i).getYP() > maxY){
                maxY=r.get(i).getYP();
            }
        }
        maxY+=15;
        
         //get the start for the beginning of the b/g
        int startY=maxY;
        for (int i=0;i<r.size();i++){
            if(startY > r.get(i).getYP()){
               startY = r.get(i).getYP();  
            }
        }
        
        
        //get the height of the b/g
        h = maxY - startY; 
        
        //maximum x position for the inner b/g
        int maxX=0;
            for (int i=0;i<r.size();i++){
            if (r.get(i).getXP() > maxX){
                maxX=r.get(i).getXP();
            }
        }
        maxX+=30;
        
        //buffered image takes the max dimensions into consideration and adds extra space for figs and scale
        
        BufferedImage buff = new BufferedImage(maxX+300,maxY+300,BufferedImage.TYPE_INT_ARGB);
        
        //starting x position
        startX = r.get(0).getXP();
        
        //width
        w = maxX-startX;
        
       //create the buffered image
        Graphics2D g=buff.createGraphics();
        int yc=startY+15;
        
        //b/g of heatmap - white to display text easily - whole size of buffered image
        g.setColor(Color.WHITE);
        g.fillRect(0,0,maxX+300,maxY+300);
       
        //border rectangle of heatmap - 5px on each side
        //find max rectangles then
        g.setColor(Color.DARK_GRAY);
        g.fillRect(startX-5, startY-5, w+10, h+10);
        
        g.setColor(bk);
        g.fillRect(startX, startY, w, h);
        
        //draw diversity scale rectangles - add dark grey border as heatmap
        g.setColor(Color.BLACK);
        g.drawString("Shannon Entropy:", startX-200, maxY+40);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(startX-5, maxY+20, w+10, 25);
        g.setColor(bk);
        g.fillRect(startX,maxY+25,w,15);
        int xdiv=startX;
        for(int i=0;i<cd.size();i++){
            g.setColor(cd.get(i));
            g.fillRect(xdiv,maxY+25, 30, 15);
            xdiv+=31;
        }
        
        //draw a filled gradient rectange for the scale
        //use gradient paint and set paint when draw rectangle
        
        GradientPaint gr = new GradientPaint(maxX+50,maxY,botGrad,maxX+50,maxY-100,topGrad);
        g.setPaint(gr);
        g.fillRect(maxX+100, maxY-90, 25, 100);

        
        //write the max and min numbers onto the scale
        g.setColor(Color.BLACK);
        g.drawString(Double.toString(max), maxX+130, maxY-80);
        g.drawString(Double.toString(min), maxX+130, maxY+10);
        
       
        g.setFont(new Font("sansserif", Font.PLAIN, 12));
        
        //print the viral family/genus names, leaving space for tree structure when sorted by family in genus
        for (int i=0;i<vName.size();i++){
           g.drawString(vName.get(i), 220, yc);
           yc+=16;
        }
        
        
            g.setColor(Color.DARK_GRAY);

            int start=startY+4;
            //create lines and print text for displaying what viral family the genus belongs to
            for (int i=0;i<ges.size();i++){
                g.drawLine(175, start, 215, start);
                int dist=ges.get(i)*16;
                int end = start+dist;
                g.drawLine(175,end,215,end);
                int nameP = (dist/2)+start;
                FontMetrics fontMetrics = g.getFontMetrics();
                String s = fp.get(i);
                g.drawString(s, 170 - fontMetrics.stringWidth(s), nameP+2);
                start=end;
            }

        
       //create the individual rectangles
       for (int i=0;i<r.size();i++){
           g.setColor(r.get(i).getCol());
           g.fillRect(r.get(i).getXP(), r.get(i).getYP(),horz, vert);
       }
       
       g.setColor(Color.BLACK);
       

    
       //rotate the text 
        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2.0);
        g.setTransform(at);
        g.setFont(new Font("sansserif", Font.PLAIN, 12));
        //int x=-130;
        int x = startY-(2*startY)+10;
        //int y=216;
        int y =startX+16;
        
        //print the sample names
        for (int i=0;i<fName.size();i++) {
            g.drawString(fName.get(i),x ,y );
            y+=32;
        }
        
        
       
       
       g.dispose();
       
       return buff;
   }
   
  public Image getIm(){
      return im;
  }
    
}
