package cassiokf.industrialrenewal.tileentity;

import cassiokf.industrialrenewal.init.TileEntityRegister;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityCatWalk extends TileEntitySyncable
{
    private final Set<Direction> blackListedFaces = EnumSet.noneOf(Direction.class);

    public TileEntityCatWalk()
    {
        super(TileEntityRegister.CAT_WALK);
    }

    public boolean toggleFacing(final Direction facing)
    {
        if (blackListedFaces.contains(facing))
        {
            blackListedFaces.remove(facing);
            return false;
        } else
        {
            blackListedFaces.add(facing);
            return true;
        }
    }

    public boolean disableFacing(final Direction facing)
    {
        if (blackListedFaces.contains(facing))
        {
            blackListedFaces.remove(facing);
            return false;
        } else
        {
            return true;
        }
    }

    public boolean activeFacing(final Direction facing)
    {
        if (blackListedFaces.contains(facing))
        {
            return false;
        } else
        {
            blackListedFaces.add(facing);
            return true;
        }
    }

    public boolean isFacingBlackListed(final Direction facing)
    {
        return blackListedFaces.contains(facing);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        final int[] enabledFacingIndices = blackListedFaces.stream()
                .mapToInt(Direction::getIndex)
                .toArray();
        compound.putIntArray("EnabledFacings", enabledFacingIndices);
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        blackListedFaces.clear();
        final int[] enabledFacingIndices = compound.getIntArray("EnabledFacings");
        for (final int index : enabledFacingIndices)
        {
            blackListedFaces.add(Direction.byIndex(index));
        }
        super.read(compound);
    }
}
