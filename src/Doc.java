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
    private String docNum;
    private String docFile;
    private String text;
    private String title;
    private String date;

    public Doc(Element docDetails,String path) {
        docNum=valueByTag(docDetails,"DOCNO");
        text =valueByTag(docDetails,"TEXT");
        title = valueByTag(docDetails,"HEADER");
        date = valueByTag(docDetails,"DATE1");
        docFile = path;
    }

    private String valueByTag(Element doc ,String tag){
        String value = doc.getElementsByTag(tag).text();
       //System.out.println(value);
        return value;
    }



















    //getters &&& setters
    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocFile() {
        return docFile;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
