package shs.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgLineResult extends Message{

	protected List<String> lineResult;
	
	@JsonCreator
	public MsgLineResult(@JsonProperty("lineResult") List<String> lineResult) {
		super(MessageType.LINERESULT);
		this.lineResult = lineResult;
	}

	public List<String> getLineResult() {
		return lineResult;
	}
	

}
