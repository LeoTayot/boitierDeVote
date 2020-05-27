package client;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Principale {

	public static void main(String args[]) {
		
		IOCommandes monIOCommandes = new IOCommandes();
		String texteEntre = "";
		
		while(!texteEntre.equals("quit")) {
			texteEntre = monIOCommandes.lireEcran();
			monIOCommandes.ecrireReseau(texteEntre);
			String repServ = monIOCommandes.lireReseau();
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
