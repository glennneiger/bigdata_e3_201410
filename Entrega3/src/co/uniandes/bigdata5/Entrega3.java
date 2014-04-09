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
        
        String help = "Usage:\njava -jar entrega3 COMMAND options\nPossible commands:\n\tload_mongo\tLoad mongo " +
        		"with main tweetset (requires file parameter)\n\tsentiment\tPerform sentiment analysis over the " +
        		"tweetset\n\tgeolocation\tAttempt to guess geographic location based on possible mention of cities";
        
        if(args.length < 1)
        {
            System.out.println(help);
            System.exit(-1);
        }
        
        String command = args[0];
        switch (command){
            case "load_mongo":
                if(args.length < 2 || !(new File(args[1])).exists() || (new File(args[1])).isDirectory()){
                    System.out.println("Tweet file required");
                    System.exit(-1);
                }
                
                File tweetFile = new File(args[1]);
                new DatasetReader(tweetFile);
                break;
            default:
                System.out.println("Invalid command");
                System.exit(-1);
        }
        


    }

}
