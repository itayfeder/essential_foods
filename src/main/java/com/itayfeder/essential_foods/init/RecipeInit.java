package com.itayfeder.essential_foods.init;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.recipes.mill.IMillRecipe;
import com.itayfeder.essential_foods.common.recipes.mill.MillRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeInit {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, EssentialFoodsMod.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> MILL_SERIALIZER = RECIPE_SERIALIZERS.register("mill",
            () -> new MillRecipeSerializer());
    public static final IRecipeType<IMillRecipe> MILL_RECIPE_TYPE = registerType(IMillRecipe.RECIPE_TYPE_ID);

    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }

    private static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeId) {
        return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
    }

}
