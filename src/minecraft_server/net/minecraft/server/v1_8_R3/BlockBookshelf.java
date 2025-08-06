package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockBookshelf extends Block
{
    public BlockBookshelf()
    {
        super(Material.WOOD);
        this.a(CreativeModeTab.b);
    }

    public int a(Random p_a_1_)
    {
        return 3;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.BOOK;
    }
}
