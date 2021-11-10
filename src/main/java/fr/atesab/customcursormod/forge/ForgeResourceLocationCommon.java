package fr.atesab.customcursormod.forge;

import java.io.IOException;
import java.io.InputStream;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.common.handler.ResourceLocationCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ForgeResourceLocationCommon extends ResourceLocationCommon {

	private ResourceLocation resource;

	public ForgeResourceLocationCommon(String link) {
		resource = new ResourceLocation(link);
	}

	public ForgeResourceLocationCommon(ResourceLocation resource) {
		this.resource = resource;
	}

	@Override
	public void setShaderTexture() {
		RenderSystem.setShaderTexture(0, resource);
	}

	@Override
	public void bindForSetup() {
		Minecraft.getInstance().getTextureManager().bindForSetup(resource);
	}

	@Override
	public InputStream openStream() throws IOException {
		return Minecraft.getInstance().getResourceManager().getResource(resource).getInputStream();
	}
}
