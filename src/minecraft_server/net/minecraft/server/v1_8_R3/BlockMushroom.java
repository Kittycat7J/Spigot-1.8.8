package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.TreeType;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockMushroom extends BlockPlant implements IBlockFragilePlantElement
{
    protected BlockMushroom()
    {
        float f = 0.2F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.a(true);
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        int i = p_b_2_.getX();
        int j = p_b_2_.getY();
        int k = p_b_2_.getZ();

        if (p_b_4_.nextInt(Math.max(1, (int)p_b_1_.growthOdds / p_b_1_.spigotConfig.mushroomModifier * 25)) == 0)
        {
            int l = 5;

            for (BlockPosition blockposition : BlockPosition.b(p_b_2_.a(-4, -1, -4), p_b_2_.a(4, 1, 4)))
            {
                if (p_b_1_.getType(blockposition).getBlock() == this)
                {
                    --l;

                    if (l <= 0)
                    {
                        return;
                    }
                }
            }

            BlockPosition blockposition1 = p_b_2_.a(p_b_4_.nextInt(3) - 1, p_b_4_.nextInt(2) - p_b_4_.nextInt(2), p_b_4_.nextInt(3) - 1);

            for (int i1 = 0; i1 < 4; ++i1)
            {
                if (p_b_1_.isEmpty(blockposition1) && this.f(p_b_1_, blockposition1, this.getBlockData()))
                {
                    p_b_2_ = blockposition1;
                }

                blockposition1 = p_b_2_.a(p_b_4_.nextInt(3) - 1, p_b_4_.nextInt(2) - p_b_4_.nextInt(2), p_b_4_.nextInt(3) - 1);
            }

            if (p_b_1_.isEmpty(blockposition1) && this.f(p_b_1_, blockposition1, this.getBlockData()))
            {
                org.bukkit.World world = p_b_1_.getWorld();
                org.bukkit.block.BlockState blockstate = world.getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()).getState();
                blockstate.setType(CraftMagicNumbers.getMaterial((Block)this));
                BlockSpreadEvent blockspreadevent = new BlockSpreadEvent(blockstate.getBlock(), world.getBlockAt(i, j, k), blockstate);
                p_b_1_.getServer().getPluginManager().callEvent(blockspreadevent);

                if (!blockspreadevent.isCancelled())
                {
                    blockstate.update(true);
                }
            }
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return super.canPlace(p_canPlace_1_, p_canPlace_2_) && this.f(p_canPlace_1_, p_canPlace_2_, this.getBlockData());
    }

    protected boolean c(Block p_c_1_)
    {
        return p_c_1_.o();
    }

    public boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        if (p_f_2_.getY() >= 0 && p_f_2_.getY() < 256)
        {
            IBlockData iblockdata = p_f_1_.getType(p_f_2_.down());
            return iblockdata.getBlock() == Blocks.MYCELIUM ? true : (iblockdata.getBlock() == Blocks.DIRT && iblockdata.get(BlockDirt.VARIANT) == BlockDirt.EnumDirtVariant.PODZOL ? true : p_f_1_.k(p_f_2_) < 13 && this.c(iblockdata.getBlock()));
        }
        else
        {
            return false;
        }
    }

    public boolean d(World p_d_1_, BlockPosition p_d_2_, IBlockData p_d_3_, Random p_d_4_)
    {
        p_d_1_.setAir(p_d_2_);
        WorldGenHugeMushroom worldgenhugemushroom = null;

        if (this == Blocks.BROWN_MUSHROOM)
        {
            BlockSapling.treeType = TreeType.BROWN_MUSHROOM;
            worldgenhugemushroom = new WorldGenHugeMushroom(Blocks.BROWN_MUSHROOM_BLOCK);
        }
        else if (this == Blocks.RED_MUSHROOM)
        {
            BlockSapling.treeType = TreeType.RED_MUSHROOM;
            worldgenhugemushroom = new WorldGenHugeMushroom(Blocks.RED_MUSHROOM_BLOCK);
        }

        if (worldgenhugemushroom != null && worldgenhugemushroom.generate(p_d_1_, p_d_4_, p_d_2_))
        {
            return true;
        }
        else
        {
            p_d_1_.setTypeAndData(p_d_2_, p_d_3_, 3);
            return false;
        }
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_)
    {
        return true;
    }

    public boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_)
    {
        return (double)p_a_2_.nextFloat() < 0.4D;
    }

    public void b(World p_b_1_, Random p_b_2_, BlockPosition p_b_3_, IBlockData p_b_4_)
    {
        this.d(p_b_1_, p_b_3_, p_b_4_, p_b_2_);
    }
}
