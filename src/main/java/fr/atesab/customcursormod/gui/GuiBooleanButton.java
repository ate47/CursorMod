package fr.atesab.customcursormod.gui;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.resources.I18n;

/**
 * Boolean value button setter with color and text (for color blind)
 */
public class GuiBooleanButton extends AbstractButton {
	private Consumer<Boolean> setter;
	private Supplier<Boolean> getter;
	private String text;

	public GuiBooleanButton(int x, int y, int widthIn, int heightIn, String buttonText, Consumer<Boolean> setter,
			Supplier<Boolean> getter) {
		super(x, y, widthIn, heightIn, buttonText);
		this.setter = setter;
		this.getter = getter;
		this.text = buttonText;
		updateDisplay();
	}

	public GuiBooleanButton(int x, int y, String buttonText, Consumer<Boolean> setter, Supplier<Boolean> getter) {
		this(x, y, 200, 20, buttonText, setter, getter);
	}

	private void updateDisplay() {
		boolean v = getter.get();
		packedFGColor = v ? 0xff37ff37 : 0xffff3737;
		setMessage(text + " : " + I18n.format(v ? "cursormod.config.yes" : "cursormod.config.no"));
	}

	@Override
	public void onPress() {
		boolean newValue = !getter.get();
		setter.accept(newValue);
		updateDisplay();
	}

}
