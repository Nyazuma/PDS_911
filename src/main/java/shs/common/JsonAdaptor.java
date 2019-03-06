
package shs.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAdaptor {

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		Connection connection = new Connection("Justin911", "911");
		String jsonString = null;

		// Convert object Connection to JSON
		try {
			jsonString = mapper.writeValueAsString(connection);
			// Create a JSON file in SHS repertory
			//mapper.writeValue(new File("connection.json"), connection);
			System.out.println(jsonString);
		}
		catch (JsonParseException e) { e.printStackTrace();}
		catch (JsonMappingException e) {e.printStackTrace();}
		catch (IOException e) { e.printStackTrace(); }
		
		// Convert JSON to object Connection
		try {
			connection = mapper.readValue(jsonString, Connection.class);
			// Read the JSON file in SHS repertory
			//Connection connection = mapper.readValue(new File("connection.json"), Connection.class);
			System.out.println(connection);
		} 
		catch (JsonParseException e) {e.printStackTrace();} 
		catch (JsonMappingException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
		
}

