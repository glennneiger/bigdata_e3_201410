/**
 * 
 */
package co.uniandes.bigdata5.mongo;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author sebastian
 *
 */
public class Mongo {

    private static Mongo instance;
    private com.mongodb.Mongo mongo;
    private DB db;
    private DBCollection col;
    
    private Mongo()
    {
        try {
            mongo = new com.mongodb.Mongo();
            db =  mongo.getDB("tweets_main");
            col = db.getCollection("tweets");
            //col.ensureIndex(, name, unique);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        instance = this;
    }
    
    public void addTweet(TweetDocument tweet)
    {
        col.insert(tweet);
    }
    
    public final static Mongo getInstance()
    {
        if(instance==null)
            instance = new Mongo();
        
        return instance;
    }
}
