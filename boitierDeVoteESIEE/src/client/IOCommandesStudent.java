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
import java.awt.Color;
import static java.awt.Color.*;

public class IOCommandesStudent extends Thread {
	private String username;

	private JSONArray questions = new JSONArray();
	public String currentQuestion;
	public String currentQuestionType;
	public String currentTeacher;
	public String currentLabel;
	public JSONArray possibleAnswer;

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
			
			// Set VALUE IN GUI
			addNewQuestion(message);
			System.out.println("Question size :"+questions.size());
			if(questions.size() > 1) {
				sendNotification();
				// update nb question en attente 
				continue;
			} else {
				setCurrentQuestion();
				refreshUI();
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

	public IOCommandesStudent(Socket uneChaussette, String username) {
		this.username = username;
		
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
	
	public void sendNotification() {
		System.out.println("NEW QUESTION AVAILABLE");
        this.studentUI.labelNotif.setText("New question available !");
        this.studentUI.labelNotif.setBackground(new Color(51,204,255));
	}
	
	public void addNewQuestion(String message) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		Object jsonObj = null;
		try {
			jsonObj = parser.parse(message);
			jsonObject = (JSONObject) jsonObj;

			questions.add(jsonObject);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setCurrentQuestion() {
        System.out.println("Je rentre dans setCurrentQuestion");
		JSONObject jsonObject = (JSONObject) questions.get(0);
		currentQuestion = (String) jsonObject.get("questionId");
		System.out.println("Current question asked : " + currentQuestion);
		
		this.currentQuestionType = (String) jsonObject.get("questionType");
		this.currentTeacher = (String) jsonObject.get("teacher");
		this.currentLabel = (String) jsonObject.get("label");
		this.possibleAnswer = (JSONArray) jsonObject.get("possibleAnswer");
	}
	
	public void refreshUI() {
        System.out.println("je rentre dans refreshUI");
		this.studentUI.jTextPaneQuestion.setText(currentLabel);
        this.studentUI.jLabelTypeQuestion.setVisible(true);
        this.studentUI.buttonSendAnswer.setVisible(true);
        this.studentUI.labelNotif.setText("");
        this.studentUI.labelNotif.setBackground(new Color(240,240,240));
        this.studentUI.textAreaOpenAnswer.setText("");
        
        System.out.println(currentLabel + " - " + currentQuestionType);
		
		switch(currentQuestionType) {
			case "MULTIPLE" :
				clearAvailableAnswerInGui();
				this.studentUI.jLabelTypeQuestion.setText("Plusieurs reponses sont possibles");
				this.studentUI.typeQuestion(currentQuestionType);
                                
				
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
				this.studentUI.jLabelTypeQuestion.setText("Only one answer is possible");
				this.studentUI.typeQuestion(currentQuestionType);
				
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
				clearAvailableAnswerInGui();
				this.studentUI.jLabelTypeQuestion.setText("Open question");
				this.studentUI.typeQuestion(currentQuestionType);
				break;
		}
		//this.studentUI.repaint();
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
		formatedAnswer.put("questionType", this.currentQuestionType);
		formatedAnswer.put("studentName", this.username);
		formatedAnswer.put("answer", answer);
		
		System.out.println(formatedAnswer.toJSONString());
		this.ecrireReseau(formatedAnswer.toJSONString());
		
		questions.remove(0);
		if(questions.size() > 0) {
			// update nb question en attente 
			setCurrentQuestion();
			refreshUI();
		} else {
			// Afficher ecran d'attente ici
			System.out.println("ATTENTE D'UNE NOUVELLE QUESTION");
		}
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
