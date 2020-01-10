package fr.atesab.customcursormod.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;

public abstract class GuiCursorConfig extends GuiScreen {
	private GuiScreen parent;
	private GuiTextField xhotspot;
	private GuiTextField yhotspot;
	private GuiTextField cursorLocation;
	private GuiButton doneButton;
	private GuiSelectZone selectZone;
	private int imageWidth = 1;
	private int imageHeight = 1;
	private int numImage = 1;

	private final CursorType type;
	private CursorConfig cursorConfig;

	public GuiCursorConfig(GuiScreen parent, CursorType type, CursorConfig cursorConfig) {
		this.parent = parent;
		this.type = type;
		this.cursorConfig = cursorConfig.copy();
	}

	private void updateCursorValues(CursorConfig cursorConfig) {
		this.cursorConfig = cursorConfig;
		xhotspot.setText(String.valueOf(cursorConfig.getxHotSpot()));
		yhotspot.setText(String.valueOf(cursorConfig.getyHotSpot()));
		cursorLocation.setText(cursorConfig.getLink());
	}

	private void drawCenterString(String text, int x, int y, int HEIGHT, int color) {
		fontRenderer.drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2,
				y + HEIGHT / 2 - fontRenderer.FONT_HEIGHT / 2, color);
	}

	private void drawRightString(String text, int x, int y, int HEIGHT, int color) {
		fontRenderer.drawStringWithShadow(text, x - fontRenderer.getStringWidth(text),
				y + HEIGHT / 2 - fontRenderer.FONT_HEIGHT / 2, color);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		xhotspot.drawTextField(mouseX, mouseY, partialTicks);
		yhotspot.drawTextField(mouseX, mouseY, partialTicks);
		cursorLocation.drawTextField(mouseX, mouseY, partialTicks);
		drawCenterString(type.getName(), width / 2 - 74, height / 2 - 41 - 21, 20, Color.ORANGE.getRGB());
		drawRightString(I18n.format("cursormod.config.xhotspot") + " : ", xhotspot.x, xhotspot.y, xhotspot.height,
				Color.WHITE.getRGB());
		drawRightString(I18n.format("cursormod.config.yhotspot") + " : ", yhotspot.x, yhotspot.y, yhotspot.height,
				Color.WHITE.getRGB());
		drawRightString(I18n.format("cursormod.config.location") + " : ", cursorLocation.x, cursorLocation.y,
				cursorLocation.height, Color.WHITE.getRGB());
		if (syncImageSize()) {
			mc.getTextureManager().bindTexture(cursorConfig.getResourceLocation());
			drawGradientRect(width / 2 + 36, height / 2 - 64, width / 2 + 164, height / 2 + 64, -1072689136,
					-804253680);
			GlStateManager.color3f(1, 1, 1);
			drawScaledCustomSizeModalRect(width / 2 + 36, height / 2 - 64, 0, 0, imageWidth, imageHeight, 128, 128,
					imageWidth, imageHeight * numImage);
			if (cursorConfig.getxHotSpot() >= 0 && cursorConfig.getxHotSpot() < imageWidth
					&& cursorConfig.getyHotSpot() >= 0 && cursorConfig.getyHotSpot() < imageHeight)
				drawCenteredString(fontRenderer, "+",
						width / 2 + 36 + ((int) (((float) cursorConfig.getxHotSpot()) * 128F / (float) imageWidth)),
						height / 2 - 64 + ((int) (((float) cursorConfig.getyHotSpot()) * 128F / (float) imageHeight))
								- fontRenderer.FONT_HEIGHT / 2,
						Color.WHITE.getRGB());
			if (numImage > 1)
				drawCenteredString(fontRenderer, "(" + I18n.format("cursormod.gui.animate") + ")", width / 2 + 100,
						height / 2 + 64 + 1, Color.WHITE.getRGB());
			selectZone.enable = true;
		} else {
			drawCenteredString(fontRenderer, I18n.format("cursormod.gui.error"), width / 2 + 100,
					height / 2 - fontRenderer.FONT_HEIGHT / 2, Color.RED.getRGB());
			selectZone.enable = false;
		}
		super.render(mouseX, mouseY, partialTicks);
	}

	public void initGui() {
		syncImageSize();
		xhotspot = new GuiTextField(0, fontRenderer, width / 2 - 99, height / 2 - 41, 124, 18);
		yhotspot = new GuiTextField(1, fontRenderer, width / 2 - 99, height / 2 - 20, 124, 18);
		cursorLocation = new GuiTextField(2, fontRenderer, width / 2 - 99, height / 2 + 1, 124, 18);
		cursorLocation.setMaxStringLength(Integer.MAX_VALUE);
		updateCursorValues(cursorConfig);
		selectZone = new GuiSelectZone(width / 2 + 36, height / 2 - 64, 128, 128);
		addButton(new GuiButton(2, width / 2 - 174, height / 2 + 21,
				I18n.format("cursormod.gui.default")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				updateCursorValues(type.getDefaultConfig());
				super.onClick(mouseX, mouseY);
			}
		});
		addButton(doneButton = new GuiButton(0, width / 2 - 174, height / 2 + 42, 100, 20, I18n.format("gui.done")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				saveConfig(cursorConfig);
				mc.displayGuiScreen(parent);
				super.onClick(mouseX, mouseY);
			}
		});
		addButton(new GuiButton(1, width / 2 - 72, height / 2 + 42, 99, 20, I18n.format("cursormod.gui.cancel")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				mc.displayGuiScreen(parent);
				super.onClick(mouseX, mouseY);
			}
		});
		super.initGui();
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		xhotspot.charTyped(key, modifier);
		yhotspot.charTyped(key, modifier);
		cursorLocation.charTyped(key, modifier);
		return super.charTyped(key, modifier);
	}

	@Override
	public boolean keyPressed(int key, int scan, int modifier) {
		xhotspot.keyPressed(key, scan, modifier);
		yhotspot.keyPressed(key, scan, modifier);
		cursorLocation.keyPressed(key, scan, modifier);
		return super.keyPressed(key, scan, modifier);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		xhotspot.mouseClicked(mouseX, mouseY, mouseButton);
		yhotspot.mouseClicked(mouseX, mouseY, mouseButton);
		cursorLocation.mouseClicked(mouseX, mouseY, mouseButton);
		if (mouseX >= width / 2 + 36 && mouseY >= height / 2 - 64 && mouseX <= width / 2 + 164
				&& mouseY <= height / 2 + 64) {
			cursorConfig.setxHotSpot(MathHelper
					.clamp((int) ((float) (mouseX - (width / 2 + 36)) * (float) imageWidth / 128F), 0, imageWidth - 1));
			cursorConfig.setyHotSpot(MathHelper.clamp(
					(int) ((float) (mouseY - (height / 2 - 64)) * (float) imageHeight / 128F), 0, imageWidth - 1));
			xhotspot.setText(String.valueOf(cursorConfig.getxHotSpot()));
			yhotspot.setText(String.valueOf(cursorConfig.getyHotSpot()));
		}
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected abstract void saveConfig(CursorConfig cursorConfig);

	private boolean syncImageSize() {
		try {
			BufferedImage image = ImageIO.read(cursorConfig.getResource().getInputStream());
			imageWidth = image.getWidth();
			imageHeight = image.getWidth();
			numImage = image.getHeight() / image.getWidth();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void tick() {
		xhotspot.tick();
		yhotspot.tick();
		cursorLocation.tick();
		verifyValue();
		super.tick();
	}

	private void verifyValue() {
		boolean flag = syncImageSize();
		try {
			cursorConfig.setxHotSpot(Integer.valueOf(xhotspot.getText()));
			if (cursorConfig.getxHotSpot() >= 0 && cursorConfig.getxHotSpot() < imageWidth) {
				xhotspot.setTextColor(Color.WHITE.getRGB());
			} else {
				xhotspot.setTextColor(Color.RED.getRGB());
				flag = false;
			}
		} catch (Exception e) {
			xhotspot.setTextColor(Color.RED.getRGB());
			flag = false;
		}
		try {
			cursorConfig.setyHotSpot(Integer.valueOf(yhotspot.getText()));
			if (cursorConfig.getyHotSpot() >= 0 && cursorConfig.getyHotSpot() < imageHeight) {
				yhotspot.setTextColor(Color.WHITE.getRGB());
			} else {
				yhotspot.setTextColor(Color.RED.getRGB());
				flag = false;
			}
		} catch (Exception e) {
			yhotspot.setTextColor(Color.RED.getRGB());
			flag = false;
		}
		cursorConfig.setLink(cursorLocation.getText());
		doneButton.enabled = flag;
	}
}
