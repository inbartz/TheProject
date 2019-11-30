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

    public abstract String getReplacement(String term);

    private String _replaceBetweenIndexes(String text, int start, int end, String replacement){
        return text.substring(0, start) + replacement + text.substring(end);
    }

    private Matcher _matchPattern(String text, String stringPattern){
        Pattern pattern = Pattern.compile(stringPattern);
        return pattern.matcher(text);
    }

    //replace the new form pattern by adjusting to the size of the new pattern
    String replacePattern(String text){
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



// numbers handlers:
class NumberReplacer extends PatternReplacer {

    @Override
    //check if good for capital letters
    //check if needs space before bn
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
        // understand format- if less then tree
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

// dates
abstract class MonthAndNumberReplacer extends PatternReplacer {
    String[] _getMonths(){
        //check capital lleters
        return new String[] {"january","february","march","april","may","june","july",
                "august","september","october","november","december"};
    }
    String[] _getExtendedMonths() {
        return new String[] {"january","jan","february","feb","march","mar","april","apr","may","june","jun","july",
                "jul","august","aug","september","sep","october","oct","november","nov","december","dec"};
    }

    int parseNumber (String numberString){
        return Integer.parseInt(numberString.replace("k", "").replace(".", ""));
    }

    private String formatTwoDigitsNumber(int date){
        if (date < 10){
            return "0" +date;
        }
        return Integer.toString(date);
    }

    @Override
    public String getReplacement(String match) {
        //here we added the shortcut option- 3 letters for each month
        //check capital letters
        String oldMatch  = match;
        String[] months = _getMonths();
        String formattedMonth = "";
        for (int monthIndex = 0; monthIndex < months.length; monthIndex+=2){
            if (match.contains(months[monthIndex])|| match.contains(months[monthIndex].substring(0,3))){
                formattedMonth = this.formatTwoDigitsNumber(monthIndex + 1);
                match = match.replace(months[monthIndex], "").trim();
            }
        }
        //asssk
        if (match.length() == 0){
            // no other number
            return oldMatch;
        }
        //ask
        int otherNumber = parseNumber(match);
        return formatMonthAndNumber(this.formatTwoDigitsNumber(otherNumber),formattedMonth);
    }

    abstract String formatMonthAndNumber(String monthString, String otherNumberString);
}

class MonthAndDayReplacer extends MonthAndNumberReplacer{
    @Override
    public String getPattern() {
        String monthsPattern = String.join("|", _getExtendedMonths());
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
        String monthsPattern = String.join("|", _getExtendedMonths());
        return "(" + monthsPattern + ")" +
                "([\\s][1-9][.][0-9][0-9][0-9][k])";
    }

    @Override
    String formatMonthAndNumber(String monthString, String yearString) {
        return yearString + "-"+ monthString;
    }
}

//dollar
class DollarReplacer extends PatternReplacer {
    @Override
    //check capital letters
    public String getPattern() {
        // can find numbers that are not prices- better to do or
        return "[$]?" + getNumberPattern() + "[kmb]?[\\s]?(u.s.)?[\\s]?(dollars)?";
    }

    @Override
    public String getReplacement(String match) {
        //not economic
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

// percent
class PercentageReplacer extends PatternReplacer {

    @Override
    public String getPattern() {
        return getNumberPattern() + "([%]|([\\s]percentage)|([\\s]percent))";
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

//range
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
