package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockRepeater extends BlockDiodeAbstract
{
    public static final BlockStateBoolean LOCKED = BlockStateBoolean.of("locked");
    public static final BlockStateInteger DELAY = BlockStateInteger.of("delay", 1, 4);

    protected BlockRepeater(boolean p_i637_1_)
    {
        super(p_i637_1_);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(DELAY, Integer.valueOf(1)).set(LOCKED, Boolean.valueOf(false)));
    }

    public String getName()
    {
        return LocaleI18n.get("item.diode.name");
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        return p_updateState_1_.set(LOCKED, Boolean.valueOf(this.b(p_updateState_2_, p_updateState_3_, p_updateState_1_)));
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (!p_interact_4_.abilities.mayBuild)
        {
            return false;
        }
        else
        {
            p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_.a(DELAY), 3);
            return true;
        }
    }

    protected int d(IBlockData p_d_1_)
    {
        return ((Integer)p_d_1_.get(DELAY)).intValue() * 2;
    }

    protected IBlockData e(IBlockData p_e_1_)
    {
        Integer integer = (Integer)p_e_1_.get(DELAY);
        Boolean obool = (Boolean)p_e_1_.get(LOCKED);
        EnumDirection enumdirection = (EnumDirection)p_e_1_.get(FACING);
        return Blocks.POWERED_REPEATER.getBlockData().set(FACING, enumdirection).set(DELAY, integer).set(LOCKED, obool);
    }

    protected IBlockData k(IBlockData p_k_1_)
    {
        Integer integer = (Integer)p_k_1_.get(DELAY);
        Boolean obool = (Boolean)p_k_1_.get(LOCKED);
        EnumDirection enumdirection = (EnumDirection)p_k_1_.get(FACING);
        return Blocks.UNPOWERED_REPEATER.getBlockData().set(FACING, enumdirection).set(DELAY, integer).set(LOCKED, obool);
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.REPEATER;
    }

    public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_)
    {
        return this.c(p_b_1_, p_b_2_, p_b_3_) > 0;
    }

    protected boolean c(Block p_c_1_)
    {
        return d(p_c_1_);
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
        this.h(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_)).set(LOCKED, Boolean.valueOf(false)).set(DELAY, Integer.valueOf(1 + (p_fromLegacyData_1_ >> 2)));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;
        i = i | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();
        i = i | ((Integer)p_toLegacyData_1_.get(DELAY)).intValue() - 1 << 2;
        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, DELAY, LOCKED});
    }
}
