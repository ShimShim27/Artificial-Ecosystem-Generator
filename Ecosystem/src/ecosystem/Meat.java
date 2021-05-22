
package ecosystem;

import java.awt.Container;


public class Meat extends Food{

    private static final long serialVersionUID = 1L;
    final double energy_mult = 4;
    int decaying_time=100;
    
    public Meat(Container parent , GlobalVariables variables) {
        super(parent, variables.getMeatIcon() , GlobalVariables.MEAT_SIZE);
    }

    @Override
    public double getEnergy() {
        return super.getEnergy() * energy_mult; 
    }
    
    
   
    
    public void decay(){
        decaying_time--;
        
        if(decaying_time<=0){
            Container container = this.getParent();
            Meat meat = this;
            
            D.invokeLater(()->{
                container.remove(meat);
                return null;
            });
            
        }
    }
    
   
    
    
    
  
    
    
}
