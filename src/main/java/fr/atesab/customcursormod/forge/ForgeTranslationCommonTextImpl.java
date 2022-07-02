package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.CommonTextAppendable;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ForgeTranslationCommonTextImpl extends TranslationCommonText {

	private final MutableComponent handle;

	public ForgeTranslationCommonTextImpl(String text, Object... args) {
		handle = Component.translatable(text, args);
	}

	public ForgeTranslationCommonTextImpl(MutableComponent handle) {
		this.handle = handle;
	}

	@Override
	public String getString() {
		return handle.getString();
	}

	@Override
	public String getKey() {
		if (handle.getContents() instanceof TranslatableContents content) {
			return content.getKey();
		}
		throw new Error("Key element wasn't a translatable content");
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