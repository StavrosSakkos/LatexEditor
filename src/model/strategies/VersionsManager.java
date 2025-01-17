package model.strategies;

import model.Document;

public class VersionsManager {

	private boolean enabled = true;
	private Document currentDocument;
	private VersionsStrategy strategy;
	private int pointer;
	
	public VersionsManager(VersionsStrategy strategy) {
		this.strategy = strategy;
		currentDocument = new Document();
		pointer = strategy.getEntireHistory().size()-1;
	}

	public void toggleTracking() {
		enabled = enabled ? false : true ;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public boolean canIRollBack() {
		return !(strategy.getEntireHistory().size() == 0 || pointer < 1) ;
	}
	
	public void rollBackToPrevVersion() {
		if(strategy.getEntireHistory().size() == 0 || pointer < 1) {
			return;
		}
		currentDocument = strategy.getEntireHistory().get(pointer-1);
		pointer --;
		//System.out.println(currentDocument.getContent());
	}
	
	//Not the best way to keep these Documents saved because switching strategies will
	//create a lot of files without those being ,at least, named.
	public void saveHistoryToStorage() {
		for(Document i: strategy.getEntireHistory()) {
			if(!i.getContent().isEmpty()) {
				i.save("");
			}
		}
	}
	
	public void setCurrentVersion(Document doc) {
		currentDocument = (Document) doc.clone();
		strategy.putVersion(doc);
		pointer = (pointer == strategy.getEntireHistory().size() -1) ?
				pointer + 1 : strategy.getEntireHistory().size()-1;
	}
	
	//After rollback -> if we edit,we have to keep the last version.
	//In this case,that last version of the document is already "saved" as currentDocument
	//I did very basic tests in order to make it work so...
	public void putDocumentAfterEdit() {
		if(pointer < strategy.getEntireHistory().size()-1) {
			strategy.putVersion(currentDocument);
		}
	}
	
	public Document getPreviousVersion() {
		if (strategy.getEntireHistory().size() == 1) {
			return strategy.getEntireHistory().get(0);
		}
		return strategy.getEntireHistory().get(strategy.getEntireHistory().size()-1);
	}
	
	public boolean isStableVersion() {
		return strategy.getClass().getSimpleName().contentEquals("StableVersionsStrategy");
	}
	
	public void setStrategy(VersionsStrategy strategy) {
		this.strategy = strategy;
	}

	
	public VersionsStrategy getStrategy() {
		return strategy;
	}
	
	public Document getCurrentDocument() {
		return currentDocument;
	}

	/*
	// Apparently it needs changes in order to check the correct files,so this version of the method
	// is avoided.
	public Document getPreviousVersion() {
		for(int i = strategy.getEntireHistory().size()-1; i >= 0; i--) {
			if (strategy.getEntireHistory().get(i).isSameDocument(currentDocument)
					&& i >=1) {
				return strategy.getEntireHistory().get(i);
			}
		}
		if (strategy.getEntireHistory().size() == 1) {
			return strategy.getEntireHistory().get(0);
		}
		return null;
	}
	*/
}