package model.strategies;

import model.Document;
import java.util.ArrayList;

public class VolatileVersionsStrategy implements VersionsStrategy {

	private ArrayList<Document> versionsHistory;
	//private final String typeOfStrategy = "Volatile";
	
	public VolatileVersionsStrategy() {
		versionsHistory = new ArrayList<Document>();
	}

	public void putVersion(Document doc) {
		versionsHistory.add(doc);
	}

	//Based on guideline pdf : remove last version
	public void removeVersion() {
		if(versionsHistory.size() > 0) {
			versionsHistory.remove(versionsHistory.size()-1);
		}
	}

	public void setEntireHistory(ArrayList<Document> history) {
		versionsHistory = history;
	}

	public ArrayList<Document> getEntireHistory() {
		return versionsHistory;
	}

	public Document getVersion() {
		return versionsHistory.get(versionsHistory.size()-1);	
	}
	
}