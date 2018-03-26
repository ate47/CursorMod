package fr.atesab.customcursormod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class CursorConfig {
	private int xHotSpot;
	private int yHotSpot;
	private String link;
	public CursorConfig(String link) {
		this(link, 0, 0);
	}
	public CursorConfig(String link, int xHotSpot, int yHotSpot) {
		this.xHotSpot = xHotSpot;
		this.yHotSpot = yHotSpot;
		this.link = link;
	}

    public CursorConfig copy() {
		return new CursorConfig(link, xHotSpot, yHotSpot);
	}
    public String getLink() {
		return link;
	}
	public IResource getResource() {
    	try {
    		return Minecraft.getMinecraft().getResourceManager().getResource(getResourceLocation());
		} catch (Exception e) {
			return null;
		}
    	
    }
	public ResourceLocation getResourceLocation() {
    	return new ResourceLocation(link);
    }
	public int getxHotSpot() {
		return xHotSpot;
	}
	public int getyHotSpot() {
		return yHotSpot;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setxHotSpot(int xHotSpot) {
		this.xHotSpot = xHotSpot;
	}
	public void setyHotSpot(int yHotSpot) {
		this.yHotSpot = yHotSpot;
	}
}
