package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import enums.CouleursPseudo;
import utilisateur.ChatUser;

public class PrincipaleServeur {

	public static int port = 4502;
	public static int maxUsers = 4;

	public static Thread[] mesThreads = new Thread[maxUsers];
	public static Socket[] lesChaussettes = new Socket[maxUsers];
	public static Map<Socket, String> userList = new HashMap<>();
	public static Map<Socket, String> userColor = new HashMap<>();

	public static int checkPlaceLibre(int max, Thread[] tabThreads) {
		for (int i = 0; i < max - 1; i++) {
			if (tabThreads[i] == null) {
				return i;
			} else if (!tabThreads[i].isAlive()) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		
		try {
			ServerSocket socketServeur = new ServerSocket(port);
			System.out.println("Lancement du serveur");

			while (true) {
				Socket socketClient = socketServeur.accept();
				int available = checkPlaceLibre(maxUsers, mesThreads);
				IOCommandesServeur t = null;
				if (available != -1) {
					t = new IOCommandesServeur(socketClient, true);
					mesThreads[available] = (t);
					lesChaussettes[available] = (socketClient);
				} else {
					t = new IOCommandesServeur(socketClient, false);
				}

				String username = t.lireReseau();

				userList.put(socketClient, username);
				String laCouleur = CouleursPseudo.randomCouleursPseudo().toString();
				if (userColor.containsValue(laCouleur)) {
					do {
						laCouleur = CouleursPseudo.randomCouleursPseudo().toString();
					} while (userColor.containsValue(laCouleur));
				}

				userColor.put(socketClient, laCouleur);
				t.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
