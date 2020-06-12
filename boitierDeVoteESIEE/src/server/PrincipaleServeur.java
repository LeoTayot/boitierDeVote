package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PrincipaleServeur {
	public static int port = 4502;
	public static int maxUsers = 4;

	public static Thread[] mesThreads = new Thread[maxUsers];
	public static Socket[] lesChaussettes = new Socket[maxUsers];
	public static Map<Socket, String> userList = new HashMap<>();
	public static JSONArray users = new JSONArray();

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

	@SuppressWarnings("unchecked")
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
				JSONObject user = new JSONObject();
				user.put("socketId", socketClient);
				user.put("username", username);
				user.put("role", role);
				users.add(user);
				
				if(role.equals("T")) {
					System.out.println("TEACHER !");
					IOCommandesTeacher teacher = new IOCommandesTeacher(socketClient, isPlaceFree);
					mesThreads[available] = (teacher);
					t.ecrireReseauUnicast("TEACHER");
					teacher.start();
				}
				else {
					System.out.println("STUDENT !");
					IOCommandesStudent student = new IOCommandesStudent(socketClient, isPlaceFree);
					mesThreads[available] = (student);
					t.ecrireReseauUnicast("STUDENT");
					student.start();
				}
				userList.put(socketClient, username);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
