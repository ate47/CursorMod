package fr.atesab.customcursormod.fabric.gui;

import fr.atesab.customcursormod.common.cursor.SelectZone;
import net.minecraft.client.gui.Element;

public class FabricGuiSelectZone extends SelectZone implements Element {
	private int xPosition;
	private int yPosition;
	private int width;
	private int height;
	private boolean enable = true;

	public FabricGuiSelectZone(int xPosition, int yPosition, int width, int height) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the xPosition
	 */
	@Override
	public int getXPosition() {
		return xPosition;
	}

	/**
	 * @return the yPosition
	 */
	@Override
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * @return the height
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	@Override
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	@Override
	public boolean charTyped(char key, int modifier) {
		return Element.super.charTyped(key, modifier);
	}
	@Override
	public boolean keyPressed(int key, int scan, int modifier) {
		return Element.super.keyPressed(key, scan, modifier);
	}
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return Element.super.mouseClicked(mouseX, mouseY, mouseButton);
	}

}
