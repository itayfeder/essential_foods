package com.itayfeder.essential_foods.common.containers.slots;

import com.itayfeder.essential_foods.common.blocks.MillBlock;
import com.itayfeder.essential_foods.common.containers.MillContainer;
import com.itayfeder.essential_foods.utils.MillRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MillComponentSlot extends Slot {
    private final MillContainer container;

    public MillComponentSlot(MillContainer container, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.container = container;
    }

    public boolean isItemValid(ItemStack stack) {
        return MillRecipes.RECIPES.containsKey(stack.getItem());
    }

    public int getItemStackLimit(ItemStack stack) {
        return super.getItemStackLimit(stack);
    }
}