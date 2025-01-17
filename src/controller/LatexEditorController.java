package controller;

import java.util.ArrayList;
import java.util.HashMap;
import controller.commands.*;
import model.Document;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;

public class LatexEditorController {

	private HashMap<String,Command> mapOfCommands;
	private VersionsManager versionsManager;
	private Document currentDocument;
	
	public LatexEditorController() {
		mapOfCommands = createMapOfCommands();
		versionsManager = new VersionsManager(VersionsStrategyFactory.createStrategy("Volatile"));
		currentDocument = new Document();
	}
	
	private HashMap<String,Command> createMapOfCommands(){
		HashMap<String,Command> returnMap = new HashMap<String,Command>();
		returnMap.put("CreateCommand",CommandsFactory.createCommands("CreateCommand"));
		returnMap.put("AddLatexCommand",CommandsFactory.createCommands("AddLatexCommand"));
		returnMap.put("EditCommand",CommandsFactory.createCommands("EditCommand"));
		returnMap.put("LoadCommand",CommandsFactory.createCommands("LoadCommand"));
		returnMap.put("SaveCommand",CommandsFactory.createCommands("SaveCommand"));
		returnMap.put("RollBackCommand",CommandsFactory.createCommands("RollBackCommand"));
		returnMap.put("ChangeStrategyCommand",CommandsFactory.createCommands("ChangeStrategyCommand"));
		return returnMap;
	}
	
	public void enact(String command) {
		mapOfCommands.get(command).execute();
	}
	
	public void editMapOfCommands(String command,String firstString,String secondString) {
		mapOfCommands.replace(command,CommandsFactory.createCommands(
				command, versionsManager, firstString, secondString));
	}
	
	//AddLatexCommand->update the edited Document
	public void updateHistory() {
		if (versionsManager.isEnabled()) {
			versionsManager.setCurrentVersion(currentDocument);
		}
	}
	
	public void updateHistory(Document doc) {
		if (versionsManager.isEnabled()) {
			currentDocument = (Document) doc.clone();
			versionsManager.setCurrentVersion(currentDocument);
		}
	}
	
	public void updateCurrentDocument () {
		//currentDocument.setVersionID(currentDocument.getVersionID()+??something??);
		ArrayList<String> newContent = new ArrayList<String>(currentDocument.getContent());
		newContent.add(getAddLatexCommandText());
		currentDocument = currentDocument.updateDocument(newContent);
	}
	
	public void setCurrentDocument(Document currentDocument) {
		this.currentDocument = currentDocument;
	}
	
	public VersionsManager getVersionsManager() {
		return versionsManager;
	}
	
	public HashMap<String,Command> getMapOfCommands(){
		return mapOfCommands;
	}
	
	public String getTypeOfCurrentStrategy() {
		return versionsManager.getStrategy().getClass().getSimpleName();
	}
	
	public Document getCurrentDocument() {
		return currentDocument;
	}
	
	//Should be done differently
	public Document getCreateCommandDocument() {
		return ((CreateCommand) mapOfCommands.get("CreateCommand")).getCreatedDocument();
	}
	
	public String getAddLatexCommandText() {
		return ((AddLatexCommand) mapOfCommands.get("AddLatexCommand")).getText();
	}
	
	public ArrayList<String> getLoadCommandsContents(){
		return ((LoadCommand) mapOfCommands.get("LoadCommand")).getContents();
	}
	
}
