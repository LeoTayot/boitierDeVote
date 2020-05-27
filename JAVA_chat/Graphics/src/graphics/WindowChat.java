package graphics;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import client.IOCommandes;
import server.IOCommandesServeur;
import server.PrincipaleServeur;
import utilisateur.ChatUser;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class WindowChat extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextPane chatHistory;
	private JTextPane usernames;
	private JList<ChatUser> userList;

	private ButtonsHandler buttonsHandler;
	private IOCommandes monIOCommandes;

	public IOCommandes getMonIOCommandes() {
		return monIOCommandes;
	}

	public void setMonIOCommandes(IOCommandes monIOCommandes) {
		this.monIOCommandes = monIOCommandes;
	}

	private IOCommandesServeur monIOCommandesServeur;

	/**
	 * Create the frame.
	 */
	public WindowChat(IOCommandes monIOCommandes) {
		this.monIOCommandes = monIOCommandes;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane charScrollPane = new JScrollPane();
		charScrollPane.setBounds(201, 59, 464, 383);
		contentPane.add(charScrollPane);

		chatHistory = new JTextPane();
		charScrollPane.setViewportView(chatHistory);
		chatHistory.setEditable(false);
		chatHistory.setMargin(new Insets(6, 6, 6, 6));
		chatHistory.setContentType("text/html;charset=UTF-8");
		chatHistory.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

		textField = new JTextField();

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buttonsHandler.sendMessage(textField.getText().trim());
				}
			}
		});
		textField.setBounds(30, 454, 635, 41);
		contentPane.add(textField);
		textField.setColumns(10);

		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (ch == '>' || ch == '<') {
					e.consume();
				}

			}
		});

		buttonsHandler = new ButtonsHandler(textField, monIOCommandes);

		JButton btnNewButton = new JButton("Envoyer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonsHandler.sendMessage(textField.getText().trim());

			}
		});
		btnNewButton.setBounds(548, 507, 117, 29);
		contentPane.add(btnNewButton);

		JButton btnDconnexion = new JButton("Déconnexion");
		btnDconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonsHandler.stopConnexion();
				dispose();
			}
		});
		btnDconnexion.setBounds(30, 507, 117, 29);
		contentPane.add(btnDconnexion);

		JLabel lblNewLabel = new JLabel("Utilisateurs connectés");
		lblNewLabel.setBounds(30, 19, 158, 16);
		contentPane.add(lblNewLabel);

		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(201, 19, 158, 16);
		contentPane.add(lblChat);

		JScrollPane userScrollPane = new JScrollPane();
		userScrollPane.setBounds(30, 59, 148, 150);
		contentPane.add(userScrollPane);

		usernames = new JTextPane();
		userScrollPane.setViewportView(usernames);
		usernames.setEditable(false);
		usernames.setMargin(new Insets(6, 6, 6, 6));
		usernames.setContentType("text/html;charset=UTF-8");

		userList = new JList<ChatUser>();
		userList.setBounds(30, 222, 148, 220);
		contentPane.add(userList);
		usernames.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				buttonsHandler.stopConnexion();

			}
		});
	}

	public JTextPane getUsernames() {
		return usernames;
	}

	public void setUsernames(JTextPane usernames) {
		this.usernames = usernames;
	}

	public JTextPane getChatHistory() {
		return chatHistory;
	}

	public void setChatHistory(JTextPane chatHistory) {
		this.chatHistory = chatHistory;
	}

	public void appendToPane(JTextPane tp, String msg) {
		HTMLDocument doc = (HTMLDocument) tp.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) tp.getEditorKit();
		try {
			editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
			tp.setCaretPosition(doc.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearPane(JTextPane tp) {
		tp.setText("");
	}

	public void updateListUsers() {
		ArrayList<ChatUser> lesUtilisateurs = new ArrayList<ChatUser>();
		System.out.println(PrincipaleServeur.userList);
		for (Map.Entry<Socket, String> entry : PrincipaleServeur.userList.entrySet()) {
			Socket key = entry.getKey();
			String value = entry.getValue();
			System.out.println("Key : " + key + " Value : " + value);
			lesUtilisateurs.add(new ChatUser(key, value));
		}
		ChatUser[] lesUtilisateursArray = new ChatUser[lesUtilisateurs.size()];
		for (int i = 0; i < lesUtilisateurs.size(); i++) {
			lesUtilisateursArray[i] = lesUtilisateurs.get(i);
			System.out.println(lesUtilisateursArray[i]);
		}
		
		userList.setListData(lesUtilisateursArray);
		userList.revalidate();

	}
}
