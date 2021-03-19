package com.itayfeder.essential_foods.common.blocks;

import com.itayfeder.essential_foods.init.ItemInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ChiliPlantBlock extends CropsBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D),
            Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D),
            Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 14.0D, 9.0D, 14.0D),
            Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 14.0D, 10.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 13.0D, 11.0D, 13.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 13.0D, 12.0D, 13.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 13.0D, 13.0D, 13.0D),
            Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D)};


    public ChiliPlantBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected IItemProvider getSeedsItem() {
        return ItemInit.CHILI_SEEDS.get();
    }


    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(this.getAgeProperty())];
    }
}
