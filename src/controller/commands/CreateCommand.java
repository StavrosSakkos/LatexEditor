package controller.commands;

import model.Document;
import model.DocumentManager;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategy;

public class CreateCommand implements Command {
	
	private String templateID = "";
	private String nameOfFile = "";
	private Document createdDocument;
	private DocumentManager documentManager = new DocumentManager();
	private VersionsManager versionsManager;
	
	public CreateCommand() {}

	public CreateCommand(VersionsManager versionsManager,String nameOfFile) {
		this.versionsManager = versionsManager;
		setNameOfFile(nameOfFile);
		templateID = "";
	}
	
	public CreateCommand(VersionsManager versionsManager,String templateID,String nameOfFile) {
		this.versionsManager = versionsManager;
		this.templateID = templateID;
		setNameOfFile(nameOfFile);
	}
	
	public void execute() {
		try {
			if(templateID.equals("")) {
				createdDocument = new Document();
			}
			if(documentManager.getMapOfPrototypes().keySet().contains(templateID)) {
				createdDocument = documentManager.createDocument(templateID);
			}
			if(versionsManager.isStableVersion() && versionsManager.isEnabled()) {
				createdDocument.save(nameOfFile);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Null CreateCommand->createdDocument");
		}
	}

	public Document getCreatedDocument() {
		return createdDocument;
	}
	
	public void setVersionsManagerStrategy(VersionsStrategy strategy) {
		versionsManager.setStrategy(strategy);
	}
	
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	
	public void setNameOfFile(String nameOfFile) {
		this.nameOfFile = (nameOfFile.endsWith(".tex")) ? nameOfFile : nameOfFile +".tex";
	}

}
