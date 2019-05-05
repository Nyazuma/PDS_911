package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgNumberObjectDeleted extends Message {
	
	protected String dateFrom;
	protected String dateTo;

	@JsonCreator
	public MsgNumberObjectDeleted(@JsonProperty("dateFrom") String dateFrom, @JsonProperty("dateTo") String dateTo) {
			super(MessageType.NUMBEROBJECTDELETED);
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