import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class readFile {

    private String path;

    public readFile(String path){
        this.path = path;
        try {
            listFilesForFolder(new File(path));
        }
        catch (IOException e){

        }
    }

    public void listFilesForFolder(final File folder) throws IOException {
        ArrayList<String> filesToDocs = new ArrayList<>();
        int count10Files =0;

        for (File fileEntry : folder.listFiles()) {
            File currFile = null;
            currFile = fileEntry.listFiles()[0];
            char a = 'a';

            try {
                String fileRead =readFileToStream(currFile,fileEntry.getPath());
                if( count10Files ==10){
                    writeTenFiles(filesToDocs,a);
                    filesToDocs = new ArrayList<>();
                    count10Files=0;
                    a++;
                }
                filesToDocs.add(fileRead);
                count10Files++;



            } catch (IOException e) {
                System.exit(0);
            }
        }
    }

    private void writeTenFiles(ArrayList<String> filesToDOC ,char a){
        for ( String file: filesToDOC){
            writeDocInToFile(file, "C:\\Users\\inbar\\Desktop\\test"+a);
        }

    }

    private String readFileToStream(File file, String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader (file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            //writeDocInToFile(stringBuilder,path);
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    private void writeDocInToFile(String file, String path){
        int countFiles =1;
        String pat = "<DOC>*(.+?)*</DOC>";
        Pattern pattern = Pattern.compile(pat, Pattern.DOTALL);
        Matcher mach = pattern.matcher(file);
        while (mach.find()){
            String currDoc = mach.group(0);
            writeToFile(path, currDoc, countFiles);
            countFiles++;
        }

    }

    private void writeToFile(String path, String docToWrite, int countFiles) {
        try {
            File file = new File(path+"\\"+countFiles);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("<DOC> ");

            fileWriter.write(docToWrite);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
