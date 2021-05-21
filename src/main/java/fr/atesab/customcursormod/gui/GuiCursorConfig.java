package fr.atesab.customcursormod.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class GuiCursorConfig extends Screen {
	private static final ITextComponent EMPTY = new StringTextComponent("");
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
		xhotspot.setValue(String.valueOf(cursorConfig.getxHotSpot()));
		yhotspot.setValue(String.valueOf(cursorConfig.getyHotSpot()));
		cursorLocation.setValue(cursorConfig.getLink());
	}

	private void drawCenterString(MatrixStack stack, String text, int x, int y, int HEIGHT, int color) {
		font.drawShadow(stack, text, x - font.width(text) / 2, y + HEIGHT / 2 - font.lineHeight / 2,
				color);
	}

	private void drawRightString(MatrixStack stack, String text, int x, int y, int HEIGHT, int color) {
		font.drawShadow(stack, text, x - font.width(text), y + HEIGHT / 2 - font.lineHeight / 2, color);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(stack);
		xhotspot.render(stack, mouseX, mouseY, partialTicks);
		yhotspot.render(stack, mouseX, mouseY, partialTicks);
		cursorLocation.render(stack, mouseX, mouseY, partialTicks);
		drawCenterString(stack, type.getName(), width / 2 - 74, height / 2 - 41 - 21, 20, Color.ORANGE.getRGB());
		drawRightString(stack, I18n.get("cursormod.config.xhotspot") + " : ", xhotspot.x, xhotspot.y, xhotspot.getHeight(),
				Color.WHITE.getRGB());
		drawRightString(stack, I18n.get("cursormod.config.yhotspot") + " : ", yhotspot.x, yhotspot.y, yhotspot.getHeight(),
				Color.WHITE.getRGB());
		drawRightString(stack, I18n.get("cursormod.config.location") + " : ", cursorLocation.x, cursorLocation.y,
				cursorLocation.getHeight(), Color.WHITE.getRGB());
		if (syncImageSize()) {
			getMinecraft().getTextureManager().bind(cursorConfig.getResourceLocation());
			GuiUtils.drawGradientRect((float) getBlitOffset(), width / 2 + 36, height / 2 - 64, width / 2 + 164,
					height / 2 + 64, -1072689136, -804253680);
			RenderSystem.color3f(1, 1, 1);
			GuiUtils.drawScaledCustomSizeModalRect(width / 2 + 36, height / 2 - 64, 0, 0, imageWidth, imageHeight, 128,
					128, imageWidth, imageHeight * numImage);
			if (cursorConfig.getxHotSpot() >= 0 && cursorConfig.getxHotSpot() < imageWidth
					&& cursorConfig.getyHotSpot() >= 0 && cursorConfig.getyHotSpot() < imageHeight)
				drawCenteredString(stack, font, "+",
						width / 2 + 36 + ((int) (((float) cursorConfig.getxHotSpot()) * 128F / (float) imageWidth)),
						height / 2 - 64 + ((int) (((float) cursorConfig.getyHotSpot()) * 128F / (float) imageHeight))
								- font.lineHeight / 2,
						Color.WHITE.getRGB());
			if (numImage > 1)
				drawCenteredString(stack, font, "(" + I18n.get("cursormod.gui.animate") + ")", width / 2 + 100,
						height / 2 + 64 + 1, Color.WHITE.getRGB());
			selectZone.enable = true;
		} else {
			drawCenteredString(stack, font, I18n.get("cursormod.gui.error"), width / 2 + 100,
					height / 2 - font.lineHeight / 2, Color.RED.getRGB());
			selectZone.enable = false;
		}
		super.render(stack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void init() {
		syncImageSize();
		xhotspot = new TextFieldWidget(font, width / 2 - 99, height / 2 - 41, 124, 18, EMPTY);
		yhotspot = new TextFieldWidget(font, width / 2 - 99, height / 2 - 20, 124, 18, EMPTY);
		cursorLocation = new TextFieldWidget(font, width / 2 - 99, height / 2 + 1, 124, 18, EMPTY);
		cursorLocation.setMaxLength(Integer.MAX_VALUE);
		updateCursorValues(cursorConfig);
		selectZone = new GuiSelectZone(width / 2 + 36, height / 2 - 64, 128, 128);
		children.add(selectZone);
		addButton(new Button(width / 2 - 174, height / 2 + 21, 200, 20, new TranslationTextComponent("cursormod.gui.default"),
				b -> updateCursorValues(type.getDefaultConfig())));

		addButton(doneButton = new Button(width / 2 - 174, height / 2 + 42, 100, 20, new TranslationTextComponent("gui.done"), b -> {
			saveConfig(cursorConfig);
			getMinecraft().setScreen(parent);
		}));
		addButton(new Button(width / 2 - 72, height / 2 + 42, 99, 20, new TranslationTextComponent("cursormod.gui.cancel"),
				b -> getMinecraft().setScreen(parent)));
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
			xhotspot.setValue(String.valueOf(cursorConfig.getxHotSpot()));
			yhotspot.setValue(String.valueOf(cursorConfig.getyHotSpot()));
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
			cursorConfig.setxHotSpot(Integer.valueOf(xhotspot.getValue()));
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
			cursorConfig.setyHotSpot(Integer.valueOf(yhotspot.getValue()));
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
		cursorConfig.setLink(cursorLocation.getValue());
		doneButton.active = flag;
	}
}
