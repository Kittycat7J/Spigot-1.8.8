package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;

public class WorldGenBonusChest extends WorldGenerator
{
    private final List<StructurePieceTreasure> a;
    private final int b;

    public WorldGenBonusChest(List<StructurePieceTreasure> p_i693_1_, int p_i693_2_)
    {
        this.a = p_i693_1_;
        this.b = p_i693_2_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        Block block;

        while (((block = p_generate_1_.getType(p_generate_3_).getBlock()).getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) && p_generate_3_.getY() > 1)
        {
            p_generate_3_ = p_generate_3_.down();
        }

        if (p_generate_3_.getY() < 1)
        {
            return false;
        }
        else
        {
            p_generate_3_ = p_generate_3_.up();

            for (int i = 0; i < 4; ++i)
            {
                BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(3) - p_generate_2_.nextInt(3), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4));

                if (p_generate_1_.isEmpty(blockposition) && World.a((IBlockAccess)p_generate_1_, (BlockPosition)blockposition.down()))
                {
                    p_generate_1_.setTypeAndData(blockposition, Blocks.CHEST.getBlockData(), 2);
                    TileEntity tileentity = p_generate_1_.getTileEntity(blockposition);

                    if (tileentity instanceof TileEntityChest)
                    {
                        StructurePieceTreasure.a(p_generate_2_, this.a, (IInventory)((TileEntityChest)tileentity), this.b);
                    }

                    BlockPosition blockposition1 = blockposition.east();
                    BlockPosition blockposition2 = blockposition.west();
                    BlockPosition blockposition3 = blockposition.north();
                    BlockPosition blockposition4 = blockposition.south();

                    if (p_generate_1_.isEmpty(blockposition2) && World.a((IBlockAccess)p_generate_1_, (BlockPosition)blockposition2.down()))
                    {
                        p_generate_1_.setTypeAndData(blockposition2, Blocks.TORCH.getBlockData(), 2);
                    }

                    if (p_generate_1_.isEmpty(blockposition1) && World.a((IBlockAccess)p_generate_1_, (BlockPosition)blockposition1.down()))
                    {
                        p_generate_1_.setTypeAndData(blockposition1, Blocks.TORCH.getBlockData(), 2);
                    }

                    if (p_generate_1_.isEmpty(blockposition3) && World.a((IBlockAccess)p_generate_1_, (BlockPosition)blockposition3.down()))
                    {
                        p_generate_1_.setTypeAndData(blockposition3, Blocks.TORCH.getBlockData(), 2);
                    }

                    if (p_generate_1_.isEmpty(blockposition4) && World.a((IBlockAccess)p_generate_1_, (BlockPosition)blockposition4.down()))
                    {
                        p_generate_1_.setTypeAndData(blockposition4, Blocks.TORCH.getBlockData(), 2);
                    }

                    return true;
                }
            }

            return false;
        }
    }
}
