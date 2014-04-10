<?php

//include 'lotsofcode-tag-cloud-f63f8ca/src/lotsofcode/TagCloud/TagCloud.php';

echo "<!DOCTYPE html>\n";
echo "<html>\n";
echo "<head>\n";
echo "<title>Big Data - Taller 3</title>\n";
?>
<style type="text/css">
#tagcloud{
  color: #dda0dd;
  font-family: Arial, verdana, sans-serif;
  width:200px;
  border: 1px solid black;
  text-align: center;
}

#tagcloud a{
  color: green;
  text-decoration: none;
  text-transform: capitalize;
}
</style>
<?php
echo "</head>\n";
echo "<body>\n";

// connect
$m = new MongoClient("mongodb://clusterbigdata59.virtual.uniandes.edu.co");

// select a database
$db = $m->Grupo05_tweetsmain;

// select a collection
$collection = $db->tweets;

echo "<h1>Mapa</h1>\n";

$mapurl = "http://www.mapquestapi.com/staticmap/v4/getmap?key=Fmjtd|luur2qu72g%2Caa%3Do5-9a2w9w&size=550,350&zoom=1&center=45,0&pois=";

$cursor = $collection->find();
// iterate through the results
$counter = 0;
foreach ($cursor as $document) {
  foreach ($document as $item) {
    foreach ($item as $loc) {
      $coords = array();
      foreach ($loc as $coord) {
        array_push($coords, $coord);
        $counter++;
      }
      if (sizeof($coords) == 2 && $counter < 50) {
        $mapurl = $mapurl . "mcenter," . $coords[0] . "," . $coords[1] . "|";
      }
    }
  }
}

//$mapurl = $mapurl . "&scale=110935786&center=18.103006224564613,-36.78173100272301&imagetype=JPEG&projection=sm&key=Fmjtd%7Cluur2qu72g%2Caa%3Do5-9a2w9w";

echo "<iframe width=\"600\" height=\"400\" src=\"" . $mapurl . "\"></iframe>\n";

echo "<hr />\n";

echo "<h1>tagCloud</h1>\n";

$ops = array(
  array(
    '$project' => array(
      "hashtags" => 1
    )
  ),
  array('$unwind' => '$hashtags'),
  array(
    '$group' => array(
      "_id" => '$hashtags',
      "count" => array('$sum' => 1),
    ),
  ),
);
$cursor = $collection->aggregate($ops);

echo "<p>\n";
// iterate through the results
foreach ($cursor as $document) {
  foreach ($document as $tag) {
    $intCount = intval($tag["count"]);
    if ($intCount <= 5) {
      $size = "8";
    }
    elseif ($tag["count"] > 5 && $tag["count"] <= 20) {
      $size = "10";
    }
    elseif ($tag["count"] > 20 && $tag["count"] <= 100) {
      $size = "12";
    }
    elseif ($tag["count"] > 100 && $tag["count"] <= 500) {
      $size = "16";
    }
    elseif ($tag["count"] > 500 && $tag["count"] <= 1000) {
      $size = "20";
    }
    else {
      $size = "30";
    }
    echo "<a href=\"http://twitter.com/search?q=%23" . substr($tag["_id"], 1) . "\" style=\"font-size: " . $size . "pt\">" . $tag["_id"] . "</a> ";
    //echo $intCount . "<br />\n";
  }
}
echo "</p>\n";

echo "<hr />\n";

echo "<h1>Polaridad</h1>\n";
echo "<h3 style=\"color: #0f0\">Positivo</h3>\n";
echo "<h3 style=\"color: #000\">Neutral</h3>\n";
echo "<h3 style=\"color: #f00\">Negativo</h3>\n";

$cursor = $collection->find();
// iterate through the results
foreach ($cursor as $document) {
  if ($document["ourFoundSentiment"] == "1") {
    $colour = "#f00";
  }
  elseif ($document["ourFoundSentiment"] == "2") {
    $colour = "#0f0";
  }
  else {
    $colour = "#000";
  }
    
  echo "<p style=\"color: " . $colour . "\">\n";
  echo $document["content"] . "\n";
  echo "</p>\n";
}

echo "<p>Los datos de Twitter fueron obtenidos por <a href=\"http://www.infochimps.com/datasets/twitter-sentiment-dataset-2008-debates\">InfoChimps</a>.</p>\n";
echo "</body>\n";
echo "</html>\n";
?>
