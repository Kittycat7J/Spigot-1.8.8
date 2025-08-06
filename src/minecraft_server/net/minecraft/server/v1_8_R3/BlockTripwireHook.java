package net.minecraft.server.v1_8_R3;

import com.google.common.base.Objects;
import java.util.Random;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTripwireHook extends Block
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
    public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
    public static final BlockStateBoolean ATTACHED = BlockStateBoolean.of("attached");
    public static final BlockStateBoolean SUSPENDED = BlockStateBoolean.of("suspended");

    public BlockTripwireHook()
    {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(POWERED, Boolean.valueOf(false)).set(ATTACHED, Boolean.valueOf(false)).set(SUSPENDED, Boolean.valueOf(false)));
        this.a(CreativeModeTab.d);
        this.a(true);
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        return p_updateState_1_.set(SUSPENDED, Boolean.valueOf(!World.a(p_updateState_2_, p_updateState_3_.down())));
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

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_)
    {
        return p_canPlace_3_.k().c() && p_canPlace_1_.getType(p_canPlace_2_.shift(p_canPlace_3_.opposite())).getBlock().isOccluding();
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            if (p_canPlace_1_.getType(p_canPlace_2_.shift(enumdirection)).getBlock().isOccluding())
            {
                return true;
            }
        }

        return false;
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        IBlockData iblockdata = this.getBlockData().set(POWERED, Boolean.valueOf(false)).set(ATTACHED, Boolean.valueOf(false)).set(SUSPENDED, Boolean.valueOf(false));

        if (p_getPlacedState_3_.k().c())
        {
            iblockdata = iblockdata.set(FACING, p_getPlacedState_3_);
        }

        return iblockdata;
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        this.a(p_postPlace_1_, p_postPlace_2_, p_postPlace_3_, false, false, -1, (IBlockData)null);
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (p_doPhysics_4_ != this && this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_))
        {
            EnumDirection enumdirection = (EnumDirection)p_doPhysics_3_.get(FACING);

            if (!p_doPhysics_1_.getType(p_doPhysics_2_.shift(enumdirection.opposite())).getBlock().isOccluding())
            {
                this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
                p_doPhysics_1_.setAir(p_doPhysics_2_);
            }
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_, boolean p_a_5_, int p_a_6_, IBlockData p_a_7_)
    {
        EnumDirection enumdirection = (EnumDirection)p_a_3_.get(FACING);
        boolean flag = ((Boolean)p_a_3_.get(ATTACHED)).booleanValue();
        boolean flag1 = ((Boolean)p_a_3_.get(POWERED)).booleanValue();
        boolean flag2 = !World.a((IBlockAccess)p_a_1_, (BlockPosition)p_a_2_.down());
        boolean flag3 = !p_a_4_;
        boolean flag4 = false;
        int i = 0;
        IBlockData[] aiblockdata = new IBlockData[42];

        for (int j = 1; j < 42; ++j)
        {
            BlockPosition blockposition = p_a_2_.shift(enumdirection, j);
            IBlockData iblockdata = p_a_1_.getType(blockposition);

            if (iblockdata.getBlock() == Blocks.TRIPWIRE_HOOK)
            {
                if (iblockdata.get(FACING) == enumdirection.opposite())
                {
                    i = j;
                }

                break;
            }

            if (iblockdata.getBlock() != Blocks.TRIPWIRE && j != p_a_6_)
            {
                aiblockdata[j] = null;
                flag3 = false;
            }
            else
            {
                if (j == p_a_6_)
                {
                    iblockdata = (IBlockData)Objects.firstNonNull(p_a_7_, iblockdata);
                }

                boolean flag5 = !((Boolean)iblockdata.get(BlockTripwire.DISARMED)).booleanValue();
                boolean flag6 = ((Boolean)iblockdata.get(BlockTripwire.POWERED)).booleanValue();
                boolean flag7 = ((Boolean)iblockdata.get(BlockTripwire.SUSPENDED)).booleanValue();
                flag3 &= flag7 == flag2;
                flag4 |= flag5 && flag6;
                aiblockdata[j] = iblockdata;

                if (j == p_a_6_)
                {
                    p_a_1_.a((BlockPosition)p_a_2_, (Block)this, this.a(p_a_1_));
                    flag3 &= flag5;
                }
            }
        }

        flag3 = flag3 & i > 1;
        flag4 = flag4 & flag3;
        IBlockData iblockdata2 = this.getBlockData().set(ATTACHED, Boolean.valueOf(flag3)).set(POWERED, Boolean.valueOf(flag4));

        if (i > 0)
        {
            BlockPosition blockposition1 = p_a_2_.shift(enumdirection, i);
            EnumDirection enumdirection1 = enumdirection.opposite();
            p_a_1_.setTypeAndData(blockposition1, iblockdata2.set(FACING, enumdirection1), 3);
            this.a(p_a_1_, blockposition1, enumdirection1);
            this.a(p_a_1_, blockposition1, flag3, flag4, flag, flag1);
        }

        org.bukkit.block.Block block = p_a_1_.getWorld().getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
        BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, 15, 0);
        p_a_1_.getServer().getPluginManager().callEvent(blockredstoneevent);

        if (blockredstoneevent.getNewCurrent() <= 0)
        {
            this.a(p_a_1_, p_a_2_, flag3, flag4, flag, flag1);

            if (!p_a_4_)
            {
                p_a_1_.setTypeAndData(p_a_2_, iblockdata2.set(FACING, enumdirection), 3);

                if (p_a_5_)
                {
                    this.a(p_a_1_, p_a_2_, enumdirection);
                }
            }

            if (flag != flag3)
            {
                for (int k = 1; k < i; ++k)
                {
                    BlockPosition blockposition2 = p_a_2_.shift(enumdirection, k);
                    IBlockData iblockdata1 = aiblockdata[k];

                    if (iblockdata1 != null && p_a_1_.getType(blockposition2).getBlock() != Blocks.AIR)
                    {
                        p_a_1_.setTypeAndData(blockposition2, iblockdata1.set(ATTACHED, Boolean.valueOf(flag3)), 3);
                    }
                }
            }
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_)
    {
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        this.a(p_b_1_, p_b_2_, p_b_3_, false, true, -1, (IBlockData)null);
    }

    private void a(World p_a_1_, BlockPosition p_a_2_, boolean p_a_3_, boolean p_a_4_, boolean p_a_5_, boolean p_a_6_)
    {
        if (p_a_4_ && !p_a_6_)
        {
            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.1D, (double)p_a_2_.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
        }
        else if (!p_a_4_ && p_a_6_)
        {
            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.1D, (double)p_a_2_.getZ() + 0.5D, "random.click", 0.4F, 0.5F);
        }
        else if (p_a_3_ && !p_a_5_)
        {
            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.1D, (double)p_a_2_.getZ() + 0.5D, "random.click", 0.4F, 0.7F);
        }
        else if (!p_a_3_ && p_a_5_)
        {
            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.1D, (double)p_a_2_.getZ() + 0.5D, "random.bowhit", 0.4F, 1.2F / (p_a_1_.random.nextFloat() * 0.2F + 0.9F));
        }
    }

    private void a(World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_)
    {
        p_a_1_.applyPhysics(p_a_2_, this);
        p_a_1_.applyPhysics(p_a_2_.shift(p_a_3_.opposite()), this);
    }

    private boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!this.canPlace(p_e_1_, p_e_2_))
        {
            this.b(p_e_1_, p_e_2_, p_e_3_, 0);
            p_e_1_.setAir(p_e_2_);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        float f = 0.1875F;

        switch (BlockTripwireHook.SyntheticClass_1.a[((EnumDirection)p_updateShape_1_.getType(p_updateShape_2_).get(FACING)).ordinal()])
        {
            case 1:
                this.a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
                break;

            case 2:
                this.a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
                break;

            case 3:
                this.a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
                break;

            case 4:
                this.a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        boolean flag = ((Boolean)p_remove_3_.get(ATTACHED)).booleanValue();
        boolean flag1 = ((Boolean)p_remove_3_.get(POWERED)).booleanValue();

        if (flag || flag1)
        {
            this.a(p_remove_1_, p_remove_2_, p_remove_3_, true, false, -1, (IBlockData)null);
        }

        if (flag1)
        {
            p_remove_1_.applyPhysics(p_remove_2_, this);
            p_remove_1_.applyPhysics(p_remove_2_.shift(((EnumDirection)p_remove_3_.get(FACING)).opposite()), this);
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_)
    {
        return ((Boolean)p_a_3_.get(POWERED)).booleanValue() ? 15 : 0;
    }

    public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_)
    {
        return !((Boolean)p_b_3_.get(POWERED)).booleanValue() ? 0 : (p_b_3_.get(FACING) == p_b_4_ ? 15 : 0);
    }

    public boolean isPowerSource()
    {
        return true;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_ & 3)).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0)).set(ATTACHED, Boolean.valueOf((p_fromLegacyData_1_ & 4) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        byte b0 = 0;
        int i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();

        if (((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue())
        {
            i |= 8;
        }

        if (((Boolean)p_toLegacyData_1_.get(ATTACHED)).booleanValue())
        {
            i |= 4;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, POWERED, ATTACHED, SUSPENDED});
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[EnumDirection.values().length];

        static
        {
            try
            {
                a[EnumDirection.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                a[EnumDirection.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                a[EnumDirection.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[EnumDirection.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
