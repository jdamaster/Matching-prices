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
     * 
     * @param productName 
     * @param model
     * @return 
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
