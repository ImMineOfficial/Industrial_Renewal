package cassiokf.industrialrenewal.tileentity;

import cassiokf.industrialrenewal.blocks.BlockSmallWindTurbine;
import cassiokf.industrialrenewal.blocks.BlockWindTurbinePillar;
import cassiokf.industrialrenewal.tileentity.tubes.TileEntityMultiBlocksTube;
import cassiokf.industrialrenewal.util.VoltsEnergyContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEntityWindTurbinePillar extends TileEntityMultiBlocksTube<TileEntityWindTurbinePillar> implements ICapabilityProvider, ITickable
{
    private final VoltsEnergyContainer energyContainer;
    private final VoltsEnergyContainer dummyEnergyContainer;

    private int energyGenerated;

    private int tick;

    private EnumFacing[] faces = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN};
    private BlockPos turbinePos;
    private boolean isBase;

    public TileEntityWindTurbinePillar()
    {
        this.energyContainer = new VoltsEnergyContainer(1024, 1024, 1024);
        this.dummyEnergyContainer = new VoltsEnergyContainer(0, 0, 0);
    }

    @Override
    public void update()
    {
        super.update();
        if (isMaster())
        {
            if (!world.isRemote)
            {
                energyContainer.setMaxEnergyStored(Math.max(1024 * getPosSet().size(), energyContainer.getEnergyStored()));
                for (BlockPos currentPos : getPosSet().keySet())
                {
                    TileEntity te = world.getTileEntity(currentPos);
                    EnumFacing face = getPosSet().get(currentPos);
                    if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, face.getOpposite()))
                    {
                        IEnergyStorage eStorage = te.getCapability(CapabilityEnergy.ENERGY, face.getOpposite());
                        if (eStorage != null && eStorage.canReceive())
                        {
                            this.energyContainer.extractEnergy(eStorage.receiveEnergy(this.energyContainer.extractEnergy(this.energyContainer.getMaxOutput(), true), false), false);
                        }
                    }
                }
            } else if (getTurbinePos() != null)
            {
                tick++;
                if (tick % 10 == 0)
                {
                    tick = 0;
                    if (world.getBlockState(turbinePos).getBlock() instanceof BlockSmallWindTurbine && world.getTileEntity(turbinePos) instanceof TileEntitySmallWindTurbine)
                    {
                        TileEntitySmallWindTurbine te = (TileEntitySmallWindTurbine) world.getTileEntity(turbinePos);
                        if (te != null) energyGenerated = te.getEnergyGenerated();
                        else energyGenerated = 0;
                    } else
                    {
                        energyGenerated = 0;
                        forceNewTurbinePos();
                    }
                }
            }
        }
    }

    @Override
    public EnumFacing[] getFacesToCheck()
    {
        return faces;
    }

    @Override
    public boolean instanceOf(TileEntity te)
    {
        return te instanceof TileEntityWindTurbinePillar;
    }

    @Override
    public void checkForOutPuts(BlockPos bPos)
    {
        isBase = getIsBase();
        if (world.isRemote) return;
        for (EnumFacing face : EnumFacing.HORIZONTALS)
        {
            BlockPos currentPos = pos.offset(face);
            if (isBase)
            {
                IBlockState state = world.getBlockState(currentPos);
                TileEntity te = world.getTileEntity(currentPos);
                boolean hasMachine = !(state.getBlock() instanceof BlockWindTurbinePillar)
                        && te != null && te.hasCapability(CapabilityEnergy.ENERGY, face.getOpposite());

                if (hasMachine && te.getCapability(CapabilityEnergy.ENERGY, face.getOpposite()).canReceive())
                    getMaster().addMachine(currentPos, face);
                else getMaster().removeMachine(currentPos);
            } else
            {
                getMaster().removeMachine(currentPos);
            }
        }
        this.Sync();
    }

    private BlockPos getTurbinePos()
    {
        if (turbinePos != null) return turbinePos;
        return forceNewTurbinePos();
    }

    private BlockPos forceNewTurbinePos()
    {
        int n = 1;
        while (world.getBlockState(pos.up(n)).getBlock() instanceof BlockWindTurbinePillar)
        {
            n++;
        }
        if (world.getBlockState(pos.up(n)).getBlock() instanceof BlockSmallWindTurbine) turbinePos = pos.up(n);
        return turbinePos;
    }

    public EnumFacing getBlockFacing()
    {
        return this.world.getBlockState(this.pos).getValue(BlockWindTurbinePillar.FACING);
    }

    public float getGenerationforGauge()
    {
        float currentAmount = getEnergyGenerated();
        float totalCapacity = TileEntitySmallWindTurbine.getMaxGeneration();
        currentAmount = currentAmount / totalCapacity;
        return currentAmount * 180f;
    }

    public int getEnergyGenerated()
    {
        return getMaster().energyGenerated;
    }

    public boolean isBase()
    {
        return isBase;
    }

    public boolean getIsBase()
    {
        IBlockState state = world.getBlockState(pos.down());
        return !(state.getBlock() instanceof BlockWindTurbinePillar);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return (capability == CapabilityEnergy.ENERGY && (facing == EnumFacing.UP || isBase())) || super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY && (facing == EnumFacing.UP))
            return CapabilityEnergy.ENERGY.cast(getMaster().energyContainer);
        if (capability == CapabilityEnergy.ENERGY && (isBase()))
            return CapabilityEnergy.ENERGY.cast(dummyEnergyContainer);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        this.energyContainer.deserializeNBT(compound.getCompoundTag("StoredIR"));
        this.isBase = compound.getBoolean("base");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("StoredIR", this.energyContainer.serializeNBT());
        compound.setBoolean("base", this.isBase);
        return super.writeToNBT(compound);
    }
}
