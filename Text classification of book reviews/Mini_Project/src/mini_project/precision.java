package mini_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tartarus.snowball.EnglishSnowballStemmerFactory;
import mini_project.Review;
import org.tartarus.snowball.util.StemmerException;

public class precision {
    
    static double tp = 0, tn = 0, fp = 0, fn = 0;
    public static void accuracy(String ff1, int flag) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(ff1));
        String line = in.readLine();
        int count = 0;
        StringBuilder paragraph = new StringBuilder();
        while (true) {
            if (line == null || line.trim().length() == 0) {
                count++;
                String input = paragraph.toString();
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
                    w5 = new PrintWriter("input_format_para.txt");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                    Review.replace_punc(str, stopwrds, w5);
                    i++;
                }
                w5.close();
                File f1 = new File("input_format_para.txt");
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
            int jp = Review.indexx();
           //System.out.println("total : " + jp);
            a1 = Review.calculate_positive(input_hash, frequency_input, jp, f4);
            //System.out.println("Negative Claculation :");
            a2 = Review.calculate_negative(input_hash, frequency_input, jp, f5);
            //System.out.println(a1 + " " + a2);
            int res;
            if(a1 > a2){
                res = 0;
            }else{
                res = 1;
            }
            
            if(flag == 0 && res == 0)
                tp++;
            else if(flag == 1 && res == 1)
                tn++;
            else if(flag == 0 && res == 1)
                fp++;
            else
                fn++;
        } catch (FileNotFoundException | StemmerException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
                    paragraph.setLength(0);
                    if (line == null) {
                        break;
                    }
                
            }else {
                paragraph.append(" ");
                paragraph.append(line);
            }
                line = in.readLine();
            }
            in.close();
}
    

    public static void main(String[] args) throws IOException {
        //String file1 = "new_pos.txt";
        //String file2 = "new_neg.txt";
        //accuracy(file1, 0);
        //accuracy(file2, 1);
        //recall_acc ra = new recall_acc(tp, tn, fp, fn);
          
    }
}
