package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer
{
    public static final BlockStateDirection FACING = BlockPistonExtension.FACING;
    public static final BlockStateEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockPistonExtension.TYPE;

    public BlockPistonMoving()
    {
        super(Material.PISTON);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(TYPE, BlockPistonExtension.EnumPistonType.DEFAULT));
        this.c(-1.0F);
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return null;
    }

    public static TileEntity a(IBlockData p_a_0_, EnumDirection p_a_1_, boolean p_a_2_, boolean p_a_3_)
    {
        return new TileEntityPiston(p_a_0_, p_a_1_, p_a_2_, p_a_3_);
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        TileEntity tileentity = p_remove_1_.getTileEntity(p_remove_2_);

        if (tileentity instanceof TileEntityPiston)
        {
            ((TileEntityPiston)tileentity).h();
        }
        else
        {
            super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return false;
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_)
    {
        return false;
    }

    public void postBreak(World p_postBreak_1_, BlockPosition p_postBreak_2_, IBlockData p_postBreak_3_)
    {
        BlockPosition blockposition = p_postBreak_2_.shift(((EnumDirection)p_postBreak_3_.get(FACING)).opposite());
        IBlockData iblockdata = p_postBreak_1_.getType(blockposition);

        if (iblockdata.getBlock() instanceof BlockPiston && ((Boolean)iblockdata.get(BlockPiston.EXTENDED)).booleanValue())
        {
            p_postBreak_1_.setAir(blockposition);
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

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (!p_interact_1_.isClientSide && p_interact_1_.getTileEntity(p_interact_2_) == null)
        {
            p_interact_1_.setAir(p_interact_2_);
            return true;
        }
        else
        {
            return false;
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return null;
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
        if (!p_dropNaturally_1_.isClientSide)
        {
            TileEntityPiston tileentitypiston = this.e(p_dropNaturally_1_, p_dropNaturally_2_);

            if (tileentitypiston != null)
            {
                IBlockData iblockdata = tileentitypiston.b();
                iblockdata.getBlock().b(p_dropNaturally_1_, p_dropNaturally_2_, iblockdata, 0);
            }
        }
    }

    public MovingObjectPosition a(World p_a_1_, BlockPosition p_a_2_, Vec3D p_a_3_, Vec3D p_a_4_)
    {
        return null;
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!p_doPhysics_1_.isClientSide)
        {
            p_doPhysics_1_.getTileEntity(p_doPhysics_2_);
        }
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        TileEntityPiston tileentitypiston = this.e(p_a_1_, p_a_2_);

        if (tileentitypiston == null)
        {
            return null;
        }
        else
        {
            float f = tileentitypiston.a(0.0F);

            if (tileentitypiston.d())
            {
                f = 1.0F - f;
            }

            return this.a(p_a_1_, p_a_2_, tileentitypiston.b(), f, tileentitypiston.e());
        }
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        TileEntityPiston tileentitypiston = this.e(p_updateShape_1_, p_updateShape_2_);

        if (tileentitypiston != null)
        {
            IBlockData iblockdata = tileentitypiston.b();
            Block block = iblockdata.getBlock();

            if (block == this || block.getMaterial() == Material.AIR)
            {
                return;
            }

            float f = tileentitypiston.a(0.0F);

            if (tileentitypiston.d())
            {
                f = 1.0F - f;
            }

            block.updateShape(p_updateShape_1_, p_updateShape_2_);

            if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON)
            {
                f = 0.0F;
            }

            EnumDirection enumdirection = tileentitypiston.e();
            this.minX = block.B() - (double)((float)enumdirection.getAdjacentX() * f);
            this.minY = block.D() - (double)((float)enumdirection.getAdjacentY() * f);
            this.minZ = block.F() - (double)((float)enumdirection.getAdjacentZ() * f);
            this.maxX = block.C() - (double)((float)enumdirection.getAdjacentX() * f);
            this.maxY = block.E() - (double)((float)enumdirection.getAdjacentY() * f);
            this.maxZ = block.G() - (double)((float)enumdirection.getAdjacentZ() * f);
        }
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, float p_a_4_, EnumDirection p_a_5_)
    {
        if (p_a_3_.getBlock() != this && p_a_3_.getBlock().getMaterial() != Material.AIR)
        {
            AxisAlignedBB axisalignedbb = p_a_3_.getBlock().a(p_a_1_, p_a_2_, p_a_3_);

            if (axisalignedbb == null)
            {
                return null;
            }
            else
            {
                double d0 = axisalignedbb.a;
                double d1 = axisalignedbb.b;
                double d2 = axisalignedbb.c;
                double d3 = axisalignedbb.d;
                double d4 = axisalignedbb.e;
                double d5 = axisalignedbb.f;

                if (p_a_5_.getAdjacentX() < 0)
                {
                    d0 -= (double)((float)p_a_5_.getAdjacentX() * p_a_4_);
                }
                else
                {
                    d3 -= (double)((float)p_a_5_.getAdjacentX() * p_a_4_);
                }

                if (p_a_5_.getAdjacentY() < 0)
                {
                    d1 -= (double)((float)p_a_5_.getAdjacentY() * p_a_4_);
                }
                else
                {
                    d4 -= (double)((float)p_a_5_.getAdjacentY() * p_a_4_);
                }

                if (p_a_5_.getAdjacentZ() < 0)
                {
                    d2 -= (double)((float)p_a_5_.getAdjacentZ() * p_a_4_);
                }
                else
                {
                    d5 -= (double)((float)p_a_5_.getAdjacentZ() * p_a_4_);
                }

                return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
            }
        }
        else
        {
            return null;
        }
    }

    private TileEntityPiston e(IBlockAccess p_e_1_, BlockPosition p_e_2_)
    {
        TileEntity tileentity = p_e_1_.getTileEntity(p_e_2_);
        return tileentity instanceof TileEntityPiston ? (TileEntityPiston)tileentity : null;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, BlockPistonExtension.b(p_fromLegacyData_1_)).set(TYPE, (p_fromLegacyData_1_ & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;
        i = i | ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();

        if (p_toLegacyData_1_.get(TYPE) == BlockPistonExtension.EnumPistonType.STICKY)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, TYPE});
    }
}
