package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgReportRFID extends Message {

	protected Integer id;
	
	@JsonCreator
	public MsgReportRFID(@JsonProperty("id") Integer id) {
		super(MessageType.REPORTRFID);
		this.id=id;
	}

	public Integer getID() {
		return id;
	}

}
