package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgReportMotion extends Message {
	
	protected int id;

	@JsonCreator
	public MsgReportMotion(@JsonProperty("id") Integer id) {
		super(MessageType.REPORTMOTION);
		this.id=id;
	}

	public int getId() {
		return id;
	}

}
