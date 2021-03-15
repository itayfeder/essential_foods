package com.itayfeder.essential_foods.common.blocks;

import com.itayfeder.essential_foods.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class RicePlantBlock extends CropsBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D)};


    public RicePlantBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected IItemProvider getSeedsItem() {
        return ItemInit.RICE_SHOOTS.get();
    }


    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(this.getAgeProperty())];
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState soil = worldIn.getBlockState(pos.down());
        //if (soil.canSustainPlant(worldIn, pos.down(), Direction.UP, this)) return true;
        BlockState blockstate = worldIn.getBlockState(pos.down());
        if (blockstate.isIn(Blocks.SAND) || blockstate.isIn(Blocks.RED_SAND)) {
            BlockPos blockpos = pos.down();

            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = worldIn.getBlockState(blockpos.offset(direction));
                FluidState fluidstate = worldIn.getFluidState(blockpos.offset(direction));
                if (fluidstate.isTagged(FluidTags.WATER) || blockstate1.isIn(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return false;
    }

}
