package fr.atesab.customcursormod.common.gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.common.CursorMod;
import fr.atesab.customcursormod.common.config.CursorConfig;
import fr.atesab.customcursormod.common.cursor.CursorType;
import fr.atesab.customcursormod.common.handler.CommonButton;
import fr.atesab.customcursormod.common.handler.CommonButtonValue;
import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonScreen;
import fr.atesab.customcursormod.common.handler.GuiUtils;
import fr.atesab.customcursormod.common.handler.StringCommonText;
import fr.atesab.customcursormod.common.handler.CommonScreen.ScreenListener;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;
import fr.atesab.customcursormod.common.utils.Color;
import fr.atesab.customcursormod.common.utils.I18n;

public class GuiConfigCursorMod extends ScreenListener {

	public static CommonScreen create(CommonScreen parent) {
		return CommonScreen.create(parent, TranslationCommonText.create("cursormod.gui.cursorList"),
				new GuiConfigCursorMod());
	}

	private Map<CursorType, CursorConfig> cursors;
	private List<CommonButtonValue<CursorType>> cursorButtons = new ArrayList<>();
	private CommonButton lastPage;
	private CommonButton nextPage;

	private int page = 0;
	private int elementByPage = 1;

	private GuiConfigCursorMod() {
		this.cursors = CursorMod.getInstance().getCursors();
	}

	private void defineButton() {
		if (lastPage != null)
			lastPage.setEnable(page > 0);
		if (nextPage != null)
			nextPage.setEnable(page < cursors.size() / elementByPage);
		for (int i = 0; i < cursorButtons.size(); i++)
			cursorButtons.get(i).setVisible(i < (page + 1) * elementByPage && i >= (page) * elementByPage);
	}

	private void drawCursor(int posX, int posY, CursorConfig cursorConfig) {
		try {
			BufferedImage image = ImageIO.read(cursorConfig.getResource());
			int imageWidth = image.getWidth();
			int imageHeight = image.getWidth();
			int numImage = image.getHeight() / image.getWidth();
			GuiUtils.get().drawGradientRect(getScreen().getBlitOffset(), posX, posY, posX + 20, posY + 20, -1072689136,
					-804253680);
			cursorConfig.getResourceLocation().bind();
			RenderSystem.color3f(1.0F, 1.0F, 1.0F);
			GuiUtils.get().drawScaledCustomSizeModalRect(posX, posY, 0, 0, imageWidth, imageHeight, 20, 20, imageWidth,
					imageHeight * numImage);
		} catch (Exception e) {
			// hide
		}
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		CommonScreen screen = getScreen();
		screen.renderDefaultBackground(stack);
		screen.drawCenterString(stack, I18n.get("cursormod.gui.cursorList"), width / 2,
				height / 2 - ((elementByPage / 2 + 1) * 21 + 2 + GuiUtils.get().fontHeight()), Color.ORANGE);
		for (int i = page * elementByPage; i < cursorButtons.size() && i < (page + 1) * elementByPage; i++) {
			CommonButtonValue<CursorType> button = cursorButtons.get(i);
			drawCursor(button.getXPosition() + button.getWidth() + 1, button.getYPosition(),
					cursors.get(button.getValue()));
		}
		super.render(stack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void init() {
		CommonScreen screen = getScreen();
		page = 0;
		elementByPage = (height - 63) / 21;
		if (elementByPage > 10)
			elementByPage = 10;
		screen.addChildren(CommonButton.create(TranslationCommonText.create("gui.done"), width / 2 - 79,
				height / 2 + (elementByPage / 2) * 21, 158, 20, b -> screen.getParent().displayScreen()));
		if (elementByPage <= cursors.size()) {
			lastPage = screen.addChildren(CommonButton.create(StringCommonText.create("<"), width / 2 - 100,
					height / 2 + (elementByPage / 2) * 21, 20, 20, b -> {
						page--;
						defineButton();
					}));
			nextPage = screen.addChildren(CommonButton.create(StringCommonText.create(">"), width / 2 + 80,
					height / 2 + (elementByPage / 2) * 21, 20, 20, b -> {
						page++;
						defineButton();
					}));
		} else
			lastPage = nextPage = null;
		int i = -1;
		cursorButtons.clear();
		for (Entry<CursorType, CursorConfig> entry : cursors.entrySet()) {
			CursorType type = entry.getKey();
			CursorConfig cfg = entry.getValue();
			cursorButtons.add(CommonButtonValue.<CursorType>create(type, type.getTranslation(), width / 2 - 100,
					height / 2 + ((i++) % elementByPage - elementByPage / 2) * 21, 200, 20,
					b -> GuiCursorConfig
							.create(screen, type, cfg,
									cursorConfig -> CursorMod.getInstance().replaceCursor(type, cursorConfig))
							.displayScreen()));
		}
		screen.childrens.addAll(cursorButtons);
		defineButton();
		super.init();
	}
}
