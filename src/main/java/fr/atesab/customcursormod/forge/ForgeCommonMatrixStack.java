package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.atesab.customcursormod.common.handler.BasicHandler;

public class ForgeCommonMatrixStack extends BasicHandler<PoseStack> implements CommonMatrixStack {
	public ForgeCommonMatrixStack(PoseStack handle) {
		super(handle);
	}
}
