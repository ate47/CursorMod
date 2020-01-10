package fr.atesab.customcursormod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.gui.GuiConfig;
import fr.atesab.customcursormod.gui.GuiSelectZone;
import fr.atesab.customcursormod.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseClickedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.gui.GuiModList;
import net.minecraftforge.fml.client.gui.GuiSlotModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

@OnlyIn(Dist.CLIENT)
@Mod(CursorMod.MOD_ID)
public class CursorMod {
	public static final String MOD_ID = "customcursormod";
	public static final String MOD_NAME = "Custom Cursor Mod";
	public static final String MOD_VERSION = "1.2.2";
	public static final String MOD_GUI_FACTORY = "fr.atesab.customcursormod.gui.GuiFactoryCursorMod";
	public static final Logger logger = Logger.getLogger(MOD_NAME);
	private static boolean forceNextCursor = false;
	private static CursorType currentCursorType = CursorType.POINTER;
	private static Map<CursorType, CursorConfig> cursors = new HashMap<CursorType, CursorConfig>();
	private static List<CursorClick> cursorClicks = new ArrayList<CursorClick>();
	private static Configuration config;

	private static void changeCursor(CursorType cursor) {
		changeCursor(cursor, forceNextCursor);
		forceNextCursor = false;
	}

	private static void changeCursor(CursorType cursor, boolean forceChange) {
		if (!forceChange && cursor == currentCursorType)
			return;
		currentCursorType = cursor;
		CursorConfig cursorConfig = cursors.getOrDefault(cursor, cursor.getDefaultConfig());
		long cursorPtr = cursorConfig.getCursor();
		if (cursorPtr == MemoryUtil.NULL)
			throw new NullPointerException();
		GLFW.glfwSetCursor(Minecraft.getInstance().func_228018_at_().getHandle(), cursorPtr); // getMainWindow
	}

	/**
	 * Force the next cursor to change (if config were change for example)
	 */
	public static void forceNextCursor() {
		forceNextCursor = config.dynamicCursor;
	}

	/**
	 * @return the configuration
	 */
	public static Configuration getConfig() {
		return config;
	}

	/**
	 * @return the map of all {@link CursorConfig} by there {@link CursorType}
	 */
	public static Map<CursorType, CursorConfig> getCursors() {
		return cursors;
	}

	/**
	 * Register new {@link CursorType} (Work only before FML Post Initialization)
	 * 
	 * @param cursorTypes
	 *            the cursors
	 */
	public static void registerCursor(CursorType... cursorTypes) {
		for (CursorType cursorType : cursorTypes)
			cursors.put(cursorType, cursorType.getDefaultConfig());
	}

	/**
	 * replace a {@link CursorType} config
	 * 
	 * @param type
	 *            the cursor type
	 * @param config
	 *            the new cursor config
	 */
	public static void replaceCursor(CursorType type, CursorConfig config) {
		CursorConfig old = cursors.put(type, config);
		if (type == currentCursorType)
			changeCursor(type, true);
		if (old.isAllocate())
			old.freeCursor();
	}

	/**
	 * Save mod config
	 */
	public static void saveConfig() {
		config.save();
	}

	public CursorMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);
		config = new Configuration();
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
				() -> (mc, parent) -> new GuiConfig(parent));

		registerCursor(currentCursorType = CursorType.POINTER, CursorType.HAND, CursorType.HAND_GRAB, CursorType.BEAM,
				CursorType.CROSS);
	}

	private void checkModList(Screen screen) {
		// enabling the config button
		if (screen != null && screen instanceof GuiModList) {
			/* GuiSlotModList.ModEntry */ Object entry = getFirstFieldOfTypeInto(
					GuiSlotModList.class.getDeclaredClasses()[0], screen);
			if (entry != null) {
				ModInfo info = getFirstFieldOfTypeInto(ModInfo.class, entry);
				if (info != null) {
					Optional<? extends ModContainer> op = ModList.get().getModContainerById(info.getModId());
					if (op.isPresent()) {
						boolean value = op.get().getCustomExtension(ExtensionPoint.CONFIGGUIFACTORY).isPresent();
						String config = I18n.format("fml.menu.mods.config");
						for (IGuiEventListener b : screen.children())
							if (b instanceof Button && ((Button) b).getMessage().equals(config))
								((Button) b).active = value;
					}
				}
			}
		}
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

	private boolean isHoverButton(int mouseX, int mouseY, Button button) {
		return button != null && button.visible && button.active
				&& isHover(mouseX, mouseY, button.x, button.y, button.getWidth(), button.getHeight());
	}

	private boolean isHoverTextField(int mouseX, int mouseY, TextFieldWidget textField) {
		return textField != null && textField.getVisible()
				&& isHover(mouseX, mouseY, textField.x, textField.y, textField.getWidth(), textField.getHeight());
	}

	@SubscribeEvent
	public void onDrawScreen(DrawScreenEvent.Post ev) {
		Screen gui = ev.getGui();
		CursorType newCursorType = CursorType.POINTER;
		if (config.dynamicCursor) {
			for (Field[] fa : getDeclaredField(gui.getClass()))
				for (Field f : fa) {
					try {
						f.setAccessible(true);
						Object o = f.get(gui);
						if (o == null)
							continue;
						else if (o instanceof TextFieldWidget) {
							if (isHoverTextField(ev.getMouseX(), ev.getMouseY(), (TextFieldWidget) o))
								newCursorType = CursorType.BEAM;
						} else if (o instanceof Button) {
							if (isHoverButton(ev.getMouseX(), ev.getMouseY(), (Button) o))
								newCursorType = CursorType.HAND;
						} else if (o instanceof GuiSelectZone) {
							GuiSelectZone selectZone = (GuiSelectZone) o;
							if (isHover(ev.getMouseX(), ev.getMouseY(), selectZone.xPosition, selectZone.yPosition,
									selectZone.width, selectZone.height) && selectZone.enable)
								newCursorType = CursorType.CROSS;
						} else if (o instanceof List) {
							for (Object e : (List<?>) o)
								if (e instanceof Button) {
									if (isHoverButton(ev.getMouseX(), ev.getMouseY(), (Button) e))
										newCursorType = CursorType.HAND;
								} else if (e instanceof TextFieldWidget) {
									if (isHoverTextField(ev.getMouseX(), ev.getMouseY(), (TextFieldWidget) e))
										newCursorType = CursorType.BEAM;
								} else if (e instanceof GuiSelectZone) {
									GuiSelectZone selectZone = (GuiSelectZone) e;
									if (isHover(ev.getMouseX(), ev.getMouseY(), selectZone.xPosition,
											selectZone.yPosition, selectZone.width, selectZone.height)
											&& selectZone.enable)
										newCursorType = CursorType.CROSS;
								} else
									break;
						}
					} catch (Exception e) {
					}
				}
			if (gui instanceof ContainerScreen) {
				ContainerScreen<?> container = (ContainerScreen<?>) gui;
				if (gui.getMinecraft().player.inventory.getItemStack() != null
						&& !gui.getMinecraft().player.inventory.getItemStack().getItem().equals(Items.AIR))
					newCursorType = CursorType.HAND_GRAB;
				else if (container.getSlotUnderMouse() != null && container.getSlotUnderMouse().getHasStack())
					newCursorType = CursorType.HAND;
			} else if (gui instanceof ChatScreen) {
				ITextComponent ichatcomponent = gui.getMinecraft().ingameGUI.getChatGUI().getTextComponent(
						gui.getMinecraft().mouseHelper.getMouseX(), gui.getMinecraft().mouseHelper.getMouseY());
				if (ichatcomponent != null && ichatcomponent.getStyle().getClickEvent() != null)
					newCursorType = CursorType.HAND;
			}
			for (CursorType cursorType : cursors.keySet())
				if (cursorType.getCursorTester() != null && cursorType.getCursorTester().testCursor(newCursorType, gui,
						ev.getMouseX(), ev.getMouseY(), ev.getRenderPartialTicks())) {
					newCursorType = cursorType;
					break;
				}
		}
		changeCursor(newCursorType);
		if (config.clickAnimation) {
			Iterator<CursorClick> iterator = cursorClicks.iterator();
			while (iterator.hasNext()) {
				CursorClick cursorClick = iterator.next();
				int posX = (int) cursorClick.getPosX();
				int posY = (int) cursorClick.getPosY();
				gui.getMinecraft().getTextureManager().bindTexture(
						new ResourceLocation("textures/gui/click_" + (2 - cursorClick.getTime() / 4) + ".png"));
				RenderSystem.color3f(1.0F, 1.0F, 1.0F);
				GuiUtils.drawScaledCustomSizeModalRect(posX - 8, posY - 8, 0, 0, 16, 16, 16, 16, 16, 16);
				cursorClick.descreaseTime();
				if (cursorClick.getTime() <= 0) {
					iterator.remove();
				}
			}
		}
	}

	@SubscribeEvent
	public void onGuiCloses(TickEvent ev) {
		if (cursorClicks.size() != 0 && Minecraft.getInstance().currentScreen == null)
			cursorClicks.clear();
	}

	@SubscribeEvent
	public void onGuiCloses(ClientTickEvent ev) {
		if (ev.phase == TickEvent.Phase.END)
			checkModList(Minecraft.getInstance().currentScreen);
	}

	@SubscribeEvent
	public void onInitScreen(InitGuiEvent.Post ev) {
		forceNextCursor();
	}

	@SubscribeEvent
	public void onMouseClicked(MouseClickedEvent.Pre ev) {
		if (ev.getButton() == 0 && config.clickAnimation)
			cursorClicks.add(new CursorClick(11, ev.getMouseX(), ev.getMouseY()));
	}

	private void setup(FMLLoadCompleteEvent ev) {
		config.sync(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".toml").toFile());
		cursors.values().forEach(CursorConfig::getCursor); // force allocation
	}

}
