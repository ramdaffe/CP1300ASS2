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
import java.awt.image.BufferedImage;
import java.math.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class JavaDrawBoard implements ActionListener{
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
        //jl = new JLabel("Click and Drag");
        //frame.getContentPane().add(jl, BorderLayout.SOUTH);
        JMenuBar mainmenu = menu();
        frame.getContentPane().add(mainmenu, BorderLayout.NORTH);
        frame.repaint();
        frame.setSize(800, 800);
        frame.setVisible(true);
      }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand())){
            System.out.print("exit");
            System.exit(0);
        } else if ("save".equals(e.getActionCommand())){
            System.out.print("save");
            mousePad.save("new.png");
            //
        }
    }

   class mouseListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e){
            //jl.setText("" + e.getX() + "," + e.getY());
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
    
   
    class DrawBoard extends JPanel {
        
        public void save(String imageFile) {
            Rectangle r = getBounds();
            try {
                BufferedImage i = new BufferedImage(r.width, r.height,
                    BufferedImage.TYPE_INT_ARGB);
                Graphics g = i.getGraphics();
                paint(g);
                ImageIO.write(i, "png", new File(imageFile));
            } catch (IOException e) {
            }
        }
        
        
        @Override
        public void paint(Graphics g) { //paintComponent()
            //BufferedImage bi = new BufferedImage(DrawBoard.WIDTH, DrawBoard.HEIGHT, BufferedImage.TYPE_INT_ARGB);
            //Graphics2D g2d = bi.createGraphics();
            Graphics2D g2d = (Graphics2D) g;
            int r = (int) (Math.random() * 256);
            int gr = (int) (Math.random() * 256);
            int b = (int) (Math.random() * 256);
            g2d.setColor(new Color(r,gr,b));
            int c = (int) (Math.random() * 4);
            int scale = (int) (Math.random()  * 3);
            double rotate = Math.random() * 360;
            Point p1 = new Point(50 / 3, (2 * 50) / 3);
            Point p2 = new Point(50 / 2, 50 / 3);
            Point p3 = new Point((2 * 50) / 3, (2 * 50) / 3);
            int[] xs = { p1.x+x, p2.x+x, p3.x+x };
            int[] ys = { p1.y+y, p2.y+y, p3.y+y };
            Polygon tr = new Polygon(xs,ys,xs.length);
            Rectangle rect = new Rectangle(x,y,25*scale,25*scale);
            g2d.rotate(rotate,x,y);
            switch (c) {
                case 1: g2d.fillOval(x,y,25*scale,25*scale);break;
                case 2: g2d.fill(rect);break;
                case 3: g2d.fillPolygon(tr);break;
                default: break;
            }
            //g2d.drawImage(bi, null, 0, 0);
        }
    }
}
