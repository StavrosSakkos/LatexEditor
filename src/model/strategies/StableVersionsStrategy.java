package model.strategies;

import model.Document;
import model.DocumentManager;
import util.PathUtilization;

import java.util.ArrayList;
import java.util.HashMap;

public class StableVersionsStrategy implements VersionsStrategy {

	private String path;
	private HashMap<String,Document> files;
	private ArrayList<Document> versionsHistory;
	private ArrayList<String> nameOfFiles;

	public StableVersionsStrategy() {
		path = PathUtilization.OUTPUT_FOLDER_PATH;
		getEntireHistoryFromStorage(path);
	}
	
	public StableVersionsStrategy(String path) {
		this.path = path;
		getEntireHistoryFromStorage(path);
	}
	
	public void putVersion(Document doc) {
		versionsHistory.add(doc);
		//System.out.println(versionsHistory);
	}
	
	public void removeVersion() {
		if(versionsHistory.size() > 0) {
			versionsHistory.remove(versionsHistory.size()-1);
		}
	}

	public void setEntireHistory(ArrayList<Document> history) {
		this.versionsHistory = history;
	}

	public ArrayList<Document> getEntireHistory() {
		return versionsHistory;
	}

	public ArrayList<Document> getEntireHistoryFromStorage(String folderPath) {
		try {
			files = (new DocumentManager(folderPath)).getMapOfPrototypes();
			versionsHistory = new ArrayList<Document>(files.values());
			nameOfFiles = new ArrayList<String>(files.keySet());
		} catch (Exception e) {
			files = new HashMap<String,Document>();
			versionsHistory = new ArrayList<Document>();
			nameOfFiles = new ArrayList<String>();
			e.printStackTrace();
		}
		return versionsHistory;
	}
	
	public Document getVersion() {
		return versionsHistory.get(versionsHistory.size()-1);	
	}
	
	public String getPath() {
		return path;
	}
	
	public ArrayList<String> getNameOfFiles() {
		return nameOfFiles;
	}
	
	/*
	public static void main(String args[]) {
		StableVersionsStrategy svs = new StableVersionsStrategy();
		System.out.println(svs.getPath());
		for(String i : svs.getNameOfFiles() ) {
			System.out.println(i);
		}
		for(Document i : svs.getEntireHistory() ) {
			System.out.println(i.getContents());
		}
	}
	*/
}