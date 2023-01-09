package com.Twins730.guineapigs.client.model;

import com.Twins730.guineapigs.objects.entity.GuineaPigEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class GuineaPigModel<T extends GuineaPigEntity> extends AgeableModel<T> {

    private final ImmutableList<ModelRenderer> head_parts;
    private final ImmutableList<ModelRenderer> body_parts;
    private final ModelRenderer body;
    private final ModelRenderer RBFoot;
    private final ModelRenderer LBFoot;
    private final ModelRenderer FRFoot;
    private final ModelRenderer FLFoot;
    private final ModelRenderer head;
    private final ModelRenderer ears;
    private final ModelRenderer rightEar_r1;
    private final ModelRenderer leftEar_r1;

    public GuineaPigModel(){
        this.textureHeight = 32;
        this.textureWidth = 32;


        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 0).addBox(-3.5F, -5.25F, -3.0F, 6.0F, 4.0F, 9.0F, 0.0F, false);
        body.setTextureOffset(18, 13).addBox(-2.0F, -3.5F, 4.5F, 3.0F, 3.0F, 2.0F, 0.0F, false);

        RBFoot = new ModelRenderer(this);
        RBFoot.setRotationPoint(0.0F, -2.0F, 5.0F);
        body.addChild(RBFoot);
        RBFoot.setTextureOffset(8, 20).addBox(1.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        LBFoot = new ModelRenderer(this);
        LBFoot.setRotationPoint(0.0F, -2.0F, 5.0F);
        body.addChild(LBFoot);
        LBFoot.setTextureOffset(0, 20).addBox(-4.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        FRFoot = new ModelRenderer(this);
        FRFoot.setRotationPoint(0.0F, -2.0F, -3.0F);
        body.addChild(FRFoot);
        FRFoot.setTextureOffset(16, 18).addBox(1.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        FLFoot = new ModelRenderer(this);
        FLFoot.setRotationPoint(0.0F, -2.0F, -3.0F);
        body.addChild(FLFoot);
        FLFoot.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, -0.75F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(-1.0F, -3.0F, -4.0F);
        body.addChild(head);
        head.setTextureOffset(0, 13).addBox(-2.0F, -2.0F, -3.0F, 5.0F, 3.0F, 4.0F, 0.0F, false);

        ears = new ModelRenderer(this);
        ears.setRotationPoint(0.5F, -2.0F, -0.5F);
        head.addChild(ears);


        rightEar_r1 = new ModelRenderer(this);
        rightEar_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        ears.addChild(rightEar_r1);
        setRotationAngle(rightEar_r1, -0.8727F, 0.0F, 0.3491F);
        rightEar_r1.setTextureOffset(0, 5).addBox(2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);

        leftEar_r1 = new ModelRenderer(this);
        leftEar_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        ears.addChild(leftEar_r1);
        setRotationAngle(leftEar_r1, -0.8727F, 0.0F, -0.3491F);
        leftEar_r1.setTextureOffset(4, 5).addBox(-4.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
        ImmutableList.Builder<ModelRenderer> bodyBuilder = ImmutableList.builder();
        bodyBuilder.add(body);
        this.body_parts = bodyBuilder.build();

        ImmutableList.Builder<ModelRenderer> headBuilder = ImmutableList.builder();
        this.head_parts = headBuilder.build();

    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return head_parts;
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return body_parts;
    }

    @Override
    public void setRotationAngles(GuineaPigEntity guineaPig, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        if(!guineaPig.isSitting()) {
            this.LBFoot.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.RBFoot.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.FLFoot.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.FRFoot.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        } else {
            this.LBFoot.rotateAngleX = 90;
            this.RBFoot.rotateAngleX = 90;
            this.FLFoot.rotateAngleX = 90;
            this.FRFoot.rotateAngleX = 90;
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
