package fr.atesab.customcursormod.forge;

import java.util.function.Supplier;

import fr.atesab.customcursormod.common.handler.BasicHandler;
import fr.atesab.customcursormod.common.handler.CommonShader;
import net.minecraft.client.renderer.ShaderInstance;

public class ForgeCommonShader extends BasicHandler<Supplier<ShaderInstance>> implements CommonShader {

    public ForgeCommonShader(Supplier<ShaderInstance> handle) {
        super(handle);
    }

}
