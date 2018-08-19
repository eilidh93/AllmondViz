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

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.BorderFactory; 
import javax.swing.JPanel; 

/*GUI, layout, actionlisteners and scaling of data
creates instance of heatmap canvas by passing scaling parameters 
and info from file handling/sorting classes
*/



public class mapGUI extends JFrame implements ActionListener, MouseMotionListener, MouseListener, ItemListener {
    
    public JButton openFile,addFile,closeButton,generateMap,clearMap,upMap,saveF,export,searchSeq;
    private JPanel filePanel,centerPan,inPan;
    private JTabbedPane tb;
    private JFileChooser choose,chooser;
    private File files [];
    public JTextArea heatInfo;
    JEditorPane infoText;
    private JTextField lengthInput;
    private final static String newLine="\n";
    private JScrollPane jsp;
    private JRadioButton byEV, byMinEV, byConLength, byMaxConLength, byPercent, byNumCon,alph,byFam,gen,byPerc,fam,byGroup,byOrd;
    private JCheckBox invert,phage;
    private JComboBox colCom;
    public String filePath = null;
    private int xp,yp,maxC,maxEP,minEP;
    private Double maxL,perc,maxEPA,minEPA,maxAvg,maxDivL,minDivL;
    public Double maxi = 0.0;
    public Double mini = 0.0;
    fileH fileH = new fileH();
    FileHGen fGen = new FileHGen();
    private ArrayList <String> fileList = new ArrayList(); 
    private ArrayList <Integer> genSize = new ArrayList();
    private ArrayList <String> fPos = new ArrayList();
    public ArrayList <String> vir = new ArrayList();
    public ArrayList <String> fnl = new ArrayList();
    public ArrayList <Rectangle> rect;
    private Color col,topG,bottomG,bg;
    private heatCanvas h;
    private JList jl;
    private String [] hcol;
    private ArrayList <Color> divCol= new ArrayList();

    
    //basic GUI setup
    public mapGUI(){
        
        //this.setSize(800,800);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setLocation(10,10);
        this.setTitle("AllmondViz");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        guiLayout();
        this.pack();
        
        
        
    }
    
    /*layout of the GUI - panels for user sorting options, tabs for 
    info page and heatmap, open/add file buttons, saving options
    for images and contigs
    */
    public void guiLayout(){

        //panel with open/add/reset buttons
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        topPanel.setBackground(new Color(70, 50, 32));

        openFile = new JButton("Open");
        openFile.addActionListener(this);
        addFile = new JButton("Add File");
        addFile.addActionListener(this);
        addFile.setEnabled(false);
        clearMap = new JButton("Reset");
        clearMap.addActionListener(this);
        saveF = new JButton("Save");
        saveF.addActionListener(this);
    
        
        filePanel=new JPanel();
        BoxLayout boxy = new BoxLayout(filePanel,BoxLayout.X_AXIS);
        filePanel.add(openFile);
        filePanel.add(addFile);
        filePanel.add(clearMap);
        
        filePanel.setBackground(new Color(47, 47, 30));
        topPanel.add(filePanel,BorderLayout.NORTH);
        this.add(topPanel,BorderLayout.NORTH);
        
        
        //panel with all user scaling and sorting options
        JPanel mainP = new JPanel();
        BoxLayout bx = new BoxLayout(mainP,BoxLayout.Y_AXIS);
        mainP.setLayout(bx);
        mainP.setBorder(BorderFactory.createLineBorder(Color.black));
        mainP.setBackground(new Color(231, 216, 203));
        this.add(mainP,BorderLayout.WEST);
        
        mainP.add(Box.createRigidArea(new Dimension(0,10)));
        JLabel di = new JLabel("  Display: ");
        di.setHorizontalAlignment(SwingConstants.LEFT);
        mainP.add(di);
        
        fam = new JRadioButton("Family"); 
        fam.setOpaque(false);
        gen = new JRadioButton("Genus");
        gen.setOpaque(false);
        ButtonGroup bgr = new ButtonGroup();
        bgr.add(gen);
        bgr.add(fam);
        fam.setSelected(true);
        
        mainP.add(Box.createRigidArea(new Dimension(0,10)));
        mainP.add(fam);
        mainP.add(gen);
        
        mainP.add(Box.createRigidArea(new Dimension(0,20)));
        JLabel colourBy = new JLabel("  Colour By: ");
        colourBy.setHorizontalAlignment(SwingConstants.LEFT);
        mainP.add(colourBy);
        
        mainP.add(Box.createRigidArea(new Dimension(0,10)));
        byEV = new JRadioButton("Average eValue");
        byMinEV =new JRadioButton("Minimum eValue");
        byConLength = new JRadioButton("Average Contig Length");
        byMaxConLength = new JRadioButton("Maximum Contig Length");      
        byPercent = new JRadioButton("Total Contig Length as % of Average Genome Size");
        byPerc = new JRadioButton("Max Contig Length as % of Average Segment Size");
        byNumCon = new JRadioButton("Number of Contigs");
        
        mainP.add(byMaxConLength);
        byMaxConLength.setSelected(true);
        mainP.add(byConLength);
        mainP.add(byPercent);
        mainP.add(byPerc);
        mainP.add(byMinEV);
        mainP.add(byEV);
        mainP.add(byNumCon);        
        mainP.add(Box.createRigidArea(new Dimension(0,20)));
        
        ButtonGroup checkGroup = new ButtonGroup();
        checkGroup.add(byEV);
        checkGroup.add(byMinEV);
        checkGroup.add(byConLength);
        checkGroup.add(byMaxConLength);
        checkGroup.add(byPercent);
        checkGroup.add(byPerc);
        checkGroup.add(byNumCon);
        
        byEV.setOpaque(false);
        byMinEV.setOpaque(false);
        byConLength.setOpaque(false);
        byMaxConLength.setOpaque(false);
        byPercent.setOpaque(false);
        byPerc.setOpaque(false);
        byNumCon.setOpaque(false);
        
        JLabel sel = new JLabel("  Select Colour:");
        sel.setHorizontalAlignment(SwingConstants.LEFT);
        mainP.add(sel);
        mainP.add(Box.createRigidArea(new Dimension(0,10)));
        
        hcol = new String[3];
        hcol[1]="Red/Green";
        hcol[0]="Purple/Yellow";
        hcol[2]="Green/Blue";
        colCom = new JComboBox(hcol);
        colCom.setAlignmentX(LEFT_ALIGNMENT);
        colCom.setPreferredSize(new Dimension(150,30));
        colCom.setMaximumSize(new Dimension(150,30));
        mainP.add(colCom);
        mainP.add(Box.createRigidArea(new Dimension(0,20)));
        
        JLabel or = new JLabel("  Family/Genus Order:");
        mainP.add(or);
        mainP.add(Box.createRigidArea(new Dimension(0,10)));
        
        alph = new JRadioButton("Alphabetical Order");
        alph.setOpaque(false);
        byFam = new JRadioButton("By Viral Family (Genus only)");
        byFam.setOpaque(false);
        byOrd =new JRadioButton ("By Order");
        byOrd.setOpaque(false);
        byGroup = new JRadioButton("By Group");
        byGroup.setOpaque(false);
        
        ButtonGroup o = new ButtonGroup();
        o.add(alph);
        alph.setSelected(true);
        o.add(byFam);
        o.add(byGroup);
        o.add(byOrd);
        
        mainP.add(alph);
        mainP.add(byFam);
        mainP.add(byGroup);
        mainP.add(byOrd);
        
        mainP.add(Box.createRigidArea(new Dimension(0,20)));
        
        invert=new JCheckBox("Show Vertebrate Infecting Viruses Only?");
        invert.setOpaque(false);
        phage = new JCheckBox("Exclude Bacteriophages?");
        phage.setOpaque(false);
        mainP.add(invert);
        mainP.add(phage);        
        mainP.add(Box.createRigidArea(new Dimension(0,20)));
        
        JPanel cons = new JPanel();
        cons.setBackground(new Color(231, 216, 203));
        JLabel in = new JLabel("Only Include Contigs Over: ");
        lengthInput = new JTextField();
        lengthInput.setPreferredSize(new Dimension(150,30));
        lengthInput.setMaximumSize(new Dimension(150,30));
        lengthInput.setEditable(true);
        cons.add(in,BorderLayout.WEST);
        cons.add(lengthInput,BorderLayout.EAST);
        mainP.add(cons);
        
        
        
        mainP.add(Box.createHorizontalGlue());
        tb = new JTabbedPane();
        infoText = new JEditorPane();
        infoText.setContentType("text/html");
        
        JPanel aboutPan = new JPanel();
        try {
            addReadMe();
        } catch (IOException ex) {
            Logger.getLogger(mapGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        aboutPan.add(infoText);
        JScrollPane pn = new JScrollPane(aboutPan);
        pn.setPreferredSize(new Dimension(900,900));
        tb.addTab("About <Title>",pn);
        
        
        centerPan = new JPanel();
        jsp = new JScrollPane(centerPan);
        jsp.setPreferredSize(new Dimension(900,900));
        tb.addTab("HeatMap", jsp);
        
        inPan = new JPanel();
        tb.addTab("Summary Information",inPan);
              
        this.add(tb,BorderLayout.CENTER);
        
        JPanel disText = new JPanel();
        disText.setLayout(new BorderLayout());
        heatInfo = new JTextArea();
        heatInfo.setBackground(new Color(231, 216, 203));
        heatInfo.setEditable(false);
        heatInfo.setText("");
        JScrollPane scroll = new JScrollPane(heatInfo);
        scroll.setPreferredSize(new Dimension(350,300));
        disText.add(scroll, BorderLayout.CENTER);
        JPanel exP = new JPanel();
        export = new JButton("Export Contigs");
        export.addActionListener(this);
        searchSeq=new JButton("Search Sequences");
        searchSeq.addActionListener(this);
        exP.add(export,BorderLayout.WEST);
        exP.add(searchSeq,BorderLayout.EAST);
        disText.add(exP,BorderLayout.SOUTH);
        this.add(disText, BorderLayout.EAST);
        
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.add(saveF);
        bottom.setBorder(BorderFactory.createLineBorder(Color.black));
        bottom.setForeground(Color.WHITE);
        bottom.setBackground(new Color(70, 50, 32));
        this.add(bottom,BorderLayout.SOUTH);



        export.setEnabled(false);
        searchSeq.setEnabled(false);

        }
    


    /*if user clicks any buttons e.g. to open/add files, reset the display,
    save the image, save the contigs, or some of the options 
    */
 
   
    @Override
    public void actionPerformed(ActionEvent e){
        
        //for when the user initially wants to open a file
        //will enable the add file after this has completed
        if (e.getSource()==openFile){
            choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            choose.setFileSelectionMode(choose.FILES_AND_DIRECTORIES);
            choose.setMultiSelectionEnabled(true);
            int rt = choose.showOpenDialog(this);
            if (rt == JFileChooser.APPROVE_OPTION){
                files = choose.getSelectedFiles();
                //create an array list of the file paths to use
                
                if (files == null){
                    JOptionPane.showMessageDialog(this,"No File Selected","Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    for (File file : files) {
                        String fP = file.getAbsolutePath();
                        fileList.add(fP);
                    }
                    

                //rectangles for heatmap    
                rect = new ArrayList();
                
                //method for making changes to display
                ifSelected();
                
                //for ordering display
                yAssign();
                
                //add heatmap to panel
                h = new heatCanvas(rect,vir,fnl,topG,bottomG,maxi,mini,genSize,fPos,divCol,bg);
                centerPan.add(h, BorderLayout.CENTER);
                
                //mmouseliseners for contig display
                h.addMouseMotionListener(this);
                h.addMouseListener(this);
                h.repaint();
                
                //change tab to heatmap display tab
                tb.setSelectedIndex(1);
                this.pack();
                this.setExtendedState(this.MAXIMIZED_BOTH);
                addListeners();
            }
            addFile.setEnabled(true);
            openFile.setEnabled(false);
            }
        }

                
        //when adding a file it adds the file name to the list being opened and rereads the files so map can be reprocessed
        //does not simply add to map as needs to rescale
        if (e.getSource()==addFile){
            heatInfo.setText("");
            choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            choose.setFileSelectionMode(choose.FILES_AND_DIRECTORIES);
            choose.setMultiSelectionEnabled(true);
            int ret = choose.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION){
                files = choose.getSelectedFiles();
                //create an array list of the file paths to use
                for (File file : files) {
                    String fP = file.getAbsolutePath();
                    System.out.println("file: " + fP);
                    fileList.add(fP);
                }
            }
                clearLists();
                ifSelected();
                yAssign();
            h = new heatCanvas(rect,vir,fnl,topG,bottomG,maxi,mini,genSize,fPos,divCol,bg);
            centerPan.add(h, BorderLayout.CENTER);
            h.addMouseMotionListener(this);
            h.addMouseListener(this);
            tb.setSelectedIndex(1);
            h.repaint();
            this.pack();
            this.setExtendedState(this.MAXIMIZED_BOTH);
        
        }
        
            
        //clear the list of files so not reading old ones again
        //should clear heatmap
        if (e.getSource()==clearMap){
            choose.setSelectedFile(new File(""));
            for (int i=0;i<fileH.ff.size();i++) {
                fileH.ff.get(i).famO.clear();
            }

            clearLists();
            fileList.clear();           
            addFile.setEnabled(false);
            openFile.setEnabled(true);
            heatInfo.setText("");
            h.repaint();
            removeListeners(); 
            export.setEnabled(false);
            searchSeq.setEnabled(false);
        }
        
        //save the heatmap panel image
        if(e.getSource()==saveF){
            
            choose = new JFileChooser();
            choose.setDialogTitle("Save As...");   
 
            int userSelection = choose.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                try{
                    BufferedImage bi = (BufferedImage)h.getIm();
                    ImageIO.write(bi, "png", choose.getSelectedFile());
                } 
                catch (IOException io){
                JOptionPane.showMessageDialog(this,"Unable to save file","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        //if presses export to file
        if(e.getSource()==export){
            if(!heatInfo.getText().equals("")){
                choose = new JFileChooser();
                choose.setDialogTitle("Save As...");   

                int userSelection = choose.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try{
                        File file = choose.getSelectedFile();
                        if (!file.getName().toLowerCase().endsWith(".txt")) {
                            file = new File(file.getParentFile(), file.getName() + ".txt");
                        }
                        heatInfo.write(new OutputStreamWriter(new FileOutputStream(file)));

                    } 
                    catch (IOException io){
                    JOptionPane.showMessageDialog(this,"Unable to save file","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } 
            }
        }
        
        //when user presses this button the program displays a file chooser for the contig file
        //gets the contig names from the text box and passes to contigfile class to read file and search for sequence
        //then writes the sequence to new file
        
        if(e.getSource()==searchSeq){
            if(!heatInfo.getText().equals("")){
                chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setDialogTitle("Select Contig .fasta File");
                 System.out.println("after setting title");
                //choose.setFileSelectionMode(choose.FILES_AND_DIRECTORIES);
                int rt = chooser.showOpenDialog(this);
                //String n = fGen.fg.get(i).getFileName().substring(fGen.fg.get(i).getFileName().lastIndexOf("\\") + 1);
                String cname = new String();
                if (rt == JFileChooser.APPROVE_OPTION){
                    System.out.println("after aprove option if statement");
                    File file = chooser.getSelectedFile();
                    cname = file.toString();
                }
                String cons = heatInfo.getText();
                ContigFile cf = new ContigFile();
                cf.readContigFile(cname,cons);
            }  
        }

        if(e.getSource()==lengthInput){
            clearLists();
            ifSelected();        
            yAssign();
            h = new heatCanvas(rect,vir,fnl,topG,bottomG,maxi,mini,genSize,fPos,divCol,bg);
            centerPan.add(h, BorderLayout.CENTER);
            h.addMouseMotionListener(this);
            h.addMouseListener(this);
            h.repaint();
            this.pack();
            this.setExtendedState(this.MAXIMIZED_BOTH);
        }
        
        
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie){
        clearLists();
        ifSelected();
        yAssign();
        h = new heatCanvas(rect,vir,fnl,topG,bottomG,maxi,mini,genSize,fPos,divCol,bg);
        centerPan.add(h, BorderLayout.CENTER);
        h.addMouseMotionListener(this);
        h.addMouseListener(this);
        h.repaint();
        tb.setSelectedIndex(1);
        this.pack();
        this.setExtendedState(this.MAXIMIZED_BOTH);
    }
    
    public void clearLists(){
        centerPan.remove(h);
        vir.clear();
        genSize.clear();
        fPos.clear();
        fnl.clear();
        rect.clear(); 
        divCol.clear();
        
        fileH.ff.clear();
        fileH.ord.clear();
        fileH.ofo.clear();
        fileH.clust.cl.clear();
        fileH.cx.clear();
        fileH.sx.clear();
        fileH.ogf.clear();
        fileH.famCount.clear();
        fileH.recList.clear();
        fileH.wrongFiles.clear();
        
        fGen.cx.clear();
        fGen.genCount.clear();
        fGen.sx.clear();
        fGen.ord.clear();
        fGen.ofo.clear();
        fGen.og.clear();
        fGen.ogf.clear();
        fGen.fg.clear();
        fGen.recList.clear();
        fGen.wrongFiles.clear();
        
    }  
    
    public void addListeners(){
        byNumCon.addItemListener(this);
        byEV.addItemListener(this);
        byPercent.addItemListener(this);
        byPerc.addItemListener(this);
        byMaxConLength.addItemListener(this);
        byConLength.addItemListener(this);
        byMinEV.addItemListener(this);
        gen.addItemListener(this);
        fam.addItemListener(this);
        alph.addItemListener(this);
        byFam.addItemListener(this);
        colCom.addItemListener(this);
        lengthInput.addActionListener(this);
        phage.addItemListener(this);
        invert.addItemListener(this);
        byGroup.addItemListener(this);
        byOrd.addItemListener(this);
    }
    
    public void removeListeners(){
        byEV.removeItemListener(this);
        byPercent.removeItemListener(this);
        byPerc.removeItemListener(this);
        byMaxConLength.removeItemListener(this);
        byConLength.removeItemListener(this);
        byMinEV.removeItemListener(this);
        gen.removeItemListener(this);
        fam.removeItemListener(this);
        alph.removeItemListener(this);
        byFam.removeItemListener(this);    
        colCom.removeItemListener(this);
        lengthInput.removeActionListener(this);
        byGroup.removeItemListener(this);
        byOrd.removeItemListener(this);
    }
    
    public void ifSelected(){
        
        String ph;
        if(phage.isSelected()){
                ph="y";
            }
            else{
                ph="n";
            }
        String iv;
        if(invert.isSelected()){
            iv="y";
        }
        else{
            iv="n";
        }
        String ip = lengthInput.getText().trim();            
        if(fam.isSelected()){
            fileH.readFile(fileList,ip,ph,iv);
            fileH.readLengths();
            String or = new String();
            if(alph.isSelected()){
                    or = "a";
                }
            if(byFam.isSelected()){
                    or = "f";
            }
            if(byOrd.isSelected()){
                or="o";
            }
            else if(byGroup.isSelected()){
                or = "g";
            }
            fileH.setYPos(or);
            shanLengthF();
            if(byEV.isSelected()){
                sortEAvg();
            }
            if(byMinEV.isSelected()){
                sortEMin();
            }
            if(byMaxConLength.isSelected()){
                sortConMax();
            }
            if(byConLength.isSelected()){
                sortConAvg();
            }
            if(byNumCon.isSelected()){
                sortConNum();
            }
            if(byPercent.isSelected()){
                sortPerc();
            }
            if(byPerc.isSelected()){
                sortPSeg();
            }
            virName();
            fNameList();
        }
        if(gen.isSelected()){
            fGen.readFile(fileList,ip,ph,iv);
            fGen.readLengths();
            String or = new String();
            if(alph.isSelected()){
                    or = "a";
                }
            if(byOrd.isSelected()){
                or="o";
            }
            if(byGroup.isSelected()){
                or = "g";
            }
            else if(byFam.isSelected()){
                or = "f";
            }
            fGen.setYPos(or);
            shanLengthG();
            if(byEV.isSelected()){
                sortEAvgG();
            }
            if(byMinEV.isSelected()){
                sortEMinG();
            }
            if(byMaxConLength.isSelected()){
                sortConMaxG();
            }
            if(byConLength.isSelected()){
                sortConAvgG();
            }
            if(byNumCon.isSelected()){
                sortConNumG();
            }
            if(byPercent.isSelected()){
                sortPercG();
            }
            if(byPerc.isSelected()){
                sortPSegG();
            }
            gName();
            fNameListG();
        }  
    }
    
    public void eMinScale(){

        minEP=0;
        for (int k=0;k<fileH.ff.size();k++){
            for (int l=fileH.ff.get(k).famO.size()-1;l>=0;l--){
                if ((fileH.ff.get(k).famO.get(l).getMinEP()<minEP)&&(fileH.ff.get(k).famO.get(l).getMinEP()!=0)){
                   minEP=fileH.ff.get(k).famO.get(l).getMinEP();
                }
            }
        }  
        maxEP = minEP;
        
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                
                if ((fileH.ff.get(i).famO.get(j).getMinEP()>maxEP)&&(fileH.ff.get(i).famO.get(j).getMinEP()!=0)) {
                    maxEP=fileH.ff.get(i).famO.get(j).getMinEP();
                    
                }
            }
        }
         mini=(double)maxEP;
         maxi=(double)minEP;
    }
    
    //since minimum do they same way e.g. find percentage of max then subtract that percentage from 100?
    
    public void sortEMin(){
        eMinScale();
        for (int i=0;i<fileH.ff.size();i++) {
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
               
                double p = ((double)fileH.ff.get(i).famO.get(j).getMinEP()/maxEP);
                perc = 100-p;
                xp=fileH.ff.get(i).famO.get(j).xPos();
                yp=fileH.ff.get(i).famO.get(j).getY();
                addRect();
        }
        }
    
   }
    
    
    public void eAvgScale(){
         minEPA=0.0;
        
         for (int k=0;k<fileH.ff.size();k++){
            for (int l=fileH.ff.get(k).famO.size()-1;l>=0;l--){
                if ((fileH.ff.get(k).famO.get(l).getAvgEP()<minEPA)&&(fileH.ff.get(k).famO.get(l).getAvgEP()!=0)){
                    minEPA=fileH.ff.get(k).famO.get(l).getAvgEP();
                    }
            }
        }
        
        maxEPA = minEPA;
        
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                 if ((fileH.ff.get(i).famO.get(j).getAvgEP()>maxEPA)&&(fileH.ff.get(i).famO.get(j).getAvgEP()!=0)) {
                    maxEPA=fileH.ff.get(i).famO.get(j).getAvgEP();
                    }
        }
        }
        mini=(double)maxEPA;
        maxi=(double)minEPA; 
    }
    
    public void sortEAvg(){
         eAvgScale();
          for (int i=0;i<fileH.ff.size();i++) {
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
               
                double p = fileH.ff.get(i).famO.get(j).getAvgEP()/maxEPA;
                perc = 100-p;
                xp=fileH.ff.get(i).famO.get(j).xPos();
                yp=fileH.ff.get(i).famO.get(j).getY();
                addRect();
        }
        }
    }
    
    public void conNumScale(){
        maxC=0;
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                if (fileH.ff.get(i).famO.get(j).totCon()>maxC){
                    maxC=fileH.ff.get(i).famO.get(j).totCon();     
                }
            }
        }
        maxi=(double)maxC; 
        int minC = maxC;
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                if (fileH.ff.get(i).famO.get(j).totCon()<minC){
                    minC=fileH.ff.get(i).famO.get(j).totCon();
                }    
            }
        }
        mini=(double)minC;
    }
    
    public void sortConNum(){
        conNumScale();
        for (int i=0;i<fileH.ff.size();i++) {
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                perc = ((double)fileH.ff.get(i).famO.get(j).totCon()/maxC)*100;
                xp=fileH.ff.get(i).famO.get(j).xPos();
                yp=fileH.ff.get(i).famO.get(j).getY();
                addRect();
            }
        }
    }
    
    public void conMaxLengthScale(){
        maxL=0.0;
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                if (fileH.ff.get(i).famO.get(j).getMaxLength()>maxL){
                    maxL=fileH.ff.get(i).famO.get(j).getMaxLength();
                }
            }
        }
        maxi=maxL;
        mini=maxL;
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                if (fileH.ff.get(i).famO.get(j).getMaxLength()<mini){
                    mini=fileH.ff.get(i).famO.get(j).getMaxLength();
                }
            }
        }
    }
    
    
    public void sortConMax(){
        conMaxLengthScale();
        for (int i=0;i<fileH.ff.size();i++) {
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                perc = (((double)fileH.ff.get(i).famO.get(j).getMaxLength()/maxL)*100);
                xp=fileH.ff.get(i).famO.get(j).xPos();
                yp=fileH.ff.get(i).famO.get(j).getY();
                addRect();
        }
        }
    }
    
    public void conAvgLengthScale(){
        maxAvg=0.0;
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                if (fileH.ff.get(i).famO.get(j).avgConL()>maxAvg){
                    maxAvg=fileH.ff.get(i).famO.get(j).avgConL(); 
                }
            }
        }
        maxi=maxAvg;
        mini=maxAvg;
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                if (fileH.ff.get(i).famO.get(j).avgConL()<mini){
                    mini=fileH.ff.get(i).famO.get(j).avgConL(); 
                }
            }
        }
    }
    
    public void sortConAvg(){
        conAvgLengthScale();
        for (int i=0;i<fileH.ff.size();i++) {
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                perc = (((double)fileH.ff.get(i).famO.get(j).avgConL()/maxAvg)*100);
                xp=fileH.ff.get(i).famO.get(j).xPos();
                yp=fileH.ff.get(i).famO.get(j).getY();
                addRect();
        }
        }
    }
    
    //total contig length % of avg genome
    //find total length for each family 
    //find percentage 
    //then find max and min percentage for scaling
    
    
    public void percScale(){
        
        maxi=100.0;
        mini=0.0;
        
    }
    
    public void sortPerc(){
        percScale();
        for (int i=0;i<fileH.ff.size();i++) {
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                perc = (fileH.ff.get(i).famO.get(j).getSumConL()/fileH.ff.get(i).famO.get(j).getAvgGen())*100;
                xp=fileH.ff.get(i).famO.get(j).xPos();
                yp=fileH.ff.get(i).famO.get(j).getY();
                addRect();
        }
        }
    }
    
    public void sortPercG(){
        percScale();
        for (int i=0;i<fGen.fg.size();i++) {
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                perc = (fGen.fg.get(i).genO.get(j).getSumConL()/fGen.fg.get(i).genO.get(j).getAvgGen())*100;
                xp=fGen.fg.get(i).genO.get(j).xPos();
                yp=fGen.fg.get(i).genO.get(j).getY();
                addRect();
        }
        }
    }
    
    //max contig length as percentage of average segement size
    public void sortPSeg(){
        percScale();
        
        for (int i=0;i<fileH.ff.size();i++) {
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                perc = (fileH.ff.get(i).famO.get(j).getMaxLength()/fileH.ff.get(i).famO.get(j).getAvgSeg())*100;
                xp=fileH.ff.get(i).famO.get(j).xPos();
                yp=fileH.ff.get(i).famO.get(j).getY();
                addRect();
        }
        }
    }
    
     public void sortPSegG(){
        percScale();
       
        for (int i=0;i<fGen.fg.size();i++) {
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                perc = (fGen.fg.get(i).genO.get(j).getMaxLength()/fGen.fg.get(i).genO.get(j).getAvgSeg())*100;
                xp=fGen.fg.get(i).genO.get(j).xPos();
                yp=fGen.fg.get(i).genO.get(j).getY();
                addRect();
        }
        }
    }
    
    public void eMinScaleG(){

        
        minEP=0;
        
         for (int k=0;k<fGen.fg.size();k++){
            for (int l=fGen.fg.get(k).genO.size()-1;l>=0;l--){
                if ((fGen.fg.get(k).genO.get(l).getMinEP()<minEP)&&(fGen.fg.get(k).genO.get(l).getMinEP()!=0)){
                    minEP=fGen.fg.get(k).genO.get(l).getMinEP();
                    }
            }
        }
         
        
         
        maxEP = minEP;
        
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                if ((fGen.fg.get(i).genO.get(j).getMinEP()>maxEP)&&(fGen.fg.get(i).genO.get(j).getMinEP()!=0)) {
                    maxEP=fGen.fg.get(i).genO.get(j).getMinEP();
                }
            }
        }
        //as e scale goes opposite - lower value is better
         maxi=(double)minEP;
         mini=(double)maxEP;
         
         
    }
    
    //since minimum do they same way e.g. find percentage of max then subtract that percentage from 100?
    
    public void sortEMinG(){
        eMinScaleG();
        for (int i=0;i<fGen.fg.size();i++) {
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
               
                double p = ((double)fGen.fg.get(i).genO.get(j).getMinEP()/maxEP);
                perc = 100-p;
                xp=fGen.fg.get(i).genO.get(j).xPos();
                yp=fGen.fg.get(i).genO.get(j).getY();
                addRect();
        }
        }
    
   }
    
    
    public void eAvgScaleG(){
         minEPA=0.0;
        
         for (int k=0;k<fGen.fg.size();k++){
            for (int l=fGen.fg.get(k).genO.size()-1;l>=0;l--){
                if ((fGen.fg.get(k).genO.get(l).getAvgEP()<minEPA)&&(fGen.fg.get(k).genO.get(l).getAvgEP()!=0)){
                    minEPA=fGen.fg.get(k).genO.get(l).getAvgEP();
                    }
            }
        }
        
        maxEPA = minEPA;        
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
//                if ( (fGen.fg.get(i).genO.get(j).getAvgEP()==0)){
//                    //System.out.println("The value is 0 so we should ignore this " + fileH.ff.get(i).famO.get(j).getAvgEP());
//                }
                if ((fGen.fg.get(i).genO.get(j).getAvgEP()>maxEPA)&&(fGen.fg.get(i).genO.get(j).getAvgEP()!=0)) {
                    maxEPA=fGen.fg.get(i).genO.get(j).getAvgEP();
                    }
        }
        }
        maxi=(double)minEPA;
        mini=(double)maxEPA; 
    }
    
    public void sortEAvgG(){
         eAvgScaleG();
          for (int i=0;i<fGen.fg.size();i++) {
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){               
                double p = fGen.fg.get(i).genO.get(j).getAvgEP()/maxEPA;
                perc = 100-p;
                xp=fGen.fg.get(i).genO.get(j).xPos();
                yp=fGen.fg.get(i).genO.get(j).getY();
                addRect();
        }
        }
    }
    
    public void conNumScaleG(){
        maxC=0;
        
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                if (fGen.fg.get(i).genO.get(j).totCon()>maxC){
                    maxC=fGen.fg.get(i).genO.get(j).totCon();
                    
                }
                
            }
        }
        maxi=(double)maxC;
        int minC=maxC;
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                if (fGen.fg.get(i).genO.get(j).totCon()<minC){
                    minC=fGen.fg.get(i).genO.get(j).totCon();
                    
                }
                
            }
        }
        mini=(double)minC;
    }
    
    public void sortConNumG(){
        conNumScaleG();
        for (int i=0;i<fGen.fg.size();i++) {
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                perc = ((double)fGen.fg.get(i).genO.get(j).totCon()/maxC)*100;
                xp=fGen.fg.get(i).genO.get(j).xPos();
                yp=fGen.fg.get(i).genO.get(j).getY();
                addRect();
            }
        }
    }
    
    public void conMaxLengthScaleG(){
        maxL=0.0;
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                if (fGen.fg.get(i).genO.get(j).getMaxLength()>maxL){
                    maxL=fGen.fg.get(i).genO.get(j).getMaxLength();
                }
            }
        }
        maxi=maxL;
        mini=maxL;
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                if (fGen.fg.get(i).genO.get(j).getMaxLength()<mini){
                    mini=fGen.fg.get(i).genO.get(j).getMaxLength();
                }
            }
        }
    }
    
    
    public void sortConMaxG(){
        conMaxLengthScaleG();
        for (int i=0;i<fGen.fg.size();i++) {
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                perc = (((double)fGen.fg.get(i).genO.get(j).getMaxLength()/maxL)*100);
                xp=fGen.fg.get(i).genO.get(j).xPos();
                yp=fGen.fg.get(i).genO.get(j).getY();
                addRect();
        }
        }
    }
    
    public void conAvgLengthScaleG(){
        maxAvg=0.0;
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                if (fGen.fg.get(i).genO.get(j).avgConL()>maxAvg){
                    maxAvg=fGen.fg.get(i).genO.get(j).avgConL(); 
                }
            }
        }
        maxi=maxAvg;
        mini=maxAvg;
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                if (fGen.fg.get(i).genO.get(j).avgConL()<mini){
                    mini=fGen.fg.get(i).genO.get(j).avgConL(); 
                }
            }
        }
    }
    
    public void sortConAvgG(){
        conAvgLengthScaleG();
        for (int i=0;i<fGen.fg.size();i++) {
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                perc = (((double)fGen.fg.get(i).genO.get(j).avgConL()/maxAvg)*100);
                xp=fGen.fg.get(i).genO.get(j).xPos();
                yp=fGen.fg.get(i).genO.get(j).getY();
                addRect();
        }
        }
    }
    
    public void shanLengthScaleG(){
        //find the total length of all contigs in all files
        Double totC=0.0;
        for (int i=0;i<fGen.fg.size();i++){
            totC+=fGen.fg.get(i).conScore();
        }
        
        //find proporion for each family in a file 
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                Double p = fGen.fg.get(i).genO.get(j).totCon()/fGen.fg.get(i).conScore();
                fGen.fg.get(i).genO.get(j).setProp(p);
            }
        }
        
        //mult natural log prop x prop x -1
        for (int i=0;i<fGen.fg.size();i++){
            for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                Double p = (Math.log(fGen.fg.get(i).genO.get(j).getProp()))*fGen.fg.get(i).genO.get(j).getProp()*-1;
                fGen.fg.get(i).genO.get(j).setLogProp(p);    
            }
        }
        
        //use div scores - find max and min across files
        maxDivL=0.0;
        Double totalD=0.0;
        
        //first find total div for all files
        for (int i=0;i<fGen.fg.size();i++){
            totalD+=fGen.fg.get(i).divScore();
        }
        
        
        
        //find the max
        
        for (int i=0;i<fGen.fg.size();i++){
            if(fGen.fg.get(i).divScore()>maxDivL){
            maxDivL=fGen.fg.get(i).divScore();
            }
        }
        
        minDivL=maxDivL;
        
        for (int i=0;i<fGen.fg.size();i++){
            if(fGen.fg.get(i).divScore()<minDivL){
            minDivL=fGen.fg.get(i).divScore();
            }
        }
        
        maxi=maxDivL;
        mini=minDivL;
    }
    
    public void shanLengthG(){
        shanLengthScaleG();
       
        for (int i=0;i<fGen.fg.size();i++) {
                perc = (fGen.fg.get(i).divScore()/maxDivL)*100;
                    String c = (String)colCom.getSelectedItem();
                    if (c.equals("Purple/Yellow")){
                        divCol.add(getColourPurpleYellow());
                    }
                    if(c.equals("Red/Green")){
                        divCol.add(getColourRedGreen());
                    }
                    if(c.equals("Green/Blue")){
                        divCol.add(getColourGreenBlue());
                    }
            
        }
    }
    
    public void shanLengthScaleF(){
        Double totC=0.0;
        for (int i=0;i<fileH.ff.size();i++){
            totC+=fileH.ff.get(i).conScore();
        }
        
        //find proporion for each family in a file 
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                Double p = fileH.ff.get(i).famO.get(j).totCon()/fileH.ff.get(i).conScore();
                fileH.ff.get(i).famO.get(j).setProp(p);
            }
        }
        
        //mult natural log prop x prop x -1
        for (int i=0;i<fileH.ff.size();i++){
            for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                Double p = (Math.log(fileH.ff.get(i).famO.get(j).getProp()))*fileH.ff.get(i).famO.get(j).getProp()*-1;
                fileH.ff.get(i).famO.get(j).setLogProp(p);    
            }
        }
        
        //use div scores - find max and min across files
        maxDivL=0.0;
        Double totalD=0.0;
        
        //first find total div for all files
        for (int i=0;i<fileH.ff.size();i++){
            totalD+=fileH.ff.get(i).divScore();
        }
        
        
        
        //find the max
        
        for (int i=0;i<fileH.ff.size();i++){
            if(fileH.ff.get(i).divScore()>maxDivL){
            maxDivL=fileH.ff.get(i).divScore();
            }
        }
        
        minDivL=maxDivL;
        
        for (int i=0;i<fileH.ff.size();i++){
            if(fileH.ff.get(i).divScore()<minDivL){
            minDivL=fileH.ff.get(i).divScore();
            }
        }
        
        maxi=maxDivL;
        mini=minDivL;
    }
    
    public void shanLengthF(){
        shanLengthScaleF();
        for (int i=0;i<fileH.ff.size();i++) {
                perc = (fileH.ff.get(i).divScore()/maxDivL)*100;
                String c = (String)colCom.getSelectedItem();
                    if (c.equals("Purple/Yellow")){
                        divCol.add(getColourPurpleYellow());
                    }
                    if(c.equals("Red/Green")){
                        divCol.add(getColourRedGreen());
                    }
                    if(c.equals("Green/Blue")){
                        divCol.add(getColourGreenBlue());
                    }
        
        }
    }
    

    
    //rectangle arraylist to pass to heatmap drawing class
    public void addRect(){
       // System.out.println("perc is " + perc);
        rect.add(new Rectangle());
        int add = rect.size()-1;
        rect.get(add).setXP(xp);
        rect.get(add).setYP(yp);
        
        //do if yellow combo box selected, if red etc to do this part and have diff methods
        //creates colour scale bar also
        String c = (String)colCom.getSelectedItem();
        if (c.equals("Purple/Yellow")){
            rect.get(add).setCol(getColourPurpleYellow());
            topG=(new Color(144,0,200));
            bottomG=(new Color(224,216,105));
            bg=new Color(249, 245, 134);
        }
        if(c.equals("Red/Green")){
            rect.get(add).setCol(getColourRedGreen());
            topG=(new Color(255,0,0));
            bottomG=(new Color(0,255,0));
            bg=new Color(128, 255, 128);
        }
        if(c.equals("Green/Blue")){
            rect.get(add).setCol(getColourGreenBlue());
            topG=new Color(13,39,247);
            bottomG=new Color(67,238,17);
            bg=new Color(162, 246, 136);
        }
    }
    
    //list of family names to pass to heatmap drawing class
    public void virName(){
        for (int i=0; i<fileH.ord.size();i++){
            vir.add(fileH.ord.get(i).getFam());
        }
    }
    
    public void gName(){
        for (int i=0;i<fGen.ord.size();i++){
            vir.add(fGen.ord.get(i).getGen());
        }
    }
    
    //list of file names to pass to heatmap drawing class
    public void fNameList(){
        for (int i=0;i<fileH.clust.samNames.size();i++){
            String n = fileH.clust.samNames.get(i);
            fnl.add(n);
        }
    }
    
    public void fNameListG(){
        for (int i=0;i<fGen.clust.samNames.size();i++){
            String n = fGen.clust.samNames.get(i);
            fnl.add(n);
        }
    }
    
    //gets the ordered list for the y axis ordering e.g. family, group order plus the size 
    public void yAssign(){
        if (gen.isSelected() && byFam.isSelected()){
            for (int i=0;i<fGen.ogf.size();i++){
                fPos.add(fGen.ogf.get(i).getFam());
                genSize.add(fGen.ogf.get(i).gens.size());
            }
        }
        if (gen.isSelected() && byOrd.isSelected()){
             for (int i=0;i<fGen.ofo.size();i++){
                fPos.add(fGen.ofo.get(i).getOrder());
                genSize.add(fGen.ofo.get(i).fams.size());
             }
        }
        if (gen.isSelected() && byGroup.isSelected()){
            for (int i=0;i<fGen.og.size();i++){
                fPos.add(fGen.og.get(i).getGroup());
                genSize.add(fGen.og.get(i).fams.size());
             }
        }
        if (fam.isSelected() && byGroup.isSelected()){
            for (int i=0;i<fileH.ogf.size();i++){
                fPos.add(fileH.ogf.get(i).getGroup());
                genSize.add(fileH.ogf.get(i).fams.size());
            }
        }
        else if(fam.isSelected() && byOrd.isSelected()){
            for (int i=0;i<fileH.ofo.size();i++){
                fPos.add(fileH.ofo.get(i).getOrder());
                genSize.add(fileH.ofo.get(i).fams.size());
            }
        }
        
        
    }

    //purple yellow colour scale based on percentages of max value (e.g. contig length)
    public Color getColourPurpleYellow(){
        if (perc>=95){
            col=new Color(144,0,200);
        }
        if ((perc<95) && (perc>=90)){
            col=new Color(138,0,191);
        }
        if ((perc<90) && (perc>=85)){
            col=new Color(142,12,180);
        }
        if ((perc<85) && (perc>=80)){
            col=new Color(147,24,170);
        }
        if ((perc<80) && (perc>=75)){
            col=new Color(152,36,160);
        }
        if ((perc<75) && (perc>=70)){
            col=new Color(157,48,150);
        }
        if ((perc<70) && (perc>=65)){
            col=new Color(161,60,140);
        }
        if ((perc<65) && (perc>=60)){
            col=new Color(166,72,130);
        }
        if ((perc<60) && (perc>=55)){
            col=new Color(171,84,120);
        }
        if ((perc<55) && (perc>=50)){
            col=new Color(176,96,110);
        }
        if ((perc<50) && (perc>=45)){
            col=new Color(181,128,100);
        }
        if ((perc<45) && (perc>=40)){
            col=new Color(185,120,90);
        }
        if ((perc<40) && (perc>=35)){
            col=new Color(190,132,80);
        }
        if ((perc<35) && (perc>=30)){
            col=new Color(195,144,70);
        }
        if ((perc<30) && (perc>=25)){
            col=new Color(200,156,60);
        }
        if ((perc<25) && (perc>=20)){
            col=new Color(205,168,50);
        }
        if ((perc<20) && (perc>=15)){
            col=new Color(209,180,40);
        }
        if ((perc<15) && (perc>=10)){
            col=new Color(214,192,30);
        }
        if ((perc<10) && (perc>=5)){
            col=new Color(219,204,20);
        }
        if ((perc<5) && (perc>=0)){
            col=new Color(224,216,10);
        }
        else if (perc==null){
            col=new Color(229,229,0);
        }
        return col;
        }

    
    //red green colour scale - based on percentages of max value (e.g. length)
    public Color getColourRedGreen(){
        if (perc>=95){
            col=new Color(255, 0, 0);
        }
        if ((perc<95) && (perc>=90)){
            col=new Color(225, 30, 0);
        }
        if ((perc<90) && (perc>=85)){
            col=new Color(255, 60, 0);
        }
        if ((perc<85) && (perc>=80)){
            col=new Color(255,90,0);
        }
        if ((perc<80) && (perc>=75)){
            col=new Color(255,120,0);
        }
        if ((perc<75) && (perc>=70)){
            col=new Color(255,150,0);
        }
        if ((perc<70) && (perc>=65)){
            col=new Color(255,180,0);
        }
        if ((perc<65) && (perc>=60)){
            col=new Color(255,210,0);
        }
        if ((perc<60) && (perc>=55)){
            col=new Color(255,240,0);
        }
        if ((perc<55) && (perc>=50)){
            col=new Color(255,255,0);
        }
        if ((perc<50) && (perc>=45)){
            col=new Color(255,255,0);
        }
        if ((perc<45) && (perc>=40)){
            col=new Color(200,255,0);
        }
        if ((perc<40) && (perc>=35)){
            col=new Color(175,255,0);
        }
        if ((perc<35) && (perc>=30)){
            col=new Color(150,255,0);
        }
        if ((perc<30) && (perc>=25)){
            col=new Color(125,255,0);
        }
        if ((perc<25) && (perc>=20)){
            col=new Color(100,255,0);
        }
        if ((perc<20) && (perc>=15)){
            col=new Color(75,255,0);
        }
        if ((perc<15) && (perc>=10)){
            col=new Color(50,255,0);
        }
        if ((perc<10) && (perc>=5)){
            col=new Color(25,255,0);
        }
        if ((perc<5) && (perc>=0)){
            col=new Color(0,255,0);
        }
        else if (perc==null){
            col=new Color(0,255,0);
        }
        return col;
    }
    
    //green blue colour scale
       public Color getColourGreenBlue(){
        if (perc>=95){
            col=new Color(13, 39, 247);
        }
        if ((perc<95) && (perc>=90)){
            col=new Color(15,49,235);
        }
        if ((perc<90) && (perc>=85)){
            col=new Color(18,59,223);
        }
        if ((perc<85) && (perc>=80)){
            col=new Color(21,70,211);
        }
        if ((perc<80) && (perc>=75)){
            col=new Color(24,80,209);
        }
        if ((perc<75) && (perc>=70)){
            col=new Color(27,91,187);
        }
        if ((perc<70) && (perc>=65)){
            col=new Color(30,101,175);
        }
        if ((perc<65) && (perc>=60)){
            col=new Color(32,112,162);
        }
        if ((perc<60) && (perc>=55)){
            col=new Color(36,122,150);
        }
        if ((perc<55) && (perc>=50)){
            col=new Color(38,133,138);
        }
        if ((perc<50) && (perc>=45)){
            col=new Color(41,143,126);
        }
        if ((perc<45) && (perc>=40)){
            col=new Color(44,154,114);
        }
        if ((perc<40) && (perc>=35)){
            col=new Color(47,164,102);
        }
        if ((perc<35) && (perc>=30)){
            col=new Color(49,175,89);
        }
        if ((perc<30) && (perc>=25)){
            col=new Color(52,185,77);
        }
        if ((perc<25) && (perc>=20)){
            col=new Color(55,196,65);
        }
        if ((perc<20) && (perc>=15)){
            col=new Color(58,206,53);
        }
        if ((perc<15) && (perc>=10)){
            col=new Color(61,217,41);
        }
        if ((perc<10) && (perc>=5)){
            col=new Color(64,227,29);
        }
        if ((perc<5) && (perc>=0)){
            col=new Color(67,238,17);
        }
        else if (perc==null){
            col=new Color(67,238,17);
        }
        return col;
    }

    
    

          
    //contigs displayed when mouse hovers over rectangles      
        @Override
    public void mouseMoved(MouseEvent e){
        
        if(fam.isSelected()){
            for (int i=0;i<fileH.ff.size();i++){
                for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                    if ((e.getX()>=fileH.ff.get(i).famO.get(j).xPos()) && (e.getX()<=(fileH.ff.get(i).famO.get(j).xPos()+30)) && (e.getY()>=fileH.ff.get(i).famO.get(j).getY()) && (e.getY()<=(fileH.ff.get(i).famO.get(j).getY()+15))){
                        StringBuilder st = new StringBuilder();
                        st.append("<html>");
                        for (int k=0;k<fileH.ff.get(i).famO.get(j).contigNames.size();k++){
                            st.append(fileH.ff.get(i).famO.get(j).getConName(k) + "<br>");

                        }
                        st.append("</html>");
                        h.setToolTipText(st.toString());
                    }
                }
            }
        }
        if(gen.isSelected()){
            for (int i=0;i<fGen.fg.size();i++){
                for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                    if ((e.getX()>=fGen.fg.get(i).genO.get(j).xPos()) && (e.getX()<=(fGen.fg.get(i).genO.get(j).xPos()+30)) && (e.getY()>=fGen.fg.get(i).genO.get(j).getY()) && (e.getY()<=(fGen.fg.get(i).genO.get(j).getY()+15))){
                        StringBuilder st = new StringBuilder();
                        st.append("<html>");
                        for (int k=0;k<fGen.fg.get(i).genO.get(j).contigNames.size();k++){
                            st.append(fGen.fg.get(i).genO.get(j).getConName(k) + "<br>");

                        }
                        st.append("</html>");
                        h.setToolTipText(st.toString());
                    }
                }
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        
    }
    
    //if click on rectangle - contigs are displayed on right hand side panel
    
    @Override
    public void mouseClicked(MouseEvent e){
        heatInfo.setText("");
        String t = "";
        DefaultListModel listModel = new DefaultListModel();   
        if (fam.isSelected()){
            for (int i=0;i<fileH.ff.size();i++){
                for (int j=0;j<fileH.ff.get(i).famO.size();j++){
                    if ((e.getX()>=fileH.ff.get(i).famO.get(j).xPos()) && (e.getX()<=(fileH.ff.get(i).famO.get(j).xPos()+30)) && (e.getY()>=fileH.ff.get(i).famO.get(j).getY()) && (e.getY()<=(fileH.ff.get(i).famO.get(j).getY()+15))){
                        for (int k=0;k<fileH.ff.get(i).famO.get(j).contigNames.size();k++){
                            String st = fileH.ff.get(i).famO.get(j).getConName(k);
                            listModel.addElement(st);

                        }
                        String subT=fileH.ff.get(i).getFileName();
                        t=("Contigs for " + fileH.ff.get(i).famO.get(j).getFamName() + " in " + subT.substring(fileH.ff.get(i).getFileName().lastIndexOf("\\") + 1));
                        jl = new JList(listModel);
                        jl.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                        jl.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                        jl.setVisibleRowCount(-1);
                        JScrollPane sc = new JScrollPane(jl);
                        sc.setPreferredSize(new Dimension(300, 80));

                        //JOptionPane.showMessageDialog(this,sc,t,JOptionPane.PLAIN_MESSAGE);
                        heatInfo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
                        heatInfo.append(t + "\n" + ">" + "\n");
                        heatInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                        for (int index=0;index<listModel.getSize();index++){
                            heatInfo.append(listModel.getElementAt(index).toString() + "\n");
                        }
                        export.setEnabled(true);
                        searchSeq.setEnabled(true);
                    }
                }
            }
        }
        if (gen.isSelected()){
             for (int i=0;i<fGen.fg.size();i++){
                for (int j=0;j<fGen.fg.get(i).genO.size();j++){
                    if ((e.getX()>=fGen.fg.get(i).genO.get(j).xPos()) && (e.getX()<=(fGen.fg.get(i).genO.get(j).xPos()+30)) && (e.getY()>=fGen.fg.get(i).genO.get(j).getY()) && (e.getY()<=(fGen.fg.get(i).genO.get(j).getY()+15))){
                        for (int k=0;k<fGen.fg.get(i).genO.get(j).contigNames.size();k++){
                            String st = fGen.fg.get(i).genO.get(j).getConName(k);
                            listModel.addElement(st);

                        }
                        String subT=fGen.fg.get(i).getFileName();
                        t=("Contigs for " + fGen.fg.get(i).genO.get(j).getGenName() + " in " + subT.substring(fGen.fg.get(i).getFileName().lastIndexOf("\\") + 1));
                        jl = new JList(listModel);
                        jl.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                        jl.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                        jl.setVisibleRowCount(-1);
                        JScrollPane sc = new JScrollPane(jl);
                        sc.setPreferredSize(new Dimension(300, 80));

                        //JOptionPane.showMessageDialog(this,sc,t,JOptionPane.PLAIN_MESSAGE);
                        heatInfo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
                        heatInfo.append(t + "\n" + "\n");
                        heatInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                        for (int index=0;index<listModel.getSize();index++){
                            heatInfo.append(listModel.getElementAt(index).toString() + "\n");
                        }
                        export.setEnabled(true);
                        searchSeq.setEnabled(true);
                    }

                }
            }
        }

        

    }
    
    //read info file - adds to tab 1 of display
    public void addReadMe() throws IOException{
        StringBuilder sbld = new StringBuilder();
        
        InputStream in = mapGUI.class.getResourceAsStream("info.txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = bf.readLine()) != null) {
            sbld.append(line);
        }
        infoText.setText(sbld.toString());
          
    }
    
    @Override
    public void mouseExited(MouseEvent e){
        
    }
          
    @Override
    public void mouseEntered(MouseEvent e){
        
    }
    
     @Override
    public void mouseReleased(MouseEvent e){
        
    }
    
     @Override
    public void mousePressed(MouseEvent e){
        
    }
}

