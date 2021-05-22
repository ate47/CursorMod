package fr.atesab.customcursormod.common.handler;

public class GameType {
	/**
	 * Common mod loader
	 */
	public static final GameType COMMON = new GameType("COMMON");
	/**
	 * Fabric mod loader
	 */
	public static final GameType FABRIC = new GameType("FABRIC");
	/**
	 * Forge mod loader
	 */
	public static final GameType FORGE = new GameType("FORGE");
	private String name;
	public GameType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GameType))
			return false;
		return name.equals(((GameType)obj).name);
	}
}
