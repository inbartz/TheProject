import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


public class Doc {
    private String m_docNum;
    private String m_docFile;
    private String m_title;
    private String m_date;
    private int m_sizeOfDoc;
    private HashMap<String,Integer> m_termDictionery;
    private String m_text;

    public Doc(Element docDetails,String path, Parser parser) {
        m_docNum=valueByTag(docDetails,"DOCNO");
        m_title = valueByTag(docDetails,"HEADER");
        m_date = valueByTag(docDetails,"DATE1");
        m_text = valueByTag(docDetails,"TEXT");
        m_text=valueByTag(docDetails,"TEXT");
        m_docFile = path;
        m_termDictionery = new HashMap<>();
    }

    private String valueByTag(Element doc , String tag){
        String value = doc.getElementsByTag(tag).text();
        return value;
    }

    private int mostFrequensTermInDoc(){
        int max = 0;
        for (String term : m_termDictionery.keySet()){
            if(m_termDictionery.get(term)>max){
                max = m_termDictionery.get(term);
            }
        }
        return max;
    }

    //getter and setter
    public String getM_docNum() {
        return m_docNum;
    }

    public void setM_docNum(String m_docNum) {
        this.m_docNum = m_docNum;
    }

    public String getM_docFile() {
        return m_docFile;
    }

    public void setM_docFile(String m_docFile) {
        this.m_docFile = m_docFile;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    public int getM_sizeOfDoc() {
        return m_sizeOfDoc;
    }

    public void setM_sizeOfDoc(int m_sizeOfDoc) {
        this.m_sizeOfDoc = m_sizeOfDoc;
    }

    public StringBuilder getM_text() {
        return m_text;
    }
}
