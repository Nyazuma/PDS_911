package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgUpdateNonConfigured extends Message {

	protected Integer id;
	protected String minCapteur;
	protected String maxCapteur;
	protected String minDate;
	protected String maxDate;
	protected String typeCapteur;
	
	@JsonCreator
	public MsgUpdateNonConfigured( @JsonProperty("id") Integer id, @JsonProperty("typeCapteur") String typeCapteur, 
			@JsonProperty("minCapteur") String minCapteur, @JsonProperty("maxCapteur") String maxCapteur, 
			@JsonProperty("minDate") String minDate, @JsonProperty("maxDate ") String maxDate) {
		super(MessageType.UPDATEOBJECTNONCONFIG);
		this.id=id;
		this.typeCapteur=typeCapteur;
		this.minCapteur = minCapteur;
		this.maxCapteur = maxCapteur;
		this.minDate = minDate;
		this.maxDate = maxDate;
	}
	
	
	public Integer getID() {
		return id;
	}
	public String getTypeCapteur() {
		return typeCapteur;
	}
	public String getMinDate() {
		return minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public String getMinCapteur() {
		return minCapteur;
	}
	public String getMaxCapteur() {
		return maxCapteur;
	}
	

}
