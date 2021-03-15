package com.itayfeder.essential_foods.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MortarAndPestleItem extends Item {
    public MortarAndPestleItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamage(itemstack.getDamage() + 1);
        if(retval.getDamage() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if (toRepair.getItem() == Items.STONE) {
            return true;
        }
        return super.getIsRepairable(toRepair, repair);
    }
}
