package fr.atesab.customcursormod.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import fr.atesab.customcursormod.CursorMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiConfig extends GuiScreen {
	private GuiButton dynCursor;
	private GuiButton dynOption;
	private GuiButton clickAnim;
	private GuiScreen parent;

	public GuiConfig(GuiScreen parent) {
		this.parent = parent;
	}

	private void drawScaledCenterString(String text, float x, float y, int color, float factor) {
		drawScaledString(text, x - fontRendererObj.getStringWidth(text) * factor / 2, (float) y, color, factor);
	}

	private void drawScaledString(String text, float x, float y, int color, float factor) {
		GL11.glScalef(factor, factor, factor);
		fontRendererObj.drawStringWithShadow(text, x / factor, y / factor, color);
		GL11.glScalef(1F / factor, 1F / factor, 1F / factor);
	}

	private GuiButton updateButton(GuiButton button, boolean var) {
		button.packedFGColour = var ? 0xff00ff00 : 0xffff0000;
		button.displayString = I18n.format(var ? "cursormod.config.yes" : "cursormod.config.no");
		return button;
	}

	private void drawRightString(String text, GuiButton button, int color) {
		fontRendererObj.drawStringWithShadow(text, button.xPosition - fontRendererObj.getStringWidth(text),
				button.yPosition + button.height / 2 - fontRendererObj.FONT_HEIGHT / 2, color);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
			mc.displayGuiScreen(parent);
			CursorMod.saveConfig();
			break;
		case 1:
			updateButton(button, dynOption.enabled = (CursorMod.dynamicCursor = !CursorMod.dynamicCursor));
			break;
		case 2:
			mc.displayGuiScreen(new GuiConfigCursorMod(this));
			break;
		case 3:
			updateButton(button, CursorMod.clickAnimation = !CursorMod.clickAnimation);
			break;
		}
		super.actionPerformed(button);
	}

	@Override
	public void initGui() {
		buttonList
				.add(dynOption = new GuiButton(2, width / 2 + 2, height / 2 - 24, 98, 20, I18n.format("menu.options")));
		buttonList.add(updateButton(dynCursor = new GuiButton(1, width / 2 - 100, height / 2 - 24, 98, 20, null),
				dynOption.enabled = CursorMod.dynamicCursor));
		buttonList.add(updateButton(clickAnim = new GuiButton(3, width / 2 - 100, height / 2, null),
				CursorMod.clickAnimation));
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 2 + 24, I18n.format("gui.done")));
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawScaledCenterString(CursorMod.MOD_NAME + " " + CursorMod.MOD_VERSION, width / 2, height / 2 - 60, 0xffffffff,
				2.5F);
		drawRightString(I18n.format("cursormod.config.clickAnim") + " : ", clickAnim, 0xffffffff);
		drawRightString(I18n.format("cursormod.config.dynCursor") + " : ", dynCursor, 0xffffffff);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
