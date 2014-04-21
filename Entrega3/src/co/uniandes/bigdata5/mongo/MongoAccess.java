/**
 * 
 */
package co.uniandes.bigdata5.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

/**
 * @author sebastian
 *
 */
public class MongoAccess {

    private static MongoAccess instance;
    private com.mongodb.Mongo mongo;
    private DB db;
    private DBCollection col;
    
    private MongoAccess(boolean bonus)
    {
        try {
            //Connection setup
        	mongo = new MongoClient();
            if (bonus)
            	db =  mongo.getDB("Grupo05_tweetsmain");
            else
            	db =  mongo.getDB("Grupo05_tweetsbonus");
            col = db.getCollection("tweets");
            mongo.setWriteConcern(WriteConcern.SAFE); //Exception thrown in any error
            
            //Index creation
//            BasicDBObject uniqueIndex = new BasicDBObject("id", 1 ); 
//            uniqueIndex.append("unique", "true");
//            uniqueIndex.append("dropDups","true");
//            col.createIndex(uniqueIndex);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        instance = this;
    }
    
    public void addTweet(TweetDocument tweet)
    {
        col.insert(tweet);
    }
    
    public long getTweetCount()
    {
        return col.count();
    }
    
    public final static MongoAccess getInstance(boolean bonus)
    {
        if(instance==null)
            instance = new MongoAccess(bonus);
        
        return instance;
    }
}
