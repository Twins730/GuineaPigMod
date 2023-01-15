package com.Twins730.guineapigs.client.model;

import com.Twins730.guineapigs.GuineaPigMod;
import com.Twins730.guineapigs.objects.entity.GuineaPigEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GuineaPigModel<G extends GuineaPigEntity> extends AgeableListModel<G> {

    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(GuineaPigMod.MOD_ID, "guinea_pig"), "main");
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart frontLeftFoot;
    private final ModelPart frontRightFoot;
    private final ModelPart backRightFoot;
    private final ModelPart backLeftFoot;


    public GuineaPigModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.frontLeftFoot = root.getChild("frontLeftFoot");
        this.frontRightFoot = root.getChild("frontRightFoot");
        this.backRightFoot = root.getChild("backRightFoot");
        this.backLeftFoot = root.getChild("backLeftFoot");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 13).addBox(-2.0F, -3.5F, 4.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.5F, -5.25F, -3.0F, 6.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-2.5F, -1.5F, -4.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 20.5F, -3.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -1.5F));

        PartDefinition rightEar_r1 = ears.addOrReplaceChild("rightEar_r1", CubeListBuilder.create().texOffs(0, 5).addBox(2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.3491F));

        PartDefinition leftEar_r1 = ears.addOrReplaceChild("leftEar_r1", CubeListBuilder.create().texOffs(4, 5).addBox(-4.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, -0.3491F));

        PartDefinition frontLeftFoot = partdefinition.addOrReplaceChild("frontLeftFoot", CubeListBuilder.create().texOffs(0, 20).addBox(-6.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 22.0F, -3.0F));

        PartDefinition frontRightFoot = partdefinition.addOrReplaceChild("frontRightFoot", CubeListBuilder.create().texOffs(0, 0).addBox(4.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 22.0F, -3.0F));

        PartDefinition backRightFoot = partdefinition.addOrReplaceChild("backRightFoot", CubeListBuilder.create().texOffs(16, 18).addBox(4.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 22.0F, 5.0F));

        PartDefinition backLeftFoot = partdefinition.addOrReplaceChild("backLeftFoot", CubeListBuilder.create().texOffs(8, 20).addBox(-6.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 22.0F, 5.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void setupAnim(GuineaPigEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        if(!entity.isInSittingPose()) {
            this.backLeftFoot.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.backRightFoot.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.frontLeftFoot.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.frontRightFoot.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        } else {
            this.backLeftFoot.xRot = 90;
            this.backRightFoot.xRot = 90;
            this.frontLeftFoot.xRot = 90;
            this.frontRightFoot.xRot = 90;
        }
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.head, this.body, this.backLeftFoot, this.backRightFoot, this.frontLeftFoot, this.frontRightFoot);

    }
}
