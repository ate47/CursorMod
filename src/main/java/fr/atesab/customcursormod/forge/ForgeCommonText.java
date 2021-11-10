package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.common.handler.CommonTextAppendable;
import net.minecraft.network.chat.Component;

public class ForgeCommonText extends CommonText {
	private Component handle;

	public ForgeCommonText(Component handle) {
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
	public CommonTextAppendable copy() {
		return new ForgeCommonTextAppendable(handle.copy());
	}

}
