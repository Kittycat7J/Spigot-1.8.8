package net.minecraft.server.v1_8_R3;

public class BlockChest extends BlockContainer
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
    public final int b;

    protected BlockChest(int p_i597_1_)
    {
        super(Material.WOOD);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
        this.b = p_i597_1_;
        this.a(CreativeModeTab.c);
        this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public int b()
    {
        return 2;
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        if (p_updateShape_1_.getType(p_updateShape_2_.north()).getBlock() == this)
        {
            this.a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (p_updateShape_1_.getType(p_updateShape_2_.south()).getBlock() == this)
        {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
        }
        else if (p_updateShape_1_.getType(p_updateShape_2_.west()).getBlock() == this)
        {
            this.a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (p_updateShape_1_.getType(p_updateShape_2_.east()).getBlock() == this)
        {
            this.a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
        }
        else
        {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            BlockPosition blockposition = p_onPlace_2_.shift(enumdirection);
            IBlockData iblockdata = p_onPlace_1_.getType(blockposition);

            if (iblockdata.getBlock() == this)
            {
                this.e(p_onPlace_1_, blockposition, iblockdata);
            }
        }
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection());
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        EnumDirection enumdirection = EnumDirection.fromType2(MathHelper.floor((double)(p_postPlace_4_.yaw * 4.0F / 360.0F) + 0.5D) & 3).opposite();
        p_postPlace_3_ = p_postPlace_3_.set(FACING, enumdirection);
        BlockPosition blockposition = p_postPlace_2_.north();
        BlockPosition blockposition1 = p_postPlace_2_.south();
        BlockPosition blockposition2 = p_postPlace_2_.west();
        BlockPosition blockposition3 = p_postPlace_2_.east();
        boolean flag = this == p_postPlace_1_.getType(blockposition).getBlock();
        boolean flag1 = this == p_postPlace_1_.getType(blockposition1).getBlock();
        boolean flag2 = this == p_postPlace_1_.getType(blockposition2).getBlock();
        boolean flag3 = this == p_postPlace_1_.getType(blockposition3).getBlock();

        if (!flag && !flag1 && !flag2 && !flag3)
        {
            p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_, 3);
        }
        else if (enumdirection.k() != EnumDirection.EnumAxis.X || !flag && !flag1)
        {
            if (enumdirection.k() == EnumDirection.EnumAxis.Z && (flag2 || flag3))
            {
                if (flag2)
                {
                    p_postPlace_1_.setTypeAndData(blockposition2, p_postPlace_3_, 3);
                }
                else
                {
                    p_postPlace_1_.setTypeAndData(blockposition3, p_postPlace_3_, 3);
                }

                p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_, 3);
            }
        }
        else
        {
            if (flag)
            {
                p_postPlace_1_.setTypeAndData(blockposition, p_postPlace_3_, 3);
            }
            else
            {
                p_postPlace_1_.setTypeAndData(blockposition1, p_postPlace_3_, 3);
            }

            p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_, 3);
        }

        if (p_postPlace_5_.hasName())
        {
            TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

            if (tileentity instanceof TileEntityChest)
            {
                ((TileEntityChest)tileentity).a(p_postPlace_5_.getName());
            }
        }
    }

    public IBlockData e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (p_e_1_.isClientSide)
        {
            return p_e_3_;
        }
        else
        {
            IBlockData iblockdata = p_e_1_.getType(p_e_2_.north());
            IBlockData iblockdata1 = p_e_1_.getType(p_e_2_.south());
            IBlockData iblockdata2 = p_e_1_.getType(p_e_2_.west());
            IBlockData iblockdata3 = p_e_1_.getType(p_e_2_.east());
            EnumDirection enumdirection = (EnumDirection)p_e_3_.get(FACING);
            Block block = iblockdata.getBlock();
            Block block1 = iblockdata1.getBlock();
            Block block2 = iblockdata2.getBlock();
            Block block3 = iblockdata3.getBlock();

            if (block != this && block1 != this)
            {
                boolean flag = block.o();
                boolean flag1 = block1.o();

                if (block2 == this || block3 == this)
                {
                    BlockPosition blockposition1 = block2 == this ? p_e_2_.west() : p_e_2_.east();
                    IBlockData iblockdata6 = p_e_1_.getType(blockposition1.north());
                    IBlockData iblockdata7 = p_e_1_.getType(blockposition1.south());
                    enumdirection = EnumDirection.SOUTH;
                    EnumDirection enumdirection2;

                    if (block2 == this)
                    {
                        enumdirection2 = (EnumDirection)iblockdata2.get(FACING);
                    }
                    else
                    {
                        enumdirection2 = (EnumDirection)iblockdata3.get(FACING);
                    }

                    if (enumdirection2 == EnumDirection.NORTH)
                    {
                        enumdirection = EnumDirection.NORTH;
                    }

                    Block block6 = iblockdata6.getBlock();
                    Block block7 = iblockdata7.getBlock();

                    if ((flag || block6.o()) && !flag1 && !block7.o())
                    {
                        enumdirection = EnumDirection.SOUTH;
                    }

                    if ((flag1 || block7.o()) && !flag && !block6.o())
                    {
                        enumdirection = EnumDirection.NORTH;
                    }
                }
            }
            else
            {
                BlockPosition blockposition = block == this ? p_e_2_.north() : p_e_2_.south();
                IBlockData iblockdata4 = p_e_1_.getType(blockposition.west());
                IBlockData iblockdata5 = p_e_1_.getType(blockposition.east());
                enumdirection = EnumDirection.EAST;
                EnumDirection enumdirection1;

                if (block == this)
                {
                    enumdirection1 = (EnumDirection)iblockdata.get(FACING);
                }
                else
                {
                    enumdirection1 = (EnumDirection)iblockdata1.get(FACING);
                }

                if (enumdirection1 == EnumDirection.WEST)
                {
                    enumdirection = EnumDirection.WEST;
                }

                Block block4 = iblockdata4.getBlock();
                Block block5 = iblockdata5.getBlock();

                if ((block2.o() || block4.o()) && !block3.o() && !block5.o())
                {
                    enumdirection = EnumDirection.EAST;
                }

                if ((block3.o() || block5.o()) && !block2.o() && !block4.o())
                {
                    enumdirection = EnumDirection.WEST;
                }
            }

            p_e_3_ = p_e_3_.set(FACING, enumdirection);
            p_e_1_.setTypeAndData(p_e_2_, p_e_3_, 3);
            return p_e_3_;
        }
    }

    public IBlockData f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        EnumDirection enumdirection = null;

        for (EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            IBlockData iblockdata = p_f_1_.getType(p_f_2_.shift(enumdirection1));

            if (iblockdata.getBlock() == this)
            {
                return p_f_3_;
            }

            if (iblockdata.getBlock().o())
            {
                if (enumdirection != null)
                {
                    enumdirection = null;
                    break;
                }

                enumdirection = enumdirection1;
            }
        }

        if (enumdirection != null)
        {
            return p_f_3_.set(FACING, enumdirection.opposite());
        }
        else
        {
            EnumDirection enumdirection2 = (EnumDirection)p_f_3_.get(FACING);

            if (p_f_1_.getType(p_f_2_.shift(enumdirection2)).getBlock().o())
            {
                enumdirection2 = enumdirection2.opposite();
            }

            if (p_f_1_.getType(p_f_2_.shift(enumdirection2)).getBlock().o())
            {
                enumdirection2 = enumdirection2.e();
            }

            if (p_f_1_.getType(p_f_2_.shift(enumdirection2)).getBlock().o())
            {
                enumdirection2 = enumdirection2.opposite();
            }

            return p_f_3_.set(FACING, enumdirection2);
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        int i = 0;
        BlockPosition blockposition = p_canPlace_2_.west();
        BlockPosition blockposition1 = p_canPlace_2_.east();
        BlockPosition blockposition2 = p_canPlace_2_.north();
        BlockPosition blockposition3 = p_canPlace_2_.south();

        if (p_canPlace_1_.getType(blockposition).getBlock() == this)
        {
            if (this.m(p_canPlace_1_, blockposition))
            {
                return false;
            }

            ++i;
        }

        if (p_canPlace_1_.getType(blockposition1).getBlock() == this)
        {
            if (this.m(p_canPlace_1_, blockposition1))
            {
                return false;
            }

            ++i;
        }

        if (p_canPlace_1_.getType(blockposition2).getBlock() == this)
        {
            if (this.m(p_canPlace_1_, blockposition2))
            {
                return false;
            }

            ++i;
        }

        if (p_canPlace_1_.getType(blockposition3).getBlock() == this)
        {
            if (this.m(p_canPlace_1_, blockposition3))
            {
                return false;
            }

            ++i;
        }

        return i <= 1;
    }

    private boolean m(World p_m_1_, BlockPosition p_m_2_)
    {
        if (p_m_1_.getType(p_m_2_).getBlock() != this)
        {
            return false;
        }
        else
        {
            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                if (p_m_1_.getType(p_m_2_.shift(enumdirection)).getBlock() == this)
                {
                    return true;
                }
            }

            return false;
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        super.doPhysics(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
        TileEntity tileentity = p_doPhysics_1_.getTileEntity(p_doPhysics_2_);

        if (tileentity instanceof TileEntityChest)
        {
            tileentity.E();
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        TileEntity tileentity = p_remove_1_.getTileEntity(p_remove_2_);

        if (tileentity instanceof IInventory)
        {
            InventoryUtils.dropInventory(p_remove_1_, p_remove_2_, (IInventory)tileentity);
            p_remove_1_.updateAdjacentComparators(p_remove_2_, this);
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (p_interact_1_.isClientSide)
        {
            return true;
        }
        else
        {
            ITileInventory itileinventory = this.f(p_interact_1_, p_interact_2_);

            if (itileinventory != null)
            {
                p_interact_4_.openContainer(itileinventory);

                if (this.b == 0)
                {
                    p_interact_4_.b(StatisticList.aa);
                }
                else if (this.b == 1)
                {
                    p_interact_4_.b(StatisticList.U);
                }
            }

            return true;
        }
    }

    public ITileInventory f(World p_f_1_, BlockPosition p_f_2_)
    {
        TileEntity tileentity = p_f_1_.getTileEntity(p_f_2_);

        if (!(tileentity instanceof TileEntityChest))
        {
            return null;
        }
        else
        {
            Object object = (TileEntityChest)tileentity;

            if (this.n(p_f_1_, p_f_2_))
            {
                return null;
            }
            else
            {
                for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                {
                    BlockPosition blockposition = p_f_2_.shift(enumdirection);
                    Block block = p_f_1_.getType(blockposition).getBlock();

                    if (block == this)
                    {
                        if (this.n(p_f_1_, blockposition))
                        {
                            return null;
                        }

                        TileEntity tileentity1 = p_f_1_.getTileEntity(blockposition);

                        if (tileentity1 instanceof TileEntityChest)
                        {
                            if (enumdirection != EnumDirection.WEST && enumdirection != EnumDirection.NORTH)
                            {
                                object = new InventoryLargeChest("container.chestDouble", (ITileInventory)object, (TileEntityChest)tileentity1);
                            }
                            else
                            {
                                object = new InventoryLargeChest("container.chestDouble", (TileEntityChest)tileentity1, (ITileInventory)object);
                            }
                        }
                    }
                }

                return (ITileInventory)object;
            }
        }
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityChest();
    }

    public boolean isPowerSource()
    {
        return this.b == 1;
    }

    public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_)
    {
        if (!this.isPowerSource())
        {
            return 0;
        }
        else
        {
            int i = 0;
            TileEntity tileentity = p_a_1_.getTileEntity(p_a_2_);

            if (tileentity instanceof TileEntityChest)
            {
                i = ((TileEntityChest)tileentity).l;
            }

            return MathHelper.clamp(i, 0, 15);
        }
    }

    public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_)
    {
        return p_b_4_ == EnumDirection.UP ? this.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_) : 0;
    }

    private boolean n(World p_n_1_, BlockPosition p_n_2_)
    {
        return this.o(p_n_1_, p_n_2_) || this.p(p_n_1_, p_n_2_);
    }

    private boolean o(World p_o_1_, BlockPosition p_o_2_)
    {
        return p_o_1_.getType(p_o_2_.up()).getBlock().isOccluding();
    }

    private boolean p(World p_p_1_, BlockPosition p_p_2_)
    {
        for (Entity entity : p_p_1_.a(EntityOcelot.class, new AxisAlignedBB((double)p_p_2_.getX(), (double)(p_p_2_.getY() + 1), (double)p_p_2_.getZ(), (double)(p_p_2_.getX() + 1), (double)(p_p_2_.getY() + 2), (double)(p_p_2_.getZ() + 1))))
        {
            EntityOcelot entityocelot = (EntityOcelot)entity;

            if (entityocelot.isSitting())
            {
                return true;
            }
        }

        return false;
    }

    public boolean isComplexRedstone()
    {
        return true;
    }

    public int l(World p_l_1_, BlockPosition p_l_2_)
    {
        return Container.b((IInventory)this.f(p_l_1_, p_l_2_));
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
