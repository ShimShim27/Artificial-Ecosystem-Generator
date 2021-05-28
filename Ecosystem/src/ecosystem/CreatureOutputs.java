
package ecosystem;

import java.util.ArrayList;
import java.util.List;


public class CreatureOutputs {
    final static private int moveCounts = 8;
    final static private int wantsCount = 3;
    final static private int phermoneCount = 1;
    
    //getting the outputs
    public static List<Double[]> getOutputs(double inputs[],Brain brain){
        
        
        List<Double[]> outputLists = new ArrayList<>();
        
        
        double rawOutputs[] = brain.runBrain(inputs);
        
        
        //split the raw outputs
        double rawMovements[] = getValuesFromRaw(rawOutputs,0,moveCounts-1);
        double rawWants[] = getValuesFromRaw(rawOutputs,moveCounts ,(moveCounts+wantsCount) -1);
        double rawPhermone[] = getValuesFromRaw(rawOutputs , (moveCounts+wantsCount) , (moveCounts+wantsCount+phermoneCount)-1);
     
       
        
        Double finalMove[] = converToNonPrimi(rawMovements);
        Double finalWants[] = converToNonPrimi(rawWants);
        Double finalPhermone[] = converToNonPrimi(rawPhermone);
        
        
        outputLists.add(finalMove);
        outputLists.add(finalWants);
        outputLists.add(finalPhermone);
        
        return outputLists;
    }
    
    
    
    
    private static Double[] converToNonPrimi(double primitive[]){
        Double[] nonPrimi = new Double[primitive.length];
        for(int i=0;i<primitive.length;i++) nonPrimi[i] = primitive[i];
         
        return nonPrimi;
    }
    
    
    
    private static double[] getValuesFromRaw(double raw[] , int startIndex , int endIndex){
        double newArr[] = new double[(endIndex - startIndex)+1];
        
        int newArrInd = 0;
        for(int i=startIndex; i<=endIndex;i++){
            newArr[newArrInd] = raw[i];
            newArrInd++;
        }
        
        return newArr;
    }
    
}
