package fr.atesab.customcursormod.gui;

import org.lwjgl.opengl.GL11;

import fr.atesab.customcursormod.Configuration;
import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorMod;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiConfig extends GuiScreen {
	private GuiScreen parent;

	public GuiConfig(GuiScreen parent) {
		this.parent = parent;
	}

	private void drawScaledCenterString(String text, float x, float y, int color, float factor) {
		drawScaledString(text, x - fontRenderer.getStringWidth(text) * factor / 2, (float) y, color, factor);
	}

	private void drawScaledString(String text, float x, float y, int color, float factor) {
		GL11.glScalef(factor, factor, factor);
		fontRenderer.drawStringWithShadow(text, x / factor, y / factor, color);
		GL11.glScalef(1F / factor, 1F / factor, 1F / factor);
	}

	@Override
	public void initGui() {
		Configuration cfg = CursorMod.getConfig();
		addButton(new GuiButton(2, width / 2 - 100, height / 2 + 24, 200, 20, I18n.format("menu.options")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				if (cfg.dynamicCursor)
					mc.displayGuiScreen(new GuiConfigCursorMod(GuiConfig.this));
				else {
					CursorConfig ccfg = CursorMod.getCursors().get(CursorType.POINTER);
					mc.displayGuiScreen(new GuiCursorConfig(GuiConfig.this, CursorType.POINTER, ccfg) {

						@Override
						protected void saveConfig(CursorConfig cursorConfig) {
							CursorMod.replaceCursor(CursorType.POINTER, cursorConfig);
						}
					});
				}
				super.onClick(mouseX, mouseY);
			}
		});
		addButton(new GuiBooleanButton(1, width / 2 - 100, height / 2, 200, 20,
				I18n.format("cursormod.config.dynCursor"), cfg::setDynamicCursor, cfg::isDynamicCursor));
		addButton(new GuiBooleanButton(3, width / 2 - 100, height / 2 - 24, 200, 20,
				I18n.format("cursormod.config.clickAnim"), cfg::setClickAnimation, cfg::isClickAnimation));

		addButton(new GuiButton(0, width / 2 - 100, height / 2 + 48, 200, 20, I18n.format("gui.done")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				mc.displayGuiScreen(parent);
				CursorMod.saveConfig();
				super.onClick(mouseX, mouseY);
			}
		});
		super.initGui();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawScaledCenterString(CursorMod.MOD_NAME + " " + CursorMod.MOD_VERSION, width / 2, height / 2 - 60, 0xffffffff,
				2.5F);
		super.render(mouseX, mouseY, partialTicks);
	}
}
