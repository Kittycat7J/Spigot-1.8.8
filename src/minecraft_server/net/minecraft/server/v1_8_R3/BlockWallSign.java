package net.minecraft.server.v1_8_R3;

public class BlockWallSign extends BlockSign
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);

    public BlockWallSign()
    {
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        EnumDirection enumdirection = (EnumDirection)p_updateShape_1_.getType(p_updateShape_2_).get(FACING);
        float f = 0.28125F;
        float f1 = 0.78125F;
        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.125F;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        switch (enumdirection)
        {
            case NORTH:
                this.a(f2, f, 1.0F - f4, f3, f1, 1.0F);
                break;

            case SOUTH:
                this.a(f2, f, 0.0F, f3, f1, f4);
                break;

            case WEST:
                this.a(1.0F - f4, f, f2, 1.0F, f1, f3);
                break;

            case EAST:
                this.a(0.0F, f, f2, f4, f1, f3);
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        EnumDirection enumdirection = (EnumDirection)p_doPhysics_3_.get(FACING);

        if (!p_doPhysics_1_.getType(p_doPhysics_2_.shift(enumdirection.opposite())).getBlock().getMaterial().isBuildable())
        {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }

        super.doPhysics(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        EnumDirection enumdirection = EnumDirection.fromType1(p_fromLegacyData_1_);

        if (enumdirection.k() == EnumDirection.EnumAxis.Y)
        {
            enumdirection = EnumDirection.NORTH;
        }

        return this.getBlockData().set(FACING, enumdirection);
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING});
    }
}
