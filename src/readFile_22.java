import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class readFile_22 {

    private HashMap<String,Doc> allDocs;

    public readFile_22(String path) throws IOException{
        allDocs = new HashMap<>();
        listFilesForFolder(new File(path));
        System.out.println("g");
    }

    public void listFilesForFolder(final File folder) throws IOException {
        for (File fileEntry : folder.listFiles()) {
            File currFile = null;
            currFile = fileEntry.listFiles()[0];
            //split the file into documents
            fromFileToDocs(currFile,fileEntry.getPath());
        }
    }

    public void fromFileToDocs(File file,String path){
        try {
            Document xmlFile = Jsoup.parse(file, "UTF-8");
            org.jsoup.select.Elements ListOfDocs = (org.jsoup.select.Elements)( xmlFile.getElementsByTag("DOC"));
            for(Element element : ListOfDocs){
                Doc newDoc = new Doc(element,path);
                allDocs.put(newDoc.getDocNum(),newDoc);
            }
        }
        catch (
                IOException e) {
        }
    }


}
