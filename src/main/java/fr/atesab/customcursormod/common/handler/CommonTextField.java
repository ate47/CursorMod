package fr.atesab.customcursormod.common.handler;

public abstract class CommonTextField implements CommonElement {
	public static class CommonTextFieldObject {
		public final String value;
		public final int xPosition;
		public final int yPosition;
		public final int width;
		public final int height;

		private CommonTextFieldObject(String value, int xPosition, int yPosition, int width, int height) {
			this.value = value;
			this.xPosition = xPosition;
			this.yPosition = yPosition;
			this.width = width;
			this.height = height;
		}
	}
	public static final CommonSupplier<CommonTextFieldObject, CommonTextField> SUPPLIER = new CommonSupplier<>(false);

	public static CommonTextField create(String value, int xPosition, int yPosition, int width, int height) {
		return SUPPLIER.fetch(new CommonTextFieldObject(value, xPosition, yPosition, width, height));
	}

	public abstract void setValue(String value);
	public abstract String getValue();
	public abstract void setMaxLength(int length);
	public abstract void setTextColor(int color);
}
