package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.CommonTextAppendable;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ForgeTranslationCommonTextImpl extends TranslationCommonText {

	private TranslationTextComponent handle;

	public ForgeTranslationCommonTextImpl(String text, Object... args) {
		handle = new TranslationTextComponent(text, args);
	}

	public ForgeTranslationCommonTextImpl(TranslationTextComponent handle) {
		this.handle = handle;
	}

	@Override
	public String getString() {
		return handle.getString();
	}

	@Override
	public String getKey() {
		return handle.getKey();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getHandle() {
		return (T) handle;
	}

	@Override
	public ForgeCommonTextAppendable copy() {
		return new ForgeCommonTextAppendable(this.handle.copy());
	}

	@Override
	public CommonTextAppendable append(CommonText text) {
		return new ForgeCommonTextAppendable(this.handle.append(text.<IFormattableTextComponent>getHandle()));
	}
}