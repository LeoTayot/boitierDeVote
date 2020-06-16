package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IOCommandesTeacher extends Thread {

	private BufferedReader lectureEcran;
	private BufferedReader lectureReseau;
	private PrintStream ecritureEcran;
	private PrintStream ecritureReseau;
	private Socket maChaussette;

	private boolean placeLibre;

	public IOCommandesTeacher(Socket uneChaussette, boolean placeLibre) {
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

	
	public Socket getMaChaussette() {
		return maChaussette;
	}

	public void setPlaceLibre(boolean placeLibre) {
		this.placeLibre = placeLibre;
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
	
	public boolean ecrireToStudent(String userName, String message) {
		boolean retcode = false;
		JSONParser parser = new JSONParser();
		Object jsonObj = null;
		try {
			jsonObj = parser.parse(message);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) jsonObj;
		String questionId = (String) jsonObject.get("questionId");
		jsonObject.put("type", "question");

		String currentRole = "";
		for(Object user : PrincipaleServeur.users) {
			currentRole = (String) ((JSONObject) user).get("role");
			if(currentRole.equals("T")) {
				continue;
			}
			((JSONObject) user).put("ans_" + questionId, null);
		}
		System.out.println("After question : ");
		System.out.println(PrincipaleServeur.users.toJSONString());
		
		PrincipaleServeur.infos.put("lastQuestion", questionId);
		PrincipaleServeur.infos.put("possibleAnswer", jsonObject.get("possibleAnswer"));
		PrincipaleServeur.infos.put("questionType", jsonObject.get("questionType"));
		PrincipaleServeur.infos.put("labelQuestion", jsonObject.get("label"));
		
		for (int i = 0; i < PrincipaleServeur.maxUsers; i++) {
			PrintStream tmp = null;
			if(PrincipaleServeur.mesThreads[i] == null || PrincipaleServeur.lesChaussettes[i] == null) {
				continue;
			}
			
			String tmpUserClass = PrincipaleServeur.mesThreads[i].getClass().getName();
			try {
				if (PrincipaleServeur.lesChaussettes[i] != null
					&& tmpUserClass != null
					&& tmpUserClass.equals("server.IOCommandesStudent")) {
					retcode = true;
					tmp = new PrintStream(PrincipaleServeur.lesChaussettes[i].getOutputStream());
					tmp.println(jsonObject.toString());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retcode;
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
								tmp.println(">>>"
										+ PrincipaleServeur.userList.get(PrincipaleServeur.lesChaussettes[j]));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void run() {
		try {
			String message = "";
			ecrireEcran("Connexion du Professeur : " + maChaussette.getInetAddress());
			//ecrireReseauBroadcastUsers();
			if (placeLibre) {
				while (!message.equals("quit")) {
					message = lireReseau();
					if(message.equals("quit")) {
						
						String currentUsername = PrincipaleServeur.userList.get(maChaussette);

						String currentRole = "";
						String currentName = "";
						JSONObject userToDelete = null;
						for( Object user : PrincipaleServeur.users) {
							currentRole = (String) ((JSONObject) user).get("role");
							currentName = (String) ((JSONObject) user).get("username");
							if(currentRole.equals("T") && currentName.equals(currentUsername)) {
								userToDelete = (JSONObject) user;
							}
						}

						PrincipaleServeur.users.remove(userToDelete);
						ecrireReseauUnicast("EXIT");
						PrincipaleServeur.userList.remove(maChaussette);
						
						continue;
					}
					ecrireEcran(PrincipaleServeur.userList.get(maChaussette) + ">" + message);
					if (message.startsWith("@")) {
						/*ecrireReseauMessagePrive(message,
								PrincipaleServeur.userList.get(maChaussette)
								+ " > <b>(Private)</b> "
								+ message);
						 */
					}
					else {
						ecrireToStudent(PrincipaleServeur.userList.get(maChaussette), message);
						/*if(true == ecrireToStudent(PrincipaleServeur.userList.get(maChaussette), message)){
							ecrireReseauUnicast("OK");
						}
						else {
							ecrireReseauUnicast("NOK");
						}*/
						//ecrireReseauBroadcast(PrincipaleServeur.userList.get(maChaussette) + " >" + message);
					}
				}
			} else {
				ecrireReseauUnicast("Plus de place sur le serveur");
			}
			PrincipaleServeur.userList.remove(maChaussette);
			//ecrireReseauBroadcastUsers();
			maChaussette.close();
			ecrireEcran("Déconnexion de : " + maChaussette.getInetAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
