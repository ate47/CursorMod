package fr.atesab.customcursormod.common.handler;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonScreen {
	public static class ScreenListener {
		protected int width;
		protected int height;
		private CommonScreen screen;

		/**
		 * @return the screen
		 */
		public CommonScreen getScreen() {
			return screen;
		}

		public void resize(int width, int height) {
			// To implement
		}

		public void init() {
			// To implement
		}

		public boolean charTyped(char key, int modifier) {
			// To implement
			return false;
		}

		public boolean keyPressed(int key, int scan, int modifier) {
			// To implement
			return false;
		}

		public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
			// To implement
			return false;
		}

		public void tick() {
			// To implement
		}

		public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
			// To implement
		}
	}

	public static class CommonScreenObject {
		public final CommonScreen parent;
		public final CommonText title;
		public final ScreenListener listener;

		public CommonScreenObject(CommonScreen parent, CommonText title, ScreenListener listener) {
			this.title = title;
			this.parent = parent;
			this.listener = listener;
		}
	}

	public static final CommonSupplier<CommonScreenObject, CommonScreen> SUPPLIER = new CommonSupplier<>(false);
	public static final CommonSupplier<Void, CommonScreen> SUPPLIER_CURRENT = new CommonSupplier<>(false);
	private static final ScreenListener NULL_LISTENER = new ScreenListener();

	public static CommonScreen create(CommonScreen parent, CommonText title, ScreenListener listener) {
		return SUPPLIER.fetch(new CommonScreenObject(parent == null ? createNull() : parent, title, listener));
	}

	public static CommonScreen createNull() {
		return create(null, null, NULL_LISTENER);
	}

	public static CommonScreen getCurrent() {
		return SUPPLIER_CURRENT.fetch();
	}

	private CommonScreen parent;
	public final ScreenListener listener;
	public final List<CommonElement> childrens = new ArrayList<>();

	protected CommonScreen(CommonScreen parent, ScreenListener listener) {
		this.parent = parent;
		this.listener = listener == null ? new ScreenListener() : listener;
		// attach the screen to this listener
		this.listener.screen = this;
	}

	protected CommonScreen(CommonScreen parent) {
		this(parent, null);
	}

	public <E extends CommonElement> E addChildren(E child) {
		childrens.add(child);
		return child;
	}

	/**
	 * @return the parent
	 */
	public CommonScreen getParent() {
		return parent == null ? createNull() : parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(CommonScreen parent) {
		this.parent = parent == null ? createNull() : parent;
	}

	public void resize(int width, int height) {
		listener.width = width;
		listener.height = height;
	}

	public void init() {
		childrens.clear();
		listener.init();
	}

	public boolean charTyped(char key, int modifier) {
		if (listener.charTyped(key, modifier))
			return true;
		for (CommonElement e : childrens) {
			if (e.charTyped(key, modifier))
				return true;
		}
		return false;
	}

	public boolean keyPressed(int key, int scan, int modifier) {
		if (listener.keyPressed(key, scan, modifier))
			return true;
		for (CommonElement e : childrens) {
			if (e.keyPressed(key, scan, modifier))
				return true;
		}
		return false;
	}

	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (listener.mouseClicked(mouseX, mouseY, mouseButton))
			return true;
		for (CommonElement e : childrens) {
			if (e.mouseClicked(mouseX, mouseY, mouseButton))
				return true;
		}
		return false;
	}

	public void tick() {
		listener.tick();
		for (CommonElement e : childrens)
			e.tick();
	}

	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		listener.render(stack, mouseX, mouseY, partialTicks);
		for (CommonElement e : childrens)
			e.render(stack, mouseX, mouseY, partialTicks);
	}

	public abstract void renderDefaultBackground(CommonMatrixStack stack);

	public abstract void displayScreen();

	public abstract int fontWidth(String text);

	public void drawCenterString(CommonMatrixStack stack, String text, int x, int y, int color, float factor) {
		drawCenterString(stack, text, (float) x, (float) y, color, factor);
	}

	public void drawCenterString(CommonMatrixStack stack, String text, float x, float y, int color, float factor) {
		drawString(stack, text, x - fontWidth(text) * factor / 2, y, color, factor);
	}

	public void drawRightString(CommonMatrixStack stack, String text, int x, int y, int color, float factor) {
		drawRightString(stack, text, (float) x, (float) y, color, factor);
	}

	public void drawRightString(CommonMatrixStack stack, String text, float x, float y, int color, float factor) {
		drawString(stack, text, x - fontWidth(text) * factor, y, color, factor);
	}

	public void drawCenterString(CommonMatrixStack stack, String text, int x, int y, int color) {
		drawCenterString(stack, text, (float) x, (float) y, color);
	}

	public void drawCenterString(CommonMatrixStack stack, String text, float x, float y, int color) {
		drawString(stack, text, x - (float) fontWidth(text) / 2, y, color);
	}

	public void drawRightString(CommonMatrixStack stack, String text, int x, int y, int color) {
		drawRightString(stack, text, (float) x, (float) y, color);
	}

	public void drawRightString(CommonMatrixStack stack, String text, float x, float y, int color) {
		drawString(stack, text, x - fontWidth(text), y, color);
	}

	public void drawString(CommonMatrixStack stack, String text, int x, int y, int color, float factor) {
		drawString(stack, text, (float) x, (float) y, color, factor);
	}

	public void drawString(CommonMatrixStack stack, String text, float x, float y, int color, float factor) {
		stack.scale(factor);
		drawString(stack, text, x / factor, y / factor, color);
		stack.scaleInv(factor);
	}

	public void drawString(CommonMatrixStack stack, String text, int x, int y, int color) {
		drawString(stack, text, (float) x, (float) y, color);
	}

	public abstract void drawString(CommonMatrixStack stack, String text, float x, float y, int color);

	public abstract float getBlitOffset();
}
