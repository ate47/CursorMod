package fr.atesab.customcursormod.common.config;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;

import fr.atesab.customcursormod.common.handler.ResourceLocationCommon;

public class CursorConfig {
	public static class CursorConfigStore {
		int xHotSpot;
		int yHotSpot;
		String link;
		public CursorConfigStore(CursorConfig current) {
			this();
			xHotSpot = current.xHotSpot;
			yHotSpot = current.yHotSpot;
			link = current.link;
		}
		private CursorConfigStore() {
		}
	}
	public static CursorConfig read(CursorConfigStore store, CursorConfig defaultConfig) {
		CursorConfig cursor = new CursorConfig();
		if (store == null) {
			cursor.xHotSpot = defaultConfig.xHotSpot;
			cursor.yHotSpot = defaultConfig.yHotSpot;
			cursor.link = defaultConfig.link;
		} else {
			cursor.xHotSpot = store.xHotSpot;
			cursor.yHotSpot = store.yHotSpot;
			cursor.link = store.link == null ? defaultConfig.link : store.link;
		}

		return cursor;
	}

	private int xHotSpot;
	private int yHotSpot;
	private String link;
	private long cursor = MemoryUtil.NULL;

	private GLFWImage glfwImage = GLFWImage.create();

	private CursorConfig() {
	}

	public CursorConfig(String link) {
		this(link, 0, 0);
	}

	public CursorConfig(String link, int xHotSpot, int yHotSpot) {
		this.xHotSpot = xHotSpot;
		this.yHotSpot = yHotSpot;
		this.link = link;
	}

	private void allocate() {
		readImage();
		if (cursor != MemoryUtil.NULL)
			freeCursor();
		cursor = GLFW.glfwCreateCursor(glfwImage, getxHotSpot(), getyHotSpot());
	}

	public CursorConfig copy() {
		CursorConfig config = new CursorConfig(link, xHotSpot, yHotSpot);
		return config;
	}

	public void freeCursor() {
		if (isAllocate())
			GLFW.glfwDestroyCursor(cursor);
	}

	public long getCursor() {
		if (cursor == MemoryUtil.NULL)
			allocate();
		return cursor;
	}

	public String getLink() {
		return link;
	}

	public InputStream getResource() {
		try {
			return getResourceLocation().openStream();
		} catch (Exception e) {
			return null;
		}

	}

	public ResourceLocationCommon getResourceLocation() {
		return ResourceLocationCommon.create(link);
	}

	public int getxHotSpot() {
		return xHotSpot;
	}

	public int getyHotSpot() {
		return yHotSpot;
	}

	public boolean isAllocate() {
		return cursor != MemoryUtil.NULL;
	}

	private void readImage() {
		try {
			BufferedImage image = ImageIO.read(getResource());
			int w = image.getWidth();
			int h = image.getHeight();
			int[] pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
			ByteBuffer buffer = BufferUtils.createByteBuffer(w * h * 4);
			for (int y = h - 1; y >= 0; y--)
				for (int x = 0; x < w; x++) {
					int pixel = pixels[(h - 1 - y) * w + x]; // load pixel & flip them
					buffer.put((byte) ((pixel >> 16) & 0xFF)); // blue
					buffer.put((byte) ((pixel >> 8) & 0xFF)); // green
					buffer.put((byte) (pixel & 0xFF)); // red
					buffer.put((byte) ((pixel >> 24) & 0xFF)); // alpha
				}
			buffer.flip();
			glfwImage.pixels(buffer).width(w).height(h);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void setLink(String link) {
		if (cursor != MemoryUtil.NULL)
			allocate();
		this.link = link;
	}

	public void setxHotSpot(int xHotSpot) {
		if (cursor != MemoryUtil.NULL)
			allocate();
		this.xHotSpot = xHotSpot;
	}

	public void setyHotSpot(int yHotSpot) {
		if (cursor != MemoryUtil.NULL)
			allocate();
		this.yHotSpot = yHotSpot;
	}

	public CursorConfigStore write() {
		return new CursorConfigStore(this);
	}
}
