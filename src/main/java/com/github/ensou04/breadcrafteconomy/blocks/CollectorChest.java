package com.github.ensou04.breadcrafteconomy.blocks;

import com.github.ensou04.breadcrafteconomy.core.ModTileEntities;
import com.github.ensou04.breadcrafteconomy.tileentities.BankerSafeTile;
import com.github.ensou04.breadcrafteconomy.tileentities.CollectorChestTile;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CollectorChest extends AbstractChestBlock<CollectorChestTile> implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CollectorChest() {
        super(Properties.copy(Blocks.ENDER_CHEST).noOcclusion(),
                () -> ModTileEntities.COLLECTOR_CHEST_TILE.get());

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
        );
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new CollectorChestTile();
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (p_225533_2_.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = p_225533_2_.getBlockEntity(p_225533_3_);
            EnderChestInventory enderchestinventory = p_225533_4_.getEnderChestInventory();

            if (enderchestinventory != null && tileentity instanceof CollectorChestTile) {
                p_225533_4_.openMenu(new SimpleNamedContainerProvider((p_226928_1_, p_226928_2_, p_226928_3_) -> {
                    return ChestContainer.threeRows(p_226928_1_, p_226928_2_, enderchestinventory);
                }, new TranslationTextComponent("container.enderchest")));

                p_225533_4_.awardStat(Stats.OPEN_ENDERCHEST);
                PiglinTasks.angerNearbyPiglins(p_225533_4_, true);
            }
            return ActionResultType.CONSUME;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        for(int i = 0; i < 3; ++i) {
            int j = p_180655_4_.nextInt(2) * 2 - 1;
            int k = p_180655_4_.nextInt(2) * 2 - 1;
            double d0 = (double)p_180655_3_.getX() + 0.5D + 0.25D * (double)j;
            double d1 = (double)((float)p_180655_3_.getY() + p_180655_4_.nextFloat());
            double d2 = (double)p_180655_3_.getZ() + 0.5D + 0.25D * (double)k;
            double d3 = (double)(p_180655_4_.nextFloat() * (float)j);
            double d4 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.125D;
            double d5 = (double)(p_180655_4_.nextFloat() * (float)k);
            p_180655_2_.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING,context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (p_196271_1_.getValue(WATERLOGGED)) {
            p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
        }

        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING,WATERLOGGED);
    }

    @Override
    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity entity, ItemStack itemStack) {
        if(entity instanceof PlayerEntity){
            EffectInstance hero = new EffectInstance(Effects.HERO_OF_THE_VILLAGE,1000000,1,false,false,true);
            entity.addEffect(hero);
        }
    }

    @Override
    public TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> combine(BlockState p_225536_1_, World p_225536_2_, BlockPos p_225536_3_, boolean p_225536_4_) {
        return TileEntityMerger.ICallback::acceptNone;
    }
}
