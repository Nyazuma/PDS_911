package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgStringResult extends Message {

	protected String string;
	
	@JsonCreator
	public MsgStringResult(@JsonProperty("string") String string) {
		super(MessageType.STRINGRESULT);
		this.string=string;
	}
	
	public String getString() {
		return string;
	}
}
