package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgUpdateEmplacement extends Message{

	protected String sensorID;
	protected String locationID;
	
	@JsonCreator
	public MsgUpdateEmplacement(@JsonProperty("sensorID") String sensorID, @JsonProperty("locationID") String locationID) {
		super(MessageType.UPDATEEMPLACEMENT);
		this.sensorID=sensorID;
		this.locationID=locationID;
	}

	public String getSensorID() {
		return sensorID;
	}

	public String getLocationID() {
		return locationID;
	}
	

}
