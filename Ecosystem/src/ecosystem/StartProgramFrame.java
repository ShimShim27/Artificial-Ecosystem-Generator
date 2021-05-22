
package ecosystem;


public class StartProgramFrame extends javax.swing.JFrame {

    public StartProgramFrame() {
        initComponents();
    }

  
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        startEvo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 300));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 67, 87));

        startEvo.setBackground(new java.awt.Color(63, 111, 191));
        startEvo.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        startEvo.setForeground(new java.awt.Color(255, 255, 255));
        startEvo.setText("Start Evolution");
        startEvo.setBorderPainted(false);
        startEvo.setFocusable(false);
        startEvo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startEvoActionPerformed(evt);
            }
        });
        jLayeredPane1.setLayer(startEvo, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(startEvo);
        startEvo.setBounds(160, 120, 180, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/start_screen.png"))); // NOI18N
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(0, 0, 500, 300);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
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

    
    private void startEvoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startEvoActionPerformed
        this.setVisible(false);
        new MainGUI().main(null);
    }//GEN-LAST:event_startEvoActionPerformed

    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StartProgramFrame pframe = new StartProgramFrame();
                pframe.setVisible(true);
                
                D.alignCenter(pframe);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton startEvo;
    // End of variables declaration//GEN-END:variables
}
