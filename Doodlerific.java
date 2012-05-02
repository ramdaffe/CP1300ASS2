/**
 * Doodlerific 1.0
 * James Cook University
 * @author Ramda Yanurzha <ramda.yanurzha@my.jcu.edu.au>
 * @version 1.0
 * @since 2012-01-11
 */

package assignmenttwo;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Doodlerific implements ActionListener{
    JFrame frame;
    DrawBoard Canvas;
    JLabel jl;
    BufferedImage bimaster;
    Image img;
    Graphics2D g2d;
    Boolean initstate = true;
    Boolean newstate = false;
    int mode;
    int xpos = 0;
    int ypos = 0;
    int DEFAULTWIDTH = 800;
    int DEFAULTHEIGHT = 800;
    JFileChooser fchoose;

    /**
     * Main method
     */
    public static void main(String[] arg) {
        Doodlerific gui = new Doodlerific();
        gui.init();
    }

    /**
     * Initialize the program
     */
    public void init() {
        frame = new JFrame("Doodlerific v 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas = new DrawBoard();
        frame.getContentPane().add(Canvas, BorderLayout.CENTER);
        Canvas.addMouseMotionListener(new mouseListener());
        Canvas.addMouseListener(new mouseListener2());
        Canvas.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "clrcanvas");
        Canvas.getActionMap().put("clrcanvas",
    new AbstractAction("clrcanvas") {
            @Override
        public void actionPerformed(ActionEvent evt) {
            Canvas.clearImage();
        }
    }
);
        JMenuBar mainmenu = menu();
        fchoose = new JFileChooser();
        frame.getContentPane().add(mainmenu, BorderLayout.NORTH);
        frame.setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        mode = 6;   //0 for square, 1 for circle, 2 for triangle
      }

    @Override
    /**
     * action event for menu bar
     */
    public void actionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand())){
            System.out.print("exit");
            System.exit(0);
        } else if ("save".equals(e.getActionCommand())){
            System.out.print("save");
            save();
        } else if ("new".equals(e.getActionCommand())){
            System.out.print("new");
            Canvas.clearImage();
        } else if ("open".equals(e.getActionCommand())){
            System.out.print("open");
            open();
        } 
    }

    /**
     * save current file
     */
    public void save() {
        try {
            File imageFile = new File("doodle.png");
            ImageIO.write(bimaster, "png", imageFile);
        } catch (IOException e) {
        }
    }

    /**
     * open an existing file
     */
    public void open(){
        int returnVal = fchoose.showDialog(frame, null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File myFile = fchoose.getSelectedFile();
            try {
                img = ImageIO.read(myFile);
                newstate = true;
                Canvas.repaint();
            } catch( IOException e ) {
                e.printStackTrace();
            }
        }
    }
        
    /**
     * mouse motion listener class for drag event
     */
    class mouseListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e){
            if  (initstate){
                mode = getRandomInt (8);
                initstate = false;
            }
             xpos = e.getX();
             ypos = e.getY();
             Canvas.repaint();
             
        }
        
        @Override
        public void mouseMoved(MouseEvent e){
             xpos = e.getX();
             ypos = e.getY();
        }
    }

    /**
     * mouse listener class for pressed and released event
     */
    class mouseListener2 extends MouseAdapter {
        
        
        @Override
        public void mousePressed(MouseEvent e){
             if  (initstate){
                mode = getRandomInt (8);
                initstate = false;
             }
             xpos = e.getX();
             ypos = e.getY();
             Canvas.repaint();
             mode = getRandomInt (8);
        }
  
    }

    /**
     * create a new menu bar
     *
     */
    public JMenuBar menu(){
        JMenuBar m = new JMenuBar();
        JMenu file = new JMenu("File");

        //new file menu
        JMenuItem newmenu = new JMenuItem("New");
        newmenu.addActionListener(this);
        newmenu.setActionCommand("new");

        //open file menu
        JMenuItem openmenu = new JMenuItem("Open");
        openmenu.addActionListener(this);
        openmenu.setActionCommand("open");

        //save file menu
        JMenuItem savemenu = new JMenuItem("Save");
        savemenu.addActionListener(this);
        savemenu.setActionCommand("save");

        //terminate program menu
        JMenuItem exitmenu = new JMenuItem("Exit");
        exitmenu.addActionListener(this);
        exitmenu.setActionCommand("exit");


        file.add(new JSeparator());
        file.add(newmenu);
        file.add(openmenu);
        file.add(savemenu);
        file.add(exitmenu);
        m.add(file);
        return m;
    }
    /**
     * random number generator
     * @param factor
     * @return
     */
    public int getRandomInt(int factor){
            int r = (int) (Math.random() * factor);
            return r;
    }
    

    /**
     * DrawBoard class
     */
    public class DrawBoard extends JPanel {
        /**
         * Create a new BufferedImage
         */
        public void createNewImage(){
            bimaster = new BufferedImage(DEFAULTWIDTH, DEFAULTHEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            g2d = (Graphics2D)bimaster.getGraphics();
        } 

        /**
         * Clear the canvas
         */
        public void clearImage(){
            createNewImage();
            initstate = true;
            repaint();
        }

        /**
         * render image into the frame
         * @param g
         */
        public void renderImage(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Image scaledImage = img.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
            g2d.drawImage(scaledImage,0,0,this);
}
        /**
         * get random color
         * @return
         */
        public Color setColor(){
            int r = getRandomInt(256);
            int gr = getRandomInt(256);
            int b = getRandomInt(256);
            return new Color(r,gr,b);
        }

        /**
         * get rectangle
         * @param scale
         * @return
         */
        public Rectangle createRect(int scale){
            return new Rectangle(xpos-(scale /2),ypos-(scale /2),scale,scale);
        }

        /**
         * get triangle
         * @param scale
         * @return
         */
        public Polygon createTriangle(int scale){
            Point p1 = new Point(0, 0);
            Point p2 = new Point(scale, 0);
            Point p3 = new Point((scale/2), (3 * scale) / 2);
            int[] xs = { p1.x+xpos-(scale /2), p2.x+xpos-(scale /2), p3.x+xpos-(scale /2) };
            int[] ys = { p1.y+ypos-(scale /2), p2.y+ypos-(scale /2), p3.y+ypos-(scale /2) };
            return new Polygon(xs,ys,xs.length);
        }

        public Polygon createPentagon(int scale){
            Point p1 = new Point ((scale/2),0);
            Point p2 = new Point (scale,(int)(0.374 * scale));
            Point p3 = new Point ((int)(0.825 * scale),scale);
            Point p4 = new Point ((int)(0.175 * scale),scale);
            Point p5 = new Point (0,(int)(0.374 * scale));
            int[] xs = { p1.x+xpos-(scale /2), p2.x+xpos-(scale /2), p3.x+xpos-(scale /2), p4.x+xpos-(scale /2), p5.x+xpos-(scale /2) };
            int[] ys = { p1.y+ypos-(scale /2), p2.y+ypos-(scale /2), p3.y+ypos-(scale /2), p4.y+ypos-(scale /2), p5.y+ypos-(scale /2) };
            return new Polygon(xs,ys,xs.length);
        }

        /**
         * Override the paint method
         * @param g
         */
        @Override
        public void paint(Graphics g) {
            if (bimaster == null) {
                createNewImage();
            }
            if (newstate) {
                renderImage(g2d);
                newstate = false;
            }
            super.paint(g);
            g.drawImage(bimaster, 0, 0, null);
            Color currentcolor = setColor();
            g2d.setColor(currentcolor);
            int scale = getRandomInt(30);
            switch (mode) {
                case 0: g2d.fillOval(xpos-(scale/2),ypos-(scale /2),scale,scale);break;
                case 1: g2d.drawOval(xpos-(scale/2),ypos-(scale /2),scale,scale);break;
                case 2: g2d.fill(createRect(scale));break;
                case 3: g2d.draw(createRect(scale));break;
                case 4: g2d.fillPolygon(createTriangle(scale));break;
                case 5: g2d.drawPolygon(createTriangle(scale));break;
                case 6: g2d.fillPolygon(createPentagon(scale));break;
                case 7: g2d.drawPolygon(createPentagon(scale));break;
                default: break;
            }
        }
    }
}
