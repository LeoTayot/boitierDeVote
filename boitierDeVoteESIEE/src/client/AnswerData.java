package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import graphics.ClientStudentUI;
import graphics.ClientTeacherUI;

public class AnswerData extends Thread {
	private BufferedReader lectureReseau;
	private Socket maChaussette;
	private ClientTeacherUI teacherUI;

	public String currentQuestion;
	public JSONArray currentAnswers = new JSONArray();
	public String currentQuestionType;
	public String currentQuestionLabel;
	
	public JSONArray students = new JSONArray();
	
	public AnswerData(Socket uneChaussette, ClientTeacherUI teacher) {
		// Partie réseau
		teacherUI = teacher;
		maChaussette = uneChaussette;
		InputStreamReader monInputStreamReseau = null;

		try {
			monInputStreamReseau = new InputStreamReader(maChaussette.getInputStream());
			lectureReseau = new BufferedReader(monInputStreamReseau);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	@Override
	public void run() {
		String message = "";
		while(!Thread.interrupted()) {
			message = this.lireReseau();
			if(message == null || message.contentEquals("EXIT")) {
				continue;
			}
			
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = null;
			Object jsonObj = null;
	
			try {
				jsonObj = parser.parse(message);
				jsonObject = (JSONObject) jsonObj;
				if(jsonObject.get("type") == null || !jsonObject.get("type").equals("answerData")) {
					continue;
				}
				
				setCurrentQuestion((JSONObject) jsonObject.get("infos"));
				formatAnswer((JSONArray) jsonObject.get("data"));
				refreshUI();
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setCurrentQuestion(JSONObject infos) {
		currentQuestion = (String) infos.get("lastQuestion");
		currentQuestionType = (String) infos.get("questionType");
		currentAnswers = (JSONArray) infos.get("possibleAnswer");
		currentQuestionLabel = (String) infos.get("labelQuestion");
		
		this.teacherUI.textPaneQuestion.setText(currentQuestionLabel);
	}
	
	@SuppressWarnings("unchecked")
	private void formatAnswer(JSONArray data) {
		students.clear();
		
		for(Object obj : data) {
			JSONObject person = (JSONObject) obj;
			if(person.get("role").equals("S")) {
				JSONObject objStudent = new JSONObject();
				objStudent.put("username", (String) person.get("username"));
				boolean hasAnswered = false;
				String answer = null;
				if(!(person.get("ans_"+currentQuestion) == null)) {
					answer = (String) person.get("ans_"+currentQuestion);
					hasAnswered = true;
				}
				objStudent.put("username", (String) person.get("username"));
				objStudent.put("hasAnswered", hasAnswered);
				objStudent.put("answer", answer);
				students.add(objStudent);
			}
		}
	}
	
	private void refreshUI() {
		resetStudents();
		refreshStudents();
		resetAnswers();
		if(currentQuestion != null) {
			refreshAnswers();			
		}
	}
	
	private void resetStudents() {
		this.teacherUI.labelUser12.setVisible(false);
		this.teacherUI.labelUser11.setVisible(false);
		this.teacherUI.labelUser10.setVisible(false);
		this.teacherUI.labelUser9.setVisible(false);
		this.teacherUI.labelUser8.setVisible(false);
		this.teacherUI.labelUser7.setVisible(false);
		this.teacherUI.labelUser6.setVisible(false);
		this.teacherUI.labelUser5.setVisible(false);
		this.teacherUI.labelUser4.setVisible(false);
		this.teacherUI.labelUser3.setVisible(false);
		this.teacherUI.labelUser2.setVisible(false);
		this.teacherUI.labelUser1.setVisible(false);
		this.teacherUI.buttonUserAnswer12.setVisible(false);
		this.teacherUI.buttonUserAnswer11.setVisible(false);
		this.teacherUI.buttonUserAnswer10.setVisible(false);
		this.teacherUI.buttonUserAnswer9.setVisible(false);
		this.teacherUI.buttonUserAnswer8.setVisible(false);
		this.teacherUI.buttonUserAnswer7.setVisible(false);
		this.teacherUI.buttonUserAnswer6.setVisible(false);
		this.teacherUI.buttonUserAnswer5.setVisible(false);
		this.teacherUI.buttonUserAnswer4.setVisible(false);
		this.teacherUI.buttonUserAnswer3.setVisible(false);
		this.teacherUI.buttonUserAnswer2.setVisible(false);
		this.teacherUI.buttonUserAnswer1.setVisible(false);
	}
	
	private void refreshStudents() {
		JSONObject student = null;
		
		switch(students.size()) {
			case 12:
				student = (JSONObject) students.get(11);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser12.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer12.setVisible(true);
				} else {
					this.teacherUI.labelUser12.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser12.setVisible(true);
			case 11:
				student = (JSONObject) students.get(10);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser11.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer11.setVisible(true);
				} else {
					this.teacherUI.labelUser11.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser11.setVisible(true);
			case 10:
				student = (JSONObject) students.get(9);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser10.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer10.setVisible(true);
				} else {
					this.teacherUI.labelUser10.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser10.setVisible(true);
			case 9:
				student = (JSONObject) students.get(8);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser9.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer9.setVisible(true);
				} else {
					this.teacherUI.labelUser9.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser9.setVisible(true);
			case 8:
				student = (JSONObject) students.get(7);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser8.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer8.setVisible(true);
				} else {
					this.teacherUI.labelUser8.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser8.setVisible(true);
			case 7:
				student = (JSONObject) students.get(6);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser7.setText("<html><font color='#04c811'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer7.setVisible(true);
				} else {
					this.teacherUI.labelUser7.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser7.setVisible(true);
			case 6:
				student = (JSONObject) students.get(5);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser6.setText("<html><font color='#04c971'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer6.setVisible(true);
				} else {
					this.teacherUI.labelUser6.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser6.setVisible(true);
			case 5:
				student = (JSONObject) students.get(4);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser5.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer5.setVisible(true);
				} else {
					this.teacherUI.labelUser5.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser5.setVisible(true);
			case 4:
				student = (JSONObject) students.get(3);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser4.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer4.setVisible(true);
				} else {
					this.teacherUI.labelUser4.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser4.setVisible(true);
			case 3:
				student = (JSONObject) students.get(2);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser3.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer3.setVisible(true);
				} else {
					this.teacherUI.labelUser3.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser3.setVisible(true);
			case 2:
				student = (JSONObject) students.get(1);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser2.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer2.setVisible(true);
				} else {
					this.teacherUI.labelUser2.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser2.setVisible(true);
			case 1:
				student = (JSONObject) students.get(0);
				if((boolean) student.get("hasAnswered")) {
					this.teacherUI.labelUser1.setText("<html><font color='#04c911'>"+(String) student.get("username")+"</font></html>");
					this.teacherUI.buttonUserAnswer1.setVisible(true);
				} else {
					this.teacherUI.labelUser1.setText("<html><font color='#000000'>"+(String) student.get("username")+"</font></html>");
				}
				this.teacherUI.labelUser1.setVisible(true);
				break;
			default:
				this.teacherUI.labelUser1.setText("Aucun etudiant pour le moment");
				break;
		}
	}

	private float[] setPercents(int[] stats, int nbAnswer) {
		float[] percents = new float[currentAnswers.size()];
		if(nbAnswer <= 0) {
			return percents;
		}
		
		switch(currentAnswers.size()) {
		case 12 :
			percents[11] = stats[11] * 100 / nbAnswer;
		case 11 :
			percents[10] = stats[10] * 100 / nbAnswer;
		case 10 :
			percents[9] = stats[9] * 100 / nbAnswer;
		case 9 :
			percents[8] = stats[8] * 100 / nbAnswer;
		case 8 :
			percents[7] = stats[7] * 100 / nbAnswer;
		case 7 :
			percents[6] = stats[6] * 100 / nbAnswer;
		case 6 :
			percents[5] = stats[5] * 100 / nbAnswer;
		case 5 :
			percents[4] = stats[4] * 100 / nbAnswer;
		case 4 :
			percents[3] = stats[3] * 100 / nbAnswer;
		case 3 :
			percents[2] = stats[2] * 100 / nbAnswer;
		case 2 :
			percents[1] = stats[1] * 100 / nbAnswer;
		case 1 :
			percents[0] = stats[0] * 100 / nbAnswer;
			break;
		}
		
		return percents;
	}
	
	private void resetAnswers() {
		this.teacherUI.jPanelAnswersOpen.setVisible(false);
		this.teacherUI.jPanelAnswersMulti.setVisible(false);
	}
	
	private void refreshAnswers() {
		int nbAnswer = 0;
		int[] stats = new int[currentAnswers.size()];
		float[] percents = new float[currentAnswers.size()];
		switch(currentQuestionType) {
			case "OPEN" :
				this.teacherUI.jPanelAnswersOpen.setVisible(true);
				String answersToShow = "";
				
				for(Object obj : students) {
					JSONObject student = (JSONObject) obj;
					if(student.get("answer") == null) {
						continue;
					}
					answersToShow += (String) student.get("username")+" : "
							+(String) student.get("answer")
							+"\n---------------------------\n\n";
				}
				this.teacherUI.textPaneQuickViewAnswers.setText(answersToShow);
				break;
			case "MULTIPLE" :
				this.teacherUI.jPanelAnswersMulti.setVisible(true);
				stats = new int[currentAnswers.size()];
				for(Object obj : students) {
					JSONObject student = (JSONObject) obj;
					JSONArray answer = null;
					if(student.get("answer") == null) {
						continue;
					}
					JSONParser parser = new JSONParser();
					System.out.println(student.toJSONString());
					try {
						answer = (JSONArray)parser.parse((String) student.get("answer"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(answer == null || answer.size() == 0) {
						continue;
					}
					
					Iterator it = answer.iterator();
			        while (it.hasNext()) {
			        	String uniqueAnswer = (String) it.next();
			        	stats[Integer.parseInt(uniqueAnswer)]++;
			        	nbAnswer++;
			        }
				}
				percents = setPercents(stats, nbAnswer);
				setProgressBars(stats, percents);
				break;
			case "UNIQUE" :
				this.teacherUI.jPanelAnswersMulti.setVisible(true);
				stats = new int[currentAnswers.size()];
				
				for(Object obj : students) {
					JSONObject student = (JSONObject) obj;
					if(student.get("answer") == null) {
						continue;
					}
					String answer = (String) student.get("answer");
					stats[Integer.parseInt(answer)]++;
					nbAnswer++;
				}
				percents = setPercents(stats, nbAnswer);
				setProgressBars(stats, percents);
				break;
		}
	}
	
	private void setProgressBars(int[] stats, float[] percents) {
		switch(currentAnswers.size()) {
			case 8 :
				this.teacherUI.labelOverviewAnswer8.setText((String) currentAnswers.get(7));
				this.teacherUI.labelOverviewAnswer8.setVisible(true);
				this.teacherUI.progressBarAns8.setString(Integer.toString(stats[7])+" answer(s)");
				this.teacherUI.progressBarAns8.setStringPainted(true);
				this.teacherUI.progressBarAns8.setValue(Math.round(percents[7]));
				this.teacherUI.progressBarAns8.setVisible(true);
			case 7 :
				this.teacherUI.labelOverviewAnswer7.setText((String) currentAnswers.get(6));
				this.teacherUI.labelOverviewAnswer7.setVisible(true);
				this.teacherUI.progressBarAns7.setString(Integer.toString(stats[6])+" answer(s)");
				this.teacherUI.progressBarAns7.setStringPainted(true);
				this.teacherUI.progressBarAns7.setValue(Math.round(percents[6]));
				this.teacherUI.progressBarAns7.setVisible(true);
			case 6 :
				this.teacherUI.labelOverviewAnswer6.setText((String) currentAnswers.get(5));
				this.teacherUI.labelOverviewAnswer6.setVisible(true);
				this.teacherUI.progressBarAns6.setString(Integer.toString(stats[5])+" answer(s)");
				this.teacherUI.progressBarAns6.setStringPainted(true);
				this.teacherUI.progressBarAns6.setValue(Math.round(percents[5]));
				this.teacherUI.progressBarAns6.setVisible(true);
			case 5 :
				this.teacherUI.labelOverviewAnswer5.setText((String) currentAnswers.get(4));
				this.teacherUI.labelOverviewAnswer5.setVisible(true);
				this.teacherUI.progressBarAns5.setString(Integer.toString(stats[4])+" answer(s)");
				this.teacherUI.progressBarAns5.setStringPainted(true);
				this.teacherUI.progressBarAns5.setValue(Math.round(percents[4]));
				this.teacherUI.progressBarAns5.setVisible(true);
			case 4 :
				this.teacherUI.labelOverviewAnswer4.setText((String) currentAnswers.get(3));
				this.teacherUI.labelOverviewAnswer4.setVisible(true);
				this.teacherUI.progressBarAns4.setString(Integer.toString(stats[3])+" answer(s)");
				this.teacherUI.progressBarAns4.setStringPainted(true);
				this.teacherUI.progressBarAns4.setValue(Math.round(percents[3]));
				this.teacherUI.progressBarAns4.setVisible(true);
			case 3 :
				this.teacherUI.labelOverviewAnswer3.setText((String) currentAnswers.get(2));
				this.teacherUI.labelOverviewAnswer3.setVisible(true);
				this.teacherUI.progressBarAns3.setString(Integer.toString(stats[2])+" answer(s)");
				this.teacherUI.progressBarAns3.setStringPainted(true);
				this.teacherUI.progressBarAns3.setValue(Math.round(percents[2]));
				this.teacherUI.progressBarAns3.setVisible(true);
			case 2 :
				this.teacherUI.labelOverviewAnswer2.setText((String) currentAnswers.get(1));
				this.teacherUI.labelOverviewAnswer2.setVisible(true);
				this.teacherUI.progressBarAns2.setString(Integer.toString(stats[1])+" answer(s)");
				this.teacherUI.progressBarAns2.setStringPainted(true);
				this.teacherUI.progressBarAns2.setValue(Math.round(percents[1]));
				this.teacherUI.progressBarAns2.setVisible(true);
			case 1 :
				this.teacherUI.labelOverviewAnswer1.setText((String) currentAnswers.get(0));
				this.teacherUI.labelOverviewAnswer1.setVisible(true);
				this.teacherUI.progressBarAns1.setString(Integer.toString(stats[0])+" answer(s)");
				this.teacherUI.progressBarAns1.setStringPainted(true);
				this.teacherUI.progressBarAns1.setValue(Math.round(percents[0]));
				this.teacherUI.progressBarAns1.setVisible(true);
				break;
		}
	}
}
