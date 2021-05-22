
package ecosystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;


public class LoadSaveDataFrame extends javax.swing.JFrame {
    final Color selectedColor = D.hexToColor("#8551c9");
    
    String filePath;
    String extension;
    JLabel selectedData;
    MainGUI mainGui;
    
    
    public LoadSaveDataFrame(MainGUI mg , String f,String e ) {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
       
        D.alignCenter(this);
        
        mainPanelView.setLayout(new BoxLayout(mainPanelView, BoxLayout.Y_AXIS));
        
        mainGui = mg;
        filePath = f;
        extension = e;
        
        
        //load the data display
        loadFileDisplay();
    }
    
    
    private void loadFileDisplay(){
        selectedData = null;
        mainPanelView.removeAll();
        mainPanelView.revalidate();
        mainPanelView.repaint();
        
        
        
        File directory = new File(filePath);
        
        //get all evolution data files
        for(File f : directory.listFiles()){
            String name = f.getName();
            
            if(name.endsWith(extension)){
                //create the label display
                JLabel label = new JLabel(name.replace(extension, ""));
                label.setFont(new Font("MS UI GOTHIC" , Font.BOLD , 16));
                label.setForeground(Color.WHITE);
                label.setBorder(new EmptyBorder(5,10,5,20));
                
                
                //this is the selected color
                label.setBackground(selectedColor);
                
                mainPanelView.add(label);
                
                
                //select the very first to be the selected
                if(selectedData==null){
                    setSelected(label);
                }
                
                
                
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        setSelected(label);
                        
                    }

                });
                
                
                
               
            }
            
        }
    }
    

    
    private void setSelected(JLabel label){
        //the previous color will be hidden
        if(selectedData!=null){
            selectedData.setOpaque(false);
            selectedData.repaint();
        }
        
        selectedData = label;
        selectedData.repaint();
        selectedData.setOpaque(true);
        inputField.setText(label.getText());
    }
  
    
    
    private void loadData(){
        String evoname = selectedData.getText();
        
        int result = JOptionPane.showConfirmDialog(this, String.format("Current evolution will not be saved. Load ' %s '?", evoname) , "Confirm Load" ,JOptionPane.YES_NO_OPTION );
        if(result == JOptionPane.YES_OPTION){
             mainGui.loadEvoData(filePath + evoname + extension);
             this.setVisible(false);
        }
        
       
    }
    
    
    
    
    
    private void saveData(){
        String evoName = inputField.getText();
        String fullPath = filePath + evoName + extension;
        
        
        
        if(evoName.isEmpty()){
            JOptionPane.showMessageDialog(this, "Evolution name can't be empty !" , "Invalid Input" , JOptionPane.ERROR_MESSAGE);
        }
        else if( (new File(fullPath).exists() && JOptionPane.showConfirmDialog(this, String.format("Do you want to replace \' %s \' with a new one ?" , evoName) , "Replace" , JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) ||
                
                !new File(fullPath).exists()){
            
            //save data if user decides to override or not yet exist
            mainGui.saveEvoData(fullPath);
           
            this.setVisible(false);
            
        }
       
    }
    
    
    
    
    
    private void deleteData(){
        String evoName = selectedData.getText();
        
        if(!evoName.isEmpty()){
            
            File file = new File(filePath + evoName + extension);
        
            int result = JOptionPane.showConfirmDialog(this, String.format("Are you sure you want to delete \' %s \' ?", evoName) , "Confirm Delete" , JOptionPane.YES_NO_OPTION);

            //delete data
            if(file.exists() && result == JOptionPane.YES_OPTION){
                file.delete();
            }


            loadFileDisplay();
        }
    }
    
    
    
    
    
    public void renameData(){
        String newName = inputField.getText();
        
        if(newName.isEmpty()){
            JOptionPane.showMessageDialog(this, "Name should not be empty" , "Invalid",JOptionPane.ERROR_MESSAGE);
        }
        else{
            
            int res = JOptionPane.showConfirmDialog(this, String.format("Do you want to rename ' %s ' to ' %s '", selectedData.getText() , newName) , "Confirm Rename" , JOptionPane.YES_NO_OPTION);
            
            if(res == JOptionPane.YES_NO_OPTION){
                
                //rename the data
                File file = new File(filePath + selectedData.getText() + extension);
                file.renameTo(new File(filePath + newName + extension));

                loadFileDisplay();
                
            }
            
            
        }
        
    }
    
    
    
    
    //message dialog if there are nothing
    private void showNoData(){
        JOptionPane.showMessageDialog(this, "No evolution data available. Please create a new one." , "No Data" , JOptionPane.WARNING_MESSAGE);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loadBttn = new javax.swing.JButton();
        inputField = new javax.swing.JTextField();
        deleteBttn = new javax.swing.JButton();
        renameBttn = new javax.swing.JButton();
        mainScroll = new javax.swing.JScrollPane();
        mainPanelView = new javax.swing.JPanel();
        saveBttn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

        jPanel1.setBackground(new java.awt.Color(70, 67, 87));

        loadBttn.setBackground(new java.awt.Color(63, 111, 191));
        loadBttn.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        loadBttn.setForeground(new java.awt.Color(255, 255, 255));
        loadBttn.setText("Load");
        loadBttn.setBorderPainted(false);
        loadBttn.setFocusable(false);
        loadBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBttnActionPerformed(evt);
            }
        });

        inputField.setBackground(new java.awt.Color(95, 92, 110));
        inputField.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        inputField.setForeground(new java.awt.Color(255, 255, 255));
        inputField.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5));
        inputField.setCaretColor(java.awt.Color.white);
        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });

        deleteBttn.setBackground(new java.awt.Color(63, 111, 191));
        deleteBttn.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        deleteBttn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBttn.setText("Delete");
        deleteBttn.setBorderPainted(false);
        deleteBttn.setFocusable(false);
        deleteBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBttnActionPerformed(evt);
            }
        });

        renameBttn.setBackground(new java.awt.Color(63, 111, 191));
        renameBttn.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        renameBttn.setForeground(new java.awt.Color(255, 255, 255));
        renameBttn.setText("Rename");
        renameBttn.setBorderPainted(false);
        renameBttn.setFocusable(false);
        renameBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameBttnActionPerformed(evt);
            }
        });

        mainPanelView.setBackground(new java.awt.Color(95, 92, 110));
        mainPanelView.setBorder(null);

        javax.swing.GroupLayout mainPanelViewLayout = new javax.swing.GroupLayout(mainPanelView);
        mainPanelView.setLayout(mainPanelViewLayout);
        mainPanelViewLayout.setHorizontalGroup(
            mainPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelViewLayout.setVerticalGroup(
            mainPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        mainScroll.setViewportView(mainPanelView);

        saveBttn.setBackground(new java.awt.Color(63, 111, 191));
        saveBttn.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        saveBttn.setForeground(new java.awt.Color(255, 255, 255));
        saveBttn.setText("Save");
        saveBttn.setBorderPainted(false);
        saveBttn.setFocusable(false);
        saveBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBttnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Evolution Name: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mainScroll)
                            .addComponent(inputField, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(loadBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deleteBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(renameBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(mainScroll)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(saveBttn)
                .addGap(21, 21, 21)
                .addComponent(loadBttn)
                .addGap(21, 21, 21)
                .addComponent(renameBttn)
                .addGap(21, 21, 21)
                .addComponent(deleteBttn)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBttnActionPerformed
       if(selectedData !=null){
           loadData();
       }
       else{
           showNoData();
       }
        
    }//GEN-LAST:event_loadBttnActionPerformed

    private void saveBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBttnActionPerformed
        saveData();
        
    }//GEN-LAST:event_saveBttnActionPerformed

    private void deleteBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBttnActionPerformed
        if(selectedData !=null){
            deleteData();
        }
        else{
           showNoData();
       }
       
    }//GEN-LAST:event_deleteBttnActionPerformed

    private void renameBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameBttnActionPerformed
        if(selectedData !=null){
            renameData();
        }
        else{
           showNoData();
       }
    }//GEN-LAST:event_renameBttnActionPerformed

    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputFieldActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteBttn;
    private javax.swing.JTextField inputField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loadBttn;
    private javax.swing.JPanel mainPanelView;
    private javax.swing.JScrollPane mainScroll;
    private javax.swing.JButton renameBttn;
    private javax.swing.JButton saveBttn;
    // End of variables declaration//GEN-END:variables
}
