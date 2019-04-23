package shs.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shs.common.Message;
import shs.common.MessageType;
import shs.common.MsgListResult;
import shs.common.Tool;

public class Simulation {

	protected Scanner sc;
	
	protected List<List<String>> listReferentiels;
	protected List<List<String>> sortedCaptors;

	public Simulation() {
		System.out.println("911 SHS Simulation > launched!\n");
		init();
		menu();
	}

	private void init() {
		sc = new Scanner(System.in);
		
		System.out.println("################### Initialisation ##################");
		//We get the referentiel of objects
		Message requestReferentielList = new Message(MessageType.LISTREFERENTIELS);
		String answerReferentiel = Connector.contactServer(Tool.messageToJSON(requestReferentielList));
		listReferentiels = ((MsgListResult) Tool.jsonToMessage(answerReferentiel)).getListResult();
		System.out.println("The referential type list was loaded from the server!");

		//We get a list of current sensors
		Message requestCaptorsList = new Message(MessageType.LISTOBJECTS);
		String answerCaptors = Connector.contactServer(Tool.messageToJSON(requestCaptorsList));
		List<List<String>> listObjects = ((MsgListResult) Tool.jsonToMessage(answerCaptors)).getListResult();
		System.out.println("The sensors list was loaded from the server!");

		//We aggregate objects by their referentiel types
		sortedCaptors = new ArrayList<List<String>>();
		//Init
		for(int i=0; i<listReferentiels.size();i++)
			sortedCaptors.add(new ArrayList<String>());
		int cursor;
		for(List<String> captor : listObjects) {
			cursor = 0;
			for(List<String> reference : listReferentiels) {
				if(captor.get(1).trim().equalsIgnoreCase(reference.get(0))) {
					//We add its id
					sortedCaptors.get(cursor).add(captor.get(0));
					continue;
				}
				cursor++;
			}
		}
		System.out.println("The sensors list was sorted!");
		System.out.println("#####################################################\n");
	}

	private void menu() {
		System.out.println("##################### Main Menu #####################");
		System.out.println("Please choose which simulation you want to run : ");
		System.out.println("1 : Specify the sensors to operate (default choice)");
		System.out.println("2 : Simulate random alerts");
		String answer;
		System.out.print("Your choice : ");
		do {
			answer = sc.nextLine();
			if(answer.equals(""))
				answer="1";
			if(!(answer.equals("1") || answer.equals("2")))
				System.out.print("Invalid! Try again : ");
		}while(!(answer.equals("1") || answer.equals("2")));
		System.out.println("#####################################################\n");
		if(answer.equals("1"))
			while(true)
				specificAlerts();
		else
			randomAlerts();
	}

	private void specificAlerts() {
		System.out.println("############# Launch specific alerts (1) ############");
		System.out.println("Which type of sensor do you want to operate?");
		int i = 1;
		for(List<String> sensorType : listReferentiels) {
			if(i==1)
				System.out.println(i++ + " : " + sensorType.get(0) + " (default choice)");
			else
				System.out.println(i++ + " : " + sensorType.get(0));
		}
		String rawAnswer;
		int answer=0;
		System.out.print("Your choice : ");
		do {
			try {
				rawAnswer = sc.nextLine();
				if(rawAnswer.equals(""))
					answer=1;
				else
					answer=Integer.parseInt(rawAnswer);
			}catch(NumberFormatException e) {
				answer=0;
			}
			if(!(0<answer && answer<=sortedCaptors.size()))
				System.out.print("Invalid! Try again : ");
		}while(!(0<answer && answer<=sortedCaptors.size()));
		System.out.println("#####################################################\n");
		launchAlert(sortedCaptors.get(answer-1));
	}

	private void launchAlert(List<String> list) {
		//TODO As usual, config file
		final Integer maxNumberSensorsDisplayed = 10;
		System.out.println("############# Launch specific alerts (2) ############");
		System.out.println("With which sensor do you want to launch an alert?");
		System.out.println("(only the first " + maxNumberSensorsDisplayed + " sensor are displayed");
		//TODO boucle
		System.out.println("#####################################################\n");
	}

	private void randomAlerts() {
		System.out.println("################ Launch random alerts ###############");
		CategoryRFID categoryRFID = new CategoryRFID(listReferentiels.get(0).get(0), sortedCaptors.get(0));
		Thread threadRFID = new Thread(categoryRFID);
		threadRFID.start();

		CategoryCall categoryCall = new CategoryCall(listReferentiels.get(1).get(0), sortedCaptors.get(1));
		Thread threadCall = new Thread(categoryCall);
		threadCall.start();

		CategorySmoke categorySmoke = new CategorySmoke(listReferentiels.get(2).get(0), sortedCaptors.get(2));
		Thread threadSmoke = new Thread(categorySmoke);
		threadSmoke.start();

		CategoryMotion categoryMotion = new CategoryMotion(listReferentiels.get(3).get(0), sortedCaptors.get(3));
		Thread threadMotion = new Thread(categoryMotion);
		threadMotion.start();

		CategoryTemperature categoryTemperature = new CategoryTemperature(listReferentiels.get(4).get(0), sortedCaptors.get(4));
		Thread threadTemperature = new Thread(categoryTemperature);
		threadTemperature.start();

		CategoryHygro categoryHygro = new CategoryHygro(listReferentiels.get(5).get(0), sortedCaptors.get(5));
		Thread threadHygro = new Thread(categoryHygro);
		threadHygro.start();

		CategoryOpening categoryOpening = new CategoryOpening(listReferentiels.get(6).get(0), sortedCaptors.get(6));
		Thread threadOpening = new Thread(categoryOpening);
		threadOpening.start();
	}

}
