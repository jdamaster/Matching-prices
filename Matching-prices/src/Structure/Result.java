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
    public static JSONObject MakeResult(String productPath,String listingPath){
        productPath="D:\\products.txt";
        listingPath="D:\\listings.txt";
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
