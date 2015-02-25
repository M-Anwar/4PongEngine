/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.*;
import javax.imageio.ImageIO;

/**
 * Basic Utilities that are useful for IO functionalities.
 * @author muhammed.anwar
 */
public class Utils 
{      
    /**
     * Reads the text from a file in the local classpath folder (src). 
     * @param fileName the local folder path
     * @return the string representation of the text read seperated by new lines
     * @throws IOException 
     */
    public static String readTextFromLocalFile(String fileName) throws IOException
    {
        java.lang.StringBuilder result = new java.lang.StringBuilder("");	
	try (Scanner scanner = new Scanner(ClassLoader.class.getResourceAsStream(fileName))) {
 
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result.append(line).append("\n");
		}
 
		scanner.close(); 
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return result.toString();
    }
    
    /**
     * Reads the text from a file specified by the path. 
     * @param fileName the local folder path
     * @return the string representation of the text read seperated by new lines
     * @throws IOException 
     */
    public static String readTextFromFile(String fileName) throws IOException
    {
        java.lang.StringBuilder result = new java.lang.StringBuilder("");	
	try (Scanner scanner = new Scanner(new File(fileName))) {
 
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result.append(line).append("\n");
		}
 
		scanner.close(); 
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return result.toString();
    }
    
    /**
     * Write to a string to a local text file - overwrites existing file or
     * creates a new one.
     * @param path - the path of the local file to write to
     * @param txt - the text to write to the file
     * @throws Exception - Throws Exception if writeToFile fails 
     */
    public static void writeToFile(String path, String txt) throws Exception
    {        
        PrintWriter writer = null;
        try{            
            writer = new PrintWriter(new BufferedWriter(new FileWriter(path,false)));
            writer.println(txt);
        }finally{
            if(writer!= null){
                writer.close();
            }
        }        
    }           
   
    
    /**
     * Load an image resource from the classpath of the project
     * @param s - the local path of the resource
     * @return - a generic image representing the loaded resource.
     */
    public static Image loadJava2DImage(String s) {
        Java2DImage image = null;
        try {
            image = new Java2DImage(ImageIO.read(ClassLoader.class.getResourceAsStream(s)));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    /**
     * Serialize an object to a file
     * @param filename - the file to save the serialized object
     * @param object - the object to serialize
     * @throws Exception - Any exception thrown by the operation
     */
    public static void writeObjectToFile(String filename, Object object) throws Exception {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
 
        try {
            fos = new FileOutputStream(new File(filename));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
    
    /**
     * Reads a serialized object from a file
     * @param filename - the file to read the serialized object from
     * @return - An object from the read file ( needs to be parsed into another
     * object type if necessary)
     * @throws Exception - Any exception thrown by the operation
     */
    public static Object readObjectFromFile(String filename) throws Exception {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
 
        try {
            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return object;
    }
}
