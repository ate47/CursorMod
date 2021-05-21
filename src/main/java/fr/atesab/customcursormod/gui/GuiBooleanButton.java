package fr.atesab.customcursormod.gui;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Boolean value button setter with color and text (for color blind)
 */
public class GuiBooleanButton extends AbstractButton {
	private static final TranslationTextComponent CONFIG_YES = new TranslationTextComponent("cursormod.config.yes");
	private static final TranslationTextComponent CONFIG_NO = new TranslationTextComponent("cursormod.config.no");
	private Consumer<Boolean> setter;
	private Supplier<Boolean> getter;
	private ITextComponent text;

	public GuiBooleanButton(int x, int y, int widthIn, int heightIn, ITextComponent buttonText, Consumer<Boolean> setter,
			Supplier<Boolean> getter) {
		super(x, y, widthIn, heightIn, buttonText);
		this.setter = setter;
		this.getter = getter;
		this.text = getMessage();
		updateDisplay();
	}

	public GuiBooleanButton(int x, int y, ITextComponent buttonText, Consumer<Boolean> setter, Supplier<Boolean> getter) {
		this(x, y, 200, 20, buttonText, setter, getter);
	}

	private void updateDisplay() {
		boolean v = getter.get();
		if (v) {
			packedFGColor = 0xff37ff37;
			setMessage(text.copy().append(" : ").append(CONFIG_YES));
		} else {
			packedFGColor = 0xffff3737;
			setMessage(text.copy().append(" : ").append(CONFIG_NO));
		}
	}

	@Override
	public void onPress() {
		boolean newValue = !getter.get();
		setter.accept(newValue);
		updateDisplay();
	}

}
