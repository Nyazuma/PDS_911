package shs.common;

public enum MessageType {

	// Answer from the server
	BOOLEANRESULT, STRINGRESULT, INTRESULT, LINERESULT, LISTRESULT, 
	// Request from the GUI with a specific class
	CONNECTION, ADDOBJECT, DELETEOBJECT, UPDATEOBJECT, 
	// Request from the GUI without a specific class
	NUMBEROBJECT, PING, 
	LISTZONES, LISTPIECES, LISTRESIDENCES, LISTREFERENTIELS, LISTOBJECTS,
	MONITORING_ON, MONITORING_OFF, 
}
