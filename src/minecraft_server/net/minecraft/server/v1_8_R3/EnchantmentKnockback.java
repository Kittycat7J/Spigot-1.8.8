package net.minecraft.server.v1_8_R3;

public class EnchantmentKnockback extends Enchantment
{
    protected EnchantmentKnockback(int p_i533_1_, MinecraftKey p_i533_2_, int p_i533_3_)
    {
        super(p_i533_1_, p_i533_2_, p_i533_3_, EnchantmentSlotType.WEAPON);
        this.c("knockback");
    }

    public int a(int p_a_1_)
    {
        return 5 + 20 * (p_a_1_ - 1);
    }

    public int b(int p_b_1_)
    {
        return super.a(p_b_1_) + 50;
    }

    public int getMaxLevel()
    {
        return 2;
    }
}
