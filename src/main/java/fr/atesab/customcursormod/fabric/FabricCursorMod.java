package fr.atesab.customcursormod.fabric;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.DISPATCHER;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.arguments.BoolArgumentType;

import fr.atesab.customcursormod.common.CursorMod;
import fr.atesab.customcursormod.common.config.CursorConfig;
import fr.atesab.customcursormod.common.cursor.CursorClick;
import fr.atesab.customcursormod.common.cursor.CursorType;
import fr.atesab.customcursormod.common.cursor.SelectZone;
import fr.atesab.customcursormod.common.gui.GuiConfig;
import fr.atesab.customcursormod.common.handler.CommonButton;
import fr.atesab.customcursormod.common.handler.CommonElement;
import fr.atesab.customcursormod.common.handler.CommonScreen;
import fr.atesab.customcursormod.common.handler.CommonShaders;
import fr.atesab.customcursormod.common.handler.CommonTextField;
import fr.atesab.customcursormod.common.handler.GameType;
import fr.atesab.customcursormod.common.handler.GuiUtils;
import fr.atesab.customcursormod.common.handler.ResourceLocationCommon;
import fr.atesab.customcursormod.common.handler.StringCommonText;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;
import fr.atesab.customcursormod.fabric.FabricCommonScreen.FabricCommonScreenHandler;
import fr.atesab.customcursormod.fabric.gui.FabricGuiSelectZone;
import fr.atesab.customcursormod.fabric.mixin.HandledScreenMixin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStarted;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class FabricCursorMod implements ClientModInitializer, ClientTickEvents.StartTick, ScreenEvents.AfterInit,
		ScreenEvents.AfterRender, ScreenEvents.AfterTick, ScreenMouseEvents.AfterMouseClick, ClientStarted {
	static {
		SelectZone.SUPPLIER.forType(GameType.FABRIC,
				o -> new FabricGuiSelectZone(o.xPosition, o.yPosition, o.width, o.height));
		GuiUtils.SUPPLIER.forType(GameType.FABRIC, FabricGuiUtils::getFabric);
		TranslationCommonText.SUPPLIER.forType(GameType.FABRIC,
				obj -> new FabricTranslationCommonTextImpl(obj.format, obj.args));
		StringCommonText.SUPPLIER.forType(GameType.FABRIC, FabricStringCommonTextImpl::new);
		ResourceLocationCommon.SUPPLIER.forType(GameType.FABRIC, FabricResourceLocationCommon::new);
		CommonButton.SUPPLIER.forType(GameType.FABRIC, FabricCommonButton::new);
		CommonTextField.SUPPLIER.forType(GameType.FABRIC, FabricCommonTextField::new);
		CommonScreen.SUPPLIER.forType(GameType.FABRIC, FabricCommonScreen::new);
		CommonScreen.SUPPLIER_CURRENT.forType(GameType.FABRIC,
				v -> new FabricBasicCommonScreen(MinecraftClient.getInstance().currentScreen));
		fr.atesab.customcursormod.common.utils.I18n.SUPPLIER.forType(GameType.FABRIC,
				obj -> I18n.translate(obj.format, obj.args));
		CommonShaders.SUPPLIER.forType(GameType.FABRIC, FabricCommonShaders::getFabric);
	}

	private CursorMod mod = new CursorMod(GameType.FABRIC);
	public final TranslatableText yes = new TranslatableText("cursormod.config.yes");
	public final TranslatableText no = new TranslatableText("cursormod.config.no");

	@Override
	public void onInitializeClient() {
		DISPATCHER.register(literal("cursormod")
				.then(literal("dynamicCursor").then(argument("dc_value", BoolArgumentType.bool()).executes(c -> {
					mod.getConfig().dynamicCursor = BoolArgumentType.getBool(c, "dc_value");
					c.getSource().sendFeedback(new TranslatableText("cursormod.config.dynCursor").append(": ")
							.append(mod.getConfig().dynamicCursor ? yes : no));
					return 1;
				})).executes(c -> {
					c.getSource().sendFeedback(new TranslatableText("cursormod.config.dynCursor").append(": ")
							.append(mod.getConfig().dynamicCursor ? yes : no));
					return 1;
				})).then(literal("clickAnimation").then(argument("ca_value", BoolArgumentType.bool()).executes(c -> {
					mod.getConfig().clickAnimation = BoolArgumentType.getBool(c, "ca_value");
					c.getSource().sendFeedback(new TranslatableText("cursormod.config.clickAnim").append(": ")
							.append(mod.getConfig().clickAnimation ? yes : no));
					return 1;
				})).executes(c -> {
					c.getSource().sendFeedback(new TranslatableText("cursormod.config.clickAnim").append(": ")
							.append(mod.getConfig().clickAnimation ? yes : no));
					return 1;
				})).executes(c -> {
					mod.waiter.register(GuiConfig.create(CommonScreen.getCurrent()));
					return 0;
				}));
		ScreenEvents.AFTER_INIT.register(this);
		ClientLifecycleEvents.CLIENT_STARTED.register(this);
		ClientTickEvents.START_CLIENT_TICK.register(this);
	}

	private List<Field[]> getDeclaredField(Class<?> cls) {
		List<Field[]> l = new ArrayList<Field[]>();
		l.add(cls.getDeclaredFields());
		while (!cls.equals(Object.class)) {
			cls = cls.getSuperclass();
			l.add(cls.getDeclaredFields());
		}
		return l;
	}

	private boolean isHover(int mouseX, int mouseY, int x, int y, int width, int height) {
		x = Math.min(x + width, x);
		y = Math.min(y + height, y);
		width = Math.abs(width);
		height = Math.abs(height);
		return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
	}

	private boolean isHoverButton(int mouseX, int mouseY, PressableWidget button) {
		return button != null && button.visible && button.active
				&& isHover(mouseX, mouseY, button.x, button.y, button.getWidth(), button.getHeight());
	}

	private boolean isHoverTextField(int mouseX, int mouseY, TextFieldWidget textField) {
		return textField != null && textField.isVisible()
				&& isHover(mouseX, mouseY, textField.x, textField.y, textField.getWidth(), textField.getHeight());
	}

	@Override
	public void afterInit(MinecraftClient client, Screen screen, int scaledWidth, int scaledHeight) {
		mod.forceNextCursor();
		ScreenEvents.afterRender(screen).register(this);
		ScreenEvents.afterTick(screen).register(this);
		ScreenMouseEvents.afterMouseClick(screen).register(this);
	}

	public static Slot getSlotUnderMouse(HandledScreen<?> screen) {
		return ((HandledScreenMixin) screen).getFocusedSlot();
	}

	@Override
	public void afterRender(Screen gui, MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
		CursorType newCursorType = CursorType.POINTER;
		if (mod.getConfig().dynamicCursor) {
			if (gui instanceof FabricCommonScreenHandler handle) { // Our menu
				CommonScreen cs = handle.cs;
				for (CommonElement o : cs.childrens) {
					if (!o.isEnable())
						continue;
					if (o.isHover(mouseX, mouseY)) {
						if (o instanceof CommonTextField) {
							newCursorType = CursorType.BEAM;
						} else if (o instanceof CommonButton) {
							newCursorType = CursorType.HAND;
						} else if (o instanceof SelectZone) {
							newCursorType = CursorType.CROSS;
						}
					}
				}
			} else
				for (Field[] fa : getDeclaredField(gui.getClass()))
					for (Field f : fa) {
						try {
							f.setAccessible(true);
							Object o = f.get(gui);
							if (o == null)
								continue;
							else if (o instanceof TextFieldWidget) {
								if (isHoverTextField(mouseX, mouseY, (TextFieldWidget) o))
									newCursorType = CursorType.BEAM;
							} else if (o instanceof PressableWidget b) {
								if (isHoverButton(mouseX, mouseY, b))
									newCursorType = CursorType.HAND;
							} else if (o instanceof SelectZone) {
								SelectZone selectZone = (SelectZone) o;
								if (selectZone.isHover(mouseX, mouseY) && selectZone.isEnable())
									newCursorType = CursorType.CROSS;
							} else if (o instanceof Iterable) {
								for (Object e : (Iterable<?>) o)
									if (e instanceof PressableWidget b) {
										if (isHoverButton(mouseX, mouseY, b))
											newCursorType = CursorType.HAND;
									} else if (e instanceof TextFieldWidget) {
										if (isHoverTextField(mouseX, mouseY, (TextFieldWidget) e))
											newCursorType = CursorType.BEAM;
									} else if (e instanceof SelectZone) {
										SelectZone selectZone = (SelectZone) e;
										if (isHover(mouseX, mouseY, selectZone.getXPosition(),
												selectZone.getYPosition(), selectZone.getWidth(),
												selectZone.getHeight()) && selectZone.isEnable())
											newCursorType = CursorType.CROSS;
									} else
										break;
							}
						} catch (Exception e) {
						}
					}
			MinecraftClient mc = MinecraftClient.getInstance();
			if (gui instanceof HandledScreen<?> container) {
				if (mc.player.currentScreenHandler.getCursorStack() != null
						&& !mc.player.currentScreenHandler.getCursorStack().getItem().equals(Items.AIR))
					newCursorType = CursorType.HAND_GRAB;
				else {
					Slot slot = getSlotUnderMouse(container);
					if (slot != null && slot.hasStack())
						newCursorType = CursorType.HAND;
				}
			} else if (gui instanceof ChatScreen) {
				int mx = (int) (mc.mouse.getX() * (double) mc.getWindow().getScaledWidth()
						/ (double) mc.getWindow().getWidth());
				int my = (int) (mc.mouse.getY() * (double) mc.getWindow().getScaledHeight()
						/ (double) mc.getWindow().getHeight());
				Style style = mc.inGameHud.getChatHud().getText(mx, my);
				if (style != null && style.getClickEvent() != null)
					newCursorType = CursorType.HAND;
			}

			CommonScreen commonScreen;
			if (gui instanceof FabricCommonScreenHandler handler) {
				commonScreen = handler.cs;
			} else {
				commonScreen = new FabricBasicCommonScreen(gui);
			}

			for (CursorType cursorType : mod.getCursors().keySet())
				if (cursorType.getCursorTester() != null && cursorType.getCursorTester().testCursor(newCursorType,
						commonScreen, mouseX, mouseY, tickDelta)) {
					newCursorType = cursorType;
					break;
				}
		}
		mod.changeCursor(newCursorType);
		if (mod.getConfig().clickAnimation) {
			Iterator<CursorClick> iterator = mod.getCursorClicks().iterator();
			while (iterator.hasNext()) {
				CursorClick cursorClick = iterator.next();
				int posX = (int) cursorClick.getPosX();
				int posY = (int) cursorClick.getPosY();
				FabricGuiUtils.getFabric().setShader(FabricCommonShaders.getFabric().getPositionTexShader());
				RenderSystem.setShaderTexture(0,
						new Identifier("textures/gui/click_" + (2 - cursorClick.getTime() / 3) + ".png"));
				FabricGuiUtils.getFabric().drawScaledCustomSizeModalRect(posX - 8, posY - 8, 0, 0, 16, 16, 16, 16, 16,
						16, 0xffffffff, true);
				cursorClick.descreaseTime();
				if (cursorClick.getTime() <= 0) {
					iterator.remove();
				}
			}
		}
	}

	@Override
	public void afterMouseClick(Screen screen, double mouseX, double mouseY, int button) {
		if (button == 0 && mod.getConfig().clickAnimation)
			mod.getCursorClicks().add(new CursorClick(6, mouseX, mouseY));
	}

	@Override
	public void afterTick(Screen screen) {
		if (!mod.getCursorClicks().isEmpty() && MinecraftClient.getInstance().currentScreen == null)
			mod.getCursorClicks().clear();
	}

	@Override
	public void onClientStarted(MinecraftClient client) {
		File saveDir = new File(client.runDirectory, "config");
		saveDir.mkdirs();
		File save = new File(saveDir, CursorMod.MOD_ID + ".json");
		mod.getConfig().sync(save);
		mod.getCursors().values().forEach(CursorConfig::getCursor); // force allocation
		mod.loadData(client.getWindow().getHandle());
	}

	@Override
	public void onStartTick(MinecraftClient client) {
		mod.waiter.tick();
	}
}
