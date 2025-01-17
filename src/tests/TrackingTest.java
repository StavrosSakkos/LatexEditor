package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.commands.CreateCommand;
import controller.commands.EditCommand;
import model.DocumentManager;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;
import util.FileParser;
import util.PathUtilization;

//Note: Enable/Disable tracking is a boolean ,located in VersionsManager.
//There arent Enable/Disable commands.The JRadioButton toggles the boolean.
//Default tracking : true.

class TrackingTest {

	@Test
	void test() {
		DocumentManager docMan = new DocumentManager();
		VersionsManager vm = new VersionsManager(VersionsStrategyFactory.createStrategy("Stable"));
		int size = vm.getStrategy().getEntireHistory().size();
		ArrayList<String> newTextArr;
		String newText = "new next";
		
		System.out.println(vm.getStrategy().getEntireHistory().size());
		//-----------------------------------------------------------------------------------------
		CreateCommand createDocumentEnabled = new CreateCommand(vm,"Article","TestArticleTrackingEnabled");
		createDocumentEnabled.execute();
		CreateCommand createDocumentDisabled = new CreateCommand(vm,"Article","TestArticleTrackingDisabled");
		createDocumentDisabled.execute();
		//-----------------------------------------------------------------------------------------
		
		//-----------------------------------------------------------------------------------------
		EditCommand editComWEnabledTracking = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestArticleTrackingEnabled.tex",newText);
		editComWEnabledTracking.execute();
		
		vm.toggleTracking();
		EditCommand editComWDisabledTracking = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestArticleTrackingDisabled.tex",newText);
		editComWDisabledTracking.execute();
		//-----------------------------------------------------------------------------------------
		newTextArr = editComWEnabledTracking.getArrayListBasedOnString(newText);
		
		assertArrayEquals(FileParser.createDocument(
				new File(PathUtilization.OUTPUT_FOLDER_PATH+"TestArticleTrackingEnabled.tex")).getContent().toArray(),
				newTextArr.toArray());
		assertArrayEquals(FileParser.createDocument(
				new File(PathUtilization.OUTPUT_FOLDER_PATH+"TestArticleTrackingDisabled.tex")).getContent().toArray(),
				docMan.getMapOfPrototypes().get("Article").getContent().toArray());
		assertEquals(vm.getStrategy().getEntireHistory().size(),size+1);
	}

}
