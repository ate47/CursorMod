package fr.atesab.customcursormod.common.handler;

public abstract class CommonTextAppendable extends CommonText {
	public abstract CommonTextAppendable append(CommonText text);
	public CommonTextAppendable append(String text) {
		return append(StringCommonText.create(text));
	}

}
