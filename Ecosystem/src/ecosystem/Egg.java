
package ecosystem;

import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Egg extends JPanel{

    private static final long serialVersionUID = 1L;
    final String eggImageName = "egg.png";
    final int eggSize = 20;
    
    Creature hatchLing;
    int hatchingTime=0;
    
    JLabel eggImageDisplay;
    
    public Egg(Creature c,int h){
        hatchLing = c;
        hatchingTime = h;
       
        this.setOpaque(false);
        this.setSize(eggSize,eggSize);
        
        eggImageDisplay = new JLabel();
        this.add(eggImageDisplay);
        
        
    }
    
    
    public void timeElapsed(){
        hatchingTime--;
        if(hatchingTime<=0){
            Egg thisEgg = this;
            Container mContainer = thisEgg.getParent();
            
            D.invokeLater(()->{
                mContainer.add(hatchLing);
                mContainer.remove(thisEgg);
                return null;
            });
        }
        
    }
    
    
    
    
    public void showEggImage(){
        D.showComponentsImage(eggImageDisplay, eggSize, eggSize, eggImageName, hatchLing.getSkinColor());
       
    }
    
}
