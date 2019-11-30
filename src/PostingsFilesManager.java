import java.io.File;
import java.util.*;


class PostingFile{
    // This class represents a single posting file containing terms
    private String _filepath;
    private HashMap<String,Term> termStringToTerm;

    PostingFile(String filepath){
        this._filepath = filepath;
    }

    PostingFile(String filepath, ArrayList<Term> terms){
        this._filepath = filepath;
        this.termStringToTerm = new HashMap<String, Term>();
        for (Term term: terms){
            termStringToTerm.put(term.m_Term, term);
        }
    }

    void updateTerms(ArrayList<Term> newTerms){
        for (Term term: newTerms){
            if (this.termStringToTerm.containsKey(term.m_Term)){
                this.termStringToTerm.get(term.m_Term).merge(term);
            } else {
                this.termStringToTerm.put(term.m_Term, term);
            }
        }
    }

    static PostingFile readIfExists(String _filepath){
        File f = new File(_filepath);
        if (f.exists()){
            //TODO: read the file and load posting file
            return new PostingFile(_filepath);
        } else {
            return new PostingFile(_filepath);
        }
    }

    void writeToFile(){

    }
}


public class PostingsFilesManager {
    // this class handles all posting file writings and readings
    public String folderPath;
    private ArrayList<Term> _currentTerms;

    public PostingsFilesManager(ArrayList<Term> currentTerms) {
        _currentTerms = currentTerms;
    }

    private String getPostingFileNameForTerm(String term){
        return "POSTING_" + term.substring(0,1) + ".txt";
    }

    public void updateCurrentTermsInDisk(){
        HashMap filenameToTerms = new HashMap<String,ArrayList<Term>> ();
        for (Term term : _currentTerms){
            String filename = this.getPostingFileNameForTerm(term.m_Term);
            if (!filenameToTerms.containsKey(filename)){
                filenameToTerms.put(filename, new ArrayList<Term>());
            }
            ArrayList<Term> fileTerms = (ArrayList<Term>) filenameToTerms.get(filename);
            fileTerms.add(term);
        }

        for (String filename: (Set<String>)filenameToTerms.keySet()){
            PostingFile postingFile = PostingFile.readIfExists(filename);
            postingFile.updateTerms((ArrayList<Term>) filenameToTerms.get(filename));
            postingFile.writeToFile();
        }

        this._currentTerms.clear();
    }
}
