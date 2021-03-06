/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package G4Pong;


import Engine.Java2DImage;
import Engine.Utils;
import GameStates.TestBench.MTestBench;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author muhammed.anwar
 */
public class Main extends JFrame{
    public static final boolean debug = true;
    public Main()
    {           
        this.setTitle("4Pong");
        Image icon = ((Java2DImage)Utils.loadJava2DImage("/icon.png")).image;
        this.setIconImage(icon);
        GamePanel panel = new GamePanel();
        panel.setInitialState(new MTestBench());
        this.add(panel);           
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setUndecorated(!debug);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
         
        try {
            //custom cursor            
            BufferedImage i = ImageIO.read(ClassLoader.class.getResourceAsStream("/mouse.png"));
            Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(i, new Point(0, 0), "mouse");
            this.setCursor(c);
        } catch (IOException ex) {}           
    }
    public static void main(String[] args) {        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);                
            }
        });  
    }    
}
