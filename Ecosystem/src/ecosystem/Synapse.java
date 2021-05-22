
package ecosystem;

import java.io.Serializable;


public class Synapse implements Serializable{

    private static final long serialVersionUID = 1L;
    
 
    
    double weight;
    Node toNode;
    boolean active;
    
    
    Synapse(Node n){
        active = true;
        weight = D.random(-1, 1);
        toNode = n;
    }
    
    public Node getToNode(){
        return toNode;
    }
    
    
    public void setToNode(Node n){
        toNode = n;
    }
    
    
    
    public double getWeight(){
        return weight;
    }
    
    
    public void setWeight(double d){
        weight = d;
    }
    
    
    
    
    public boolean isActive(){
        return active;
    }
    
    
    
    public void setActive(boolean b){
        active = b;
    }
    
    
   
}
