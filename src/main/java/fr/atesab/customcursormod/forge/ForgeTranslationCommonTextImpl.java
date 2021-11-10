package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.CommonTextAppendable;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ForgeTranslationCommonTextImpl extends TranslationCommonText {

	private TranslatableComponent handle;

	public ForgeTranslationCommonTextImpl(String text, Object... args) {
		handle = new TranslatableComponent(text, args);
	}

	public ForgeTranslationCommonTextImpl(TranslatableComponent handle) {
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
		return new ForgeCommonTextAppendable(this.handle.append(text.<MutableComponent>getHandle()));
	}
}