
package ecosystem;

import java.awt.Color;
import java.awt.image.RGBImageFilter;


public class ImageColorFilter extends RGBImageFilter{
    Color tobeReplaced;
    Color newColor;
    
    ImageColorFilter(Color tobeReplaced, Color newColor){
        this.tobeReplaced = tobeReplaced;
        this.newColor = newColor;
    }
    
    @Override
    public int filterRGB(int x, int y, int rgb) {
        
        int newRGB;
        
        
        if(rgb == tobeReplaced.getRGB()){
            //replace the old rgb with new rgb if it is equal to the target
             newRGB = newColor.getRGB();
        }
        else{
           //no changes
            newRGB = rgb;
        }
        
      
        return newRGB;
        
    }

  

    
    
    
    
}
