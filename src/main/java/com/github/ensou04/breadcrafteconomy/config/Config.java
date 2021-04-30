package com.github.ensou04.breadcrafteconomy.config;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.github.ensou04.breadcrafteconomy.world.CoinDropEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import static com.github.ensou04.breadcrafteconomy.BreadCraftEconomy.LOGGER;
import static com.github.ensou04.breadcrafteconomy.BreadCraftEconomy.MODID;

@Mod.EventBusSubscriber(modid = BreadCraftEconomy.MODID, bus = Bus.MOD)
public class Config {

    static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        {
            final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
            SERVER = specPair.getLeft();
            SERVER_SPEC = specPair.getRight();
        }
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (config.getSpec() == SERVER_SPEC) {
            BakeServer(config);
            CoinDropEvent.Init();
            //LOGGER.debug(MODID.toUpperCase() + ": Baked Config");
        }
    }

    public static void BakeServer(ModConfig config){
        CoinDropEvent.MaxDrops = SERVER.MaxDrops.get();
        CoinDropEvent.CoinDropAmount = SERVER.CoinDropAmount.get();
        CoinDropEvent.DropRateMax = SERVER.DropRateMax.get().floatValue();
        CoinDropEvent.DropRateMin = SERVER.DropRateMin.get().floatValue();
        CoinDropEvent.GoldRate = SERVER.GoldRate.get().floatValue();
    }

    public static class ServerConfig {

        public ForgeConfigSpec.ConfigValue<Integer> MaxDrops;
        public ForgeConfigSpec.ConfigValue<Integer> CoinDropAmount;
        public ForgeConfigSpec.DoubleValue DropRateMax;
        public ForgeConfigSpec.DoubleValue DropRateMin;
        public ForgeConfigSpec.DoubleValue GoldRate;

        ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Main Server Settings").push("General");
            MaxDrops = builder.comment("Maximum Available Coin Drops in the World").define("Max Drops", 1280);
            CoinDropAmount = builder.comment("Number of possible coin loots each drop").define("Coin Amount", 2);
            DropRateMax = builder.comment("Starting Drop Rate").defineInRange("Max Rate", 0.1d, 0, 1);
            DropRateMin = builder.comment("Ending Drop Rate").defineInRange("Min Rate", 0.01d, 0, 1);
            GoldRate = builder.comment(
                    "Percentage that a Gold Coin Drops instead of Silver",
                    "Gold Rate starts at 0 and ends at the given value")
                    .defineInRange("Gold Rate", 0.5f, 0, 1);
        }
    }
}

