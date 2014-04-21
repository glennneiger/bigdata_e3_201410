/**
 * 
 */
package co.uniandes.bigdata5;

import java.io.File;

/**
 * @author sebastian
 *
 */
public class Entrega3 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String help = "Usage:\njava -jar entrega3 load_mongo tweetfile";
        
        if(args.length < 1)
        {
            System.out.println(help);
            System.exit(-1);
        }
        
        String command = args[0];
        boolean bonus;
        switch (command){
            case "load_mongo":
                if(args.length < 2 || !(new File(args[1])).exists() || (new File(args[1])).isDirectory()){
                    System.out.println("Tweet file required");
                    System.exit(-1);
                }
                
                bonus = false;
                File tweetFile = new File(args[1]);
                new DatasetReader(tweetFile, bonus);
                break;
            case "load_mongo_bonus":
                if(args.length < 2 || !(new File(args[1])).exists() || (new File(args[1])).isDirectory()){
                    System.out.println("Tweet file required");
                    System.exit(-1);
                }
                
                bonus = true;
                tweetFile = new File(args[1]);
                new DatasetReader(tweetFile, bonus);
                break;
            case "sentiment_analysis":
                //new SentimentAnalyzer();
                break;
            default:
                System.out.println("Invalid command");
                System.exit(-1);
        }
        


    }

}
