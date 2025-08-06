package net.minecraft.server.v1_8_R3;

public class EnchantmentArrowKnockback extends Enchantment
{
    public EnchantmentArrowKnockback(int p_i525_1_, MinecraftKey p_i525_2_, int p_i525_3_)
    {
        super(p_i525_1_, p_i525_2_, p_i525_3_, EnchantmentSlotType.BOW);
        this.c("arrowKnockback");
    }

    public int a(int p_a_1_)
    {
        return 12 + (p_a_1_ - 1) * 20;
    }

    public int b(int p_b_1_)
    {
        return this.a(p_b_1_) + 25;
    }

    public int getMaxLevel()
    {
        return 2;
    }
}
