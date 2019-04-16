package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgReportCall extends Message {

	protected Integer id;
	
	@JsonCreator
	public MsgReportCall(@JsonProperty("id") int id) {
		super(MessageType.REPORTCALL);
		this.id=id;
	}

	public Integer getId() {
		return id;
	}

	
}
