
package mini_project;

import java.io.IOException;

public class recall_acc extends javax.swing.JFrame {
    static double[][] confusion = new double[2][2];
    public recall_acc() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel2.setText("Accuracy and Recall ");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(190, 20, 240, 40);

        jLabel3.setFont(new java.awt.Font("Mukti Narrow", 1, 16)); // NOI18N
        jLabel3.setText("Accuracy  =  (tp  +  tf) / (tp + tf + fp + fn)");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(80, 90, 330, 30);
        jPanel1.add(jLabel4);
        jLabel4.setBounds(180, 130, 200, 30);

        jLabel5.setFont(new java.awt.Font("Mukti Narrow", 1, 16)); // NOI18N
        jLabel5.setText("Recall      =  ( tp ) / ( tp  + fn)");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(80, 180, 290, 27);
        jPanel1.add(jLabel6);
        jLabel6.setBounds(180, 220, 200, 40);

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel7.setText("Confusion Matrix:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(80, 290, 170, 20);

        jLabel8.setText("positive");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(270, 310, 90, 17);

        jLabel9.setText("negative");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(370, 310, 80, 20);

        jLabel10.setText("positive");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(173, 350, 70, 17);

        jLabel11.setText("negative");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(170, 380, 80, 17);
        jPanel1.add(jLabel12);
        jLabel12.setBounds(270, 350, 70, 20);
        jPanel1.add(jLabel13);
        jLabel13.setBounds(370, 350, 70, 20);
        jPanel1.add(jLabel14);
        jLabel14.setBounds(270, 380, 60, 20);
        jPanel1.add(jLabel15);
        jLabel15.setBounds(370, 380, 60, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon("/home/mala/Desktop/gradient-background.jpg")); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 660, 460);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(recall_acc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(recall_acc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(recall_acc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(recall_acc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new recall_acc().setVisible(true);
            }
        });
        //System.out.println("hello");
        String file1 = "new_pos.txt";
        String file2 = "new_neg.txt";
        precision.accuracy(file1, 0);
        precision.accuracy(file2, 1);
        
        confusion[0][0] = precision.tp;
        confusion[0][1] = precision.fp;
        confusion[1][0] = precision.fn;
        confusion[1][1] = precision.tn;
        double ans = (confusion[0][0] + confusion[1][1]) / (confusion[0][0] + confusion[1][1] + confusion[0][1] + confusion[1][0]);
        //System.out.println(ans);
        jLabel4.setText("=" + " " + Double.toString(ans));
        double recall1 = (confusion[0][0])/(confusion[0][0] + confusion[1][0]);
        double recall2 = (confusion[1][1])/(confusion[1][1] + confusion[0][1]);
        double recall = recall1 < recall2  ? recall1 : recall2;
        jLabel6.setText("=" + " " + Double.toString(recall));
        jLabel12.setText(Double.toString(confusion[0][0]));
        jLabel13.setText(Double.toString(confusion[1][0]));
        jLabel14.setText(Double.toString(confusion[0][1]));
        jLabel15.setText(Double.toString(confusion[1][1]));
           
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private static javax.swing.JLabel jLabel12;
    private static javax.swing.JLabel jLabel13;
    private static javax.swing.JLabel jLabel14;
    private static javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private static javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private static javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
