package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.atesab.customcursormod.common.handler.BasicHandler;

public class ForgeCommonMatrixStack extends BasicHandler<PoseStack> implements CommonMatrixStack {
	public ForgeCommonMatrixStack(PoseStack handle) {
		super(handle);
	}

	@Override
	public void scale(float x, float y, float z) {
		handle.scale(x, y, z);
	}

	@Override
	public void setIdentity() {
		handle.setIdentity();
	}

	@Override
	public void translate(float x, float y, float z) {
		handle.translate(x, y, z);
	}
}
