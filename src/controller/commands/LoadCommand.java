package controller.commands;

import java.io.File;
import java.util.ArrayList;

import model.strategies.VersionsManager;
import util.FileParser;

public class LoadCommand implements Command {

	@SuppressWarnings("unused")
	private VersionsManager versionsManager;
	private File aFile;
	@SuppressWarnings("unused")
	private String path;
	private ArrayList<String> contents;
	
	public LoadCommand() {
		path = "";
		aFile = null;
	}
	
	public LoadCommand(VersionsManager versionsManager,String path) {
		this.versionsManager = versionsManager;
		this.path = path;
		aFile = new File(path);
	}
	
	public void execute() {
		if(!(aFile ==  null)) {
			contents = FileParser.getContents(aFile);
		}
	}
	
	public ArrayList<String> getContents() {
		return contents;
	}
}
