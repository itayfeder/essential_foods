package com.itayfeder.essential_foods.init;

import com.google.common.eventbus.Subscribe;
import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.recipes.mill.MillRecipe;
import com.itayfeder.essential_foods.common.recipes.mill.MillRecipeType;
import com.itayfeder.essential_foods.common.recipes.seed_producer.SeedProducerRecipe;
import com.itayfeder.essential_foods.common.recipes.seed_producer.SeedProducerRecipeType;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeInit {

    public static final IRecipeType<MillRecipe> MILL_RECIPE = new MillRecipeType();
    public static final IRecipeType<SeedProducerRecipe> SEED_PRODUCER_RECIPE = new SeedProducerRecipeType();

    public static void registerRecipeSerializers (RegistryEvent.Register<IRecipeSerializer<?>> event) {

        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MILL_RECIPE.toString()), MILL_RECIPE);
        event.getRegistry().register(MillRecipe.SERIALIZER);

        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(SEED_PRODUCER_RECIPE.toString()), SEED_PRODUCER_RECIPE);
        event.getRegistry().register(SeedProducerRecipe.SERIALIZER);
    }

}
