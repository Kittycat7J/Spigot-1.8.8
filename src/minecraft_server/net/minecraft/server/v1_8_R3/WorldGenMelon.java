package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenMelon extends WorldGenerator
{
    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));

            if (Blocks.MELON_BLOCK.canPlace(p_generate_1_, blockposition) && p_generate_1_.getType(blockposition.down()).getBlock() == Blocks.GRASS)
            {
                p_generate_1_.setTypeAndData(blockposition, Blocks.MELON_BLOCK.getBlockData(), 2);
            }
        }

        return true;
    }
}
