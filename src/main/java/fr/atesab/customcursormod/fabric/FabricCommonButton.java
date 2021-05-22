package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonButton;
import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonText;
import fr.atesab.customcursormod.fabric.mixin.AbstractButtonWidgetMixin;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FabricCommonButton extends CommonButton {
	public final ButtonWidget handle;

	public FabricCommonButton(CommonButtonObject obj) {
		handle = new ButtonWidget(obj.xPosition, obj.yPosition, obj.width, obj.height, obj.message.<Text>getHandle(),
				b -> obj.action.accept(this));
	}

	public FabricCommonButton(ButtonWidget handle) {
		this.handle = handle;
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
		return handle.active;
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
		handle.active = enable;
	}

	@Override
	public void setVisible(boolean visible) {
		handle.visible = visible;
	}

	@Override
	public CommonText getMessage() {
		return new FabricCommonText(handle.getMessage());
	}

	@Override
	public void setMessage(CommonText message) {
		handle.setMessage(message.<Text>getHandle());
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
	public boolean isVisible() {
		return handle.visible;
	}

}
