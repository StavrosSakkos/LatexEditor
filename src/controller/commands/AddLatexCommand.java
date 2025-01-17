package controller.commands;

import java.util.ArrayList;
import java.util.Arrays;
import model.strategies.VersionsManager;
import util.FileParser;


public class AddLatexCommand implements Command {

	private VersionsManager versionsManager;
	private ArrayList<String> currentVersion;
	private String description;
	private String text;
	@SuppressWarnings("unused")
	private String textOfDocument;
	
	public AddLatexCommand() {}
	
	public AddLatexCommand(VersionsManager versionsManager,String description,String textOfDocument) {
		this.versionsManager = versionsManager;
		this.description = description;
		this.textOfDocument = textOfDocument;
		setContentsBasedOnString(textOfDocument);
		text = "";
	}

	//I wont make this more complicated with given filenames and passing Strings around view-controller-VM-here
	public void execute( ) {
		if(!isAgainstLimitations()) {
			text = getCommand(description);
		}
		//if(versionsManager.isEnabled()) {
		//	versionsManager.setCurrentVersion(FileParser.createDocument(
		//			currentVersion,getArrayListBasedOnString(textOfDocument+text)));
		//}
		if(versionsManager.isStableVersion()) {
			versionsManager.getCurrentDocument().save("");
		}
	}
	
	//addEnumerationList -> I = itemize / E = enumerate
	//addTable -> T = table / F = figure
	public String getCommand(String description) {
		String nL = System.getProperty("line.separator");
		switch(description) {
			case "chapter":
				return "\\chapter{...}"+ nL;
			case "section":
				return "\\section{}"+ nL;
			case "subsection":
				return "\\subsection{}"+ nL;
			case "subsubsection":
				return "\\subsubsection{}"+ nL;
			case "addEnumerationListI":
				return "\\begin{itemize}"+nL+nL+"\\item ..."
						+nL+nL+"\\item..."+nL+nL+"\\end{itemize}"+nL;
			case "addEnumerationListE":
				return "\\begin{enumerate}"+nL+nL+"\\item ..."
				+nL+nL+"\\item..."+nL+nL+"\\end{enumerate}"+nL;
			case "addTableT":
				return "\\begin{table}"+nL+nL+"\\caption{...}\\label{...}"
				+nL+"\t"+"\\line "+nL+nL+"...&...&...\\\\"+"...&...&...\\\\"
				+nL+nL+"...&...&...\\\\"+nL+nL+"\t\\hline"+nL+nL
				+"\\end{tabular}"+nL+nL+"\\end{table}"+nL;
			case "addTableF":
				return "\\begin{figure}"+nL+nL+"\\includegraphics[width=...,height=...]{...}"
				+nL+nL+"\\caption{....}\\label{...}"+nL+nL
				+"\\end{figure}"+nL;
			default:
				return "";
		}
	}
	
	//Note: Letter has a few exceptions,so it might be better to check if its letter instead of checking
	//the command itself
	public boolean isAgainstLimitations() {
		String typeOfDocument = FileParser.getTypeOfLatexDocument(currentVersion);
		if(typeOfDocument.toLowerCase().equals("letter")) {
			return isAgainstLetterLimitations(description);
		}
		if(description.equals("chapter")) {
			return isAgainstChapterLimitations(typeOfDocument);
		}
		return false;
	}

	private boolean isAgainstChapterLimitations(String typeOfDocument) {
		if(typeOfDocument.toLowerCase().equals("letter")
				|| typeOfDocument.toLowerCase().equals("article") ) {
			return true;
		}
		return false;
	}
	
	//Maybe i should create an array/map ... 
	private boolean isAgainstLetterLimitations(String description) {
		if(description.equals("chapter") || description.equals("section") || description.equals("subsection") || 
				description.equals("subsubsection") || description.equals("addEnumerationListI") ||
				description.equals("addEnumerationListE") || description.equals("addTableT") || description.equals("addTableF")) {
			return true;
		}
		return false;
	}
	
	public void setContentsBasedOnString(String text) {
		currentVersion = new ArrayList<>(Arrays.asList(text.split(System.getProperty("line.separator"))));
	}
	
	public ArrayList<String> getArrayListBasedOnString(String text) {
		return new ArrayList<>(Arrays.asList(
				text.split(System.getProperty("line.separator"))));
	}
	
	public String getText() {
		return text;
	}

}
