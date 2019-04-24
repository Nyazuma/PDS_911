package shs.common;

public enum MessageType {

	// Answer from the server
	BOOLEANRESULT, STRINGRESULT, INTRESULT, LINERESULT, LISTRESULT, 
	// Request from the GUI with a specific class
	CONNECTION, ADDOBJECT, DELETEOBJECT, UPDATEOBJECT, 
	REPORTRFID, REPORTCALL, REPORTSMOKE, REPORTMOTION, REPORTTEMPERATURE, REPORTHYGRO, REPORTOPENING,
	CHANGEALERT, UPDATEEMPLACEMENT,
	// Request from the GUI without a specific class
	NUMBEROBJECT, PING, 
	LISTRESIDENCES, LISTREFERENTIELS, LISTOBJECTS, LISTEMPLACEMENTS, LISTETAGES,
	MONITORING,  LISTCAPTEURS, 
}