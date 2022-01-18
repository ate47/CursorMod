package fr.atesab.customcursormod.fabric;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonShader;
import fr.atesab.customcursormod.common.handler.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;

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
	public int fontHeight() {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}

	@Override
	public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight, int color, boolean useAlpha) {
		float scaleX = 1.0F / tileWidth;
		float scaleY = 1.0F / tileHeight;
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = color & 0xFF;
		int alpha = useAlpha ? (color >> 24) : 0xff;
		var tesselator = Tessellator.getInstance();
		var bufferbuilder = tesselator.getBuffer();
		bufferbuilder.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		bufferbuilder.vertex((double) x, (double) (y + height), 0.0D)
				.texture((float) (u * scaleX), (float) ((v + (float) vHeight) * scaleY)).color(red, green, blue, alpha)
				.next();
		bufferbuilder.vertex((double) (x + width), (double) (y + height), 0.0D)
				.texture((float) ((u + (float) uWidth) * scaleX), (float) ((v + (float) vHeight) * scaleY))
				.color(red, green, blue, alpha).next();
		bufferbuilder.vertex((double) (x + width), (double) y, 0.0D)
				.texture((float) ((u + (float) uWidth) * scaleX), (float) (v * scaleY)).color(red, green, blue, alpha)
				.next();
		bufferbuilder.vertex((double) x, (double) y, 0.0D).texture((float) (u * scaleX), (float) (v * scaleY))
				.color(red, green, blue, alpha).next();
		tesselator.draw();
	}

	@Override
	public void drawGradientRect(CommonMatrixStack stack, int left, int top, int right, int bottom, int rightTopColor,
			int leftTopColor, int leftBottomColor, int rightBottomColor, float zLevel) {
		float alphaRightTop = (float) (rightTopColor >> 24 & 255) / 255.0F;
		float redRightTop = (float) (rightTopColor >> 16 & 255) / 255.0F;
		float greenRightTop = (float) (rightTopColor >> 8 & 255) / 255.0F;
		float blueRightTop = (float) (rightTopColor & 255) / 255.0F;
		float alphaLeftTop = (float) (leftTopColor >> 24 & 255) / 255.0F;
		float redLeftTop = (float) (leftTopColor >> 16 & 255) / 255.0F;
		float greenLeftTop = (float) (leftTopColor >> 8 & 255) / 255.0F;
		float blueLeftTop = (float) (leftTopColor & 255) / 255.0F;
		float alphaLeftBottom = (float) (leftBottomColor >> 24 & 255) / 255.0F;
		float redLeftBottom = (float) (leftBottomColor >> 16 & 255) / 255.0F;
		float greenLeftBottom = (float) (leftBottomColor >> 8 & 255) / 255.0F;
		float blueLeftBottom = (float) (leftBottomColor & 255) / 255.0F;
		float alphaRightBottom = (float) (rightBottomColor >> 24 & 255) / 255.0F;
		float redRightBottom = (float) (rightBottomColor >> 16 & 255) / 255.0F;
		float greenRightBottom = (float) (rightBottomColor >> 8 & 255) / 255.0F;
		float blueRightBottom = (float) (rightBottomColor & 255) / 255.0F;
		var bufferbuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
				GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		bufferbuilder.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
		var mat = stack.<MatrixStack>getHandle().peek().getPositionMatrix();
		bufferbuilder.vertex(mat, right, top, zLevel).color(redRightTop, greenRightTop, blueRightTop, alphaRightTop)
				.next();
		bufferbuilder.vertex(mat, left, top, zLevel).color(redLeftTop, greenLeftTop, blueLeftTop, alphaLeftTop).next();
		bufferbuilder.vertex(mat, left, bottom, zLevel)
				.color(redLeftBottom, greenLeftBottom, blueLeftBottom, alphaLeftBottom).next();
		bufferbuilder.vertex(mat, right, bottom, zLevel)
				.color(redRightBottom, greenRightBottom, blueRightBottom, alphaRightBottom).next();
		bufferbuilder.end();
		BufferRenderer.draw(bufferbuilder);
		RenderSystem.disableBlend();
		RenderSystem.enableTexture();
	}

	@Override
	public void setShaderColor(float r, float g, float b, float a) {
		RenderSystem.setShaderColor(r, g, b, a);
	}

	@Override
	public void setShader(CommonShader shader) {
		RenderSystem.setShader(shader.getHandle());
	}
}
