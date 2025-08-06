package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockVine extends Block
{
    public static final BlockStateBoolean UP = BlockStateBoolean.of("up");
    public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
    public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
    public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
    public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
    public static final BlockStateBoolean[] Q = new BlockStateBoolean[] {UP, NORTH, SOUTH, WEST, EAST};

    public BlockVine()
    {
        super(Material.REPLACEABLE_PLANT);
        this.j(this.blockStateList.getBlockData().set(UP, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        return p_updateState_1_.set(UP, Boolean.valueOf(p_updateState_2_.getType(p_updateState_3_.up()).getBlock().u()));
    }

    public void j()
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_)
    {
        return true;
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        float f = 1.0F;
        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        boolean flag = false;

        if (((Boolean)p_updateShape_1_.getType(p_updateShape_2_).get(WEST)).booleanValue())
        {
            f3 = Math.max(f3, 0.0625F);
            f = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if (((Boolean)p_updateShape_1_.getType(p_updateShape_2_).get(EAST)).booleanValue())
        {
            f = Math.min(f, 0.9375F);
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if (((Boolean)p_updateShape_1_.getType(p_updateShape_2_).get(NORTH)).booleanValue())
        {
            f5 = Math.max(f5, 0.0625F);
            f2 = 0.0F;
            f = 0.0F;
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            flag = true;
        }

        if (((Boolean)p_updateShape_1_.getType(p_updateShape_2_).get(SOUTH)).booleanValue())
        {
            f2 = Math.min(f2, 0.9375F);
            f5 = 1.0F;
            f = 0.0F;
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            flag = true;
        }

        if (!flag && this.c(p_updateShape_1_.getType(p_updateShape_2_.up()).getBlock()))
        {
            f1 = Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f = 0.0F;
            f3 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
        }

        this.a(f, f1, f2, f3, f4, f5);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_)
    {
        switch (BlockVine.SyntheticClass_1.a[p_canPlace_3_.ordinal()])
        {
            case 1:
                return this.c(p_canPlace_1_.getType(p_canPlace_2_.up()).getBlock());

            case 2:
            case 3:
            case 4:
            case 5:
                return this.c(p_canPlace_1_.getType(p_canPlace_2_.shift(p_canPlace_3_.opposite())).getBlock());

            default:
                return false;
        }
    }

    private boolean c(Block p_c_1_)
    {
        return p_c_1_.d() && p_c_1_.material.isSolid();
    }

    private boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        IBlockData iblockdata = p_e_3_;

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            BlockStateBoolean blockstateboolean = getDirection(enumdirection);

            if (((Boolean)p_e_3_.get(blockstateboolean)).booleanValue() && !this.c(p_e_1_.getType(p_e_2_.shift(enumdirection)).getBlock()))
            {
                IBlockData iblockdata1 = p_e_1_.getType(p_e_2_.up());

                if (iblockdata1.getBlock() != this || !((Boolean)iblockdata1.get(blockstateboolean)).booleanValue())
                {
                    p_e_3_ = p_e_3_.set(blockstateboolean, Boolean.valueOf(false));
                }
            }
        }

        if (d(p_e_3_) == 0)
        {
            return false;
        }
        else
        {
            if (iblockdata != p_e_3_)
            {
                p_e_1_.setTypeAndData(p_e_2_, p_e_3_, 2);
            }

            return true;
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!p_doPhysics_1_.isClientSide && !this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_))
        {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (!p_b_1_.isClientSide && p_b_1_.random.nextInt(4) == 0)
        {
            byte b0 = 4;
            int i = 5;
            boolean flag = false;
            label49:

            for (int j = -b0; j <= b0; ++j)
            {
                for (int k = -b0; k <= b0; ++k)
                {
                    for (int l = -1; l <= 1; ++l)
                    {
                        if (p_b_1_.getType(p_b_2_.a(j, l, k)).getBlock() == this)
                        {
                            --i;

                            if (i <= 0)
                            {
                                flag = true;
                                break label49;
                            }
                        }
                    }
                }
            }

            EnumDirection enumdirection1 = EnumDirection.a(p_b_4_);
            BlockPosition blockposition1 = p_b_2_.up();

            if (enumdirection1 == EnumDirection.UP && p_b_2_.getY() < 255 && p_b_1_.isEmpty(blockposition1))
            {
                if (!flag)
                {
                    IBlockData iblockdata2 = p_b_3_;

                    for (EnumDirection enumdirection3 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                    {
                        if (p_b_4_.nextBoolean() || !this.c(p_b_1_.getType(blockposition1.shift(enumdirection3)).getBlock()))
                        {
                            iblockdata2 = iblockdata2.set(getDirection(enumdirection3), Boolean.valueOf(false));
                        }
                    }

                    if (((Boolean)iblockdata2.get(NORTH)).booleanValue() || ((Boolean)iblockdata2.get(EAST)).booleanValue() || ((Boolean)iblockdata2.get(SOUTH)).booleanValue() || ((Boolean)iblockdata2.get(WEST)).booleanValue())
                    {
                        org.bukkit.block.Block block5 = p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
                        org.bukkit.block.Block block6 = p_b_1_.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
                        CraftEventFactory.handleBlockSpreadEvent(block6, block5, this, this.toLegacyData(iblockdata2));
                    }
                }
            }
            else if (enumdirection1.k().c() && !((Boolean)p_b_3_.get(getDirection(enumdirection1))).booleanValue())
            {
                if (!flag)
                {
                    BlockPosition blockposition2 = p_b_2_.shift(enumdirection1);
                    Block block4 = p_b_1_.getType(blockposition2).getBlock();

                    if (block4.material == Material.AIR)
                    {
                        EnumDirection enumdirection2 = enumdirection1.e();
                        EnumDirection enumdirection4 = enumdirection1.f();
                        boolean flag1 = ((Boolean)p_b_3_.get(getDirection(enumdirection2))).booleanValue();
                        boolean flag2 = ((Boolean)p_b_3_.get(getDirection(enumdirection4))).booleanValue();
                        BlockPosition blockposition3 = blockposition2.shift(enumdirection2);
                        BlockPosition blockposition4 = blockposition2.shift(enumdirection4);
                        org.bukkit.block.Block block7 = p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
                        org.bukkit.block.Block block3 = p_b_1_.getWorld().getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ());

                        if (flag1 && this.c(p_b_1_.getType(blockposition3).getBlock()))
                        {
                            CraftEventFactory.handleBlockSpreadEvent(block3, block7, this, this.toLegacyData(this.getBlockData().set(getDirection(enumdirection2), Boolean.valueOf(true))));
                        }
                        else if (flag2 && this.c(p_b_1_.getType(blockposition4).getBlock()))
                        {
                            CraftEventFactory.handleBlockSpreadEvent(block3, block7, this, this.toLegacyData(this.getBlockData().set(getDirection(enumdirection4), Boolean.valueOf(true))));
                        }
                        else if (flag1 && p_b_1_.isEmpty(blockposition3) && this.c(p_b_1_.getType(p_b_2_.shift(enumdirection2)).getBlock()))
                        {
                            block3 = p_b_1_.getWorld().getBlockAt(blockposition3.getX(), blockposition3.getY(), blockposition3.getZ());
                            CraftEventFactory.handleBlockSpreadEvent(block3, block7, this, this.toLegacyData(this.getBlockData().set(getDirection(enumdirection1.opposite()), Boolean.valueOf(true))));
                        }
                        else if (flag2 && p_b_1_.isEmpty(blockposition4) && this.c(p_b_1_.getType(p_b_2_.shift(enumdirection4)).getBlock()))
                        {
                            block3 = p_b_1_.getWorld().getBlockAt(blockposition4.getX(), blockposition4.getY(), blockposition4.getZ());
                            CraftEventFactory.handleBlockSpreadEvent(block3, block7, this, this.toLegacyData(this.getBlockData().set(getDirection(enumdirection1.opposite()), Boolean.valueOf(true))));
                        }
                        else if (this.c(p_b_1_.getType(blockposition2.up()).getBlock()))
                        {
                            CraftEventFactory.handleBlockSpreadEvent(block3, block7, this, this.toLegacyData(this.getBlockData()));
                        }
                    }
                    else if (block4.material.k() && block4.d())
                    {
                        p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(getDirection(enumdirection1), Boolean.valueOf(true)), 2);
                    }
                }
            }
            else if (p_b_2_.getY() > 1)
            {
                BlockPosition blockposition = p_b_2_.down();
                IBlockData iblockdata = p_b_1_.getType(blockposition);
                Block block = iblockdata.getBlock();

                if (block.material == Material.AIR)
                {
                    IBlockData iblockdata1 = p_b_3_;

                    for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                    {
                        if (p_b_4_.nextBoolean())
                        {
                            iblockdata1 = iblockdata1.set(getDirection(enumdirection), Boolean.valueOf(false));
                        }
                    }

                    if (((Boolean)iblockdata1.get(NORTH)).booleanValue() || ((Boolean)iblockdata1.get(EAST)).booleanValue() || ((Boolean)iblockdata1.get(SOUTH)).booleanValue() || ((Boolean)iblockdata1.get(WEST)).booleanValue())
                    {
                        org.bukkit.block.Block block1 = p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
                        org.bukkit.block.Block block2 = p_b_1_.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                        CraftEventFactory.handleBlockSpreadEvent(block2, block1, this, this.toLegacyData(iblockdata1));
                    }
                }
                else if (block == this)
                {
                    IBlockData iblockdata3 = iblockdata;

                    for (EnumDirection enumdirection5 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                    {
                        BlockStateBoolean blockstateboolean = getDirection(enumdirection5);

                        if (p_b_4_.nextBoolean() && ((Boolean)p_b_3_.get(blockstateboolean)).booleanValue())
                        {
                            iblockdata3 = iblockdata3.set(blockstateboolean, Boolean.valueOf(true));
                        }
                    }

                    if (((Boolean)iblockdata3.get(NORTH)).booleanValue() || ((Boolean)iblockdata3.get(EAST)).booleanValue() || ((Boolean)iblockdata3.get(SOUTH)).booleanValue() || ((Boolean)iblockdata3.get(WEST)).booleanValue())
                    {
                        p_b_1_.setTypeAndData(blockposition, iblockdata3, 2);
                    }
                }
            }
        }
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        IBlockData iblockdata = this.getBlockData().set(UP, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false));
        return p_getPlacedState_3_.k().c() ? iblockdata.set(getDirection(p_getPlacedState_3_.opposite()), Boolean.valueOf(true)) : iblockdata;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return null;
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_)
    {
        if (!p_a_1_.isClientSide && p_a_2_.bZ() != null && p_a_2_.bZ().getItem() == Items.SHEARS)
        {
            p_a_2_.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
            a(p_a_1_, p_a_3_, new ItemStack(Blocks.VINE, 1, 0));
        }
        else
        {
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
        }
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(SOUTH, Boolean.valueOf((p_fromLegacyData_1_ & 1) > 0)).set(WEST, Boolean.valueOf((p_fromLegacyData_1_ & 2) > 0)).set(NORTH, Boolean.valueOf((p_fromLegacyData_1_ & 4) > 0)).set(EAST, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;

        if (((Boolean)p_toLegacyData_1_.get(SOUTH)).booleanValue())
        {
            i |= 1;
        }

        if (((Boolean)p_toLegacyData_1_.get(WEST)).booleanValue())
        {
            i |= 2;
        }

        if (((Boolean)p_toLegacyData_1_.get(NORTH)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)p_toLegacyData_1_.get(EAST)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {UP, NORTH, EAST, SOUTH, WEST});
    }

    public static BlockStateBoolean getDirection(EnumDirection p_getDirection_0_)
    {
        switch (BlockVine.SyntheticClass_1.a[p_getDirection_0_.ordinal()])
        {
            case 1:
                return UP;

            case 2:
                return NORTH;

            case 3:
                return SOUTH;

            case 4:
                return EAST;

            case 5:
                return WEST;

            default:
                throw new IllegalArgumentException(p_getDirection_0_ + " is an invalid choice");
        }
    }

    public static int d(IBlockData p_d_0_)
    {
        int i = 0;

        for (BlockStateBoolean blockstateboolean : Q)
        {
            if (((Boolean)p_d_0_.get(blockstateboolean)).booleanValue())
            {
                ++i;
            }
        }

        return i;
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[EnumDirection.values().length];

        static
        {
            try
            {
                a[EnumDirection.UP.ordinal()] = 1;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                a[EnumDirection.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                a[EnumDirection.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                a[EnumDirection.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[EnumDirection.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
