package com.itayfeder.essential_foods.init;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            EssentialFoodsMod.MOD_ID);

    public static final RegistryObject<Block> MILL = BLOCKS.register("mill",
            () -> new MillBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(2.0F, 6.0F)));

    public static final RegistryObject<Block> SEED_PRODUCER = BLOCKS.register("seed_producer",
            () -> new SeedProducerBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5F)));

    //SALT MODULE
    public static final RegistryObject<Block> SALT_ORE = BLOCKS.register("salt_ore",
            () -> new ModOreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F)));

    //CORN MODULE
    public static final RegistryObject<Block> CORN_PLANT = BLOCKS.register("corn_plant",
            () -> new CornPlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.CROP)));

    //RICE MODULE
    public static final RegistryObject<Block> RICE_PLANT = BLOCKS.register("rice_plant",
            () -> new RicePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.CROP)));

    //COCOA MODULE
    public static final RegistryObject<Block> CHOCOLATE_CAKE = BLOCKS.register("chocolate_cake",
            () -> new CakeBlock(AbstractBlock.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));

    //TOMATO MODULE
    public static final RegistryObject<Block> TOMATO_PLANT = BLOCKS.register("tomato_plant",
            () -> new TomatoPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.CROP)));

    //CHILI MODULE
    public static final RegistryObject<Block> CHILI_PLANT = BLOCKS.register("chili_plant",
            () -> new ChiliPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.CROP)));

}
