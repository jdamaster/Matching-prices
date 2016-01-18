/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matching.prices;

import Structure.Result;
import Tools.Sanitizer;
import Tools.Writer;
import org.json.simple.JSONObject;

/**
 *
 * @author Julian David Arango
 */
public class MatchingPrices {

    /**
     * @param args the command line arguments
     * args[0] path to products.txt
     * args[1] path to listings.txt
     * args[2] path to result folder 
     */
    public static void main(String[] args) {
        
        if((args[0]!=null )&&(args[1]!=null)&&(args[2]!=null)){
            JSONObject resultado = Result.MakeResult(args[0], args[1]);
            Writer.Write(args[2], Sanitizer.ParseResult(resultado));
        }else{
            System.out.println("Correct usage: \n"
                    + "java Matching-prces.jar <path-to-products.txt> <path-to-listings.txt> <path-to-result-folder>");
        }
                
    }
    
}
