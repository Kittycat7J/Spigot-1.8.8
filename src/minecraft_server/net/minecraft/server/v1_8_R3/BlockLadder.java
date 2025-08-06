package net.minecraft.server.v1_8_R3;

public class BlockLadder extends Block
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);

    protected BlockLadder()
    {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
        this.a(CreativeModeTab.c);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        this.updateShape(p_a_1_, p_a_2_);
        return super.a(p_a_1_, p_a_2_, p_a_3_);
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        IBlockData iblockdata = p_updateShape_1_.getType(p_updateShape_2_);

        if (iblockdata.getBlock() == this)
        {
            float f = 0.125F;

            switch ((EnumDirection)iblockdata.get(FACING))
            {
                case NORTH:
                    this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                    break;

                case SOUTH:
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                    break;

                case WEST:
                    this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case EAST:
                default:
                    this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return p_canPlace_1_.getType(p_canPlace_2_.west()).getBlock().isOccluding() ? true : (p_canPlace_1_.getType(p_canPlace_2_.east()).getBlock().isOccluding() ? true : (p_canPlace_1_.getType(p_canPlace_2_.north()).getBlock().isOccluding() ? true : p_canPlace_1_.getType(p_canPlace_2_.south()).getBlock().isOccluding()));
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        if (p_getPlacedState_3_.k().c() && this.a(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_))
        {
            return this.getBlockData().set(FACING, p_getPlacedState_3_);
        }
        else
        {
            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                if (this.a(p_getPlacedState_1_, p_getPlacedState_2_, enumdirection))
                {
                    return this.getBlockData().set(FACING, enumdirection);
                }
            }

            return this.getBlockData();
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        EnumDirection enumdirection = (EnumDirection)p_doPhysics_3_.get(FACING);

        if (!this.a(p_doPhysics_1_, p_doPhysics_2_, enumdirection))
        {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }

        super.doPhysics(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
    }

    protected boolean a(World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_)
    {
        return p_a_1_.getType(p_a_2_.shift(p_a_3_.opposite())).getBlock().isOccluding();
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
