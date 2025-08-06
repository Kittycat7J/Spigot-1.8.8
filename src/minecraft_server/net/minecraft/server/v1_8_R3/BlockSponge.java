package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;

public class BlockSponge extends Block
{
    public static final BlockStateBoolean WET = BlockStateBoolean.of("wet");

    protected BlockSponge()
    {
        super(Material.SPONGE);
        this.j(this.blockStateList.getBlockData().set(WET, Boolean.valueOf(false)));
        this.a(CreativeModeTab.b);
    }

    public String getName()
    {
        return LocaleI18n.get(this.a() + ".dry.name");
    }

    public int getDropData(IBlockData p_getDropData_1_)
    {
        return ((Boolean)p_getDropData_1_.get(WET)).booleanValue() ? 1 : 0;
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
        super.doPhysics(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
    }

    protected void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!((Boolean)p_e_3_.get(WET)).booleanValue() && this.e(p_e_1_, p_e_2_))
        {
            p_e_1_.setTypeAndData(p_e_2_, p_e_3_.set(WET, Boolean.valueOf(true)), 2);
            p_e_1_.triggerEffect(2001, p_e_2_, Block.getId(Blocks.WATER));
        }
    }

    private boolean e(World p_e_1_, BlockPosition p_e_2_)
    {
        LinkedList linkedlist = Lists.newLinkedList();
        ArrayList arraylist = Lists.newArrayList();
        linkedlist.add(new Tuple(p_e_2_, Integer.valueOf(0)));
        int i = 0;

        while (!linkedlist.isEmpty())
        {
            Tuple tuple = (Tuple)linkedlist.poll();
            BlockPosition blockposition = (BlockPosition)tuple.a();
            int j = ((Integer)tuple.b()).intValue();

            for (EnumDirection enumdirection : EnumDirection.values())
            {
                BlockPosition blockposition1 = blockposition.shift(enumdirection);

                if (p_e_1_.getType(blockposition1).getBlock().getMaterial() == Material.WATER)
                {
                    p_e_1_.setTypeAndData(blockposition1, Blocks.AIR.getBlockData(), 2);
                    arraylist.add(blockposition1);
                    ++i;

                    if (j < 6)
                    {
                        linkedlist.add(new Tuple(blockposition1, Integer.valueOf(j + 1)));
                    }
                }
            }

            if (i > 64)
            {
                break;
            }
        }

        for (BlockPosition blockposition2 : arraylist)
        {
            p_e_1_.applyPhysics(blockposition2, Blocks.AIR);
        }

        return i > 0;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(WET, Boolean.valueOf((p_fromLegacyData_1_ & 1) == 1));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Boolean)p_toLegacyData_1_.get(WET)).booleanValue() ? 1 : 0;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {WET});
    }
}
