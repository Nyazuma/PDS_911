package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgIntResult extends Message {

	protected Integer number;
	
	@JsonCreator
	public MsgIntResult(@JsonProperty("number") Integer number) {
		super(MessageType.INTRESULT);
		this.number=number;
	}
	
	public Integer getNumber() {
		return number;
	}
}
