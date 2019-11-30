import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import javax.swing.text.InternationalFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Parser {
    private ArrayList<String> stopWords;

    Parser() throws IOException {
//        this.createSetOfStopWords();
    }

    /**
     * creat the stop word set
     * @throws IOException
     */

    private String removeAllCommas(String text){
        return text.replace(",", "");
    }


    private void createSetOfStopWords()throws IOException {
        stopWords = new ArrayList<>();
        FileReader reader = new FileReader(getClass().getClassLoader().getResource("stopWords.txt").getFile());
        BufferedReader buffer = new BufferedReader(reader);
        String stopWord = null;
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

    String cleanDoc(String docText){
        docText = removeAllCommas(docText);
        docText = cleanDocFromNonAssci(docText);
        //replace double spaces
        docText = docText.trim().replaceAll(" +", " ");

        docText = new NumberReplacer().replacePattern(docText);
        docText = new DollarReplacer().replacePattern(docText);
        docText = new PercentageReplacer().replacePattern(docText);
        docText = new MonthAndDayReplacer().replacePattern(docText);
        docText = new MonthAndYearReplacer().replacePattern(docText);
        docText = new RangeReplacer().replacePattern(docText);
        return docText;
    }

    private String cleanDocFromNonAssci(String text){
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }

    private String removeStopwords(String original) throws IOException {
        ArrayList<String> allWords = Stream.of(original.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopWords);

        String result = allWords.stream().collect(Collectors.joining(" "));
        return result;
    }
}
