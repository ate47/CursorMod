package fr.atesab.customcursormod.common.handler;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public abstract class GuiUtils {
	public static final CommonSupplier<Void, GuiUtils> SUPPLIER = new CommonSupplier<>(true);
	public static GuiUtils get() {
		return SUPPLIER.fetch();
	}
	private final TranslationCommonText YES = TranslationCommonText.create("cursormod.config.yes");
	private final TranslationCommonText NO = TranslationCommonText.create("cursormod.config.no");
	
	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used
	 * anywhere in vanilla code.
	 */
	public abstract void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight);

	/**
	 * Draws a rectangle with a vertical gradient between the specified colors (ARGB
	 * format). Args : x1, y1, x2, y2, topColor, bottomColor
	 */
	public abstract void drawGradientRect(float zLevel, int left, int top, int right, int bottom, int startColor,
			int endColor);
	public abstract int fontHeight();
	public CommonButton createBooleanButton(CommonText title, int xPosition, int yPosition, int width, int height, BooleanSupplier getter, Consumer<Boolean> setter) {
		boolean value = getter.getAsBoolean();

		CommonText yes = title.copy().append(": ").append(YES);
		CommonText no = title.copy().append(": ").append(NO);

		CommonText msg = value ? yes : no;

		return CommonButton.create(msg, xPosition, yPosition, width, height, b -> {
			boolean v = !getter.getAsBoolean();
			setter.accept(v);
			b.setMessage(v ? yes : no);
		});
	}
		
}
