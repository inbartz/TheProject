import java.util.HashMap;

class TermDocFrequency {
    String docId;
    int occurrencesInDoc;
    int overallNumOfWords;
    TermDocFrequency (String docId, int occurrencesInDoc, int overallNumOfWords){
        this.docId = docId;
        this.occurrencesInDoc = occurrencesInDoc;
        this.overallNumOfWords = overallNumOfWords;
    }

    String serialize(){
        return "" + this.docId + "||" + this.occurrencesInDoc + "||";
    }

    static TermDocFrequency deserialize(String serializedObj){
        String[] propSplitted = serializedObj.split("[|][|]");
        return new TermDocFrequency(propSplitted[0],
                Integer.parseInt(propSplitted[1]),
                Integer.parseInt(propSplitted[2]));
    }
}
