
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
			if(map.get("type").toString().equalsIgnoreCase(MessageType.LINERESULT.toString())){
				return mapper.readValue(json, MsgLineResult.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.DELETEOBJECT.toString())) {
				return mapper.readValue(json, MsgDeleteObject.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.UPDATEOBJECT.toString())) {
				return mapper.readValue(json, MsgUpdateObject.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.UPDATEOBJECTNONCONFIG.toString())) {
				return mapper.readValue(json, MsgUpdateNonConfigured.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.REPORTRFID.toString())) {
				return mapper.readValue(json, MsgReportRFID.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.REPORTCALL.toString())) {
				return mapper.readValue(json, MsgReportCall.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.REPORTHYGRO.toString())) {
				return mapper.readValue(json, MsgReportHygro.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.REPORTMOTION.toString())) {
				return mapper.readValue(json, MsgReportMotion.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.REPORTOPENING.toString())) {
				return mapper.readValue(json, MsgReportOpening.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.REPORTSMOKE.toString())) {
				return mapper.readValue(json, MsgReportSmoke.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.REPORTTEMPERATURE.toString())) {
				return mapper.readValue(json, MsgReportTemperature.class); 
			}
			if (map.get("type").toString().equalsIgnoreCase(MessageType.NUMBEROBJECTADDED.toString())) {
				return mapper.readValue(json, MsgNumberObjectAdded.class);
			}
			if (map.get("type").toString().equalsIgnoreCase(MessageType.NUMBEROBJECTDELETED.toString())) {
				return mapper.readValue(json, MsgNumberObjectDeleted.class);
			}
			if (map.get("type").toString().equalsIgnoreCase(MessageType.NUMBEROBJECTUPDATED.toString())) {
				return mapper.readValue(json, MsgNumberObjectUpdated.class);
			}
			if (map.get("type").toString().equalsIgnoreCase(MessageType.NUMBEROBJECTALERT.toString())) {
				return mapper.readValue(json, MsgNumberObjectAlert.class);
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.NUMBEROBJECTFETCH.toString())) {
				return mapper.readValue(json, MsgNumberObjectFetch.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.CHANGEALERT.toString())) {
				return mapper.readValue(json, MsgChangeAlert.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.UPDATEEMPLACEMENT.toString())) {
				return mapper.readValue(json, MsgUpdateEmplacement.class); 
			}
			if(map.get("type").toString().equalsIgnoreCase(MessageType.DELETEEMPLACEMENT.toString())) {
				return mapper.readValue(json, MsgDeleteEmplacement.class); 
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

