package com.itayfeder.essential_foods.common.tileentities;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.containers.MillContainer;
import com.itayfeder.essential_foods.common.containers.SeedProducerContainer;
import com.itayfeder.essential_foods.common.recipes.mill.MillRecipe;
import com.itayfeder.essential_foods.common.recipes.seed_producer.SeedProducerRecipe;
import com.itayfeder.essential_foods.init.RecipeInit;
import com.itayfeder.essential_foods.init.TileEntityInit;
import com.itayfeder.essential_foods.utils.ItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class SeedProducerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private ITextComponent customName;
    private ItemHandler inventory;
    public int productionTime;
    public static int productionTimeTotal = 200;

    public SeedProducerTileEntity() {
        super(TileEntityInit.SEED_PRODUCER_TILE.get());

        this.inventory = new ItemHandler(2);
    }

    @Override
    public void tick() {
        boolean dirty = false;

        if (this.world != null && !this.world.isRemote) {
            if (this.getRecipe(this.inventory.getStackInSlot(0)) != null) {
                if (this.productionTime != this.productionTimeTotal) {
                    this.productionTime++;
                    dirty = true;
                } else {
                    this.productionTime = 0;
                    ItemStack output = this.getRecipe(this.inventory.getStackInSlot(0)).getRecipeOutput();
                    this.inventory.insertItem(1, output.copy(), false);
                    this.inventory.decrStackSize(0, 1);
                    dirty = true;
                }
            }
        }

        if (dirty) {
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(),
                    Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    public int getSizeInventory() {
        return 2;
    }

    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = ITextComponent.Serializer.getComponentFromJson(compound.getString("CustomName"));
        }
        NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.productionTime = compound.getInt("ProductionTime");

    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        compound.putInt("ProductionTime", this.productionTime);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());

        return compound;
    }

    public void setCustomName(ITextComponent name) {
        this.customName = name;
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + EssentialFoodsMod.MOD_ID + ".seed_producer");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }

    @Override
    public Container createMenu(final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new SeedProducerContainer(windowID, playerInv, this);
    }

    @Nullable
    private SeedProducerRecipe getRecipe(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        Set<IRecipe<?>> recipes = findRecipesByType(RecipeInit.SEED_PRODUCER_RECIPE, this.world);
        for (IRecipe<?> iRecipe : recipes) {
            SeedProducerRecipe recipe = (SeedProducerRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
                return recipe;
            }
        }

        return null;
    }

    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }
}
