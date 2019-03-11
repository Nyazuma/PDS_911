package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgAddObject extends Message {

	protected String object;
	
	@JsonCreator
	public MsgAddObject(@JsonProperty("object") String object) {
		super(MessageType.ADDOBJECT);
		this.object=object;
	}
	
	public String getObject() {
		return object;
	}

}
