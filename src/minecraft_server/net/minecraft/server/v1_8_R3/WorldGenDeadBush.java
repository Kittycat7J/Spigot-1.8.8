package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator
{
    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        Block block;

        while (((block = p_generate_1_.getType(p_generate_3_).getBlock()).getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) && p_generate_3_.getY() > 0)
        {
            p_generate_3_ = p_generate_3_.down();
        }

        for (int i = 0; i < 4; ++i)
        {
            BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));

            if (p_generate_1_.isEmpty(blockposition) && Blocks.DEADBUSH.f(p_generate_1_, blockposition, Blocks.DEADBUSH.getBlockData()))
            {
                p_generate_1_.setTypeAndData(blockposition, Blocks.DEADBUSH.getBlockData(), 2);
            }
        }

        return true;
    }
}
