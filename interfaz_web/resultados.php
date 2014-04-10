<?php

echo "<!DOCTYPE html>\n";
echo "<html>\n";
echo "<head>\n";
echo "<title>Big Data - Taller 3</title>\n";
echo "</head>\n";
echo "<body>\n";

echo "<h3>\n";
echo "db.tweets.find( { type: \"" . $_POST["consulta"] . "\" } )";
echo "</h3>\n";
// connect
$m = new MongoClient("mongodb://clusterbigdata59.virtual.uniandes.edu.co");

// select a database
$db = $m->Grupo05_tweetsmain;

// select a collection
$collection = $db->tweets;

// find everything in the collection
$cursor = $collection->find();

// iterate through the results
echo "<p>\n";
foreach ($cursor as $document) {
  echo $document[htmlspecialchars($_POST["consulta"])] . "<br />\n";
}

echo "</p>\n";

echo "<p>Los datos de Twitter fueron obtenidos por <a href=\"http://www.infochimps.com/datasets/twitter-sentiment-dataset-2008-debates\">InfoChimps</a>.</p>\n";
echo "</body>\n";
echo "</html>\n";
?>
