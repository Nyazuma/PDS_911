package shs.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgUpdateObject extends Message{

	protected List<String> object;

	@JsonCreator
	public MsgUpdateObject(@JsonProperty("object") List<String> object) {
		super(MessageType.UPDATEOBJECT);
		this.object=object;
	}

	public List<String> getObject() {
		return object;
	}




}
