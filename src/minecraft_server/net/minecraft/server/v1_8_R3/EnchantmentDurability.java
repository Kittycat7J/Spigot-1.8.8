package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class EnchantmentDurability extends Enchantment
{
    protected EnchantmentDurability(int p_i527_1_, MinecraftKey p_i527_2_, int p_i527_3_)
    {
        super(p_i527_1_, p_i527_2_, p_i527_3_, EnchantmentSlotType.BREAKABLE);
        this.c("durability");
    }

    public int a(int p_a_1_)
    {
        return 5 + (p_a_1_ - 1) * 8;
    }

    public int b(int p_b_1_)
    {
        return super.a(p_b_1_) + 50;
    }

    public int getMaxLevel()
    {
        return 3;
    }

    public boolean canEnchant(ItemStack p_canEnchant_1_)
    {
        return p_canEnchant_1_.e() ? true : super.canEnchant(p_canEnchant_1_);
    }

    public static boolean a(ItemStack p_a_0_, int p_a_1_, Random p_a_2_)
    {
        return p_a_0_.getItem() instanceof ItemArmor && p_a_2_.nextFloat() < 0.6F ? false : p_a_2_.nextInt(p_a_1_ + 1) > 0;
    }
}
