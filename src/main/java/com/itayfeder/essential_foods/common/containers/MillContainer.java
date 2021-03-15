package com.itayfeder.essential_foods.common.containers;

import com.itayfeder.essential_foods.common.blocks.MillBlock;
import com.itayfeder.essential_foods.common.containers.slots.MillComponentSlot;
import com.itayfeder.essential_foods.common.containers.slots.MillResultSlot;
import com.itayfeder.essential_foods.common.tileentities.MillTileEntity;
import com.itayfeder.essential_foods.init.ContainerInit;
import com.itayfeder.essential_foods.init.ItemInit;
import com.itayfeder.essential_foods.utils.MillRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MillContainer extends Container {
    private final IInventory inventory;
    public IIntArray millData;

    public MillContainer(int windowID, PlayerInventory playerInv) {
        this(windowID, playerInv, new Inventory(2), new IntArray(2));
    }

    public MillContainer(int windowID, PlayerInventory playerInv, IInventory inv, IIntArray data) {
        super(ContainerInit.MILL_CONTAINER.get(), windowID);
        assertInventorySize(inv, 2);
        this.inventory = inv;
        this.millData = data;
        inv.openInventory(playerInv.player);

        this.addSlot(new MillComponentSlot(this, inv, 0, 62 + 0 * 18, 17 + 1 * 18));
        this.addSlot(new MillResultSlot(playerInv.player, inv, 1, 62 + 2 * 18, 17 + 1 * 18));

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
        }

        trackIntArray(this.millData);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (MillRecipes.RECIPES.containsKey(stack.getItem())) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 29) {
                    if (!this.mergeItemStack(stack, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 38 && !this.mergeItemStack(stack, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.millData.get(0);
        int j = this.millData.get(1);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }
}
