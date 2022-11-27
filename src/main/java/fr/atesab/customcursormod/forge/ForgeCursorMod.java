package fr.atesab.customcursormod.forge;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;

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
import fr.atesab.customcursormod.forge.ForgeCommonScreen.ForgeCommonScreenHandler;
import fr.atesab.customcursormod.forge.gui.ForgeGuiSelectZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.client.gui.widget.ModListWidget;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
@Mod(CursorMod.MOD_ID)
public class ForgeCursorMod {
	private final CursorMod mod = new CursorMod(GameType.FORGE);
	static {
		SelectZone.SUPPLIER.forType(GameType.FORGE,
				o -> new ForgeGuiSelectZone(o.xPosition, o.yPosition, o.width, o.height));
		GuiUtils.SUPPLIER.forType(GameType.FORGE, ForgeGuiUtils::getForge);
		TranslationCommonText.SUPPLIER.forType(GameType.FORGE,
				obj -> new ForgeTranslationCommonTextImpl(obj.format, obj.args));
		StringCommonText.SUPPLIER.forType(GameType.FORGE, ForgeStringCommonTextImpl::new);
		ResourceLocationCommon.SUPPLIER.forType(GameType.FORGE, ForgeResourceLocationCommon::new);
		CommonButton.SUPPLIER.forType(GameType.FORGE, ForgeCommonButton::new);
		CommonTextField.SUPPLIER.forType(GameType.FORGE, ForgeCommonTextField::new);
		CommonScreen.SUPPLIER.forType(GameType.FORGE, ForgeCommonScreen::new);
		CommonScreen.SUPPLIER_CURRENT.forType(GameType.FORGE,
				v -> new ForgeBasicCommonScreen(Minecraft.getInstance().screen));
		fr.atesab.customcursormod.common.utils.I18n.SUPPLIER.forType(GameType.FORGE,
				obj -> I18n.get(obj.format, obj.args));
		CommonShaders.SUPPLIER.forType(GameType.FORGE, ForgeCommonShaders::getForge);
	}

	public ForgeCursorMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc,
																																						 parent) -> ((ForgeCommonScreen) GuiConfig.create(new ForgeBasicCommonScreen(parent))).getHandle()));
	}

	private void checkModList(Screen screen) {
		// enabling the config button
		if (screen instanceof ModListScreen) {
			ModListWidget.ModEntry entry = getFirstFieldOfTypeInto(ModListWidget.ModEntry.class, screen);
			if (entry != null) {
				var info = entry.getInfo();
				if (info != null) {
					Optional<? extends ModContainer> op = ModList.get().getModContainerById(info.getModId());
					if (op.isPresent()) {
						boolean value = op.get().getCustomExtension(ConfigScreenHandler.ConfigScreenFactory.class).isPresent();
						String configText = I18n.get("fml.menu.mods.config");
						for (var b : screen.children())
							if (b instanceof Button && ((Button) b).getMessage().getString().equals(configText))
								((Button) b).active = value;
					}
				}
			}
		}
	}

	private List<Field[]> getDeclaredField(Class<?> cls) {
		List<Field[]> l = new ArrayList<>();
		l.add(cls.getDeclaredFields());
		while (!cls.equals(Object.class)) {
			cls = cls.getSuperclass();
			l.add(cls.getDeclaredFields());
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	private <T> T getFirstFieldOfTypeInto(Class<T> cls, Object obj) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.getType() == cls)
				try {
					return (T) f.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					return null;
				}
		}
		return null;
	}

	private boolean isHover(int mouseX, int mouseY, int x, int y, int width, int height) {
		x = Math.min(x + width, x);
		y = Math.min(y + height, y);
		width = Math.abs(width);
		height = Math.abs(height);
		return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
	}

	private boolean isHoverButton(int mouseX, int mouseY, AbstractButton button) {
		return button != null && button.visible && button.active
				&& isHover(mouseX, mouseY, button.x, button.y, button.getWidth(), button.getHeight());
	}

	private boolean isHoverTextField(int mouseX, int mouseY, EditBox textField) {
		return textField != null && textField.isVisible()
				&& isHover(mouseX, mouseY, textField.x, textField.y, textField.getWidth(), textField.getHeight());
	}

	@SubscribeEvent
	public void onDrawScreen(ScreenEvent.Render.Post ev) {
		Screen gui = ev.getScreen();
		CursorType newCursorType = CursorType.POINTER;
		if (mod.getConfig().dynamicCursor) {
			if (gui instanceof ForgeCommonScreenHandler handle) { // Our menu
				CommonScreen cs = handle.cs;
				for (CommonElement o : cs.childrens) {
					if (!o.isEnable())
						continue;
					if (o.isHover(ev.getMouseX(), ev.getMouseY())) {
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
							if (o == null) {
								continue;
							}

							if (o instanceof EditBox) {
								if (isHoverTextField(ev.getMouseX(), ev.getMouseY(), (EditBox) o))
									newCursorType = CursorType.BEAM;
							} else if (o instanceof AbstractButton b) {
								if (isHoverButton(ev.getMouseX(), ev.getMouseY(), b))
									newCursorType = CursorType.HAND;
							} else if (o instanceof SelectZone selectZone) {
								if (isHover(ev.getMouseX(), ev.getMouseY(), selectZone.getXPosition(),
										selectZone.getYPosition(), selectZone.getWidth(), selectZone.getHeight())
										&& selectZone.isEnable())
									newCursorType = CursorType.CROSS;
							} else if (o instanceof Iterable) {
								for (Object e : (Iterable<?>) o)
									if (e instanceof AbstractButton b) {
										if (isHoverButton(ev.getMouseX(), ev.getMouseY(), b))
											newCursorType = CursorType.HAND;
									} else if (e instanceof EditBox b) {
										if (isHoverTextField(ev.getMouseX(), ev.getMouseY(), b))
											newCursorType = CursorType.BEAM;
									} else if (e instanceof SelectZone selectZone) {
										if (selectZone.isHover(ev.getMouseX(), ev.getMouseY()) && selectZone.isEnable())
											newCursorType = CursorType.CROSS;
									} else
										break;
							}
						} catch (Exception e) {
							// ignore
						}
					}
			if (gui instanceof AbstractContainerScreen<?> container) {
				LocalPlayer player = gui.getMinecraft().player;
				if (player != null && !player.containerMenu.getCarried().getItem().equals(Items.AIR))
					newCursorType = CursorType.HAND_GRAB;
				else if (container.getSlotUnderMouse() != null && container.getSlotUnderMouse().hasItem())
					newCursorType = CursorType.HAND;
			} else if (gui instanceof ChatScreen) {
				Minecraft mc = gui.getMinecraft();
				int mx = (int) (mc.mouseHandler.xpos() * (double) mc.getWindow().getGuiScaledWidth()
						/ (double) mc.getWindow().getScreenWidth());
				int my = (int) (mc.mouseHandler.ypos() * (double) mc.getWindow().getGuiScaledHeight()
						/ (double) mc.getWindow().getScreenHeight());
				Style style = mc.gui.getChat().getClickedComponentStyleAt(mx, my);
				if (style != null && style.getClickEvent() != null)
					newCursorType = CursorType.HAND;
			}

			CommonScreen commonScreen;
			if (gui instanceof ForgeCommonScreenHandler handler) {
				commonScreen = handler.cs;
			} else {
				commonScreen = new ForgeBasicCommonScreen(gui);
			}

			for (CursorType cursorType : mod.getCursors().keySet())
				if (cursorType.getCursorTester() != null && cursorType.getCursorTester().testCursor(newCursorType,
						commonScreen, ev.getMouseX(), ev.getMouseY(), ev.getPartialTick())) {
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
				ForgeGuiUtils.getForge().setShader(ForgeCommonShaders.getForge().getPositionTexShader());
				RenderSystem.setShaderTexture(0,
						new ResourceLocation("textures/gui/click_" + cursorClick.getImage() + ".png"));
				ForgeGuiUtils.getForge().drawScaledCustomSizeModalRect(posX - 8, posY - 8, 0, 0, 16, 16, 16, 16, 16, 16,
						0xffffffff, true);
				cursorClick.descreaseTime(ev.getPartialTick());
				if (cursorClick.getTime() <= 0) {
					iterator.remove();
				}
			}
		}
	}

	@SubscribeEvent
	public void onGuiCloses(TickEvent ev) {
		if (!mod.getCursorClicks().isEmpty() && Minecraft.getInstance().screen == null)
			mod.getCursorClicks().clear();
	}

	@SubscribeEvent
	public void onGuiCloses(ClientTickEvent ev) {
		if (ev.phase == TickEvent.Phase.END) {
			checkModList(Minecraft.getInstance().screen);
			mod.waiter.tick();
		}
	}

	@SubscribeEvent
	public void onInitScreen(ScreenEvent.Init.Post ev) {
		mod.forceNextCursor();
	}

	@SubscribeEvent
	public void onMouseClicked(ScreenEvent.MouseButtonPressed.Pre ev) {
		if (ev.getButton() == 0 && mod.getConfig().clickAnimation)
			mod.getCursorClicks().add(new CursorClick(ev.getMouseX(), ev.getMouseY()));
	}

	private void setup(FMLLoadCompleteEvent ev) {
		File saveDir = new File(Minecraft.getInstance().gameDirectory, "config");
		try {
			Files.createDirectories(saveDir.toPath());
		} catch (IOException e) {
			throw new RuntimeException("can't create directories: " + saveDir, e);
		}
		File save = new File(saveDir, CursorMod.MOD_ID + ".json");
		mod.getConfig().sync(save);
		mod.getConfig().sync(save);
		mod.getCursors().values().forEach(CursorConfig::getCursor); // force allocation
		mod.loadData(Minecraft.getInstance().getWindow().getWindow());
	}

}
