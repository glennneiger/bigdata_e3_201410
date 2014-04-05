#!/bin/sh
mongo tweets --eval "db.dropDatabase()"
cat debate08_sentiment_tweets.tsv | sed 's/\r9/\n9/g' | sed 's/#\r/#\n/g' | grep -v ^# | mongoimport -d tweets -c tweets --type tsv --headerline
