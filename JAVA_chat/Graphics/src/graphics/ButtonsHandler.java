package graphics;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import client.IOCommandes;

public class ButtonsHandler {
	private JTextField champMessage;
	private IOCommandes monIOCommandes;
	
	public ButtonsHandler(JTextField champMessage, IOCommandes monIOCommandes) {
		this.champMessage = champMessage;
		this.monIOCommandes = monIOCommandes;
	}
	
	public void sendMessage(String message) {
	    try {
	      if (message.equals("")) {
	        return;
	      }
	      monIOCommandes.ecrireReseau(message);
	      champMessage.requestFocus();
	      champMessage.setText(null);
	    } catch (Exception ex) {
	      JOptionPane.showMessageDialog(null, ex.getMessage());
	    }
	  }
	
	public void stopConnexion() {
		sendMessage("quit");
		try {
			monIOCommandes.getMaChaussette().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
}
