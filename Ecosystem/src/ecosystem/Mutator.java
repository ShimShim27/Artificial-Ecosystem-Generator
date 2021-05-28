
package ecosystem;

import java.awt.Container;


public class Mutator {
    static double mutation_rate ;
    static double mutation_prob ;
    static double crossover_prob ;
    static int brain_mutation_frequency ;
    static double not_inherited_prob; 
    
    
    
    private  static double mutatedVersion(double min,double max,double orig){
      
        if(D.chanceHappened(mutation_prob)){
            orig += (mutatorDirection() * mutation_rate * 0.01 * (max-min));
        }
      
        //ensuring the values are between max and minimum
        if(orig>max){
            orig = max;
        }
        else if(orig<min){
            orig=min;
          
        }
      
      
        return orig;
    }
    
    
    
    
    
    public static Creature createNewOffspring(Container creatures_container,Creature firstParent,Creature secondParent){
        getValuesFromGlobal(firstParent);
        
        Creature newC = new Creature(creatures_container);  //create the new offspring
        
        //be sure to pass the global variables
        newC.setGV(firstParent.getGlobalVariables());
        
        
        
        Brain newCBrain = newC.getBrain();
        
        //do a brain crossover by chance else brain is brand new
        if(!D.chanceHappened(not_inherited_prob)){
            newCBrain.crossoverBrain(firstParent.getBrain(), secondParent.getBrain() , crossover_prob);
        }
      
        
        newCBrain.brainMutate(mutation_rate, mutation_prob, brain_mutation_frequency);
        crossOverPhysicalTraits(newC, firstParent, secondParent);
        
        
        newC.setBounds(firstParent.getX(),firstParent.getY(),newC.getWidth(),newC.getHeight());

        return newC;
    }
    
    
    
    
   
    private static void crossOverPhysicalTraits(Creature newC , Creature firstParent , Creature secondParent){
        
         
        //this the main parent go be used when not crossovered
        Creature notCrossovered = firstParent;  
        
        if(D.choose()){
            notCrossovered = secondParent;
        }
        
        
        
        if(D.chanceHappened(crossover_prob)){
            //crossover will happen
            notCrossovered = null;
        }
        
        
        
        //this will be use in getting which parent to be used
        Parents parentsClass = new Parents(firstParent, secondParent, notCrossovered , newC);
        
        
        
        //crossovered physical traits if there is a chance
        
        newC.setSkinColor(mutatedVersion(0, 255, parentsClass.getSelectedParent().getRedSkinColor()),
                        mutatedVersion(0, 255, parentsClass.getSelectedParent().getGreenSkinColor()),
                        mutatedVersion(0, 255, parentsClass.getSelectedParent().getBlueSkinColor()));

       
        newC.setInitialEnergy(mutatedVersion(newC.MIN_ENERGY,newC.MAX_ENERGY,parentsClass.getSelectedParent().getInitialEnergy()));
        newC.setMass(mutatedVersion(newC.MIN_MASS,newC.MAX_MASS,parentsClass.getSelectedParent().getMass()));
        newC.setEyeSight(mutatedVersion(newC.MIN_EYE_SIGHT, newC.MAX_EYE_SIGHT, parentsClass.getSelectedParent().getEyeSight()));
        newC.setSpeed(mutatedVersion(newC.MIN_SPEED,newC.MAX_SPEED,parentsClass.getSelectedParent().getSpeed()));
        newC.setReproScale(mutatedVersion(newC.MIN_REPRO,newC.MAX_REPRO,parentsClass.getSelectedParent().getReproScale()));
        newC.setFeedingScale(mutatedVersion(newC.MIN_FEED_SCALE, newC.MAX_FEED_SCALE, parentsClass.getSelectedParent().getFeedingScale()));
        newC.setDamage(mutatedVersion(newC.MIN_DAMAGE,newC.MAX_DAMAGE,parentsClass.getSelectedParent().getDamage()));
        newC.setHatchTime(mutatedVersion(newC.MIN_HATCH_TIME, newC.MAX_HATCH_TIME, parentsClass.getSelectedParent().getHatchTime()));
    }
    
    
    
    
    
    
    //either plus or subtract
    private static double mutatorDirection(){
        int[] mult = {1,-1};    
        double adder = mult[(int)D.random(0, 2)]; 
        return adder;
    }
    
    
    
    
    
    
    //get the related values from global variables
    private static void getValuesFromGlobal(Creature parent){
        GlobalVariables globalVariables = parent.getGlobalVariables();
        brain_mutation_frequency = globalVariables.getMutationFrequency();
        mutation_prob = globalVariables.getMutation_prob();
        mutation_rate = globalVariables.getMutation_rate();
        crossover_prob = globalVariables.getCrossover_prob();
        not_inherited_prob = globalVariables.getNotInheritedProb();
    }
    
    
    
    
    
    
    
    
    //handles getting the parents
    static private class Parents {
        Creature parent1;
        Creature parent2;
        Creature notCrossOveredParent;
        Creature offspring;
    
        Parents(Creature parent1 , Creature parent2 , Creature notCrossOveredParent , Creature offspring){
            
            this.parent1 = parent1;
            this.parent2 = parent2;
            this.notCrossOveredParent = notCrossOveredParent;
            this.offspring = offspring;
            
            
        }
        
        
        
        //choosing randomly between two parents , or if crossover not achieve , return the notCrossOveredParent
    
        private  Creature getSelectedParent(){
            Creature selected;

            //either a crossovered parent or one single parent
            if(notCrossOveredParent == null){

                if(D.choose()){
                        selected = parent1;
                }
                else{
                    selected = parent2;
                }
                
                
                
            }
            else{
                selected = notCrossOveredParent;

            }



            //there is a small chance that the trait is not inherited
            if(D.chanceHappened(not_inherited_prob)){
                selected = offspring;
            }
                
            
            return selected;
        }
        
    }
    
    
    
    
    
}
