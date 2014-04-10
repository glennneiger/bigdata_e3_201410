package co.uniandes.bigdata5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.ardverk.collection.PatriciaTrie;
import org.ardverk.collection.StringKeyAnalyzer;
import org.ardverk.collection.Trie;


public class GeoData {
	
	private final static String GEODATA_LOCATION = "."+File.separator+"geodata.csv";
	private Trie<String, float[]> locations;
	public GeoData(){
		try {
			File file = new File(GEODATA_LOCATION);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			locations = new PatriciaTrie<String,float[]>(StringKeyAnalyzer.INSTANCE);
			
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				String[] data  = line.split(",");
				String name = data[0].toLowerCase();
				float lat = Float.parseFloat(data[1]);
				float lon = Float.parseFloat(data[2]);
				float[] latlon = {lat,lon};
				locations.put(name,latlon);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public float[] getLocation(String tweet)
	{
		String[] data = tweet.split(" ");
		for (int i = 0; i < data.length; i++) {
			if(locations.containsKey(data[i]))
				return locations.get(data[i]);
		}
		return null;
	}
}
