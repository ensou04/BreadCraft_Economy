package com.github.ensou04.breadcrafteconomy.core;

import com.github.ensou04.breadcrafteconomy.blocks.BankerSafe;
import com.github.ensou04.breadcrafteconomy.blocks.CollectorChest;
import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BreadCraftEconomy.MODID);

    // Blocks Register
    public static final RegistryObject<Block> BANKER_SAFE = BLOCKS.register("banker_safe", BankerSafe::new);
    public static final RegistryObject<Block> COLLECTOR_CHEST = BLOCKS.register("collector_chest", CollectorChest::new);
}
