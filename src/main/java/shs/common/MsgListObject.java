package shs.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgListObject extends Message {

	protected List<List<String>> listObjet; 
	
	@JsonCreator
	public MsgListObject(@JsonProperty("listObject") List<List<String>> list) {
		super(MessageType.LISTOBJECT);
		this.listObjet = list; 
		
	}
	
	public List<List<String>> getListObject() {
		return listObjet;
	}

}
