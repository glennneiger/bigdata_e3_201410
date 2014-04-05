/**
 * 
 */
package co.uniandes.bigdata5.mongo;

import com.mongodb.BasicDBObject;

/**
 * @author sebastian
 *
 */
public class TweetDocument extends BasicDBObject {

    /**
     * Generated serialization constant
     */
    private static final long serialVersionUID = 5041362872689473376L;

    /**
     * Builds a mongo object with the basic tweet fields
     * 
     * @param id Tweet id
     * @param date Publication date
     * @param content Tweet content
     * @param author Tweet author
     * @param nickname Tweet author's nickname
     */
    public TweetDocument(int id, String date, String content, String author, String nickname) {
        super("id", id);
        append("date", date);
        append("content", content);
        append("author", author);
        append("nickname", nickname);
        append("ratings", new BasicDBObject());
    }
    
    /**
     * Adds a mechanical turk sentiment rating to the tweet
     * 
     * @param id
     * @param rating
     */
    public void addRating(int id, int rating)
    {
       BasicDBObject ratings = (BasicDBObject) get("ratings");
       ratings.append("id", id);
       ratings.append("rating", rating);
    }
}
