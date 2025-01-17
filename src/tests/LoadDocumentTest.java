package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.commands.LoadCommand;
import model.DocumentManager;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;
import util.PathUtilization;


// Notes : Run this test AFTER CreateDocumentTest for obvious reasons
// The test will FAIL if there arent files in that specific path

/* To test the creation of a new Latex document we need 5 acceptance 
 * tests, one for each different template plus one test that considers the 
 * case  where  the  user  does  not  specify  a  template.  An  idea  for 
 * implementing the tests is to create a CreateCommand object, execute 
 * it and  compare the contents of the newly created document with the 
 * expected contents of the selected template.  
*/

class LoadDocumentTest {

	@Test
	public void test() {
		VersionsManager versionsManager = new VersionsManager(VersionsStrategyFactory.createStrategy("Stable"));
		DocumentManager docMan = new DocumentManager();
		DocumentManager testDoc;
		LoadCommand loadArticle = new LoadCommand(versionsManager,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestArticle.tex");
		loadArticle.execute();
		
		LoadCommand loadLetter = new LoadCommand(versionsManager,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestLetter.tex");
		loadLetter.execute();
		
		LoadCommand loadReport = new LoadCommand(versionsManager,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestReport.tex");
		loadReport.execute();
		
		LoadCommand loadBook= new LoadCommand(versionsManager,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestBook.tex");
		loadBook.execute();
		
		testDoc = new DocumentManager(PathUtilization.OUTPUT_FOLDER_PATH);
		assertArrayEquals(docMan.getMapOfPrototypes().get("Article").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestArticle").getContent().toArray());
		assertArrayEquals(docMan.getMapOfPrototypes().get("Book").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestBook").getContent().toArray());
		assertArrayEquals(docMan.getMapOfPrototypes().get("Report").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestReport").getContent().toArray());
		assertArrayEquals(docMan.getMapOfPrototypes().get("Letter").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestLetter").getContent().toArray());
	}

}
