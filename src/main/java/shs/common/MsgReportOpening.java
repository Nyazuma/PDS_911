package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgReportOpening extends Message {

	protected Integer id;
	
	@JsonCreator
	public MsgReportOpening(@JsonProperty("id") Integer id) {
		super(MessageType.REPORTOPENING);
		this.id=id;
	}

	public Integer getId() {
		return id;
	}

}
