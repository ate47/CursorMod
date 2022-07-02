package fr.atesab.customcursormod.fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.common.handler.ResourceLocationCommon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class FabricResourceLocationCommon extends ResourceLocationCommon {
	private final Identifier resource;

	public FabricResourceLocationCommon(String link) {
		resource = new Identifier(link);
	}

	public FabricResourceLocationCommon(Identifier resource) {
		this.resource = resource;
	}

	@Override
	public void bindForSetup() {
		MinecraftClient.getInstance().getTextureManager().bindTexture(resource);
	}

	@Override
	public void setShaderTexture() {
		RenderSystem.setShaderTexture(0, resource);
	}

	@Override
	public InputStream openStream() throws IOException {
		Optional<Resource> res = MinecraftClient.getInstance().getResourceManager().getResource(resource);
		if (res.isEmpty()) {
			return null;
		}
		return res.get().getInputStream();
	}

}
