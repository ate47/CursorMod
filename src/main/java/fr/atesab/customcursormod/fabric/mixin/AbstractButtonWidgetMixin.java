package fr.atesab.customcursormod.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.AbstractButtonWidget;

@Mixin(AbstractButtonWidget.class)
public interface AbstractButtonWidgetMixin {
	@Accessor("height")
	void setHeight(int height);
}
