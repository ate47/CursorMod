package fr.atesab.customcursormod.common.handler;

public abstract class StringCommonText extends CommonTextAppendable {
	
	public static final CommonSupplier<String, StringCommonText> SUPPLIER = new CommonSupplier<>(false);
	public static StringCommonText create(String text) {
		return SUPPLIER.fetch(text);
	}
}
