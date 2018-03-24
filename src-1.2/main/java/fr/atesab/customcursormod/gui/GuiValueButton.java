package fr.atesab.customcursormod.gui;

import net.minecraft.client.gui.GuiButton;

public class GuiValueButton<T> extends GuiButton {
	private T Value;

	public GuiValueButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, T value) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		Value = value;
	}

	public GuiValueButton(int buttonId, int x, int y, String buttonText, T value) {
		super(buttonId, x, y, buttonText);
		Value = value;
	}

	public T getValue() {
		return Value;
	}

	public void setValue(T value) {
		Value = value;
	}
}
