package fr.atesab.customcursormod.common.cursor;

import fr.atesab.customcursormod.common.config.CursorConfig;
import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;

/**
 * a basic cursor type, use {@link ForgeCursorMod#registerCursor(CursorType...)} to
 * register a {@link CursorType}
 * 
 * @author ATE47
 */
public class CursorType {

	/**
	 * the pointer (nothing happen or no dynamic cursor)
	 */
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
	private TranslationCommonText localizedName;

	private String configName;
	private CursorTester cursorTester;
	private int animationDelays;

	public CursorType(String configName, String localizedName, CursorConfig defaultConfig) {
		this(configName, localizedName, defaultConfig, null);
	}

	public CursorType(String configName, String localizedName, CursorConfig defaultConfig, CursorTester cursorTester) {
		this(configName, localizedName, defaultConfig, cursorTester, 100);
	}

	public CursorType(String configName, String localizedName, CursorConfig defaultConfig, CursorTester cursorTester,
			int animationDelays) {
		this.configName = configName;
		this.localizedName = TranslationCommonText.create(localizedName);
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
		return localizedName.getKey();
	}

	public String getName() {
		return localizedName.getString();
	}

	public CommonText getTranslation() {
		return localizedName; 
	}
}
