package fr.atesab.customcursormod.common.handler;

public interface CommonElement {
	int getXPosition();
	int getYPosition();
	int getWidth();
	int getHeight();
	boolean isEnable();
	void setXPosition(int xPosition);
	void setYPosition(int yPosition);
	void setWidth(int width);
	void setHeight(int height);
	void setEnable(boolean enable);
	default boolean charTyped(char key, int modifier) {
		return false;
	}
	default boolean keyPressed(int key, int scan, int modifier) {
		return false;
	}
	default boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return false;
	}
	default void tick() {
	}
	default void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
	}
	default boolean isHover(int mouseX, int mouseY) {
		int x = getXPosition();
		int y = getYPosition();
		int width = getWidth();
		int height = getHeight();
		x = Math.min(x + width, x);
		y = Math.min(y + height, y);
		width = Math.abs(width);
		height = Math.abs(height);
		return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
	}
}
