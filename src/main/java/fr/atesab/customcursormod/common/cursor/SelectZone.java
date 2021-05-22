package fr.atesab.customcursormod.common.cursor;

import fr.atesab.customcursormod.common.handler.CommonElement;
import fr.atesab.customcursormod.common.handler.CommonSupplier;

public abstract class SelectZone implements CommonElement {
	public static class SelectZoneObject {
		public final int xPosition;
		public final int yPosition;
		public final int width;
		public final int height;

		private SelectZoneObject(int xPosition, int yPosition, int width, int height) {
			this.xPosition = xPosition;
			this.yPosition = yPosition;
			this.width = width;
			this.height = height;
		}
	}
	public static final CommonSupplier<SelectZoneObject, SelectZone> SUPPLIER = new CommonSupplier<>(false);

	public static SelectZone create(int xPosition, int yPosition, int width, int height) {
		return SUPPLIER.fetch(new SelectZoneObject(xPosition, yPosition, width, height));
	}
}
