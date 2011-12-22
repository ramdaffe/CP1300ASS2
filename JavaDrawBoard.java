/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.*;

public class JavaDrawBoard {
    JFrame frame;
    DrawBoard mousePad;
    JLabel jl;
    
    int x = 0;
    int y = 0;

    public static void main(String[] arg) {
        JavaDrawBoard gui = new JavaDrawBoard();
        gui.init();
    }

    public void init() {
        frame = new JFrame("Doodle Kid beta");
        frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mousePad = new DrawBoard();
        frame.getContentPane().add(mousePad, BorderLayout.CENTER);
        mousePad.addMouseMotionListener(new mouseListener());
        jl = new JLabel("Click and Drag");
        frame.getContentPane().add(jl, BorderLayout.SOUTH);
        frame.setJMenuBar(menu());
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
      }

   class mouseListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e){
            jl.setText("" + e.getX() + "," + e.getY());
             mousePad.repaint();
             x = e.getX();
             y = e.getY();
        }
    }
    
    public JMenuBar menu(){
        JMenuBar m = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");
        JMenuItem newmenu = new JMenuItem("New");
        JMenuItem savemenu = new JMenuItem("Save");
        JMenuItem exitmenu = new JMenuItem("Exit");
        file.add(new JSeparator());
        about.add(new JSeparator());
        file.add(newmenu);
        file.add(savemenu);
        file.add(exitmenu);
        m.add(file);
        m.add(about);
        return m;
    }
   
   
    class DrawBoard extends JPanel {
        @Override
        public void paint(Graphics g) { //paintComponent()
            Graphics2D g2d = (Graphics2D) g;
            int r = (int) (Math.random() * 256);
            int gr = (int) (Math.random() * 256);
            int b = (int) (Math.random() * 256);
            g2d.setColor(new Color(r,gr,b));
            int c = (int) (Math.random() * 3);
            Polygon p = new Polygon();
            switch (c) {
                case 1: g2d.fillOval(x,y,50,50);break;
                case 2: g2d.fillRect(x,y,50,50);break;
                default: break;
            }
        }
    }
}
