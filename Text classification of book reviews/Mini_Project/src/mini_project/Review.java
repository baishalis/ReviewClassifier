package mini_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tartarus.snowball.EnglishSnowballStemmerFactory;
import org.tartarus.snowball.util.StemmerException;

public class Review extends javax.swing.JFrame {

    static double sum1, sum2;

    public static void replace_punc(String str, String[] stopwrds, PrintWriter w) {
        str = str.replace(".", " ");
        str = str.replace("!", " ");
        str = str.replace("'", "");
        str = str.replace(",", " ");
        str = str.replace("-", " ");
        str = str.replace("/", " ");
        str = str.replace("\"", " ");
        str = str.replace("\\", " ");
        str = str.replace("?", " ");
        str = str.replace("(", " ");
        str = str.replace(")", " ");
        if (Arrays.asList(stopwrds).indexOf(str.toLowerCase()) == -1) {
            w.print(str.toLowerCase() + " ");
        }
    }

    public static int indexx() throws FileNotFoundException, StemmerException {
        Scanner s2 = null, s1 = null;
        File f1 = new File("newpositive.txt"); //training +ve input file
        File f2 = new File("newnegative.txt");
        s1 = new Scanner(f1);
        s2 = new Scanner(f2);
        Hashtable<Integer, String> source3 = new Hashtable<>();
        Hashtable<Integer, Integer> frequency3 = new Hashtable<Integer, Integer>();
        Vector<Integer> v3 = new Vector<Integer>();
        int index = 1;
        while (s1.hasNext()) {
            String a = s1.next().toLowerCase();
            String str = EnglishSnowballStemmerFactory.getInstance().process(a);
            if (source3.containsValue(str)) {
                for (Map.Entry<Integer, String> e : source3.entrySet()) {
                    String value = e.getValue();
                    if (value.equals(str)) {
                        int key = e.getKey();
                        for (Map.Entry<Integer, Integer> e1 : frequency3.entrySet()) {
                            int z = e1.getKey();
                            if (z == key) {
                                int val = e1.getValue() + 1;
                                frequency3.put(key, val);
                            }
                        }
                    }
                }
            } else {
                source3.put(index, str);
                frequency3.put(index, 1);
                index++;
            }
        }
        index--;
        while (s2.hasNext()) {
            String str = s2.next();
            if (source3.containsValue(str)) {
                for (Map.Entry<Integer, String> e : source3.entrySet()) {
                    String value = e.getValue();
                    if (value.equals(str)) {
                        int key = e.getKey();
                        for (Map.Entry<Integer, Integer> e1 : frequency3.entrySet()) {
                            int z = e1.getKey();
                            if (z == key) {
                                int val = e1.getValue() + 1;
                                frequency3.put(key, val);
                            }
                        }
                    }
                }//Chinese Chinese Chinese Tokyo Japan
            } else {
                source3.put(index, str);
                frequency3.put(index, 1);
                index++;
            }
        }
        int index1 = index - 1;
        return index1;
    }

    public static double calculate_positive(Hashtable<Integer, String> input_hash, Hashtable<Integer, Integer> frequency_input, int jp, File f) throws FileNotFoundException, StemmerException {
        String a = null;
        Scanner s = null;
        double foo = 0, p = 1, sum = 1;
        Iterator<Map.Entry<Integer, String>> entries2 = input_hash.entrySet().iterator();
        Iterator<Map.Entry<Integer, Integer>> entries3 = frequency_input.entrySet().iterator();

        while (entries2.hasNext()) {
            foo = 0;
            Map.Entry<Integer, String> entry = entries2.next();
            Map.Entry<Integer, Integer> entry1 = entries3.next();
            String str = entry.getValue().toLowerCase();
            int fre = entry1.getValue();  //testset
            String line;
            s = new Scanner(f);
            while (s.hasNextLine()) {
                line = s.nextLine();
                String[] tokens = line.split(" ");
                //System.out.println(str + " " + tokens[0] + " " + tokens[1]);
                if (str.equals(tokens[0].toLowerCase())) {
                    a = tokens[1];
                    foo = Double.parseDouble(a); //traingset count
                    //System.out.println(tokens[0] + " " + foo);
                    break;
                }
            }
            //System.out.println(sum1 + " " + jp);
            double ans = (foo + 1) / (sum1 + jp);
            //System.out.println("ans " + ans);
            sum = sum * (Math.pow(ans, fre));
            //System.out.println("sum " + sum);
        }
        double result = sum * (0.5);
        //System.out.println("result " + result);
        return result;
    }

    public static double calculate_negative(Hashtable<Integer, String> input_hash, Hashtable<Integer, Integer> frequency_input, int jp, File f) throws FileNotFoundException {
        
        String a = null;
        Scanner s = null;
        double foo = 0, p = 1, sum = 1;
        Iterator<Map.Entry<Integer, String>> entries2 = input_hash.entrySet().iterator();
        Iterator<Map.Entry<Integer, Integer>> entries3 = frequency_input.entrySet().iterator();

        while (entries2.hasNext()) {
            foo = 0;
            Map.Entry<Integer, String> entry = entries2.next();
            Map.Entry<Integer, Integer> entry1 = entries3.next();
            String str = entry.getValue().toLowerCase();
            int fre = entry1.getValue();  //testset
            String line;
            s = new Scanner(f);
            while (s.hasNextLine()) {
                line = s.nextLine();
                String[] tokens = line.split(" ");
                //System.out.println(str + " " + tokens[0] + " " + tokens[1]);
                if (str.equals(tokens[0].toLowerCase())) {
                    a = tokens[1];
                    foo = Double.parseDouble(a); //traingset count
                    //System.out.println(tokens[0] + " " + foo);
                    break;
                }
            }
            //System.out.println(sum1 + " " + jp);
            //System.out.println(foo);
            double ans = (foo + 1) / (sum2 + jp);
            //System.out.println("ans " + ans);
            sum = sum * (Math.pow(ans, fre));
            //System.out.println("sum " + sum);
        }
        double result = sum * (0.5);
       // System.out.println(result);
        return result;
    }
    public Review(double s, double y) {
        sum1 = s;
        sum2 = y;
        //System.out.println(sum1 + " " + sum2);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tlwg Typist", 1, 24)); // NOI18N
        jLabel2.setText("Review");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(120, 20, 230, 60);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(120, 120, 510, 130);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(540, 320, 90, 29);

        jLabel3.setIcon(new javax.swing.ImageIcon("/home/mala/Documents/3455468-Man-write-on-white-background-with-fountain-pen-Stock-Photo.jpg")); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 880, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Scanner s = null;
        String[] stopwrds = new String[1000];
        File f = new File("stopwrds.txt");
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 0;
        while (s.hasNext()) {
            stopwrds[i++] = s.next();
        }
        PrintWriter w5 = null;
        try {
            w5 = new PrintWriter("input_format.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Pos_nb pn = new Pos_nb();
        String input = jTextField1.getText();
        String[] token = input.split(" ");
        i = 0;

        while (i < token.length) {
            String a = token[i];
            String str = null;
            try {
                str = EnglishSnowballStemmerFactory.getInstance().process(a);
            } catch (StemmerException ex) {
                Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
            }
            replace_punc(str, stopwrds, w5);
            i++;
        }
        w5.close();
        File f1 = new File("input_format.txt");
        Scanner s1 = null;
        Hashtable<Integer, String> input_hash = new Hashtable<Integer, String>();
        Hashtable<Integer, Integer> frequency_input = new Hashtable<Integer, Integer>();
        int index = 1;
        try {
            s1 = new Scanner(f1);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (s1.hasNext()) {
            String str = s1.next();
            
            
            if (input_hash.containsValue(str)) {
                for (Map.Entry<Integer, String> e : input_hash.entrySet()) {
                    String value = e.getValue();
                    if (value.equals(str)) {
                        int key = e.getKey();
                        for (Map.Entry<Integer, Integer> e1 : frequency_input.entrySet()) {
                            int z = e1.getKey();
                            if (z == key) {
                                int val = e1.getValue() + 1;
                                frequency_input.put(key, val);
                            }
                        }
                    }
                }
            } else {
                input_hash.put(index, str);
                frequency_input.put(index, 1);
                index++;
            }
        }
        double a1 = 0, a2 = 0;
        File f4 = new File("pos_freq.txt");
        File f5 = new File("neg_freq.txt");
        try {
            int jp = indexx();
           //System.out.println("total : " + jp);
            a1 = calculate_positive(input_hash, frequency_input, jp, f4);
            //System.out.println("Negative Claculation :");
            a2 = calculate_negative(input_hash, frequency_input, jp, f5);
            //System.out.println(a1 + " " + a2);
        } catch (FileNotFoundException | StemmerException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(a1 +" " + a2);
        this.setVisible(false);
        result r = new result(a1, a2);
        r.func();
        r.setLocationRelativeTo(null);
        r.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Review(sum1, sum2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
