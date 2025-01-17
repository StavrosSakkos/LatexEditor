package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.commands.CreateCommand;
import controller.commands.EditCommand;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;
import util.FileParser;
import util.PathUtilization;

class EditDocumentTest {

	@Test
	public void test() {
		VersionsManager vm = new VersionsManager(VersionsStrategyFactory.createStrategy("Stable"));
		String letterContents;
		String newText = "new next";

		//----------------------------------------------------------------------------------------
		CreateCommand createEmptyDocument = new CreateCommand(vm,"TestEmptyEdit");
		createEmptyDocument.execute();
		
		CreateCommand createDocument = new CreateCommand(vm,"Letter","TestLetterEdit");
		createDocument.execute();
		
		CreateCommand createLetter = new CreateCommand(vm,"Letter","TestLetterEditExt");
		createLetter.execute();
		letterContents = FileParser.convertArrayListToString(createLetter.getCreatedDocument().getContent());
		
		//----------------------------------------------------------------------------------------
		EditCommand editCommand = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestEmptyEdit.tex",newText);
		editCommand.execute();
		ArrayList<String> contents = editCommand.getArrayListBasedOnString(newText);
		
		EditCommand editCommandLetter = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestLetterEdit.tex",newText);
		editCommandLetter.execute();
		
		EditCommand editCommandLetterExt = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestLetterEditExt.tex",
				letterContents + newText);
		editCommandLetterExt.execute();
		ArrayList<String> contentsExt = editCommand.getArrayListBasedOnString(letterContents+newText);
		//----------------------------------------------------------------------------------------
		
		assertArrayEquals(FileParser.createDocument(
				new File(PathUtilization.OUTPUT_FOLDER_PATH+"TestEmptyEdit.tex")).getContent().toArray(),
				contents.toArray());
		assertArrayEquals(FileParser.createDocument(
				new File(PathUtilization.OUTPUT_FOLDER_PATH+"TestLetterEdit.tex")).getContent().toArray(),
				contents.toArray());
		assertArrayEquals(FileParser.createDocument(
				new File(PathUtilization.OUTPUT_FOLDER_PATH+"TestLetterEditExt.tex")).getContent().toArray(),
				contentsExt.toArray());
	}
}
