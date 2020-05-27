package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import graphics.RegisterForm;

public class Connector {

	private Connection maConnection;

	public Connection getMaConnection() {
		return maConnection;
	}

	public void setMaConnection(Connection maConnection) {
		this.maConnection = maConnection;
	}

	public void initConnection() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root",
					"UmpaLumpa80480");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		maConnection = connection;

	}

	public boolean checkUsername(String username) {
		PreparedStatement ps;
		ResultSet rs;
		boolean checkUser = false;
		String query = "SELECT * FROM `utilisateurs` WHERE `username` =?";

		try {
			ps = maConnection.prepareStatement(query);
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				checkUser = true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
		}
		return checkUser;
	}
	
	public String hashPassword(String plainTextPassword){
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}
	
	public boolean checkPass(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}

}
