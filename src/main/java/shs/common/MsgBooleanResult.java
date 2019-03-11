package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgBooleanResult extends Message{

	protected Boolean status;
	
	@JsonCreator
	public MsgBooleanResult(@JsonProperty("status") Boolean status) {
		super(MessageType.BOOLEANRESULT);
		this.status=status;
	}
	
	public Boolean getStatus() {
		return status;
	}


}
