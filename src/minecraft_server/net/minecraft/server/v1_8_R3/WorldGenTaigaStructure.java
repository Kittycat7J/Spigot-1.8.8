package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenTaigaStructure extends WorldGenerator
{
    private final Block a;
    private final int b;

    public WorldGenTaigaStructure(Block p_i692_1_, int p_i692_2_)
    {
        super(false);
        this.a = p_i692_1_;
        this.b = p_i692_2_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        while (true)
        {
            label0:
            {
                if (p_generate_3_.getY() > 3)
                {
                    if (p_generate_1_.isEmpty(p_generate_3_.down()))
                    {
                        break label0;
                    }

                    Block block = p_generate_1_.getType(p_generate_3_.down()).getBlock();

                    if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.STONE)
                    {
                        break label0;
                    }
                }

                if (p_generate_3_.getY() <= 3)
                {
                    return false;
                }

                int i1 = this.b;

                for (int i = 0; i1 >= 0 && i < 3; ++i)
                {
                    int j = i1 + p_generate_2_.nextInt(2);
                    int k = i1 + p_generate_2_.nextInt(2);
                    int l = i1 + p_generate_2_.nextInt(2);
                    float f = (float)(j + k + l) * 0.333F + 0.5F;

                    for (BlockPosition blockposition : BlockPosition.a(p_generate_3_.a(-j, -k, -l), p_generate_3_.a(j, k, l)))
                    {
                        if (blockposition.i(p_generate_3_) <= (double)(f * f))
                        {
                            p_generate_1_.setTypeAndData(blockposition, this.a.getBlockData(), 4);
                        }
                    }

                    p_generate_3_ = p_generate_3_.a(-(i1 + 1) + p_generate_2_.nextInt(2 + i1 * 2), 0 - p_generate_2_.nextInt(2), -(i1 + 1) + p_generate_2_.nextInt(2 + i1 * 2));
                }

                return true;
            }
            p_generate_3_ = p_generate_3_.down();
        }
    }
}
