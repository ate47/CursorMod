package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonShader;
import fr.atesab.customcursormod.common.handler.CommonShaders;
import net.minecraft.client.renderer.GameRenderer;

public class ForgeCommonShaders extends CommonShaders {

    private ForgeCommonShaders() {
    }

    private static final ForgeCommonShaders instance = new ForgeCommonShaders();

    /**
     * @return the instance
     */
    public static ForgeCommonShaders getForge() {
        return instance;
    }

    @Override
    public CommonShader getPositionShader() {
        return new ForgeCommonShader(GameRenderer::getPositionShader);
    }

    @Override
    public CommonShader getPositionColorShader() {
        return new ForgeCommonShader(GameRenderer::getPositionColorShader);
    }

    @Override
    public CommonShader getPositionColorTexShader() {
        return new ForgeCommonShader(GameRenderer::getPositionColorTexShader);
    }

    @Override
    public CommonShader getPositionTexShader() {
        return new ForgeCommonShader(GameRenderer::getPositionTexShader);
    }

    @Override
    public CommonShader getPositionTexColorShader() {
        return new ForgeCommonShader(GameRenderer::getPositionTexColorShader);
    }

    @Override
    public CommonShader getBlockShader() {
        return new ForgeCommonShader(GameRenderer::getBlockShader);
    }

    @Override
    public CommonShader getNewEntityShader() {
        return new ForgeCommonShader(GameRenderer::getNewEntityShader);
    }

    @Override
    public CommonShader getParticleShader() {
        return new ForgeCommonShader(GameRenderer::getParticleShader);
    }

    @Override
    public CommonShader getPositionColorLightmapShader() {
        return new ForgeCommonShader(GameRenderer::getPositionColorLightmapShader);
    }

    @Override
    public CommonShader getPositionColorTexLightmapShader() {
        return new ForgeCommonShader(GameRenderer::getPositionColorTexLightmapShader);
    }

    @Override
    public CommonShader getPositionTexColorNormalShader() {
        return new ForgeCommonShader(GameRenderer::getPositionTexColorNormalShader);
    }

    @Override
    public CommonShader getPositionTexLightmapColorShader() {
        return new ForgeCommonShader(GameRenderer::getPositionTexLightmapColorShader);
    }

    @Override
    public CommonShader getRendertypeSolidShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeSolidShader);
    }

    @Override
    public CommonShader getRendertypeCutoutMippedShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeCutoutMippedShader);
    }

    @Override
    public CommonShader getRendertypeCutoutShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeCutoutShader);
    }

    @Override
    public CommonShader getRendertypeTranslucentShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTranslucentShader);
    }

    @Override
    public CommonShader getRendertypeTranslucentMovingBlockShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTranslucentMovingBlockShader);
    }

    @Override
    public CommonShader getRendertypeTranslucentNoCrumblingShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTranslucentNoCrumblingShader);
    }

    @Override
    public CommonShader getRendertypeArmorCutoutNoCullShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeArmorCutoutNoCullShader);
    }

    @Override
    public CommonShader getRendertypeEntitySolidShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntitySolidShader);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityCutoutShader);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityCutoutNoCullShader);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullZOffsetShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityCutoutNoCullZOffsetShader);
    }

    @Override
    public CommonShader getRendertypeItemEntityTranslucentCullShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeItemEntityTranslucentCullShader);
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentCullShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityTranslucentCullShader);
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityTranslucentShader);
    }

    @Override
    public CommonShader getRendertypeEntitySmoothCutoutShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntitySmoothCutoutShader);
    }

    @Override
    public CommonShader getRendertypeBeaconBeamShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeBeaconBeamShader);
    }

    @Override
    public CommonShader getRendertypeEntityDecalShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityDecalShader);
    }

    @Override
    public CommonShader getRendertypeEntityNoOutlineShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityNoOutlineShader);
    }

    @Override
    public CommonShader getRendertypeEntityShadowShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityShadowShader);
    }

    @Override
    public CommonShader getRendertypeEntityAlphaShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityAlphaShader);
    }

    @Override
    public CommonShader getRendertypeEyesShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEyesShader);
    }

    @Override
    public CommonShader getRendertypeEnergySwirlShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEnergySwirlShader);
    }

    @Override
    public CommonShader getRendertypeLeashShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeLeashShader);
    }

    @Override
    public CommonShader getRendertypeWaterMaskShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeWaterMaskShader);
    }

    @Override
    public CommonShader getRendertypeOutlineShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeOutlineShader);
    }

    @Override
    public CommonShader getRendertypeArmorGlintShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeArmorGlintShader);
    }

    @Override
    public CommonShader getRendertypeArmorEntityGlintShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeArmorEntityGlintShader);
    }

    @Override
    public CommonShader getRendertypeGlintTranslucentShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeGlintTranslucentShader);
    }

    @Override
    public CommonShader getRendertypeGlintShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeGlintShader);
    }

    @Override
    public CommonShader getRendertypeGlintDirectShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeGlintDirectShader);
    }

    @Override
    public CommonShader getRendertypeEntityGlintShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityGlintShader);
    }

    @Override
    public CommonShader getRendertypeEntityGlintDirectShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEntityGlintDirectShader);
    }

    @Override
    public CommonShader getRendertypeTextShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTextShader);
    }

    @Override
    public CommonShader getRendertypeTextIntensityShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTextIntensityShader);
    }

    @Override
    public CommonShader getRendertypeTextSeeThroughShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTextSeeThroughShader);
    }

    @Override
    public CommonShader getRendertypeTextIntensitySeeThroughShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTextIntensitySeeThroughShader);
    }

    @Override
    public CommonShader getRendertypeLightningShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeLightningShader);
    }

    @Override
    public CommonShader getRendertypeTripwireShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeTripwireShader);
    }

    @Override
    public CommonShader getRendertypeEndPortalShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEndPortalShader);
    }

    @Override
    public CommonShader getRendertypeEndGatewayShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeEndGatewayShader);
    }

    @Override
    public CommonShader getRendertypeLinesShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeLinesShader);
    }

    @Override
    public CommonShader getRendertypeCrumblingShader() {
        return new ForgeCommonShader(GameRenderer::getRendertypeCrumblingShader);
    }
}
