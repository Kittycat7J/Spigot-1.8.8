package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator
{
    private Block a;

    public WorldGenLiquids(Block p_i709_1_)
    {
        this.a = p_i709_1_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        if (p_generate_1_.getType(p_generate_3_.up()).getBlock() != Blocks.STONE)
        {
            return false;
        }
        else if (p_generate_1_.getType(p_generate_3_.down()).getBlock() != Blocks.STONE)
        {
            return false;
        }
        else if (p_generate_1_.getType(p_generate_3_).getBlock().getMaterial() != Material.AIR && p_generate_1_.getType(p_generate_3_).getBlock() != Blocks.STONE)
        {
            return false;
        }
        else
        {
            int i = 0;

            if (p_generate_1_.getType(p_generate_3_.west()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            if (p_generate_1_.getType(p_generate_3_.east()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            if (p_generate_1_.getType(p_generate_3_.north()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            if (p_generate_1_.getType(p_generate_3_.south()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            int j = 0;

            if (p_generate_1_.isEmpty(p_generate_3_.west()))
            {
                ++j;
            }

            if (p_generate_1_.isEmpty(p_generate_3_.east()))
            {
                ++j;
            }

            if (p_generate_1_.isEmpty(p_generate_3_.north()))
            {
                ++j;
            }

            if (p_generate_1_.isEmpty(p_generate_3_.south()))
            {
                ++j;
            }

            if (i == 3 && j == 1)
            {
                p_generate_1_.setTypeAndData(p_generate_3_, this.a.getBlockData(), 2);
                p_generate_1_.a(this.a, p_generate_3_, p_generate_2_);
            }

            return true;
        }
    }
}
