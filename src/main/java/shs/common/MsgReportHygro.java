package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgReportHygro extends Message {

	protected Integer id;
	// The hygroValue mesures the percentage(0-100) of humidity
	protected Integer hygroValue;
	
	@JsonCreator
	public MsgReportHygro(@JsonProperty("id") Integer id, @JsonProperty("hygroValue")Integer hygroValue) {
		super(MessageType.REPORTHYGRO);
		this.id=id;
		this.hygroValue=hygroValue;
	}

	public Integer getId() {
		return id;
	}

	public Integer getHygroValue() {
		return hygroValue;
	}
	
}
