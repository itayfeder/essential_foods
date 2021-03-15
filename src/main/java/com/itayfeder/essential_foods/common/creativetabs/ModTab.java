package com.itayfeder.essential_foods.common.creativetabs;

import com.itayfeder.essential_foods.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ModTab extends ItemGroup {
    public ModTab() {
        super("modtab");
    }

    @Override
    public ItemStack createIcon() {
        return ItemInit.FLOUR.get().getDefaultInstance();
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
    }
}
