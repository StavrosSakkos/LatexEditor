package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import model.Document;

public class FileParser {

	private String path;
	private File [] files;

	public FileParser() {}
	
	public FileParser(String path){
		files = (new File(path)).listFiles();
		this.path = path;
	}
	
	// Create object based on contents.Works like a util method
	// 1 author | 2 date | 3 copyright | 4 versionID | 5 contents
	public static Document createDocument(ArrayList<String> contents) {
		return new Document(getAuthor(contents),(new Date()).toString(),
				getCopyright(contents),"Default",contents);
	}

	public static Document createDocument(ArrayList<String> firstArr,ArrayList<String> secondArr) {
		ArrayList<String> combined = new ArrayList<>();
		combined.addAll(firstArr);
		combined.addAll(secondArr);
		return createDocument(combined);
	}
	
	public static Document createDocument(File aFile) {
		ArrayList<String> contents = getContents(aFile);
		return new Document(getAuthor(contents),getLastTimeModification(aFile),
				getCopyright(contents),"Default",getContents(aFile));
	}
	
	//Get name of the file -> remove .tex -> add
	public String getTemplateID(File aFile) {
		return aFile.getName().replace(".tex","");
	}

	// Bad way
	public static String getTypeOfLatexDocument(ArrayList<String> currentVersion) {
		for(String i : currentVersion) {
			if(i.trim().contains("\\documentclass")) {
				try {
					//System.out.println(i.trim().substring(i.trim().lastIndexOf("{")+1,i.trim().lastIndexOf("}")));
					return i.trim().substring(i.trim().lastIndexOf("{")+1,i.trim().lastIndexOf("}"));
				} catch (Exception e) {
					return "Unspecified";
				}
			}
		}
		return "Unspecified";
	}
	
	public static ArrayList<String> getContents(File aFile) {
		ArrayList<String> contents = new ArrayList<>();
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(aFile));
			while ((line = br.readLine()) != null) {
				contents.add(line);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Document -> getContents -> ");
			e.printStackTrace();
		}
		return contents;
	}
	
	// @TODO Find a better way to get authors.The \\\\ thing will work only on Windows.
	// Running this to Linux probably needs just 2.It looks more like a patch than a solution.
	// Also there is templateID which i can use to categorize ...
	public static String getAuthor(ArrayList<String> contents) {
		for(String i : contents){
			if(i.contains("\\signature")) {
				return i.replace("\\signature{","").replace("}","");
			}
			if(i.contains("author{")){
				return String.join(",",i.replace("\\author{", "").replace("}", "").split("\\\\and"));
			}
		}
		return "Unknown";
	}


	public static String getCopyright(ArrayList<String> content) {
		String emptyCopyright = "Empty";
		for(String i : content){
			if(i.contains("\\encl") && i.contains("{Copyright")){
				return i.replace("\\encl{","").replace("}","");
			}
		}
		return emptyCopyright;
	}

	// nameOfDay Month Day hh:mm:ss zone yyyy
	public static String getLastTimeModification(File aFile) {
		return (new Date(aFile.lastModified())).toString();
	}

	public static ArrayList<String> convertStringToArrayList(String text){
		return new ArrayList<String>(
				Arrays.asList(text.split(System.getProperty("line.separator"))));
	}
	
	public static String convertArrayListToString(ArrayList<String> arr) {
		String returnString = "";
		for(String i : arr) {
			returnString = returnString + i + System.getProperty("line.separator");
		}
		return returnString;
	}
	
	public String getPath() {
		return path;
	}
	
	public File[] getFiles() {
		return files;
	}
}