
package ecosystem;


public class Phermone extends Particle{
    
    private static final long serialVersionUID = 1L;

    public Phermone(Creature c) {
        initializeValue(c , c.getGlobalVariables().getPhermondIcon(), 7 , GlobalVariables.PHERMONE_SIZE);
    }
    
    
    
}
