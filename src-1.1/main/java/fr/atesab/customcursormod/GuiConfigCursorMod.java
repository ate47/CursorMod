package fr.atesab.customcursormod;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

import javax.imageio.ImageIO;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiConfigCursorMod extends GuiScreen {
	private GuiScreen parent;
	private GuiTextField xhotspot;
	private GuiTextField yhotspot;
	private GuiTextField cursorLocation;
	private GuiButton saveReloadButton;
	private GuiButton doneButton;
	private int imageWidth = 1;
	private int imageHeight = 1;

	public final int oldXHotspot;
	public final int oldYHotspot;
	public final String oldCursorLocation;
	public GuiConfigCursorMod(GuiScreen parent) {
		this.parent = parent;
		this.oldCursorLocation = ModMain.cursorLocation;
		this.oldXHotspot = ModMain.xHotspot;
		this.oldYHotspot = ModMain.yHotspot;
	}
	public void initGui() {
		syncImageSize();
		xhotspot = new GuiTextField(0, fontRendererObj, width/2-99, height/2-41, 124, 18);
		yhotspot = new GuiTextField(1, fontRendererObj, width/2-99, height/2-20, 124, 18);
		cursorLocation = new GuiTextField(2, fontRendererObj, width/2-99, height/2+1, 124, 18);
		xhotspot.setText(String.valueOf(ModMain.xHotspot));
		yhotspot.setText(String.valueOf(ModMain.yHotspot));
		cursorLocation.setText(ModMain.cursorLocation);
		buttonList.add(saveReloadButton = new GuiButton(2, width/2-174, height/2+21, I18n.format("cursormod.gui.saveReload")));
		buttonList.add(doneButton = new GuiButton(0, width/2-174, height/2+42, 100, 20, I18n.format("gui.done")));
		buttonList.add(new GuiButton(1, width/2-72, height/2+42, 99, 20, I18n.format("cursormod.gui.cancel")));
		super.initGui();
	}
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		xhotspot.drawTextBox();
		yhotspot.drawTextBox();
		cursorLocation.drawTextBox();
		drawCenterString(ModMain.MOD_NAME+" v"+ModMain.MOD_VERSION, width/2-74, height/2-41-21, 20, Color.ORANGE.getRGB());
		drawRightString(I18n.format("cursormod.config.xhotspot")+" : ", xhotspot.xPosition, xhotspot.yPosition,
				xhotspot.height, Color.WHITE.getRGB());
		drawRightString(I18n.format("cursormod.config.yhotspot")+" : ", yhotspot.xPosition, yhotspot.yPosition,
				yhotspot.height, Color.WHITE.getRGB());
		drawRightString(I18n.format("cursormod.config.location")+" : ", cursorLocation.xPosition, cursorLocation.yPosition,
				cursorLocation.height, Color.WHITE.getRGB());
		if(syncImageSize()) {
			mc.getTextureManager().bindTexture(ModMain.getCursorResourceLocation().getResourceLocation());
			drawGradientRect(width/2+36, height/2-64, width/2+164, height/2+64, -1072689136, -804253680);
			drawScaledCustomSizeModalRect(width/2+36, height/2-64, 0, 0, imageWidth, imageHeight, 128, 128, imageWidth, imageHeight);
			if(ModMain.xHotspot >= 0 && ModMain.xHotspot < imageWidth && ModMain.yHotspot >= 0 && ModMain.yHotspot < imageHeight)
				drawCenteredString(fontRendererObj, "+", width/2+36+((int)(((float)ModMain.xHotspot)*128F/(float)imageWidth)), 
						height/2-64 + ((int)(((float)ModMain.yHotspot)*128F/(float)imageHeight))-fontRendererObj.FONT_HEIGHT/2, 
						Color.WHITE.getRGB());
		} else drawCenteredString(fontRendererObj, I18n.format("cursormod.gui.error"), width/2+100, 
					height/2-fontRendererObj.FONT_HEIGHT/2, Color.RED.getRGB());
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	private boolean syncImageSize() {
		try {
			BufferedImage image = ImageIO.read(ModMain.getCursorResourceLocation().getInputStream());
		    imageWidth = image.getWidth();
		    imageHeight = image.getHeight();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	private void verifyValue() {
		boolean flag = syncImageSize();
		try {
			ModMain.xHotspot = Integer.valueOf(xhotspot.getText());
			if(ModMain.xHotspot >= 0 && ModMain.xHotspot < imageWidth) {
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
			ModMain.yHotspot = Integer.valueOf(yhotspot.getText());
			if(ModMain.yHotspot >= 0 && ModMain.yHotspot < imageHeight) {
				yhotspot.setTextColor(Color.WHITE.getRGB());
			} else {
				yhotspot.setTextColor(Color.RED.getRGB());
				flag = false;
			}
		} catch (Exception e) {
			yhotspot.setTextColor(Color.RED.getRGB());
			flag = false;
		}
		ModMain.cursorLocation = cursorLocation.getText();
		doneButton.enabled = flag;
		saveReloadButton.enabled = flag;
	}
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) { //done
			ModMain.saveConfig();
			mc.displayGuiScreen(parent);
		} else if(button.id == 1) { //cancel
			ModMain.cursorLocation = this.oldCursorLocation;
			ModMain.xHotspot = this.oldXHotspot;
			ModMain.yHotspot = this.oldYHotspot;
			ModMain.saveConfig();
			mc.displayGuiScreen(parent);
		} else if(button.id == 2) { //save
			ModMain.saveConfig();
		}
		super.actionPerformed(button);
	}
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		xhotspot.textboxKeyTyped(typedChar, keyCode);
		yhotspot.textboxKeyTyped(typedChar, keyCode);
		cursorLocation.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
	public void updateScreen() {
		xhotspot.updateCursorCounter();
		yhotspot.updateCursorCounter();
		cursorLocation.updateCursorCounter();
		verifyValue();
		super.updateScreen();
	}
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		xhotspot.mouseClicked(mouseX, mouseY, mouseButton);
		yhotspot.mouseClicked(mouseX, mouseY, mouseButton);
		cursorLocation.mouseClicked(mouseX, mouseY, mouseButton);
		if(mouseX>=width/2+36 && mouseY>=height/2-64 && mouseX<=width/2+164 && mouseY<=height/2+64) {
			xhotspot.setText(String.valueOf(ModMain.xHotspot = MathHelper.clamp_int((int) ((float)(mouseX - (width/2+36))*(float)imageWidth/128F), 0, imageWidth-1)));
			yhotspot.setText(String.valueOf(ModMain.yHotspot = MathHelper.clamp_int((int) ((float)(mouseY - (height/2-64))*(float)imageHeight/128F), 0, imageWidth-1)));
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	private void drawRightString(String text, int x, int y, int color) {
		drawRightString(text, x, y, fontRendererObj.FONT_HEIGHT, color);
	}

	private void drawRightString(String text, int x, int y, int HEIGHT,
			int color) {
		fontRendererObj.drawStringWithShadow(text, x - fontRendererObj.getStringWidth(text),
				y + HEIGHT / 2 - fontRendererObj.FONT_HEIGHT / 2, color);
	}
	private void drawCenterString(String text, int x, int y, int color) {
		drawCenterString(text, x, y, fontRendererObj.FONT_HEIGHT, color);
	}

	private void drawCenterString(String text, int x, int y, int HEIGHT,
			int color) {
		fontRendererObj.drawStringWithShadow(text, x - fontRendererObj.getStringWidth(text) / 2,
				y + HEIGHT / 2 - fontRendererObj.FONT_HEIGHT / 2, color);
	}
}
