package com.itayfeder.essential_foods.init;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.items.MortarAndPestleItem;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            EssentialFoodsMod.MOD_ID);

    public static final RegistryObject<BlockItem> MILL = ITEMS.register("mill",
            () -> new BlockItem(BlockInit.MILL.get(),
                    new Item.Properties().group(EssentialFoodsMod.MOD_TAB)));

    public static final RegistryObject<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle",
            () -> new MortarAndPestleItem((new Item.Properties().group(EssentialFoodsMod.MOD_TAB).maxStackSize(1))));

    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour",
            () -> new Item((new Item.Properties().group(EssentialFoodsMod.MOD_TAB))));

    //CORN MODULE
    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new BlockNamedItem(BlockInit.CORN_PLANT.get(), (new Item.Properties().group(EssentialFoodsMod.MOD_TAB))));

    public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            () -> new Item((new Item.Properties().group(EssentialFoodsMod.MOD_TAB)).food((new Food.Builder()).hunger(2).saturation(0.5F).build())));

    //RICE MODULE
    public static final RegistryObject<Item> RICE_SHOOTS = ITEMS.register("rice_shoots",
            () -> new BlockNamedItem(BlockInit.RICE_PLANT.get(), (new Item.Properties().group(EssentialFoodsMod.MOD_TAB))));

    public static final RegistryObject<Item> UNMILLED_RICE = ITEMS.register("unmilled_rice",
            () -> new Item((new Item.Properties()).group(EssentialFoodsMod.MOD_TAB)));

    public static final RegistryObject<Item> RICE = ITEMS.register("rice",
            () -> new Item((new Item.Properties()).group(EssentialFoodsMod.MOD_TAB).food((new Food.Builder()).hunger(1).saturation(0.2F).fastToEat().build())));

    //COCOA MODULE
    public static final RegistryObject<Item> COCOA_POWDER = ITEMS.register("cocoa_powder",
            () -> new Item((new Item.Properties()).group(EssentialFoodsMod.MOD_TAB)));

    public static final RegistryObject<Item> CHOCOLATE_MILK = ITEMS.register("chocolate_milk",
            () -> new MilkBucketItem((new Item.Properties()).group(EssentialFoodsMod.MOD_TAB).maxStackSize(1)));

    public static final RegistryObject<BlockItem> CHOCOLATE_CAKE = ITEMS.register("chocolate_cake",
            () -> new BlockItem(BlockInit.CHOCOLATE_CAKE.get(),
                    new Item.Properties().group(EssentialFoodsMod.MOD_TAB)));

    //TOMATO MODULE
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new BlockNamedItem(BlockInit.TOMATO_PLANT.get(), (new Item.Properties().group(EssentialFoodsMod.MOD_TAB))));

    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item((new Item.Properties().group(EssentialFoodsMod.MOD_TAB)).food((new Food.Builder()).hunger(3).saturation(0.8F).build())));

}
