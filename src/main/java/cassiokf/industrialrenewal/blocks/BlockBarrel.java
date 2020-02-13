package cassiokf.industrialrenewal.blocks;

import cassiokf.industrialrenewal.init.ModItems;
import cassiokf.industrialrenewal.tileentity.TileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

public class BlockBarrel extends BlockTileEntity<TileEntityBarrel>
{
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty FRAME = BooleanProperty.create("frame");

    public BlockBarrel(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
    {
        if (!worldIn.isRemote)
        {
            TileEntityBarrel te = (TileEntityBarrel) worldIn.getTileEntity(pos);
            if (!FluidUtil.interactWithFluidHandler(player, handIn, worldIn, pos, p_225533_6_.getFace()))
                player.sendMessage(new StringTextComponent(te.GetChatQuantity()));
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {

        return getDefaultState().with(FACING, context.getPlayer().getHorizontalFacing()).with(FRAME, context.getPlayer().isCrouching());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, FRAME);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        TileEntityBarrel te = (TileEntityBarrel) worldIn.getTileEntity(pos);
        ItemStack itemst = SaveStackContainer(te);
        spawnAsEntity(worldIn, pos, itemst);
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        TileEntityBarrel te = (TileEntityBarrel) world.getTileEntity(pos);
        return SaveStackContainer(te);
    }

    private ItemStack SaveStackContainer(TileEntityBarrel te)
    {
        ItemStack stack = new ItemStack(ModItems.barrel);
        if (te != null)
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt == null) nbt = new CompoundNBT();
            if (te.tank.getFluid() != null)
            {
                te.tank.writeToNBT(nbt);
                stack.setTag(nbt);
            }
        }
        return stack;
    }

    @Nullable
    @Override
    public TileEntityBarrel createTileEntity(BlockState state, IBlockReader world)
    {
        return new TileEntityBarrel();
    }
}
