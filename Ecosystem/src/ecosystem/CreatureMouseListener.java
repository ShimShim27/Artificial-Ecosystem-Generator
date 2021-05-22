
package ecosystem;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class CreatureMouseListener implements MouseListener{
    Creature creature;
    Container bDisplay;
    Container tDisplay;
    
    public CreatureMouseListener(Creature c,Container bDisplay , Container tDisplay){
        creature = c;
        this.bDisplay = bDisplay;
        this.tDisplay = tDisplay;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
      

    }

    @Override
    public void mousePressed(MouseEvent e) {
        D.invokeLater(()->{
            displayBrain();
            displayTraits();
            
            return null;
        });
       
    }

    
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
    
    
    
    
    
    
    // -------------------------------------- CUSTOM METHODS -------------------------------------------
    
    private void displayBrain(){
        bDisplay.removeAll();
        BrainDisplay brainDisplay = new BrainDisplay(creature.getBrain());

        //adding a scrollable because brain layers tends to be large
        JScrollPane jsp = new JScrollPane(brainDisplay);
        jsp.setSize(new Dimension(bDisplay.getWidth(),bDisplay.getHeight()));

        bDisplay.add(jsp);


        bDisplay.revalidate();
        bDisplay.repaint();
       
    }
    
    
    
    
    
    
    private void displayTraits(){
        tDisplay.removeAll();
        
        //create a scrollable pane
        JPanel mainDisplayPanel = new JPanel();
        mainDisplayPanel.setLayout(null);
        mainDisplayPanel.setBackground(D.hexToColor("#545659"));
        
        JScrollPane scrollPane = new JScrollPane(mainDisplayPanel);
        scrollPane.setSize(tDisplay.getWidth(),tDisplay.getHeight());
        tDisplay.add(scrollPane);
        
        
       
        addReplicaCreature(mainDisplayPanel);

        
        
        
        //this will be the map used to display the traits
        Map<String, Object> traits = new HashMap<>();
        traits.put("Mass", String.format("%.2f", creature.getMass()));
        traits.put("Intitial Energy", String.format("%.2f", creature.getInitialEnergy()));
        traits.put("Speed", String.format("%.2f", creature.getSpeed()));
        traits.put("Eye Sight", String.format("%.2f", creature.getEyeSight()));
        traits.put("Damage", String.format("%.2f", creature.getDamage()));
        traits.put("Skin Color", D.colorToHex(creature.getSkinColor()));
        traits.put("Hatching Time" , String.format("%.2f", creature.getHatchTime()));
      

        

        //set reproduction type
        String reproType = "";
        if(creature.isAsexual(creature.getReproScale())){
            reproType = "ASEXUAL";
        }
        else{
            reproType = "SEXUAL";
        }
        traits.put("Reproduction Type", reproType);






        //set feeding type
        String fTypeString = "OMNIVORE";
        int feedingType = creature.getFeedingType();
        if(feedingType == creature.HERBIVORE){
           fTypeString = "HERBIVORE";
        }
        else if(feedingType == creature.CARNIVORE){
           fTypeString = "CARNIVORE";
        }

        traits.put("Feeding Type", fTypeString);




        //this serves as the height for the panel inside a jscroll
        int lastPost = addTraitsJLabel(mainDisplayPanel , traits,(int)creature.getMass()+2);





        mainDisplayPanel.setPreferredSize(new Dimension(scrollPane.getWidth(),lastPost));
        
    }
    
    
    
    //create replica for showing purposes
    private void  addReplicaCreature(JPanel mainDisplayPanel){
       
        Creature dummyCreature = new Creature(tDisplay);
        dummyCreature.setSize(creature.getWidth(),creature.getHeight());
        dummyCreature.setSkinColor(creature.getRedSkinColor(),creature.getGreenSkinColor(),creature.getBlueSkinColor());
        dummyCreature.showCreatureImage();
        
        //align the replica in the middle
        int xpos = (tDisplay.getWidth()/2) - (dummyCreature.getWidth()/2);
        dummyCreature.setLocation(xpos,0);
        
        
        mainDisplayPanel.add(dummyCreature);
        
      
      
        
    }
    
    
    
    
    
    
    
    
    private int addTraitsJLabel(JPanel mainDisplayPanel , Map<String,Object> traits , int startY){
        //serves as the height and distance of the labels
        final int labelHeight = 30;
        
        
        int ypos = startY;
        
        for(String trait:traits.keySet()){
            
          //  String text = String.format("%s:  %s", trait , traits.get(trait));
            String t1 = String.format("<font color='#dfc7ff' size=4>%s: </font>", trait);
            String t2 = String.format("<font color='#FFFFFF' size=3>%s </font>", traits.get(trait));
            
            JLabel label = new JLabel(String.format("<html>&nbsp;&nbsp;%s&nbsp;&nbsp;%s</html>", t1,t2));
            //label.setForeground(Color.WHITE);
            
            //getting the font size to determine the width of jlabel
            Font font = label.getFont();
            int width = label.getFontMetrics(font).stringWidth(t1);
            label.setBounds(0,ypos,width,labelHeight);
          
          
            
            mainDisplayPanel.add(label);
            
            ypos+=labelHeight;
        }
        
        
        //we can determine the last size of the panel which is useful in scrollable
        return ypos;
    }
}
