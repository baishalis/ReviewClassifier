/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shallowparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import javax.swing.text.html.HTMLDocument;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 *
 * @author HP
 */



public class unsupervised {
    //stores the scored of the parsed sentences in the main sentence.
    public static HashMap<String, Double> hm = new HashMap<String, Double>();
    public static String[] neg_arr= {"not","no","don't","do not"};
    public static Integer arr[][] = new Integer[2][2] ;
    public static int count = 0;
    static Set<String> nounPhrases = new HashSet<>();
 //   static String pathToSWN = "C:\\Users\\Administrator\\Documents\\ShallowParser\\home\\swn\\www\\admin\\dump\\SentiWordNet_3.0.0_20130122.txt";
     static swn3 sentiwordnet;
   
    

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
       for(int i = 0;i < 2;i++) {
           for(int j = 0;j < 2;j++) {
               arr[i][j] = 0;
           }
       } 
      String pathToSWN = "F:\\5th sem\\SWE\\ShallowParser\\home\\swn\\www\\admin\\dump\\SentiWordNet_3.0.0_20130122.txt";
      if(pathToSWN.length()<1) {
      System.err.println("Usage: java swn3 <pathToSentiWordNetFile>");
      return;
    }
    
   // String pathToSWN = args[0];
     sentiwordnet = new swn3(pathToSWN);
     String content;
     BufferedReader br;
   //  String para = "";
     try{
      br = new BufferedReader(new FileReader("positive_reviews.txt"));
     }
     catch (FileNotFoundException e) {
            System.out.println("file not found\n");
            e.printStackTrace();
            return;
        }
    content = br.readLine();
     //System.out.println("debug");
     while(content != null) {
         count++;
  //       System.out.println(content);
 //        if(content == null || content.trim() == "") {
          //  content = new Scanner(new File("positive_reviews.txt")).useDelimiter("\\Z").next();
            
            if(content.indexOf("but") != -1) {
                content = content.substring(content.indexOf("but")+3);
            }
            if(content.indexOf("don't") != -1) {
                content = content.replace("don't", "do not");
            }
            else if(content.indexOf("couldn't") != -1) {
               content =  content.replace("couldn't", "could not");
            }
            else if(content.indexOf("shouldn't") != -1) {
                content = content.replace("shouldn't", "should not");
            }
//     
            content = content.replace("!", ". ");
            content = content.replace("'", "");
            content = content.replace(",", " ");
            content = content.replace("-", " ");
            content = content.replace("/", " ");
            content = content.replace("\"", " ");
            content = content.replace("\\", " ");
            content = content.replace("?", " ");
            content = content.replace("(", " ");
            content = content.replace(")", " ");
            
           
       // System.out.println(content);
        
        // always start with a model, a model is learned from training data
        //detects a sentence
        InputStream is = new FileInputStream("en-sent.bin");

        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String sentences[] = sdetector.sentDetect(content);

        is.close();
        InputStream is1 = new FileInputStream("en-token.bin");

        TokenizerModel model1 = new TokenizerModel(is1);

        Tokenizer tokenizer = new TokenizerME(model1);
        for (int i = 0; i < sentences.length; i++) {
            String tokens[] = tokenizer.tokenize(sentences[i]);
            /*for (String a : tokens)
             System.out.println(a);*/
        }

        is1.close();
        InputStream is2 = new FileInputStream("en-parser-chunking.bin");
      //   File parsed_output = new File("parsed_output.txt");
        //   FileWriter fw = new FileWriter(parsed_output);
        //  BufferedWriter bw = new BufferedWriter(fw);
        ParserModel model2 = new ParserModel(is2);
        PrintStream out = new PrintStream(new FileOutputStream("parsed_output.txt"));
        // System.setOut(out);
        Parser parser = ParserFactory.create(model2);

        for (int i = 0; i < sentences.length; i++) {
            Parse topParses[] = ParserTool.parseLine(sentences[i], parser, 1);
            for (Parse p : topParses) {
                p.show();
             //   System.out.println("noun phrases");
                getNounPhrases(p);
              //  System.out.println("adverb phrases");
                getAdverbPhrases(p);
             //   System.out.println("Verb phrases");
                getVerbPhrases(p);
             //   System.out.println("Adjective phrases");
                getAdjPhrases(p);
             //   System.out.println("Preposition phrases");
                getPrepPhrases(p);
            }

            System.out.println("");
        }
        
        

        is.close();
        out.close();
        
        
        Set s = hm.entrySet();
        Iterator i = s.iterator();
        double final_score = 0.0;
        while(i.hasNext()) {
            Entry thisEntry = (Entry) i.next();
           System.out.println(thisEntry.getKey() + " ->" + thisEntry.getValue());
           
            for(int j = 0;j < sentences.length;j++) {
                int f = 0;
                if(sentences[j].indexOf("and") != -1) {
                    String s1 = sentences[j].substring(0,sentences[j].indexOf("and")-1);
                    s1 = s1.trim();
                    String s2 = sentences[j].substring(sentences[j].indexOf("and")+3);
                    s2 = s2.trim();
                    for(int l = 0;l < sentences.length;l++){
                        if(thisEntry.getKey().equals(s1)) {
                             final_score += (Double.parseDouble(thisEntry.getValue().toString()));
                             f++;
                         } 
                        if(thisEntry.getKey().equals(s2)) {
                              final_score += (Double.parseDouble(thisEntry.getValue().toString()));
                                f++;
                         }
                        if(f == 2) {
                            break;
                        }
                    }
                  
                }
                if(f == 0) {
                if(thisEntry.getKey().equals(sentences[j])) {
                    final_score += (Double.parseDouble(thisEntry.getValue().toString()));
                } 
                }
            }
//            
           
        }
         if(Double.parseDouble(final_score+"") > 0.0) {
                System.out.println("positive");
            }
            else if(Double.parseDouble(final_score+"") == 0.0) {
                System.out.println("neutral");
            }
            else {
                 System.out.println("negative");
            }
         if(count <= 6) {
         if(final_score > 0.0) {
             arr[0][0]++;
         }
         else if(final_score < 0.0) {
             arr[0][1]++;
         }
         }
         else if(count >= 7 && count <= 11) {
             if(final_score > 0.0) {
             arr[1][0]++;
         }
         else if(final_score < 0.0) {
             arr[1][1]++;
         }
         }
       
         
         
 //        System.exit(0);
//         content = "";
//         }
//         else {
 //            para.concat(content);
            // System.out.println(para);
 //        }
         content = br.readLine();
 //    }
  
     }
     for(int i = 0;i < 2;i++) {
           for(int j = 0;j < 2;j++) {
               System.out.print(arr[i][j] + " ");
           }
           System.out.println("");
       } 
     double accuracy = (arr[0][0] + arr[1][1])/(double)(arr[0][0] + arr[0][1] + arr[1][0] + arr[1][1]);
     System.out.println("accuracy = " + accuracy);
    double recall = (arr[0][0])/(double)(arr[0][0] + arr[1][0]);
    System.out.println("recall = " + recall); 
    }
    //recursively loop through tree, extracting noun phrases
    public static void getNounPhrases(Parse p) {

        if (p.getType().equals("NP")) { //NP=noun phrase
             double score = 0;
           // nounPhrases.add(p.getCoveredText());
            
       //     System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
              try {
            score += getRB(p); //adverb
           // System.out.println(score+"");
            score += getJJ(p); //adjective
            score += getVB(p); //verb
            }
            catch(Exception e) {
              //  e.printStackTrace();
            }
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score);
            }
        }
        for (Parse child : p.getChildren()) {
            getNounPhrases(child);
        }
    }
    
      public static void getPrepPhrases(Parse p) {

        if (p.getType().equals("PP")) { //NP=noun phrase
            double score = 0;
            //nounPhrases.add(p.getCoveredText());
        //    System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
        //    System.out.println("prep phrases");
            try {
            score += getRB(p); //adverb
           // System.out.println(score+"");
            score += getJJ(p); //adjective
            score += getVB(p); //verb
            }
            catch(Exception e) {
              //  e.printStackTrace();
            }
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score);
            }
        }
        for (Parse child : p.getChildren()) {
            getPrepPhrases(child);
        }
    }
    public static void getAdverbPhrases(Parse p) {

        if (p.getType().equals("ADVP")) { //NP=noun phrase
            double score = 0;
            //nounPhrases.add(p.getCoveredText());
       //     System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
       //     System.out.println("rb phrases");
            try {
            score += getRB(p); //adverb
           // System.out.println(score+"");
            score += getJJ(p); //adjective
            score += getVB(p); //verb
            }
            catch(Exception e) {
              //  e.printStackTrace();
            }
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score);
            }
        }
        for (Parse child : p.getChildren()) {
            getAdverbPhrases(child);
        }
    }
      public static void getVerbPhrases(Parse p) {

        if (p.getType().equals("VP")) { //NP=noun phrase
            double score = 0;
            //nounPhrases.add(p.getCoveredText());
       //     System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
       //     System.out.println("rb phrases");
            try {
            score += getRB(p); //adverb
           // System.out.println(score+"");
            score += getJJ(p); //adjective
            score += getVB(p); //verb
            }
            catch(Exception e) {
             //   e.printStackTrace();
            }
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score);
            }
        }
        for (Parse child : p.getChildren()) {
            getVerbPhrases(child);
        }
    }
    
    public static void getAdjPhrases(Parse p) {

        if (p.getType().equals("ADJP")) { //NP=noun phrase
            double score = 0;
            //nounPhrases.add(p.getCoveredText());
       //     System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
            try {
            score += getRB(p); //adverb
            score += getJJ(p); //adjective
            score += getVB(p); //verb
            }
            catch(Exception e) {
              //  e.printStackTrace();
            }
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score);
            }
        }
        for (Parse child : p.getChildren()) {
            getAdjPhrases(child);
        }
    }
   public static double getRB(Parse p) throws IOException {
       double sc = 0.0;
        if (p.getType().equals("RB")) { //NP=noun phrase
            //nounPhrases.add(p.getCoveredText());
        //    System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
            
            double score1 = 0.0;
//            swn3 s1 = new swn3(pathToSWN);
            //System.out.println("debug");
            score1 = sentiwordnet.extract(p.getCoveredText().toString().trim(), "r");
           // System.out.println(score1+"");
            
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score1);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score1);
            }
            
            return score1;
        }
       
       for (Parse child : p.getChildren()) {
            sc += getRB(child);
        }
   return sc;
   }
   
    public static double getJJ(Parse p) throws IOException {
        double sc = 0.0;
        if (p.getType().equals("JJ")) { //NP=noun phrase
            //nounPhrases.add(p.getCoveredText());
            double score1;
           // swn3 s1 = new swn3(pathToSWN);
            score1 = sentiwordnet.extract(p.getCoveredText(), "a");
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score1);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score1);
            }
        //    System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
            return score1;
        }
       
       for (Parse child : p.getChildren()) {
            sc += getJJ(child);
        }
   return sc;
   }
    
      public static double getVB(Parse p) throws IOException {
          double sc = 0.0;
          double score1;
        if (p.getType().equals("VBP") || p.getType().equals("VB")) { //NP=noun phrase
            //nounPhrases.add(p.getCoveredText());
//            int flag = 0;
//            for(int i = 0;i < neg_arr.length;i++) {
//                if(neg_arr[i].matches(p.getCoveredText())) {
//                    if(hm.containsKey(p.getParent().toString())) {
//                        hm.put(p.getParent().toString(), -1 * hm.get(p.getParent().toString()));
//                    }
//                    else {
//                        hm.put(p.getParent().toString(), -1.);
//                    }
//                    flag = 1;
//                    break;
//                }
//            }
            
           // swn3 s1 = new swn3(pathToSWN);
            score1 = sentiwordnet.extract(p.getCoveredText(), "v");
            if(!hm.containsKey(p.getParent().toString())) {
                hm.put(p.getParent().toString(), score1);
            }
            else {
                hm.put(p.getParent().toString(), hm.get(p.getParent().toString())+ score1);
            }
       //     System.out.println(p.getCoveredText()+" -> "+p.getParent().toString());
            return score1;
        }
       
       for (Parse child : p.getChildren()) {
            sc += getVB(child);
        }
   return sc;
   }

}
