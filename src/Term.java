import java.util.HashMap;

public class Term {
//    private boolean mIsAlwaysCapital;
    private String m_Term;
    private  TermDocFrequency[] m_DocFrequencies;
    public Term(String term) {
        this.m_Term = term;
    }

    public Term(String term, TermDocFrequency[] docFrequencies){
        this.m_Term = term;
        this.m_DocFrequencies = docFrequencies;
    }

    public void updateTerm(Doc document){

    }

    String serialize(){
        String[] serializedDocFreqs = new String[this.m_DocFrequencies.length];
        for (int i=0; i< this.m_DocFrequencies.length; i++){
            serializedDocFreqs[i] = this.m_DocFrequencies[i].serialize();
        }

        return "" + this.m_Term + "::" + String.join(",", serializedDocFreqs);
    }

    static Term deserialize(String serializedObj){
        String[] splitted = serializedObj.split("[:][:]");
        String term = splitted[0];
        String[] splittedDocFreqs = splitted[1].split("[,]");
        TermDocFrequency[] docFreqs =  new TermDocFrequency[splittedDocFreqs.length];
        for (int i=0; i< splittedDocFreqs.length; i++){
            docFreqs[i] =TermDocFrequency.deserialize(splittedDocFreqs[i]);
        }
        return new Term(term, docFreqs);
    }


    /**
     * add a doc to this term
     * @param document
     */
    private void addDocToTerm(Doc document){
        if(! m_DicOfDocs.containsKey(document)){
            m_DicOfDocs.put(document.getDocNum(),1);
        }
        else {
            int frequency = m_DicOfDocs.get(document.getDocNum());
            m_DicOfDocs.replace(document.getDocNum(),frequency,++frequency);
        }
    }

    private int df(){
        return m_DicOfDocs.size();
    }

    private boolean containCapital(String str) {
        return (Character.isUpperCase(str.charAt(0)));
    }

    private int tfInDocument(String docId){
        return m_DicOfDocs.get(docId);
    }

    private int getTotalTf(){
        int total = 0;
        for(String doc : m_DicOfDocs.keySet()){
            total = total + m_DicOfDocs.get(doc);
        }
        return total;
    }

    public boolean isM_isAlwaysCapital() {
        return m_isAlwaysCapital;
    }

    public String getM_term() {
        return m_term;
    }

    public HashMap<String, Integer> getM_DicOfDocs() {
        return m_DicOfDocs;
    }
}
