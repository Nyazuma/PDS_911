package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgChangeAlert extends Message{

	protected Integer id;
	protected boolean status;
	
	@JsonCreator
	public MsgChangeAlert(@JsonProperty("id") Integer id, @JsonProperty("status") boolean status) {
		super(MessageType.CHANGEALERT);
		this.id=id;
		this.status=status;
	}

	public Integer getId() {
		return id;
	}

	public boolean getStatus() {
		return status;
	}
	
}
