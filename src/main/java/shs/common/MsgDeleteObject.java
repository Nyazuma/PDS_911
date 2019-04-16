package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgDeleteObject extends Message{

	protected String object;
	
	@JsonCreator
	public MsgDeleteObject(@JsonProperty("object") String object) {
		super(MessageType.DELETEOBJECT);
		this.object=object;
	}
	
	public String getObject() {
		return object;
	}


}
