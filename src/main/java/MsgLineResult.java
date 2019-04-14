import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import shs.common.Message;
import shs.common.MessageType;

public class MsgLineResult extends Message {
	
	protected List<String> lineResult;

	@JsonCreator
	public MsgLineResult(@JsonProperty List<String> lineResult) {
		super(MessageType.LINERESULT);
		this.lineResult = lineResult;
	}

	public List<String> getLineResult() {
		return lineResult;
	}

	
}
