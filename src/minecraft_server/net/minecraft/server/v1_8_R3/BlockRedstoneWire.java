package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstoneWire extends Block
{
    public static final BlockStateEnum<BlockRedstoneWire.EnumRedstoneWireConnection> NORTH = BlockStateEnum.<BlockRedstoneWire.EnumRedstoneWireConnection>of("north", BlockRedstoneWire.EnumRedstoneWireConnection.class);
    public static final BlockStateEnum<BlockRedstoneWire.EnumRedstoneWireConnection> EAST = BlockStateEnum.<BlockRedstoneWire.EnumRedstoneWireConnection>of("east", BlockRedstoneWire.EnumRedstoneWireConnection.class);
    public static final BlockStateEnum<BlockRedstoneWire.EnumRedstoneWireConnection> SOUTH = BlockStateEnum.<BlockRedstoneWire.EnumRedstoneWireConnection>of("south", BlockRedstoneWire.EnumRedstoneWireConnection.class);
    public static final BlockStateEnum<BlockRedstoneWire.EnumRedstoneWireConnection> WEST = BlockStateEnum.<BlockRedstoneWire.EnumRedstoneWireConnection>of("west", BlockRedstoneWire.EnumRedstoneWireConnection.class);
    public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
    private boolean Q = true;
    private final Set<BlockPosition> R = Sets.<BlockPosition>newHashSet();

    public BlockRedstoneWire()
    {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(NORTH, BlockRedstoneWire.EnumRedstoneWireConnection.NONE).set(EAST, BlockRedstoneWire.EnumRedstoneWireConnection.NONE).set(SOUTH, BlockRedstoneWire.EnumRedstoneWireConnection.NONE).set(WEST, BlockRedstoneWire.EnumRedstoneWireConnection.NONE).set(POWER, Integer.valueOf(0)));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        p_updateState_1_ = p_updateState_1_.set(WEST, this.c(p_updateState_2_, p_updateState_3_, EnumDirection.WEST));
        p_updateState_1_ = p_updateState_1_.set(EAST, this.c(p_updateState_2_, p_updateState_3_, EnumDirection.EAST));
        p_updateState_1_ = p_updateState_1_.set(NORTH, this.c(p_updateState_2_, p_updateState_3_, EnumDirection.NORTH));
        p_updateState_1_ = p_updateState_1_.set(SOUTH, this.c(p_updateState_2_, p_updateState_3_, EnumDirection.SOUTH));
        return p_updateState_1_;
    }

    private BlockRedstoneWire.EnumRedstoneWireConnection c(IBlockAccess p_c_1_, BlockPosition p_c_2_, EnumDirection p_c_3_)
    {
        BlockPosition blockposition = p_c_2_.shift(p_c_3_);
        Block block = p_c_1_.getType(p_c_2_.shift(p_c_3_)).getBlock();

        if (!a(p_c_1_.getType(blockposition), p_c_3_) && (block.u() || !d(p_c_1_.getType(blockposition.down()))))
        {
            Block block1 = p_c_1_.getType(p_c_2_.up()).getBlock();
            return !block1.u() && block.u() && d(p_c_1_.getType(blockposition.up())) ? BlockRedstoneWire.EnumRedstoneWireConnection.UP : BlockRedstoneWire.EnumRedstoneWireConnection.NONE;
        }
        else
        {
            return BlockRedstoneWire.EnumRedstoneWireConnection.SIDE;
        }
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

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return World.a((IBlockAccess)p_canPlace_1_, (BlockPosition)p_canPlace_2_.down()) || p_canPlace_1_.getType(p_canPlace_2_.down()).getBlock() == Blocks.GLOWSTONE;
    }

    private IBlockData e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        p_e_3_ = this.a(p_e_1_, p_e_2_, p_e_2_, p_e_3_);
        ArrayList arraylist = Lists.newArrayList(this.R);
        this.R.clear();

        for (BlockPosition blockposition : arraylist)
        {
            p_e_1_.applyPhysics(blockposition, this);
        }

        return p_e_3_;
    }

    private IBlockData a(World p_a_1_, BlockPosition p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_)
    {
        IBlockData iblockdata = p_a_4_;
        int i = ((Integer)p_a_4_.get(POWER)).intValue();
        byte b0 = 0;
        int j = this.getPower(p_a_1_, p_a_3_, b0);
        this.Q = false;
        int k = p_a_1_.A(p_a_2_);
        this.Q = true;

        if (k > 0 && k > j - 1)
        {
            j = k;
        }

        int l = 0;

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            BlockPosition blockposition = p_a_2_.shift(enumdirection);
            boolean flag = blockposition.getX() != p_a_3_.getX() || blockposition.getZ() != p_a_3_.getZ();

            if (flag)
            {
                l = this.getPower(p_a_1_, blockposition, l);
            }

            if (p_a_1_.getType(blockposition).getBlock().isOccluding() && !p_a_1_.getType(p_a_2_.up()).getBlock().isOccluding())
            {
                if (flag && p_a_2_.getY() >= p_a_3_.getY())
                {
                    l = this.getPower(p_a_1_, blockposition.up(), l);
                }
            }
            else if (!p_a_1_.getType(blockposition).getBlock().isOccluding() && flag && p_a_2_.getY() <= p_a_3_.getY())
            {
                l = this.getPower(p_a_1_, blockposition.down(), l);
            }
        }

        if (l > j)
        {
            j = l - 1;
        }
        else if (j > 0)
        {
            --j;
        }
        else
        {
            j = 0;
        }

        if (k > j - 1)
        {
            j = k;
        }

        if (i != j)
        {
            BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(p_a_1_.getWorld().getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ()), i, j);
            p_a_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
            j = blockredstoneevent.getNewCurrent();
        }

        if (i != j)
        {
            p_a_4_ = p_a_4_.set(POWER, Integer.valueOf(j));

            if (p_a_1_.getType(p_a_2_) == iblockdata)
            {
                p_a_1_.setTypeAndData(p_a_2_, p_a_4_, 2);
            }

            this.R.add(p_a_2_);

            for (EnumDirection enumdirection1 : EnumDirection.values())
            {
                this.R.add(p_a_2_.shift(enumdirection1));
            }
        }

        return p_a_4_;
    }

    private void e(World p_e_1_, BlockPosition p_e_2_)
    {
        if (p_e_1_.getType(p_e_2_).getBlock() == this)
        {
            p_e_1_.applyPhysics(p_e_2_, this);

            for (EnumDirection enumdirection : EnumDirection.values())
            {
                p_e_1_.applyPhysics(p_e_2_.shift(enumdirection), this);
            }
        }
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        if (!p_onPlace_1_.isClientSide)
        {
            this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);

            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.VERTICAL)
            {
                p_onPlace_1_.applyPhysics(p_onPlace_2_.shift(enumdirection), this);
            }

            for (EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                this.e(p_onPlace_1_, p_onPlace_2_.shift(enumdirection1));
            }

            for (EnumDirection enumdirection2 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                BlockPosition blockposition = p_onPlace_2_.shift(enumdirection2);

                if (p_onPlace_1_.getType(blockposition).getBlock().isOccluding())
                {
                    this.e(p_onPlace_1_, blockposition.up());
                }
                else
                {
                    this.e(p_onPlace_1_, blockposition.down());
                }
            }
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);

        if (!p_remove_1_.isClientSide)
        {
            for (EnumDirection enumdirection : EnumDirection.values())
            {
                p_remove_1_.applyPhysics(p_remove_2_.shift(enumdirection), this);
            }

            this.e(p_remove_1_, p_remove_2_, p_remove_3_);

            for (EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                this.e(p_remove_1_, p_remove_2_.shift(enumdirection1));
            }

            for (EnumDirection enumdirection2 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                BlockPosition blockposition = p_remove_2_.shift(enumdirection2);

                if (p_remove_1_.getType(blockposition).getBlock().isOccluding())
                {
                    this.e(p_remove_1_, blockposition.up());
                }
                else
                {
                    this.e(p_remove_1_, blockposition.down());
                }
            }
        }
    }

    public int getPower(World p_getPower_1_, BlockPosition p_getPower_2_, int p_getPower_3_)
    {
        if (p_getPower_1_.getType(p_getPower_2_).getBlock() != this)
        {
            return p_getPower_3_;
        }
        else
        {
            int i = ((Integer)p_getPower_1_.getType(p_getPower_2_).get(POWER)).intValue();
            return i > p_getPower_3_ ? i : p_getPower_3_;
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!p_doPhysics_1_.isClientSide)
        {
            if (this.canPlace(p_doPhysics_1_, p_doPhysics_2_))
            {
                this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
            }
            else
            {
                this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
                p_doPhysics_1_.setAir(p_doPhysics_2_);
            }
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.REDSTONE;
    }

    public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_)
    {
        return !this.Q ? 0 : this.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
    }

    public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_)
    {
        if (!this.Q)
        {
            return 0;
        }
        else
        {
            int i = ((Integer)p_a_3_.get(POWER)).intValue();

            if (i == 0)
            {
                return 0;
            }
            else if (p_a_4_ == EnumDirection.UP)
            {
                return i;
            }
            else
            {
                EnumSet enumset = EnumSet.noneOf(EnumDirection.class);

                for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                {
                    if (this.d(p_a_1_, p_a_2_, enumdirection))
                    {
                        enumset.add(enumdirection);
                    }
                }

                if (p_a_4_.k().c() && enumset.isEmpty())
                {
                    return i;
                }
                else if (enumset.contains(p_a_4_) && !enumset.contains(p_a_4_.f()) && !enumset.contains(p_a_4_.e()))
                {
                    return i;
                }
                else
                {
                    return 0;
                }
            }
        }
    }

    private boolean d(IBlockAccess p_d_1_, BlockPosition p_d_2_, EnumDirection p_d_3_)
    {
        BlockPosition blockposition = p_d_2_.shift(p_d_3_);
        IBlockData iblockdata = p_d_1_.getType(blockposition);
        Block block = iblockdata.getBlock();
        boolean flag = block.isOccluding();
        boolean flag1 = p_d_1_.getType(p_d_2_.up()).getBlock().isOccluding();
        return !flag1 && flag && e(p_d_1_, blockposition.up()) ? true : (a(iblockdata, p_d_3_) ? true : (block == Blocks.POWERED_REPEATER && iblockdata.get(BlockDiodeAbstract.FACING) == p_d_3_ ? true : !flag && e(p_d_1_, blockposition.down())));
    }

    protected static boolean e(IBlockAccess p_e_0_, BlockPosition p_e_1_)
    {
        return d(p_e_0_.getType(p_e_1_));
    }

    protected static boolean d(IBlockData p_d_0_)
    {
        return a(p_d_0_, (EnumDirection)null);
    }

    protected static boolean a(IBlockData p_a_0_, EnumDirection p_a_1_)
    {
        Block block = p_a_0_.getBlock();

        if (block == Blocks.REDSTONE_WIRE)
        {
            return true;
        }
        else if (Blocks.UNPOWERED_REPEATER.e(block))
        {
            EnumDirection enumdirection = (EnumDirection)p_a_0_.get(BlockRepeater.FACING);
            return enumdirection == p_a_1_ || enumdirection.opposite() == p_a_1_;
        }
        else
        {
            return block.isPowerSource() && p_a_1_ != null;
        }
    }

    public boolean isPowerSource()
    {
        return this.Q;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(POWER, Integer.valueOf(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Integer)p_toLegacyData_1_.get(POWER)).intValue();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {NORTH, EAST, SOUTH, WEST, POWER});
    }

    static enum EnumRedstoneWireConnection implements INamable
    {
        UP("up"),
        SIDE("side"),
        NONE("none");

        private final String d;

        private EnumRedstoneWireConnection(String p_i322_3_)
        {
            this.d = p_i322_3_;
        }

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this.d;
        }
    }
}
