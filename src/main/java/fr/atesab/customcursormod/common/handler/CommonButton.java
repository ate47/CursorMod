package fr.atesab.customcursormod.common.handler;

import java.util.function.Consumer;

public abstract class CommonButton implements CommonElement {
	public static class CommonButtonObject {
		public final CommonText message;
		public final int xPosition;
		public final int yPosition;
		public final int width;
		public final int height;
		public final Consumer<CommonButton> action;

		private CommonButtonObject(CommonText message, int xPosition, int yPosition, int width, int height, Consumer<CommonButton> action) {
			this.message = message;
			this.xPosition = xPosition;
			this.yPosition = yPosition;
			this.width = width;
			this.height = height;
			this.action = action;
		}
	}
	public static final CommonSupplier<CommonButtonObject, CommonButton> SUPPLIER = new CommonSupplier<>(false);

	public static CommonButton create(CommonText message, int xPosition, int yPosition, int width, int height, Consumer<CommonButton> action) {
		return SUPPLIER.fetch(new CommonButtonObject(message, xPosition, yPosition, width, height, action));
	}
	
	public abstract CommonText getMessage();
	public abstract void setMessage(CommonText message);
	public abstract boolean isVisible();
	public abstract void setVisible(boolean visible);
	
}
