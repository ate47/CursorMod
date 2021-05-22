package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.BasicHandler;

import com.mojang.blaze3d.matrix.MatrixStack;
public class ForgeCommonMatrixStack extends BasicHandler<MatrixStack> implements CommonMatrixStack {
	public ForgeCommonMatrixStack(MatrixStack handle) {
		super(handle);
	}
}
