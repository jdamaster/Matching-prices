/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julian David Arango
 */
public class Writer {
    /**
     * The method writhe in a given path a file
     * which each Line is a string of the iter List
     * @param path path to the directory when the file will be created
     * @param iter String List to write into the file
     */
    public static void Write(String path,List<String> iter){
        if(path.endsWith("/")){
            path=path+"result.txt";
        }else{
            path=path+"/result.txt";
        }
       
        try {
            Files.deleteIfExists(Paths.get(path));
            Files.createFile(Paths.get(path));
            Files.write(Paths.get(path), iter);
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, "Error while trying to create the result file");
        }
    }
}
