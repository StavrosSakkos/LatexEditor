package controller.commands;

import model.strategies.VersionsManager;

public class RollBackCommand implements Command {
	
	VersionsManager versionsManager;
	
	public RollBackCommand() {}
	
	public RollBackCommand(VersionsManager versionsManager) {
		this.versionsManager = versionsManager;
	}

	public void execute() {
		versionsManager.rollBackToPrevVersion();
	}
	
}
