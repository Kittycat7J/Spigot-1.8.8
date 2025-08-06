package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator
{
    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));

            if (p_generate_1_.isEmpty(blockposition) && p_generate_1_.getType(blockposition.down()).getBlock() == Blocks.GRASS && Blocks.PUMPKIN.canPlace(p_generate_1_, blockposition))
            {
                p_generate_1_.setTypeAndData(blockposition, Blocks.PUMPKIN.getBlockData().set(BlockPumpkin.FACING, EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_generate_2_)), 2);
            }
        }

        return true;
    }
}
