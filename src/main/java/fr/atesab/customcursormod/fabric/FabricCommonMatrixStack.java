package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.BasicHandler;
import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import net.minecraft.client.util.math.MatrixStack;

public class FabricCommonMatrixStack extends BasicHandler<MatrixStack> implements CommonMatrixStack {
	public FabricCommonMatrixStack(MatrixStack handle) {
		super(handle);
	}
}
