package controller.commands;

import java.util.ArrayList;

import model.Document;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategyFactory;

public class ChangeStrategyCommand implements Command{

	private String strategy = "";
	private VersionsManager versionsManager;
	
	public ChangeStrategyCommand() {}
	
	public ChangeStrategyCommand(VersionsManager versionsManager,String strategy) {
		this.versionsManager = versionsManager;
		this.strategy = strategy;
	}
	
	public void execute() {
		ArrayList<Document> history = new ArrayList<Document>(versionsManager.getStrategy().getEntireHistory());
		switch (strategy) {
			case "Stable":
				versionsManager.saveHistoryToStorage();
				versionsManager.setStrategy(VersionsStrategyFactory.createStrategy("Stable"));
				versionsManager.getStrategy().setEntireHistory(history);
				break;
			case "Volatile":
				//ArrayList<Document> history = versionsManager.getStrategy().getEntireHistory();
				versionsManager.setStrategy(VersionsStrategyFactory.createStrategy("Volatile"));
				versionsManager.getStrategy().setEntireHistory(history);
				break;
			default:
				break;
		}
	}
	
	public String getStrategy() {
		return strategy;
	}
}