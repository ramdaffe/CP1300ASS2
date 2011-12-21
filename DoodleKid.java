/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwo;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Ramda
 */
public class DoodleKid {
    private static void createGUI(){
        Dimension paneldim = new Dimension(600,580);
        Dimension menudim = new Dimension(600,20);
        JFrame frame = new JFrame("Doodle Kid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menubar = new JMenuBar();
        menubar.setPreferredSize(menudim);
        JPanel panel = new JPanel();
        panel.setPreferredSize(paneldim);
        frame.setJMenuBar(menubar);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        createGUI();
    }
}
