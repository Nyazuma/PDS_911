package shs.common;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

//import java.util.List;
//
//import javax.swing.JFormattedTextField;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgUpdateObjectNonConfigured extends Message{

	protected String minCapteur;
	protected String maxCapteur;
	protected String minDate;
	protected String maxDate;
	protected Integer id;
	protected String typeCapteur;

	@JsonCreator

	public MsgUpdateObjectNonConfigured(
			@JsonProperty("id") Integer id,
			@JsonProperty("typeCapteur") String typeCapteur,
			@JsonProperty("minCapteur") String minCapteur,
			@JsonProperty("maxCapteur") String maxCapteur, 
			@JsonProperty("minDate") String minDate, 	
			@JsonProperty("maxDate") String maxDate) {
		super(MessageType.UPDATEOBJECTNONCONFIG);
		this.id= id;
		this.typeCapteur=typeCapteur;
		this.minCapteur=minCapteur;
		this.maxCapteur=maxCapteur;
		this.minDate=minDate;
		this.maxDate=maxDate;
	}

	public String getMinCaptor() {
		return minCapteur;
	}
	public Integer getId() {
		return id;
	}
	public String getTypeCaptor() {
		return typeCapteur;
	}
	public String getMaxCaptor() {
		return maxCapteur;
	}
	public String getMinDate() {
		return minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}



}
