package cassiokf.industrialrenewal.blocks;


import cassiokf.industrialrenewal.IndustrialRenewal;
import net.minecraft.block.BlockRail;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Date;

public class BlockCrossingRail extends BlockRail {

    protected String name;

    private final long PERIOD = 3000L; // Adjust to suit timing
    private long lastTime = System.currentTimeMillis() - PERIOD;

    public BlockCrossingRail(String name) {


        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(0.8f);
        setSoundType(SoundType.WOOD);
        setCreativeTab(IndustrialRenewal.creativeTab);
    }


    @Override
    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
        /**Melhorar isso, passar para a locomotiva porque executa só 1 som por vez em todo mapa */
        long thisTime = System.currentTimeMillis();
        if ((thisTime - lastTime) >= PERIOD) {
            lastTime = thisTime;
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound((EntityPlayer) null, (double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("industrialrenewal:train_horn")), SoundCategory.BLOCKS, 2.0F, 1.0F);
        }
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canMakeSlopes(IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public void registerItemModel(Item itemBlock) {
        IndustrialRenewal.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }
}