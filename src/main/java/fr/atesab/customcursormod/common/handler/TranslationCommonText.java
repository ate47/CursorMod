package fr.atesab.customcursormod.common.handler;

public abstract class TranslationCommonText extends CommonTextAppendable {
	public static class TranslationObject {
		public final String format;
		public final Object[] args;
		public TranslationObject(String format, Object[] args) {
			this.format = format;
			this.args = args;
		}
	}
	public static final CommonSupplier<TranslationObject, TranslationCommonText> SUPPLIER = new CommonSupplier<>(false);
	public static TranslationCommonText create(String format, Object... args) {
		return SUPPLIER.fetch(new TranslationObject(format, args));
	}
	public abstract String getKey();
}
