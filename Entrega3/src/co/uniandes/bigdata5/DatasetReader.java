/**
 * 
 */
package co.uniandes.bigdata5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.uniandes.bigdata5.mongo.MongoAccess;
import co.uniandes.bigdata5.mongo.TweetDocument;
import co.uniandes.bigdata5.sentimentAnalysis.SentimentAnalyzer;
/**
 * @author sebastian
 * 
 */
public class DatasetReader {

    private MongoAccess mongoAccess = MongoAccess.getInstance();
    
    private static final String REGEX = "#(\\w*)";
    private Pattern hashtagPattern = Pattern.compile(REGEX);
    
    public DatasetReader(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            SentimentAnalyzer sa = new SentimentAnalyzer();
            
            String line;

            long initialTweetCount = mongoAccess.getTweetCount();
            
            // Skip the headers
            while ((line = br.readLine()).startsWith("#")) {
            }
            line = br.readLine();
            ArrayList<Integer> valueSet = new ArrayList<Integer>();
            for (int i =0;i<10;i++)
            {
                valueSet.add(0);
            }
            double tweetCount = 0.0;
            double hitCount = 0.0;
            while ((line = br.readLine()) != null) {
                // Tab separated values
                
                String[] data = line.split("\t");
                while(data.length < 5 || (data[2].startsWith("\"") && !data[2].endsWith("\"")))
                {
                    line = br.readLine();
                    String[] newData = line.split("\t");
                    ArrayList<String> temporaryArray = new ArrayList<>();
                    data[2] = data[2] + newData[0];
                    temporaryArray.addAll(Arrays.asList(data));
                    temporaryArray.addAll(Arrays.asList(newData));
                    temporaryArray.remove(data.length);
                    data = (String[])temporaryArray.toArray(data);
                }
                TweetDocument tweet = null;
                tweet = new TweetDocument(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]);
                
                //Hashtags
                Matcher matcher = hashtagPattern.matcher(data[2]);
                ArrayList<String> hashtags = new ArrayList<String>();
                while(matcher.find())
                {
                	hashtags.add(matcher.group());
                }
                if(hashtags.size()>0)
                	tweet.append("hashtags", hashtags.toArray());
                
                //Rating processing
                double ratingCount = 0.0;
                double[] ratings = {0.0,0.0,0.0,0.0};
                int ratingSummary = -1;
                for (int i = 5; i < data.length; i++) {
                    if (!data[i].isEmpty())
                    {
                        ratings[Integer.parseInt(data[i])-1]++;
                        tweet.addRating(i, Integer.parseInt(data[i]));
                        ratingCount++;
                    }                   
                }
                
                for (int i = 0; i < ratings.length; i++) {
                    ratings[i] /= ratingCount;
                    if(ratings[i] > 0.5)
                    {
                        ratingSummary = i+1;
                        break;
                    }
                }
                
                if(ratingSummary==-1)
                {
                    if(ratings[0]+ratings[1] > 0.5)
                        ratingSummary = 2;
                    else
                        ratingSummary = 4;
                }
                tweet.append("ratingSummary", ratingSummary);
                int sentiment = sa.findSentiment(data[2]);
                
                valueSet.ensureCapacity(sentiment+2);
                Integer stored = valueSet.get(sentiment);
                if(stored == null)
                    stored = new Integer(0);
                stored++;
                valueSet.set(sentiment, stored);
                
                if((sentiment <=2 && ratingSummary == 1) || (sentiment >=4 && ratingSummary == 2) || (sentiment == 3 && ratingSummary == 3) || ratingSummary == 4)
                {
                    hitCount++;
                }
                //mongoAccess.addTweet(tweet);
                tweetCount++;
            }
            
            System.out.println("Read "+tweetCount+" tweets");
            System.out.println("Added "+(mongoAccess.getTweetCount()- initialTweetCount)+" tweets");
            System.out.println("Hit count "+(hitCount/tweetCount)+"%");
            Arrays.toString(valueSet.toArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
