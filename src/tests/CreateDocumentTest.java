package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.commands.CreateCommand;
import model.DocumentManager;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;
import util.PathUtilization;


// Note : /output/ folder should be empty ... or not.

/* To test the creation of a new Latex document we need 5 acceptance 
 * tests, one for each different template plus one test that considers the 
 * case  where  the  user  does  not  specify  a  template.  An  idea  for 
 * implementing the tests is to create a CreateCommand object, execute 
 * it and  compare the contents of the newly created document with the 
 * expected contents of the selected template.  
*/

class CreateDocumentTest {

	@Test
	public void createArticle() {

		DocumentManager docMan = new DocumentManager();
		DocumentManager testDoc;
		VersionsManager vm = new VersionsManager(VersionsStrategyFactory.createStrategy("Stable"));
		CreateCommand createArticle = new CreateCommand(vm,"Article","TestArticle");
		createArticle.execute();
		
		CreateCommand createBook = new CreateCommand(vm,"Book","TestBook");
		createBook.execute();

		CreateCommand createLetter = new CreateCommand(vm,"Letter","TestLetter");
		createLetter.execute();
		
		CreateCommand createReport = new CreateCommand(vm,"Report","TestReport");
		createReport.execute();
		
		CreateCommand createEmptyDocument = new CreateCommand(vm,"TestEmpty");
		createEmptyDocument.execute();
		
		testDoc = new DocumentManager(PathUtilization.OUTPUT_FOLDER_PATH);
		assertArrayEquals(docMan.getMapOfPrototypes().get("Article").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestArticle").getContent().toArray());
		assertArrayEquals(docMan.getMapOfPrototypes().get("Book").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestBook").getContent().toArray());
		assertArrayEquals(docMan.getMapOfPrototypes().get("Letter").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestLetter").getContent().toArray());
		assertArrayEquals(docMan.getMapOfPrototypes().get("Report").getContent().toArray(),
				testDoc.getMapOfPrototypes().get("TestReport").getContent().toArray());
		assertArrayEquals((new ArrayList<String>()).toArray(),
				testDoc.getMapOfPrototypes().get("TestEmpty").getContent().toArray());
	}

}
