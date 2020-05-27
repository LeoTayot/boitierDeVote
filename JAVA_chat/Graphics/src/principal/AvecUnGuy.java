package principal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import graphics.LoginForm;
import graphics.LoginHandler;
import graphics.RegisterForm;
import graphics.WindowChat;
import outils.PopUpManager;
import server.IOCommandesServeur;
import server.PrincipaleServeur;
import server.TextPaneOutputStream;
import client.IOCommandes;

public class AvecUnGuy {

	public static void main(String args[]) {

		Socket chaussette = null;
		try {
			chaussette = new Socket("127.0.0.1", 4502);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int available = PrincipaleServeur.checkPlaceLibre(PrincipaleServeur.maxUsers, PrincipaleServeur.mesThreads);
		if (available == -1) {
			JFrame f = new JFrame();
			JOptionPane.showMessageDialog(f,
					"Il n'y a plus de place sur le serveur, veuillez réessayer ultérieurement.");
		} else {

			IOCommandes monIOCommandes = new IOCommandes(chaussette);
			PopUpManager popUpManager = new PopUpManager(monIOCommandes);
			
			// if (popUpManager.showInputNom()) {
			LoginHandler loginHandler = new LoginHandler();
			
			while (!loginHandler.getLeLogin().isConnexionReussie() && !loginHandler.getLeLogin().isAbort()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}

			}
			loginHandler.getLeLogin().validate();
			loginHandler.getLeLogin().setVisible(false);

			if (!loginHandler.getLeLogin().isAbort()) {
				monIOCommandes.ecrireReseau(loginHandler.getLeLogin().getUname());
				WindowChat frameChat = new WindowChat(monIOCommandes);
				TextPaneOutputStream nouveauOutput = new TextPaneOutputStream(frameChat.getChatHistory());
				TextPaneOutputStream nouveauOutputUsers = new TextPaneOutputStream(frameChat.getUsernames());

				monIOCommandes.setEcritureEcran(new PrintStream(nouveauOutput));
				monIOCommandes.setEcritureUser(new PrintStream(nouveauOutputUsers));
				monIOCommandes.setLaFenetre(frameChat);

				frameChat.setVisible(true);

				monIOCommandes.start();
			} else {
				System.exit(0);
			}

		}

	}
}
