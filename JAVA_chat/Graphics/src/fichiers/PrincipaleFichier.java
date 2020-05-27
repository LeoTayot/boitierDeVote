package fichiers;

import java.io.File;

import client.IOCommandes;

public class PrincipaleFichier {
	public static void main(String args[]) {
		CommandesFichier commandes = new CommandesFichier();
		IOCommandes ioc = new IOCommandes();
		String commande = "";
		String repertoireCourant = "fichiers";
		
		do {
			ioc.ecrireEcran("Répertoire courant : /" + repertoireCourant);
			ioc.ecrireEcran("Tapez votre commande : ");
			commande = ioc.lireEcran();
			if(commande.equals("?")) {
				ioc.ecrireEcran("cd <rep> : se déplacer");
				ioc.ecrireEcran("ls <rep> : afficher le contenu d'un répertoire");
				ioc.ecrireEcran("cat <fichier> : lire un fichier");
				ioc.ecrireEcran("vi <fichier> : ajouter du contenu à la fin du fichier");
			} else if(commande.startsWith("cd")){
				String fileName = commande.substring(3);
				File dossierOuAller = new File(repertoireCourant + "/" + fileName);
				if(dossierOuAller.exists() && dossierOuAller.isDirectory()) {
					repertoireCourant = repertoireCourant + "/" + fileName;
				}
				else {
					ioc.ecrireEcran("Commande invalide");
				}
				
			} else if(commande.startsWith("ls")) {
				if(commande.length()>3) {
					String fileName = repertoireCourant + "/" + commande.substring(3);
					File dossier = new File(fileName);
					if(dossier.exists() && dossier.isDirectory()) {
						commandes.afficheContenu(fileName);
					}
					else {
						ioc.ecrireEcran("Commande invalide");
					}
				}
				else {
					commandes.afficheContenu(repertoireCourant);
				}

				
			} else if(commande.startsWith("cat")) {
				String fileName = repertoireCourant + "/" + commande.substring(4);
				File fic = new File(fileName);
				if(fic.isFile()) {
					commandes.afficheContenuFichier(fileName);
				}
				else {
					ioc.ecrireEcran("Commande invalide");
				}
			} else if(commande.startsWith("vi")) {
				String fileName = repertoireCourant + "/" + commande.substring(3);
				File fic = new File(fileName);
				if(fic.isFile()) {
					commandes.afficheContenuFichier(fileName);
					commandes.ecrireDansFichier(fileName);
				}
				else {
					ioc.ecrireEcran("Commande invalide");
				}
				
			} else if (commande.equals("exit")){
				break;
			} else {
				ioc.ecrireEcran("Commande inconnue...");
			}
			
		}while(!commande.equals("exit"));
	}
}
