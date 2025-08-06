package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockFalling extends Block
{
    public static boolean instaFall;

    public BlockFalling()
    {
        super(Material.SAND);
        this.a((CreativeModeTab)CreativeModeTab.b);
    }

    public BlockFalling(Material p_i610_1_)
    {
        super(p_i610_1_);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        p_onPlace_1_.a((BlockPosition)p_onPlace_2_, (Block)this, this.a(p_onPlace_1_));
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        p_doPhysics_1_.a((BlockPosition)p_doPhysics_2_, (Block)this, this.a(p_doPhysics_1_));
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (!p_b_1_.isClientSide)
        {
            this.f(p_b_1_, p_b_2_);
        }
    }

    private void f(World p_f_1_, BlockPosition p_f_2_)
    {
        if (canFall(p_f_1_, p_f_2_.down()) && p_f_2_.getY() >= 0)
        {
            byte b0 = 32;

            if (!instaFall && p_f_1_.areChunksLoadedBetween(p_f_2_.a(-b0, -b0, -b0), p_f_2_.a(b0, b0, b0)))
            {
                if (!p_f_1_.isClientSide)
                {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(p_f_1_, (double)p_f_2_.getX() + 0.5D, (double)p_f_2_.getY(), (double)p_f_2_.getZ() + 0.5D, p_f_1_.getType(p_f_2_));
                    this.a(entityfallingblock);
                    p_f_1_.addEntity(entityfallingblock);
                }
            }
            else
            {
                p_f_1_.setAir(p_f_2_);
                BlockPosition blockposition;

                for (blockposition = p_f_2_.down(); canFall(p_f_1_, blockposition) && blockposition.getY() > 0; blockposition = blockposition.down())
                {
                    ;
                }

                if (blockposition.getY() > 0)
                {
                    p_f_1_.setTypeUpdate(blockposition.up(), this.getBlockData());
                }
            }
        }
    }

    protected void a(EntityFallingBlock p_a_1_)
    {
    }

    public int a(World p_a_1_)
    {
        return 2;
    }

    public static boolean canFall(World p_canFall_0_, BlockPosition p_canFall_1_)
    {
        Block block = p_canFall_0_.getType(p_canFall_1_).getBlock();
        Material material = block.material;
        return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
    }

    public void a_(World p_a__1_, BlockPosition p_a__2_)
    {
    }
}
