package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgDeleteEmplacement extends Message{
	
	protected String sensorID;
	
	@JsonCreator
	public MsgDeleteEmplacement(@JsonProperty("sensorID") String sensorID) {
		super(MessageType.DELETEEMPLACEMENT);
		this.sensorID=sensorID;
	}

	public String getSensorID() {
		return sensorID;
	}

}
