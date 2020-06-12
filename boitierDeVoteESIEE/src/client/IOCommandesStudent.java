package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IOCommandesStudent extends Thread {

	private String currentQuestion;
	private String currentQuestionType;
	private String currentTeacher;
	private String currentLabel;
	private JSONArray possibleAnswer;

	private BufferedReader lectureEcran;
	private BufferedReader lectureReseau;
	private PrintStream ecritureEcran;
	private PrintStream ecritureUser;
	private PrintStream ecritureReseau;
	
	private Socket maChaussette;
	//private WindowChat laFenetre;

	public Socket getMaChaussette() {
		return maChaussette;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		String message = "";
		ecrireEcran("Connexion au serveur etablie !");
		while(!Thread.interrupted()) {
			// TODO : Lire uniquement Teacher
			message = this.lireReseau();
			ecrireEcran(message);
			
			String firstChar = message.substring(0, 1);
			if(!firstChar.equals("{")) {
				continue;
			}
			// Parse JSON
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = null;
			Object jsonObj = null;
	
			try {
				jsonObj = parser.parse(message);
				jsonObject = (JSONObject) jsonObj;
				currentQuestion = (String) jsonObject.get("questionId");
				System.out.println("Current question asked : " + currentQuestion);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			this.currentQuestionType = (String) jsonObject.get("questionType");
			this.currentTeacher = (String) jsonObject.get("teacher");
			this.currentLabel = (String) jsonObject.get("label");
			this.possibleAnswer = (JSONArray) jsonObject.get("possibleAnswer");

			System.out.println("NOUVELLE QUESTION RECUE");
			System.out.println("Teacher = " + currentTeacher);
			System.out.println("QuestionType = " + currentQuestionType);
			System.out.println("Label = " + currentLabel);
			
			Iterator<String> answer = possibleAnswer.iterator();
			while (answer.hasNext()) {
				System.out.println("Answer = " + answer.next());
			}
		}
	}

	public IOCommandesStudent() {

		// Partie en local
		InputStreamReader monInputStream = new InputStreamReader(System.in);
		lectureEcran = new BufferedReader(monInputStream);
		ecritureEcran = new PrintStream(System.out);
		ecritureUser = new PrintStream(System.out);
		maChaussette = null;

	}

	public IOCommandesStudent(Socket uneChaussette) {

		// Partie en local
		InputStreamReader monInputStream = new InputStreamReader(System.in);
		lectureEcran = new BufferedReader(monInputStream);
		ecritureEcran = new PrintStream(System.out);
		ecritureUser = new PrintStream(System.out);

		// Partie réseau
		maChaussette = uneChaussette;
		InputStreamReader monInputStreamReseau = null;

		try {
			monInputStreamReseau = new InputStreamReader(maChaussette.getInputStream());
			lectureReseau = new BufferedReader(monInputStreamReseau);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ecritureReseau = new PrintStream(maChaussette.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PrintStream getEcritureUser() {
		return ecritureUser;
	}

	public void setEcritureUser(PrintStream ecritureUser) {
		this.ecritureUser = ecritureUser;
	}

	public void ecrireEcran(String texte) {
		ecritureEcran.println(texte);
	}
	
	public void ecrireUser(String texte) {
		ecritureUser.println(texte);
	}

	public String lireEcran() {
		String texte = "";
		try {
			texte = lectureEcran.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return texte;
	}

	public void ecrireReseau(String texte) {
		if (maChaussette != null)
			ecritureReseau.println(texte);
	}
	
	public void sendAnswer(String answer) {		
		JSONObject formatedAnswer = new JSONObject();
		formatedAnswer.put("questionId", this.currentQuestion);
		
		switch(currentQuestionType) {
			case "OPEN" :
				formatedAnswer.put("answer", answer);
				break;
			case "UNIQUE" :
				// Formater result into string
				formatedAnswer.put("answer", answer);
				break;
			case "MULTIPLE" :
				// Formater result into string
				formatedAnswer.put("answer", answer);
				break;
		}
		
		System.out.println(formatedAnswer.toJSONString());
		this.ecrireReseau(formatedAnswer.toJSONString());
	}

	public String lireReseau() {
		if (maChaussette != null && !maChaussette.isClosed()) {
			String texte = "";
			try {
				texte = lectureReseau.readLine();
			} catch (Exception e ) {
				e.printStackTrace();
			}

			return texte;
		}
		return null;
	}

	public void setEcritureEcran(PrintStream printStream) {
		this.ecritureEcran = printStream;

	}
}
