import java.io.IOException;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
         String s = "POLITICIANS,  PARTY PREFERENCES \n" +
                "\n" +
                "   Summary:  Newspapers in the Former Yugoslav Republic of \n" +
                "   Macedonia have published the results of opinion polls, \n" +
                "   indicating the relative popularity of politicians, \n" +
                "   political parties, and attitudes toward the political system. \n" +
                "\n" +
                "   The 22-23 January edition of the Skopje newspaper VECER in \n" +
                "Macedonian published on pages 6-7 the results of an opinion poll \n" +
                "conducted by the \"BriMa\" agency in November 1993. According to \n" +
                "VECER, 1,036 respondents were classified by age and residence, but \n" +
                "the paper did not explain the methodology or give the margin of \n" +
                "error.  For the purpose of comparison, the paper cited the results \n" +
                "of an unidentified poll made in May 1993. The approval/disapproval \n" +
                "ratings, in percent, for ten Macedonian politicians were: \n" +
                "\n" +
                "                                           November 1993    May 1993 \n" +
                "\n" +
                "Kiro Gligorov, President of the Republic      76/15           78/13 \n" +
                "\n" +
                "Vasil Tupurkovski, former Macedonian          50/36           43/37 \n" +
                "   official in Federal Yugoslavia \n" +
                "\n" +
                "Ljubomir Frckovski, Interior Minister         50/42           42/43 \n" +
                "\n" +
                "Stojan Andov, Parliamentary Chairman          48/41           48/39 \n" +
                "\n" +
                "Branko Crvenkovski, Prime Minister            46/41           44/38 \n" +
                "\n" +
                "Vlado Popovski, Defense Minister              41/41           36/37 \n" +
                "\n" +
                "Stevo Crvenkovski, Foreign Minister           40/43   No Data Given \n" +
                "\n" +
                "Petar Gosev, Democratic Party leader          34/53           40/42 \n" +
                "\n" +
                "Todor Petrov, Independent parliamentarian     32/53   No Data Given \n" +
                "\n" +
                "Nikola Popovski, Social Democratic            29/46           32/42 \n" +
                "   Party parliamentarian \n" +
                "\n";


         Parse p = new Parse();
       //  p.findPattern("(\\d)+(\\d)+(/)+(\\d)+(\\d)+( )",s);
        StringBuilder st1 = new StringBuilder();
        st1.append("10 percentage");
        //String s4= p.MNumber(st1);
        //System.out.println(s4);
        //String s5= p.changeNum("1,050,060", 1000000);
        //System.out.println(s5);
        p.presentage(st1);
        System.out.println(st1);

    }
}
