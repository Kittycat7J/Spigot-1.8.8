package net.minecraft.server.v1_8_R3;

public class BlockPowered extends Block
{
    public BlockPowered(Material p_i633_1_, MaterialMapColor p_i633_2_)
    {
        super(p_i633_1_, p_i633_2_);
    }

    public boolean isPowerSource()
    {
        return true;
    }

    public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_)
    {
        return 15;
    }
}
