package com.itayfeder.essential_foods.common.effects;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SpicyEffect extends Effect {
    private final ResourceLocation potionIcon;
    public SpicyEffect() {
        super(EffectType.NEUTRAL, 14981690);
        potionIcon = new ResourceLocation(EssentialFoodsMod.MOD_ID, "textures/mob_effect/spicy.png");
        addAttributesModifier(Attributes.MOVEMENT_SPEED, "82638b85-28d2-4457-a95c-6d39eb02a75a", (double)0.35F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public String getName() {
        return "effect.spicy";
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        int j = (int)(0.5*amplifier+1);
        System.out.println(j);
        entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, (float)j);
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }
}
