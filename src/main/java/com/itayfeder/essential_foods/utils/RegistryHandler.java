package com.itayfeder.essential_foods.utils;

import com.itayfeder.essential_foods.init.*;
import com.sun.scenario.effect.Effect;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class RegistryHandler {
    public static void Init() {
        EffectInit.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityInit.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ContainerInit.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockInit.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
