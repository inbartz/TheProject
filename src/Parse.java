import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

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

    private ArrayList<String> stopWords;


    public Parse(){
    }

    /**
     * creat the stop word set
     * @throws IOException
     */
    private void createSetOfStopWords()throws IOException {
        stopWords = new ArrayList<>();
        FileReader reader = new FileReader(getClass().getClassLoader().getResource("stopWords.txt").getFile());
        BufferedReader buffer = new BufferedReader(reader);
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

    public String cleanDocFromNonAssci(String text){
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }

    public String RemoveStopwords(String original) throws IOException {
        ArrayList<String> allWords = Stream.of(original.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopWords);

        String result = allWords.stream().collect(Collectors.joining(" "));
        return result;
    }

    public Matcher findMatches(String patternToCreate , StringBuilder text){

        Pattern pattern = Pattern.compile(patternToCreate);
        Matcher matcher = pattern.matcher(text);
        return matcher;
    }

    public StringBuilder replaceMatcheToPattern(int StartIndex , int EndIndex , StringBuilder original,String changes){
        original.replace(StartIndex,EndIndex,changes);
        return original;
    }

    public void KNumber (StringBuilder text){
        String regularE = "";
        Matcher matcheToKNumber = findMatches(regularE , text);
        while (matcheToKNumber.find()){
            if(matcheToKNumber.group(2)=="Thousand"){

            }
           // replaceMatcheToPattern(matcheToKNumber.start(),matcheToKNumber.end(),text,);
        }
    }

    public String MNumber( StringBuilder text){
        String regularE = " ([0-9]{1,3}[,]+[0-9]{3}[,])+[0-9]{3}|([0-9]{1,3}\\s\\Million\\b)";
        Matcher matcheToKNumber = findMatches(regularE , text);
        String newStrForm="";
        while (matcheToKNumber.find()){
            String group0 = matcheToKNumber.group(0);
            if (matcheToKNumber.group(0).contains("Million")){
                String[]s =matcheToKNumber.group(2).split("[M,\\ ]");
                newStrForm = s[0]+"M";
            }
            else{
                String number = changeNum(group0,1000000);
                newStrForm = number+"M";
            }
        }
        return newStrForm;
    }

    public String changeNum(String number , int divider){
        int accuracy = 3;
        String d = ""+divider;
        int dividerByNum = d.length()-1;
        StringBuilder newNumber =removePsik(number);

        String result = "";
        if((!newNumber.toString().contains("."))){
            newNumber.append(".");
        }
        String stringNewNum = newNumber.toString();
        StringBuilder removeZ = new StringBuilder();
        for(int i = newNumber.length()-1 ; i >=0 ;i--) {
            if (stringNewNum.charAt(i) == '.') {
                int pointLocation = i-dividerByNum;
                for(int j =0 ; j < newNumber.length();j++){
                    if(pointLocation==j){
                        result=result+".";
                        for (int k = j; k < newNumber.length()&&accuracy>0 ;k++){
                            accuracy--;
                            result=result+stringNewNum.charAt(k);
                        }
                        removeZ.append(result);
                        for(int p = result.length()-1; p>0 ;p--){
                            if(result.charAt(p)==0){
                                removeZ.deleteCharAt(p);
                            }

                            return removeZ.toString();
                        }
                    }
                    else {
                        result=result+stringNewNum.charAt(j);
                    }
                }
            }
        }
        return result;
    }

    private StringBuilder removePsik(String number) {
        StringBuilder newStr = new StringBuilder();
        for( int i=0; i<number.length(); i++){
            if(!(number.charAt(i)==',')){
                newStr.append(number.charAt(i));
            }
        }
        return newStr;
    }
}
