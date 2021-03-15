package com.itayfeder.essential_foods.init;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.tileentities.MillTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,
            EssentialFoodsMod.MOD_ID);

    public static final RegistryObject<TileEntityType<MillTileEntity>> MILL_TILE = TILE_ENTITIES.register("mill",
            () -> TileEntityType.Builder.create(MillTileEntity::new, BlockInit.MILL.get()).build(null));


}
