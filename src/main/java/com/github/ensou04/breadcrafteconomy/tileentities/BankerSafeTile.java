package com.github.ensou04.breadcrafteconomy.tileentities;

import com.github.ensou04.breadcrafteconomy.core.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BankerSafeTile extends LockableLootTileEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

    public BankerSafeTile(){
        this(ModTileEntities.BANKER_SAFE_TILE.get());
    }
    public BankerSafeTile(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_230337_2_)) {
            ItemStackHelper.loadAllItems(p_230337_2_, this.items);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);
        if (!this.trySaveLootTable(p_189515_1_)) {
            ItemStackHelper.saveAllItems(p_189515_1_, this.items);
        }

        return p_189515_1_;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.breadcrafteconomy.banker_safe");
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return ChestContainer.threeRows(p_213906_1_, p_213906_2_, this);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }

    @Override
    public int getContainerSize() {
        return 27;
    }


}
