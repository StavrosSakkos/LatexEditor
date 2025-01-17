package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.commands.ChangeStrategyCommand;
import controller.commands.CreateCommand;
import controller.commands.EditCommand;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VersionsStrategyFactory;
import util.PathUtilization;

class ChangeStrategyTest {

	@Test
	void test() {
		VersionsStrategy strat = VersionsStrategyFactory.createStrategy("Volatile");
		VersionsManager vm = new VersionsManager(strat);
		String newText = "new text";
		
		CreateCommand cC = new CreateCommand(vm,"TestArticleStrategy");
		cC.execute();
		
		EditCommand editCommand = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestArticleStrategy.tex",newText);
		editCommand.execute();
		ArrayList<String> contentVolatile = editCommand.getArrayListBasedOnString(newText);
		
		EditCommand editCommandLetter = new EditCommand(vm,
				PathUtilization.OUTPUT_FOLDER_PATH+"TestArticleStrategy.tex","final");
		editCommandLetter.execute();
		
		ChangeStrategyCommand cS = new ChangeStrategyCommand(vm,"Stable");
		cS.execute();
		
		assertArrayEquals(contentVolatile.toArray(),
				strat.getEntireHistory().get(0).getContent().toArray());
		assertArrayEquals(contentVolatile.toArray(),
				strat.getEntireHistory().get(0).getContent().toArray());
		assertEquals("Stable",cS.getStrategy());
	}

}
