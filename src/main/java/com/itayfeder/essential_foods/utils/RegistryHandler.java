package com.itayfeder.essential_foods.utils;

import com.itayfeder.essential_foods.init.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class RegistryHandler {
    public static void Init() {
        ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityInit.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ContainerInit.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockInit.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }
}
