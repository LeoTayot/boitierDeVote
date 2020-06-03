package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import server.PrincipaleServeur;

public class Principale {

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
		System.out.println("Place available :" + available);
		if (available == -1) {
			monIOCommandes = new IOCommandes();
			monIOCommandes.ecrireEcran("Il n'y a plus de place sur le serveur, veuillez réessayer ultérieurement.");
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
			monIOCommandes.ecrireReseau(texteEntre);
			System.out.println("Texte entr� :" + texteEntre);
			//repServ = monIOCommandes.lireReseau();
			//System.out.println("Rep Serv :" + repServ);
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
