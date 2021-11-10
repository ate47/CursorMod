package fr.atesab.customcursormod.common.handler;

import java.io.IOException;
import java.io.InputStream;

public abstract class ResourceLocationCommon {
	public static final CommonSupplier<String, ResourceLocationCommon> SUPPLIER = new CommonSupplier<>(false);

	public static ResourceLocationCommon create(String link) {
		return SUPPLIER.fetch(link);
	}

	/**
	 * bind the texture
	 */
	public abstract void setShaderTexture();

	/**
	 * bind the texture
	 */
	public abstract void bindForSetup();

	/**
	 * @return open the resource location as a stream
	 */
	public abstract InputStream openStream() throws IOException;
}
