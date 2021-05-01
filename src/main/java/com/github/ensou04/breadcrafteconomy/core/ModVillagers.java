package com.github.ensou04.breadcrafteconomy.core;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModVillagers {
    public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES, BreadCraftEconomy.MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, BreadCraftEconomy.MODID);

    public static final RegistryObject<PointOfInterestType> BANKER_STATION_POI = POINT_OF_INTEREST.register("banker_safe",
            () -> new PointOfInterestType("banker_station", PointOfInterestType.getBlockStates(ModBlocks.BANKER_SAFE.get()),1,1));

    public static final RegistryObject<PointOfInterestType> COLLECTOR_STATION_POI = POINT_OF_INTEREST.register("collector_chest",
            () -> new PointOfInterestType("collector_chest", PointOfInterestType.getBlockStates(ModBlocks.COLLECTOR_CHEST.get()),1,1));

    public static final RegistryObject<VillagerProfession> BANKER_PROF = PROFESSIONS.register("banker",
            () -> new VillagerProfession("banker", BANKER_STATION_POI.get(), ImmutableSet.of(),ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN));

    public static final RegistryObject<VillagerProfession> COLLECTOR_PROF = PROFESSIONS.register("collector",
            () -> new VillagerProfession("collector", COLLECTOR_STATION_POI.get(), ImmutableSet.of(),ImmutableSet.of(), SoundEvents.ENDER_CHEST_CLOSE));

}
