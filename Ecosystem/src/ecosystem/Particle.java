
package ecosystem;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Particle extends JPanel {

    private static final long serialVersionUID = 1L;
    
    
    long duration;   
    JLabel particleImage;

    
    public Particle() {
        particleImage = new JLabel();
        this.add(particleImage);
    }
    
    
    
    
    //this set the values needed as a particle
    public void initializeValue(Creature target , ImageIcon icon , long mduration , int size){
        this.setOpaque(false);
        
        particleImage.setIcon(icon);
        duration = mduration;
       
        
        int xpos = (int)D.random(target.getX() - 1, target.getX() + target.getWidth()+1 );
        int ypos = (int)D.random(target.getY() - 1,target.getY() + target.getHeight()/2);
        Rectangle bounds = new Rectangle(xpos,ypos , size,size);
        
        this.setBounds(bounds);
    }
    
    //remove the particles if duration is finish
    public void dissipateParticles(){
        duration--;
        Particle p = this;
        
        if(duration<=0){
            D.invokeLater(()->{
                p.getParent().remove(p);
                return null;
            });
        }
        
    }
    
    
  
    
}
