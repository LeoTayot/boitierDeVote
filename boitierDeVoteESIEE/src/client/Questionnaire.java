package client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Questionnaire {
	private String questionId;
	private String questionType;
    private String teacher;
    private String label;
    private List<String> possibleAnswer = new ArrayList<>();
    // private int timer;
    // private boolean isOtherAcceptable;
    // private boolean showCorrection;

	public Questionnaire() {
		questionId = UUID.randomUUID().toString();
		questionType = null;
	    teacher = null;
	    label = null;
	}
	
	public Questionnaire(String teacherName) {
		questionId = UUID.randomUUID().toString();
		teacher = teacherName;
		questionType = null;
	    label = null;
	}
	
	public boolean setLabel(String newLabel) {
		// Test if label is suitable --- Regex ?
		this.label = newLabel;
		return true;
	}
	
	public boolean setTeacher(String teacherName) {
		this.teacher = teacherName;
		return true;
	}
	
	public boolean setQuestionType(String newQuestionType) {
		this.questionType = newQuestionType;
		return true;
	}
	
	public void clearAnswers() {
		this.possibleAnswer.clear();
	}

	public void removeAnswer(String answerToRemove) {
		this.possibleAnswer.remove(answerToRemove);
	}
	
	public void addAnswer(String newAnswer) {
		this.possibleAnswer.add(newAnswer);
	}
	
	public Object getQuestions() {
		// get Questionnaire
		JSONObject question = new JSONObject();
		question.put("questionId", this.questionId);
		question.put("questionType", this.questionType);
		question.put("teacher", this.teacher);
		question.put("label", this.label);
		JSONArray answersToJson = new JSONArray();
		
		if(!questionType.equals("OPEN") && !possibleAnswer.isEmpty()) {
			for( String answer : this.possibleAnswer) {
				answersToJson.add(answer);
			}
		}
		question.put("possibleAnswer", answersToJson);
		
		return question.toJSONString();
	}
}
