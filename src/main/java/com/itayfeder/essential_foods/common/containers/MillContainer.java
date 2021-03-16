package com.itayfeder.essential_foods.common.containers;

import com.itayfeder.essential_foods.common.containers.slots.MillResultSlot;
import com.itayfeder.essential_foods.common.tileentities.MillTileEntity;
import com.itayfeder.essential_foods.init.BlockInit;
import com.itayfeder.essential_foods.init.ContainerInit;
import com.itayfeder.essential_foods.utils.helper.FunctionalIntReferenceHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class MillContainer extends Container {
    public MillTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder currentConvertTime;

    public MillContainer(int windowID, PlayerInventory playerInv) {
        this(windowID, playerInv, new MillTileEntity());
    }

    public MillContainer(int windowID, PlayerInventory playerInv, MillTileEntity tile) {
        super(ContainerInit.MILL_CONTAINER.get(), windowID);
        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 62 + 0 * 18, 17 + 1 * 18));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 62 + 2 * 18, 17 + 1 * 18));

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
        }

        this.trackInt(currentConvertTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.convertTime,
                value -> this.tileEntity.convertTime = value));
    }

    private static MillTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof MillTileEntity) {
            return (MillTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.MILL.get());
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getSmeltProgressionScaled() {
        return this.currentConvertTime.get() != 0 && this.tileEntity.convertTimeTotal != 0
                ? this.currentConvertTime.get() * 24 / this.tileEntity.convertTimeTotal
                : 0;
    }
}
