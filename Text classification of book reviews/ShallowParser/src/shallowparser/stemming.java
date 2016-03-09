package shallowparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.tartarus.snowball.*;
import org.tartarus.snowball.util.StemmerException;




public class stemming {
    static String[] stopwrds = new String[1000];
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
            w.print(str + " ");
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, StemmerException {
        // TODO code application logic here


        File f = new File("F:\\5th sem\\SWE\\ShallowParser\\stopwrds.txt");
        Scanner sc = new Scanner(f);
        Scanner s1 = new Scanner(new File("F:\\5th sem\\SWE\\ShallowParser\\input_stop.txt"));
        PrintWriter w = new PrintWriter("F:\\5th sem\\SWE\\ShallowParser\\stop_output.txt");
        int i = 0;
        while(sc.hasNext()){
            stopwrds[i++] = sc.next();
        }
        
        while(s1.hasNext()){
            String str = s1.next();
            replace_punc(str, stopwrds,w);
        }
    
        w.close();
        Scanner s2 = new Scanner(new File("F:\\5th sem\\SWE\\ShallowParser\\stop_output.txt"));
        PrintWriter pw1 = new PrintWriter(new File("F:\\5th sem\\SWE\\ShallowParser\\stem_out.txt"));
        //code to perform stemming
        while(s2.hasNext()) {
            String str = s2.next();
            String a = EnglishSnowballStemmerFactory.getInstance().process(str);//method to access the porter stemmer for english
            pw1.print(a+" ");
        }
        pw1.close();
    }
   
}
    