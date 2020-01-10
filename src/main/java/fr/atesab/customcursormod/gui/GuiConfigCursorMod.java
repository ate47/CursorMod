package fr.atesab.customcursormod.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorMod;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiConfigCursorMod extends GuiScreen {
	private GuiScreen parent;
	private Map<CursorType, CursorConfig> cursors;
	private List<GuiValueButton<CursorType>> cursorButtons = new ArrayList<GuiValueButton<CursorType>>();
	private GuiButton lastPage;
	private GuiButton nextPage;

	private int page = 0;
	private int elementByPage = 1;

	public GuiConfigCursorMod(GuiScreen parent) {
		this.parent = parent;
		this.cursors = CursorMod.getCursors();
	}

	private void defineButton() {
		if (lastPage != null)
			lastPage.enabled = page > 0;
		if (nextPage != null)
			nextPage.enabled = page < cursors.size() / elementByPage;
		for (int i = 0; i < cursorButtons.size(); i++)
			cursorButtons.get(i).visible = i < (page + 1) * elementByPage && i >= (page) * elementByPage;
	}

	private void drawCursor(int posX, int posY, CursorConfig cursorConfig) {
		try {
			BufferedImage image = ImageIO.read(cursorConfig.getResource().getInputStream());
			int imageWidth = image.getWidth();
			int imageHeight = image.getWidth();
			int numImage = image.getHeight() / image.getWidth();
			drawGradientRect(posX, posY, posX + 20, posY + 20, -1072689136, -804253680);
			mc.getTextureManager().bindTexture(cursorConfig.getResourceLocation());
			GlStateManager.color3f(1.0F, 1.0F, 1.0F);
			drawScaledCustomSizeModalRect(posX, posY, 0, 0, imageWidth, imageHeight, 20, 20, imageWidth,
					imageHeight * numImage);
		} catch (Exception e) {
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, CursorMod.MOD_NAME + " v" + CursorMod.MOD_VERSION, width / 2,
				height / 2 - ((elementByPage / 2 + 1) * 21 + 2 + fontRenderer.FONT_HEIGHT), Color.ORANGE.getRGB());
		for (int i = page * elementByPage; i < cursorButtons.size() && i < (page + 1) * elementByPage; i++) {
			GuiValueButton<CursorType> button = cursorButtons.get(i);
			drawCursor(button.x + button.width + 1, button.y, cursors.get(button.getValue()));
		}
		super.render(mouseX, mouseY, partialTicks);
	}

	public void initGui() {
		page = 0;
		elementByPage = (height - 63) / 21;
		if (elementByPage > 10)
			elementByPage = 10;
		addButton(new GuiButton(0, width / 2 - 79, height / 2 + (elementByPage / 2) * 21, 158, 20,
				I18n.format("gui.done")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				mc.displayGuiScreen(parent);
				super.onClick(mouseX, mouseY);
			}
		});
		if (elementByPage <= cursors.size()) {
			addButton(lastPage = new GuiButton(1, width / 2 - 100, height / 2 + (elementByPage / 2) * 21, 20, 20, "<") {
				@Override
				public void onClick(double mouseX, double mouseY) {
					page--;
					defineButton();
					super.onClick(mouseX, mouseY);
				}
			});
			addButton(nextPage = new GuiButton(2, width / 2 + 80, height / 2 + (elementByPage / 2) * 21, 20, 20, ">") {
				@Override
				public void onClick(double mouseX, double mouseY) {
					page++;
					defineButton();
					super.onClick(mouseX, mouseY);
				}
			});
		} else
			lastPage = nextPage = null;
		int i = -1;
		cursorButtons.clear();
		for (CursorType type : cursors.keySet())
			cursorButtons.add(new GuiValueButton<CursorType>(3, width / 2 - 100,
					height / 2 + ((i++) % elementByPage - elementByPage / 2) * 21, 200, 20, type.getName(), type) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					mc.displayGuiScreen(new GuiCursorConfig(GuiConfigCursorMod.this, type, cursors.get(type)) {
						protected void saveConfig(CursorConfig cursorConfig) {
							CursorMod.replaceCursor(type, cursorConfig);
						}
					});
					super.onClick(mouseX, mouseY);
				}
			});
		buttons.addAll(cursorButtons);
		children.addAll(cursorButtons);
		defineButton();
		super.initGui();
	}
}
