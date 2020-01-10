package fr.atesab.customcursormod.gui;

import org.lwjgl.opengl.GL11;

import fr.atesab.customcursormod.Configuration;
import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorMod;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiConfig extends Screen {
	private Screen parent;

	public GuiConfig(Screen parent) {
		super(new TranslationTextComponent("cursormod.gui.config"));
		this.parent = parent;
	}

	private void drawScaledCenterString(String text, float x, float y, int color, float factor) {
		drawScaledString(text, x - font.getStringWidth(text) * factor / 2, (float) y, color, factor);
	}

	private void drawScaledString(String text, float x, float y, int color, float factor) {
		GL11.glScalef(factor, factor, factor);
		font.drawStringWithShadow(text, x / factor, y / factor, color);
		GL11.glScalef(1F / factor, 1F / factor, 1F / factor);
	}

	@Override
	public void init() {
		Configuration cfg = CursorMod.getConfig();
		addButton(new Button(width / 2 - 100, height / 2 + 24, 200, 20, I18n.format("menu.options"), b -> {
			if (cfg.dynamicCursor)
				getMinecraft().displayGuiScreen(new GuiConfigCursorMod(this));
			else {
				CursorConfig ccfg = CursorMod.getCursors().get(CursorType.POINTER);
				getMinecraft().displayGuiScreen(new GuiCursorConfig(this, CursorType.POINTER, ccfg) {

					@Override
					protected void saveConfig(CursorConfig cursorConfig) {
						CursorMod.replaceCursor(CursorType.POINTER, cursorConfig);
					}
				});
			}
		}));
		addButton(new GuiBooleanButton(width / 2 - 100, height / 2, 200, 20, I18n.format("cursormod.config.dynCursor"),
				cfg::setDynamicCursor, cfg::isDynamicCursor));
		addButton(new GuiBooleanButton(width / 2 - 100, height / 2 - 24, 200, 20,
				I18n.format("cursormod.config.clickAnim"), cfg::setClickAnimation, cfg::isClickAnimation));

		addButton(new Button(width / 2 - 100, height / 2 + 48, 200, 20, I18n.format("gui.done"), b -> {
			getMinecraft().displayGuiScreen(parent);
			CursorMod.saveConfig();
		}));
		super.init();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		drawScaledCenterString(CursorMod.MOD_NAME + " " + CursorMod.MOD_VERSION, width / 2, height / 2 - 60, 0xffffffff,
				2.5F);
		super.render(mouseX, mouseY, partialTicks);
	}
}
