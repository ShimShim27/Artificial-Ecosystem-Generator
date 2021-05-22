package ecosystem;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.util.Random;
import java.util.concurrent.Callable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class D {
    
    public static double random(double min , double max){
        return min + (max - min) * new Random().nextDouble();
    }
    
    
    
    
    /*produces either true or false
    useful in two deecisions*/
    public static boolean  choose(){
        boolean chose = false;
        if(((int)random(0,2))==1){
            chose= true;
        }
                
        return chose;
    }
    
    
    
    
     public  static void print(Object o){
        System.out.println(o);
    }
    
     
     
     
     
     public static double distance(double x1,double x2,double y1,double y2){
         return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
     }
     
     
     
     
     
     
     //convert a range to range -1 to 1
    public static double oneToNegaOne(double max,double min,double oldvalue){
        return convertRange(max, min , oldvalue , 1, -1);
    }
    
    
    //converting from one range to another
    public static double convertRange(double oldMax,double oldMin,double oldvalue, double newMax , double newMin){
        double oldRange = oldMax-oldMin;
        double newRange = newMax - newMin;
        double newval = (((oldvalue - oldMin) * newRange)/oldRange) + newMin;
        
        return newval;
    }
    
    
    
    //adding or removing contents in jswing produces error if not using this
    public static void invokeLater(Callable<Object> callable){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    callable.call();
                }catch(Exception e){
                    
                }
            }
        });
        
        
    }
    
    
    
    
    
    
    
    public static Color hexToColor(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }   
    
    
    
    public static String colorToHex(Color color){
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());  
    }
    
    
    //aligning any container in center of the screen
    public static void alignCenter(Container container){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        container.setLocation(dimension.width/2 - container.getWidth()/2 , dimension.height/2 - container.getHeight()/2);
    }
    
    
    
    //this shows the custom image of the creature , food and etc
    public  static void showComponentsImage(JLabel imageDisplay, int width , int height , String image_path , Color newColor){
        //load image if not yet
        if(imageDisplay.getName()==null){
            imageDisplay.setName(".");
            
            //using context class loader to load image
            ImageIcon imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(image_path));
            imageDisplay.setBounds(0,0,width,height);


            //replace white pixels with custom color
            ImageProducer ip = new FilteredImageSource(imageIcon.getImage().getSource(),new ImageColorFilter(Color.WHITE, newColor)); 
            imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(ip));




            //scale it to fit
            final ImageIcon fImageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width ,height -4, Image.SCALE_SMOOTH));
            
            
            //ensures that icon are not yet set
             if(imageDisplay.getIcon()== null){
                    imageDisplay.setIcon(fImageIcon);
            }
           
                        
        }
        
    }
    
    
        
    //check if a probabbility happened with the highest 100 percent
    public static boolean chanceHappened(double probability){
        boolean happened = false;
        
        //ensures that probability is always max of 100
        if(probability>100){
            probability = 100;
        }
        
       
        //(int)ensures that 100 is the highest percent
        int happening = (int)D.random(0, 101);
        
        
        if(happening<Math.abs(probability)){
            happened = true;
            
        }
       
        return happened;
    }
    
    
    
   
    
}

