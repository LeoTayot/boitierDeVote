package graphics;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.Connector;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;

	private Connector monConnector;
	private boolean connexionReussie;
	private boolean abort;

	private String uname;
	private LoginHandler parent;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public boolean isAbort() {
		return abort;
	}

	public void setAbort(boolean abort) {
		this.abort = abort;
	}

	public boolean isConnexionReussie() {
		return connexionReussie;
	}

	public void setConnexionReussie(boolean connexionReussie) {
		this.connexionReussie = connexionReussie;
	}

	/**
	 * Create the frame.
	 */
	public LoginForm(LoginHandler parent) {
		monConnector = new Connector();
		monConnector.initConnection();
		connexionReussie = false;
		abort = false;
		this.parent = parent;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(20, 90, 220, 32);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		usernameField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!Character.isLetter(ch)) {
					e.consume();
				}

			}
		});

		JLabel lblNewLabel = new JLabel("Username :");
		lblNewLabel.setBounds(20, 62, 144, 16);
		contentPane.add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(20, 134, 144, 16);
		contentPane.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(20, 162, 220, 32);
		contentPane.add(passwordField);

		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(20, 18, 98, 32);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Connexion");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreparedStatement ps;
				ResultSet rs;
				uname = usernameField.getText();
				String pass = String.valueOf(passwordField.getPassword());

				String query = "SELECT * FROM `utilisateurs` WHERE `username` =?";

				try {
					ps = monConnector.getMaConnection().prepareStatement(query);
					ps.setString(1, uname);
					rs = ps.executeQuery();

					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "Nom d'utilisateur incorrect");
						connexionReussie = false;
					} else {
						String passwordEnBase = rs.getString("password"); 
						if(monConnector.checkPass(pass, passwordEnBase)) {
							connexionReussie = true;
						}else {
							JOptionPane.showMessageDialog(null, "Mot de passe incorrect");
							connexionReussie = false;
						}
						
					}

				} catch (SQLException ex) {
					Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
		});
		btnNewButton.setBounds(149, 266, 117, 29);
		contentPane.add(btnNewButton);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abort = true;
			}
		});
		btnAnnuler.setBounds(20, 266, 117, 29);
		contentPane.add(btnAnnuler);
		
		JLabel labelNewCompte = new JLabel("Vous n'avez pas de compte ?");
		labelNewCompte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.goRegister();
				
			}
		});
		labelNewCompte.setForeground(Color.BLUE);
		labelNewCompte.setBounds(20, 206, 220, 16);
		labelNewCompte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(labelNewCompte);
	}
	
}
