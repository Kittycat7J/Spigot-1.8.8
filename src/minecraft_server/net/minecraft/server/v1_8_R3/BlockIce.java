package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockIce extends BlockHalfTransparent
{
    public BlockIce()
    {
        super(Material.ICE, false);
        this.frictionFactor = 0.98F;
        this.a(true);
        this.a(CreativeModeTab.b);
    }

    public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_)
    {
        p_a_2_.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
        p_a_2_.applyExhaustion(0.025F);

        if (this.I() && EnchantmentManager.hasSilkTouchEnchantment(p_a_2_))
        {
            ItemStack itemstack = this.i(p_a_4_);

            if (itemstack != null)
            {
                a(p_a_1_, p_a_3_, itemstack);
            }
        }
        else
        {
            if (p_a_1_.worldProvider.n())
            {
                p_a_1_.setAir(p_a_3_);
                return;
            }

            int i = EnchantmentManager.getBonusBlockLootEnchantmentLevel(p_a_2_);
            this.b(p_a_1_, p_a_3_, p_a_4_, i);
            Material material = p_a_1_.getType(p_a_3_.down()).getBlock().getMaterial();

            if (material.isSolid() || material.isLiquid())
            {
                p_a_1_.setTypeUpdate(p_a_3_, Blocks.FLOWING_WATER.getBlockData());
            }
        }
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (p_b_1_.b(EnumSkyBlock.BLOCK, p_b_2_) > 11 - this.p())
        {
            if (CraftEventFactory.callBlockFadeEvent(p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()), (Block)(p_b_1_.worldProvider.n() ? Blocks.AIR : Blocks.WATER)).isCancelled())
            {
                return;
            }

            if (p_b_1_.worldProvider.n())
            {
                p_b_1_.setAir(p_b_2_);
            }
            else
            {
                this.b(p_b_1_, p_b_2_, p_b_1_.getType(p_b_2_), 0);
                p_b_1_.setTypeUpdate(p_b_2_, Blocks.WATER.getBlockData());
            }
        }
    }

    public int k()
    {
        return 0;
    }
}
