import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parse {

    private HashSet<String> stopWords;

    public Parse(){
    }

    /**
     * creat the stop word set
     * @throws IOException
     */
    private void createSetOfStopWords()throws IOException {
        stopWords = new HashSet();
        File f =new File("C:\\Users\\inbar\\Desktop\\stopWords.txt");
        BufferedReader buffer = new BufferedReader(new FileReader(f));
        String stopWord = null ;
        try{
            while ((stopWord=buffer.readLine())!=null){
                stopWords.add(stopWord);
            }
        }
        catch (IOException e){
            System.out.println("fuck of");
        }
        buffer.close();
    }

    public String parseDoc(String docText) {
        // replace numbers

        //



    }






    public String cleanDoc(String text){
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }

    public String RemoveStopwords(String original) throws IOException {
       List<String> stopwords = Files.readAllLines(Paths.get("C:\\Users\\inbar\\Desktop\\stopWords.txt"));
        ArrayList<String> allWords = Stream.of(original.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopwords);

        String result = allWords.stream().collect(Collectors.joining(" "));
        return result;
    }

    private String isMillonNum(String termToPerse){
        String afterPars = "";
        return afterPars;
    }

}
