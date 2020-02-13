package cassiokf.industrialrenewal.blocks;

import cassiokf.industrialrenewal.tileentity.TileEntityLocker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLocker extends BlockTileEntity<TileEntityLocker>
{

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public BlockLocker(Block.Properties property)
    {
        super(property);
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
    {
        if (worldIn.isRemote)
        {
            return ActionResultType.SUCCESS;
        }
        if (player.isCrouching())
        {
            worldIn.setBlockState(pos, state.cycle(OPEN));
            return ActionResultType.SUCCESS;
        }
        LockableLootTileEntity ilockablecontainer = getContainer(worldIn, pos);
        if (ilockablecontainer != null)
        {
            player.openContainer(ilockablecontainer);
            player.addStat(Stats.OPEN_CHEST);
        }
        return ActionResultType.SUCCESS;
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof LockableLootTileEntity)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (LockableLootTileEntity) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    public boolean hasComparatorInputOverride(BlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstoneFromInventory(this.getLockableContainer(worldIn, pos));
    }

    @Nullable
    public LockableLootTileEntity getLockableContainer(World worldIn, BlockPos pos)
    {
        return this.getContainer(worldIn, pos);
    }

    @Nullable
    public LockableLootTileEntity getContainer(World worldIn, BlockPos pos)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityLocker))
        {
            return null;
        }
        return (TileEntityLocker) tileentity;
    }

    private boolean connectDown(IBlockReader world, BlockPos pos)
    {
        BlockState downState = world.getBlockState(pos.down());
        return downState.getBlock() instanceof BlockLocker;
    }


    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return stateIn.with(DOWN, connectDown(worldIn, currentPos));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(FACING, context.getPlayer().getHorizontalFacing()).with(OPEN, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, OPEN, DOWN);
    }

    @Nullable
    @Override
    public TileEntityLocker createTileEntity(BlockState state, IBlockReader world)
    {
        return new TileEntityLocker();
    }
}
