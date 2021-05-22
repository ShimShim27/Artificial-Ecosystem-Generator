
package ecosystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;


public class CreatureInputs {
    
    final static int type_creature = 1;
    final static int type_food = 2;
    
    Container creatures_container;
    Creature creature;
    
    int adder;
    int noXDist;
    int noYDist;
    
    
    public CreatureInputs(Container container,Creature c) {
        creatures_container = container;
        creature = c;
        
        
        /*For nearest object. Because of theeyesight , sometimes the creature can't detect a nearest object , therefore giving no inputsW. 
            This will be the default nearest object distance.
            The distance is intentionally reall near to negative infinity to give the creature a better distinction if there are
            nearest object or not.*/
        
        adder = (int)(creature.MAX_MASS + creature.MAX_SPEED)*10;
        noXDist = -(creatures_container.getWidth()+ adder);
        noYDist  = -(creatures_container.getHeight()+ adder);
        
    }
    
    //return all the formulated inputs
    public double[] getInputs(){
        List<Double> inptList = getNearestObject(type_food);
    
        inptList.addAll(getNearestObject(type_creature));  
        inptList.addAll(getPhysicalInputs());
        inptList.addAll(getContainerBorderInputs());
        
        int inp_size = inptList.size();
        double inputs[] = new double[inp_size];
        for(int i=0;i<inp_size;i++){
            if(inptList.get(i)>1 || inptList.get(i)<-1){
                D.print(i + ".)" + inptList.get(i));
            }
            
            inputs[i]=inptList.get(i);
        }
       
        return inputs;
    }
    
    
    
    
    
    
    //get the input value for the nearest objects
    public List<Double> getNearestObject(int type){
       
        int x2 = creature.getX();
        int y2 = creature.getY();
        
        Component nearest =null;
        
        
        double leastDist = 0;
        
        //get what object is the nearest
        Component[] components = creatures_container.getComponents();
        for(Component component:components){
          
            if(component!=null){
                
                if(!component.equals(creature)  && isTargetComponent(component,type)){
                    double dist = D.distance(x2, component.getX(), y2, component.getY());

                    //the nearest object should be always dependent on the creatures eyesight
                    if(((dist < leastDist) || (nearest == null)) && dist<= (creature.getMass()/2.0) + creature.getEyeSight() ){
                        nearest = component;
                        leastDist = dist;
                       
                    }
                    
                }
            }
            
        }
        
        
  
      
       int xDist;
       int yDist;
        
       if(nearest !=null){
           xDist = creature.getX() - nearest.getX();
           yDist = creature.getY() - nearest.getY();
            
       }
       else{
           xDist = noXDist;
           yDist = noYDist;
       }
     
     
       
   
       
       List<Double> nearestOBj = new ArrayList<>();
       
       //distaance input for the nearest object
       nearestOBj.add(D.oneToNegaOne(creatures_container.getWidth(), noXDist, xDist));
       nearestOBj.add(D.oneToNegaOne(creatures_container.getHeight(), noYDist, yDist));
     
       
       
       
       
       //other inputs from nearest object
       if(type == type_creature ){
          nearestCreatureInput(nearestOBj,nearest);
           
       }
       
       else if(type == type_food){
            nearestFoodInput(nearestOBj,nearest);
       }
      
        return nearestOBj;
        
    }
    
    
    
    
    
    
    
    //input for nearest creature
    private void nearestCreatureInput(List<Double> nearestOBj , Component nearest){

       
        double nearestFeedScale= -1;
        double nearestSkinColor = -1;
        double nearestMass = -1;
        double nearestPhermone=-1;

         //get the nearest creature inputs
        if(nearest!= null && nearest instanceof Creature){

             Creature nearestC = (Creature)nearest;
             
            
             //the nearet phermone
             nearestPhermone = D.oneToNegaOne(nearestC.MAX_PHERMONE, nearestC.MIN_PHERMONE, nearestC.getPhermone());
          
             
             //nearest mass
             nearestMass = D.oneToNegaOne(nearestC.MAX_MASS, nearestMass, nearestC.getMass());

          
             //nearest feeding scale
             nearestFeedScale = D.oneToNegaOne(nearestC.MAX_FEED_SCALE, nearestFeedScale, nearestC.getFeedingScale());
             

             //get skin color
             Color skinColor = nearestC.getSkinColor();
             nearestSkinColor = D.oneToNegaOne(Color.WHITE.getRGB(), Color.BLACK.getRGB() - 1,  skinColor.getRGB());
            
        }
      
      
        nearestOBj.add(nearestMass);
        
        nearestOBj.add(nearestSkinColor);
        
        nearestOBj.add(nearestPhermone);
        
        nearestOBj.add(nearestFeedScale);
        
    }
    
    
    
    
    
    
    
    // input for nearest food
    private void nearestFoodInput(List<Double> nearestOBj , Component nearest){
        double nearestInp = -1;
        if(nearest!= null && nearest instanceof Food){
            int kind_of_food=0;

            //check what type of food it is
            Food food = (Food)nearest;
            if(food instanceof Pellet){
                kind_of_food = food.PELLET;
            }
            else if(food instanceof Meat){
                kind_of_food = food.MEAT;
            }
            

           nearestInp = D.oneToNegaOne(food.MEAT, nearestInp, kind_of_food);
           
        }
        nearestOBj.add(nearestInp);
    }
    
    
    
    
     private List<Double> getContainerBorderInputs(){
         List<Double> borderInputVal = new ArrayList<>();
         
       
        
         
         double borderLeft = noXDist;
         double borderRight = noXDist;
         double borderUp = noYDist;
         double borderDown = noYDist;
       
         
         
         double actualBorderLeft = creature.getX();
         double actualBorderRight =  creatures_container.getWidth() - creature.getX();
         double actualBorderUp =  creature.getY();
         double actualDown = creatures_container.getHeight() - creature.getY();
         
         double eyeSight = (creature.getMass()/2.0) + creature.getEyeSight();
         
         
         //if border is in sight , then take its distance as an input
        if(actualBorderLeft<=eyeSight){
            borderLeft = actualBorderLeft;
        }
         
         
        if(actualBorderRight<=eyeSight){
            borderRight = actualBorderRight;
        }
          
        
        if(actualBorderUp<=eyeSight){
            borderUp = actualBorderUp;
        }
           
           
        if(actualDown<=eyeSight){
            borderDown = actualDown;
        }
         
         
        
        

        //for border X
        borderInputVal.add(D.oneToNegaOne(creatures_container.getWidth(), noXDist, borderRight));
        borderInputVal.add(D.oneToNegaOne(creatures_container.getWidth(), noXDist, borderLeft));

     

        //for border Y
        borderInputVal.add(D.oneToNegaOne(creatures_container.getHeight(), noYDist, borderDown));
        borderInputVal.add(D.oneToNegaOne(creatures_container.getHeight() , noYDist ,  borderUp));

      
         return borderInputVal;
         
     }
     
     
    
    
    
    
    
    //get the physical traits of creature as input
    private List<Double> getPhysicalInputs(){
        
        List<Double> physicalVal = new ArrayList<>();
        physicalVal.add(D.oneToNegaOne(creature.MAX_WBREED, creature.MIN_WBREED, creature.getWillingToBreed()));
        physicalVal.add(D.oneToNegaOne(creature.MAX_ENERGY, creature.MIN_ENERGY, creature.getEnergy()));
        physicalVal.add(D.oneToNegaOne(creature.MAX_SICK_INDICATOR, creature.MIN_SICK_INDICATOR, creature.getSickIndicator()));
    
        return physicalVal;
    }
    
    
    
   
    
    //component type checkerrs
    private  boolean isTargetComponent(Component component,int type){
         boolean confirmed = false;
        if(type==type_creature && component instanceof Creature){
            confirmed = true;
        }
        else if(type==type_food && component instanceof Food){
            confirmed = true;
        }
        
        return confirmed;
    }
    
}
