package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenEnder extends WorldGenerator
{
    private Block baseBlockRequired;

    public WorldGenEnder(Block p_i708_1_)
    {
        this.baseBlockRequired = p_i708_1_;
    }

    public boolean generate(World worldIn, Random rand, BlockPosition position)
    {
        if (worldIn.isEmpty(position) && worldIn.getType(position.down()).getBlock() == this.baseBlockRequired)
        {
            int i = rand.nextInt(32) + 6;
            int j = rand.nextInt(4) + 1;
            logger.debug("WorldGenEnder.java i", i, "j", j, "position", position);
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

            for (int k = position.getX() - j; k <= position.getX() + j; ++k)
            {
                for (int l = position.getZ() - j; l <= position.getZ() + j; ++l)
                {
                    int i1 = k - position.getX();
                    int j1 = l - position.getZ();

                    if (i1 * i1 + j1 * j1 <= j * j + 1 && worldIn.getType(blockposition$mutableblockposition.c(k, position.getY() - 1, l)).getBlock() != this.baseBlockRequired)
                    {
                        return false;
                    }
                }
            }

            for (int l1 = position.getY(); l1 < position.getY() + i && l1 < 256; ++l1)
            {
                for (int i2 = position.getX() - j; i2 <= position.getX() + j; ++i2)
                {
                    for (int j2 = position.getZ() - j; j2 <= position.getZ() + j; ++j2)
                    {
                        int k2 = i2 - position.getX();
                        int k1 = j2 - position.getZ();

                        if (k2 * k2 + k1 * k1 <= j * j + 1)
                        {
                            worldIn.setTypeAndData(new BlockPosition(i2, l1, j2), Blocks.OBSIDIAN.getBlockData(), 2);
                        }
                    }
                }
            }

            EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(worldIn);
            entityendercrystal.setPositionRotation((double)((float)position.getX() + 0.5F), (double)(position.getY() + i), (double)((float)position.getZ() + 0.5F), rand.nextFloat() * 360.0F, 0.0F);
            worldIn.addEntity(entityendercrystal);
            worldIn.setTypeAndData(position.up(i), Blocks.BEDROCK.getBlockData(), 2);
            logger.debug("WorldGenEnder.java Created new bedrock at", position, "and also", position.up(i));
            return true;
        }
        else
        {
            return false;
        }
    }
}
