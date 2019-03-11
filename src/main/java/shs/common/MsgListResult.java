package shs.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgListResult extends Message {

	protected List<Object> list;
	
	@JsonCreator
	public MsgListResult(@JsonProperty("list") List<Object> list) {
		super(MessageType.LISTRESULT);
		this.list=list;
	}
	
	public List<Object> getList() {
		return list;
	}
}
