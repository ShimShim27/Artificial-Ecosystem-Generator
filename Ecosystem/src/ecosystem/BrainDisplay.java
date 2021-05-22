
package ecosystem;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;


public class BrainDisplay extends JPanel{
    final double nodeSize = 10;
    final double nodeXDist = 50;
    final double nodeYDist = 10;
    final float maxSynapseThickness = 2.5f;
    
    final Color nodeColor = Color.GREEN;
    final Color inactiveColor = Color.GRAY;
    final Color negaSynapseColor = Color.RED;
    final Color posSynapseColor = Color.BLUE;
    
    
    final float leftPadding = 10;
    final float topPadding = 10;
    final float rightPadding = 10;
    final float bottomPadding = 10;
    
    List<List<Node>> layers;

    
    
    
    int maxlayerSize;
    
    double maxLayerHeight;
    double maxLayerWidth;
    
    
    public BrainDisplay(Brain b) {
        layers = b.getLayer();
        
        //the size of the biggest layer vertically
        maxlayerSize = getMaxLayerSize();
        
        //getting the inital y position. This is the responsible to aligning the nodes center vertical
        maxLayerHeight = (nodeSize * maxlayerSize ) + (maxlayerSize * nodeYDist) + topPadding + bottomPadding;
        
        
        maxLayerWidth= (layers.size() * nodeSize) + (layers.size() * nodeXDist) + leftPadding + rightPadding;
        
        
        //important for jscrolling
        this.setPreferredSize(new Dimension((int)maxLayerWidth, (int)maxLayerHeight));
        
        //this is not important , but i will still set this
        this.setBounds(0, 0, (int)maxLayerWidth+1 , (int)maxLayerHeight+1);
        
        this.setBackground(Color.BLACK);
    }
    
    
    
 

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        
        //this will be use to map the node position in order to draw the synapses
        Map<Node,double[]> nodePositionMap = new HashMap<>();
        
        drawNodes(g,nodePositionMap);
        drawSynapse(g, nodePositionMap);
    }
    
    
    
    
   
    private void drawNodes(Graphics g, Map<Node,double[]> nodePositionMap){
        
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
        
        double x_pos =leftPadding;
        for(int layer=0;layer<layers.size();layer++){
            List<Node> currentLayer = layers.get(layer);
            
            
            /*this will be the initial y position. This is base on the maximum height.
            This also ensures that the layer is center vertical*/
            double y_pos = maxLayerHeight - ((currentLayer.size()  * nodeSize) + (currentLayer.size() * nodeYDist)) + topPadding;
            y_pos/=2.0;
            
            
            
            for(Node node:currentLayer){
                
                if(node.isActive()){
                    g2d.setPaint(nodeColor);
                }
                else{
                    g2d.setPaint(inactiveColor);
                }
                
                //draw nodes
                g2d.fill(new Ellipse2D.Double(x_pos, y_pos, nodeSize, nodeSize));
                
                
                //add the nodes to the position map
                nodePositionMap.put(node, new double[]{x_pos,y_pos});
                
                
                y_pos+=nodeSize + nodeYDist;
            }
            
            x_pos +=nodeSize + nodeXDist;
            
        }
        
    }
    
    
    
    private void drawSynapse(Graphics g,Map<Node,double[]> nodePositionMap){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
        
        for(Node fromNode:nodePositionMap.keySet()){
            double fromPos[] = nodePositionMap.get(fromNode);
            double fromX = (float)fromPos[0];
            double fromY = (float)fromPos[1];
            
            
            
            
            for(Synapse synapse:fromNode.getSynapses()){
                
                Node toNode = synapse.getToNode();
               
                //draw the connection if to node is not yet deleted
                
                double[] toPos = nodePositionMap.get(toNode);

                double toX = toPos[0];
                double toY = toPos[1];

                double halfNodeSize = nodeSize/2.0;


                double weight = synapse.getWeight();

               //synapse color base on weight and states
               
               if(!synapse.isActive()){
                   g2d.setPaint(inactiveColor);
               }
               else if(weight>0){
                    g2d.setPaint(posSynapseColor);
                }
                else{
                    g2d.setPaint(negaSynapseColor);
                }

                //synapse thickness base on weight
                float thickness = Math.abs((float)(weight*maxSynapseThickness));
                float halfThickness = thickness/2;
                
                g2d.setStroke(new BasicStroke(thickness));

                //this draw the synapse between nodes and ensuring that the position of synapse is center verical
                g2d.draw(new Line2D.Double(fromX+nodeSize,fromY + halfNodeSize + halfThickness, toX, toY + halfNodeSize + halfThickness));
               
                
                
                
                
                
            }
            
            
        }
        
    }
    
   
    private int getMaxLayerSize(){
        int max=0;
        for(int layer=0;layer<layers.size();layer++){
            int layerSize = layers.get(layer).size();
            if(layerSize>max){
                max = layerSize;
            }
        }
        
        return max;
    }
    
    
}
