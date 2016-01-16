/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structure;

import java.util.ArrayList;
import org.json.simple.JSONArray;

/**
 *
 * @author Julian David A.
 */
/**
 * This class represents a product and its list of models/variations
 * A product will be Sony Cybershot
 * And its models S01 , M23, etc
 */
public class Node {
    private String product;
    private ArrayList<String> References;
    JSONArray j1=new JSONArray();
    
    public Node(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public ArrayList<String> getReferences() {
        return References;
    }

    public void setReferences(ArrayList<String> References) {
        this.References = References;
    }
}
