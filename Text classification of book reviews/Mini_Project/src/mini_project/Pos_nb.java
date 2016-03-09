package mini_project;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tartarus.snowball.EnglishSnowballStemmerFactory;
import org.tartarus.snowball.util.StemmerException;

public class Pos_nb {
    static double index1, index2;
    static String[] stopwrds = new String[1000];
    static double sum = 0, sum1 = 0;
    public static void replace_punc(String str, String[] stopwrds,PrintWriter w){
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
   
    public static void main(String[] args) throws FileNotFoundException, IOException,  NullPointerException, StemmerException{
        Scanner s = null, s1 = null, s2 = null, s3 = null, s4 = null, s5 = null;
        PrintWriter w = null, w1 = null, w2 = null,w3 = null;
        
        try {
            w = new PrintWriter("newpositive.txt", "UTF-8");
            w1 = new PrintWriter("pos_freq.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Pos_nb.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            File f = new File("stopwrds.txt");
            File f1 = new File("positive_reviews.txt"); //training +ve input file
            File f2 = new File("newpositive.txt"); //after replacing stopwrds

            s = new Scanner(f);
            s1 = new Scanner(f1);
            s2 = new Scanner(f2);
        } catch (FileNotFoundException e) {
            System.out.println("File not found\n");
            return;
        }
        int i = 0;
        while(s.hasNext()){
            stopwrds[i++] = s.next();
        }
        while(s1.hasNext()){
            String str = s1.next();
            replace_punc(str, stopwrds,w);
        }
        w.close();
        
        Hashtable<Integer, String > source = new Hashtable<>();
        Hashtable<Integer, Integer > frequency = new Hashtable<Integer,Integer>();
        Vector<Integer> v = new Vector<Integer>();
        int index = 1;
        while(s2.hasNext()){
            String a = s2.next();
            String str = EnglishSnowballStemmerFactory.getInstance().process(a);
            if (source.containsValue(str))
            {
                for (Map.Entry<Integer, String> e : source.entrySet()) {
                        //String key = e.getKey();
                        String value = e.getValue();
                        if (value.equals(str))
                        {
                            int key = e.getKey();
                            for (Map.Entry<Integer, Integer> e1 : frequency.entrySet()) {
                                int z = e1.getKey();
                                if (z == key)
                                {
                                  int val = e1.getValue() + 1;
                                  frequency.put(key,val);
                                }
                                //frequency.put(key, frequency.getValue(key) + 1);
                            }
                        }
                }
                //int ss = source.getKey(str);
            }
            else 
            {
                source.put(index,str);
                frequency.put(index, 1);
                index++;
            }
        }
        index1 = index - 1;
        
        Iterator<Map.Entry<Integer, String>> entries = source.entrySet().iterator();
        Iterator<Map.Entry<Integer, Integer>> entries1 = frequency.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, String> entry = entries.next();
            Map.Entry<Integer, Integer> entry1 = entries1.next();
            //System.out.println(entry.getValue() +  " = " + entry1.getValue());
            w1.print(entry.getValue() +  " " + entry1.getValue() + "\n");
            sum = sum + entry1.getValue();
        }
        w1.close();
        Hashtable<Integer, String > source1 = new Hashtable<>();
        Hashtable<Integer, Integer > frequency1 = new Hashtable<Integer,Integer>();
        Vector<Integer> v1 = new Vector<Integer>();
        w2 = new PrintWriter("newnegative.txt", "UTF-8");
        w3 = new PrintWriter("neg_freq.txt", "UTF-8");
        
        File f3 = new File("negative_reviews.txt");
        File f4 = new File("newnegative.txt");
        
         s3 = new Scanner(f3);
         s4 = new Scanner(f4);
         
         while(s3.hasNext()){
            String str = s3.next();
            replace_punc(str, stopwrds,w2);
        }
        w2.close();
        index = 1;
        while(s4.hasNext()){
            String a = s4.next();
            String str = EnglishSnowballStemmerFactory.getInstance().process(a);
            if (source1.containsValue(str))
            {
                for (Map.Entry<Integer, String> e : source1.entrySet()) {
                        //String key = e.getKey();
                        String value = e.getValue();
                        if (value.equals(str))
                        {
                            int key = e.getKey();
                            for (Map.Entry<Integer, Integer> e1 : frequency1.entrySet()) {
                                int z = e1.getKey();
                                if (z == key)
                                {
                                  int val = e1.getValue() + 1;
                                  frequency1.put(key,val);
                                }
                                //frequency.put(key, frequency.getValue(key) + 1);
                            }
                        }
                }
                //int ss = source.getKey(str);
            }
            else 
            {
                source1.put(index,str);
                frequency1.put(index, 1);
                index++;
            }
        }
        index2 = index - 1;
        Iterator<Map.Entry<Integer, String>> entriess = source1.entrySet().iterator();
        Iterator<Map.Entry<Integer, Integer>> entriess1 = frequency1.entrySet().iterator();
        while (entriess.hasNext()) {
            Map.Entry<Integer, String> entry = entriess.next();
            Map.Entry<Integer, Integer> entry1 = entriess1.next();
            //System.out.println("giuhjkkkkkkkkkkk" + " "+entry.getValue() +  " = " + entry1.getValue());
            w3.print(entry.getValue() +  " " + entry1.getValue() + "\n");
            sum1 = sum1 + entry1.getValue();
        }
        w3.close();
        Review r = new Review(sum, sum1);
        r.setLocationRelativeTo(null);
        r.setVisible(true);
    }
}
