/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

/**
 * Represents a color object with red, green, blue and alpha values
 * The range of the values goes from 0 - 255.
 * @author Muhammed Anwar
 */

public class Color {    
    public static final Color WHITE = new Color(255,255,255);   
    public static final Color LIGHT_GRAY = new Color(192,192,192);    
    public static final Color GRAY = new Color(128,128,128);
    public static final Color DARK_GRAY = new Color(64,64,64);  
    public static final Color BLACK = new Color(0,0,0);    
    public static final Color RED = new Color(255,0,0);    
    public static final Color PINK = new Color(255,175,175);    
    public static final Color ORANGE = new Color(255,200,0);    
    public static final Color YELLOW = new Color(255,255,0);    
    public static final Color GREEN = new Color(0,255,0);    
    public static final Color MAGENTA = new Color(255,0,255);    
    public static final Color CYAN = new Color(0,255,255);    
    public static final Color BLUE = new Color(0,0,255);
        
    private int r,g,b,a;
    
    public Color(int r, int g, int b){
        this(r,g,b,255);
    }
    public Color(int r, int g, int b, int a){
        setColor(r,g,b,a);
    }
    public Color (int value){
        this(value>>16 &0xFF, value>>8 & 0xFF,value & 0xFF,value>>24);  
    }
    public Color(Color c){
        this(c.a,c.g,c.b,c.a);
    }
    public void setRed(int value){this.a =value;}
    public void setGreen(int value){this.g=value;}
    public void setBlue(int value){this.b= value;}
    public void setAlpha(int value){this.a = value;}
    public int getRed(){return this.r;}
    public int getGreen(){return this.g;}
    public int getBlue(){return this.b;}
    public int getAlpha(){return this.a;}
    public void setColor(int r,int g,int b, int a){
        this.r = r;
        this.g = g;
        this.b = b;
        if(a <0)this.a = 255;
        else this.a = a;
    }    
    
    /**
     * Returns the integer represented by the color data. Color is encoded as:
     * ((a&0x0ff)<<24)|((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
     * @return the integer representing the color
     */
    public int getRGB(){
        return ((a&0x0ff)<<24)|((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }
    
    public float[] getColorComponents(){
        return new float[]{r,g,b,a};
    }
    
    public String toString(){
        return "[" + r +", " +g+", "+b+", "+a+"]";
    }
        
}
