package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonShader;
import fr.atesab.customcursormod.common.handler.CommonShaders;
import net.minecraft.client.render.GameRenderer;

public class FabricCommonShaders extends CommonShaders {

    private FabricCommonShaders() {
    }

    private static final FabricCommonShaders instance = new FabricCommonShaders();

    /**
     * @return the instance
     */
    public static FabricCommonShaders getFabric() {
        return instance;
    }

    @Override
    public CommonShader getPositionShader() {
        return new FabricCommonShader(GameRenderer::getPositionShader);
    }

    @Override
    public CommonShader getPositionColorShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorShader);
    }

    @Override
    public CommonShader getPositionColorTexShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorTexShader);
    }

    @Override
    public CommonShader getPositionTexShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexShader);
    }

    @Override
    public CommonShader getPositionTexColorShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexColorShader);
    }

    @Override
    public CommonShader getBlockShader() {
        return new FabricCommonShader(GameRenderer::getBlockShader);
    }

    @Override
    public CommonShader getNewEntityShader() {
        return new FabricCommonShader(GameRenderer::getNewEntityShader);
    }

    @Override
    public CommonShader getParticleShader() {
        return new FabricCommonShader(GameRenderer::getParticleShader);
    }

    @Override
    public CommonShader getPositionColorLightmapShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorLightmapShader);
    }

    @Override
    public CommonShader getPositionColorTexLightmapShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorTexLightmapShader);
    }

    @Override
    public CommonShader getPositionTexColorNormalShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexColorNormalShader);
    }

    @Override
    public CommonShader getPositionTexLightmapColorShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexLightmapColorShader);
    }

    @Override
    public CommonShader getRendertypeSolidShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeSolidShader);
    }

    @Override
    public CommonShader getRendertypeCutoutMippedShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeCutoutMippedShader);
    }

    @Override
    public CommonShader getRendertypeCutoutShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeCutoutShader);
    }

    @Override
    public CommonShader getRendertypeTranslucentShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTranslucentShader);
    }

    @Override
    public CommonShader getRendertypeTranslucentMovingBlockShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTranslucentMovingBlockShader);
    }

    @Override
    public CommonShader getRendertypeTranslucentNoCrumblingShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTranslucentNoCrumblingShader);
    }

    @Override
    public CommonShader getRendertypeArmorCutoutNoCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeArmorCutoutNoCullShader);
    }

    @Override
    public CommonShader getRendertypeEntitySolidShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntitySolidShader);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityCutoutShader);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityCutoutNoNullShader);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullZOffsetShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityCutoutNoNullZOffsetShader);
    }

    @Override
    public CommonShader getRendertypeItemEntityTranslucentCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeItemEntityTranslucentCullShader);
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityTranslucentCullShader);
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityTranslucentShader);
    }

    @Override
    public CommonShader getRendertypeEntitySmoothCutoutShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntitySmoothCutoutShader);
    }

    @Override
    public CommonShader getRendertypeBeaconBeamShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeBeaconBeamShader);
    }

    @Override
    public CommonShader getRendertypeEntityDecalShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityDecalShader);
    }

    @Override
    public CommonShader getRendertypeEntityNoOutlineShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityNoOutlineShader);
    }

    @Override
    public CommonShader getRendertypeEntityShadowShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityShadowShader);
    }

    @Override
    public CommonShader getRendertypeEntityAlphaShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityAlphaShader);
    }

    @Override
    public CommonShader getRendertypeEyesShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEyesShader);
    }

    @Override
    public CommonShader getRendertypeEnergySwirlShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEnergySwirlShader);
    }

    @Override
    public CommonShader getRendertypeLeashShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeLeashShader);
    }

    @Override
    public CommonShader getRendertypeWaterMaskShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeWaterMaskShader);
    }

    @Override
    public CommonShader getRendertypeOutlineShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeOutlineShader);
    }

    @Override
    public CommonShader getRendertypeArmorGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeArmorGlintShader);
    }

    @Override
    public CommonShader getRendertypeArmorEntityGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeArmorEntityGlintShader);
    }

    @Override
    public CommonShader getRendertypeGlintTranslucentShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeGlintTranslucentShader);
    }

    @Override
    public CommonShader getRendertypeGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeGlintShader);
    }

    @Override
    public CommonShader getRendertypeGlintDirectShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeGlintDirectShader);
    }

    @Override
    public CommonShader getRendertypeEntityGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityGlintShader);
    }

    @Override
    public CommonShader getRendertypeEntityGlintDirectShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityGlintDirectShader);
    }

    @Override
    public CommonShader getRendertypeTextShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextShader);
    }

    @Override
    public CommonShader getRendertypeTextIntensityShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextIntensityShader);
    }

    @Override
    public CommonShader getRendertypeTextSeeThroughShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextSeeThroughShader);
    }

    @Override
    public CommonShader getRendertypeTextIntensitySeeThroughShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextIntensitySeeThroughShader);
    }

    @Override
    public CommonShader getRendertypeLightningShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeLightningShader);
    }

    @Override
    public CommonShader getRendertypeTripwireShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTripwireShader);
    }

    @Override
    public CommonShader getRendertypeEndPortalShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEndPortalShader);
    }

    @Override
    public CommonShader getRendertypeEndGatewayShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEndGatewayShader);
    }

    @Override
    public CommonShader getRendertypeLinesShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeLinesShader);
    }

    @Override
    public CommonShader getRendertypeCrumblingShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeCrumblingShader);
    }
}
