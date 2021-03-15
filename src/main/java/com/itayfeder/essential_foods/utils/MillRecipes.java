package com.itayfeder.essential_foods.utils;

import com.itayfeder.essential_foods.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class MillRecipes {
    public static final Map<Item, Item> RECIPES = new HashMap<Item, Item>() {{
        put(Items.WHEAT, ItemInit.FLOUR.get());
        put(ItemInit.UNMILLED_RICE.get(), ItemInit.RICE.get());
    }};
}
