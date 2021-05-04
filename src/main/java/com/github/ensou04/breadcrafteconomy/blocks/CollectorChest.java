package com.github.ensou04.breadcrafteconomy.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CollectorChest extends Block implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CollectorChest() {
        super(Properties.copy(Blocks.ENDER_CHEST).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
        );
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
}
