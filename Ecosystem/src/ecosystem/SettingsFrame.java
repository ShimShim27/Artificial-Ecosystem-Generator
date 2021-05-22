
package ecosystem;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public final class SettingsFrame extends javax.swing.JFrame {
    public static final String GLOBAL_VAR_FILENAME = "globalvar.evolutionsettings";
    private static final long serialVersionUID = 1L;
    
    private  GlobalVariables variables;
    public SettingsFrame(GlobalVariables v , boolean useDefaultVar) {
        variables = v;
        variables.loadStaticImages(); //load all the static images
        
        initComponents();
        
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        D.alignCenter(this);
       
        
        //If there are saved settings , then don't use default variables
        if(useDefaultVar){
            defaultGlobalVariables();
        }
        else{
            displayVariables();
        }
        
        
        
        addInputFilters();
       
        
        
        
        //on close button press , make the input fields back to its early states 
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                displayVariables();
            }
            
        });
       
    }

    
    
    
    
    
    //add input filters
    private void addInputFilters(){
        //the regex
        final String doublesFilter = "(\\d{0,5})(\\.(\\d{0,10}))?";
        final String intFilter5dgt = "\\d{0,5}";
        final String intFilter3dgt = "\\d{0,3}";
        
        brainMutationFrequency.addKeyListener(new InputFilter(brainMutationFrequency,intFilter5dgt));
        mutationProbability.addKeyListener(new InputFilter(mutationProbability,doublesFilter));
        mutationRate.addKeyListener(new InputFilter(mutationRate,doublesFilter));
        crossoverProbability.addKeyListener(new InputFilter(crossoverProbability,doublesFilter));
        notInheritedProb.addKeyListener(new InputFilter(notInheritedProb, doublesFilter));
        
        popLimit.addKeyListener(new InputFilter(popLimit, intFilter3dgt));
        startingPop.addKeyListener(new InputFilter(startingPop, intFilter3dgt));
        pelletCount.addKeyListener(new InputFilter(pelletCount, intFilter3dgt));
        pelletTimer.addKeyListener(new InputFilter(pelletTimer, intFilter5dgt));
        
        
        gameDelay.addKeyListener(new InputFilter(gameDelay, intFilter5dgt));
        maxThread.addKeyListener(new InputFilter(maxThread, intFilter3dgt));
      
    }
    
    
    
    
    
    
    
    //reset the values back to default
    public void defaultGlobalVariables(){
        
        variables.setMutation_rate(0.6969);
        variables.setMutation_prob(5);
        variables.setBrainMutationFrequency(2);
        variables.setCrossover_prob(97);
        variables.setNotInheritedProb(0);
        
        variables.setPopLimit(100);
        variables.setStartingPop(50);
        variables.setPelletCount(300);
        variables.setPelletReplenishTimer(150);



        variables.setGameDelay(50);
        variables.setMaxThread(5);
        
        
       displayVariables();
    }
    
 
    
    
    //show the variables in input box
    private void displayVariables(){
        brainMutationFrequency.setText(String.valueOf(variables.getMutationFrequency()));
        mutationProbability.setText(String.valueOf(variables.getMutation_prob()));
        mutationRate.setText(String.valueOf(variables.getMutation_rate()));
        crossoverProbability.setText(String.valueOf(variables.getCrossover_prob()));
        notInheritedProb.setText(String.valueOf(variables.getNotInheritedProb()));
        
        popLimit.setText(String.valueOf(variables.getPopLimit()));
        startingPop.setText(String.valueOf(variables.getStartingPop()));
        pelletCount.setText(String.valueOf(variables.getPelletCount()));
        pelletTimer.setText(String.valueOf(variables.getPelletReplenishTimer()));
        
        
        gameDelay.setText(String.valueOf(variables.getGameDelay()));
        maxThread.setText(String.valueOf(variables.getMaxThread()));
      
        
    }
    
    
    
    
    //save the variables
    private void saveNewVariables(){
       try{
            variables.setBrainMutationFrequency(Integer.parseInt(String.valueOf(brainMutationFrequency.getText())));
            variables.setCrossover_prob(Double.parseDouble(String.valueOf(crossoverProbability.getText())));
            variables.setMutation_prob(Double.parseDouble(mutationProbability.getText()));
            variables.setMutation_rate(Double.parseDouble(mutationRate.getText()));
            variables.setNotInheritedProb(Double.parseDouble(notInheritedProb.getText()));
            
            variables.setPopLimit(Integer.parseInt(popLimit.getText()));
            variables.setStartingPop(Integer.parseInt(startingPop.getText()));
            variables.setPelletCount(Integer.parseInt(pelletCount.getText()));
            variables.setPelletReplenishTimer(Integer.parseInt(pelletTimer.getText()));
            
            variables.setGameDelay(Integer.parseInt(gameDelay.getText()));
            variables.setMaxThread(Integer.parseInt(maxThread.getText()));
           
            
            saveVariablesAsFile();
            
            displayVariables();
            this.setVisible(false);
            
            
       }catch(NumberFormatException e){
           JOptionPane.showMessageDialog(this, "Please fill out all the inputs correctly" , "Invalid",JOptionPane.WARNING_MESSAGE);
       }
      
        
    }    
    
    
    
    
    private void saveVariablesAsFile(){
        try{
            Cloner.saveObject(MainGUI.SAVED_DATA_PATH +  GLOBAL_VAR_FILENAME , variables);
        }
        catch(Exception e){
            
        }
    }

    
    
  
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        mutationRate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        brainMutationFrequency = new javax.swing.JTextField();
        mutationProbability = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        crossoverProbability = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        popLimit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        startingPop = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pelletCount = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        pelletTimer = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        gameDelay = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        maxThread = new javax.swing.JTextField();
        resetBttn = new javax.swing.JButton();
        saveChanges = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        notInheritedProb = new javax.swing.JTextField();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        jPanel1.setBackground(new java.awt.Color(70, 67, 87));

        mutationRate.setBackground(new java.awt.Color(95, 92, 110));
        mutationRate.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        mutationRate.setForeground(new java.awt.Color(255, 255, 255));
        mutationRate.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        mutationRate.setCaretColor(java.awt.Color.white);
        mutationRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mutationRateActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mutation Rate");

        jLabel2.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Brain Mutation Frequency");

        brainMutationFrequency.setBackground(new java.awt.Color(95, 92, 110));
        brainMutationFrequency.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        brainMutationFrequency.setForeground(new java.awt.Color(255, 255, 255));
        brainMutationFrequency.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        brainMutationFrequency.setCaretColor(java.awt.Color.white);
        brainMutationFrequency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brainMutationFrequencyActionPerformed(evt);
            }
        });

        mutationProbability.setBackground(new java.awt.Color(95, 92, 110));
        mutationProbability.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        mutationProbability.setForeground(new java.awt.Color(255, 255, 255));
        mutationProbability.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        mutationProbability.setCaretColor(java.awt.Color.white);
        mutationProbability.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mutationProbabilityActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mutation Probability");

        jLabel5.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Crossover Proability");

        crossoverProbability.setBackground(new java.awt.Color(95, 92, 110));
        crossoverProbability.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        crossoverProbability.setForeground(new java.awt.Color(255, 255, 255));
        crossoverProbability.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        crossoverProbability.setCaretColor(java.awt.Color.white);
        crossoverProbability.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crossoverProbabilityActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Population Limit");

        popLimit.setBackground(new java.awt.Color(95, 92, 110));
        popLimit.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        popLimit.setForeground(new java.awt.Color(255, 255, 255));
        popLimit.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        popLimit.setCaretColor(java.awt.Color.white);

        jLabel7.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Starting Population");

        startingPop.setBackground(new java.awt.Color(95, 92, 110));
        startingPop.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        startingPop.setForeground(new java.awt.Color(255, 255, 255));
        startingPop.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        startingPop.setCaretColor(java.awt.Color.white);
        startingPop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startingPopActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Pellet Count");

        pelletCount.setBackground(new java.awt.Color(95, 92, 110));
        pelletCount.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        pelletCount.setForeground(new java.awt.Color(255, 255, 255));
        pelletCount.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        pelletCount.setCaretColor(java.awt.Color.white);

        jLabel10.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Pellet Spawn Timer");

        pelletTimer.setBackground(new java.awt.Color(95, 92, 110));
        pelletTimer.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        pelletTimer.setForeground(new java.awt.Color(255, 255, 255));
        pelletTimer.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        pelletTimer.setCaretColor(java.awt.Color.white);

        jLabel11.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Game Delay (ms)");

        gameDelay.setBackground(new java.awt.Color(95, 92, 110));
        gameDelay.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        gameDelay.setForeground(new java.awt.Color(255, 255, 255));
        gameDelay.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        gameDelay.setCaretColor(java.awt.Color.white);

        jLabel12.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Maximum Thread");

        maxThread.setBackground(new java.awt.Color(95, 92, 110));
        maxThread.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        maxThread.setForeground(new java.awt.Color(255, 255, 255));
        maxThread.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        maxThread.setCaretColor(java.awt.Color.white);

        resetBttn.setBackground(new java.awt.Color(63, 111, 191));
        resetBttn.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        resetBttn.setForeground(new java.awt.Color(255, 255, 255));
        resetBttn.setText("Reset");
        resetBttn.setBorderPainted(false);
        resetBttn.setFocusable(false);
        resetBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBttnActionPerformed(evt);
            }
        });

        saveChanges.setBackground(new java.awt.Color(63, 111, 191));
        saveChanges.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        saveChanges.setForeground(new java.awt.Color(255, 255, 255));
        saveChanges.setText("Save Changes");
        saveChanges.setBorderPainted(false);
        saveChanges.setFocusable(false);
        saveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChangesActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("MS UI Gothic", 1, 38)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Settings");

        jLabel20.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Not Inherited Probability");

        notInheritedProb.setBackground(new java.awt.Color(95, 92, 110));
        notInheritedProb.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        notInheritedProb.setForeground(new java.awt.Color(255, 255, 255));
        notInheritedProb.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        notInheritedProb.setCaretColor(java.awt.Color.white);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(355, 355, 355))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel9)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(saveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(crossoverProbability)
                                .addComponent(popLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(mutationRate, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(brainMutationFrequency, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mutationProbability, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(notInheritedProb, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel12))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(maxThread)
                                    .addComponent(startingPop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pelletCount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(gameDelay)
                                    .addComponent(pelletTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel13)
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel1))
                            .addComponent(mutationRate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(brainMutationFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(mutationProbability, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(crossoverProbability, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(popLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pelletCount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pelletTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(gameDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maxThread, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7))
                            .addComponent(startingPop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(notInheritedProb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(resetBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(saveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mutationRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mutationRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mutationRateActionPerformed

    private void saveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChangesActionPerformed
        saveNewVariables();
    }//GEN-LAST:event_saveChangesActionPerformed

    private void resetBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBttnActionPerformed
        this.setVisible(false);
        defaultGlobalVariables();
        saveVariablesAsFile();
    }//GEN-LAST:event_resetBttnActionPerformed

    private void mutationProbabilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mutationProbabilityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mutationProbabilityActionPerformed

    private void brainMutationFrequencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brainMutationFrequencyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brainMutationFrequencyActionPerformed

    private void crossoverProbabilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crossoverProbabilityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_crossoverProbabilityActionPerformed

    private void startingPopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startingPopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startingPopActionPerformed

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField brainMutationFrequency;
    private javax.swing.JTextField crossoverProbability;
    private javax.swing.JTextField gameDelay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField maxThread;
    private javax.swing.JTextField mutationProbability;
    private javax.swing.JTextField mutationRate;
    private javax.swing.JTextField notInheritedProb;
    private javax.swing.JTextField pelletCount;
    private javax.swing.JTextField pelletTimer;
    private javax.swing.JTextField popLimit;
    private javax.swing.JButton resetBttn;
    private javax.swing.JButton saveChanges;
    private javax.swing.JTextField startingPop;
    // End of variables declaration//GEN-END:variables
}
