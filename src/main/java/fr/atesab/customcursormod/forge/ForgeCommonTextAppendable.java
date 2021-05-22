package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.CommonTextAppendable;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;

public class ForgeCommonTextAppendable extends CommonTextAppendable {

	private IFormattableTextComponent handle;
	public ForgeCommonTextAppendable(IFormattableTextComponent handle) {
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
		return new ForgeCommonTextAppendable(handle.append(text.<ITextComponent>getHandle()));
	}
	
}
