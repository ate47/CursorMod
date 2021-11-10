package fr.atesab.customcursormod.common.handler;

public abstract class CommonShaders {
    public static final CommonSupplier<Void, CommonShaders> SUPPLIER = new CommonSupplier<>(true);

    public static CommonShaders get() {
        return SUPPLIER.fetch();
    }

    public abstract CommonShader getPositionShader();

    public abstract CommonShader getPositionColorShader();

    public abstract CommonShader getPositionColorTexShader();

    public abstract CommonShader getPositionTexShader();

    public abstract CommonShader getPositionTexColorShader();

    public abstract CommonShader getBlockShader();

    public abstract CommonShader getNewEntityShader();

    public abstract CommonShader getParticleShader();

    public abstract CommonShader getPositionColorLightmapShader();

    public abstract CommonShader getPositionColorTexLightmapShader();

    public abstract CommonShader getPositionTexColorNormalShader();

    public abstract CommonShader getPositionTexLightmapColorShader();

    public abstract CommonShader getRendertypeSolidShader();

    public abstract CommonShader getRendertypeCutoutMippedShader();

    public abstract CommonShader getRendertypeCutoutShader();

    public abstract CommonShader getRendertypeTranslucentShader();

    public abstract CommonShader getRendertypeTranslucentMovingBlockShader();

    public abstract CommonShader getRendertypeTranslucentNoCrumblingShader();

    public abstract CommonShader getRendertypeArmorCutoutNoCullShader();

    public abstract CommonShader getRendertypeEntitySolidShader();

    public abstract CommonShader getRendertypeEntityCutoutShader();

    public abstract CommonShader getRendertypeEntityCutoutNoCullShader();

    public abstract CommonShader getRendertypeEntityCutoutNoCullZOffsetShader();

    public abstract CommonShader getRendertypeItemEntityTranslucentCullShader();

    public abstract CommonShader getRendertypeEntityTranslucentCullShader();

    public abstract CommonShader getRendertypeEntityTranslucentShader();

    public abstract CommonShader getRendertypeEntitySmoothCutoutShader();

    public abstract CommonShader getRendertypeBeaconBeamShader();

    public abstract CommonShader getRendertypeEntityDecalShader();

    public abstract CommonShader getRendertypeEntityNoOutlineShader();

    public abstract CommonShader getRendertypeEntityShadowShader();

    public abstract CommonShader getRendertypeEntityAlphaShader();

    public abstract CommonShader getRendertypeEyesShader();

    public abstract CommonShader getRendertypeEnergySwirlShader();

    public abstract CommonShader getRendertypeLeashShader();

    public abstract CommonShader getRendertypeWaterMaskShader();

    public abstract CommonShader getRendertypeOutlineShader();

    public abstract CommonShader getRendertypeArmorGlintShader();

    public abstract CommonShader getRendertypeArmorEntityGlintShader();

    public abstract CommonShader getRendertypeGlintTranslucentShader();

    public abstract CommonShader getRendertypeGlintShader();

    public abstract CommonShader getRendertypeGlintDirectShader();

    public abstract CommonShader getRendertypeEntityGlintShader();

    public abstract CommonShader getRendertypeEntityGlintDirectShader();

    public abstract CommonShader getRendertypeTextShader();

    public abstract CommonShader getRendertypeTextIntensityShader();

    public abstract CommonShader getRendertypeTextSeeThroughShader();

    public abstract CommonShader getRendertypeTextIntensitySeeThroughShader();

    public abstract CommonShader getRendertypeLightningShader();

    public abstract CommonShader getRendertypeTripwireShader();

    public abstract CommonShader getRendertypeEndPortalShader();

    public abstract CommonShader getRendertypeEndGatewayShader();

    public abstract CommonShader getRendertypeLinesShader();

    public abstract CommonShader getRendertypeCrumblingShader();
}
