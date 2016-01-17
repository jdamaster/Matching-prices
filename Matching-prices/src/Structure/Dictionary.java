/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structure;

import Tools.Sanitizer;
import java.util.List;
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

/**
 * The dictionary has the following structure
 * {"product1":{"model1":"product_name","model2":"product_name"},"product2"...
 * 
 * I choose this structure because is more efficient searching, that's because
 * when an item of the list is not a product, all its models are avoided in the 
 * dictionary, and look for another product.
 */
public class Dictionary {
    private JSONObject dictionary;

    public Dictionary(List<String> list) {
        initDictionary(list);
    }
    
    private void initDictionary(List<String> list){
        //Product form file
        JSONObject product;
        //Product Sanitized
        JSONObject productSanitized;
        JSONParser parser=new  JSONParser();
        dictionary=new JSONObject();
        for(String json:list){
            
            try {
                product=(JSONObject)parser.parse(json);
            } catch (ParseException ex) {
                Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, "There was a parse error when trying to parse: {0}", json);
                product=null;
            }
            if(product!=null){
                //Extract the product name and model
                productSanitized=Sanitizer.Sanitize(product.get("product_name").toString(),product.get("model").toString());
                /**
                 * If the product exist in the dictionary, then we insert the model in the dictionary,
                 * else the product and model will be added to the dictionary
                 */
                
                if(dictionary.containsKey(productSanitized.get("product"))){
                    JSONObject models=(JSONObject) dictionary.get(productSanitized.get("product"));
                    models.put(productSanitized.get("model"),product.get("product_name"));
                    dictionary.replace(productSanitized.get("product"), models);
                }else{
                    JSONObject dictionaryEntry = new JSONObject();
                    JSONObject models = new JSONObject();
                    models.put(productSanitized.get("model"), product.get("product_name"));
                    dictionaryEntry.put(productSanitized.get("product"), models);
                }
                        
            }
        }
        
    }
    

    public JSONObject getDictionary() {
        return dictionary;
    }
}
