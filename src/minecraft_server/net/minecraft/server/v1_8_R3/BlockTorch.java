package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;

public class BlockTorch extends Block
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", new Predicate<EnumDirection>()
    {
        public boolean a(EnumDirection p_a_1_)
        {
            return p_a_1_ != EnumDirection.DOWN;
        }
    });

    protected BlockTorch()
    {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.UP));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    private boolean e(World p_e_1_, BlockPosition p_e_2_)
    {
        if (World.a((IBlockAccess)p_e_1_, (BlockPosition)p_e_2_))
        {
            return true;
        }
        else
        {
            Block block = p_e_1_.getType(p_e_2_).getBlock();
            return block instanceof BlockFence || block == Blocks.GLASS || block == Blocks.COBBLESTONE_WALL || block == Blocks.STAINED_GLASS;
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        for (EnumDirection enumdirection : FACING.c())
        {
            if (this.a(p_canPlace_1_, p_canPlace_2_, enumdirection))
            {
                return true;
            }
        }

        return false;
    }

    private boolean a(World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_)
    {
        BlockPosition blockposition = p_a_2_.shift(p_a_3_.opposite());
        boolean flag = p_a_3_.k().c();
        return flag && p_a_1_.d(blockposition, true) || p_a_3_.equals(EnumDirection.UP) && this.e(p_a_1_, blockposition);
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        if (this.a(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_))
        {
            return this.getBlockData().set(FACING, p_getPlacedState_3_);
        }
        else
        {
            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                if (p_getPlacedState_1_.d(p_getPlacedState_2_.shift(enumdirection.opposite()), true))
                {
                    return this.getBlockData().set(FACING, enumdirection);
                }
            }

            return this.getBlockData();
        }
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        this.f(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
    }

    protected boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!this.f(p_e_1_, p_e_2_, p_e_3_))
        {
            return true;
        }
        else
        {
            EnumDirection enumdirection = (EnumDirection)p_e_3_.get(FACING);
            EnumDirection.EnumAxis enumdirection$enumaxis = enumdirection.k();
            EnumDirection enumdirection1 = enumdirection.opposite();
            boolean flag = false;

            if (enumdirection$enumaxis.c() && !p_e_1_.d(p_e_2_.shift(enumdirection1), true))
            {
                flag = true;
            }
            else if (enumdirection$enumaxis.b() && !this.e(p_e_1_, p_e_2_.shift(enumdirection1)))
            {
                flag = true;
            }

            if (flag)
            {
                this.b(p_e_1_, p_e_2_, p_e_3_, 0);
                p_e_1_.setAir(p_e_2_);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    protected boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        if (p_f_3_.getBlock() == this && this.a(p_f_1_, p_f_2_, (EnumDirection)p_f_3_.get(FACING)))
        {
            return true;
        }
        else
        {
            if (p_f_1_.getType(p_f_2_).getBlock() == this)
            {
                this.b(p_f_1_, p_f_2_, p_f_3_, 0);
                p_f_1_.setAir(p_f_2_);
            }

            return false;
        }
    }

    public MovingObjectPosition a(World p_a_1_, BlockPosition p_a_2_, Vec3D p_a_3_, Vec3D p_a_4_)
    {
        EnumDirection enumdirection = (EnumDirection)p_a_1_.getType(p_a_2_).get(FACING);
        float f = 0.15F;

        if (enumdirection == EnumDirection.EAST)
        {
            this.a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        }
        else if (enumdirection == EnumDirection.WEST)
        {
            this.a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        }
        else if (enumdirection == EnumDirection.SOUTH)
        {
            this.a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        }
        else if (enumdirection == EnumDirection.NORTH)
        {
            this.a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
        else
        {
            f = 0.1F;
            this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        }

        return super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        IBlockData iblockdata = this.getBlockData();

        switch (p_fromLegacyData_1_)
        {
            case 1:
                iblockdata = iblockdata.set(FACING, EnumDirection.EAST);
                break;

            case 2:
                iblockdata = iblockdata.set(FACING, EnumDirection.WEST);
                break;

            case 3:
                iblockdata = iblockdata.set(FACING, EnumDirection.SOUTH);
                break;

            case 4:
                iblockdata = iblockdata.set(FACING, EnumDirection.NORTH);
                break;

            case 5:
            default:
                iblockdata = iblockdata.set(FACING, EnumDirection.UP);
        }

        return iblockdata;
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;

        switch ((EnumDirection)p_toLegacyData_1_.get(FACING))
        {
            case EAST:
                i = i | 1;
                break;

            case WEST:
                i = i | 2;
                break;

            case SOUTH:
                i = i | 3;
                break;

            case NORTH:
                i = i | 4;
                break;

            case DOWN:
            case UP:
            default:
                i = i | 5;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING});
    }
}
