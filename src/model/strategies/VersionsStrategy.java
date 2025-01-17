package model.strategies;

import java.util.ArrayList;
import model.Document;

public interface VersionsStrategy {

	public abstract void removeVersion();
	public abstract void putVersion(Document doc);
	public abstract void setEntireHistory(ArrayList<Document> history);
	public abstract Document getVersion();
	public abstract ArrayList<Document> getEntireHistory();
	
}