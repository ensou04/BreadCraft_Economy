package com.github.ensou04.breadcrafteconomy.items;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import net.minecraft.item.Item;

public class EnderWayfinderItem extends Item {
    public EnderWayfinderItem() {
        super(new Item.Properties().tab(BreadCraftEconomy.BREADCRAFT_GROUP).fireResistant());
    }
}
