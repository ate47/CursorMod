package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class FabricBasicCommonScreen extends CommonScreen {
	private final Screen handle;

	public FabricBasicCommonScreen(Screen handle) {
		super(null);
		this.handle = handle;
	}

	@Override
	public void renderDefaultBackground(CommonMatrixStack stack) {
		throw new UnsupportedOperationException("renderDefaultBackground");
	}

	@Override
	public void displayScreen() {
		MinecraftClient.getInstance().setScreen(handle);
	}

	@Override
	public int fontWidth(String text) {
		throw new UnsupportedOperationException("fontWidth");
	}

	@Override
	public void drawString(CommonMatrixStack stack, String text, float x, float y, int color) {
		throw new UnsupportedOperationException("drawString");
	}

	@Override
	public float getBlitOffset() {
		throw new UnsupportedOperationException("getBlitOffset");
	}

}
