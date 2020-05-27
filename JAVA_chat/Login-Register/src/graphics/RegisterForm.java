package graphics;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.Connector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;

public class RegisterForm extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField nomField;
	private JTextField prenomField;

	private Connector monConnector;
	private LoginHandler parent;

	/**
	 * Create the frame.
	 */
	public RegisterForm(LoginHandler parent) {

		monConnector = new Connector();
		monConnector.initConnection();
		this.parent = parent;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 321, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Username :");
		label.setBounds(20, 209, 144, 16);
		contentPane.add(label);

		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(20, 237, 220, 32);
		contentPane.add(usernameField);
		usernameField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!Character.isLetter(ch)) {
					e.consume();
				}

			}
		});

		JLabel label_1 = new JLabel("Password :");
		label_1.setBounds(20, 281, 144, 16);
		contentPane.add(label_1);

		passwordField = new JPasswordField();
		passwordField.setBounds(20, 309, 220, 32);
		contentPane.add(passwordField);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(20, 381, 220, 32);
		contentPane.add(confirmPasswordField);

		JLabel lblConfiremerLeMot = new JLabel("Confirmer le mot de passe :");
		lblConfiremerLeMot.setBounds(20, 353, 220, 16);
		contentPane.add(lblConfiremerLeMot);

		JLabel lblNom = new JLabel("Nom :");
		lblNom.setBounds(20, 65, 144, 16);
		contentPane.add(lblNom);

		nomField = new JTextField();
		nomField.setColumns(10);
		nomField.setBounds(20, 93, 220, 32);
		contentPane.add(nomField);

		JLabel lblPrnom = new JLabel("Prénom :");
		lblPrnom.setBounds(20, 137, 144, 16);
		contentPane.add(lblPrnom);

		prenomField = new JTextField();
		prenomField.setColumns(10);
		prenomField.setBounds(20, 165, 220, 32);
		contentPane.add(prenomField);

		JLabel lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblRegister.setBounds(20, 18, 98, 32);
		contentPane.add(lblRegister);

		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = nomField.getText();
				String prenom = prenomField.getText();
				String username = usernameField.getText();
				String pass = String.valueOf(passwordField.getPassword());
				String rePass = String.valueOf(confirmPasswordField.getPassword());

				if (username.equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez renseigner un nom d'utilisateur");
				} else if (pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez renseigner un mot de passe");
				}

				else if (nom.equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez renseigner votre nom");
				} else if (prenom.equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez renseigner votre prénom");
				} else if (!pass.equals(rePass)) {
					JOptionPane.showMessageDialog(null, "Les mots de passe saisis ne correspondent pas");
				}

				else if (monConnector.checkUsername(username)) {
					JOptionPane.showMessageDialog(null, "Ce nom d'utilisateur est déjà pris");
				} else {
					PreparedStatement ps;
					String query = "INSERT INTO `utilisateurs`(`nom`, `prenom`, `username`, `password`) VALUES (?,?,?,?)";

					try {
						ps = monConnector.getMaConnection().prepareStatement(query);
						ps.setString(1, nom);
						ps.setString(2, prenom);
						ps.setString(3, username);
						ps.setString(4, monConnector.hashPassword(pass));
						if(ps.executeUpdate() > 0)
			            {
							JOptionPane.showMessageDialog(null, "Enregistrement réussi !");
			            }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

				}
			}
		});
		btnConfirmer.setBounds(149, 444, 117, 29);
		contentPane.add(btnConfirmer);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.goBackToLogin();
			}
		});
		btnAnnuler.setBounds(20, 444, 117, 29);
		contentPane.add(btnAnnuler);
		
		JLabel lblRetourLogin = new JLabel("Retour Login");
		lblRetourLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.goBackToLogin();
				
			}
		});
		lblRetourLogin.setForeground(Color.BLUE);
		lblRetourLogin.setBounds(105, 485, 87, 16);
		lblRetourLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(lblRetourLogin);
	}

}
