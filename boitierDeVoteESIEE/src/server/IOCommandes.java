package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class IOCommandes extends Thread {

	private BufferedReader lectureEcran;
	private BufferedReader lectureReseau;
	private PrintStream ecritureEcran;
	private PrintStream ecritureReseau;
	private Socket maChaussette;

	private boolean placeLibre;

	public Socket getMaChaussette() {
		return maChaussette;
	}

	public IOCommandes(Socket uneChaussette, boolean placeLibre) {
		// Partie en local
		InputStreamReader monInputStream = new InputStreamReader(System.in);
		lectureEcran = new BufferedReader(monInputStream);
		ecritureEcran = new PrintStream(System.out);

		// Partie réseau
		maChaussette = uneChaussette;
		InputStreamReader monInputStreamReseau = null;

		try {
			monInputStreamReseau = new InputStreamReader(maChaussette.getInputStream());
			lectureReseau = new BufferedReader(monInputStreamReseau);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ecritureReseau = new PrintStream(maChaussette.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.placeLibre = placeLibre;
	}

	public void ecrireEcran(String texte) {
		ecritureEcran.println(texte);
	}

	public void ecrireReseauUnicast(String texte) {
		if (maChaussette != null)
			ecritureReseau.println(texte);
	}

	public void ecrireReseauBroadcast(String texte) {
		for (int i = 0; i < PrincipaleServeur.maxUsers; i++) {
			PrintStream tmp = null;
			try {
				if (PrincipaleServeur.lesChaussettes[i] != null) {
					tmp = new PrintStream(PrincipaleServeur.lesChaussettes[i].getOutputStream());
					tmp.println(texte);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public String lireReseau() {
		if (maChaussette != null) {
			String texte = "";
			try {
				texte = lectureReseau.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return texte;
		}
		return null;
	}
}
