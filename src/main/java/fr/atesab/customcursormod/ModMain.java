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
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = ModMain.ModId, name="Custom Cursor Mod", version="1.0")
public class ModMain {
	public static final String ModId="customcursormod";
	public static void changeCursor(ResourceLocation cursor) {
		try {
			BufferedImage image = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(cursor).getInputStream());
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
		    Mouse.setNativeCursor(new Cursor(w, h, 0, h-1, 1, buffer.asIntBuffer(), null)); //set cursor
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @EventHandler
	public void init(FMLPostInitializationEvent ev) {
		changeCursor(new ResourceLocation("textures/gui/customcursor.png"));
	}
}
