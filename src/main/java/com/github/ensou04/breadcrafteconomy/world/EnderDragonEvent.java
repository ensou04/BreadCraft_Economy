package com.github.ensou04.breadcrafteconomy.world;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.github.ensou04.breadcrafteconomy.core.ModItems;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BreadCraftEconomy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnderDragonEvent {

    public static Random random = new Random();
    public static int randomNumber = MathHelper.nextInt(random,1,2);
    public static double randomPositionX(){
        return MathHelper.nextDouble(random,-32,32);
    }
    public static double randomPositionY(){
        return MathHelper.nextDouble(random,-32,32);
    }
    @SubscribeEvent
    public static void onDragonDropEvent(LivingDropsEvent event)
    {
        if(event.getEntity().getCommandSenderWorld().isClientSide())
            return;

        if (!(event.getEntity() instanceof EnderDragonEntity))
            return;

        Entity entity = event.getEntity();
        World world = entity.getCommandSenderWorld();
        // Coins
        for(int i = 0; i < 512; i++){
            if(i % 4 == 0){
                if(new Random().nextFloat() > 0.5f){
                    event.getDrops().add(new ItemEntity(world, randomPositionX(), entity.position().y+1, randomPositionY(), new ItemStack(ModItems.COIN_GOLD.get(),randomNumber)));
                } else {
                    event.getDrops().add(new ItemEntity(world, randomPositionX(), entity.position().y+1, randomPositionY(), new ItemStack(ModItems.COIN_SILVER.get(),randomNumber)));
                }
            }
        }
        // Ender Wayfinder
        event.getDrops().add(new ItemEntity(world, 0, entity.position().y+1, 0, new ItemStack(ModItems.ENDER_WAYFINDER.get(),1)));
    }
}
