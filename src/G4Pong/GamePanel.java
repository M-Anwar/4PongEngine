/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package G4Pong;

import Engine.Audio.Audio;
import Engine.Audio.JavaAudio;
import Engine.Color;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import Engine.Java2DGraphics;
import Engine.Keys;
import Engine.Mouse;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Platform dependant game screen. Contains the main game loop and event handling.
 * @author muhammed.anwar
 */
public class GamePanel extends JPanel implements Runnable,MouseListener, MouseMotionListener, KeyListener
{
    //Width and Height of the Panel
    public static int WIDTH =1024;
    public static int HEIGHT = 720;  
    
    //Game loop speeds
    public static final int NORMAL =    100000000;
    public static final int SLOMO =     500000000;
    public static final int FAST =      10000000;
    
    //Game-loop variables
    protected static int speed = NORMAL;
    private Thread thread;
    protected boolean running;
    protected final int FPS = 60;
    private final long targetTime = 1000/FPS;
    
    //FPS information
    protected float actualFPS;
    protected float averageFPS;
    protected static final int SAMPLES = 60;
    private float[] averageFPSSamples = new float[SAMPLES+1];
    private int tickIndex=0;
    private float tickSum =0;
    
    
    //Graphics Variables
    private BufferedImage image;    
    private Engine.Graphics g;  
    
    //The game state manager resposible for delegating drawing and updating
    protected GameStateManager gsm;
    protected GameState initial;
    
    //Audio Engine Handle
    protected static Audio gameAudio;
    
    //Parent JFrame
    public static JFrame parent;
    
    public GamePanel(){
        this(null);
    }
    public GamePanel(GameState initial)
    {               
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setFocusable(true);
        this.requestFocus(); 
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                WIDTH = getWidth();
                HEIGHT = getHeight();                
                updateSize(WIDTH,HEIGHT);
            }
          });
        this.initial = initial;
        gsm = new GameStateManager(this.initial);    
    }
    private void updateSize(int argWidth, int argHeight) {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();        
        g = new Java2DGraphics(g2d);
    }
    @Override
    public void addNotify() {
        super.addNotify();
        if(thread == null) {			
                addMouseListener(this);	
                addMouseMotionListener(this);
                addKeyListener(this);
                thread = new Thread(this);
                thread.setName("G4PMainEngineThread");
                thread.start();
                parent = (JFrame)SwingUtilities.getWindowAncestor(this);
        }
    }
    
    private void init() {        
        System.setProperty("sun.java2d.opengl","true");
        updateSize(WIDTH,HEIGHT);       
        running = true;	        
        gameAudio = new JavaAudio();
            try {
                //Load Audio here - Might be changed to a loader in the future                
                gameAudio.load("/res/button.wav","BUTTON");               
                gameAudio.setMute(true);
            } catch (Exception ex) {
                System.out.println("Unable to load audio: " + ex.getMessage());
            }               
    }    
    
    @Override
    public void run() {
        init();
		
        long start;
        long elapsed=0;
        long wait;
        long delta=0;
        
        //Main game loop
        while(running) {
            start = System.nanoTime();
            
            update(delta);            
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if(wait < 0) wait = 0;
            try {Thread.sleep(wait);}
            catch(Exception e) {e.printStackTrace();}
            
            delta = System.nanoTime()- start;
            actualFPS = (1000000000/(float)delta); 
            
            //Average FPS calculation
            tickSum -=averageFPSSamples[tickIndex];
            tickSum += actualFPS;
            averageFPSSamples[tickIndex] = actualFPS;
            if(++tickIndex>SAMPLES) tickIndex =0;
            averageFPS = tickSum/SAMPLES;
        }        
    }
    
    public void update(long delta)
    {       
        float deltaf = delta;
        deltaf = deltaf/speed;       
        
        //Update code goes here   
        gsm.update(deltaf);
        Mouse.update();
        Keys.update();
    }
    boolean showDebug = false;
    public void draw()
    { 
        g.setColor(Color.BLACK.getRGB());       
        g.fillRect(0, 0, WIDTH, HEIGHT);                
        gsm.draw(g);        
        
        if(showDebug){
            g.setColor(new Color(0,0,0,128).getRGB());
            g.fillRect(10, 10, 150, 30);
            g.setColor(Color.WHITE.getRGB());
            g.drawRect(10,10,150,30);
            g.setFont("Arial", Graphics.BOLD, 17);
            g.drawString("FPS: "+ String.format("%.1f", averageFPS), 20, 30);
        }
                
    }
    public void drawToScreen()
    {
        Graphics2D g2 = (Graphics2D)getGraphics();       
        g2.drawImage(image, 0, 0, null);               
        g2.dispose();       
    }
    
    /**
     * Set the game loop speed. Scales the value of the update delta parameter
     * @param playSpeed GamePanel.NORMAL, GamePanel.FAST or GamePanel.SLOMO
     */
    public static void setPlaySpeed(int playSpeed){speed = playSpeed;}
    
    public void setInitialState(GameState init){        
        if (this.initial==null) initial = init;
        this.gsm.setState(init);
    }
    /**
     * Returns the audio engine of the game
     * @return 
     */
    public static Audio getAudio(){return gameAudio;}
    
    //Mouse Events
    @Override
    public void mousePressed(MouseEvent me) {         
         Mouse.setAction(Mouse.PRESSED);         
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        Mouse.setAction(Mouse.RELEASED);
    }   
    @Override
    public void mouseDragged(MouseEvent me) {
        Mouse.setAction(Mouse.PRESSED);
	Mouse.setPosition(me.getX(), me.getY());
    }
    @Override
    public void mouseMoved(MouseEvent me) {
        Mouse.setPosition(me.getX(), me.getY());           
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        Keys.keySet(ke.getKeyCode(),true);
        Keys.keyTyped(ke);        
        if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_D){showDebug=!showDebug;}
    }
    @Override
    public void keyReleased(KeyEvent ke) {
         Keys.keySet(ke.getKeyCode(),false);         
    }
    
    public void mouseEntered(MouseEvent me) {}    
    public void mouseExited(MouseEvent me) {}
    public void mouseClicked(MouseEvent me) {}
    public void keyTyped(KeyEvent ke){}
    

   
}
