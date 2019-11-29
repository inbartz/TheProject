import com.sun.deploy.util.SyncAccess;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String s = "maor is $3bn U.S. dollars between 10 and 100 and a 55.0008 percent accuracy 06 MAY or i masdf sdf May 1995";
        Parser parser = new Parser();
        s = parser.cleanDoc(s);
        System.out.println(s);
    }
}
