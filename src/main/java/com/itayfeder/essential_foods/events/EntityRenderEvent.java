package com.itayfeder.essential_foods.events;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.client.renderer.layer.EntitySpicyLayer;
import com.itayfeder.essential_foods.init.EffectInit;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EssentialFoodsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityRenderEvent {
    @SubscribeEvent
    public static void preRenderEvent(RenderLivingEvent.Pre event) {
        event.getMatrixStack().push();
        if (event.getEntity().getActivePotionEffect(EffectInit.SPICY.get()) != null) {
            event.getRenderer().addLayer(new EntitySpicyLayer(event.getRenderer()));
        }
    }

    @SubscribeEvent
    public static void preRenderEvent(RenderLivingEvent.Post event) {
        event.getMatrixStack().pop();
    }
}
