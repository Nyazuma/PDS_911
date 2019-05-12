package shs.common;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

//import java.util.List;
//
//import javax.swing.JFormattedTextField;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgUpdateObjectNonConfigured extends Message{

	protected JTextField minCapteur;
	protected JTextField maxCapteur;
	protected JFormattedTextField minDate;
	protected JFormattedTextField maxDate;
	protected Integer id;
	protected String typeCapteur;

	@JsonCreator
	

	public MsgUpdateObjectNonConfigured(
			@JsonProperty("id") Integer id,
			@JsonProperty("typeCapteur") String typeCapteur,
			@JsonProperty("minCapteur") JTextField minCapteur,
			@JsonProperty("maxCapteur") JTextField maxCapteur, 
			@JsonProperty("minDate") JFormattedTextField minDate, 	
			@JsonProperty("maxDate") JFormattedTextField maxDate) {
		super(MessageType.UPDATEOBJECTNONCONFIG);
		this.id= id;
		this.typeCapteur=typeCapteur;
		this.minCapteur=minCapteur;
		this.maxCapteur=maxCapteur;
		this.minDate=minDate;
		this.maxDate=maxDate;
	}

	public String getMinCaptor() {
		return minCapteur.getText().toString();
	}
	public Integer getId() {
		return id;
	}
	public String getTypeCaptor() {
		return typeCapteur;
	}
	public String getMaxCaptor() {
		return maxCapteur.getText().toString();
	}
	public String getMinDate() {
		return minDate.getText().toString();
	}
	public String getMaxDate() {
		return maxDate.getText().toString();
	}



}
