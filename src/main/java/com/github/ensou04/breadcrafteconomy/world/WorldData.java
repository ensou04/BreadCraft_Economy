package com.github.ensou04.breadcrafteconomy.world;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.function.Supplier;

import static com.github.ensou04.breadcrafteconomy.BreadCraftEconomy.*;

public class WorldData extends WorldSavedData implements Supplier {

    public int TotalCoins = 0;

    public WorldData() {
        super(MODID);
    }

    public WorldData(String name) {
        super(name);
    }

    @Override
    public void load(CompoundNBT compoundNBT) {
        TotalCoins = compoundNBT.getInt("Total Coins");
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        compoundNBT.putInt("Total Coins", TotalCoins);
        return compoundNBT;
    }

    @Override
    public boolean isDirty()
    {
        return true;
    }

    public static WorldData getWorldData(ServerWorld world){

        DimensionSavedDataManager storage = world.getDataStorage();
        Supplier<WorldData> supplier = new WorldData();
        WorldData saver = storage.get(supplier, MODID);
        if(saver == null){
            saver = new WorldData();
            storage.set(saver);
        }

        return saver;
    }

    @Override
    public Object get() {
        return this;
    }
}


