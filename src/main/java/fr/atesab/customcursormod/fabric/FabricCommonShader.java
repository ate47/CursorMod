package fr.atesab.customcursormod.fabric;

import java.util.function.Supplier;

import fr.atesab.customcursormod.common.handler.BasicHandler;
import fr.atesab.customcursormod.common.handler.CommonShader;
import net.minecraft.client.render.Shader;

public class FabricCommonShader extends BasicHandler<Supplier<Shader>> implements CommonShader {

    public FabricCommonShader(Supplier<Shader> handle) {
        super(handle);
    }

}