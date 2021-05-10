package com.github.ensou04.breadcrafteconomy.core;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.github.ensou04.breadcrafteconomy.tileentities.BankerSafeTile;
import com.github.ensou04.breadcrafteconomy.tileentities.CollectorChestTile;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BreadCraftEconomy.MODID);

    public static final RegistryObject<TileEntityType<BankerSafeTile>> BANKER_SAFE_TILE = TILE_ENTITIES.register("banker_safe",
            () -> TileEntityType.Builder.of(BankerSafeTile::new, ModBlocks.BANKER_SAFE.get()).build(null));

    public static final RegistryObject<TileEntityType<CollectorChestTile>> COLLECTOR_CHEST_TILE = TILE_ENTITIES.register("collector_chest",
            () -> TileEntityType.Builder.of(CollectorChestTile::new, ModBlocks.COLLECTOR_CHEST.get()).build(null));

}
