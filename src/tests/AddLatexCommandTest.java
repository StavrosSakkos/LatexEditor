package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.commands.AddLatexCommand;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;

// NOTE: The text remains "" if there are limitations.
// This is an old (1st Release) JUnit test.

class AddLatexCommandTest {

	@Test
	public void test() {
		VersionsManager vm = new VersionsManager(VersionsStrategyFactory.createStrategy("Volatile"));
		String nL = System.getProperty("line.separator");
		String textArea = "\\documentclass[11pt,twocolumn,a4paper]{article}"+nL+ 
				"\\usepackage{graphicx}"+nL
				+nL + 
				"\\begin{document}"+nL
				+nL + 
				"\\title{Article: How to Structure a LaTeX Document}"+nL + 
				"\\author{Author1 \\and Author2 \\and ...}"+nL + 
				"\\date{\\today}"+nL 
				+nL + 
				"\\maketitle"+nL
				+nL + 
				"\\section{Section Title 1}"+nL 
				+nL + 
				"\\section{Section Title 2}"+nL
				+nL + 
				"\\section{Section Title.....}"+nL 
				+nL + 
				"\\section{Conclusion}"+nL 
				+nL + 
				"\\section*{References}"+nL 
				+nL + 
				"\\end{document}";
		
		AddLatexCommand chapter = new AddLatexCommand(vm,"chapter",textArea);
		AddLatexCommand section = new AddLatexCommand(vm,"section",textArea);
		AddLatexCommand subsection = new AddLatexCommand(vm,"subsection",textArea);
		AddLatexCommand subsubsection = new AddLatexCommand(vm,"subsubsection",textArea);
		AddLatexCommand addEnumerationListI = new AddLatexCommand(vm,"addEnumerationListI",textArea);
		AddLatexCommand addEnumerationListE = new AddLatexCommand(vm,"addEnumerationListE",textArea);
		AddLatexCommand addTableT = new AddLatexCommand(vm,"addTableT",textArea);
		AddLatexCommand addTableF = new AddLatexCommand(vm,"addTableF",textArea);
		
		chapter.execute();
		section.execute();
		subsection.execute();
		subsubsection.execute();
		addEnumerationListI.execute();
		addEnumerationListE.execute();
		addTableT.execute();
		addTableF.execute();
		
		assertEquals(chapter.getText(),"");
		assertEquals(section.getText(),section.getCommand("section"));
		assertEquals(subsection.getText(),section.getCommand("subsection"));
		assertEquals(subsubsection.getText(),section.getCommand("subsubsection"));
		assertEquals(addEnumerationListI.getText(),section.getCommand("addEnumerationListI"));
		assertEquals(addEnumerationListE.getText(),section.getCommand("addEnumerationListE"));
		assertEquals(addTableT.getText(),section.getCommand("addTableT"));
		assertEquals(addTableF.getText(),section.getCommand("addTableF"));
	
	}

}
