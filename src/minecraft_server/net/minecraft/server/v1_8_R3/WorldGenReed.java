package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenReed extends WorldGenerator
{
    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        for (int i = 0; i < 20; ++i)
        {
            BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), 0, p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4));

            if (p_generate_1_.isEmpty(blockposition))
            {
                BlockPosition blockposition1 = blockposition.down();

                if (p_generate_1_.getType(blockposition1.west()).getBlock().getMaterial() == Material.WATER || p_generate_1_.getType(blockposition1.east()).getBlock().getMaterial() == Material.WATER || p_generate_1_.getType(blockposition1.north()).getBlock().getMaterial() == Material.WATER || p_generate_1_.getType(blockposition1.south()).getBlock().getMaterial() == Material.WATER)
                {
                    int j = 2 + p_generate_2_.nextInt(p_generate_2_.nextInt(3) + 1);

                    for (int k = 0; k < j; ++k)
                    {
                        if (Blocks.REEDS.e(p_generate_1_, blockposition))
                        {
                            p_generate_1_.setTypeAndData(blockposition.up(k), Blocks.REEDS.getBlockData(), 2);
                        }
                    }
                }
            }
        }

        return true;
    }
}
