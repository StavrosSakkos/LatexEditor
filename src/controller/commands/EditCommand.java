package controller.commands;

import java.util.ArrayList;
import java.util.Arrays;

import model.Document;
import model.strategies.VersionsManager;
import util.FileParser;

public class EditCommand implements Command {
	
	private VersionsManager versionsManager;
	private String newText;
	private String fullPath;
	
	public EditCommand() {}
	
	public EditCommand(VersionsManager versionsManager,String fullPath,String newText) {
		this.versionsManager = versionsManager;
		this.newText = newText;
		this.fullPath = fullPath;
	}
	
	public void execute() {
		Document newDocument = FileParser.createDocument(getArrayListBasedOnString(newText));
		if (versionsManager.isEnabled()) {
			versionsManager.setCurrentVersion(newDocument);
			if(versionsManager.isStableVersion()) {
				newDocument.saveFileWithGivenPath(fullPath);
			}
		}
	}
	
	public ArrayList<String> getArrayListBasedOnString(String text) {
		return new ArrayList<>(Arrays.asList(
				text.split(System.getProperty("line.separator"))));
	}
}
