package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockDragonEgg extends Block
{
    public BlockDragonEgg()
    {
        super(Material.DRAGON_EGG, MaterialMapColor.E);
        this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
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
        this.e(p_b_1_, p_b_2_);
    }

    private void e(World p_e_1_, BlockPosition p_e_2_)
    {
        if (BlockFalling.canFall(p_e_1_, p_e_2_.down()) && p_e_2_.getY() >= 0)
        {
            byte b0 = 32;

            if (!BlockFalling.instaFall && p_e_1_.areChunksLoadedBetween(p_e_2_.a(-b0, -b0, -b0), p_e_2_.a(b0, b0, b0)))
            {
                p_e_1_.addEntity(new EntityFallingBlock(p_e_1_, (double)((float)p_e_2_.getX() + 0.5F), (double)p_e_2_.getY(), (double)((float)p_e_2_.getZ() + 0.5F), this.getBlockData()));
            }
            else
            {
                p_e_1_.setAir(p_e_2_);
                BlockPosition blockposition;

                for (blockposition = p_e_2_; BlockFalling.canFall(p_e_1_, blockposition) && blockposition.getY() > 0; blockposition = blockposition.down())
                {
                    ;
                }

                if (blockposition.getY() > 0)
                {
                    p_e_1_.setTypeAndData(blockposition, this.getBlockData(), 2);
                }
            }
        }
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        this.f(p_interact_1_, p_interact_2_);
        return true;
    }

    public void attack(World p_attack_1_, BlockPosition p_attack_2_, EntityHuman p_attack_3_)
    {
        this.f(p_attack_1_, p_attack_2_);
    }

    private void f(World p_f_1_, BlockPosition p_f_2_)
    {
        IBlockData iblockdata = p_f_1_.getType(p_f_2_);

        if (iblockdata.getBlock() == this)
        {
            for (int i = 0; i < 1000; ++i)
            {
                BlockPosition blockposition = p_f_2_.a(p_f_1_.random.nextInt(16) - p_f_1_.random.nextInt(16), p_f_1_.random.nextInt(8) - p_f_1_.random.nextInt(8), p_f_1_.random.nextInt(16) - p_f_1_.random.nextInt(16));

                if (p_f_1_.getType(blockposition).getBlock().material == Material.AIR)
                {
                    org.bukkit.block.Block block = p_f_1_.getWorld().getBlockAt(p_f_2_.getX(), p_f_2_.getY(), p_f_2_.getZ());
                    org.bukkit.block.Block block1 = p_f_1_.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                    BlockFromToEvent blockfromtoevent = new BlockFromToEvent(block, block1);
                    Bukkit.getPluginManager().callEvent(blockfromtoevent);

                    if (blockfromtoevent.isCancelled())
                    {
                        return;
                    }

                    blockposition = new BlockPosition(blockfromtoevent.getToBlock().getX(), blockfromtoevent.getToBlock().getY(), blockfromtoevent.getToBlock().getZ());

                    if (p_f_1_.isClientSide)
                    {
                        for (int j = 0; j < 128; ++j)
                        {
                            double d0 = p_f_1_.random.nextDouble();
                            float f = (p_f_1_.random.nextFloat() - 0.5F) * 0.2F;
                            float f1 = (p_f_1_.random.nextFloat() - 0.5F) * 0.2F;
                            float f2 = (p_f_1_.random.nextFloat() - 0.5F) * 0.2F;
                            double d1 = (double)blockposition.getX() + (double)(p_f_2_.getX() - blockposition.getX()) * d0 + (p_f_1_.random.nextDouble() - 0.5D) * 1.0D + 0.5D;
                            double d2 = (double)blockposition.getY() + (double)(p_f_2_.getY() - blockposition.getY()) * d0 + p_f_1_.random.nextDouble() * 1.0D - 0.5D;
                            double d3 = (double)blockposition.getZ() + (double)(p_f_2_.getZ() - blockposition.getZ()) * d0 + (p_f_1_.random.nextDouble() - 0.5D) * 1.0D + 0.5D;
                            p_f_1_.addParticle(EnumParticle.PORTAL, d1, d2, d3, (double)f, (double)f1, (double)f2, new int[0]);
                        }
                    }
                    else
                    {
                        p_f_1_.setTypeAndData(blockposition, iblockdata, 2);
                        p_f_1_.setAir(p_f_2_);
                    }

                    return;
                }
            }
        }
    }

    public int a(World p_a_1_)
    {
        return 5;
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }
}
