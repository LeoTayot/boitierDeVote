package utilisateur;

import java.net.Socket;

public class ChatUser {
	
	private Socket userChaussette;
	private String pseudo;
	
	public Socket getUserChaussette() {
		return userChaussette;
	}

	public void setUserChaussette(Socket userChaussette) {
		this.userChaussette = userChaussette;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public ChatUser(Socket userChaussette, String pseudo) {
		super();
		this.userChaussette = userChaussette;
		this.pseudo = pseudo;
	}

	@Override
	public String toString() {
		return pseudo;
	}
	

}
