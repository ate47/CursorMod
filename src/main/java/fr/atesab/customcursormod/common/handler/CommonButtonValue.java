package fr.atesab.customcursormod.common.handler;

import java.util.function.Consumer;

public class CommonButtonValue<T> extends CommonButton {
	public static <T> CommonButtonValue<T> create(T value, CommonText message, int xPosition, int yPosition, int width,
			int height, Consumer<CommonButton> action) {
		return new CommonButtonValue<>(create(message, xPosition, yPosition, width, height, action), value);
	}

	private CommonButton handle;
	private T value;

	private CommonButtonValue(CommonButton handle, T value) {
		this.handle = handle;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public int getXPosition() {
		return handle.getXPosition();
	}

	@Override
	public int getYPosition() {
		return handle.getYPosition();
	}

	@Override
	public int getWidth() {
		return handle.getWidth();
	}

	@Override
	public int getHeight() {
		return handle.getHeight();
	}

	@Override
	public boolean isEnable() {
		return handle.isEnable();
	}

	@Override
	public void setXPosition(int xPosition) {
		handle.setXPosition(xPosition);
	}

	@Override
	public void setYPosition(int yPosition) {
		handle.setYPosition(yPosition);
	}

	@Override
	public void setWidth(int width) {
		handle.setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		handle.setHeight(height);
	}

	@Override
	public void setEnable(boolean enable) {
		handle.setEnable(enable);
	}

	@Override
	public CommonText getMessage() {
		return handle.getMessage();
	}

	@Override
	public void setMessage(CommonText message) {
		handle.setMessage(message);
	}

	@Override
	public boolean isVisible() {
		return handle.isVisible();
	}

	@Override
	public void setVisible(boolean visible) {
		handle.setVisible(visible);
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return handle.charTyped(key, modifier);
	}

	@Override
	public boolean keyPressed(int key, int scan, int modifier) {
		return handle.keyPressed(key, scan, modifier);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return handle.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		handle.render(stack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void tick() {
		handle.tick();
	}
}
