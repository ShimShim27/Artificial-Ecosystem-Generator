
package ecosystem;

/*
    Class for brain mutation and crossovers
*/



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BrainMutator {

    private List<List<Node>> layers;
    private double mutation_rate;
    private double mutation_probability;
    private int mutation_freq;
    
    BrainMutator( List<List<Node>> layers , double mutation_rate,double mutation_probability,int mutation_freq){
        this.layers = layers;
        this.mutation_rate = mutation_rate;
        this.mutation_probability = mutation_probability;
        this.mutation_freq = mutation_freq;
    }
    
    
    
    
    public void mutate(){
        int mutations[] = {1,2,3,4,5,6,7,8,9};
        
        for(int i=0;i<mutation_freq;i++){
            if(D.chanceHappened(mutation_probability)){
               chooseMutation(mutations[(int)D.random(0, mutations.length)]);
            }
           
        }
        
    }
    
    

    
    
    private void chooseMutation(int which_mutation){
         switch(which_mutation){
            case 1:
                addNodeInBetween();
                break;

            case 2:
                addConnectSynpase();
                break;

            case 3:
                deleteSynapse();
                break;

            case 4:
                 mutateWeight();
                 break;

            case 5:
                deleteRandomNode();
                break;

            case 6:
                addRandomNode();
                break;


            case 7:
                toggleNodeState();
                break;


            case 8:
                toggleSynapseState();
                break;


            case 9:
                randomWeight();
                break;
         }

     }
    
    
    
    
    
    
     private void addNodeInBetween(){

        //get a random synapse list first
        int whichLayer = (int)D.random(0,layers.size()-1);
        List<Node> layer = layers.get(whichLayer);
        Node fromNode = layer.get((int)D.random(0, layer.size()));
        List<Synapse> nodeSynapse = fromNode.getSynapses();





        if(nodeSynapse.size()>0){

            Synapse fromSynapse = nodeSynapse.get((int)D.random(0,nodeSynapse.size()));
            int firstNodeLayer = whichLayer;


            //do the oldNode insertion between two nodes
            Node oldToNode = fromSynapse.getToNode();
            Node newNode = new Node();
            fromSynapse.setToNode(newNode);

            Synapse newSynapse = new Synapse(oldToNode);
            newSynapse.setToNode(oldToNode);
            newNode.addSynapse(newSynapse);




            //getting the layer position
            int oldLayerIndex = -1;


            for(List<Node> nodeList:layers){
                int oldNodeIndex = nodeList.indexOf(oldToNode);


                if(oldNodeIndex!=-1){
                    oldLayerIndex = layers.indexOf(nodeList);
                    break;
                }


            }




           /*
            The Logic in adding nodes in between
            - when adding nodes , its layer should be strictly between the first oldNode and old to oldNode
            - input and output nodes are fix , so nothing will be addTheNode on the first and last layer
            - add new oldNode in the middle if there are only input and output layer
            //- (There is a 50% chance when maximum brain size is not yet reach to add new layer) - not yet
            - 100% chance to add new layer if from oldNode's layer has a distance of one to old to oldNode's layer
                and the brain maximum size is not yet reach
            - If the above are not satisfied , then no changes where made

            */


           //layer position in case new oldNode will be addTheNode to an existing layer
            int layerPos = (int)D.random(firstNodeLayer+1 , oldLayerIndex-1);

            boolean oneLayerDist = oldLayerIndex - firstNodeLayer <= 1;
            boolean addNewLayer = D.choose();



            if(layers.size()<=2){
                 List<Node> newLayer = new ArrayList<>();
                 newLayer.add(newNode);
                 layers.add(1,newLayer);
             }
             else if(addNewLayer
                     || (!addNewLayer && oneLayerDist)){

                 List<Node> newLayer = new ArrayList<>();
                 newLayer.add(newNode);
                 layers.add(oldLayerIndex,newLayer);

             }
             else if(!addNewLayer && layerPos!=firstNodeLayer){
                 layers.get(layerPos).add(newNode);

             }

             else{
                 //undo the chsnges
                  fromSynapse.setToNode(oldToNode);
             }






        }









    }




     //deleting random oldNode and its connection
     private void deleteRandomNode(){
         if(layers.size()>2){
             int layerPos =(int) D.random(1, layers.size()-1);
             List<Node> nodeLayer = layers.get(layerPos);
             Node targetNode = nodeLayer.get((int)D.random(0, nodeLayer.size()));

             //delete the target oldNode
             nodeLayer.remove(targetNode);


             //empty layer will be deleted
             if(nodeLayer.size()<=0){
                 layers.remove(nodeLayer);
             }




             //check for any synapse connection to the deleted oldNode on the previous layers
             for(int i=0;i<layerPos;i++){
                 List<Node> nLayer = layers.get(i);
                 
                 for(Node node:nLayer){
                     List<Synapse> nodeSynapse = node.getSynapses();
                     Synapse toBeRemoved = null;


                     //checking for synapse that was connected to the deleted oldNode
                     for(Synapse s:nodeSynapse){
                         if(s.getToNode().equals(targetNode)){
                             toBeRemoved = s;
                             break;
                         }

                     }

                     //remove the synapse that was connected to the deleted oldNode
                     nodeSynapse.remove(toBeRemoved);

                 }
             }




         }
     }

     
     //adding random oldNode
     private void addRandomNode(){
         if(layers.size()>2){
             int layerPos =(int) D.random(1, layers.size()-1);
             layers.get(layerPos).add(new Node());

         }



     }


     //toggling oldNode state
     private void toggleNodeState(){

        List<Node> whichLayer = layers.get((int)D.random(1,layers.size()-1));
        Node node = whichLayer.get((int)D.random(0,whichLayer.size()));


        if(layers.size()>2){
            boolean active;

            if(node.isActive()){
                active = false;
            }
            else{
                active = true;
            }
            node.setActive(active);
        }


     }


    //toggling synapse state
     private void toggleSynapseState(){
         //get a synapse list first
        List<Node> whichLayer = layers.get((int)D.random(1,layers.size()-1));
        Node whichNode = whichLayer.get((int)D.random(0, whichLayer.size()));
        List<Synapse> synapses = whichNode.getSynapses();


        if(synapses.size() > 0){
            Synapse synapse = synapses.get((int)D.random(0, synapses.size()));
            boolean active;

           if(synapse.isActive()){
               active = false;
           }
           else{
               active = true;
           }

           synapse.setActive(active);

        }
     }



     //deleting random synapse
     private void deleteSynapse(){
        int layerPos = (int)D.random(0,layers.size()-1);
        List<Node> layer = layers.get(layerPos);
        List<Synapse> nodeSynapse = layer.get((int)D.random(0, layer.size())).getSynapses();


        if(nodeSynapse.size()>0){
            nodeSynapse.remove(nodeSynapse.get((int)D.random(0, nodeSynapse.size())));
        }



     }



     //adding new synapse
     private void addConnectSynpase(){

        //get a random oldNode
        int layerPosition = (int)D.random(0,layers.size()-1);
        List<Node>choosenLayer = layers.get(layerPosition);
        Node fromNode = choosenLayer.get((int)D.random(0, choosenLayer.size()));





         int fromNodeLayer = -1;

         //find what layer is the from oldNode
         for(int layer=0;layer<layers.size();layer++){
             List<Node> nodeList = layers.get(layer);
             int nodeIndex =nodeList.indexOf(fromNode);


             if(nodeIndex!= -1){
                fromNodeLayer = layers.indexOf(nodeList);
                 break;
             }
         }

        //connect the synapse to a random oldNode

        List<Node> whichLayer = layers.get((int)D.random(fromNodeLayer+1, layers.size()-1));
        Node connectingNode = whichLayer.get((int)D.random(0, whichLayer.size()));


        boolean connected = false;

        //check for an existing
        for(Synapse existingSynapse:fromNode.getSynapses()){
            if(existingSynapse.getToNode().equals(connectingNode)){
                connected = true;
                break;
            }
        }



        //add the from are not yet connected to the chosen oldNode
        if(!connected){
             Synapse newSynapse = new Synapse(connectingNode);
             fromNode.addSynapse(newSynapse);
        }



     }


     //mutating existing weights
     private void mutateWeight(){
        int layerPos = (int)D.random(0,layers.size()-1);
        List<Node> layer = layers.get(layerPos);
        List<Synapse> nodeSynapse = layer.get((int)D.random(0, layer.size())).getSynapses();



        if(nodeSynapse.size()>0){

           Synapse synapse = nodeSynapse.get((int)D.random(0, nodeSynapse.size()));

           int adder[] = {-1,1};

           double weight = synapse.getWeight();
           double mutation = adder[(int)D.random(0, 1)] * 0.01 * 2 * mutation_rate;
           weight+=mutation;

           //weight should be in range of -1 to 1
           if(weight>1){
               weight = 1;
           }
           else if(weight < -1){
               weight=-1;
           }

           synapse.setWeight(weight);



        }



     }


     //changing weight value to a random one
     private void randomWeight(){
        int layerPos = (int)D.random(0,layers.size()-1);
        List<Node> layer = layers.get(layerPos);
        List<Synapse> nodeSynapse = layer.get((int)D.random(0, layer.size())).getSynapses();




        if(nodeSynapse.size()>0){

           Synapse synapse = nodeSynapse.get((int)D.random(0, nodeSynapse.size()));
           synapse.setWeight(D.random(-1, 1));
        }
        
        
        

     }
     
     
     
     
     
     
     
     
     
     
     public static List<List<Node>> crossoverBrain(Brain brain1 , Brain brain2 , double crossover_prob){
        List<List<Node>> crossovered = new ArrayList<>();
       
        //crossover will only happen by chance and for non asexual individuals
        if(D.chanceHappened(crossover_prob) && !brain1.equals(brain2)){
            
            // do the node crossover and return a mapped nodes
            Map[] nodesMap = crossOverNodes(crossovered , brain1.getLayer(),brain2.getLayer());


            //stores the previous connection of synapses which will be referenced later
            Map<String,Synapse> synapseConnectionMap = new HashMap<>();

            crossOverSynapse(nodesMap[0],synapseConnectionMap);
            crossOverSynapse(nodesMap[1],synapseConnectionMap);
            
        }
        else{
            
            //use the cloned version of a selected brain
            
            Brain selectedbrain =brain1;
            if(D.choose()){
                selectedbrain = brain2;
            }
            
            
            try{
                crossovered = ((Brain)Cloner.deepCopy(selectedbrain)).getLayer();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            
        }
         
        
        
        
       
        return crossovered;
        
         
     }
     
     
   
     
     private static Map<Node,Node>[] crossOverNodes(List<List<Node>> crossovered , List<List<Node>> origNN1 , List<List<Node>> origNN2){
         
         //create a copy of original two brain layers because we where doing some modifications
         List<List<Node>> nn1 = new ArrayList<>(origNN1);
         List<List<Node>> nn2 = new ArrayList<>(origNN2);
         
         
         int nn1LayerSize = nn1.size();
         int nn2LayerSize = nn2.size();
        
         
         /*
            We need to make the two copied neural network layer size to be match for easy crossover.
            To do this , we will insert empty layers between the second to the last layer and the output layer
            of the copied neural network with the least layer size.
         */
         
         
         int layerShortage = Math.abs(nn1LayerSize - nn2LayerSize); //how much layer to be inserted
         List<List<Node>> smallestNN;
         
         if(nn1LayerSize < nn2LayerSize){
            smallestNN = nn1;
         }
         else{
             smallestNN = nn2;
         }
         
         
       
         //insert the additional layer
         for(int i=0;i<layerShortage;i++){
             smallestNN.add(smallestNN.size()-1,new ArrayList<>());
         }
         
         
         
         
        
         //using the smallestNN size because we are sure that both layer size is already the same
         int layerSize = smallestNN.size();
         
         
         
         //this will contain all the old oldNode to new oldNode mapping
         Map<Node,Node> nn1Map = new HashMap<>();
         Map<Node,Node> nn2Map = new HashMap<>();
         
         
         
         
         for(int layer = 0; layer < layerSize ; layer++){
             
             //add new layer
             List<Node> nodeList = new ArrayList<>();
             crossovered.add(nodeList);
             
             
             int nn1CurrLayerSize = nn1.get(layer).size();
             int nn2CurrLayerSize = nn2.get(layer).size();
             int maxCurrLayerSize = nn1CurrLayerSize;
             
             //get the largest oldNode layer size
             if(maxCurrLayerSize < nn2CurrLayerSize){
                 maxCurrLayerSize = nn2CurrLayerSize;
             }
            
             
             for(int n=0;n<maxCurrLayerSize;n++){
                
                Node node = new Node();
                 
                boolean addTheNode = false;
                boolean isNodeNN1 = false;
                boolean isNodeNN2 = false;
                
              
                
                if(layer <= 0 || layer >=  layerSize - 1){
                    //if input or output layer , add them
                    
                    if(nn1CurrLayerSize>n){
                       isNodeNN1 = true;
                    }
                    
                    
                    
                    if(nn2CurrLayerSize>n){
                        isNodeNN2 = true;
                    }
                    
                    
                    addTheNode = true;


                }
                
                else if(nn1CurrLayerSize > n && nn2CurrLayerSize>n){
                     //If a layer has a matching oldNode to another layer , add the oldNode
                     isNodeNN1 = true;
                     isNodeNN2 = true;
                     addTheNode = true;
                }
                
                
                else if(nn1CurrLayerSize <= n && nn2CurrLayerSize>n ){
                    // 50% chance of adding nn2 excess oldNode
                    isNodeNN2 = true;
                    addTheNode = true;
                }
                
                
                else if(nn1CurrLayerSize > n && nn2CurrLayerSize <= n ){
                    // 50% chance of adding nn1 excess oldNode
                    isNodeNN1 = true;
                    addTheNode = true;
                  
                }
                
                
                
                
                //check if the oldNode will be added
                if(addTheNode){
                   nodeList.add(node);
                }
               
                
                //this means that nn1 or nn2 oldNode in this position will be mapped to the new node
                
                if(isNodeNN1){
                    nn1Map.put(nn1.get(layer).get(n), node);
                }
                
                
                if(isNodeNN2){
                    nn2Map.put(nn2.get(layer).get(n), node);
                }
                
                
                
                
                
                 
             }
            
            
             
             
         }
         
         
         
         
         
         
         return new Map[]{nn1Map , nn2Map};
         
         
     }
     
    
     
     private static void crossOverSynapse(Map<Node,Node> nodesMap , Map<String,Synapse> previousConnectionMap){
         
         for(Node oldNode:nodesMap.keySet()){
             
             for(Synapse oldSynapse:oldNode.getSynapses()){
                 
                 
                 Node newFromNode = nodesMap.get(oldNode);
                 Node newToNode = nodesMap.get(oldSynapse.getToNode());
                 
                
                 /* A to node can be null if it is an excess node and the node crossover decides not to include them anymore.
                    It can also be null if it has no connection to the right.
                 */
                 if(newToNode != null){
                     
                     
                    Synapse newSynapse = new Synapse(newToNode);
                    newSynapse.setWeight(oldSynapse.getWeight());

                    //this is the key reference to connection
                    String connectionKey = String.format("%s - %s", newFromNode , newToNode);


                    //check if the connection already exist between two nodes through map
                    boolean connectionExists  = previousConnectionMap.containsKey(connectionKey);
                  

                    if((connectionExists && D.choose()) || !connectionExists){
                        //if connection exist , remove the old connection
                        if(connectionExists){
                            Synapse oldConnection = previousConnectionMap.get(connectionKey);
                            newFromNode.getSynapses().remove(oldConnection);
                        }
                        
                        
                        //add the new connection
                        newFromNode.addSynapse(newSynapse);
                        previousConnectionMap.put(connectionKey, newSynapse);
                    
                    }
                    
                    
                 }
                 
                 
                 
                 
             }
             
             
         }
         
         
     }
     
     
     
     
     
}
