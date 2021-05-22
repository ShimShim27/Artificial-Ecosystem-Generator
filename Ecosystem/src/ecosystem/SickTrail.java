
package ecosystem;


public class SickTrail extends Particle{

    private static final long serialVersionUID = 1L;
    
    SickTrail(Creature c){
        initializeValue(c , c.getGlobalVariables().getSickIcon(),  6 , GlobalVariables.SICK_SIZE);
    }
    
    
    
   
}
