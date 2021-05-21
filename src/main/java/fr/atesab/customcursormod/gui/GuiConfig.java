package fr.atesab.customcursormod.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import org.lwjgl.opengl.GL11;

import fr.atesab.customcursormod.Configuration;
import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorMod;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiConfig extends Screen {
	private Screen parent;

	public GuiConfig(Screen parent) {
		super(new TranslationTextComponent("cursormod.gui.config"));
		this.parent = parent;
	}

	private void drawScaledCenterString(MatrixStack stack, String text, float x, float y, int color, float factor) {
		drawScaledString(stack, text, x - font.width(text) * factor / 2, (float) y, color, factor);
	}

	private void drawScaledString(MatrixStack stack, String text, float x, float y, int color, float factor) {
		GL11.glScalef(factor, factor, factor);
		font.drawShadow(stack, text, x / factor, y / factor, color);
		GL11.glScalef(1F / factor, 1F / factor, 1F / factor);
	}

	@Override
	public void init() {
		Configuration cfg = CursorMod.getConfig();
		addButton(new Button(width / 2 - 100, height / 2 + 24, 200, 20, new TranslationTextComponent("menu.options"), b -> {
			if (cfg.dynamicCursor)
				getMinecraft().setScreen(new GuiConfigCursorMod(this));
			else {
				CursorConfig ccfg = CursorMod.getCursors().get(CursorType.POINTER);
				getMinecraft().setScreen(new GuiCursorConfig(this, CursorType.POINTER, ccfg) {

					@Override
					protected void saveConfig(CursorConfig cursorConfig) {
						CursorMod.replaceCursor(CursorType.POINTER, cursorConfig);
					}
				});
			}
		}));
		addButton(new GuiBooleanButton(width / 2 - 100, height / 2, 200, 20, new TranslationTextComponent("cursormod.config.dynCursor"),
				cfg::setDynamicCursor, cfg::isDynamicCursor));
		addButton(new GuiBooleanButton(width / 2 - 100, height / 2 - 24, 200, 20,
		new TranslationTextComponent("cursormod.config.clickAnim"), cfg::setClickAnimation, cfg::isClickAnimation));

		addButton(new Button(width / 2 - 100, height / 2 + 48, 200, 20, new TranslationTextComponent("gui.done"), b -> {
			getMinecraft().setScreen(parent);
			CursorMod.saveConfig();
		}));
		super.init();
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(stack);
		drawScaledCenterString(stack, CursorMod.MOD_NAME + " " + CursorMod.MOD_VERSION, width / 2, height / 2 - 60, 0xffffffff,
				2.5F);
		super.render(stack, mouseX, mouseY, partialTicks);
	}
}
