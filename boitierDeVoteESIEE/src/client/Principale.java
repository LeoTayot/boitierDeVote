package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import server.PrincipaleServeur;

public class Principale {

	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		Socket chaussette = null;
		IOCommandesTeacher teacher = null;
		IOCommandesStudent student = null;
		try {
			chaussette = new Socket("127.0.0.1", 4502);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IOCommandes monIOCommandes = null;
		
		int available = PrincipaleServeur.checkPlaceLibre(PrincipaleServeur.maxUsers, PrincipaleServeur.mesThreads);
//		System.out.println("Place available :" + available);
		if (available == -1) {
			monIOCommandes = new IOCommandes();
			monIOCommandes.ecrireEcran("Il n'y a plus de place sur le serveur, veuillez rÃ©essayer ultÃ©rieurement.");
		} else {
			monIOCommandes = new IOCommandes(chaussette);
		}
		
		String repServ = "";
		
		// Set pseudo
		String username = "";
		username = monIOCommandes.lireEcran();
		monIOCommandes.ecrireReseau(username);
		repServ = monIOCommandes.lireReseau();
		System.out.println("Rep Serv :" + repServ);
		
		// Set userType
		String userType = "";
		userType = monIOCommandes.lireEcran();
		monIOCommandes.ecrireReseau(userType);
		System.out.println("User Type :" + userType);
		repServ = monIOCommandes.lireReseau();
		switch(repServ) {
			case "STUDENT" :
				userType = "STUDENT";
				System.out.println("STUDENT CONNECTED");
				student = new IOCommandesStudent(chaussette);
				student.start();
				break;
			case "TEACHER" :
				userType = "TEACHER";
				System.out.println("TEACHER CONNECTED");
				teacher = new IOCommandesTeacher(chaussette);
				teacher.start();
				break;
			default :
				// Error
				break;
		}
		
		String texteEntre = "";
		while(!texteEntre.equals("quit")) {
			texteEntre = monIOCommandes.lireEcran();
			
			if(userType.equals("TEACHER")) {
				if(texteEntre.equals("POC_JSON")) {
					// TESTER LE JSON
					Questionnaire question = new Questionnaire(username);
					question.setQuestionType("UNIQUE");
					question.setLabel("Combien font léès çhiffres 1 + 3 ?");
					question.addAnswer("4");
					question.addAnswer("5");
					question.addAnswer("6");
					question.removeAnswer("5");
					monIOCommandes.ecrireReseau((String) question.getQuestions());
				}
				else {
					monIOCommandes.ecrireReseau(texteEntre);
					System.out.println("Texte entré :" + texteEntre);
				}
			}
			else {
				if(texteEntre.equals("quit")) {
					monIOCommandes.ecrireReseau(texteEntre);
				}
				else {
					System.out.println("Réponse entrée :" + texteEntre);
					student.sendAnswer(texteEntre);					
				}
			}
		}

		try {
			if(userType.equals("TEACHER")) {
				teacher.interrupt();
			}
			else {
				student.interrupt();
			}
			monIOCommandes.getMaChaussette().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
