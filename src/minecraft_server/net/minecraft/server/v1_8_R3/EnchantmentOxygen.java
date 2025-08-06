package net.minecraft.server.v1_8_R3;

public class EnchantmentOxygen extends Enchantment
{
    public EnchantmentOxygen(int p_i535_1_, MinecraftKey p_i535_2_, int p_i535_3_)
    {
        super(p_i535_1_, p_i535_2_, p_i535_3_, EnchantmentSlotType.ARMOR_HEAD);
        this.c("oxygen");
    }

    public int a(int p_a_1_)
    {
        return 10 * p_a_1_;
    }

    public int b(int p_b_1_)
    {
        return this.a(p_b_1_) + 30;
    }

    public int getMaxLevel()
    {
        return 3;
    }
}
