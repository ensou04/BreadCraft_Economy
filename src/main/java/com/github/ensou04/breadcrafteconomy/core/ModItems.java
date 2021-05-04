package com.github.ensou04.breadcrafteconomy.core;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.github.ensou04.breadcrafteconomy.items.CoinGoldItem;
import com.github.ensou04.breadcrafteconomy.items.CoinItem;
import com.github.ensou04.breadcrafteconomy.items.EnderWayfinderItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BreadCraftEconomy.MODID);

    // Items
    public static final RegistryObject<Item> COIN_SILVER = ITEMS.register("coin_silver", CoinItem::new);
    public static final RegistryObject<Item> COIN_GOLD = ITEMS.register("coin_gold", CoinItem::new);
    public static final RegistryObject<Item> ENDER_WAYFINDER = ITEMS.register("ender_wayfinder", EnderWayfinderItem::new);

    // Blocks Item
    public static final RegistryObject<Item> BANKER_SAFE = ITEMS.register("banker_safe",
            () -> new BlockItem(ModBlocks.BANKER_SAFE.get(), new Item.Properties().tab(BreadCraftEconomy.BREADCRAFT_GROUP)));
    public static final RegistryObject<Item> COLLECTOR_CHEST = ITEMS.register("collector_chest",
            () -> new BlockItem(ModBlocks.COLLECTOR_CHEST.get(), new Item.Properties().tab(BreadCraftEconomy.BREADCRAFT_GROUP)));
}
