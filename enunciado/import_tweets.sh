#!/bin/sh
mongo tweets --eval "db.dropDatabase()"
cat debate08_sentiment_tweets.tsv | tr '\r' '\n' | grep -v ^# | mongoimport -d tweets -c tweets --type tsv --headerline
