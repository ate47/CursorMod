package fr.atesab.customcursormod.common.utils;

import fr.atesab.customcursormod.common.handler.CommonSupplier;
import fr.atesab.customcursormod.common.handler.TranslationCommonText.TranslationObject;

public class I18n {
	public static final CommonSupplier<TranslationObject, String> SUPPLIER = new CommonSupplier<>(false);
	public static String get(String format, Object... args) {
		return SUPPLIER.fetch(new TranslationObject(format, args));
	}
	private I18n() {
	}
}
