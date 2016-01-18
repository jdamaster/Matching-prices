/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Julian David Arango
 */
/**
 * This class extract the product name and model from the product name field
 * It will be separate because in the listing the name and the model of the product
 * could be separate, like this: 
 * Fujifilm Camera model Tx-234
 */
public class Sanitizer {
    private static final String delim="_-";
    /**
     * This method takes the product_name (unique for each product) and let only
     * the product name, without the model, and without the family (in case that 
     * the product has family)
     * @param productName id unique of the product,
     * @param model model of the product (present in the product name)
     * @param family Optional family of the product, if the product has no family
     * the family arg will be null
     * @return a json object with the name of the product (separated with blanks) and the model
     */
    public static JSONObject Sanitize(String productName,String model,Object family){
        JSONObject produc=new JSONObject();
        //Extracting the model of the product
        productName=Remove(productName,model);
        if(family!=null){
            productName=Remove(productName,family.toString());
        }
        StringTokenizer token=new StringTokenizer(productName,delim);
        String name=token.nextToken();
        while(token.hasMoreTokens()){
            name=name+" "+token.nextToken();
        }
        produc.put("product", name);
        produc.put("model",model);
        return produc;
    }
    /**
     * This method parse a json object {(name:Array),(name2:Array2),...}and give a
     * list of json objects like (product_name:name)(Listings:array) 
     * @param result a json object compound of products names and his respective arrays.
     * @return a list of the text representation of the array
     */
    public static List ParseResult(JSONObject result){
        List<String> iter=new ArrayList<>();
        Iterator<?> rows=result.keySet().iterator();
        JSONObject parsedRow;
        while(rows.hasNext()){
            parsedRow=new JSONObject();
            String product = (String)rows.next();
            parsedRow.put("product_name", product);
            parsedRow.put("listings",(JSONArray)result.get(product));
            iter.add(parsedRow.toJSONString());
        }
        return iter;
    }
    /**
     * this method remove the "words" present in toRemove from the string base
     * the words in the toRemove string could be separathed by the delimiters 
     * - / \ _ or blank, if the words are together are taken like a single word
     * @param base String base, that could have one or more ocurrences of the 
     * words in toRemove string 
     * @param toRemove String to remove from base
     * @return result string after remove the words in toRemove sstring
     */
    static private String Remove(String base,String toRemove){
        //Case when there is exact matching
        if(base.contains(toRemove)){
            return base.replaceAll(toRemove,"");
        }
        //Case when the model could be tokenized 
        String delimiter="-/\\_ ";
        StringTokenizer st=new StringTokenizer(toRemove,delimiter);
        while(st.hasMoreTokens()){
            base=base.replaceAll(st.nextToken(),"");
        }
        return base;
        
    }
}
