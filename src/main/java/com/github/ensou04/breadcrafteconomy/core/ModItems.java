package com.github.ensou04.breadcrafteconomy.core;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.github.ensou04.breadcrafteconomy.items.CoinGoldItem;
import com.github.ensou04.breadcrafteconomy.items.CoinSilverItem;
import com.github.ensou04.breadcrafteconomy.items.EnderWayfinderItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BreadCraftEconomy.MODID);

    // Items
    public static final RegistryObject<Item> COIN_SILVER = ITEMS.register("probread_coin_silver", CoinSilverItem::new);
    public static final RegistryObject<Item> COIN_GOLD = ITEMS.register("probread_coin_gold", CoinGoldItem::new);
    public static final RegistryObject<Item> ENDER_WAYFINDER = ITEMS.register("ender_wayfinder", EnderWayfinderItem::new);

    // Blocks Item
    public static final RegistryObject<Item> BANKER_STATION = ITEMS.register("banker_station",
            () -> new BlockItem(ModBlocks.BANKER_STATION.get(), new Item.Properties().tab(BreadCraftEconomy.BREADCRAFT_GROUP)));
    public static final RegistryObject<Item> COLLECTOR_STATION = ITEMS.register("collector_station",
            () -> new BlockItem(ModBlocks.COLLECTOR_CHEST.get(), new Item.Properties().tab(BreadCraftEconomy.BREADCRAFT_GROUP)));
}
