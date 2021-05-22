package ecosystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Node implements Serializable{

    private static final long serialVersionUID = 1L;
    
 
    double storedVal;
    List<Synapse> synapses = new ArrayList<>();
    boolean active;
  
    
    Node(){
        active = true;
    }
    
    public void addSynapse(Synapse s){
        synapses.add(s);
    }
    
    public List<Synapse> getSynapses(){
        return synapses;
    }
    
    public void setSynapses(List<Synapse> s){
        synapses = s;
    }
    
    
    public double getStoredVal(){
        return storedVal;
    }
    
    
    public void setStoredVal(double d){
        storedVal = d;
    }
    
    
    public boolean isActive(){
        return active;
    
    }
    
    public void setActive(boolean b){
        active = b;
    }
    
    
    
   
    
    
    public void fowardValues(){
        for(Synapse synapse:synapses){
            
            Node toNode = synapse.getToNode();

            //foward values only if node is active as well as the synapse
            if(toNode.isActive() && synapse.isActive()){
                toNode.setStoredVal((storedVal * synapse.weight) + toNode.getStoredVal());
            }

        }
      
       
    }
    
    
    
    public void flushValues(){
        storedVal = 0;
    }
    
    
    public void activationFunction(){
        storedVal = Math.tanh(storedVal);
    }
    
 
    
}
