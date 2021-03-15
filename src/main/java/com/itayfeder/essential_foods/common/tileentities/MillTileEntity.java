package com.itayfeder.essential_foods.common.tileentities;

import com.itayfeder.essential_foods.common.containers.MillContainer;
import com.itayfeder.essential_foods.init.TileEntityInit;
import com.itayfeder.essential_foods.utils.MillRecipes;
import jdk.nashorn.internal.codegen.CompileUnit;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.DispenserContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MillTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
    private int convertTime;
    private int convertTimeTotal;
    public final IIntArray millData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return MillTileEntity.this.convertTime;
                case 1:
                    return MillTileEntity.this.convertTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    MillTileEntity.this.convertTime = value;
                    break;
                case 1:
                    MillTileEntity.this.convertTimeTotal = value;
            }

        }

        public int size() {
            return 2;
        }
    };


    public MillTileEntity() {
        super(TileEntityInit.MILL_TILE.get());
    }

    public void tick() {
        boolean flag1 = false;

        if (!this.world.isRemote) {
            ItemStack itemstack = this.stacks.get(1);
            if (!this.stacks.get(0).isEmpty()) {
                if (this.canConvert(this.stacks.get(0))) {
                    flag1 = true;
                    if (itemstack.hasContainerItem())
                        this.stacks.set(1, itemstack.getContainerItem());

                }
                if (this.canConvert(this.stacks.get(0))) {
                    ++this.convertTime;
                    if (this.convertTime == this.convertTimeTotal) {
                        this.convertTime = 0;
                        this.convertTimeTotal = 200;
                        this.convert(this.stacks.get(0));
                        flag1 = true;
                    }
                } else {
                    this.convertTime = 0;
                }
            } else if (this.convertTime > 0) {
                this.convertTime = MathHelper.clamp(this.convertTime - 2, 0, this.convertTimeTotal);
            }

            if (flag1) {
                this.markDirty();
            }

        }
    }

    protected boolean canConvert(ItemStack stack) {
        if (!this.stacks.get(0).isEmpty() && MillRecipes.RECIPES.containsKey(stack.getItem())) {
            Item itemstack = MillRecipes.RECIPES.get(stack.getItem());
            if (itemstack == null) {
                return false;
            } else {
                ItemStack itemstack1 = this.stacks.get(1);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (itemstack1.getItem() != itemstack) {
                    return false;
                } else if (itemstack1.getCount() + 1 <= this.getInventoryStackLimit() && itemstack1.getCount() + 1 <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + 1 <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void convert(ItemStack stack) {
        if (MillRecipes.RECIPES.containsKey(stack.getItem()) && this.canConvert(stack)) {
            ItemStack itemstack = this.stacks.get(0);
            Item itemstack1 = MillRecipes.RECIPES.get(stack.getItem());
            ItemStack itemstack2 = this.stacks.get(1);
            if (itemstack2.isEmpty()) {
                this.stacks.set(1, itemstack1.getDefaultInstance().copy());
            } else if (itemstack2.getItem() == itemstack1) {
                itemstack2.grow(itemstack1.getDefaultInstance().getCount());
            }

            itemstack.shrink(1);
        }
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.stacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.stacks.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.convertTimeTotal = 200;
            this.convertTime = 0;
            this.markDirty();
        }

    }


    public int getSizeInventory() {
        return 2;
    }

    public int addItemStack(ItemStack stack) {
        for(int i = 0; i < this.stacks.size(); ++i) {
            if (this.stacks.get(i).isEmpty()) {
                this.setInventorySlotContents(i, stack);
                return i;
            }
        }

        return -1;
    }

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        this.convertTime = nbt.getInt("ConvertTime");
        this.convertTimeTotal = nbt.getInt("ConvertTimeTotal");
        if (!this.checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.stacks);
        }

    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("ConvertTime", this.convertTime);
        compound.putInt("ConvertTimeTotal", this.convertTimeTotal);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.stacks);
        }

        return compound;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.stacks = itemsIn;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.mill");
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return new MillContainer(id, player,this, millData);
    }


}
