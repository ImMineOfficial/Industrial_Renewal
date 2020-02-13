package cassiokf.industrialrenewal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public abstract class BlockTileEntity<TE extends TileEntity> extends BlockBase
{

    public BlockTileEntity(Block.Properties properties)
    {
        super(properties.notSolid());
    }

    public TE getTileEntity(IBlockReader world, BlockPos pos)
    {
        return (TE) world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public abstract TE createTileEntity(BlockState state, IBlockReader world);

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }
}