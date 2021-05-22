
package ecosystem;


public class BloodSplash extends Particle{

    private static final long serialVersionUID = 1L;

    public BloodSplash(Creature c) {
     
        initializeValue(c ,c.getGlobalVariables().getBloodIcon() , 10 , GlobalVariables.BLOOD_SIZE);
      
    }
    
    
    
    
}
