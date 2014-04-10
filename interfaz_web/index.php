<?php

echo "<!DOCTYPE html>\n";
echo "<html>\n";
echo "<head>\n";
echo "<title>Big Data - Taller 3</title>\n";
echo "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n";
echo "<meta http-equiv=\"Content-Language\" content=\"es\">\n";
echo "</head>\n";
echo "<body>\n";
echo "<h1>Big Data - Taller 3</h1>\n";
echo "<form action=\"resultados.php\" method=\"post\">\n";
echo "<p>\n";
echo "<label for=\"consulta\">Consoluta: </label>\n";
echo "<select name=\"consulta\">\n";
echo "<option value=\"author\">Nombre</option>\n";
echo "<option value=\"nickname\">Apodo</option>\n";
echo "<option value=\"content\">Contenido</option>\n";
echo "</select>\n";
echo "<br />\n";
echo "<input type=\"submit\" value=\"Ejectuar\" /><br />\n";
echo "</p>\n";
echo "<p>\n";
echo "<a href=\"visualizacion.php\">Visualización</a>\n";
echo "</p>\n";
echo "</form>\n";
echo "<p>Los datos de Twitter fueron obtenidos por <a href=\"http://www.infochimps.com/datasets/twitter-sentiment-dataset-2008-debates\">InfoChimps</a>.</p>\n";
echo "</body>\n";
echo "</html>\n";
?>
