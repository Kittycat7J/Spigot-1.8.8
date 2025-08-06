package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenDungeons extends WorldGenerator
{
    private static final Logger a = LogManager.getLogger();
    private static final String[] b = new String[] {"Skeleton", "Zombie", "Zombie", "Spider"};
    private static final List<StructurePieceTreasure> c = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 4, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 1, 10), new StructurePieceTreasure(Items.WHEAT, 0, 1, 4, 10), new StructurePieceTreasure(Items.GUNPOWDER, 0, 1, 4, 10), new StructurePieceTreasure(Items.STRING, 0, 1, 4, 10), new StructurePieceTreasure(Items.BUCKET, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_APPLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.REDSTONE, 0, 1, 4, 10), new StructurePieceTreasure(Items.RECORD_13, 0, 1, 1, 4), new StructurePieceTreasure(Items.RECORD_CAT, 0, 1, 1, 4), new StructurePieceTreasure(Items.NAME_TAG, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 2), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 5), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1)});

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        boolean flag = true;
        int i = p_generate_2_.nextInt(2) + 2;
        int j = -i - 1;
        int k = i + 1;
        boolean flag1 = true;
        boolean flag2 = true;
        int l = p_generate_2_.nextInt(2) + 2;
        int i1 = -l - 1;
        int j1 = l + 1;
        int k1 = 0;

        for (int l1 = j; l1 <= k; ++l1)
        {
            for (int i2 = -1; i2 <= 4; ++i2)
            {
                for (int j2 = i1; j2 <= j1; ++j2)
                {
                    BlockPosition blockposition = p_generate_3_.a(l1, i2, j2);
                    Material material = p_generate_1_.getType(blockposition).getBlock().getMaterial();
                    boolean flag3 = material.isBuildable();

                    if (i2 == -1 && !flag3)
                    {
                        return false;
                    }

                    if (i2 == 4 && !flag3)
                    {
                        return false;
                    }

                    if ((l1 == j || l1 == k || j2 == i1 || j2 == j1) && i2 == 0 && p_generate_1_.isEmpty(blockposition) && p_generate_1_.isEmpty(blockposition.up()))
                    {
                        ++k1;
                    }
                }
            }
        }

        if (k1 >= 1 && k1 <= 5)
        {
            for (int l2 = j; l2 <= k; ++l2)
            {
                for (int j3 = 3; j3 >= -1; --j3)
                {
                    for (int l3 = i1; l3 <= j1; ++l3)
                    {
                        BlockPosition blockposition1 = p_generate_3_.a(l2, j3, l3);

                        if (l2 != j && j3 != -1 && l3 != i1 && l2 != k && j3 != 4 && l3 != j1)
                        {
                            if (p_generate_1_.getType(blockposition1).getBlock() != Blocks.CHEST)
                            {
                                p_generate_1_.setAir(blockposition1);
                            }
                        }
                        else if (blockposition1.getY() >= 0 && !p_generate_1_.getType(blockposition1.down()).getBlock().getMaterial().isBuildable())
                        {
                            p_generate_1_.setAir(blockposition1);
                        }
                        else if (p_generate_1_.getType(blockposition1).getBlock().getMaterial().isBuildable() && p_generate_1_.getType(blockposition1).getBlock() != Blocks.CHEST)
                        {
                            if (j3 == -1 && p_generate_2_.nextInt(4) != 0)
                            {
                                p_generate_1_.setTypeAndData(blockposition1, Blocks.MOSSY_COBBLESTONE.getBlockData(), 2);
                            }
                            else
                            {
                                p_generate_1_.setTypeAndData(blockposition1, Blocks.COBBLESTONE.getBlockData(), 2);
                            }
                        }
                    }
                }
            }

            for (int i3 = 0; i3 < 2; ++i3)
            {
                for (int k3 = 0; k3 < 3; ++k3)
                {
                    int i4 = p_generate_3_.getX() + p_generate_2_.nextInt(i * 2 + 1) - i;
                    int j4 = p_generate_3_.getY();
                    int k4 = p_generate_3_.getZ() + p_generate_2_.nextInt(l * 2 + 1) - l;
                    BlockPosition blockposition2 = new BlockPosition(i4, j4, k4);

                    if (p_generate_1_.isEmpty(blockposition2))
                    {
                        int k2 = 0;

                        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                        {
                            if (p_generate_1_.getType(blockposition2.shift(enumdirection)).getBlock().getMaterial().isBuildable())
                            {
                                ++k2;
                            }
                        }

                        if (k2 == 1)
                        {
                            p_generate_1_.setTypeAndData(blockposition2, Blocks.CHEST.f(p_generate_1_, blockposition2, Blocks.CHEST.getBlockData()), 2);
                            List list = StructurePieceTreasure.a(c, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_generate_2_)});
                            TileEntity tileentity1 = p_generate_1_.getTileEntity(blockposition2);

                            if (tileentity1 instanceof TileEntityChest)
                            {
                                StructurePieceTreasure.a(p_generate_2_, list, (IInventory)((TileEntityChest)tileentity1), 8);
                            }

                            break;
                        }
                    }
                }
            }

            p_generate_1_.setTypeAndData(p_generate_3_, Blocks.MOB_SPAWNER.getBlockData(), 2);
            TileEntity tileentity = p_generate_1_.getTileEntity(p_generate_3_);

            if (tileentity instanceof TileEntityMobSpawner)
            {
                ((TileEntityMobSpawner)tileentity).getSpawner().setMobName(this.a(p_generate_2_));
            }
            else
            {
                a.error("Failed to fetch mob spawner entity at (" + p_generate_3_.getX() + ", " + p_generate_3_.getY() + ", " + p_generate_3_.getZ() + ")");
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private String a(Random p_a_1_)
    {
        return b[p_a_1_.nextInt(b.length)];
    }
}
