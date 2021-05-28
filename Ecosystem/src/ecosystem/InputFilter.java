
package ecosystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;


public class InputFilter extends KeyAdapter {

    JTextField tField;
    String regex;
    public InputFilter(JTextField tf , String r) {
        tField = tf;
        regex = r;
    }
    
    
    
    
    @Override
    public void keyPressed(KeyEvent ke) {
        super.keyPressed(ke); 
        
        
        if(ke.getKeyCode() != KeyEvent.VK_BACK_SPACE &&
                ke.getKeyCode() != KeyEvent.VK_LEFT &&
                ke.getKeyCode() != KeyEvent.VK_RIGHT){
            
            
            StringBuffer sb = new StringBuffer(tField.getText().toString());
            sb.insert(tField.getCaretPosition(), ke.getKeyChar());
            
            
            
            
            //checking if it matches the regex. Backspaces are ignored
            if(sb.toString().matches(regex)){
                tField.setEditable(true);
            }
            else if(ke.getKeyCode() != KeyEvent.VK_BACK_SPACE){
               tField.setEditable(false);
            }
            else{
                tField.setEditable(true);
            }
            
        }
        
       
       
                
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        tField.setEditable(true);
    }
    
    
    
    
    
    
    
}
