package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonTextField;
import fr.atesab.customcursormod.fabric.mixin.AbstractButtonWidgetMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class FabricCommonTextField extends CommonTextField {
	private final TextFieldWidget handle;

	public FabricCommonTextField(TextFieldWidget handle) {
		this.handle = handle;
	}

	public FabricCommonTextField(CommonTextFieldObject obj) {
		this.handle = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, obj.xPosition, obj.yPosition,
				obj.width, obj.height, Text.literal(""));
	}

	@Override
	public int getXPosition() {
		return handle.x;
	}

	@Override
	public int getYPosition() {
		return handle.y;
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
		return handle.visible;
	}

	@Override
	public void setXPosition(int xPosition) {
		handle.x = xPosition;
	}

	@Override
	public void setYPosition(int yPosition) {
		handle.y = yPosition;
	}

	@Override
	public void setWidth(int width) {
		handle.setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		((AbstractButtonWidgetMixin) handle).setHeight(height);
	}

	@Override
	public void setEnable(boolean enable) {
		handle.setEditable(enable);
	}

	@Override
	public void setValue(String value) {
		handle.setText(value);
	}

	@Override
	public String getValue() {
		return handle.getText();
	}

	@Override
	public void setMaxLength(int length) {
		handle.setMaxLength(length);
	}

	@Override
	public void setTextColor(int color) {
		handle.setEditableColor(color);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return handle.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		handle.render(stack.getHandle(), mouseX, mouseY, partialTicks);
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
	public void tick() {
		handle.tick();
	}
}
