package com.itayfeder.essential_foods.common.recipes.seed_producer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.recipes.mill.MillRecipe;
import com.itayfeder.essential_foods.init.BlockInit;
import com.itayfeder.essential_foods.init.RecipeInit;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SeedProducerRecipe implements IRecipe<IInventory> {

    public static final SeedProducerRecipe.Serializer SERIALIZER = new SeedProducerRecipe.Serializer();

    private final Ingredient crop;
    private final ItemStack seed;
    private final ResourceLocation id;

    public SeedProducerRecipe(ResourceLocation id, Ingredient crop, ItemStack seed) {

        this.id = id;
        this.crop = crop;
        this.seed = seed;

        // loaded into the game.
        System.out.println("Loaded " + this.toString());
    }

    @Override
    public String toString () {

        // Overriding toString is not required, it's just useful for debugging.
        return "SeedProducerRecipe [crop=" + this.crop + ", seed=" + this.seed + ", id=" + this.id + "]";
    }

    @Override
    public boolean matches (IInventory inv, World worldIn) {

        // This method is ignored by our custom recipe system, and only has partial
        // functionality. isValid is used instead.
        return this.crop.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult (IInventory inv) {

        // This method is ignored by our custom recipe system. getRecipeOutput().copy() is used
        // instead.
        return this.seed.copy();
    }

    @Override
    public ItemStack getRecipeOutput () {

        return this.seed;
    }

    @Override
    public ResourceLocation getId () {

        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer () {

        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType () {

        return RecipeInit.SEED_PRODUCER_RECIPE;
    }

    @Override
    public ItemStack getIcon () {

        return new ItemStack(BlockInit.SEED_PRODUCER.get());
    }

    public boolean fit (ItemStack crop, Block block) {

        return this.crop.test(crop);
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return true;
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SeedProducerRecipe> {

        Serializer() {

            // This registry name is what people will specify in their json files.
            this.setRegistryName(new ResourceLocation(EssentialFoodsMod.MOD_ID, "seed_producer"));
        }

        @Override
        public SeedProducerRecipe read (ResourceLocation recipeId, JsonObject json) {

            // Reads a recipe from json.

            // Ingredient.deserialize can understand.
            final JsonElement cropElement = JSONUtils.isJsonArray(json, "crop") ? JSONUtils.getJsonArray(json, "crop") : JSONUtils.getJsonObject(json, "crop");
            final Ingredient crop = Ingredient.deserialize(cropElement);

            // Reads the output. The common utility method in ShapedRecipe is what all vanilla
            // recipe classes use for this.
            final ItemStack seed = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "seed"));

            // If something is invalid or null an exception should be thrown. This is used to
            // let the game and end user know a recipe was bad.

            return new SeedProducerRecipe(recipeId, crop, seed);
        }

        @Override
        public SeedProducerRecipe read (ResourceLocation recipeId, PacketBuffer buffer) {

            // Reads a recipe from a packet buffer. This code is called on the client.
            final Ingredient crop = Ingredient.read(buffer);
            final ItemStack seed = buffer.readItemStack();
            final ResourceLocation blockId = buffer.readResourceLocation();

            return new SeedProducerRecipe(recipeId, crop, seed);
        }

        @Override
        public void write (PacketBuffer buffer, SeedProducerRecipe recipe) {

            // Writes the recipe to a packet buffer. This is called on the server when a player
            // connects or when /reload is used.
            recipe.crop.write(buffer);
            buffer.writeItemStack(recipe.seed);
        }
    }
}