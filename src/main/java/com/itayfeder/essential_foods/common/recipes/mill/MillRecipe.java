package com.itayfeder.essential_foods.common.recipes.mill;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itayfeder.essential_foods.EssentialFoodsMod;
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

public class MillRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();

    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;

    public MillRecipe(ResourceLocation id, Ingredient input, ItemStack output) {

        this.id = id;
        this.input = input;
        this.output = output;

        // This output is not required, but it can be used to detect when a recipe has been
        // loaded into the game.
        System.out.println("Loaded " + this.toString());
    }

    @Override
    public String toString () {

        // Overriding toString is not required, it's just useful for debugging.
        return "MillRecipe [input=" + this.input + ", output=" + this.output + ", id=" + this.id + "]";
    }

    @Override
    public boolean matches (IInventory inv, World worldIn) {

        // This method is ignored by our custom recipe system, and only has partial
        // functionality. isValid is used instead.
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult (IInventory inv) {

        // This method is ignored by our custom recipe system. getRecipeOutput().copy() is used
        // instead.
        return this.output.copy();
    }

    @Override
    public ItemStack getRecipeOutput () {

        return this.output;
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

        return RecipeInit.MILL_RECIPE;
    }

    @Override
    public ItemStack getIcon () {

        return new ItemStack(BlockInit.MILL.get());
    }

    public boolean fit (ItemStack input, Block block) {

        return this.input.test(input);
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return true;
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MillRecipe> {

        Serializer() {

            // This registry name is what people will specify in their json files.
            this.setRegistryName(new ResourceLocation(EssentialFoodsMod.MOD_ID, "mill"));
        }

        @Override
        public MillRecipe read (ResourceLocation recipeId, JsonObject json) {

            // Reads a recipe from json.

            // Reads the input. Accepts items, tags, and anything else that
            // Ingredient.deserialize can understand.
            final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
            final Ingredient input = Ingredient.deserialize(inputElement);

            // Reads the output. The common utility method in ShapedRecipe is what all vanilla
            // recipe classes use for this.
            final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));

            // If something is invalid or null an exception should be thrown. This is used to
            // let the game and end user know a recipe was bad.

            return new MillRecipe(recipeId, input, output);
        }

        @Override
        public MillRecipe read (ResourceLocation recipeId, PacketBuffer buffer) {

            // Reads a recipe from a packet buffer. This code is called on the client.
            final Ingredient input = Ingredient.read(buffer);
            final ItemStack output = buffer.readItemStack();
            final ResourceLocation blockId = buffer.readResourceLocation();

            return new MillRecipe(recipeId, input, output);
        }

        @Override
        public void write (PacketBuffer buffer, MillRecipe recipe) {

            // Writes the recipe to a packet buffer. This is called on the server when a player
            // connects or when /reload is used.
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output);
        }
    }
}