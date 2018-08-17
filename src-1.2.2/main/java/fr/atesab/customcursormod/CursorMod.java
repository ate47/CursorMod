package fr.atesab.customcursormod;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.atesab.customcursormod.gui.GuiSelectZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = CursorMod.MOD_ID, name = CursorMod.MOD_NAME, version = CursorMod.MOD_VERSION, guiFactory = CursorMod.MOD_GUI_FACTORY)
public class CursorMod {
	public static final String MOD_ID = "customcursormod";
	public static final String MOD_NAME = "Custom Cursor Mod";
	public static final String MOD_VERSION = "1.2.2";
	public static final String MOD_GUI_FACTORY = "fr.atesab.customcursormod.gui.GuiFactoryCursorMod";
	public static final Logger logger = Logger.getLogger(MOD_NAME);
	private static final Gson gson = new GsonBuilder().create();
	public static boolean dynamicCursor = true;
	public static boolean clickAnimation = true;
	private static boolean fmlPostInitiated = false;
	private static boolean forceNextCursor = false;
	private static CursorType currentCursorType;
	private static Map<CursorType, CursorConfig> cursors = new HashMap<CursorType, CursorConfig>();
	private static List<CursorClick> cursorClicks = new ArrayList<CursorClick>();
	private static Configuration config;

	private static void changeCursor(CursorType cursor) {
		changeCursor(cursor, forceNextCursor);
		forceNextCursor = false;
	}

	private static void changeCursor(CursorType cursor, boolean forceChange) {
		if (cursor.equals(currentCursorType) && !forceChange)
			return;
		currentCursorType = cursor;
		try {
			CursorConfig cursorConfig = cursors.getOrDefault(cursor, cursor.getDefaultConfig());
			BufferedImage image = ImageIO.read(cursorConfig.getResource().getInputStream());
			int w = image.getWidth();
			int h = image.getHeight();
			int numImg = image.getHeight() / image.getWidth();
			int[] pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
			IntBuffer buffer = BufferUtils.createIntBuffer(w * h);
			IntBuffer delayBuffer = BufferUtils.createIntBuffer(numImg * 2);
			for (int i = 0; i < numImg; i++) {
				delayBuffer.put(cursor.getAnimationDelays());
				for (int y = i * w; y < (i + 1) * w; y++)
					for (int x = 0; x < w; x++)
						buffer.put(pixels[((i + 1) * w - 1 - y) * w + x]);
			}
			buffer.flip();
			Mouse.setNativeCursor(new Cursor(w, w, cursorConfig.getxHotSpot(), w - 1 - cursorConfig.getyHotSpot(),
					numImg, buffer, numImg != 1 ? delayBuffer : null)); // set cursor
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Force the next cursor to change (if config were change for example)
	 */
	public static void forceNextCursor() {
		forceNextCursor = dynamicCursor;
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
		if (!fmlPostInitiated) {
			for (CursorType cursorType : cursorTypes)
				cursors.put(cursorType, cursorType.getDefaultConfig());
		} else
			logger.log(Level.SEVERE, "Can't register new CursorType after FML Post Initialization.");
	}

	/**
	 * Save mod config
	 */
	public static void saveConfig() {
		for (CursorType type : cursors.keySet()) {
			String s = gson.toJson(cursors.getOrDefault(type, cursors.get(CursorType.POINTER)));
			config.get("cursors", type.getConfigName(), s).set(s);
		}
		config.get("data", "dynamicCursor", true).set(dynamicCursor);
		config.get("data", "clickAnimation", true).set(clickAnimation);
		changeCursor(currentCursorType, true);
		config.save();
	}

	public static void setCursors(Map<CursorType, CursorConfig> cursors) {
		CursorMod.cursors = cursors;
	}

	/**
	 * Sync mod config with the config file
	 */
	public static void syncConfig() {
		for (CursorType type : cursors.keySet()) {
			try {
				cursors.put(type,
						gson.fromJson(
								config.getString(type.getConfigName(), "cursors",
										gson.toJson(cursors.getOrDefault(type, cursors.get(CursorType.POINTER))), ""),
								CursorConfig.class));
			} catch (Exception e) {
				logger.warning("Can't load the cursor \"" + type.getConfigName() + "\" : " + e.getMessage());
			}
		}
		dynamicCursor = config.getBoolean("dynamicCursor", "data", true, "");
		clickAnimation = config.getBoolean("clickAnimation", "data", true, "");
		changeCursor(currentCursorType, true);
		config.save();
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
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

	private boolean isHoverButton(int mouseX, int mouseY, GuiButton button) {
		return button != null && button.visible && button.enabled
				&& isHover(mouseX, mouseY, button.xPosition, button.yPosition, button.width, button.height);
	}

	private boolean isHoverTextField(int mouseX, int mouseY, GuiTextField textField) {
		return textField != null && textField.getVisible()
				&& isHover(mouseX, mouseY, textField.xPosition, textField.yPosition, textField.width, textField.height);
	}

	@EventHandler
	public void onConfigChanged(OnConfigChangedEvent ev) {
		if (ev.modID.equals(CursorMod.MOD_ID))
			syncConfig();
	}

	@SubscribeEvent
	public void onInitScreen(InitGuiEvent ev) {
		forceNextCursor();
	}

	@SubscribeEvent
	public void onDrawScreen(DrawScreenEvent ev) {
		GuiScreen gui = ev.gui;
		CursorType newCursorType = CursorType.POINTER;
		if (dynamicCursor) {
			for (Field[] fa : getDeclaredField(gui.getClass()))
				for (Field f : fa) {
					try {
						f.setAccessible(true);
						Object o = f.get(gui);
						if (o == null)
							continue;
						else if (o instanceof GuiTextField) {
							if (isHoverTextField(ev.mouseX, ev.mouseY, (GuiTextField) o))
								newCursorType = CursorType.BEAM;
						} else if (o instanceof GuiButton) {
							if (isHoverButton(ev.mouseX, ev.mouseY, (GuiButton) o))
								newCursorType = CursorType.HAND;
						} else if (o instanceof GuiSelectZone) {
							GuiSelectZone selectZone = (GuiSelectZone) o;
							if (isHover(ev.mouseX, ev.mouseY, selectZone.xPosition, selectZone.yPosition,
									selectZone.width, selectZone.height) && selectZone.enable)
								newCursorType = CursorType.CROSS;
						} else if (o instanceof List) {
							for (Object e : (List<?>) o)
								if (e instanceof GuiButton) {
									if (isHoverButton(ev.mouseX, ev.mouseY, (GuiButton) e))
										newCursorType = CursorType.HAND;
								} else if (e instanceof GuiTextField) {
									if (isHoverTextField(ev.mouseX, ev.mouseY, (GuiTextField) e))
										newCursorType = CursorType.BEAM;
								} else if (e instanceof GuiSelectZone) {
									GuiSelectZone selectZone = (GuiSelectZone) e;
									if (isHover(ev.mouseX, ev.mouseY, selectZone.xPosition, selectZone.yPosition,
											selectZone.width, selectZone.height) && selectZone.enable)
										newCursorType = CursorType.CROSS;
								} else
									break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			if (gui instanceof GuiContainer) {
				GuiContainer container = (GuiContainer) gui;
				if (gui.mc.thePlayer.inventory.getItemStack() != null)
					newCursorType = CursorType.HAND_GRAB;
				else if (container.getSlotUnderMouse() != null && container.getSlotUnderMouse().getHasStack())
					newCursorType = CursorType.HAND;
			} else if (gui instanceof GuiChat) {
				IChatComponent ichatcomponent = gui.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(),
						Mouse.getY());
				if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatClickEvent() != null)
					newCursorType = CursorType.HAND;
			}
			for (CursorType cursorType : cursors.keySet())
				if (cursorType.getCursorTester() != null && cursorType.getCursorTester().testCursor(newCursorType, gui,
						ev.mouseX, ev.mouseY, ev.renderPartialTicks)) {
					newCursorType = cursorType;
					break;
				}
		}
		changeCursor(newCursorType);
		if (clickAnimation) {
			Iterator<CursorClick> iterator = cursorClicks.iterator();
			while (iterator.hasNext()) {
				CursorClick cursorClick = iterator.next();
				int posX = (int) (gui.width * cursorClick.getPosX());
				int posY = gui.height - (int) (gui.height * cursorClick.getPosY());
				gui.mc.getTextureManager().bindTexture(
						new ResourceLocation("textures/gui/click_" + (2 - cursorClick.getTime() / 4) + ".png"));
				GlStateManager.color(1.0F, 1.0F, 1.0F);
				ev.gui.drawScaledCustomSizeModalRect(posX - 8, posY - 8, 0, 0, 16, 16, 16, 16, 16, 16);
				cursorClick.descreaseTime();
				if (cursorClick.getTime() <= 0) {
					iterator.remove();
				}
			}
		}
	}

	@SubscribeEvent
	public void onGuiCloses(TickEvent ev) {
		if (cursorClicks.size() != 0 && Minecraft.getMinecraft().currentScreen == null)
			cursorClicks.clear();
	}

	@SubscribeEvent
	public void onMouseInput(MouseInputEvent ev) {
		if (Mouse.isButtonDown(0) && clickAnimation) {
			ScaledResolution scaledResolution = new ScaledResolution(ev.gui.mc, ev.gui.mc.displayWidth,
					ev.gui.mc.displayHeight);
			cursorClicks.add(new CursorClick(11,
					(Mouse.getX() / scaledResolution.getScaleFactor()) / scaledResolution.getScaledWidth_double(),
					(Mouse.getY() / scaledResolution.getScaleFactor()) / scaledResolution.getScaledHeight_double()));
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent ev) {
		fmlPostInitiated = true;
		config.load();
		syncConfig();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent ev) {
		// create default config
		registerCursor(currentCursorType = CursorType.POINTER, CursorType.HAND, CursorType.HAND_GRAB, CursorType.BEAM,
				CursorType.CROSS);
		config = new Configuration(ev.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
}
