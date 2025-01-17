package controller.commands;

import java.util.ArrayList;
import java.util.Arrays;

import model.strategies.VersionsManager;

public class CommandsFactory {
	
	public static Command createCommands(String command) {
		switch (command) {
			case "CreateCommand":
				return new CreateCommand();
			case "AddLatexCommand":
				return new AddLatexCommand();
			case "EditCommand":
				return new EditCommand();
			case "SaveCommand":
				return new SaveCommand();
			case "LoadCommand":
				return new LoadCommand();
			case "RollBackCommand":
				return new RollBackCommand();
			case "ChangeStrategyCommand":
				return new ChangeStrategyCommand();
			default:
				System.err.println("createCommands default -> null");
				return null;
		}
	}

	public static Command createCommands(String command,VersionsManager vm,String firstString,String secondString) {
		switch(command) {
			case "CreateCommand":
				return new CreateCommand(vm,firstString,secondString);
			case "EditCommand":
				return new EditCommand(vm,firstString,secondString);
			case "AddLatexCommand":
				return new AddLatexCommand(vm,firstString,secondString);
				//return new AddLatexCommand(vm,firstString,secondString);
			case "SaveCommand":
				return new SaveCommand(vm,firstString,
						new ArrayList<>(Arrays.asList(secondString.split(System.getProperty("line.separator")))));
			case "LoadCommand":
				return new LoadCommand(vm,firstString);
			case "RollBackCommand":
				return new RollBackCommand(vm);
			case "ChangeStrategyCommand":
				return new ChangeStrategyCommand(vm,firstString);
			default:
				System.err.println("createCommands -> null");
				return null;
		}
	}
}
