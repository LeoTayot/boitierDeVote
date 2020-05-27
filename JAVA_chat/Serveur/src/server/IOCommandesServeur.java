package server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class IOCommandesServeur extends Thread {

	private BufferedReader lectureEcran;
	private BufferedReader lectureReseau;
	private PrintStream ecritureEcran;
	private PrintStream ecritureReseau;
	private Socket maChaussette;

	private boolean placeLibre;

	public Socket getMaChaussette() {
		return maChaussette;
	}

	public void setPlaceLibre(boolean placeLibre) {
		this.placeLibre = placeLibre;
	}

	@Override
	public void run() {
		discussion();
	}

	public IOCommandesServeur(Socket uneChaussette, boolean placeLibre) {

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

	public void ecrireReseauUnicast(String texte) {
		texte = insererSmileys(texte);
		if (maChaussette != null)
			ecritureReseau.println(texte);
	}

	private String insererSmileys(String texte) {
		texte = texte.replace(":)", "<img src='https://wiki.jvflux.com/images/b/b5/1.gif'>");
		texte = texte.replace(":(", "<img src='https://wiki.jvflux.com/images/5/5d/-%28.gif'>");
		texte = texte.replace(";)", "<img src='https://fr.scratch-wiki.info/w/images/6/67/Smiley-Wink.png'>");
		texte = texte.replace(":p", "<img src='https://wiki.jvflux.com/images/9/9f/Langue.gif'>");
		texte = texte.replace("-_-", "<img src='https://wiki.jvflux.com/images/7/75/Hum.gif'>");
		texte = texte.replace(":D", "<img src='https://fr.scratch-wiki.info/w/images/c/c7/Smiley-Big-Smile.png'>");
		texte = texte.replace(":0", "<img src='https://fr.scratch-wiki.info/w/images/4/41/Smiley-Yikes.png'>");
		texte = texte.replace(":?", "<img src='https://wiki.jvflux.com/images/4/4f/Question.gif'>");
		texte = texte.replace("è.é", "<img src='https://wiki.jvflux.com/images/a/ae/67.gif'>");

		return texte;
	}

	public void ecrireReseauBroadcast(String texte) {

		texte = insererSmileys(texte);
		for (int i = 0; i < PrincipaleServeur.maxUsers; i++) {
			PrintStream tmp = null;
			try {
				if (PrincipaleServeur.lesChaussettes[i] != null) {
					tmp = new PrintStream(PrincipaleServeur.lesChaussettes[i].getOutputStream());
					tmp.println(texte);
				}

			} catch (IOException e) {
			}

		}
	}

	public String getDate() {
		Date date = new Date();
		return date.toString();
	}

	public String getLangue(String txtToTranslate) {
		URL url;
		String laLangue = "";
		try {
			txtToTranslate = URLEncoder.encode(txtToTranslate, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			url = new URL(
					"https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20200221T142948Z.bfaa5e7dacec8c60.946b6d49ab913e13057a38db0727c9a6b297970c&text="
							+ txtToTranslate);
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					laLangue = line.substring(20, 22);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return laLangue;
	}

	public String getTrad(String txtToTranslate) {
		String trad = "";

		if (txtToTranslate.charAt(10) == ' ' && txtToTranslate.charAt(13) == ' ') {
			String langToTranslateTo = txtToTranslate.substring(11, 13);			
			String laLangue = getLangue(txtToTranslate.substring(14));
			String tempArray[];

			try {
				txtToTranslate = URLEncoder.encode(txtToTranslate.substring(14), StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			URL url = null;
			try {
				url = new URL(
						"https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20200221T142948Z.bfaa5e7dacec8c60.946b6d49ab913e13057a38db0727c9a6b297970c&text="
								+ txtToTranslate + "&lang=" + laLangue + "-" + langToTranslateTo);
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
					for (String line; (line = reader.readLine()) != null;) {
						tempArray = line.split("\"");
						trad = tempArray[9];
				            
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			trad = "Commande incorrecte";
		}
		return trad;

	}
	

	public void getService(String texte) {
		String serviceTexte = texte.substring(1);
		serviceTexte = serviceTexte.trim();
		String info = null;
		if (serviceTexte.toUpperCase().equals("HELP")) {
			PrintStream tmp = null;
			try {
				tmp = new PrintStream(maChaussette.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			tmp.println("Les services disponibles sont les suivants : ");
			tmp.println("@[pseudo] : Contacte en message prive a partir d'un pseudo");
			tmp.println("!date : Affiche la date actuelle");
			tmp.println("!translate [fr|en|ja|es|de|it|ru|...] [message] : Traduit le message dans la langue souhaite");
		} else if (serviceTexte.toUpperCase().equals("DATE")) {
			info = getDate();
			PrintStream tmp = null;
			try {
				tmp = new PrintStream(maChaussette.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			tmp.println("Nous sommes le : " + info);
		} else if (serviceTexte.toUpperCase().equals("TRANSLATE")) {
			info = getDate();
			PrintStream tmp = null;
			try {
				tmp = new PrintStream(maChaussette.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			tmp.println("Nous sommes le : " + info);
		}
	}

	public void ecrireReseauMessagePrive(String messageSansBalise, String texte) {
		texte = insererSmileys(texte);
		ArrayList<String> pseudos = new ArrayList<String>();
		ArrayList<Boolean> pseudosFound = new ArrayList<Boolean>();

		while (messageSansBalise.startsWith("@")) {
			String lePseudo = messageSansBalise.substring(1, messageSansBalise.indexOf(" ")).trim();
			pseudos.add(lePseudo);
			pseudosFound.add(false);
			messageSansBalise = messageSansBalise.substring(messageSansBalise.indexOf(" ") + 1);
		}

		PrintStream tmp = null;
		try {
			tmp = new PrintStream(maChaussette.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tmp.println(texte);

		for (int i = 0; i < PrincipaleServeur.maxUsers; i++) {
			if (PrincipaleServeur.lesChaussettes[i] != null) {
				Socket laChaussetteLocale = PrincipaleServeur.lesChaussettes[i];
				for (int j = 0; j < pseudos.size(); j++) {
					if (PrincipaleServeur.userList.get(laChaussetteLocale).equals(pseudos.get(j))) {
						PrintStream tmp2 = null;
						pseudosFound.set(j, true);
						try {
							tmp2 = new PrintStream(laChaussetteLocale.getOutputStream());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tmp2.println(texte);
					}
				}
			}
		}

		for (int k = 0; k < pseudosFound.size(); k++) {
			if (!pseudosFound.get(k)) {
				try {
					tmp = new PrintStream(maChaussette.getOutputStream());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tmp.println("<b>Il n'existe aucun utilisateur du nom de " + pseudos.get(k) + "...</b>");
			}

		}

	}

	public void ecrireReseauBroadcastUsers() {

		for (int i = 0; i < PrincipaleServeur.maxUsers; i++) {
			PrintStream tmp = null;
			try {
				if (PrincipaleServeur.lesChaussettes[i] != null) {
					tmp = new PrintStream(PrincipaleServeur.lesChaussettes[i].getOutputStream());
					for (int j = 0; j < PrincipaleServeur.maxUsers; j++)
						if (PrincipaleServeur.lesChaussettes[j] != null)
							if (PrincipaleServeur.userList.get(PrincipaleServeur.lesChaussettes[j]) != null)
								tmp.println(">>>" + "<font color ="
										+ PrincipaleServeur.userColor.get(PrincipaleServeur.lesChaussettes[j]) + "> "
										+ PrincipaleServeur.userList.get(PrincipaleServeur.lesChaussettes[j])
										+ "</font>");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void ecrireReseauServices(String messageSansBalise) {

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

	public void discussion() {

		try {
			String message = "";
			ecrireEcran("Connexion de : " + maChaussette.getInetAddress());
			ecrireLog("fichiers/logs.txt", "[" + new Date() + "] Connexion de : " + maChaussette.getInetAddress());
			ecrireReseauBroadcastUsers();
			if (placeLibre) {
				while (!message.equals("quit")) {
					message = lireReseau();
					String messageLog = "[" + new Date() + "] " + maChaussette.getInetAddress() + ">" + message;
					ecrireLog("fichiers/logs.txt", messageLog);
					ecrireEcran(PrincipaleServeur.userList.get(maChaussette) + ">" + message);
					if (message.startsWith("@")) {
						ecrireReseauMessagePrive(message,
								"<font color =" + PrincipaleServeur.userColor.get(maChaussette) + " >"
										+ PrincipaleServeur.userList.get(maChaussette) + "</font> > <b>(Private)</b> "
										+ message);

					} else if (message.startsWith("!") && !message.startsWith("!translate")) {
						getService(message);
					} else if (message.startsWith("!translate")) {
						message = getTrad(message);
						ecrireReseauBroadcast("<font color =" + PrincipaleServeur.userColor.get(maChaussette) + " >"
								+ PrincipaleServeur.userList.get(maChaussette) + "</font> >" + message);
					} else {
						ecrireReseauBroadcast("<font color =" + PrincipaleServeur.userColor.get(maChaussette) + " >"
								+ PrincipaleServeur.userList.get(maChaussette) + "</font> >" + message);
					}

				}
			} else {
				ecrireReseauUnicast("Plus de place sur le serveur");
				ecrireLog("fichiers/logs.txt", "Plus de place sur le serveur pour " + maChaussette.getInetAddress());
			}
			PrincipaleServeur.userList.remove(maChaussette);
			PrincipaleServeur.userColor.remove(maChaussette);
			ecrireReseauBroadcastUsers();
			maChaussette.close();
			ecrireEcran("Déconnexion de : " + maChaussette.getInetAddress());
			ecrireLog("fichiers/logs.txt", "[" + new Date() + "] Déconnexion de : " + maChaussette.getInetAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ecrireLog(String path, String message) {

		try {
			PrintWriter fichier = new PrintWriter(new FileWriter(path, true));
			fichier.println(message);
			fichier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
