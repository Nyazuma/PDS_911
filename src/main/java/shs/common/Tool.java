
package shs.common;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shs.server.DataConfig;

public class Tool {
	
	public static Logger logger = Logger.getLogger(DataConfig.class);
	
	public static Message jsonToMessage(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> map  = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
			if(map.get("type").toString().equalsIgnoreCase(MessageType.ADDOBJECT.toString())){
				return mapper.readValue(json, MsgAddObject.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.BOOLEANRESULT.toString())){
				return mapper.readValue(json, MsgBooleanResult.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.CONNECTION.toString())){
				return mapper.readValue(json, MsgConnection.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.INTRESULT.toString())){
				return mapper.readValue(json, MsgIntResult.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.LISTRESULT.toString())){
				return mapper.readValue(json, MsgListResult.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.STRINGRESULT.toString())){
				return mapper.readValue(json, MsgStringResult.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.LISTOBJECT.toString())){
				return mapper.readValue(json, MsgListObject.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.DELETEOBJECT.toString())) {
				return mapper.readValue(json, MsgDeleteObject.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.UPDATEOBJECT.toString())) {
				return mapper.readValue(json, MsgUpdateObject.class); 
			}
			// Don't forget messages without a specific class!
			return mapper.readValue(json, Message.class);
		} 
		catch (JsonParseException e) {e.printStackTrace();} 
		catch (JsonMappingException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
	public static String messageToJSON(Message object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(object);
		}
		catch (JsonParseException e) { e.printStackTrace();}
		catch (JsonMappingException e) {e.printStackTrace();}
		catch (IOException e) { e.printStackTrace(); }
		return jsonString;
	}
	
		
}

