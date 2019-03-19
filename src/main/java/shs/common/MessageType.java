package shs.common;

public enum MessageType {

	// Answer from the server
	BOOLEANRESULT, STRINGRESULT, INTRESULT, LISTRESULT,
	// Request from the GUI with a specific class
	CONNECTION, ADDOBJECT,
	// Request from the GUI without a specific class
	NUMBEROBJECT, PING
}