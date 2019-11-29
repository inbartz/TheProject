import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternReplacer {
    public static String getNumberPattern(){
//        return "([0-9]+[,]?)+([.]?)(([0-9]+)?)";
        return "[0-9]+([.]?)([0-9]+)?";
    }

    public static String removeAllTerms(String text, String[] terms){
        for (String term : terms) {
            text = text.replace(term, "");
        }
        return text;
    }


    public abstract String getPattern();
    private String _getNextWordAfterIndex(String text, int index) {
        String textAsOfIndex = text.substring(index);
        String word = "";
        if (textAsOfIndex.length() > 0 && textAsOfIndex.startsWith(" ")){
            word += " ";
            textAsOfIndex = textAsOfIndex.trim();
        }
        String[] words = textAsOfIndex.split(" ");

        if (words.length > 0){
            word += words[0];
        }
        return word;
    }
    public abstract String getReplacement(String term);

    private String _replaceBetweenIndexes(String text, int start, int end, String replacement){
        return text.substring(0, start) + replacement + text.substring(end);

    }

    private Matcher _matchPattern(String text, String stringPattern){
        Pattern pattern = Pattern.compile(stringPattern);
        return pattern.matcher(text);
    }

    public String replacePattern(String text){
        Matcher matcher = this._matchPattern(text.toLowerCase(), this.getPattern());
        int offset = 0;
        while(matcher.find()) {
            String match = matcher.group();
            String replacement = this.getReplacement(match);
            if (match.equals(replacement)){
                continue;
            }
            text = _replaceBetweenIndexes(text,
                    matcher.start() - offset,
                    matcher.end() - offset,
                    replacement);
            offset += (matcher.end() - matcher.start()) - replacement.length();
        }
        return text;
    }
}




class NumberReplacer extends PatternReplacer {

    @Override
    public String getPattern() {
        return getNumberPattern() + "([\\s]thousand)?([\\s]million)?([\\s]billion)?(bn)?";
    }

    @Override
    public String getReplacement(String term) {
        System.out.println(term);
        int multiplier = 1;
        if (term.contains("thousand")){
            multiplier = 1000;
        } else if (term.contains("million")){
            multiplier = 1000 * 1000;
        } else if (term.contains("billion") || term.endsWith("bn")){
            multiplier = 1000 * 1000 * 1000;
        }

        term = removeAllTerms(term, new String[] {"thousand", "million", "billion", "bn"}).trim();
        double number = Double.parseDouble(term);
        number *= multiplier;
        System.out.println(_formatNumber(number));
        return _formatNumber(number);
    }

    private String _formatNumber(double number){
        DecimalFormat df = new DecimalFormat("#.###");
        if (number >= 1000 * 1000 * 1000){
            return df.format(number / (1000*1000*1000)).toString()+ 'B';
        }
        else if (number >= 1000 * 1000){
            return df.format(number / (1000*1000))+ 'M';
        }
        else if (number >= 1000){
            return df.format(number / (1000))+ 'K';
        }
        else{
            return df.format(number);
        }
    }
}


abstract class MonthAndNumberReplacer extends PatternReplacer {
    String[] _getMonths(){
        return new String[] {"january","february","march","april","may","june","july","august","september",
                "october","november","december"};
    }

    int parseNumber (String numberString){
        return Integer.parseInt(numberString.replace("k", "").replace(".", ""));
    }

    private String formatTwoDigitsNumber(int date){
        if (date < 10){
            return "0" + Integer.toString(date);
        }
        return Integer.toString(date);
    }

    @Override
    public String getReplacement(String match) {
        String oldMatch  = match;
        String[] months = _getMonths();
        String formattedMonth = "";
        for (int monthIndex = 0; monthIndex < months.length; monthIndex ++){
            if (match.contains(months[monthIndex])){
                formattedMonth = this.formatTwoDigitsNumber(monthIndex + 1);
                match = match.replace(months[monthIndex], "").trim();
            }
        }
        if (match.length() == 0){
            // no other number
            return oldMatch;
        }

        int otherNumber = parseNumber(match);
        return formatMonthAndNumber(this.formatTwoDigitsNumber(otherNumber),formattedMonth);
    }

    abstract String formatMonthAndNumber(String monthString, String otherNumberString);
}

class MonthAndDayReplacer extends MonthAndNumberReplacer{
    @Override
    public String getPattern() {
        String monthsPattern = String.join("|", _getMonths());
        return "([0-9][0-9]?[\\s]?)?" +
                "(" + monthsPattern + ")" +
                "([0-9][0-9]?[\\s]?)?";
    }

    @Override
    String formatMonthAndNumber(String monthString, String otherNumberString) {
        return monthString + "-" + otherNumberString;
    }
}

class MonthAndYearReplacer extends MonthAndNumberReplacer{
    @Override
    public String getPattern() {
        String monthsPattern = String.join("|", _getMonths());
        return "(" + monthsPattern + ")" +
                "([\\s][1-9][.][0-9][0-9][0-9][k])";
    }

    @Override
    String formatMonthAndNumber(String monthString, String otherNumberString) {
        return otherNumberString + "-"+ monthString;
    }
}


class DollarReplacer extends PatternReplacer {
    @Override
    public String getPattern() {
        return "[$]?" + getNumberPattern() + "[kmb]?[\\s]?(u.s.)?[\\s]?(dollars)?";
    }

    @Override
    public String getReplacement(String match) {
        if (!(match.contains("$") || match.contains("dollars"))) {
            return match;
        }
        String[] termsToRemove = new String[] {"$", "dollars", "u.s."};
        match = removeAllTerms(match, termsToRemove).trim(); // only number

        int multiplier = 1;
        if (match.endsWith("k")){
            multiplier = 1000;
        } else if (match.endsWith("m")){
            multiplier = 1000 * 1000;
        } else if (match.endsWith("b")){
            multiplier = 1000 * 1000 * 1000;
        }
        match = removeAllTerms(match, new String[]{"k", "m", "b"});
        double number = Double.parseDouble(match) * multiplier;
        String formattedPrice = "";
        DecimalFormat decFormat = new DecimalFormat("#.###");
        if (number > 1000 * 1000){
            formattedPrice += decFormat.format(number / (1000*1000)) + " M";
        } else {
            formattedPrice += decFormat.format(number);
        }
        formattedPrice += " dollars";
        return formattedPrice;
    }
}

class PercentageReplacer extends PatternReplacer {

    @Override
    public String getPattern() {
        return  "[%]?" + getNumberPattern() + "[\\s](percentage)?(percent)?";
    }

    @Override
    public String getReplacement(String match) {
        // is percent:
        if (!(match.contains("%") || match.contains("percent"))) {
            return match;
        }
        match = removeAllTerms(match, new String[] {"%", " percentage", " percent"});
        match = "%" + match;
        return match;
    }
}

class RangeReplacer extends PatternReplacer {

    @Override
    public String getPattern() {
        return "(between)[\\s]([0-9]+)[\\s](and)[\\s]([0-9]+)";
    }

    @Override
    public String getReplacement(String match) {
        match = match.replace("between ", "");
        String[] numbers = match.split("(and)");
        return numbers[0].trim() + "-" + numbers[1].trim();
    }
}
