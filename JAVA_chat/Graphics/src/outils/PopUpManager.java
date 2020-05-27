package outils;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import client.IOCommandes;
import server.PrincipaleServeur;

public class PopUpManager {
	private String nom;
	private IOCommandes monIOCommandes;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	private int result;

	// Constructeur
	public PopUpManager(IOCommandes IO) {
		nom = "";
		monIOCommandes = IO;
	}

	// Affichage fenêtre pour saisir le pseudo
	public boolean showInputNom() {
		// Champ pour le pseudo : on ne peut mettre que des lettres
		JTextField nomField = new JTextField(15);
		nomField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!Character.isLetter(ch)) {
					e.consume();
				}

			}
		});
		nomField.requestFocus();

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(2, 2));
		myPanel.add(new JLabel("Username :"));
		myPanel.add(nomField);
		String state = "";

		do {

			result = JOptionPane.showConfirmDialog(null, myPanel, "Bienvenue !", JOptionPane.OK_CANCEL_OPTION);
			nomField.requestFocus();
			if (result == JOptionPane.OK_OPTION) {

				nom = nomField.getText();
				monIOCommandes.ecrireReseau(nom);
				state = monIOCommandes.lireReseau();
				if (!state.equals("OK")) {
					JFrame f = new JFrame();
					if(nom.equals(""))
						JOptionPane.showMessageDialog(f, "Veuillez saisir un pseudo");
					else
						JOptionPane.showMessageDialog(f, "Nom d'utilisateur déjà pris !");
				}
			} else {

				return false;
			}

		} while (!state.equals("OK") && result == JOptionPane.OK_OPTION);

		return true;
	}

}
