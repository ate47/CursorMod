package fr.atesab.customcursormod;

import java.io.File;
import java.util.Map;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

public class Configuration {
	public boolean dynamicCursor = true;

	public boolean clickAnimation = true;

	private CommentedFileConfig config;

	public boolean isClickAnimation() {
		return clickAnimation;
	}

	public boolean isDynamicCursor() {
		return dynamicCursor;
	}

	public void save() {
		Map<CursorType, CursorConfig> cursors = CursorMod.getCursors();
		for (CursorType type : cursors.keySet())
			cursors.getOrDefault(type, cursors.get(CursorType.POINTER)).write("cursors." + type.getConfigName(),
					config);
		config.set("general.dynamicCursor", dynamicCursor);
		config.set("general.clickAnimation", clickAnimation);
		config.save();
	}
	public void setClickAnimation(boolean clickAnimation) {
		this.clickAnimation = clickAnimation;
	}

	public void setDynamicCursor(boolean dynamicCursor) {
		this.dynamicCursor = dynamicCursor;
	}

	public void sync(File path) {
		// load config
		config = CommentedFileConfig.builder(path).sync().writingMode(WritingMode.REPLACE).build();
		// load file
		config.load();
		// bypassing the non mutable (or please help me because I can't find how)
		// ForgeSpec

		config.setComment("general.dynamicCursor", "dynamicCursor");
		dynamicCursor = config.getOrElse("general.dynamicCursor", dynamicCursor);

		config.setComment("general.clickAnimation", "clickAnimation");
		clickAnimation = config.getOrElse("general.clickAnimation", clickAnimation);

		Map<CursorType, CursorConfig> cursors = CursorMod.getCursors();
		for (CursorType type : cursors.keySet()) {
			try {
				CursorConfig old = cursors.put(type,
						CursorConfig.read("cursors." + type.getConfigName(), config, type.getDefaultConfig()));
				if (old != null)
					old.freeCursor();
			} catch (Exception e) {
				CursorMod.logger.warning("Can't load the cursor \"" + type.getConfigName() + "\" : " + e.getMessage());
			}
		}
		config.save();
	}
}
