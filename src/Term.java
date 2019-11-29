import java.util.HashMap;

public class Term {

    private boolean m_isAlwaysCapital;
    private String m_term;
    private HashMap<String,Integer> m_DicOfDocs;

    public Term(String word) {
        m_isAlwaysCapital = containCapital(word);
        m_term = word;
        m_DicOfDocs = new HashMap<>();
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
