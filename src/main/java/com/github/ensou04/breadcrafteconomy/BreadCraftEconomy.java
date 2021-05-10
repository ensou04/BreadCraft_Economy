package com.github.ensou04.breadcrafteconomy;

import com.github.ensou04.breadcrafteconomy.config.Config;
import com.github.ensou04.breadcrafteconomy.core.ModBlocks;
import com.github.ensou04.breadcrafteconomy.core.ModItems;
import com.github.ensou04.breadcrafteconomy.core.ModTileEntities;
import com.github.ensou04.breadcrafteconomy.core.ModVillagers;
import com.github.ensou04.breadcrafteconomy.world.CoinDropEvent;
import com.github.ensou04.breadcrafteconomy.world.WorldData;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BreadCraftEconomy.MODID)
public class BreadCraftEconomy
{
    // Modid
    public  static  final String MODID = "breadcrafteconomy";

    public static final ItemGroup BREADCRAFT_GROUP = new CustomGroup("breadcrafttab");

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public BreadCraftEconomy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModVillagers.PROFESSIONS.register(modEventBus);
        ModVillagers.POINT_OF_INTEREST.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::OnWorldLoad);
        MinecraftForge.EVENT_BUS.addListener(this::OnWorldSave);

        LOGGER.debug(MODID.toUpperCase() + ": Loaded");
    }

    private void OnWorldLoad(WorldEvent.Load event){

        if(!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld){

            WorldData worldData = WorldData.getWorldData((ServerWorld)event.getWorld());
            CoinDropEvent.TotalCoins = 0;

            if(worldData != null){
                CoinDropEvent.TotalCoins = worldData.TotalCoins;
            }

            CoinDropEvent.Init();

            LOGGER.debug(MODID.toUpperCase() + ": Server World Is Loaded");
        }
    }

    private void OnWorldSave(WorldEvent.Save event){
        if(!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld){
            WorldData worldData = WorldData.getWorldData((ServerWorld)event.getWorld());
            worldData.TotalCoins = CoinDropEvent.TotalCoins;
            worldData.setDirty();

            LOGGER.debug(MODID.toUpperCase() + ": Server World Is Saved");
        }
    }

    public static class CustomGroup extends ItemGroup{
        public CustomGroup(String label) {
            super(label);
        }

        @Override
        public ItemStack makeIcon() {
            return ModItems.COIN_GOLD.get().getDefaultInstance();
        }
    }
}
