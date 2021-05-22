
package ecosystem;

import java.awt.Container;


public class Pellet extends Food{

    private static final long serialVersionUID = 1L;
   
    Pellet(Container parent,GlobalVariables variables){
        super(parent,variables.getPelletIcon() , GlobalVariables.PELLET_SIZE);
    }
    
    
    
    
    
    
}