
package ecosystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Cloner {
    
    public static Object deepCopy(Object inpObj) throws Exception{
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        ObjectOutputStream outputStream = new ObjectOutputStream(bos);
        outputStream.writeObject(inpObj);
        outputStream.flush();
        
        
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream inputStream = new ObjectInputStream(bis);   
        
        
        return inputStream.readObject();
    }
    
    
    
    
    
    
    
    public static void saveObject(String fileName , Object object) throws Exception{
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        
        oos.writeObject(object);
        oos.flush();
        fos.close();
    }
    
    
    
    
   
    
    public static Object getObjectFromFile(String fileName ) throws Exception{
        Object readObj;
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        readObj = ois.readObject();
        
        ois.close();
        fis.close();
        
        
        return readObj;
        
    }
    
    
}
