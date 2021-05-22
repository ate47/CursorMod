package fr.atesab.customcursormod.fabric;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.common.handler.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

public class FabricGuiUtils extends GuiUtils {
	private FabricGuiUtils() {
	}
	private static final FabricGuiUtils instance = new FabricGuiUtils();
	/**
	 * @return the instance
	 */
	public static FabricGuiUtils getFabric() {
		return instance;
	}
	@Override
	public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight) {
		float f = 1.0F / tileWidth;
		float f1 = 1.0F / tileHeight;
		RenderSystem.enableAlphaTest();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferbuilder.vertex((double) x, (double) (y + height), 0.0D).texture((u * f), ((v + vHeight) * f1)).next();
		bufferbuilder.vertex((double) (x + width), (double) (y + height), 0.0D)
				.texture(((u + uWidth) * f), ((v + (float) vHeight) * f1)).next();
		bufferbuilder.vertex((double) (x + width), (double) y, 0.0D).texture(((u + uWidth) * f), v * f1).next();
		bufferbuilder.vertex((double) x, (double) y, 0.0D).texture(u * f, v * f1).next();
		tessellator.draw();
		RenderSystem.disableAlphaTest();
	}

	@Override
	public void drawGradientRect(float zLevel, int left, int top, int right, int bottom, int startColor,
			int endColor) {
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA,
				GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE,
				GlStateManager.DstFactor.ZERO);
		RenderSystem.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, VertexFormats.POSITION_COLOR);
		bufferbuilder.vertex((double) right, (double) top, (double) zLevel).color(f1, f2, f3, f)
				.next();
		bufferbuilder.vertex((double) left, (double) top, (double) zLevel).color(f1, f2, f3, f)
				.next();
		bufferbuilder.vertex((double) left, (double) bottom, (double) zLevel).color(f5, f6, f7, f4)
				.next();
		bufferbuilder.vertex((double) right, (double) bottom, (double) zLevel).color(f5, f6, f7, f4)
				.next();
		tessellator.draw();
		RenderSystem.shadeModel(7424);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}
	@Override
	public int fontHeight() {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}
}
