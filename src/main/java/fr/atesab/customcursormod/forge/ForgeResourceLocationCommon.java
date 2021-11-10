package fr.atesab.customcursormod.forge;

import java.io.IOException;
import java.io.InputStream;

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
	public void bind() {
		Minecraft.getInstance().getTextureManager().bindForSetup(resource);
	}

	@Override
	public InputStream openStream() throws IOException {
		return Minecraft.getInstance().getResourceManager().getResource(resource).getInputStream();
	}
}
