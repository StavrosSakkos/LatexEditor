package model;

import java.util.HashMap;

import util.FileParser;
import util.PathUtilization;

import java.util.ArrayList;
import java.io.File;

public class DocumentManager {

	private HashMap<String,Document> mapOfPrototypes;

	// 1 author | 2 date | 3 copyright | 4 versionID | 5 contents
	public DocumentManager() {
		mapOfPrototypes = dynamicallyLoadPrototypes(PathUtilization.TEMPLATE_ID_FOLDER_PATH);
	}

	public DocumentManager(String folder) {
		mapOfPrototypes = dynamicallyLoadPrototypes(folder);
	}
	
	public HashMap<String,Document> dynamicallyLoadPrototypes (String folderPath) {
		HashMap<String,Document> map = new HashMap<String,Document>();
		FileParser fp = new FileParser(folderPath);
		if(fp.getFiles().length != 0) {
			for(File aFile : fp.getFiles()){
				ArrayList<String> content = FileParser.getContents(aFile);
				String author = FileParser.getAuthor(content);
				String date = FileParser.getLastTimeModification(aFile);
				String copyright = FileParser.getCopyright(content);
				map.put(fp.getTemplateID(aFile), new Document(author,date,copyright,"Default",content));
			}
		}
		return map;
	}
	
	public Document createDocument(String templateID) throws CloneNotSupportedException {
		return (Document) mapOfPrototypes.get(templateID).clone();
	}

	public void addPrototypeToMap(String templateID,Document doc) {
		if(!getMapOfPrototypes().containsKey(templateID)) {
			mapOfPrototypes.put(templateID,doc);
		}
	}
	
	public HashMap<String, Document> getMapOfPrototypes(){
		return mapOfPrototypes;
	}
}