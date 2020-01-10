package fr.atesab.customcursormod.gui;

import net.minecraft.client.gui.IGuiEventListener;

public class GuiSelectZone implements IGuiEventListener {
	public int xPosition;
	public int yPosition;
	public int width;
	public int height;
	public boolean enable = true;
	public GuiSelectZone(int xPosition, int yPosition, int width, int height) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}
}
