package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgNumberObjectUpdated extends Message {
	
	protected String dateFrom;
	protected String dateTo;

	@JsonCreator
	public MsgNumberObjectUpdated(@JsonProperty("dateFrom") String dateFrom, @JsonProperty("dateTo") String dateTo) {
			super(MessageType.NUMBEROBJECTUPDATED);
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