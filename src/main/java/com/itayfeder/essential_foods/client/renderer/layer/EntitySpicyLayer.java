package com.itayfeder.essential_foods.client.renderer.layer;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.init.EffectInit;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.ResourceLocation;

public class EntitySpicyLayer extends LayerRenderer<LivingEntity, EntityModel<LivingEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EssentialFoodsMod.MOD_ID,"textures/entity/spicy_layer.png");

    public EntitySpicyLayer(IEntityRenderer<LivingEntity, EntityModel<LivingEntity>> p_i50945_1_) {
        super(p_i50945_1_);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.getActivePotionEffect(EffectInit.SPICY.get()) != null) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(entitylivingbaseIn)));
            this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F), 1.0F, 0.298F, 0.0F, 1.0F);
        }
    }

}
