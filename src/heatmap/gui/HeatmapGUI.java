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

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class HeatmapGUI {

    /**
     * @param args the command line arguments
     */
    
    //main - creates GUI, look and feel
    public static void main(String[] args) {
        mapGUI mp = new mapGUI();
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
                }
            }
        } 
        catch (Exception e) {
           
        }
        mp.setVisible(true);
    }
    
}
