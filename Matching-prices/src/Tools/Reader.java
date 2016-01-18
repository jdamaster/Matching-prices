/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Julian David Arango
 */

public class Reader {
    /**
     * This metod read a file and make a list with each line
     * @param path the path of the text file
     * @return A string list with each line of the file.
     */
    public static List<String> Read(String path){
        try {
            return Files.readAllLines(Paths.get(path));
            
        } catch (IOException ex) {
            System.err.println("There was an path error, the file: "+path+" does'nt exist or i haven´t read permission");
            return null;
        }
        
        
    }
}
