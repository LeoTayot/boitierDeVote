package fichiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import client.IOCommandes;

public class CommandesFichier {

	private IOCommandes ioc;

	public CommandesFichier() {
		ioc = new IOCommandes();
	}

	public void afficheContenuFichier(String path) {
		try {
			String ligne;
			BufferedReader fichier = new BufferedReader(new FileReader(path));

			while ((ligne = fichier.readLine()) != null) {
				ioc.ecrireEcran(ligne);
			}

			fichier.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ecrireDansFichier(String path) {

		String txtEntre = "";
		do {
			txtEntre = ioc.lireEcran();
			if (!txtEntre.equals(":q")) {
				try {
					PrintWriter fichier = new PrintWriter(new FileWriter(path, true));
					fichier.println(txtEntre);
					fichier.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} while (!txtEntre.equals(":q"));

	}

	public void afficheContenu(String racine) {
		File file = new File(racine);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory())
				ioc.ecrireEcran("rep. " + files[i].getName());
			else
				ioc.ecrireEcran("file. " + files[i].getName());
		}
	}
	
	public File[] getContenu(String racine) {
		File file = new File(racine);
		File[] files = file.listFiles();
		return files;
	}
	
}
