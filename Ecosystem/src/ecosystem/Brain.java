
package ecosystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Brain implements Serializable{

    private static final long serialVersionUID = 1L;
    List<List<Node>> layers = new ArrayList<>();
    
     public Brain(int initialSize[] ){
         createNodes(initialSize);
         createConnections();
    
     }
    
     

     private void createNodes(int layer_size[]){
       
        for(int size:layer_size){
            List<Node> nodes = new ArrayList<>();
            layers.add(nodes);
            
            for(int n=0;n<size;n++){
                nodes.add(new Node());
            }

        }
         
         
     }
     
     
     
     private void createConnections(){
         int layer=0;
         for( List<Node> nodes:layers){
            
             
            if(layer <layers.size()-1){

                for(int n=0;n<nodes.size();n++){
                    
                    //there is only a chance that connection will be made
                    if(D.choose()){
                        List<Node> nodeList = layers.get((int)D.random(layer+1,layers.size()));
                        Node connectingNode = nodeList.get((int)D.random(0,nodeList.size()));

                        Synapse connectSynapse = new Synapse(connectingNode);
                        nodes.get(n).addSynapse(connectSynapse);
                    }
                }
                
            }
            
            
            layer++;
            
         }
         
      
       
       
     }
     
     
     public void setLayer(List<List<Node>> nn){
         layers = nn;
     }
     
     
     
     public List<List<Node>> getLayer(){
         return layers;
     }
    
     
     
     
     //do the forward feeding
     public double[] runBrain(double [] inp){
         
        double [] outputs = new double[layers.get(layers.size()-1).size()];
        
        //put the input value in input node
        for(int i=0;i<inp.length;i++){
            layers.get(0).get(i).setStoredVal(inp[i]);
        } 
        
         
        for(int layer=0;layer<layers.size();layer++){
            
            List<Node> nodeList = layers.get(layer);
            
            for(int n=0;n<nodeList.size();n++){
                Node node = nodeList.get(n);
                //use activation function for inputs except for input layer
                if(layer>0){
                    node.activationFunction();
                }
                
                
                
                if(layer<layers.size()-1){
                    node.fowardValues();
                    
                }
                else{
                    outputs[n] = node.getStoredVal();
                }
                
                
                 //don't forget this !!!
                node.flushValues(); 
                
            }
            
            
            
        }
        
        return outputs;
    }
 
     
     
     
     
     //mutating brain
     public void brainMutate(double mutation_rate,double mutation_probability,int mutation_freq){
         new BrainMutator(layers, mutation_rate, mutation_probability, mutation_freq).mutate();
     }
     
     
     
     
     
     
     
     public void crossoverBrain(Brain brain1 , Brain brain2 , double crossover_pro){
         layers =  BrainMutator.crossoverBrain(brain1, brain2 , crossover_pro);
      
     }
        
         
        
        
     
     
   
     
     
    
     
     
     
}
