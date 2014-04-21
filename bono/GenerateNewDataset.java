import java.util.*;
import java.text.*;
import java.io.*;
import twitter4j.*;

public class GenerateNewDataset {
    public static void main(String[] args) {
        Twitter twitter = new TwitterFactory().getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String until = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, -10);
        String since = sdf.format(cal.getTime());
        String from = "from:falcao OR from:jamesdrodriguez OR from:NairoQuintana OR from:CamiloVillegasR OR from:ElGirald OR from:JuanManSantos OR from:AlvaroUribeVel OR from:carlosvives OR from:Jorgitoceledon OR from:shakira OR from:juanes\n";
        
        Query query = new Query(from + " since:" + since + " until:" + until);
        QueryResult result;
        
        try {
            FileWriter fstream = new FileWriter("tweets_colombianos.tsv");
            BufferedWriter out = new BufferedWriter(fstream);
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                sdf = new SimpleDateFormat("M/dd/yy H:mm");
                out.write("tweet.id\tpub.date.GMT\tcontent\tauthor.name\tauthor.nickname\trating.1\trating.2\trating.3\trating.4\trating.5\trating.6\trating.7\trating.8");
                for (Status tweet : tweets) {
                    out.write(tweet.getId() + "\t" + 
                            sdf.format(tweet.getCreatedAt()) + "\t" + 
                            tweet.getText() + "\t" + 
                            tweet.getUser().getScreenName() + "\t" + 
                            tweet.getUser().getName() + "\n");
                }
            }
            while ((query = result.nextQuery()) != null);
            out.close();
        }
        catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        System.exit(0);
    }
}
