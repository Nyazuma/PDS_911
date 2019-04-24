package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgUpdateEmplacement extends Message{

	protected String iD_Capteur;
	protected String iD_Emplacement;
	
	@JsonCreator
	public MsgUpdateEmplacement(@JsonProperty("iD_Capteur") String iD_Capteur, @JsonProperty("iD_Emplacement") String iD_Emplacement) {
		super(MessageType.UPDATEEMPLACEMENT);
		this.iD_Capteur=iD_Capteur;
		this.iD_Emplacement=iD_Emplacement;
	}
	
	public String getID_Capteur() {
		return iD_Capteur;
	}

	public String getID_Emplacement() {
		return iD_Emplacement;
	}
}
