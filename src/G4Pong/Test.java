/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package G4Pong;

import Engine.Geometry.Circle;
import Engine.Geometry.Rectangle;
import Engine.Vector2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * General Purpose test bench - anything goes here
 * @author muhammed.anwar
 */
public class Test {
    public static void main(String [] args)
    {
          Fractal koch = new Fractal();
          System.out.println(koch.expand(3));
    }
    
}
class Fractal
{
    Map<String, String> variables;
    Map<String, String> constants;
    
    public Fractal(){
        variables = new HashMap<String, String>();
        constants = new HashMap<String, String>();
        variables.put("F", "F");
        constants.put("+", "+");
        constants.put("-", "-");        
    }
    public String expand(int n){
        String value = "F";
        for(int i =0; i <n;i++){            
            value =value.replaceAll("F", "F+F-F-F+F");            
        }        
        return value;
    }
}