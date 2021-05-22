package fr.atesab.customcursormod.forge;

import com.mojang.blaze3d.matrix.MatrixStack;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class ForgeCommonScreen extends CommonScreen {
	class ForgeCommonScreenHandler extends Screen {
		ForgeCommonScreen cs;

		ForgeCommonScreenHandler(ITextComponent title) {
			super(title);
			cs = ForgeCommonScreen.this;
		}

		@Override
		protected void init() {
			ForgeCommonScreen.this.resize(width, height);
			ForgeCommonScreen.this.init();
			super.init();
		}

		@Override
		public void resize(Minecraft client, int width, int height) {
			ForgeCommonScreen.this.resize(width, height);
			super.resize(client, width, height);
		}

		@Override
		public boolean charTyped(char chr, int modifiers) {
			return ForgeCommonScreen.this.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
		}

		@Override
		public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
			return ForgeCommonScreen.this.keyPressed(keyCode, scanCode, modifiers)
					|| super.keyPressed(keyCode, scanCode, modifiers);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			return ForgeCommonScreen.this.mouseClicked(mouseX, mouseY, button)
					|| super.mouseClicked(mouseX, mouseY, button);
		}

		@Override
		public void tick() {
			ForgeCommonScreen.this.tick();
			super.tick();
		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
			ForgeCommonScreen.this.render(new ForgeCommonMatrixStack(matrices), mouseX, mouseY, partialTicks);
			super.render(matrices, mouseX, mouseY, partialTicks);
		}

		FontRenderer getTextRenderer() {
			return font;
		}
	}

	private ForgeCommonScreenHandler handle;

	public ForgeCommonScreen(CommonScreen.CommonScreenObject obj) {
		super(obj.parent, obj.listener);
		handle = new ForgeCommonScreenHandler(obj.title.<ITextComponent>getHandle());
	}

	/**
	 * @return the handle
	 */
	public ForgeCommonScreenHandler getHandle() {
		return handle;
	}

	@Override
	public void renderDefaultBackground(CommonMatrixStack stack) {
		handle.renderBackground(stack.<MatrixStack>getHandle());
	}

	@Override
	public void displayScreen() {
		Minecraft.getInstance().setScreen(handle);
	}

	@Override
	public int fontWidth(String text) {
		return handle.getTextRenderer().width(text);
	}

	@Override
	public void drawString(CommonMatrixStack stack, String text, float x, float y, int color) {
		handle.getTextRenderer().draw(stack.<MatrixStack>getHandle(), text, x, y, color);
	}

	@Override
	public float getBlitOffset() {
		return handle.getBlitOffset();
	}

}
