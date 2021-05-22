package fr.atesab.customcursormod.forge;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.common.handler.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ForgeGuiUtils extends GuiUtils {

	private ForgeGuiUtils() {
	}
	private static final ForgeGuiUtils instance = new ForgeGuiUtils();

	/**
	 * @return the instance
	 */
	public static ForgeGuiUtils getForge() {
		return instance;
	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used
	 * anywhere in vanilla code.
	 */
	@SuppressWarnings("deprecation")
	public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight) {
		float f = 1.0F / tileWidth;
		float f1 = 1.0F / tileHeight;
		RenderSystem.enableAlphaTest();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex((double) x, (double) (y + height), 0.0D).uv((u * f), ((v + vHeight) * f1)).endVertex();
		bufferbuilder.vertex((double) (x + width), (double) (y + height), 0.0D)
				.uv(((u + uWidth) * f), ((v + (float) vHeight) * f1)).endVertex();
		bufferbuilder.vertex((double) (x + width), (double) y, 0.0D).uv(((u + uWidth) * f), v * f1).endVertex();
		bufferbuilder.vertex((double) x, (double) y, 0.0D).uv(u * f, v * f1).endVertex();
		tessellator.end();
		RenderSystem.disableAlphaTest();
	}

	/**
	 * Draws a rectangle with a vertical gradient between the specified colors (ARGB
	 * format). Args : x1, y1, x2, y2, topColor, bottomColor
	 */
	@SuppressWarnings("deprecation")
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
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		RenderSystem.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.vertex((double) right, (double) top, (double) zLevel).color(f1, f2, f3, f)
				.endVertex();
		bufferbuilder.vertex((double) left, (double) top, (double) zLevel).color(f1, f2, f3, f)
				.endVertex();
		bufferbuilder.vertex((double) left, (double) bottom, (double) zLevel).color(f5, f6, f7, f4)
				.endVertex();
		bufferbuilder.vertex((double) right, (double) bottom, (double) zLevel).color(f5, f6, f7, f4)
				.endVertex();
		tessellator.end();
		RenderSystem.shadeModel(7424);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}

	@Override
	public int fontHeight() {
		return Minecraft.getInstance().font.lineHeight;
	}
}
