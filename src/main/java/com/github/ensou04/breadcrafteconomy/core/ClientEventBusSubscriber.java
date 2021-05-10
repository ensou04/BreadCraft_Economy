package com.github.ensou04.breadcrafteconomy.core;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BreadCraftEconomy.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        //ClientRegistry.bindTileEntityRenderer(ModTileEntities.COLLECTOR_CHEST_TILE.get(), ChestTileEntityRenderer::new);
    }
}
