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

import graphics.ClientStudentUI;

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
	private ClientStudentUI studentUI;

	public Socket getMaChaussette() {
		return maChaussette;
	}
	
	public void setUI(ClientStudentUI ui) {
		studentUI = ui;
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
			
			if(message == null || message.contentEquals("EXIT")) {
				continue;
			}
			
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
			
			// Set VALUE IN GUI
			this.studentUI.jTextPaneQuestion.setText((String) jsonObject.get("label"));
			
			switch((String) jsonObject.get("questionType")) {
				case "MULTIPLE" :
					clearAvailableAnswerInGui();
					this.studentUI.jLabelTypeQuestion.setText("Plusieurs réponses sont possibles");
					this.studentUI.typeQuestion((String) jsonObject.get("questionType"));
					
					switch(this.possibleAnswer.size()) {
						case 8 :
							this.studentUI.checkBoxAnswer8.setText((String) this.possibleAnswer.get(7));
							this.studentUI.checkBoxAnswer8.setVisible(true);
						case 7 :
							this.studentUI.checkBoxAnswer7.setText((String) this.possibleAnswer.get(6));
							this.studentUI.checkBoxAnswer7.setVisible(true);
						case 6 :
							this.studentUI.checkBoxAnswer6.setText((String) this.possibleAnswer.get(5));
							this.studentUI.checkBoxAnswer6.setVisible(true);
						case 5 :
							this.studentUI.checkBoxAnswer5.setText((String) this.possibleAnswer.get(4));
							this.studentUI.checkBoxAnswer5.setVisible(true);
						case 4 :
							this.studentUI.checkBoxAnswer4.setText((String) this.possibleAnswer.get(3));
							this.studentUI.checkBoxAnswer4.setVisible(true);
						case 3 :
							this.studentUI.checkBoxAnswer3.setText((String) this.possibleAnswer.get(2));
							this.studentUI.checkBoxAnswer3.setVisible(true);
						case 2 :
							this.studentUI.checkBoxAnswer2.setText((String) this.possibleAnswer.get(1));
							this.studentUI.checkBoxAnswer2.setVisible(true);
						case 1 :
							this.studentUI.checkBoxAnswer1.setText((String) this.possibleAnswer.get(0));
							this.studentUI.checkBoxAnswer1.setVisible(true);
							break;
					}
					
					break;
				case "UNIQUE" :
					clearAvailableAnswerInGui();
					this.studentUI.jLabelTypeQuestion.setText("Une seule réponse n'est admise");
					this.studentUI.typeQuestion((String) jsonObject.get("questionType"));
					
					switch(this.possibleAnswer.size()) {
						case 8 :
							this.studentUI.radioAnswer8.setText((String) this.possibleAnswer.get(7));
							this.studentUI.radioAnswer8.setVisible(true);
						case 7 :
							this.studentUI.radioAnswer7.setText((String) this.possibleAnswer.get(6));
							this.studentUI.radioAnswer7.setVisible(true);
						case 6 :
							this.studentUI.radioAnswer6.setText((String) this.possibleAnswer.get(5));
							this.studentUI.radioAnswer6.setVisible(true);
						case 5 :
							this.studentUI.radioAnswer5.setText((String) this.possibleAnswer.get(4));
							this.studentUI.radioAnswer5.setVisible(true);
						case 4 :
							this.studentUI.radioAnswer4.setText((String) this.possibleAnswer.get(3));
							this.studentUI.radioAnswer4.setVisible(true);
						case 3 :
							this.studentUI.radioAnswer3.setText((String) this.possibleAnswer.get(2));
							this.studentUI.radioAnswer3.setVisible(true);
						case 2 :
							this.studentUI.radioAnswer2.setText((String) this.possibleAnswer.get(1));
							this.studentUI.radioAnswer2.setVisible(true);
						case 1 :
							this.studentUI.radioAnwser1.setText((String) this.possibleAnswer.get(0));
							this.studentUI.radioAnwser1.setVisible(true);
							break;
					}
					
					break;
				case "OPEN" :
					this.studentUI.jLabelTypeQuestion.setText("Question ouverte");
					this.studentUI.typeQuestion((String) jsonObject.get("questionType"));
					break;
			}
		
			
			this.studentUI.repaint();
			System.out.println("TRY TO CHANGE VALUE");
/*
			System.out.println("NOUVELLE QUESTION RECUE");
			System.out.println("Teacher = " + currentTeacher);
			System.out.println("QuestionType = " + currentQuestionType);
			System.out.println("Label = " + currentLabel);
			
			Iterator<String> answer = possibleAnswer.iterator();
			while (answer.hasNext()) {
				System.out.println("Answer = " + answer.next());
			}*/
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

		// Partie rÃ©seau
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
	
	public void clearAvailableAnswerInGui() {
		this.studentUI.radioAnwser1.setVisible(false);
		this.studentUI.radioAnswer2.setVisible(false);
		this.studentUI.radioAnswer3.setVisible(false);
		this.studentUI.radioAnswer4.setVisible(false);
		this.studentUI.radioAnswer5.setVisible(false);
		this.studentUI.radioAnswer6.setVisible(false);
		this.studentUI.radioAnswer7.setVisible(false);
		this.studentUI.radioAnswer8.setVisible(false);
		
		this.studentUI.checkBoxAnswer1.setVisible(false);
		this.studentUI.checkBoxAnswer2.setVisible(false);
		this.studentUI.checkBoxAnswer3.setVisible(false);
		this.studentUI.checkBoxAnswer4.setVisible(false);
		this.studentUI.checkBoxAnswer5.setVisible(false);
		this.studentUI.checkBoxAnswer6.setVisible(false);
		this.studentUI.checkBoxAnswer7.setVisible(false);
		this.studentUI.checkBoxAnswer8.setVisible(false);
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
