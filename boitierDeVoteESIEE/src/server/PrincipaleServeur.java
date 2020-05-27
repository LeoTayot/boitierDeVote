package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PrincipaleServeur {
	public static int port = 4502;
	public static int maxUsers = 4;

	public static Thread[] mesThreads = new Thread[maxUsers];
	public static Socket[] lesChaussettes = new Socket[maxUsers];
	public static Map<Socket, String> userList = new HashMap<>();

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
				System.out.println("Available : " + available);
				IOCommandesServeur t = null;
				if (available != -1) {
					t = new IOCommandesServeur(socketClient, true);
					mesThreads[available] = (t);
					lesChaussettes[available] = (socketClient);
					System.out.println("IOCommandServer" + t);
				} else {
					t = new IOCommandesServeur(socketClient, false);
				}

				String username = t.lireReseau();

				userList.put(socketClient, username);
				t.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
