
package ecosystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class MainGUI extends javax.swing.JFrame {

   private static final long serialVersionUID = 1L;
   
   final String savedFileExt = ".evolution"; //the extension name for saved evolution files
   public static String SAVED_DATA_PATH = "";  //where the saved data will be stored
   
   GlobalVariables globalVariables ;   //vaariables than can be changed by the users in gui
   JFrame settingFrame;    //display the settings frame. This will also set the initial globalVariables values
  
   
   ExecutorService executorService;
   int extinction = 0;
   final int nextGenerationSpecie = 10;
   
   Thread evolutionGUIThread;
   
    public MainGUI() {
        initComponents();
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                stopAllThreads();
            }
            
        });
        
     
    }

    
    
    private void startSimulation(List<Component> loadedComponent){
        clearAllComponents(); //reset all
        stopAllThreads(); //stop all the threads if there are running one
        
        
       
        //start a new one if there are no any components loaded
        if(loadedComponent==null){
            extinctionView.setText(String.valueOf(extinction));
            createNewSpecies(0);
            spawnNewFoods();
        }
        else{
            int pcount = 0;
            for(Component c:loadedComponent){
                mainPlatform.add(c);
                
                if(c instanceof Pellet){
                    pcount ++;
                }
            }
            
            //replenishing pellets back because we don't know the last timer for the loaded evolution
            replenishPellet(pcount, globalVariables.getPelletReplenishTimer());
        }
        
      
        
        
        //the pool that execute other threads
        executorService = Executors.newFixedThreadPool(globalVariables.getMaxThread());
       
        
        evolutionGUIThread = new Thread(){
            @Override
            public void run() {
                super.run();
                
                evolutionMainProcess();
            }
            
        };
        
        
        
        
        evolutionGUIThread.start();
        
    }
    
  
    
    
    
    //Responsible for all evolution process such as displaying creatures and food
    private void evolutionMainProcess(){
        
        long pelletSpawnTimer = 0;
        Creature lastCreature=null;
        
        while(true){

            
            Egg lastEgg = null;
            long pelletCount=0;
            long alive = 0;


            
            //run to all components and do the required action
            Component[] components = mainPlatform.getComponents();
            for(Component component:components){

               if(component instanceof Creature){
                    
                    Creature creature = (Creature)component;
                    lastCreature= creature;
                    
                    //This are transient variables , so they tends to be null when loading from file.
                    if(creature.getContainer() == null || creature.getGlobalVariables() == null){
                        creature.setGV(globalVariables);
                        creature.setContainer(mainPlatform);
                    }
                    
                    
                   
                    creature.decide();
                    
                    


                    alive++;

                    //display creature image if it doesn't yet
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            creature.showCreatureImage();
                        }
                    });



                    //adding mouse listeners to creatures. This will be use for displaying the brain and other click events
                    if(creature.getMouseListeners().length==0){
                        creature.addMouseListener(new CreatureMouseListener(creature,nnDisplayView,traitsDisplay));

                    }





                }



                else if(component instanceof Food){

                    if(component instanceof Meat){
                        ((Meat)component).decay();
                    }
                    else{
                        pelletCount++;
                    }

                }




                else if(component instanceof Egg){

                    //no spawing if limit reached
                    if(globalVariables.getPopLimit()<alive){
                        D.invokeLater(()->{
                            mainPlatform.remove(component);
                            return null;
                        });
                    }
                    else{

                        lastEgg = (Egg)component;

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                ((Egg)component).showEggImage();
                            }
                        });


                        lastEgg.timeElapsed();
                        alive++;

                    }


                }



                else if(component instanceof Particle){
                    Particle particle = ((Particle)component);
                    particle.dissipateParticles();
                }




            }







            /*check if all creatures are extinct and then start a new one
              if the one left is asexual , then give him chance to reproduce*/
           if(isExtinct(lastCreature,lastEgg ,alive)){
                extinction++;
                extinctionView.setText(String.valueOf(extinction));
                pelletSpawnTimer = 0;
                
                //ensures that the fittest of the last generation is included
                createFreshClones(lastCreature);
                
           }


           populationView.setText(String.valueOf(alive));

           pelletSpawnTimer = replenishPellet(pelletCount,pelletSpawnTimer);



            pauseFrame();   


        }
    }
    
    
    
    
    
    
    
    //create a fresh or mutated version of the creature
    private void createFreshClones(Creature c){
        
        // if true , then the cloned version won't be similar
        boolean doChanges = false;
        
        for(int i=0;i<nextGenerationSpecie;i++){
            
            try{
                Creature newC = (Creature)Cloner.deepCopy(c);
                //reset all of the values
                newC.setWIllingToBreed(c.MIN_WBREED);
                newC.setEnergy(c.getInitialEnergy());
                newC.setFoodThatCauseSickness(null);
                newC.setAge(0);
                
                
                if(doChanges){
                    //move the cloned creature to a random position
                    Creature dummyCreature = new Creature(mainPlatform);
                    newC.setLocation(dummyCreature.getX(),dummyCreature.getY());
                    
                    //50% chance of mutated version
                    if(D.choose()){
                        newC = Mutator.createNewOffspring(mainPlatform, newC, newC);
                    }
                    
                }
                else{
                    doChanges = true;
                }
                
                
                

                Creature finalVersion = newC;
                D.invokeLater(()->{
                    //add the creature
                    mainPlatform.add(finalVersion);
                    return null;
                });


            }catch(Exception e){

            }
            
        }
    }
    
 
    private long replenishPellet(final long pelletCount , long timer){
        
        
        if(pelletCount<=globalVariables.getPelletCount() && timer >= globalVariables.getPelletReplenishTimer()){
           D.invokeLater(()->{
               for(int i=0;i<globalVariables.getPelletCount()-pelletCount;i++){
                   mainPlatform.add(new Pellet(mainPlatform,globalVariables));
               }

               return null;
           });
           timer = 0;
        }

        timer++;
        
        
        return timer;
    }
    
    
    
    
    
    
    
   
    private boolean isExtinct(Creature lastCreature, Egg lastEgg , long alive){
        boolean extinct = false;
        
        final Creature finalLastCreature = lastCreature;
        
        if(alive<=0 || (alive==1 && !finalLastCreature.isAsexual(finalLastCreature.getReproScale()) && lastEgg==null)){
            D.invokeLater(()->{
                   clearAllComponents();
                   createNewSpecies(nextGenerationSpecie);
                   spawnNewFoods();
                   return null;
            });
                
             
            //refresh the executor. This prevents the previous jobs to be still loading even the generation is already extinct
            executorService.shutdownNow();
            executorService = Executors.newFixedThreadPool(globalVariables.getMaxThread());
            
            
            extinct = true;
            

        }
        
        
        return extinct;
    }
    
    
    
    
    
    
    private void createNewSpecies(int customSpecie){
        for(int i=0;i<globalVariables.getStartingPop() - customSpecie;i++){
            Creature newC = new Creature(mainPlatform);
            newC.setGV(globalVariables);
            mainPlatform.add(newC);
        }
        
       
    }
    
    
    
    
    
    private void spawnNewFoods(){
        for(int i=0;i<globalVariables.getPelletCount();i++){
            mainPlatform.add(new Pellet(mainPlatform,globalVariables));
        }
    }
    
    
    
    
    
    
    private void clearAllComponents(){
        mainPlatform.removeAll();
        nnDisplayView.removeAll();
        traitsDisplay.removeAll();
        
        
        mainPlatform.repaint();
        nnDisplayView.repaint();
        traitsDisplay.repaint();
    }
    
    
    
    
    
    
    private void pauseFrame(){
        try{
            TimeUnit.MILLISECONDS.sleep(globalVariables.getGameDelay());
            mainPlatform.revalidate();
            mainPlatform.repaint();
        }catch(Exception e){

        }
    }
    
    
    
    
    
    
    private void initializeGlobalVariables(){
        globalVariables = new GlobalVariables();
        
        //create the saved data path if not exists
        File savedFolderDir = new File(SAVED_DATA_PATH );
        if(!savedFolderDir.exists()){
            savedFolderDir.mkdir();
        }
        
        
        
        
        String gvFilePath = SAVED_DATA_PATH + SettingsFrame.GLOBAL_VAR_FILENAME;
        
        boolean useDefaultVar = true;   //whether to use the saved variables or the default
        
        
        //check if there is saved global variable file
        if(new File(gvFilePath).exists()){
            try{
                globalVariables = (GlobalVariables)Cloner.getObjectFromFile(gvFilePath);
                useDefaultVar = false;
            }
            catch(Exception e){
               D.print(e.toString());
            }
        }
       
        
        settingFrame = new SettingsFrame(globalVariables, useDefaultVar);
        
    }
    
    
    
    
    
    
    
    private void stopAllThreads(){
        if(evolutionGUIThread!=null){
            evolutionGUIThread.stop();
            evolutionGUIThread = null;
        }
        
        if(executorService!=null){
              executorService.shutdownNow();
        }
    }
    
    
    
    
    //-------------------------------------------LOADING AND SAVING DATA --------------------------------------------
    
    
    public void loadEvoData(String filePath){
        try{
            //get the saved components and start again
            List<Component> components = (List<Component>)Cloner.getObjectFromFile(filePath);
            startSimulation(components);

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Unexpected error occur while loading data" , "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
    
    
    public void saveEvoData(String filename){
        
        /*
           mainPlatform uses grouplayout which is not serializable.
           To combat this , I first set mainPlatform's layout to null and then serialize the component inside.
           After that , I put the original layout back again.
        */


        if(evolutionGUIThread == null){
            JOptionPane.showMessageDialog(this,"Unable to save data. Please start a new evolution first." , "Error" ,JOptionPane.ERROR_MESSAGE);
        }
        
        else{
            GroupLayout gl = (GroupLayout)mainPlatform.getLayout();
            evolutionGUIThread.suspend();  //pause thread


            mainPlatform.setLayout(null);
            List<Component> components = new ArrayList<>();


           
            for(Component c:mainPlatform.getComponents()){
               components.add(c);
            }


            try{
               Cloner.saveObject(filename,components);
               mainPlatform.setLayout(gl);
            }catch (Exception e){
               mainPlatform.setLayout(gl);
               JOptionPane.showMessageDialog(this,"Unexpected error occur while saving data" , "Error" ,JOptionPane.ERROR_MESSAGE);
            }

            evolutionGUIThread.resume();
        }

        
    }
    
    
    
    
    private void initializeGUIDesigns(){
        Color mainBgColor = D.hexToColor("#464357");
        getContentPane().setBackground(mainBgColor);
        
      
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startNew = new javax.swing.JButton();
        extinctionView = new javax.swing.JLabel();
        nnDisplayView = new javax.swing.JPanel();
        extinctionLabel = new javax.swing.JLabel();
        populationLabel = new javax.swing.JLabel();
        populationView = new javax.swing.JLabel();
        settingsBttn = new javax.swing.JButton();
        loadSaveBttn = new javax.swing.JButton();
        traitsDisplay = new javax.swing.JPanel();
        mainPlatform = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        setSize(new java.awt.Dimension(1100, 643));

        startNew.setBackground(new java.awt.Color(63, 111, 191));
        startNew.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        startNew.setForeground(new java.awt.Color(255, 255, 255));
        startNew.setText("Start New");
        startNew.setBorderPainted(false);
        startNew.setFocusable(false);
        startNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startNewActionPerformed(evt);
            }
        });

        extinctionView.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        extinctionView.setForeground(new java.awt.Color(255, 255, 255));
        extinctionView.setText("0");

        nnDisplayView.setBackground(new java.awt.Color(95, 92, 110));

        javax.swing.GroupLayout nnDisplayViewLayout = new javax.swing.GroupLayout(nnDisplayView);
        nnDisplayView.setLayout(nnDisplayViewLayout);
        nnDisplayViewLayout.setHorizontalGroup(
            nnDisplayViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );
        nnDisplayViewLayout.setVerticalGroup(
            nnDisplayViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );

        extinctionLabel.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        extinctionLabel.setForeground(new java.awt.Color(255, 255, 255));
        extinctionLabel.setText("Extinction:");

        populationLabel.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        populationLabel.setForeground(new java.awt.Color(255, 255, 255));
        populationLabel.setText("Population:");

        populationView.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        populationView.setForeground(new java.awt.Color(255, 255, 255));
        populationView.setText("0");

        settingsBttn.setBackground(new java.awt.Color(63, 111, 191));
        settingsBttn.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        settingsBttn.setForeground(new java.awt.Color(255, 255, 255));
        settingsBttn.setText("Settings");
        settingsBttn.setBorderPainted(false);
        settingsBttn.setFocusable(false);
        settingsBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsBttnActionPerformed(evt);
            }
        });

        loadSaveBttn.setBackground(new java.awt.Color(63, 111, 191));
        loadSaveBttn.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        loadSaveBttn.setForeground(new java.awt.Color(255, 255, 255));
        loadSaveBttn.setText("Load / Save");
        loadSaveBttn.setBorderPainted(false);
        loadSaveBttn.setFocusable(false);
        loadSaveBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSaveBttnActionPerformed(evt);
            }
        });

        traitsDisplay.setBackground(new java.awt.Color(95, 92, 110));

        javax.swing.GroupLayout traitsDisplayLayout = new javax.swing.GroupLayout(traitsDisplay);
        traitsDisplay.setLayout(traitsDisplayLayout);
        traitsDisplayLayout.setHorizontalGroup(
            traitsDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        traitsDisplayLayout.setVerticalGroup(
            traitsDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );

        mainPlatform.setBackground(new java.awt.Color(54, 54, 54));

        javax.swing.GroupLayout mainPlatformLayout = new javax.swing.GroupLayout(mainPlatform);
        mainPlatform.setLayout(mainPlatformLayout);
        mainPlatformLayout.setHorizontalGroup(
            mainPlatformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPlatformLayout.setVerticalGroup(
            mainPlatformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(extinctionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extinctionView, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112)
                        .addComponent(populationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(populationView, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(startNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loadSaveBttn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(settingsBttn))
                    .addComponent(mainPlatform, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nnDisplayView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(traitsDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadSaveBttn)
                    .addComponent(extinctionView)
                    .addComponent(populationView)
                    .addComponent(populationLabel)
                    .addComponent(extinctionLabel)
                    .addComponent(startNew)
                    .addComponent(settingsBttn))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nnDisplayView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(traitsDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadSaveBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSaveBttnActionPerformed

        LoadSaveDataFrame dataFrame = new LoadSaveDataFrame(this,SAVED_DATA_PATH,savedFileExt);
        dataFrame.setVisible(true);

    }//GEN-LAST:event_loadSaveBttnActionPerformed

    private void settingsBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsBttnActionPerformed

        settingFrame.setVisible(true);
    }//GEN-LAST:event_settingsBttnActionPerformed

    private void startNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startNewActionPerformed

        extinction=0;
        startSimulation(null);
    }//GEN-LAST:event_startNewActionPerformed

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI gui = new MainGUI();
               
                D.alignCenter(gui);
                
                
                //get the current program path. This will be used in saving and retrieving file
                try{
                    File file = new File(MainGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                    gui.SAVED_DATA_PATH = file.getParent() + "/Evolution Data/";
                }catch(Exception e){
                
                }
                
                
                
                gui.initializeGUIDesigns();
                gui.initializeGlobalVariables();
                gui.setVisible(true);
                gui.setResizable(false);
                
            }
        });
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel extinctionLabel;
    private javax.swing.JLabel extinctionView;
    private javax.swing.JButton loadSaveBttn;
    private javax.swing.JPanel mainPlatform;
    private javax.swing.JPanel nnDisplayView;
    private javax.swing.JLabel populationLabel;
    private javax.swing.JLabel populationView;
    private javax.swing.JButton settingsBttn;
    private javax.swing.JButton startNew;
    private javax.swing.JPanel traitsDisplay;
    // End of variables declaration//GEN-END:variables
}
