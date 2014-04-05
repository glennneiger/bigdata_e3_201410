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

import javax.swing.JFileChooser;

import co.uniandes.bigdata5.mongo.Mongo;
import co.uniandes.bigdata5.mongo.TweetDocument;

/**
 * @author sebastian
 * 
 */
public class DatasetReader {

    private Mongo mongo = Mongo.getInstance();
    
    public DatasetReader(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;

            
            // Skip the headers
            while ((line = br.readLine()).startsWith("#")) {
            }
            line = br.readLine();

            int tweetCount = 0;
            while ((line = br.readLine()) != null) {
                // Tab separated values
                
                String[] data = line.split("\t");
                if(data[2].startsWith("\"") && !data[2].endsWith("\""))
                {
                    line = br.readLine();
                    String[] newData = line.split("\t");
                    ArrayList<String> temporaryArray = new ArrayList<>();
                    //
                    temporaryArray.addAll(Arrays.asList(data));
                    temporaryArray.addAll(Arrays.asList(newData));
                    data = (String[])temporaryArray.toArray(data);
                }
                TweetDocument tweet = null;
                try {
                    tweet = new TweetDocument(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(line);
                    System.out.println(data.length);
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    return;
                }
                for (int i = 5; i < data.length; i++) {
                    if (!data[i].isEmpty())
                        tweet.addRating(i, Integer.parseInt(data[i]));
                }
                //mongo.addTweet(tweet);
                tweetCount++;
            }
            
            System.out.println("Added "+tweetCount+" tweets");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        JFileChooser jfc = new JFileChooser(new File("/home/sebastian/Documents/201410/Big Data/Entregables"));
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            new DatasetReader(jfc.getSelectedFile());
        // TODO Auto-generated method stub

    }

}
