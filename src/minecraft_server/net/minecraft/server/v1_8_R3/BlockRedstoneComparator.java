package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;

public class BlockRedstoneComparator extends BlockDiodeAbstract implements IContainer
{
    public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
    public static final BlockStateEnum<BlockRedstoneComparator.EnumComparatorMode> MODE = BlockStateEnum.<BlockRedstoneComparator.EnumComparatorMode>of("mode", BlockRedstoneComparator.EnumComparatorMode.class);

    public BlockRedstoneComparator(boolean p_i601_1_)
    {
        super(p_i601_1_);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(POWERED, Boolean.valueOf(false)).set(MODE, BlockRedstoneComparator.EnumComparatorMode.COMPARE));
        this.isTileEntity = true;
    }

    public String getName()
    {
        return LocaleI18n.get("item.comparator.name");
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.COMPARATOR;
    }

    protected int d(IBlockData p_d_1_)
    {
        return 2;
    }

    protected IBlockData e(IBlockData p_e_1_)
    {
        Boolean obool = (Boolean)p_e_1_.get(POWERED);
        BlockRedstoneComparator.EnumComparatorMode blockredstonecomparator$enumcomparatormode = (BlockRedstoneComparator.EnumComparatorMode)p_e_1_.get(MODE);
        EnumDirection enumdirection = (EnumDirection)p_e_1_.get(FACING);
        return Blocks.POWERED_COMPARATOR.getBlockData().set(FACING, enumdirection).set(POWERED, obool).set(MODE, blockredstonecomparator$enumcomparatormode);
    }

    protected IBlockData k(IBlockData p_k_1_)
    {
        Boolean obool = (Boolean)p_k_1_.get(POWERED);
        BlockRedstoneComparator.EnumComparatorMode blockredstonecomparator$enumcomparatormode = (BlockRedstoneComparator.EnumComparatorMode)p_k_1_.get(MODE);
        EnumDirection enumdirection = (EnumDirection)p_k_1_.get(FACING);
        return Blocks.UNPOWERED_COMPARATOR.getBlockData().set(FACING, enumdirection).set(POWERED, obool).set(MODE, blockredstonecomparator$enumcomparatormode);
    }

    protected boolean l(IBlockData p_l_1_)
    {
        return this.N || ((Boolean)p_l_1_.get(POWERED)).booleanValue();
    }

    protected int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        TileEntity tileentity = p_a_1_.getTileEntity(p_a_2_);
        return tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).b() : 0;
    }

    private int j(World p_j_1_, BlockPosition p_j_2_, IBlockData p_j_3_)
    {
        return p_j_3_.get(MODE) == BlockRedstoneComparator.EnumComparatorMode.SUBTRACT ? Math.max(this.f(p_j_1_, p_j_2_, p_j_3_) - this.c(p_j_1_, p_j_2_, p_j_3_), 0) : this.f(p_j_1_, p_j_2_, p_j_3_);
    }

    protected boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        int i = this.f(p_e_1_, p_e_2_, p_e_3_);

        if (i >= 15)
        {
            return true;
        }
        else if (i == 0)
        {
            return false;
        }
        else
        {
            int j = this.c(p_e_1_, p_e_2_, p_e_3_);
            return j == 0 ? true : i >= j;
        }
    }

    protected int f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        int i = super.f(p_f_1_, p_f_2_, p_f_3_);
        EnumDirection enumdirection = (EnumDirection)p_f_3_.get(FACING);
        BlockPosition blockposition = p_f_2_.shift(enumdirection);
        Block block = p_f_1_.getType(blockposition).getBlock();

        if (block.isComplexRedstone())
        {
            i = block.l(p_f_1_, blockposition);
        }
        else if (i < 15 && block.isOccluding())
        {
            blockposition = blockposition.shift(enumdirection);
            block = p_f_1_.getType(blockposition).getBlock();

            if (block.isComplexRedstone())
            {
                i = block.l(p_f_1_, blockposition);
            }
            else if (block.getMaterial() == Material.AIR)
            {
                EntityItemFrame entityitemframe = this.a(p_f_1_, enumdirection, blockposition);

                if (entityitemframe != null)
                {
                    i = entityitemframe.q();
                }
            }
        }

        return i;
    }

    private EntityItemFrame a(World p_a_1_, final EnumDirection p_a_2_, BlockPosition p_a_3_)
    {
        List list = p_a_1_.a(EntityItemFrame.class, new AxisAlignedBB((double)p_a_3_.getX(), (double)p_a_3_.getY(), (double)p_a_3_.getZ(), (double)(p_a_3_.getX() + 1), (double)(p_a_3_.getY() + 1), (double)(p_a_3_.getZ() + 1)), new Predicate<Entity>()
        {
            public boolean a(Entity p_a_1_)
            {
                return p_a_1_ != null && p_a_1_.getDirection() == p_a_2_;
            }
        });
        return list.size() == 1 ? (EntityItemFrame)list.get(0) : null;
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (!p_interact_4_.abilities.mayBuild)
        {
            return false;
        }
        else
        {
            p_interact_3_ = p_interact_3_.a(MODE);
            p_interact_1_.makeSound((double)p_interact_2_.getX() + 0.5D, (double)p_interact_2_.getY() + 0.5D, (double)p_interact_2_.getZ() + 0.5D, "random.click", 0.3F, p_interact_3_.get(MODE) == BlockRedstoneComparator.EnumComparatorMode.SUBTRACT ? 0.55F : 0.5F);
            p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 2);
            this.k(p_interact_1_, p_interact_2_, p_interact_3_);
            return true;
        }
    }

    protected void g(World p_g_1_, BlockPosition p_g_2_, IBlockData p_g_3_)
    {
        if (!p_g_1_.a((BlockPosition)p_g_2_, (Block)this))
        {
            int i = this.j(p_g_1_, p_g_2_, p_g_3_);
            TileEntity tileentity = p_g_1_.getTileEntity(p_g_2_);
            int j = tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).b() : 0;

            if (i != j || this.l(p_g_3_) != this.e(p_g_1_, p_g_2_, p_g_3_))
            {
                if (this.i(p_g_1_, p_g_2_, p_g_3_))
                {
                    p_g_1_.a(p_g_2_, this, 2, -1);
                }
                else
                {
                    p_g_1_.a(p_g_2_, this, 2, 0);
                }
            }
        }
    }

    private void k(World p_k_1_, BlockPosition p_k_2_, IBlockData p_k_3_)
    {
        int i = this.j(p_k_1_, p_k_2_, p_k_3_);
        TileEntity tileentity = p_k_1_.getTileEntity(p_k_2_);
        int j = 0;

        if (tileentity instanceof TileEntityComparator)
        {
            TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;
            j = tileentitycomparator.b();
            tileentitycomparator.a(i);
        }

        if (j != i || p_k_3_.get(MODE) == BlockRedstoneComparator.EnumComparatorMode.COMPARE)
        {
            boolean flag1 = this.e(p_k_1_, p_k_2_, p_k_3_);
            boolean flag = this.l(p_k_3_);

            if (flag && !flag1)
            {
                p_k_1_.setTypeAndData(p_k_2_, p_k_3_.set(POWERED, Boolean.valueOf(false)), 2);
            }
            else if (!flag && flag1)
            {
                p_k_1_.setTypeAndData(p_k_2_, p_k_3_.set(POWERED, Boolean.valueOf(true)), 2);
            }

            this.h(p_k_1_, p_k_2_, p_k_3_);
        }
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (this.N)
        {
            p_b_1_.setTypeAndData(p_b_2_, this.k(p_b_3_).set(POWERED, Boolean.valueOf(true)), 4);
        }

        this.k(p_b_1_, p_b_2_, p_b_3_);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        super.onPlace(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
        p_onPlace_1_.setTileEntity(p_onPlace_2_, this.a(p_onPlace_1_, 0));
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
        p_remove_1_.t(p_remove_2_);
        this.h(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_, int p_a_5_)
    {
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
        TileEntity tileentity = p_a_1_.getTileEntity(p_a_2_);
        return tileentity == null ? false : tileentity.c(p_a_4_, p_a_5_);
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityComparator();
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_)).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0)).set(MODE, (p_fromLegacyData_1_ & 4) > 0 ? BlockRedstoneComparator.EnumComparatorMode.SUBTRACT : BlockRedstoneComparator.EnumComparatorMode.COMPARE);
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;
        i = i | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();

        if (((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue())
        {
            i |= 8;
        }

        if (p_toLegacyData_1_.get(MODE) == BlockRedstoneComparator.EnumComparatorMode.SUBTRACT)
        {
            i |= 4;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, MODE, POWERED});
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection().opposite()).set(POWERED, Boolean.valueOf(false)).set(MODE, BlockRedstoneComparator.EnumComparatorMode.COMPARE);
    }

    public static enum EnumComparatorMode implements INamable
    {
        COMPARE("compare"),
        SUBTRACT("subtract");

        private final String c;

        private EnumComparatorMode(String p_i600_3_)
        {
            this.c = p_i600_3_;
        }

        public String toString()
        {
            return this.c;
        }

        public String getName()
        {
            return this.c;
        }
    }
}
