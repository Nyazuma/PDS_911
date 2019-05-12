package shs.common;

public enum MessageType {

	// Answer from the server
	BOOLEANRESULT, STRINGRESULT, INTRESULT, LINERESULT, LISTRESULT, 
	// Request from the GUI with a specific class
	CONNECTION, ADDOBJECT, DELETEOBJECT, UPDATEOBJECT, UPDATEOBJECTNONCONFIG,
	REPORTRFID, REPORTCALL, REPORTSMOKE, REPORTMOTION, REPORTTEMPERATURE, REPORTHYGRO, REPORTOPENING,
	CHANGEALERT, UPDATEEMPLACEMENT, DELETEEMPLACEMENT,
	// Request from the GUI without a specific class
	NUMBEROBJECT, PING, NUMBEROBJECTADDED, NUMBEROBJECTDELETED, NUMBEROBJECTUPDATED,NUMBEROBJECTNONCONFIGURED, NUMBEROBJECTALERT, NUMBEROBJECTFETCH,
 	LISTRESIDENCES, LISTREFERENTIELS, LISTOBJECTS, LISTEMPLACEMENTS, LISTETAGES, NUMBEROBJECTON, NUMBEROBJECTOFF,
	MONITORING, LISTCAPTEURS, LISTNONCONFIGURED, LISTSENSORSDETAILS
}
 