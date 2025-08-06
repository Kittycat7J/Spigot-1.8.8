package net.minecraft.server.v1_8_R3;

public class EnchantmentDigging extends Enchantment
{
    protected EnchantmentDigging(int p_i528_1_, MinecraftKey p_i528_2_, int p_i528_3_)
    {
        super(p_i528_1_, p_i528_2_, p_i528_3_, EnchantmentSlotType.DIGGER);
        this.c("digging");
    }

    public int a(int p_a_1_)
    {
        return 1 + 10 * (p_a_1_ - 1);
    }

    public int b(int p_b_1_)
    {
        return super.a(p_b_1_) + 50;
    }

    public int getMaxLevel()
    {
        return 5;
    }

    public boolean canEnchant(ItemStack p_canEnchant_1_)
    {
        return p_canEnchant_1_.getItem() == Items.SHEARS ? true : super.canEnchant(p_canEnchant_1_);
    }
}
