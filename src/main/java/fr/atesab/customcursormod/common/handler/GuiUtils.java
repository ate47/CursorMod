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
	 * 
	 * @param x          x location
	 * @param y          y location
	 * @param u          x uv location
	 * @param v          y uv location
	 * @param uWidth     uv width
	 * @param vHeight    uv height
	 * @param width      width
	 * @param height     height
	 * @param tileWidth  tile width
	 * @param tileHeight tile height
	 */
	public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight) {
		drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight, 0xffffff);
	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used
	 * anywhere in vanilla code.
	 * 
	 * @param x          x location
	 * @param y          y location
	 * @param u          x uv location
	 * @param v          y uv location
	 * @param uWidth     uv width
	 * @param vHeight    uv height
	 * @param width      width
	 * @param height     height
	 * @param tileWidth  tile width
	 * @param tileHeight tile height
	 * @param color      tile color
	 */
	public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight, int color) {
		drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight, color, false);
	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used
	 * anywhere in vanilla code.
	 * 
	 * @param x          x location
	 * @param y          y location
	 * @param u          x uv location
	 * @param v          y uv location
	 * @param uWidth     uv width
	 * @param vHeight    uv height
	 * @param width      width
	 * @param height     height
	 * @param tileWidth  tile width
	 * @param tileHeight tile height
	 * @param color      tile color
	 * @param useAlpha   use the alpha of the color
	 */
	public abstract void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight,
			int width, int height, float tileWidth, float tileHeight, int color, boolean useAlpha);

	public void drawGradientRect(CommonMatrixStack stack, float zLevel, int left, int top, int right, int bottom,
			int startColor, int endColor) {
		drawGradientRect(stack, left, top, right, bottom, startColor, endColor, zLevel);
	}

	/**
	 * Draw a rectangle with a vertical gradient
	 * 
	 * @param stack      matrix stack
	 * @param left       left location
	 * @param top        top location
	 * @param right      right location
	 * @param bottom     bottom location
	 * @param startColor startColor color
	 * @param endColor   endColor color
	 * @param zLevel     zLevel of the screen
	 * 
	 * @see #drawGradientRect(PoseStack, int, int, int, int, int, int, int, int,
	 *      float)
	 * @since 2.0
	 */
	public void drawGradientRect(CommonMatrixStack stack, int left, int top, int right, int bottom, int startColor,
			int endColor, float zLevel) {
		drawGradientRect(stack, left, top, right, bottom, startColor, startColor, endColor, endColor, zLevel);
	}

	/**
	 * Draw a gradient rectangle
	 * 
	 * @param stack            matrix stack
	 * @param left             left location
	 * @param top              top location
	 * @param right            right location
	 * @param bottom           bottom location
	 * @param rightTopColor    rightTopColor color (ARGB)
	 * @param leftTopColor     leftTopColor color (ARGB)
	 * @param leftBottomColor  leftBottomColor color (ARGB)
	 * @param rightBottomColor rightBottomColor color (ARGB)
	 * @param zLevel           zLevel of the screen
	 * 
	 * @see #drawGradientRect(PoseStack, int, int, int, int, int, int, float)
	 * @since 2.0
	 */
	public abstract void drawGradientRect(CommonMatrixStack stack, int left, int top, int right, int bottom,
			int rightTopColor, int leftTopColor, int leftBottomColor, int rightBottomColor, float zLevel);

	public abstract int fontHeight();

	public abstract void setShaderColor(float r, float g, float b, float a);

	public void setShaderColor(float r, float g, float b) {
		setShaderColor(r, g, b, 1);
	}

	public abstract void setShader(CommonShader shader);

	public CommonButton createBooleanButton(CommonText title, int xPosition, int yPosition, int width, int height,
			BooleanSupplier getter, Consumer<Boolean> setter) {
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
