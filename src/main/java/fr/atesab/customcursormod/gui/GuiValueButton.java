package fr.atesab.customcursormod.gui;

import net.minecraft.client.gui.widget.button.AbstractButton;

public abstract class GuiValueButton<T> extends AbstractButton {
	private T Value;

	public GuiValueButton(int x, int y, int widthIn, int heightIn, String buttonText, T value) {
		super(x, y, widthIn, heightIn, buttonText);
		Value = value;
	}

	public GuiValueButton(int buttonId, int x, int y, String buttonText, T value) {
		super(x, y, 200, 20, buttonText);
		Value = value;
	}

	public T getValue() {
		return Value;
	}

	public void setValue(T value) {
		Value = value;
	}
}
