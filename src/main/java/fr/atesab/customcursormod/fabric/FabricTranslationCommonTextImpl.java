package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

public class FabricTranslationCommonTextImpl extends TranslationCommonText {

	private final MutableText handle;
	public FabricTranslationCommonTextImpl(String text, Object... args) {
		this(Text.translatable(text, args));
	}
	public FabricTranslationCommonTextImpl(MutableText handle) {
		this.handle = handle;
		if (!(handle.getContent() instanceof TranslatableTextContent)) {
			throw new IllegalArgumentException("not a translatable text!");
		}
	}


	@Override
	public String getString() {
		return handle.getString();
	}

	@Override
	public String getKey() {
		if (handle.getContent() instanceof TranslatableTextContent content) {
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
	public FabricTranslationCommonTextImpl copy() {
		return new FabricTranslationCommonTextImpl(this.handle.copy());
	}

	@Override
	public FabricCommonTextAppendable append(CommonText text) {
		return new FabricCommonTextAppendable(this.handle.append(text.<Text>getHandle()));
	}
}
