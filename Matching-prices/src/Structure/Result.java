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
    static JSONObject productsDB;
    /**
     * The MakeResult method initializes a dictionary with the products and models,
     * then read line per line of the listings file and then put it in the result Json
     * @param productPath path to the products text file
     * @param listingPath path to the listings text file
     * @return  return a json object with the following structure
     * {"product_name":[item1,item2]}, the mismatched products apear 
     * in "Missed Matches" product name
     */
    public static JSONObject MakeResult(String productPath,String listingPath){
        JSONArray rowArray;
        JSONObject aux;
        JSONParser parser= new JSONParser();
        JSONObject result=new JSONObject();
        JSONObject listingRow;
        productsDB=new JSONObject();
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
                productsDB.put(aux.get("product_name"), aux);
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
    /**
     * This method Search iterate the dictionary  entries looking
     * for a similar item that the item described in the title string
     * when a similar product could'nt be founded the metod return 
     * the name of the default product list
     * @param title a string that contain a product name and a model of the product
     * @return the name of the product present in the title
     */
    private static String Search(String title){
        Iterator<?> products=dictionary.keySet().iterator();
        while(products.hasNext()){
            String product=(String)products.next();  
            if(Contain(title, product)){
                JSONObject models=(JSONObject)dictionary.get(product);
                Iterator<?> modelsList=models.keySet().iterator();
                while(modelsList.hasNext()){
                    String model=(String)modelsList.next();
                    
                   if(Contain(title, model)){
                        return models.get(model).toString();
                    }
                }
            }
        }
        return "Missed Matches";
    }
    /**
     * The contain method try to search the toFind string in the base
     * string, the to find string could be one or more words separated by one 
     * or more delimiters, and be present in any order in the base string
     * @param base base string that could contain the toFind String
     * @param toFind String to find in base string.
     * @return true if each word of toFind is present in base String, else false
     */
    static private boolean Contain(String base,String toFind){
        String delimiter="-/\\_ ";
        base=base.toUpperCase();
        toFind=toFind.toUpperCase();
        StringTokenizer st =new StringTokenizer(toFind,delimiter);
        
        while(st.hasMoreTokens()){
            if(!base.contains(st.nextToken())){
                return false;
            }
        }
        return true;
    }
 
    
    
}
