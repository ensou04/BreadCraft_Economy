package com.github.ensou04.breadcrafteconomy.world;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.github.ensou04.breadcrafteconomy.core.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.List;
import java.util.Random;

import static com.github.ensou04.breadcrafteconomy.core.ModVillagers.BANKER_PROF;
import static com.github.ensou04.breadcrafteconomy.core.ModVillagers.COLLECTOR_PROF;

@Mod.EventBusSubscriber(modid = BreadCraftEconomy.MODID, bus = Bus.FORGE)
public class VillagerProfessionsEvent {

    // Settings
    public static int CoinsPerDiamond = 4;

    @SubscribeEvent
    public static void registerTrades(net.minecraftforge.event.village.VillagerTradesEvent event) {
        Int2ObjectMap<List<ITrade>> trades = event.getTrades();

        if (event.getType() == BANKER_PROF.get()) {
            trades.get(1).add(new BasicTrade(TradeItem(Items.DIAMOND), TradeItem(ModItems.COIN_SILVER.get(),CoinsPerDiamond), 16, 0, 0f));
            trades.get(1).add(new BasicTrade(TradeItem(Items.DIAMOND_BLOCK), TradeItem(ModItems.COIN_GOLD.get(), CoinsPerDiamond), 1, 0, 0f));
        }

        if (event.getType() == COLLECTOR_PROF.get()) {
            trades.get(1).add(new BasicTrade(TradeItem(ModItems.COIN_SILVER.get(),2), TradeItem(Items.ENDER_CHEST), 16, 2, 0.05f));
            trades.get(1).add(new BasicTrade(TradeItem(ModItems.COIN_SILVER.get(),16), TradeItem(Items.SHULKER_SHELL, 2), 12, 2, 0.05f));
            trades.get(2).add(new BasicTrade(TradeItem(ModItems.COIN_SILVER.get(),32), TradeItem(Items.TOTEM_OF_UNDYING), 4, 5, 0.05f));
            trades.get(3).add(new BasicTrade(TradeItem(ModItems.COIN_SILVER.get(),64), TradeItem(Items.ENCHANTED_GOLDEN_APPLE, 16), 4, 10, 0.05f));
            trades.get(4).add(new BasicTrade(TradeItem(ModItems.COIN_GOLD.get(),17), TradeItem(Items.END_CRYSTAL), 4, 15, 0.05f));
            trades.get(4).add(new BasicTrade(TradeItem(ModItems.COIN_GOLD.get(),18), TradeItem(Items.HEART_OF_THE_SEA), 4, 15, 0.05f));
            trades.get(5).add(new BasicTrade(TradeItem(ModItems.COIN_GOLD.get(),22), TradeItem(Items.ELYTRA), 4, 30, 0.05f));
            trades.get(5).add(new BasicTrade(TradeItem(ModItems.COIN_GOLD.get(),32), TradeItem(Items.NETHER_STAR), 4, 30, 0.05f));
        }
    }

    @SubscribeEvent
    public static void wanderingTrades(WandererTradesEvent event){
        Random rnd = new Random();
        int coinTradePrice = CoinsPerDiamond + rnd.nextInt(CoinsPerDiamond/2);
        float discount = MathHelper.nextFloat(new Random(),0.9f,0.75f);

        List<ITrade> trades = event.getGenericTrades();
        trades.add(new BasicTrade(TradeItem(Items.DIAMOND), TradeItem(ModItems.COIN_SILVER.get(), coinTradePrice), 16,0,0));
        trades.add(new BasicTrade(TradeItem(Items.DIAMOND_BLOCK), TradeItem(ModItems.COIN_GOLD.get(), coinTradePrice), 4,0,0));
        trades.add(new BasicTrade(TradeItem(ModItems.COIN_SILVER.get(),(int) (64 * discount)), TradeItem(Items.ENCHANTED_GOLDEN_APPLE, 16), 4, 10, 0.05f));
        trades.add(new BasicTrade(TradeItem(ModItems.COIN_GOLD.get(),(int) (18 * discount)), TradeItem(Items.HEART_OF_THE_SEA, 1), 4, 15, 0.05f));
        trades.add(new BasicTrade(TradeItem(ModItems.COIN_GOLD.get(),(int) (32 * discount)), TradeItem(Items.NETHER_STAR), 4, 30, 0.05f));
    }

    private static ItemStack TradeItem(Item item){
        return new ItemStack(item,1);
    }

    private static ItemStack TradeItem(Item item, int price){
        return new ItemStack(item,price);
    }
}
