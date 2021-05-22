package fr.atesab.customcursormod.common.handler;

public class UnhandledGameTypeException extends Error {
	private GameType type;
	public UnhandledGameTypeException(GameType type) {
		super(String.valueOf(type));
		this.type = type;
	}
	/**
	 * @return the type
	 */
	public GameType getType() {
		return type;
	}
}
