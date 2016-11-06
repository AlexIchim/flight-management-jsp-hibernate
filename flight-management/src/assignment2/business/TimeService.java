package assignment2.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.*;

/**
 * Created by Alex PC on 18/10/2016.
 */

public class TimeService {
	
    public static String getCurrentLocalTime (String latitude, String longitude) throws Exception  {
        
    	String key = "9ca1f6cb851ce2d8c004a425e5456";
    	String requestURL = "http://api.worldweatheronline.com/free/v2/tz.ashx?q=" + latitude + "," + longitude +"&format=json&key=" + key;

		URL obj = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		
		//Parse JSON object
		ObjectMapper objectMapper  = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(response.toString());
		String localTimeResult =  rootNode.get("data").get("time_zone").get(0).get("localtime").asText();
		
		return localTimeResult;    	
    }
}
