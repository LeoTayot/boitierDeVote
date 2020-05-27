package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import server.PrincipaleServeur;

public class Principale {

	public static void main(String args[]) {
		Socket chaussette = null;
		try {
			chaussette = new Socket("127.0.0.1", 4513);
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
		
		
		String texteEntre = "";
		
		while(!texteEntre.equals("quit")) {
			texteEntre = monIOCommandes.lireEcran();
			monIOCommandes.ecrireReseau(texteEntre);
			System.out.println("texte entr� :" + texteEntre);
			String repServ = monIOCommandes.lireReseau();
			System.out.println("Rep Serv :" + repServ);
			monIOCommandes.ecrireEcran(repServ);
		}

		try {
			monIOCommandes.getMaChaussette().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
