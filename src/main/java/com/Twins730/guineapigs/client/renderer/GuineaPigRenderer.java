package com.Twins730.guineapigs.client.renderer;

import com.Twins730.guineapigs.GuineaPigMod;
import com.Twins730.guineapigs.client.model.GuineaPigModel;
import com.Twins730.guineapigs.objects.entity.GuineaPigEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.resources.ResourceLocation;

public class GuineaPigRenderer extends MobRenderer<GuineaPigEntity, GuineaPigModel<GuineaPigEntity>> {

    private final ResourceLocation[] GUINEA_PIG_TEXTURE = new ResourceLocation[]{
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/brown_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/calico_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/orange_brown_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/tan_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/white_black_guinea.png"),
            new ResourceLocation(GuineaPigMod.MOD_ID,"textures/entity/guinea_pig/white_guinea.png")};

    public GuineaPigRenderer(EntityRendererProvider.Context context) {
        super(context, new GuineaPigModel<>(context.bakeLayer(GuineaPigModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    protected void setupRotations(GuineaPigEntity guineaPig, PoseStack stack, float p_115319_, float p_115320_, float p_115321_) {
        super.setupRotations(guineaPig, stack, p_115319_, p_115320_, p_115321_);
        if(guineaPig.isInSittingPose()){
            if(guineaPig.isBaby()){
                stack.translate(0, -0.04, 0);
            } else {
                stack.translate(0, -0.12, 0);
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(GuineaPigEntity entity) {
        return GUINEA_PIG_TEXTURE[entity.getVariant()];
    }
}
