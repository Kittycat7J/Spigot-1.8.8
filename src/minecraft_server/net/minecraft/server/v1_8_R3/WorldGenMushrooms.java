package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenMushrooms extends WorldGenerator
{
    private BlockPlant a;

    public WorldGenMushrooms(BlockPlant p_i694_1_)
    {
        this.a = p_i694_1_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));

            if (p_generate_1_.isEmpty(blockposition) && (!p_generate_1_.worldProvider.o() || blockposition.getY() < 255) && this.a.f(p_generate_1_, blockposition, this.a.getBlockData()))
            {
                p_generate_1_.setTypeAndData(blockposition, this.a.getBlockData(), 2);
            }
        }

        return true;
    }
}
