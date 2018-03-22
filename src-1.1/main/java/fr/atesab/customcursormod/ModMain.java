package fr.atesab.customcursormod;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModMain.MOD_ID, name=ModMain.MOD_NAME, version=ModMain.MOD_VERSION,
	guiFactory = ModMain.MOD_GUI_FACTORY)
public class ModMain {
	public static final String MOD_ID = "customcursormod";
	public static final String MOD_NAME = "Custom Cursor Mod";
	public static final String MOD_VERSION = "1.1";
	public static final String MOD_GUI_FACTORY = "fr.atesab.customcursormod.GuiFactoryCursorMod";
	
	private static Configuration config;
	public static Configuration getConfig() {
		return config;
	}
	public static int xHotspot = 0;
	public static int yHotspot = 0;
	public static String cursorLocation = "textures/gui/customcursor.png";
	public static void changeCursor(IResource cursor) {
		try {
			BufferedImage image = ImageIO.read(cursor.getInputStream());
		    int w = image.getWidth();
		    int h = image.getHeight();
		    int[] pixels = new int[w*h];
		    image.getRGB(0, 0, w, h, pixels, 0, w);
		    ByteBuffer buffer = BufferUtils.createByteBuffer(w*h*4);
		    for (int y = 0; y < h; y++)
		        for (int x = 0; x < w; x++)
		        {
		            int pixel = pixels[(h-1-y)*w+x]; //load pixel & flip them
		            buffer.put((byte) (pixel & 0xFF));			// red
		            buffer.put((byte) ((pixel >> 8) & 0xFF));	// green
		            buffer.put((byte) ((pixel >> 16) & 0xFF));	// blue
		            buffer.put((byte) ((pixel >> 24) & 0xFF));	// alpha
		        }
		    buffer.flip();
		    Mouse.setNativeCursor(new Cursor(w, h, xHotspot, h-1-yHotspot, 1, buffer.asIntBuffer(), null)); //set cursor
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void syncConfig() {
		if(config.hasChanged()) {
			xHotspot = config.getInt("xHotspot", Configuration.CATEGORY_GENERAL, xHotspot, Integer.MIN_VALUE, Integer.MAX_VALUE,
					"", "cursormod.config.xhotspot");
			yHotspot = config.getInt("yHotspot", Configuration.CATEGORY_GENERAL, yHotspot, Integer.MIN_VALUE, Integer.MAX_VALUE,
					"", "cursormod.config.yHotspot");
			cursorLocation = config.getString("cursorLocation", Configuration.CATEGORY_GENERAL, cursorLocation, 
					"", "cursormod.config.location");
			changeCursor(getCursorResourceLocation());
			config.save();
		}
	}
    @EventHandler
    public void onConfigChanged(OnConfigChangedEvent ev) {
    	if(ev.modID.equals(ModMain.MOD_ID)) syncConfig();
    }
    @EventHandler
	public void preInit(FMLPreInitializationEvent ev) {
    	config = new Configuration(ev.getSuggestedConfigurationFile());
    	config.load();
	}
    @EventHandler
	public void init(FMLPostInitializationEvent ev) {
		changeCursor(getCursorResourceLocation());
	}
    public static IResource getCursorResourceLocation() {
    	try {
    		return Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(cursorLocation));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	
    }
	public static void saveConfig() {
		config.get(Configuration.CATEGORY_GENERAL, "xHotspot", xHotspot).set(xHotspot);
		config.get(Configuration.CATEGORY_GENERAL, "yHotspot", yHotspot).set(yHotspot);
		config.get(Configuration.CATEGORY_GENERAL, "cursorLocation", cursorLocation).set(cursorLocation);
		changeCursor(getCursorResourceLocation());
		config.save();
	}
}
