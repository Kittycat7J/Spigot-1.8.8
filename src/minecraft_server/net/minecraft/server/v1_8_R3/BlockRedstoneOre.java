package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockRedstoneOre extends Block
{
    private final boolean a;

    public BlockRedstoneOre(boolean p_i150_1_)
    {
        super(Material.STONE);

        if (p_i150_1_)
        {
            this.a(true);
        }

        this.a = p_i150_1_;
    }

    public int a(World p_a_1_)
    {
        return 30;
    }

    public void attack(World p_attack_1_, BlockPosition p_attack_2_, EntityHuman p_attack_3_)
    {
        this.e(p_attack_1_, p_attack_2_, p_attack_3_);
        super.attack(p_attack_1_, p_attack_2_, p_attack_3_);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, Entity p_a_3_)
    {
        if (p_a_3_ instanceof EntityHuman)
        {
            PlayerInteractEvent playerinteractevent = CraftEventFactory.callPlayerInteractEvent((EntityHuman)p_a_3_, Action.PHYSICAL, p_a_2_, (EnumDirection)null, (ItemStack)null);

            if (!playerinteractevent.isCancelled())
            {
                this.e(p_a_1_, p_a_2_, p_a_3_);
                super.a(p_a_1_, p_a_2_, p_a_3_);
            }
        }
        else
        {
            EntityInteractEvent entityinteractevent = new EntityInteractEvent(p_a_3_.getBukkitEntity(), p_a_1_.getWorld().getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ()));
            p_a_1_.getServer().getPluginManager().callEvent(entityinteractevent);

            if (!entityinteractevent.isCancelled())
            {
                this.e(p_a_1_, p_a_2_, p_a_3_);
                super.a(p_a_1_, p_a_2_, p_a_3_);
            }
        }
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        this.e(p_interact_1_, p_interact_2_, p_interact_4_);
        return super.interact(p_interact_1_, p_interact_2_, p_interact_3_, p_interact_4_, p_interact_5_, p_interact_6_, p_interact_7_, p_interact_8_);
    }

    private void e(World p_e_1_, BlockPosition p_e_2_, Entity p_e_3_)
    {
        this.f(p_e_1_, p_e_2_);

        if (this == Blocks.REDSTONE_ORE)
        {
            if (CraftEventFactory.callEntityChangeBlockEvent(p_e_3_, p_e_2_.getX(), p_e_2_.getY(), p_e_2_.getZ(), Blocks.LIT_REDSTONE_ORE, 0).isCancelled())
            {
                return;
            }

            p_e_1_.setTypeUpdate(p_e_2_, Blocks.LIT_REDSTONE_ORE.getBlockData());
        }
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (this == Blocks.LIT_REDSTONE_ORE)
        {
            if (CraftEventFactory.callBlockFadeEvent(p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()), Blocks.REDSTONE_ORE).isCancelled())
            {
                return;
            }

            p_b_1_.setTypeUpdate(p_b_2_, Blocks.REDSTONE_ORE.getBlockData());
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.REDSTONE;
    }

    public int getDropCount(int p_getDropCount_1_, Random p_getDropCount_2_)
    {
        return this.a(p_getDropCount_2_) + p_getDropCount_2_.nextInt(p_getDropCount_1_ + 1);
    }

    public int a(Random p_a_1_)
    {
        return 4 + p_a_1_.nextInt(2);
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
        super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, p_dropNaturally_5_);
    }

    public int getExpDrop(World p_getExpDrop_1_, IBlockData p_getExpDrop_2_, int p_getExpDrop_3_)
    {
        if (this.getDropType(p_getExpDrop_2_, p_getExpDrop_1_.random, p_getExpDrop_3_) != Item.getItemOf(this))
        {
            int i = 1 + p_getExpDrop_1_.random.nextInt(5);
            return i;
        }
        else
        {
            return 0;
        }
    }

    private void f(World p_f_1_, BlockPosition p_f_2_)
    {
        Random random = p_f_1_.random;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)p_f_2_.getX() + random.nextFloat());
            double d2 = (double)((float)p_f_2_.getY() + random.nextFloat());
            double d3 = (double)((float)p_f_2_.getZ() + random.nextFloat());

            if (i == 0 && !p_f_1_.getType(p_f_2_.up()).getBlock().c())
            {
                d2 = (double)p_f_2_.getY() + d0 + 1.0D;
            }

            if (i == 1 && !p_f_1_.getType(p_f_2_.down()).getBlock().c())
            {
                d2 = (double)p_f_2_.getY() - d0;
            }

            if (i == 2 && !p_f_1_.getType(p_f_2_.south()).getBlock().c())
            {
                d3 = (double)p_f_2_.getZ() + d0 + 1.0D;
            }

            if (i == 3 && !p_f_1_.getType(p_f_2_.north()).getBlock().c())
            {
                d3 = (double)p_f_2_.getZ() - d0;
            }

            if (i == 4 && !p_f_1_.getType(p_f_2_.east()).getBlock().c())
            {
                d1 = (double)p_f_2_.getX() + d0 + 1.0D;
            }

            if (i == 5 && !p_f_1_.getType(p_f_2_.west()).getBlock().c())
            {
                d1 = (double)p_f_2_.getX() - d0;
            }

            if (d1 < (double)p_f_2_.getX() || d1 > (double)(p_f_2_.getX() + 1) || d2 < 0.0D || d2 > (double)(p_f_2_.getY() + 1) || d3 < (double)p_f_2_.getZ() || d3 > (double)(p_f_2_.getZ() + 1))
            {
                p_f_1_.addParticle(EnumParticle.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }

    protected ItemStack i(IBlockData p_i_1_)
    {
        return new ItemStack(Blocks.REDSTONE_ORE);
    }
}
