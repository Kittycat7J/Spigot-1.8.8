package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockDeadBush extends BlockPlant
{
    protected BlockDeadBush()
    {
        super(Material.REPLACEABLE_PLANT);
        float f = 0.4F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return MaterialMapColor.o;
    }

    protected boolean c(Block p_c_1_)
    {
        return p_c_1_ == Blocks.SAND || p_c_1_ == Blocks.HARDENED_CLAY || p_c_1_ == Blocks.STAINED_HARDENED_CLAY || p_c_1_ == Blocks.DIRT;
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_)
    {
        return true;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return null;
    }

    public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_)
    {
        if (!p_a_1_.isClientSide && p_a_2_.bZ() != null && p_a_2_.bZ().getItem() == Items.SHEARS)
        {
            p_a_2_.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
            a(p_a_1_, p_a_3_, new ItemStack(Blocks.DEADBUSH, 1, 0));
        }
        else
        {
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
        }
    }
}
