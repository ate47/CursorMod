package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class FabricTranslationCommonTextImpl extends TranslationCommonText {
	
	private TranslatableText handle;
	public FabricTranslationCommonTextImpl(String text, Object... args) {
		handle = new TranslatableText(text, args);
	}
	public FabricTranslationCommonTextImpl(TranslatableText handle) {
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
