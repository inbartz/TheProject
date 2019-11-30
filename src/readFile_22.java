//import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import java.io.File;
//import java.io.IOException;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class readFile_22 {
//
//    private HashMap<String,Doc> allDocs;
//    private Parser parse;
//
//    public readFile_22(String path) throws IOException{
//        allDocs = new HashMap<>();
//        listFilesForFolder(new File(path));
//        parse = new Parser(allDocs);
//    }
//
//    /**
//     * reads 10 files, separate them to docs and send them to the parser
//     * @param folder
//     * @throws IOException
//     */
//    public void listFilesForFolder(final File folder) throws IOException {
//        int count = 10;
//
//            for (File fileEntry : folder.listFiles()) {
//                while (count>0){
//                    File currFile = null;
//                    currFile = fileEntry.listFiles()[0];
//                    //split the file into documents
//                    fromFileToDocs(currFile,fileEntry.getPath());
//                    count--;
//                }
//                parse.startParsing();
//                count = 10;
//                allDocs=new HashMap<>();
//            }
//
//
//    }
//
//    public void fromFileToDocs(File file,String path){
//        try {
//            Document xmlFile = Jsoup.parse(file, "UTF-8");
//            org.jsoup.select.Elements ListOfDocs = (org.jsoup.select.Elements)( xmlFile.getElementsByTag("DOC"));
//            for(Element element : ListOfDocs){
//                Doc newDoc = new Doc(element,path);
//                allDocs.put(newDoc.getM_docNum(),newDoc);
//            }
//        }
//        catch (
//                IOException e) {
//        }
//    }
//
//
//}
