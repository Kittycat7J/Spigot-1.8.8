package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenHugeMushroom extends WorldGenerator
{
    private Block a;

    public WorldGenHugeMushroom(Block p_i699_1_)
    {
        super(true);
        this.a = p_i699_1_;
    }

    public WorldGenHugeMushroom()
    {
        super(false);
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        if (this.a == null)
        {
            this.a = p_generate_2_.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK;
        }

        int i = p_generate_2_.nextInt(3) + 4;
        boolean flag = true;

        if (p_generate_3_.getY() >= 1 && p_generate_3_.getY() + i + 1 < 256)
        {
            for (int j = p_generate_3_.getY(); j <= p_generate_3_.getY() + 1 + i; ++j)
            {
                byte b0 = 3;

                if (j <= p_generate_3_.getY() + 3)
                {
                    b0 = 0;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for (int k = p_generate_3_.getX() - b0; k <= p_generate_3_.getX() + b0 && flag; ++k)
                {
                    for (int l = p_generate_3_.getZ() - b0; l <= p_generate_3_.getZ() + b0 && flag; ++l)
                    {
                        if (j >= 0 && j < 256)
                        {
                            Block block = p_generate_1_.getType(blockposition$mutableblockposition.c(k, j, l)).getBlock();

                            if (block.getMaterial() != Material.AIR && block.getMaterial() != Material.LEAVES)
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block1 = p_generate_1_.getType(p_generate_3_.down()).getBlock();

                if (block1 != Blocks.DIRT && block1 != Blocks.GRASS && block1 != Blocks.MYCELIUM)
                {
                    return false;
                }
                else
                {
                    int j2 = p_generate_3_.getY() + i;

                    if (this.a == Blocks.RED_MUSHROOM_BLOCK)
                    {
                        j2 = p_generate_3_.getY() + i - 3;
                    }

                    for (int k2 = j2; k2 <= p_generate_3_.getY() + i; ++k2)
                    {
                        int i3 = 1;

                        if (k2 < p_generate_3_.getY() + i)
                        {
                            ++i3;
                        }

                        if (this.a == Blocks.BROWN_MUSHROOM_BLOCK)
                        {
                            i3 = 3;
                        }

                        int j3 = p_generate_3_.getX() - i3;
                        int k3 = p_generate_3_.getX() + i3;
                        int i1 = p_generate_3_.getZ() - i3;
                        int j1 = p_generate_3_.getZ() + i3;

                        for (int k1 = j3; k1 <= k3; ++k1)
                        {
                            for (int l1 = i1; l1 <= j1; ++l1)
                            {
                                int i2 = 5;

                                if (k1 == j3)
                                {
                                    --i2;
                                }
                                else if (k1 == k3)
                                {
                                    ++i2;
                                }

                                if (l1 == i1)
                                {
                                    i2 -= 3;
                                }
                                else if (l1 == j1)
                                {
                                    i2 += 3;
                                }

                                BlockHugeMushroom.EnumHugeMushroomVariant blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.a(i2);

                                if (this.a == Blocks.BROWN_MUSHROOM_BLOCK || k2 < p_generate_3_.getY() + i)
                                {
                                    if ((k1 == j3 || k1 == k3) && (l1 == i1 || l1 == j1))
                                    {
                                        continue;
                                    }

                                    if (k1 == p_generate_3_.getX() - (i3 - 1) && l1 == i1)
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST;
                                    }

                                    if (k1 == j3 && l1 == p_generate_3_.getZ() - (i3 - 1))
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST;
                                    }

                                    if (k1 == p_generate_3_.getX() + (i3 - 1) && l1 == i1)
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST;
                                    }

                                    if (k1 == k3 && l1 == p_generate_3_.getZ() - (i3 - 1))
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST;
                                    }

                                    if (k1 == p_generate_3_.getX() - (i3 - 1) && l1 == j1)
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST;
                                    }

                                    if (k1 == j3 && l1 == p_generate_3_.getZ() + (i3 - 1))
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST;
                                    }

                                    if (k1 == p_generate_3_.getX() + (i3 - 1) && l1 == j1)
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST;
                                    }

                                    if (k1 == k3 && l1 == p_generate_3_.getZ() + (i3 - 1))
                                    {
                                        blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST;
                                    }
                                }

                                if (blockhugemushroom$enumhugemushroomvariant == BlockHugeMushroom.EnumHugeMushroomVariant.CENTER && k2 < p_generate_3_.getY() + i)
                                {
                                    blockhugemushroom$enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.ALL_INSIDE;
                                }

                                if (p_generate_3_.getY() >= p_generate_3_.getY() + i - 1 || blockhugemushroom$enumhugemushroomvariant != BlockHugeMushroom.EnumHugeMushroomVariant.ALL_INSIDE)
                                {
                                    BlockPosition blockposition = new BlockPosition(k1, k2, l1);

                                    if (!p_generate_1_.getType(blockposition).getBlock().o())
                                    {
                                        this.a(p_generate_1_, blockposition, this.a.getBlockData().set(BlockHugeMushroom.VARIANT, blockhugemushroom$enumhugemushroomvariant));
                                    }
                                }
                            }
                        }
                    }

                    for (int l2 = 0; l2 < i; ++l2)
                    {
                        Block block2 = p_generate_1_.getType(p_generate_3_.up(l2)).getBlock();

                        if (!block2.o())
                        {
                            this.a(p_generate_1_, p_generate_3_.up(l2), this.a.getBlockData().set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.STEM));
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
