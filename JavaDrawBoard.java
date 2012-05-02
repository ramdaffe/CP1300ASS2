/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwo;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class JavaDrawBoard implements ActionListener{
    JFrame frame;
    DrawBoard mousePad;
    JLabel jl;
    BufferedImage bimaster;
    Graphics2D g2d;
    int mode;
    Boolean initstate = true;
    
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
        mousePad.addMouseListener(new mouseListener2());
        JMenuBar mainmenu = menu();
        frame.getContentPane().add(mainmenu, BorderLayout.NORTH);
        frame.setSize(800, 800);
        frame.setVisible(true);
        mode = 3;   //0 for square, 1 for circle, 2 for triangle
      }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand())){
            System.out.print("exit");
            System.exit(0);
        } else if ("save".equals(e.getActionCommand())){
            System.out.print("save");
            save();
        } else if ("new".equals(e.getActionCommand())){
            System.out.print("new");
            mousePad.clearImage();
        } 
    }
        
        public void save() {
            try {
                File imageFile = new File("doodle.png");
                ImageIO.write(bimaster, "png", imageFile);
            } catch (IOException e) {
            }
        }
        
      
    class mouseListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e){
            if  (initstate){
                mode = getRandomInt (3);
                initstate = false;
            }
             
             x = e.getX();
             y = e.getY();
             mousePad.repaint();
             
        }
        
        @Override
        public void mouseMoved(MouseEvent e){
             x = e.getX();
             y = e.getY();
        }
    }
    
    class mouseListener2 extends MouseAdapter {
        
        
        @Override
        public void mousePressed(MouseEvent e){
             if  (initstate){
                mode = getRandomInt (3);
                initstate = false;
             }
             x = e.getX();
             y = e.getY();
             mousePad.repaint();
             mode = getRandomInt (3);
        }
  
    }
   
    public JMenuBar menu(){
        JMenuBar m = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");
        JMenuItem newmenu = new JMenuItem("New");
        newmenu.addActionListener(this);
        newmenu.setActionCommand("new");
        JMenuItem savemenu = new JMenuItem("Save");
        savemenu.addActionListener(this);
        savemenu.setActionCommand("save");
        JMenuItem exitmenu = new JMenuItem("Exit");
        exitmenu.addActionListener(this);
        exitmenu.setActionCommand("exit");
        file.add(new JSeparator());
        about.add(new JSeparator());
        file.add(newmenu);
        file.add(savemenu);
        file.add(exitmenu);
        m.add(file);
        m.add(about);
        return m;
    }
    
    public int getRandomInt(int factor){
            int r = (int) (Math.random() * factor);
            return r;
    }
    

    
    public class DrawBoard extends JPanel {
        
        public void createNewImage(){
            bimaster = new BufferedImage(800, 800,
                    BufferedImage.TYPE_INT_RGB);
            g2d = (Graphics2D)bimaster.getGraphics();
        } 
    
        public void clearImage(){
            createNewImage();
            initstate = true;
            repaint();
        }
        
        public Color setColor(){
            int r = getRandomInt(256);
            int gr = getRandomInt(256);
            int b = getRandomInt(256);
            return new Color(r,gr,b);
        }
        /*
        public void Animate(){
            for (int i = 0; i<mousePad.getComponentCount();i++){
                mousePad.getComponent(i).setSize(0.5);
            };
        }
        */
        public Polygon createTriangle(int scale){
            Point p1 = new Point(0, 0);
            Point p2 = new Point(scale, 0);
            Point p3 = new Point((scale/2), (3 * scale) / 2);
            int[] xs = { p1.x+x-(scale /2), p2.x+x-(scale /2), p3.x+x-(scale /2) };
            int[] ys = { p1.y+y-(scale /2), p2.y+y-(scale /2), p3.y+y-(scale /2) };
            return new Polygon(xs,ys,xs.length);
        }
        
        @Override
        public void paint(Graphics g) {
            if (bimaster == null) {
                createNewImage();
            }
            super.paint(g);
            g.drawImage(bimaster, 0, 0, null);
            Color currentcolor = setColor();
            g2d.setColor(currentcolor);
            int scale = getRandomInt(50);
            //double rotat = Math.random() * 360;
            //Animate();
            switch (mode) {
                case 0: g2d.fillOval(x-(scale/2),y-(scale /2),scale,scale);break;
                case 1: Rectangle rect = new Rectangle(x-(scale /2),y-(scale /2),scale,scale);g2d.fill(rect);break;
                case 2: Polygon tr = createTriangle(scale);g2d.fillPolygon(tr);break;
                default: break;
            }
        }
    }
}
