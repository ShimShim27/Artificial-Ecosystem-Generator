
package ecosystem;

import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Food extends JPanel{

    private static final long serialVersionUID = 1L;
    
 
    final public int PELLET = 0;
    final public int MEAT = 1;
    
    
    final double breeding_points = 0.01;
    final double energy=300;
    
    
    //the value of sickness a creature can get eating the food
    //this will be subtracted to the creatures energy if sick
    final double sick_energy_reducer = 100 ;
    final long sick_duration = 20;
    
    
    JLabel foodImageDisplay;
     
     public Food(Container parent,ImageIcon icon,int size){
        foodImageDisplay = new JLabel();
        foodImageDisplay.setIcon(icon);
        
        this.add(foodImageDisplay);
        this.setOpaque(false);
        this.setBounds((int)D.random(1,parent.getWidth()) - size, (int)D.random(1,parent.getHeight()) - size, size, size);
     }
     
      
     public double getBreedingPoints(){
        return breeding_points;
    }
    
    
    public double getEnergy(){
        return energy;
    }
    
    
    
    public double getSickEnergyReducer(){
        return sick_energy_reducer;
    }
    
    
    public long getSickDuration(){
        return sick_duration;
    }
    
    
    
  
    
    
    
    
}
