package com.github.ensou04.breadcrafteconomy.world;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.github.ensou04.breadcrafteconomy.core.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.Collection;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BreadCraftEconomy.MODID, bus = Bus.FORGE)
public class CoinDropEvent {

    // Settings
    public static int MaxDrops = 128;
    public static float DropRateMax = 1f;
    public static float DropRateMin = 0.1f;
    public static float GoldRate = 0.5f;
    public static int CoinDropAmount = 2;

    // Data (For Saving)
    public static int TotalCoins = 0;

    // Realtime Data
    private static int availableDrops = 0;
    private static float currentRate = 0;
    private static float currentGoldRate = 0;

    private static Random rnd = new Random();
    private static float RandomNumber(float min,float max){
        return MathHelper.nextFloat(rnd, min, max);
    }

    public static void Init()
    {
        availableDrops = Math.max(MaxDrops - TotalCoins, 0);
        currentRate = Math.max(((float) availableDrops / MaxDrops) * DropRateMax, DropRateMin);
        currentGoldRate = ((float) TotalCoins / MaxDrops) * GoldRate;
    }

    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event){

        if(event.getMessage().contains(".Drop Stats")) {
            event.setCanceled(true);
            String message = String.join("\n",
                    "=== COIN DROP STATS ===",
                    "Available Drops = " + availableDrops,
                    "Max Drops = " + MaxDrops,
                    "Gold Rate = " + String.format("%.2f%%",currentGoldRate * 100),
                    "Current Rate = " + String.format("%.2f%%",currentRate * 100)
            );

            event.getPlayer().sendMessage(new StringTextComponent(message),event.getPlayer().getUUID());
        }

        if(event.getMessage().contains(".Simulate Drops")) {
            event.setCanceled(true);

            event.getPlayer().sendMessage(new StringTextComponent(
                    SimulateDrops(availableDrops,currentRate,GoldRate, DropRateMin,5,25)),
                    event.getPlayer().getUUID()
            );
        }

        if(!event.getPlayer().isCreative())
            return;

        if(event.getMessage().contains(".Reset Drops")) {
            event.setCanceled(true);

            TotalCoins = 0;
            availableDrops = Math.max(MaxDrops - TotalCoins, 0);
            currentGoldRate = ((float) TotalCoins / MaxDrops) * GoldRate;
            currentRate = Math.max(((float) availableDrops / MaxDrops) * DropRateMax, DropRateMin);

            String message = String.join("\n",
                    "=== RESET DROPS ===",
                    "Available Drops = " + availableDrops,
                    "Max Drops = " + MaxDrops,
                    "Gold Rate = " + String.format("%.2f%%",currentGoldRate * 100),
                    "Current Rate= " + String.format("%.2f%%",currentRate * 100)
            );

            event.getPlayer().sendMessage(new StringTextComponent(message),event.getPlayer().getUUID());
        }
    }

    @SubscribeEvent
    public static void onMobDropEvent(LivingDropsEvent event)
    {
        if(event.getEntity().getCommandSenderWorld().isClientSide())
            return;

        if(TotalCoins > MaxDrops)
            return;

        if (!(event.getEntity() instanceof MonsterEntity))
            return;

        if (currentRate > RandomNumber(0,1)) {
            int coinRate = MathHelper.nextInt(rnd, 1, CoinDropAmount);

            Item coinItem = ModItems.COIN_SILVER.get();

            if(currentGoldRate >  RandomNumber(0,1)){
                coinItem = ModItems.COIN_GOLD.get();
            }

            ItemStack coinDrop = new ItemStack(coinItem, coinRate);

            Entity dropEntity = event.getEntity();
            Collection<ItemEntity> drops = event.getDrops();

            drops.add(new ItemEntity(dropEntity.getCommandSenderWorld(),
                    dropEntity.position().x,
                    dropEntity.position().y,
                    dropEntity.position().z,
                    coinDrop
            ));

            TotalCoins += coinRate;

            currentGoldRate = ((float) TotalCoins / MaxDrops) * GoldRate;
            availableDrops = Math.max(MaxDrops - TotalCoins, 0);
            currentRate = Math.max(((float) availableDrops / MaxDrops) * DropRateMax, DropRateMin);
        }
    }

    public static String SimulateDrops(int maxDrops, float dropRate, float goldRate, float lowerRate, int killTimeMin,int killTimeMax)
    {
        int tDrops = 0;
        int goldDrops = 0;
        int aDrops = Math.max(maxDrops - tDrops,0);
        float cRate = Math.max(((float)aDrops / maxDrops) * dropRate, lowerRate);
        float gRate = ((float) TotalCoins / MaxDrops) * goldRate;

        int attempts = 0;
        int attemptsSeconds = 0;

        for(int i = 0; i < maxDrops; i = tDrops){
            if(tDrops > maxDrops)
                break;

            if(cRate > RandomNumber(0,1)){
                int coinRate = MathHelper.nextInt(rnd,1,2);

                if(gRate > RandomNumber(0,1)) {
                    goldDrops += coinRate;
                }

                tDrops+=coinRate;

                gRate = ((float) TotalCoins / MaxDrops) * goldRate;
                aDrops = Math.max(maxDrops - tDrops,0);
                cRate = Math.max(((float)aDrops / maxDrops) * dropRate, lowerRate);
            }

            attempts++;
            attemptsSeconds+=MathHelper.nextInt(rnd,killTimeMin,killTimeMax);
        }

        return String.join("\n",
                "=== SIMULATION ===",
                "Max Drops = " + maxDrops,
                "Total Drops = " + tDrops,
                "Gold Drops = " + goldDrops,
                "Total Kills = " + attempts,
                "Total Time (Seconds) = " + attemptsSeconds
        );
    }
}

