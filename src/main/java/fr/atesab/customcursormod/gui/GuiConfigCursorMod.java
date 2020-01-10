package fr.atesab.customcursormod.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.mojang.blaze3d.platform.GlStateManager;

import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorMod;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiConfigCursorMod extends Screen {
	private Screen parent;
	private Map<CursorType, CursorConfig> cursors;
	private List<GuiValueButton<CursorType>> cursorButtons = new ArrayList<GuiValueButton<CursorType>>();
	private Button lastPage;
	private Button nextPage;

	private int page = 0;
	private int elementByPage = 1;

	public GuiConfigCursorMod(Screen parent) {
		super(new TranslationTextComponent("cursormod.gui.cursorList"));
		this.parent = parent;
		this.cursors = CursorMod.getCursors();
	}

	private void defineButton() {
		if (lastPage != null)
			lastPage.active = page > 0;
		if (nextPage != null)
			nextPage.active = page < cursors.size() / elementByPage;
		for (int i = 0; i < cursorButtons.size(); i++)
			cursorButtons.get(i).visible = i < (page + 1) * elementByPage && i >= (page) * elementByPage;
	}

	private void drawCursor(int posX, int posY, CursorConfig cursorConfig) {
		try {
			BufferedImage image = ImageIO.read(cursorConfig.getResource().getInputStream());
			int imageWidth = image.getWidth();
			int imageHeight = image.getWidth();
			int numImage = image.getHeight() / image.getWidth();
			GuiUtils.drawGradientRect((float) blitOffset, posX, posY, posX + 20, posY + 20, -1072689136, -804253680);
			getMinecraft().getTextureManager().bindTexture(cursorConfig.getResourceLocation());
			GlStateManager.color3f(1.0F, 1.0F, 1.0F);
			GuiUtils.drawScaledCustomSizeModalRect(posX, posY, 0, 0, imageWidth, imageHeight, 20, 20, imageWidth,
					imageHeight * numImage);
		} catch (Exception e) {
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		drawCenteredString(font, CursorMod.MOD_NAME + " v" + CursorMod.MOD_VERSION, width / 2,
				height / 2 - ((elementByPage / 2 + 1) * 21 + 2 + font.FONT_HEIGHT), Color.ORANGE.getRGB());
		for (int i = page * elementByPage; i < cursorButtons.size() && i < (page + 1) * elementByPage; i++) {
			GuiValueButton<CursorType> button = cursorButtons.get(i);
			drawCursor(button.x + button.getWidth() + 1, button.y, cursors.get(button.getValue()));
		}
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void init() {
		page = 0;
		elementByPage = (height - 63) / 21;
		if (elementByPage > 10)
			elementByPage = 10;
		addButton(new Button(width / 2 - 79, height / 2 + (elementByPage / 2) * 21, 158, 20, I18n.format("gui.done"),
				b -> getMinecraft().displayGuiScreen(parent)));
		if (elementByPage <= cursors.size()) {
			addButton(lastPage = new Button(width / 2 - 100, height / 2 + (elementByPage / 2) * 21, 20, 20, "<", b -> {
				page--;
				defineButton();
			}));
			addButton(nextPage = new Button(width / 2 + 80, height / 2 + (elementByPage / 2) * 21, 20, 20, ">", b -> {
				page++;
				defineButton();
			}));
		} else
			lastPage = nextPage = null;
		int i = -1;
		cursorButtons.clear();
		for (CursorType type : cursors.keySet())
			cursorButtons.add(new GuiValueButton<CursorType>(width / 2 - 100,
					height / 2 + ((i++) % elementByPage - elementByPage / 2) * 21, 200, 20, type.getName(), type) {
				@Override
				public void onPress() {
					getMinecraft()
							.displayGuiScreen(new GuiCursorConfig(GuiConfigCursorMod.this, type, cursors.get(type)) {
								protected void saveConfig(CursorConfig cursorConfig) {
									CursorMod.replaceCursor(type, cursorConfig);
								}
							});
				}
			});
		buttons.addAll(cursorButtons);
		children.addAll(cursorButtons);
		defineButton();
		super.init();
	}
}
