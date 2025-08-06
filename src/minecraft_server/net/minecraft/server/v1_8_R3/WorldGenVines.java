package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenVines extends WorldGenerator
{
    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        for (; p_generate_3_.getY() < 128; p_generate_3_ = p_generate_3_.up())
        {
            if (p_generate_1_.isEmpty(p_generate_3_))
            {
                for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL.a())
                {
                    if (Blocks.VINE.canPlace(p_generate_1_, p_generate_3_, enumdirection))
                    {
                        IBlockData iblockdata = Blocks.VINE.getBlockData().set(BlockVine.NORTH, Boolean.valueOf(enumdirection == EnumDirection.NORTH)).set(BlockVine.EAST, Boolean.valueOf(enumdirection == EnumDirection.EAST)).set(BlockVine.SOUTH, Boolean.valueOf(enumdirection == EnumDirection.SOUTH)).set(BlockVine.WEST, Boolean.valueOf(enumdirection == EnumDirection.WEST));
                        p_generate_1_.setTypeAndData(p_generate_3_, iblockdata, 2);
                        break;
                    }
                }
            }
            else
            {
                p_generate_3_ = p_generate_3_.a(p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), 0, p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4));
            }
        }

        return true;
    }
}
