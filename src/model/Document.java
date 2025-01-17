package model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import util.FileParser;
import util.PathUtilization;

public class Document {

	private String author;
	private String copyright = "";
	private String date;
	private String versionID;
	private ArrayList<String> content;

	public Document() {
		author = "";
		date = new Date().toString();
		copyright = "";
		versionID = "Default";
		content = new ArrayList<>();
	}
	
	// 1 author | 2 date | 3 copyright | 4 versionID | 5 contents
	public Document(String author, String date, String copyright, String versionID, ArrayList<String> content) {
		this.content = content;
		this.author = author;
		this.date = date;
		this.copyright = copyright;
		this.versionID = versionID;	
	}

	public void save(String nameOfFile) {
		try {
			String name;
			name = (nameOfFile.equals("") || nameOfFile.equals(".tex")) ?
					"output"+Integer.toString((
							new FileParser(PathUtilization.OUTPUT_FOLDER_PATH)).getFiles().length + 1) + ".tex":
					nameOfFile;
			versionID = name;
			File aFile = new File(PathUtilization.OUTPUT_FOLDER_PATH + name);
			FileWriter fw = new FileWriter(aFile);
			for(String line : content) {
				fw.write(line + System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e) {
			saveFileWithGivenPath(nameOfFile);
		}
	}
	
	//In case nameOfFile is given as full-path
	public void saveFileWithGivenPath(String path) {
		try {
			String name;
			name = (path.equals("") || path.equals(".tex")) ?
					"output"+Integer.toString((
							new FileParser(PathUtilization.OUTPUT_FOLDER_PATH)).getFiles().length + 1) + ".tex":
					path;
			File aFile = new File(name);
			FileWriter fw = new FileWriter(aFile);
			for(String line : content) {
				fw.write(line + System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Comparing Document values. Author/Copyright are included in the contents so if those are different
	// then contents are different
	public boolean isSameDocument(Document other) {
		return this.date.toString().equals(other.date.toString())
				&& this.versionID.equals(other.versionID)
				&& this.content.equals(other.content);
	}

	public Object clone() {
		Document aDoc = null;
	    try {
	        aDoc = (Document) super.clone();
	    } catch (CloneNotSupportedException e) {
	        aDoc = new Document(author,date,copyright,versionID,content);
	    }
	    return aDoc;
	}
	// 1 author | 2 date | 3 copyright | 4 versionID | 5 contents
	public Document updateDocument(ArrayList<String> newContent) {
		return new Document(author,date,copyright,versionID,newContent);
	}
	
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}
	
	public String getVersionID() {
		return versionID;
	}
	
	public ArrayList<String> getContent() {
		return content;
	}

}