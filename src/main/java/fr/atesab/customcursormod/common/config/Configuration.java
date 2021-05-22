package fr.atesab.customcursormod.common.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.atesab.customcursormod.common.CursorMod;
import fr.atesab.customcursormod.common.config.CursorConfig.CursorConfigStore;
import fr.atesab.customcursormod.common.cursor.CursorType;

public class Configuration {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	static class ConfigurationStore {
		public boolean dynamicCursor = true;
		public boolean clickAnimation = true;
		public Map<String, CursorConfigStore> cursors = new HashMap<>();
		private ConfigurationStore(Configuration current) {
			this();
			dynamicCursor = current.dynamicCursor;
			clickAnimation = current.clickAnimation;
			Map<CursorType, CursorConfig> currentCursors = CursorMod.getInstance().getCursors();
			for (Entry<CursorType, CursorConfig> e : currentCursors.entrySet()) {
				CursorType type = e.getKey();
				CursorConfig cfg = e.getValue();
				cursors.put(type.getConfigName(), cfg.write());
			}
		}
		private ConfigurationStore(){
		}
	}
	public boolean dynamicCursor = true;
	public boolean clickAnimation = true;

	private File storage;

	public boolean isClickAnimation() {
		return clickAnimation;
	}

	public boolean isDynamicCursor() {
		return dynamicCursor;
	}

	public void save() {
		if (storage != null) {
			ConfigurationStore store = new ConfigurationStore(this);
			try (FileWriter w = new FileWriter(storage)) {
				GSON.toJson(store, w);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void setClickAnimation(boolean clickAnimation) {
		this.clickAnimation = clickAnimation;
	}

	public void setDynamicCursor(boolean dynamicCursor) {
		this.dynamicCursor = dynamicCursor;
	}

	public void sync(File path) {
		storage = path;
		try (FileReader r = new FileReader(storage)) {
			ConfigurationStore store = GSON.fromJson(r, ConfigurationStore.class);
			this.clickAnimation = store.clickAnimation;
			this.dynamicCursor = store.dynamicCursor;
			
			Map<CursorType, CursorConfig> cursors = CursorMod.getInstance().getCursors();
			for (CursorType type : cursors.keySet()) {
				try {
					CursorConfigStore cstore = store.cursors.get(type.getConfigName());
					CursorConfig old = cursors.put(type,
							CursorConfig.read(cstore, type.getDefaultConfig()));
					if (old != null)
						old.freeCursor();
				} catch (Exception e) {
					CursorMod.logger.warning("Can't load the cursor \"" + type.getConfigName() + "\" : " + e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			save();
		}
	}

}
