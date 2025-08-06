package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockPoweredRail extends BlockMinecartTrackAbstract
{
    public static final BlockStateEnum<BlockMinecartTrackAbstract.EnumTrackPosition> SHAPE = BlockStateEnum.a("shape", BlockMinecartTrackAbstract.EnumTrackPosition.class, new Predicate()
    {
        public boolean a(BlockMinecartTrackAbstract.EnumTrackPosition p_a_1_)
        {
            return p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST && p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST && p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST && p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.a((BlockMinecartTrackAbstract.EnumTrackPosition)p_apply_1_);
        }
    });
    public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");

    protected BlockPoweredRail()
    {
        super(true);
        this.j(this.blockStateList.getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH).set(POWERED, Boolean.valueOf(false)));
    }

    protected boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_, int p_a_5_)
    {
        if (p_a_5_ >= 8)
        {
            return false;
        }
        else
        {
            int i = p_a_2_.getX();
            int j = p_a_2_.getY();
            int k = p_a_2_.getZ();
            boolean flag = true;
            BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)p_a_3_.get(SHAPE);

            switch (BlockPoweredRail.SyntheticClass_1.a[blockminecarttrackabstract$enumtrackposition.ordinal()])
            {
                case 1:
                    if (p_a_4_)
                    {
                        ++k;
                    }
                    else
                    {
                        --k;
                    }

                    break;

                case 2:
                    if (p_a_4_)
                    {
                        --i;
                    }
                    else
                    {
                        ++i;
                    }

                    break;

                case 3:
                    if (p_a_4_)
                    {
                        --i;
                    }
                    else
                    {
                        ++i;
                        ++j;
                        flag = false;
                    }

                    blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
                    break;

                case 4:
                    if (p_a_4_)
                    {
                        --i;
                        ++j;
                        flag = false;
                    }
                    else
                    {
                        ++i;
                    }

                    blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
                    break;

                case 5:
                    if (p_a_4_)
                    {
                        ++k;
                    }
                    else
                    {
                        --k;
                        ++j;
                        flag = false;
                    }

                    blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
                    break;

                case 6:
                    if (p_a_4_)
                    {
                        ++k;
                        ++j;
                        flag = false;
                    }
                    else
                    {
                        --k;
                    }

                    blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
            }

            return this.a(p_a_1_, new BlockPosition(i, j, k), p_a_4_, p_a_5_, blockminecarttrackabstract$enumtrackposition) ? true : flag && this.a(p_a_1_, new BlockPosition(i, j - 1, k), p_a_4_, p_a_5_, blockminecarttrackabstract$enumtrackposition);
        }
    }

    protected boolean a(World p_a_1_, BlockPosition p_a_2_, boolean p_a_3_, int p_a_4_, BlockMinecartTrackAbstract.EnumTrackPosition p_a_5_)
    {
        IBlockData iblockdata = p_a_1_.getType(p_a_2_);

        if (iblockdata.getBlock() != this)
        {
            return false;
        }
        else
        {
            BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(SHAPE);
            return p_a_5_ != BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST || blockminecarttrackabstract$enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH && blockminecarttrackabstract$enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH && blockminecarttrackabstract$enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH ? (p_a_5_ != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH || blockminecarttrackabstract$enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST && blockminecarttrackabstract$enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST && blockminecarttrackabstract$enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST ? (((Boolean)iblockdata.get(POWERED)).booleanValue() ? (p_a_1_.isBlockIndirectlyPowered(p_a_2_) ? true : this.a(p_a_1_, p_a_2_, iblockdata, p_a_3_, p_a_4_ + 1)) : false) : false) : false;
        }
    }

    protected void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Block p_b_4_)
    {
        boolean flag = ((Boolean)p_b_3_.get(POWERED)).booleanValue();
        boolean flag1 = p_b_1_.isBlockIndirectlyPowered(p_b_2_) || this.a(p_b_1_, p_b_2_, p_b_3_, true, 0) || this.a(p_b_1_, p_b_2_, p_b_3_, false, 0);

        if (flag1 != flag)
        {
            int i = ((Boolean)p_b_3_.get(POWERED)).booleanValue() ? 15 : 0;
            int j = CraftEventFactory.callRedstoneChange(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), i, 15 - i).getNewCurrent();

            if (j == i)
            {
                return;
            }

            p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(POWERED, Boolean.valueOf(flag1)), 3);
            p_b_1_.applyPhysics(p_b_2_.down(), this);

            if (((BlockMinecartTrackAbstract.EnumTrackPosition)p_b_3_.get(SHAPE)).c())
            {
                p_b_1_.applyPhysics(p_b_2_.up(), this);
            }
        }
    }

    public IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> n()
    {
        return SHAPE;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.a(p_fromLegacyData_1_ & 7)).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        byte b0 = 0;
        int i = b0 | ((BlockMinecartTrackAbstract.EnumTrackPosition)p_toLegacyData_1_.get(SHAPE)).a();

        if (((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {SHAPE, POWERED});
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[BlockMinecartTrackAbstract.EnumTrackPosition.values().length];

        static
        {
            try
            {
                a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                a[BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 5;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 6;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
