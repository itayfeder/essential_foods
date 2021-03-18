package com.itayfeder.essential_foods.init;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.tileentities.MillTileEntity;
import com.itayfeder.essential_foods.common.tileentities.SeedProducerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,
            EssentialFoodsMod.MOD_ID);

    public static final RegistryObject<TileEntityType<MillTileEntity>> MILL_TILE = TILE_ENTITIES.register("mill",
            () -> TileEntityType.Builder.create(MillTileEntity::new, BlockInit.MILL.get()).build(null));

    public static final RegistryObject<TileEntityType<SeedProducerTileEntity>> SEED_PRODUCER_TILE = TILE_ENTITIES.register("seed_producer",
            () -> TileEntityType.Builder.create(SeedProducerTileEntity::new, BlockInit.SEED_PRODUCER.get()).build(null));
}
