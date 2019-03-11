package shs.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgConnection extends Message {

	
	private String username;
	private String password;
	
	private Boolean status;

	@JsonCreator
	public MsgConnection(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		super(MessageType.CONNECTION);
		this.username = username;
		this.password = password;
		this.status = Boolean.FALSE;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public MessageType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Connection [username=" + username + ", password=" + password + ", status=" + status + "]";
	}
	
	
	
	
}
