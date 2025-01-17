package model.strategies;

public class VersionsStrategyFactory {
	
	public static VersionsStrategy createStrategy(String typeOfStrategy) {
		switch (typeOfStrategy) {
			case "Stable":
				return new StableVersionsStrategy();
			case "Volatile":
				return new VolatileVersionsStrategy();
			default:
				System.err.println("VersionsStrategyFactory->createStrategy:"
						+ "Unknown type of strategy: "+typeOfStrategy);
				return null;
		}
	}
	
}
