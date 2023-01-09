package com.Twins730.guineapigs.client.renderer;

import com.Twins730.guineapigs.GuineaPigMod;
import com.Twins730.guineapigs.client.model.GuineaPigModel;
import com.Twins730.guineapigs.objects.entity.GuineaPigEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class GuineaPigRenderer extends MobRenderer<GuineaPigEntity, GuineaPigModel<GuineaPigEntity>> {

    private final ResourceLocation[] GUINEA_PIG_TEXTURE = new ResourceLocation[]{
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/brown_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/calico_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/orange_brown_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/tan_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/white_black_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/white_guinea.png")};

    public GuineaPigRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new GuineaPigModel<>(), 0.2f);
    }

    @Override
    protected void applyRotations(GuineaPigEntity guineaPig, MatrixStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(guineaPig,stack,ageInTicks,rotationYaw,partialTicks);
        if(guineaPig.isSitting()){
            if(guineaPig.isChild()){
                stack.translate(0, -0.04, 0);
            } else {
                stack.translate(0, -0.12, 0);
            }
        }
    }

    @Override
    public ResourceLocation getEntityTexture(GuineaPigEntity entity) {
        return GUINEA_PIG_TEXTURE[entity.getVariant()];
    }
}
