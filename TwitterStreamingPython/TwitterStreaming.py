import os
import json
import sys
from TwitterAPI import TwitterAPI
from pymongo import Connection
import json

conn = Connection()
db = conn.cl_database
test = db.test
consumer_secret = os.environ["TWITTER_CONSUMER_SECRET"]
access_token_secret = os.environ["TWITTER_ACCESS_TOKEN_SECRET"]

api = TwitterAPI("HwGr44NowJ3vR86MFlnQfpypE", consumer_secret, "114469092-6f5A0WnNkiBBKy1xdN7aN3qWSUraSyThaUDeurer", access_token_secret)

#San Andres y Providencia
#boundingBox1 = "-81.7863984108,12.4616635796,-81.3097076416,12.4716635796,-81.3097076416,13.4192660311,-81.7863984108,13.4292660311,"
#Partes de Colombia
#boundingBox2 = "-77.3039765358,0.281175832,-72.9820709229,0.281175832,-72.9820709229,12.6670794767,-77.3039765358,12.6670794767,"
#boundingBox3 = "-78.6662812233,1.0721300131,-76.4537506104,1.0721300131,-76.4537506104,4.1546206068,-78.6662812233,4.1546206068,"
#boundingBox4 = "-74.6782441139,-0.4418842249,-72.4547271729,-0.4418842249,-72.4547271729,8.7397855353,-74.6782441139,8.7397855353,"
#boundingBox5 = "-72.8764863014,-2.2406706529,-69.4225006104,-2.2406706529,-69.4225006104,6.9409991073,-72.8764863014,6.9409991073,"
#boundingBox6 = "-71.1186738014,1.8892749125,-67.3131256104,1.8892749125,-67.3131256104,6.242526711,-71.1186738014,6.242526711,"
#boundingBox7 = "-73.5796113014,11.4790138485,-71.4000396729,11.4790138485,-71.4000396729,12.7109248719,-73.5796113014,12.7109248719,"
#boundingBox8 = "-73.2280488014,10.4435833784,-72.3668365479,10.4435833784,-72.3668365479,12.3248196975,-73.2280488014,12.3248196975"

#boundingTotal = boundingBox1 + boundingBox2 + boundingBox3 + boundingBox5 + boundingBox6 + boundingBox7 + boundingBox8
#print boundingTotal
r = api.request('statuses/filter', {'locations':'-79.62,-5.15,-66.27,13.26'})
with open("tweets_streaming.json", "a") as streamingfile:
    for item in r.get_iterator():
        tweet = str(item)
        if " u'Colombia'" in tweet:
            streamingfile.write(tweet)
