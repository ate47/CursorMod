package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.CommonTextAppendable;
import fr.atesab.customcursormod.common.handler.StringCommonText;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.IFormattableTextComponent;

public class ForgeStringCommonTextImpl extends StringCommonText {

	private StringTextComponent handle;

	public ForgeStringCommonTextImpl(String text) {
		handle = new StringTextComponent(text);
	}

	public ForgeStringCommonTextImpl(StringTextComponent handle) {
		this.handle = handle;
	}

	@Override
	public String getString() {
		return handle.getString();
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
		return new ForgeCommonTextAppendable(handle.append(text.<IFormattableTextComponent>getHandle()));
	}
}