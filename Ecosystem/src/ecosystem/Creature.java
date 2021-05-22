

package ecosystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Creature extends JPanel{

    private static final long serialVersionUID = 1L;

    
    final String creatureImageName= "mainChar.png";
    
    
    final public int HERBIVORE = 1;
    final public int CARNIVORE =2;
    final public int OMNIVORE = 3;
    
    
  
    //public creature variables
    final public double MAX_SPEED = 5;
    final public double MIN_SPEED = 1;
    final public double MAX_MASS = 40;
    final public double MIN_MASS = 30;
    final public double MAX_ENERGY = 4000;
    final public double MAX_EYE_SIGHT = 200;
    final public double MIN_EYE_SIGHT = 10;
    final public double MIN_ENERGY = 0;
    final public double MAX_WBREED = 1;
    final public double MIN_WBREED =0.001;
    final public double MIN_REPRO = 0;
    final public double MAX_REPRO = 1;
    final public double MAX_FEED_SCALE = 1;
    final public double MIN_FEED_SCALE = 0;
    final public double MAX_DAMAGE = 10;
    final public double MIN_DAMAGE = 1;
    final public double MAX_HATCH_TIME = 100;
    final public double MIN_HATCH_TIME = 5;
    final public double MAX_PHERMONE = 1;
    final public double MIN_PHERMONE = -2;
    final public double MAX_SICK_INDICATOR = 1;
    final public double MIN_SICK_INDICATOR = -1;
    final public double ENERGY_DECAY = 0.1; //this a constant energy loss per second. 
    final public double ATTACKING_COST = 100; //fix energy cost when attacking
    final public double REPRO_COST = 2000;
    
    
    
    
    //sickness related variables
    private Food foodThatCauseSickness;
    private long sickness_duraton;
    
    
    private long age;
    
    
    public double reproScale;
    public double mass;
    private double speed;
    private Brain brain;
    private double energy;
    private double eyeSight;
    private double initialEnergy;
    private double willToBreed;
    private double feedingScale;
    private double damage;
    private double hatch_time;
    private double phermone;
    private double sickIndicator; //either sick or not
    
    //for skin color
    private Color skinColor;
    private double redSkin;
    private double greenSkin;
    private double blueSkin;
   
    
    /*
        This variables are transient because they should be always up to date.
    */
    transient private GlobalVariables globalVariables;   //this is used to store variables that are customizable by the user
    transient Container creatures_container;
    
    
    JLabel creatureImageDisplay = new JLabel();        
    
    
    
    
    Creature(Container container){
        creatures_container = container;
        intializeTraits();
        
        
       
        this.add(creatureImageDisplay);
        this.setOpaque(false); 
        
     
        
        //set location
        int maxX = container.getWidth() - this.getWidth();
        int maxY = container.getHeight()- this.getHeight();
        this.setLocation((int)D.random(1,maxX), (int)D.random(1,maxY));
        
       
        
        
       
    }

   
    
   
    
   //declare all the traits
   private void intializeTraits(){
       
        speed = (int)D.random(MIN_SPEED, MAX_SPEED+1);
        mass = (int)D.random(MIN_MASS, MAX_MASS+1);
        brain = new Brain(new int[]{16,12});
        initialEnergy = D.random(MIN_ENERGY, MAX_ENERGY);
        energy = initialEnergy;
        reproScale = D.random(MIN_REPRO, MAX_REPRO);
        feedingScale = D.random(MIN_FEED_SCALE, MAX_FEED_SCALE);
        damage = D.random(MIN_DAMAGE, MAX_DAMAGE);
        eyeSight = D.random(MIN_EYE_SIGHT, MAX_EYE_SIGHT);
        hatch_time = D.random(MIN_HATCH_TIME, MAX_HATCH_TIME);
        willToBreed = MIN_WBREED;
        phermone = MIN_PHERMONE;
        
        //set skin color randomly
        
        redSkin = D.random(0, 256);
        greenSkin =D.random(0, 256);
        blueSkin = D.random(0, 256);
     
        setSkinColor(redSkin, greenSkin, blueSkin);
       
      
       
        
         
        this.setSize((int)mass,(int)mass);
        
        
   }
    
   
   
    public void decide(){
        
        energy-=((ENERGY_DECAY*age) + (age * feedingScale * 2));
        age++;
        
        //show the heart particles if want to breed
        if(isWillingToBreed()){
            showBreedParticle(this);
        }
        
        
        //checking if the creature is sick or not
        checkSickness();
        
        
        /* check if dead. If not then do the decision making
        */
        if(energy<=0){
            creatureDead();
        }
        
        else{
           
            CreatureInputs creatureInputs = new CreatureInputs(creatures_container, this);
            double [] inputs = creatureInputs.getInputs();
            
            
            //get all the outputs
            List<Double[]> outputs = CreatureOutputs.getOutputs(inputs,brain) ;
            
            
            
            
            Double[] movements = outputs.get(0);
            Double[] wants = outputs.get(1);
            Double[] phermones = outputs.get(2);
            
            //set the secreting phermone
            phermone = phermones[0];
            if(phermone>0){
                //show phermones
                showPhermone(this);
            }
            else{
                phermone = MIN_PHERMONE;
            }
           
            
            
            //deciding for movement base on output .Zero below means no movement
            int decision;
            double maxVal = Collections.max(Arrays.asList(movements));
            if(maxVal>0){
                decision = Arrays.asList(movements).indexOf(maxVal);
                move(decision);
            }
            
            
            
            
            
            //detecting if collided to somehing
            final Double wantToEat = wants[0];
            final Double wantToBreed = wants[1];
            final Double wantToKill = wants[2];
            
            detectCollisions(wantToEat , wantToBreed , wantToKill);
            
            
            //asexual reproduction
            if(isAsexual(reproScale) && wantToBreed>0){
                spawnEgg(this);
            }
            
            
        }
        
    }
    
    
    
   
    
    
    
    private void move(int direction){
        int width = this.getWidth();
        int height = this.getHeight();
        
        int xpos = this.getX();
        int ypos = this.getY();
        
        switch(direction){
            case 0:
                xpos+=speed;
                break;
                
            case 1:
                xpos-=speed;
                break;
                
            case 2:
                ypos+=speed;
                break;
            case 3:
                ypos-=speed;
                break;
                
            case 4:
                xpos+=speed;
                ypos+=speed;
                break;
                
            case 5:
                xpos-=speed;
                ypos+=speed;
                break;
                
            case 6:
                xpos+=speed;
                ypos-=speed;
                break;
                
                
            case 7:
                xpos-=speed;
                ypos-=speed;
                break;
          
           
            
        }
        
        
        
        //deducting energy base on speed and size if the creature moves
        if(this.getX()!=xpos || this.getY()!=ypos){
            energy-=((speed/MAX_SPEED) * (mass/MAX_MASS));
        }
        
        
        
        //check if out of bounds
        int containerWidth = creatures_container.getWidth();
        int containerHeight = creatures_container.getHeight();
        
        if(xpos>containerWidth){
            xpos = 0;
        }
        else if(xpos<0){
            xpos = containerWidth - width;
        }
        
        
        if(ypos>containerHeight){
            ypos = 0;
          
        }
        else if(ypos<0){
            ypos = containerHeight -  height;
            
        }
        
        
        
        
        
        
        
        this.setBounds(xpos, ypos, width, height);
        
       
        
        
    }
    
  
    private void creatureDead(){
        D.invokeLater(()->{
                        
                int x = this.getX();
                int y = this.getY();
                Container container = this.getParent();
                container.remove(this);
                Meat meat = new Meat(container,globalVariables);
                meat.setLocation(x,y);
                container.add(meat);
                return null;
                }
            );
    }
    
    
    private void checkSickness(){
        //check if sick
        if(foodThatCauseSickness!=null ){
           
            //adding sick bubble
            showSickTrails(this);
            sickIndicator = MAX_SICK_INDICATOR;
            
            
            
            //reduce the creatures energy base of the food sickness durattion and reducer
            sickness_duraton--;
            energy-=foodThatCauseSickness.getSickEnergyReducer();
            
            
            
            //remove the sickness if the duration over
            if(sickness_duraton<=0){
                foodThatCauseSickness =null;
                
            }
            
          
        }
        else{
            sickIndicator = MIN_SICK_INDICATOR;
        }
        
        
        
    }
    
    
    
    
    
    public void spawnEgg(Creature secondParent){
        
        //energy after breeding
        double currParentAfterEnergy;
        double secondParentAfterEnergy;

        //the combined willingness of two parent to breed
        double combinedWillToBreed;
        
        if(secondParent.equals(this)){
            //for asexual , they bear all the cost of reproduction
             currParentAfterEnergy = energy - REPRO_COST;
             secondParentAfterEnergy = currParentAfterEnergy;
             combinedWillToBreed = willToBreed;
        }
        else{
            //for sexual , energy cost is divided between partners
             double dividedReproCost = REPRO_COST/2.0;
             currParentAfterEnergy = energy - dividedReproCost;
             secondParentAfterEnergy = secondParent.getEnergy() - dividedReproCost;

             combinedWillToBreed = willToBreed + secondParent.getWillingToBreed();

        }


        
        //convert to 100 range and use it to determine the chance of being willing
        double convertedChance = D.convertRange(MAX_WBREED * 2, MIN_WBREED, combinedWillToBreed, 100, 0);
        boolean willing = D.chanceHappened(convertedChance);



         if(willing
                 && currParentAfterEnergy > MIN_ENERGY
                 && secondParentAfterEnergy > MIN_ENERGY){


             D.invokeLater(()->{
                 Creature hatchLing = Mutator.createNewOffspring(creatures_container,this,secondParent);
                 Egg egg = new Egg(hatchLing,(int)hatch_time);
                 egg.setLocation(this.getX(),this.getY());
                 creatures_container.add(egg);
                 return null;
             });



             //setting will to breed back to normal
             willToBreed=MIN_WBREED;
             secondParent.setWIllingToBreed(MIN_WBREED);

             //reducing energy cost
             energy = currParentAfterEnergy;
             secondParent.setEnergy(secondParentAfterEnergy);


             if(willToBreed<MIN_WBREED){
                 willToBreed = MIN_WBREED;
             }

             if(secondParent.getWillingToBreed()<MIN_WBREED){
                 secondParent.setWIllingToBreed(MIN_WBREED);
             }



         }
        
        
       
    }
    
    
    
    
    
    private void setSickByFood(Food food){
        boolean hasChance = false;
        double spectrumVal=0;
        double maxSpectrumVal=0;
        double minSpectrumVal=0;
        
    
        double oneHalf = ((double)1/2)*MAX_FEED_SCALE;
        
       
        // I am using spectrum to determine their chance of getting sick
        if(feedingScale < oneHalf && food instanceof Meat){
            maxSpectrumVal = oneHalf;
            minSpectrumVal = 0;
            spectrumVal = oneHalf - feedingScale;
            hasChance = true;
           
        }
        else if(feedingScale >= oneHalf && food instanceof Pellet){
            maxSpectrumVal = MAX_FEED_SCALE;
            minSpectrumVal = oneHalf;
            spectrumVal = feedingScale;
            hasChance = true;
        }
        
        
      
        
        //the bigger the spectrum value , the bigger chance they have to get sick
        if(hasChance && D.random(-1, 1)<= D.oneToNegaOne(maxSpectrumVal, minSpectrumVal, spectrumVal)){
            foodThatCauseSickness = food;
            sickness_duraton = foodThatCauseSickness.getSickDuration();
        }
        
       
      
    }
    
    
    
    
    
    //as the willToBreed point increase , the greater chance of willing to breed
    public boolean isWillingToBreed(){
        double convertedChance = D.convertRange(MAX_WBREED, MIN_WBREED, willToBreed, 100, 0);
        return D.chanceHappened(convertedChance);
    }
    
    
    
    
    
    

    public void addEnergy(double e){
        energy+=e;
        if(energy>MAX_ENERGY){
            energy = MAX_ENERGY;
        }
    }
    
    
    
    public void addBreedingPoints(double p){
        willToBreed += p;
        if(willToBreed>MAX_WBREED){
            willToBreed=MAX_WBREED;
        }
    }
    
    
    public boolean isAsexual(double d){
        boolean asexual = false;
        if(d<MAX_REPRO/2.0){
            asexual = true;
        }
        
        return asexual;
    }
    
    
    
    //get how does the creature eat
    public int getFeedingType(){
        int type;
        
        
        double oneThird = ((double)1/3)*MAX_FEED_SCALE;
        double twoThird = ((double)2/3)*MAX_FEED_SCALE;
        
        if(feedingScale<= oneThird){
            type = HERBIVORE;
        }
        else if(feedingScale<=twoThird){
            type = OMNIVORE;
        }
        else{
            type = CARNIVORE;
        }
       
        
        return type;
    }
   
    
    
    
    
    

   
   
    
    
    //------------------------------------ DISPLAYS ---------------------------------------------------
    
    public void showCreatureImage(){
        D.showComponentsImage( creatureImageDisplay, this.getWidth() , this.getHeight()  , creatureImageName , skinColor);
        
    }
    
    
    private void showBlood(Creature c){
        if(D.choose()){
            D.invokeLater(()->{
                creatures_container.add(new BloodSplash(c));
                return null;
            });
        }
    }
    
    
    
    private void showSickTrails(Creature c){
        if(D.choose()){
            D.invokeLater(()->{
                creatures_container.add(new SickTrail(c));
                return null;
            });
        }
    }
    
    
    
    
    private void showBreedParticle(Creature c){
        if(D.choose()){
            D.invokeLater(()->{
                creatures_container.add(new W2BreedParticle(c));
                return null;
            });
        }
    }
    
    
    
    
    
    private void showPhermone(Creature c){
        if(D.choose()){
            D.invokeLater(()->{
                creatures_container.add(new Phermone(c));
                return null;
            });
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //------------------------------------------   COLLISION RELATED FUNCTIONS  ------------------------------------------//
    /*
        This are the functions that will handle all the decision when the crature collided with another component such as
        food , creature and etc.
    */
    
    
    
    public void detectCollisions(Double wantToEat , Double wantToBreed , Double wantToKill){
        Component[] components = creatures_container.getComponents();
        for(Component component:components){
            if(component!=null){
                
                if(collided(component) && !component.equals(this)){
                    
                    if(component instanceof  Food ){
                        Food food = (Food)component;
                        foodCollidingHandler(food,wantToEat);
                      
                    }

                    else if(component instanceof  Creature ){
                        Creature collided = (Creature)component;
                        creatureCollidingHandler(collided,wantToBreed,wantToKill);
                         
                    }
                }
                
            }
            
        }
        
    }
    
    
    
    
    
    public boolean collided(Component component){
        boolean collided=false;
        
        try{
            collided = component.getBounds().intersects(this.getBounds()) 
            ||   this.getBounds().intersects(component.getBounds());
        }catch(java.lang.NullPointerException nullPointerException){
            //the comparing or compared child already gone
        }
        
        
        return collided;

    }
    
    
    
    
   
    //handling the decision when the creature collided with other creature
    private void creatureCollidingHandler(Creature collidedCreature , Double wantToBreed , Double wantToKill){
        
        if(wantToBreed>0){
            
            if(!isAsexual(reproScale)){
                if(!isAsexual(collidedCreature.getReproScale())){
                     spawnEgg(collidedCreature);
                }

            }

        }


        

        if(wantToKill>0){
            double collidedEnergy = collidedCreature.getEnergy();
            
            if((int)mass > collidedCreature.getMass() && getFeedingType()!=HERBIVORE){
                Meat dummyMeat = new Meat(creatureImageDisplay,globalVariables);
               
                double acquiredEnergy = collidedEnergy  +  dummyMeat.getEnergy();
                if(acquiredEnergy>MAX_ENERGY){
                    acquiredEnergy = MAX_ENERGY;
                }
                
                //adding meat energy plus the acquired energy from victim
                addEnergy(acquiredEnergy);


                // adding meat breeding point
                addBreedingPoints(dummyMeat.getBreedingPoints());
               


                //eat the the collided creature whole
                D.invokeLater(()->{
                    creatures_container.remove(collidedCreature);
                    return null;
                });
                
                

            }
            else {
                //the bigger the creature , the greater its damage
                collidedCreature.setEnergy(collidedEnergy - (damage*mass));
               
            }
            
           
            //attacking costs energy
            energy-=(ATTACKING_COST * (damage/MAX_DAMAGE));
            
            //adding splash of blood
            showBlood(collidedCreature);
        }
        
        
    }
    
    
    
    
    private void foodCollidingHandler(Food food, double wantToEat){
       
        if(wantToEat > 0){
            D.invokeLater(()->{
                    creatures_container.remove(food);
                    return null;
                }
             );


             
             //there is a good chance the creature will get sick base of feeding method and food it eat
             setSickByFood(food);
             
             
             //if sick , there are no energy and points
             if(foodThatCauseSickness==null){
                addEnergy(food.getEnergy());
                addBreedingPoints(food.getBreedingPoints());
             }
             
        }
        
    }
    
    
    
    
    
  
    
    
  
    
    
    
    
    //------------------------------------------PHYSICAL TRAITS GETTER AND SETTER------------------------------------------//
    
    public Brain getBrain(){
        return brain;
    }
    
   
    public void setBrain(Brain b){
        brain = b;
    }
    
    public double getEnergy(){
        return energy;
    }
    
    public void setEnergy(double d){
        energy=d;
    }
    
    
    public void setInitialEnergy(double d){
        initialEnergy = d;
        energy = d;
    }
    
    
    public double getInitialEnergy(){
        return initialEnergy;
    }
    
    
    public void setSpeed(double d){
        speed=d;
    }
    
    public double getSpeed(){
        return speed;
    }
    
    
    public void setMass(double m){
        mass = m;
        this.setSize((int)m,(int)m);
        
        
    }
    
    public double getMass(){
        return mass;
    }
    
    
    
    public void setReproScale(double d){
        reproScale=d;
    }
    
    public double getReproScale(){
        return reproScale;
    }
    
    public double getWillingToBreed(){
        return willToBreed;
    }
    
    
    public void setEyeSight(double d){
        eyeSight = d;
    }
    
    public double getEyeSight(){
        return eyeSight;
    }
    
    
    public void setWIllingToBreed(double w){
        willToBreed = w;
    }
    
    
    
    public double getFeedingScale(){
        return feedingScale;
    }
    
    
    public void setFeedingScale(double d){
        feedingScale = d;
    }
    
    
    public double getDamage(){
        return damage;
    }
    
    
    public void setDamage(double d){
        damage = d;
    }
    
    
    
    
    public void setSkinColor(double r , double g , double b){
        redSkin = r;
        greenSkin = g;
        blueSkin = b;
       
        skinColor = new Color((int)redSkin , (int)greenSkin , (int)blueSkin);
    }
    
    
    
    public double getRedSkinColor(){
        return redSkin;
    }
    
    public double getGreenSkinColor(){
        return greenSkin;
    }
    
    
    public double getBlueSkinColor(){
        return blueSkin;
    }
    
    
    public Color getSkinColor(){
        return skinColor;
    }

   
    
    public double getHatchTime(){
        return hatch_time;
    }
    
    
    public void setHatchTime(double d){
        hatch_time = d;
    }
    
    
    
    public double getPhermone(){
        return phermone;
    }
    
    
    
    public double getSickIndicator(){
        return sickIndicator;
    }
    
    
    
    public void setFoodThatCauseSickness(Food f){
        foodThatCauseSickness = f;
    }
    
    
    public void setAge(long a){
        age = a;
    }
    
    
    public void setGV(GlobalVariables gv){
        globalVariables = gv;
    }
   
    
    
    public GlobalVariables getGlobalVariables(){
        return globalVariables;
    }
    
    
 
    public void setContainer(Container c){
        creatures_container = c;
    }
    
    
    public Container getContainer(){
        return creatures_container;
    }
    
   
  
    
    
    
    
    
}
