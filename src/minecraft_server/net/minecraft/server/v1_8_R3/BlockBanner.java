package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockBanner extends BlockContainer
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
    public static final BlockStateInteger ROTATION = BlockStateInteger.of("rotation", 0, 15);

    protected BlockBanner()
    {
        super(Material.WOOD);
        float f = 0.25F;
        float f1 = 1.0F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public String getName()
    {
        return LocaleI18n.get("item.banner.white.name");
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean d()
    {
        return false;
    }

    public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_)
    {
        return true;
    }

    public boolean c()
    {
        return false;
    }

    public boolean g()
    {
        return true;
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityBanner();
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.BANNER;
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
        TileEntity tileentity = p_dropNaturally_1_.getTileEntity(p_dropNaturally_2_);

        if (tileentity instanceof TileEntityBanner)
        {
            ItemStack itemstack = new ItemStack(Items.BANNER, 1, ((TileEntityBanner)tileentity).b());
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            tileentity.b(nbttagcompound);
            nbttagcompound.remove("x");
            nbttagcompound.remove("y");
            nbttagcompound.remove("z");
            nbttagcompound.remove("id");
            itemstack.a((String)"BlockEntityTag", (NBTBase)nbttagcompound);
            a(p_dropNaturally_1_, p_dropNaturally_2_, itemstack);
        }
        else
        {
            super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, p_dropNaturally_5_);
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return !this.e(p_canPlace_1_, p_canPlace_2_) && super.canPlace(p_canPlace_1_, p_canPlace_2_);
    }

    public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_)
    {
        if (p_a_5_ instanceof TileEntityBanner)
        {
            TileEntityBanner tileentitybanner = (TileEntityBanner)p_a_5_;
            ItemStack itemstack = new ItemStack(Items.BANNER, 1, ((TileEntityBanner)p_a_5_).b());
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            TileEntityBanner.a(nbttagcompound, tileentitybanner.b(), tileentitybanner.d());
            itemstack.a((String)"BlockEntityTag", (NBTBase)nbttagcompound);
            a(p_a_1_, p_a_3_, itemstack);
        }
        else
        {
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, (TileEntity)null);
        }
    }

    public static class BlockStandingBanner extends BlockBanner
    {
        public BlockStandingBanner()
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

    public static class BlockWallBanner extends BlockBanner
    {
        public BlockWallBanner()
        {
            this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
        }

        public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
        {
            EnumDirection enumdirection = (EnumDirection)p_updateShape_1_.getType(p_updateShape_2_).get(FACING);
            float f = 0.0F;
            float f1 = 0.78125F;
            float f2 = 0.0F;
            float f3 = 1.0F;
            float f4 = 0.125F;
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            switch (enumdirection)
            {
                case NORTH:
                default:
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
}
