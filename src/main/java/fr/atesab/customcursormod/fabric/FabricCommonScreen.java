package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FabricCommonScreen extends CommonScreen {
	class FabricCommonScreenHandler extends Screen {
		FabricCommonScreen cs;

		FabricCommonScreenHandler(Text title) {
			super(title);
			cs = FabricCommonScreen.this;
		}

		@Override
		protected void init() {
			FabricCommonScreen.this.resize(width, height);
			FabricCommonScreen.this.init();
			super.init();
		}

		@Override
		public void resize(MinecraftClient client, int width, int height) {
			FabricCommonScreen.this.resize(width, height);
			super.resize(client, width, height);
		}

		@Override
		public boolean charTyped(char chr, int modifiers) {
			return FabricCommonScreen.this.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
		}

		@Override
		public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
			return FabricCommonScreen.this.keyPressed(keyCode, scanCode, modifiers)
					|| super.keyPressed(keyCode, scanCode, modifiers);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			return FabricCommonScreen.this.mouseClicked(mouseX, mouseY, button)
					|| super.mouseClicked(mouseX, mouseY, button);
		}

		@Override
		public void tick() {
			FabricCommonScreen.this.tick();
			super.tick();
		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
			FabricCommonScreen.this.render(new FabricCommonMatrixStack(matrices), mouseX, mouseY, partialTicks);
			super.render(matrices, mouseX, mouseY, partialTicks);
		}

		TextRenderer getTextRenderer() {
			return textRenderer;
		}
	}

	private FabricCommonScreenHandler handle;

	public FabricCommonScreen(CommonScreen.CommonScreenObject obj) {
		super(obj.parent, obj.listener);
		handle = new FabricCommonScreenHandler(obj.title.<Text>getHandle());
	}

	@Override
	public void renderDefaultBackground(CommonMatrixStack stack) {
		handle.renderBackground(stack.<MatrixStack>getHandle());
	}

	@Override
	public void displayScreen() {
		MinecraftClient.getInstance().setScreen(handle);
	}

	@Override
	public int fontWidth(String text) {
		return handle.getTextRenderer().getWidth(text);
	}

	@Override
	public void drawString(CommonMatrixStack stack, String text, float x, float y, int color) {
		handle.getTextRenderer().draw(stack.<MatrixStack>getHandle(), text, x, y, color);
	}

	@Override
	public float getBlitOffset() {
		return handle.getZOffset();
	}

}
