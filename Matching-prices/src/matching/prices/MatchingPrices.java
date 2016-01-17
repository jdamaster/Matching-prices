/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matching.prices;

import Structure.Result;
import Tools.Sanitizer;
import Tools.Writer;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Julian David Arango
 */
public class MatchingPrices {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JSONObject resultado = Result.MakeResult(null, null);
        Writer.Write(null, Sanitizer.ParseResult(resultado));
        
                
    }
    
}
