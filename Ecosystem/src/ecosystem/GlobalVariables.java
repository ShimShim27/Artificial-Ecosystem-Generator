
package ecosystem;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class GlobalVariables implements Serializable{

    private static final long serialVersionUID = 1L;
    
    
    
    //---------------------------------- STATIC IMAGES --------------------------
    final public static int PELLET_SIZE = 15;
    final public static int MEAT_SIZE = 25;
    
    
    final public static int BLOOD_SIZE = 13;
    final public static int SICK_SIZE = 10;
    final public static int BREED_SIZE = 11;
    final public static int PHERMONE_SIZE = 11;
    
    
    private ImageIcon blood;
    private ImageIcon sick;
    private ImageIcon breed;
    private ImageIcon phermmone;
    
    private ImageIcon pellet;
    private ImageIcon meat;
    
  
    /*Images are hard to load , so we will be loading the static images only once
    */
    public void loadStaticImages(){
        JLabel bloodLabel = new JLabel();
        JLabel sickLabel= new JLabel();
        JLabel breedLabel = new JLabel();
        JLabel phermmoneLabel = new JLabel();
        
        JLabel pelletLabel = new JLabel();
        JLabel meatLabel = new JLabel();
        
        
        //load the image particles
        D.showComponentsImage(bloodLabel, BLOOD_SIZE, BLOOD_SIZE, "blood.png", D.hexToColor("#e61c4b"));
        D.showComponentsImage(sickLabel, SICK_SIZE, SICK_SIZE, "bubble.png", D.hexToColor("#42d638"));
        D.showComponentsImage(breedLabel, BREED_SIZE, BREED_SIZE, "heart.png", Color.PINK);
        D.showComponentsImage(phermmoneLabel, PHERMONE_SIZE, PHERMONE_SIZE , "phermone.png",  D.hexToColor("#a16fe3"));
        
        D.showComponentsImage(pelletLabel, PELLET_SIZE, PELLET_SIZE , "pellet.png", D.hexToColor("#e8d846"));
        D.showComponentsImage(meatLabel, MEAT_SIZE, MEAT_SIZE , "meat.png",  D.hexToColor("#c93c3c"));
        
     
        //image icon to be retreive later
        blood = (ImageIcon)bloodLabel.getIcon();
        sick = (ImageIcon)sickLabel.getIcon();
        breed = (ImageIcon)breedLabel.getIcon();
        phermmone = (ImageIcon)phermmoneLabel.getIcon();
        
        pellet = (ImageIcon)pelletLabel.getIcon();
        meat = (ImageIcon)meatLabel.getIcon();
    }
    
    
    
    
    public ImageIcon getBloodIcon(){
        return blood;
    }
    
    
    public ImageIcon getSickIcon(){
        return sick;
    }
    
    
    public ImageIcon getBreedIcon(){
        return breed;
    }
    
    
    public ImageIcon getPhermondIcon(){
        return phermmone;
    }
    
    
    public ImageIcon getPelletIcon(){
        return pellet;
    }
    
    
    public ImageIcon getMeatIcon(){
        return meat;
    }
    
    
    
    
    
    
    
    
    
  
    //---------------------------------------------------------- MUTATION -----------------------------------------
    

    
    private double mutation_prob;
    private double crossover_prob;
    private int brain_mutation_frequency;
    private double mutation_rate;
    private double not_inherited_prob;
    
    
    
    public void setMutation_rate(double d){
        mutation_rate = d;
    }
    
    
    public double getMutation_rate(){
        return mutation_rate;
    }
    
    
    
    
    public void setMutation_prob(double d){
        mutation_prob = d;
    }
    
    
    public double getMutation_prob(){
        return mutation_prob;
    }
    
    
    
    
    
    public void setCrossover_prob(double d){
        crossover_prob = d;
    }
    
    
    
    public double getCrossover_prob(){
        return crossover_prob;
    }
    
    
    
    
    
    public void setBrainMutationFrequency(int i){
        brain_mutation_frequency = i;
    }
    
    
    
    public int getMutationFrequency(){
        return brain_mutation_frequency;
    }
    
    
    
    public void setNotInheritedProb(double d){
        not_inherited_prob = d;
    }
    
    
    
    public double getNotInheritedProb(){
        return not_inherited_prob;
    }
    
    
    
    
    
    // -------------------------------------------------- Main GUI ----------------------------------------------
    
    
    private int pop_limit;
    private int starting_pop;
    private int pellet_count;
    private int pellet_replenesh_timer; //this is the timer before pellets spawing again
   
    
    
    public void setPopLimit(int i){
        pop_limit = i;
    }
    
    
    
    public int getPopLimit(){
        return pop_limit;
    }
   
    
    
    public void setStartingPop(int i){
        starting_pop = i;
    }
    
    
    public int getStartingPop(){
        return starting_pop;
    }
    
    
    
    public void setPelletCount(int i){
        pellet_count = i;
    }
    
    
    public int getPelletCount(){
        return pellet_count;
    }
    
    
    
    public void setPelletReplenishTimer(int i){
        pellet_replenesh_timer = i;
    }
    
    
    
    public int getPelletReplenishTimer(){
        return pellet_replenesh_timer;
    }
    
    
    
    
    
    
    //------------------------------ PROCESSES ----------------------------------------------------
    private int gameDelay ;
    private int maxThread;
  
    
    
    
    public void setGameDelay(int i){
        gameDelay = i;
    }
    
    
    public int getGameDelay(){
        return gameDelay;
    }
    
    
    public void setMaxThread(int i){
        maxThread = i;
    }
    
    
    public int getMaxThread(){
        return maxThread;
    }
    
    
 
    
    
    
    
 
}
