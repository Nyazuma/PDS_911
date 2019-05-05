package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgNumberObjectAlert extends Message {
	
	protected String dateFrom;
	protected String dateTo;

	@JsonCreator
	public MsgNumberObjectAlert(@JsonProperty("dateFrom") String dateFrom, @JsonProperty("dateTo") String dateTo) {
			super(MessageType.NUMBEROBJECTALERT);
			this.dateFrom=dateFrom;
			this.dateTo=dateTo;
		}
	
	public String getDateFrom() {
		return dateFrom;
	}
	
	public String getDateTo() {
		return dateTo;
	}

}
