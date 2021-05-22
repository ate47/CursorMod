package fr.atesab.customcursormod.forge;

import com.mojang.blaze3d.matrix.MatrixStack;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

public class ForgeCommonTextField extends CommonTextField {
	private TextFieldWidget handle;

	public ForgeCommonTextField(TextFieldWidget handle) {
		this.handle = handle;
	}

	public ForgeCommonTextField(CommonTextFieldObject obj) {
		this.handle = new TextFieldWidget(Minecraft.getInstance().font, obj.xPosition, obj.yPosition, obj.width,
				obj.height, new StringTextComponent(""));
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
		handle.setHeight(height);
	}

	@Override
	public void setEnable(boolean enable) {
		handle.setEditable(enable);
	}

	@Override
	public void setValue(String value) {
		handle.setValue(value);
	}

	@Override
	public String getValue() {
		return handle.getValue();
	}

	@Override
	public void setMaxLength(int length) {
		handle.setMaxLength(length);
	}

	@Override
	public void setTextColor(int color) {
		handle.setTextColor(color);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return handle.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		handle.render(stack.<MatrixStack>getHandle(), mouseX, mouseY, partialTicks);
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
