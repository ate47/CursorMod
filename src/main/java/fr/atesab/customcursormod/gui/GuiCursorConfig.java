package fr.atesab.customcursormod.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class GuiCursorConfig extends Screen {
	private Screen parent;
	private TextFieldWidget xhotspot;
	private TextFieldWidget yhotspot;
	private TextFieldWidget cursorLocation;
	private Button doneButton;
	private GuiSelectZone selectZone;
	private int imageWidth = 1;
	private int imageHeight = 1;
	private int numImage = 1;

	private final CursorType type;
	private CursorConfig cursorConfig;

	public GuiCursorConfig(Screen parent, CursorType type, CursorConfig cursorConfig) {
		super(new TranslationTextComponent("cursormod.gui.configCursor"));
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
		font.drawStringWithShadow(text, x - font.getStringWidth(text) / 2, y + HEIGHT / 2 - font.FONT_HEIGHT / 2,
				color);
	}

	private void drawRightString(String text, int x, int y, int HEIGHT, int color) {
		font.drawStringWithShadow(text, x - font.getStringWidth(text), y + HEIGHT / 2 - font.FONT_HEIGHT / 2, color);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		xhotspot.render(mouseX, mouseY, partialTicks);
		yhotspot.render(mouseX, mouseY, partialTicks);
		cursorLocation.render(mouseX, mouseY, partialTicks);
		drawCenterString(type.getName(), width / 2 - 74, height / 2 - 41 - 21, 20, Color.ORANGE.getRGB());
		drawRightString(I18n.format("cursormod.config.xhotspot") + " : ", xhotspot.x, xhotspot.y, xhotspot.getHeight(),
				Color.WHITE.getRGB());
		drawRightString(I18n.format("cursormod.config.yhotspot") + " : ", yhotspot.x, yhotspot.y, yhotspot.getHeight(),
				Color.WHITE.getRGB());
		drawRightString(I18n.format("cursormod.config.location") + " : ", cursorLocation.x, cursorLocation.y,
				cursorLocation.getHeight(), Color.WHITE.getRGB());
		if (syncImageSize()) {
			getMinecraft().getTextureManager().bindTexture(cursorConfig.getResourceLocation());
			GuiUtils.drawGradientRect((float) getBlitOffset(), width / 2 + 36, height / 2 - 64, width / 2 + 164,
					height / 2 + 64, -1072689136, -804253680);
			RenderSystem.color3f(1, 1, 1);
			GuiUtils.drawScaledCustomSizeModalRect(width / 2 + 36, height / 2 - 64, 0, 0, imageWidth, imageHeight, 128,
					128, imageWidth, imageHeight * numImage);
			if (cursorConfig.getxHotSpot() >= 0 && cursorConfig.getxHotSpot() < imageWidth
					&& cursorConfig.getyHotSpot() >= 0 && cursorConfig.getyHotSpot() < imageHeight)
				drawCenteredString(font, "+",
						width / 2 + 36 + ((int) (((float) cursorConfig.getxHotSpot()) * 128F / (float) imageWidth)),
						height / 2 - 64 + ((int) (((float) cursorConfig.getyHotSpot()) * 128F / (float) imageHeight))
								- font.FONT_HEIGHT / 2,
						Color.WHITE.getRGB());
			if (numImage > 1)
				drawCenteredString(font, "(" + I18n.format("cursormod.gui.animate") + ")", width / 2 + 100,
						height / 2 + 64 + 1, Color.WHITE.getRGB());
			selectZone.enable = true;
		} else {
			drawCenteredString(font, I18n.format("cursormod.gui.error"), width / 2 + 100,
					height / 2 - font.FONT_HEIGHT / 2, Color.RED.getRGB());
			selectZone.enable = false;
		}
		super.render(mouseX, mouseY, partialTicks);
	}

	public void init() {
		syncImageSize();
		xhotspot = new TextFieldWidget(font, width / 2 - 99, height / 2 - 41, 124, 18, "");
		yhotspot = new TextFieldWidget(font, width / 2 - 99, height / 2 - 20, 124, 18, "");
		cursorLocation = new TextFieldWidget(font, width / 2 - 99, height / 2 + 1, 124, 18, "");
		cursorLocation.setMaxStringLength(Integer.MAX_VALUE);
		updateCursorValues(cursorConfig);
		children.add(selectZone = new GuiSelectZone(width / 2 + 36, height / 2 - 64, 128, 128));
		addButton(new Button(width / 2 - 174, height / 2 + 21, 200, 20, I18n.format("cursormod.gui.default"),
				b -> updateCursorValues(type.getDefaultConfig())));

		addButton(doneButton = new Button(width / 2 - 174, height / 2 + 42, 100, 20, I18n.format("gui.done"), b -> {
			saveConfig(cursorConfig);
			getMinecraft().displayGuiScreen(parent);
		}));
		addButton(new Button(width / 2 - 72, height / 2 + 42, 99, 20, I18n.format("cursormod.gui.cancel"),
				b -> getMinecraft().displayGuiScreen(parent)));
		super.init();
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
		doneButton.active = flag;
	}
}
