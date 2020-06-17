package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IOAnswerData extends Thread {
	// private clientTeacherAnswerUI;
	
	/*
	public IOAnswerData() {
		clientTeacherAnswerUI
	}
	 */
	
	public void run() {
		while(!Thread.interrupted()) {
			JSONObject dataToSend = new JSONObject();
			dataToSend.put("type", "answerData");
			JSONArray data = new JSONArray();
			
			for(Object obj : PrincipaleServeur.users) {
				JSONObject jsonObj = (JSONObject) obj;
				JSONObject tmpData = (JSONObject) jsonObj.clone();
				
				tmpData.remove("socketId");
				data.add(tmpData);
			}

			dataToSend.put("infos", PrincipaleServeur.infos);
			dataToSend.put("data", data);
			
			for (Object obj : PrincipaleServeur.users) {
				JSONObject jsonObj = (JSONObject) obj;
				PrintStream tmp = null;
				
				try {
					if (jsonObj.get("socketId") != null
						&& jsonObj.get("role").equals("T")) {
						tmp = new PrintStream(((Socket) jsonObj.get("socketId")).getOutputStream());
						tmp.println(dataToSend.toJSONString());
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
