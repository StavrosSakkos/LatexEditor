package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import controller.commands.CreateCommand;
import controller.commands.EditCommand;
import controller.commands.RollBackCommand;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;
import util.PathUtilization;

//Note : I dont keep the jtextarea as empty in the first edit of the jtextarea,so i need a second one

class RollBackTest {

	@Test
	void test() {
		VersionsManager vm = new VersionsManager(VersionsStrategyFactory.createStrategy("Volatile"));
		String newText = "new text";
		ArrayList<String> newTextArr;
		//----------------------------------------------------------------------------------------
		CreateCommand createDocument = new CreateCommand(vm,"TestEmptyEdit");
		createDocument.execute();

		EditCommand editCommand = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestEmptyEdit.tex",newText);
		editCommand.execute();
		
		EditCommand ec = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestEmptyEdit.tex","final");
		ec.execute();
		
		//----------------------------------------------------------------------------------------

		newTextArr = editCommand.getArrayListBasedOnString(newText);
		RollBackCommand rbCommand = new RollBackCommand(vm);
		rbCommand.execute();
	//	System.out.println(vm.getCurrentDocument().getContent());
		
		assertArrayEquals(vm.getCurrentDocument().getContent().toArray(),
				newTextArr.toArray());
	}

}
