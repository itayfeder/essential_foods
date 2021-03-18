package com.itayfeder.essential_foods.init;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.containers.MillContainer;
import com.itayfeder.essential_foods.common.containers.SeedProducerContainer;
import com.itayfeder.essential_foods.common.tileentities.MillTileEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
            EssentialFoodsMod.MOD_ID);

    public static final RegistryObject<ContainerType<MillContainer>> MILL_CONTAINER = CONTAINERS.register("mill",
            () -> IForgeContainerType.create((windowId, inv, data) -> {
                return new MillContainer(windowId, inv);
            }));

    public static final RegistryObject<ContainerType<SeedProducerContainer>> SEED_PRODUCER_CONTAINER = CONTAINERS.register("seed_producer",
            () -> IForgeContainerType.create((windowId, inv, data) -> {
                return new SeedProducerContainer(windowId, inv);
            }));
}
