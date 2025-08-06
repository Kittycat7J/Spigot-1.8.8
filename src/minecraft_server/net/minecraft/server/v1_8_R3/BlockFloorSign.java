package net.minecraft.server.v1_8_R3;

public class BlockFloorSign extends BlockSign
{
    public static final BlockStateInteger ROTATION = BlockStateInteger.of("rotation", 0, 15);

    public BlockFloorSign()
    {
        this.j(this.blockStateList.getBlockData().set(ROTATION, Integer.valueOf(0)));
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!p_doPhysics_1_.getType(p_doPhysics_2_.down()).getBlock().getMaterial().isBuildable())
        {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }

        super.doPhysics(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(ROTATION, Integer.valueOf(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Integer)p_toLegacyData_1_.get(ROTATION)).intValue();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {ROTATION});
    }
}
