package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgNumberObjectFetch extends Message {
	
	protected String captorType;
	protected String captorState;
	protected String captorPlace;
	protected String captorFloor;
	protected String captorResidence;

	@JsonCreator
	public MsgNumberObjectFetch(
			@JsonProperty("captorType") String captorType, 
			@JsonProperty("captorState") String captorState, 
			@JsonProperty("captorPlace") String captorPlace, 
			@JsonProperty("captorFloor") String captorFloor, 
			@JsonProperty("captorResidence") String captorResidence) {
			super(MessageType.NUMBEROBJECTFETCH);
			this.captorType = captorType;
			this.captorState = captorState;
			this.captorPlace = captorPlace;
			this.captorFloor = captorFloor;
			this.captorResidence = captorResidence;
		}
	
	public String getCaptorType() {
		return captorType;
	}
	
	public String getCaptorState() {
		return captorState;
	}
	
	public String getCaptorPlace() {
		return captorPlace;
	}
	
	public String getCaptorFloor() {
		return captorFloor;
	}
	
	public String getCaptorResidence() {
		return captorResidence;
	}

}