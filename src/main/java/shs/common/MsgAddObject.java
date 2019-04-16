package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgAddObject extends Message {

	protected String object;
	protected String addresseMac;
	
	@JsonCreator
	public MsgAddObject(@JsonProperty("object") String object, @JsonProperty("addresseMac") String addresseMac) {
		super(MessageType.ADDOBJECT);
		this.object=object;
		this.addresseMac=addresseMac;
	}
	
	public String getObject() {
		return object;
	}

	public String getAddresseMac() {
		return addresseMac;
	}

	
}
