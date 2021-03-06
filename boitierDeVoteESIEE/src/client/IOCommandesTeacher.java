package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class IOCommandesTeacher extends Thread {

	private BufferedReader lectureEcran;
	private BufferedReader lectureReseau;
	private PrintStream ecritureEcran;
	private PrintStream ecritureUser;
	private PrintStream ecritureReseau;
	
	private Socket maChaussette;
	//private WindowChat laFenetre;

	public Socket getMaChaussette() {
		return maChaussette;
	}

	@Override
	public void run() {
		String message = "";
		ecrireEcran("Connexion au serveur etablie !");

		while(!Thread.interrupted()) {
			// TODO : Lire uniquement Teacher
			message = this.lireReseau();
			// ecrireEcran(message);
		}
	}

	public IOCommandesTeacher() {
		// Partie en local
		InputStreamReader monInputStream = new InputStreamReader(System.in);
		lectureEcran = new BufferedReader(monInputStream);
		ecritureEcran = new PrintStream(System.out);
		ecritureUser = new PrintStream(System.out);
		maChaussette = null;
	}

	public IOCommandesTeacher(Socket uneChaussette) {
		// Partie en local
		InputStreamReader monInputStream = new InputStreamReader(System.in);
		lectureEcran = new BufferedReader(monInputStream);
		ecritureEcran = new PrintStream(System.out);
		ecritureUser = new PrintStream(System.out);

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
	}

	public PrintStream getEcritureUser() {
		return ecritureUser;
	}

	public void setEcritureUser(PrintStream ecritureUser) {
		this.ecritureUser = ecritureUser;
	}

	public void ecrireEcran(String texte) {
		ecritureEcran.println(texte);
	}
	
	public void ecrireUser(String texte) {
		ecritureUser.println(texte);
	}

	public String lireEcran() {
		String texte = "";
		try {
			texte = lectureEcran.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return texte;
	}

	public void ecrireReseau(String texte) {
		if (maChaussette != null)
			ecritureReseau.println(texte);
	}

	public String lireReseau() {
		if (maChaussette != null && !maChaussette.isClosed()) {
			String texte = "";
			try {
				texte = lectureReseau.readLine();
			} catch (Exception e ) {
				e.printStackTrace();
			}

			return texte;
		}
		return null;
	}

	public void setEcritureEcran(PrintStream printStream) {
		this.ecritureEcran = printStream;

	}
}
