/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structure;

import Tools.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
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
public class Result {
    static JSONObject dictionary;
    
    public static JSONObject MakeResult(String productPath,String listingPath){
        productPath="D:\\products.txt";
        listingPath="D:\\listings.txt";
        JSONArray rowArray;
        JSONObject aux;
        JSONParser parser= new JSONParser();
        JSONObject result=new JSONObject();
        JSONObject listingRow;
        List<String> products=Reader.Read(productPath);
        List<String> listings=Reader.Read(listingPath);
        //Build the dictionary
        dictionary=(new Dictionary(products)).getDictionary();
        
        //Init Result object;
        /**
         * The following product will be a default product, when a match 
         * could not be done, then the item go to the "Missed Matches" list
         */
        result.put("Missed Matches", new JSONArray());
        
        for(String json:products){
            try {
                aux=(JSONObject)parser.parse(json);
            } catch (ParseException ex) {
                Logger.getLogger(Result.class.getName()).log(Level.SEVERE, "There was a parse error when trying to parse the following product: {0}", json);
                aux=null;
            }
            if(aux!=null){
                result.put(aux.get("product_name"), new JSONArray());
            }
        }
          
        
        for(String json:listings){
            try {
                listingRow=(JSONObject)parser.parse(json);
            } catch (ParseException ex) {
                Logger.getLogger(Result.class.getName()).log(Level.SEVERE, "There was a parse error when trying to parse the following item: {0}", json);
                listingRow=null;
            }
            if(listingRow!=null){
                String title=listingRow.get("title").toString();
                String position= Search(title);
                rowArray=(JSONArray)result.get(position);
                rowArray.add(listingRow);
                result.replace(position, rowArray);
            }
            
            
        }
        
        return result;
    }
    private static String Search(String title){
        title=title.toUpperCase();
        Iterator<?> products=dictionary.keySet().iterator();
        //The variable containProduct change to true when at less one
        //word of the name of a product is present in the title of a item
        boolean containProduct;
        while(products.hasNext()){
            String product=(String)products.next();
            String auxProduct=product.toUpperCase();
            containProduct=true;
            StringTokenizer st=new StringTokenizer(auxProduct);
            while(st.hasMoreTokens()){
                if(!title.contains(st.nextToken())){
                    containProduct=false;
                }
            }
            
            if(containProduct){
                JSONObject models=(JSONObject)dictionary.get(product);
                Iterator<?> modelsList=models.keySet().iterator();
                while(modelsList.hasNext()){
                    String model=(String)modelsList.next();
                    String modelAux=model.toUpperCase();
                    if(title.contains(modelAux)){
                        return models.get(model).toString();
                    }
                }
            }
        }
        return "Missed Matches";
    }
    
    
}
