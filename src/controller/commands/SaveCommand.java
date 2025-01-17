package controller.commands;

import java.util.ArrayList;
import java.util.Arrays;

import model.Document;
import model.strategies.VersionsManager;
import util.FileParser;

public class SaveCommand implements Command {
	
	private VersionsManager versionsManager;
	private ArrayList<String> currentVersion;
	private String nameOfFile = "";
	
	public SaveCommand() {}
	
	public SaveCommand(VersionsManager versionsManager,String nameOfFile,ArrayList<String> currentVersion) {
		this.versionsManager = versionsManager;
		this.currentVersion = currentVersion;
		setNameOfFile(nameOfFile);
	}
	
	public void execute() {
		if(!currentVersion.isEmpty()) {
			Document newDocument = FileParser.createDocument(currentVersion);
			if(versionsManager.isEnabled()) {
				versionsManager.setCurrentVersion(newDocument);
				if(versionsManager.isStableVersion()) {
					newDocument.save(nameOfFile);
				}
			}	
		}
	}

	public void setContentsBasedOnString(String text) {
		currentVersion = new ArrayList<>(Arrays.asList(text.split(System.getProperty("line.separator"))));
	}
	
	public void setNameOfFile(String nameOfFile) {
		this.nameOfFile = (nameOfFile.endsWith(".tex")) ? nameOfFile : nameOfFile +".tex";
	}
}