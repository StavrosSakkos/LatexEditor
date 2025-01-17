package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.commands.CommandsFactory;
import controller.commands.SaveCommand;
import model.DocumentManager;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;
import util.PathUtilization;

//Note: textArea is a template
//This is an old (1st Release) JUnit test.

class SaveDocumentTest {

	@Test
	public void test() {
		String nL = System.getProperty("line.separator");
		VersionsManager vm = new VersionsManager(VersionsStrategyFactory.createStrategy("Stable"));
		DocumentManager docMan = new DocumentManager();
		DocumentManager testDoc;
		String textArea = "\\documentclass[11pt,twocolumn,a4paper]{article}"+nL + 
				"\\usepackage{graphicx}"+nL + 
				nL + 
				"\\begin{document}"+nL + 
				nL + 
				"\\title{Article: How to Structure a LaTeX Document}"+nL + 
				"\\author{Author1 \\and Author2 \\and ...}"+nL + 
				"\\date{\\today}"+nL + 
				nL + 
				"\\maketitle"+nL + 
				nL + 
				"\\section{Section Title 1}"+nL + 
				nL + 
				"\\section{Section Title 2}"+nL + 
				nL + 
				"\\section{Section Title.....}"+nL + 
				nL + 
				"\\section{Conclusion}"+nL + 
				nL + 
				"\\section*{References}"+nL + 
				nL + 
				"\\end{document}";
		
		//SaveCommand saveArticle = new SaveCommand(vm,"SaveArticleTest",new ArrayList<>(Arrays.asList(textArea.split("\\n"))));
		SaveCommand saveArticle = (SaveCommand) CommandsFactory.createCommands("SaveCommand",vm,"SaveArticleTest",textArea);
		saveArticle.execute();
		
		testDoc = new DocumentManager(PathUtilization.OUTPUT_FOLDER_PATH);
		assertArrayEquals(docMan.getMapOfPrototypes().get("Article").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("SaveArticleTest").getContent().toArray());
	}

}
