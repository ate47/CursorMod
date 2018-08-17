package fr.atesab.customcursormod;

import net.minecraft.client.resources.I18n;

public class CursorType {

	public static final CursorType POINTER = new CursorType("pointer", "cursormod.cursor.pointer",
			new CursorConfig("textures/gui/customcursor.png"));

	public static final CursorType BEAM = new CursorType("beam", "cursormod.cursor.beam",
			new CursorConfig("textures/gui/customcursor_beam.png", 16, 16));

	public static final CursorType HAND = new CursorType("hand", "cursormod.cursor.hand",
			new CursorConfig("textures/gui/customcursor_hand.png", 9, 1));

	public static final CursorType HAND_GRAB = new CursorType("hand_grab", "cursormod.cursor.hand_grab",
			new CursorConfig("textures/gui/customcursor_hand_grab.png", 14, 7));

	public static final CursorType CROSS = new CursorType("cross", "cursormod.cursor.cross",
			new CursorConfig("textures/gui/customcursor_cross.png", 16, 16));

	private CursorConfig defaultConfig;

	private String localizedName;
	private String configName;

	private CursorTester cursorTester;
	private int animationDelays;
	public CursorType(String configName, String localizedName, CursorConfig defaultConfig) {
		this(configName, localizedName, defaultConfig, null);
	}
	public CursorType(String configName, String localizedName, CursorConfig defaultConfig, CursorTester cursorTester) {
		this(configName, localizedName, defaultConfig, cursorTester, 100);
	}
	public CursorType(String configName, String localizedName, CursorConfig defaultConfig, CursorTester cursorTester, int animationDelays) {
		this.configName = configName;
		this.localizedName = localizedName;
		this.defaultConfig = defaultConfig;
		this.cursorTester = cursorTester;
		this.animationDelays = animationDelays;
	}
	public int getAnimationDelays() {
		return animationDelays;
	}
	public String getConfigName() {
		return configName;
	}

	public CursorTester getCursorTester() {
		return cursorTester;
	}

	public CursorConfig getDefaultConfig() {
		return defaultConfig.copy();
	}

	public String getLocalizedName() {
		return localizedName;
	}

	public String getName() {
		return I18n.format(localizedName);
	}

	@Override
	public String toString() {
		return getConfigName();
	}
}
