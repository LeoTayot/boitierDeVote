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
				boolean isPlaceFree = false;
				IOCommandes t = null;
				if (available != -1) {
					isPlaceFree = true;
					t = new IOCommandes(socketClient, isPlaceFree);
					lesChaussettes[available] = (socketClient);
				} else {
					isPlaceFree = false;
					t = new IOCommandes(socketClient, isPlaceFree);
				}

				String username = t.lireReseau();
				t.ecrireReseauUnicast("Username OK");
				String role = t.lireReseau();
				
				if(role.equals("T")) {
					System.out.println("TEACHER !");
					IOCommandesTeacher teacher = new IOCommandesTeacher(socketClient, isPlaceFree);
					mesThreads[available] = (teacher);
					teacher.start();
				}
				else {
					System.out.println("STUDENT !");
					IOCommandesStudent student = new IOCommandesStudent(socketClient, isPlaceFree);
					mesThreads[available] = (student);
					student.start();
				}
				userList.put(socketClient, username);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
