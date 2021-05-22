
package ecosystem;


public class W2BreedParticle extends Particle{

    private static final long serialVersionUID = 1L;
   
    public W2BreedParticle(Creature c) {
        initializeValue(c ,c.getGlobalVariables().getBreedIcon() ,  5  ,GlobalVariables.BREED_SIZE);
    }
    
    
    
}
