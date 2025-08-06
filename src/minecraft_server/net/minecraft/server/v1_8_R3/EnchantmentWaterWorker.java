package net.minecraft.server.v1_8_R3;

public class EnchantmentWaterWorker extends Enchantment
{
    public EnchantmentWaterWorker(int p_i539_1_, MinecraftKey p_i539_2_, int p_i539_3_)
    {
        super(p_i539_1_, p_i539_2_, p_i539_3_, EnchantmentSlotType.ARMOR_HEAD);
        this.c("waterWorker");
    }

    public int a(int p_a_1_)
    {
        return 1;
    }

    public int b(int p_b_1_)
    {
        return this.a(p_b_1_) + 40;
    }

    public int getMaxLevel()
    {
        return 1;
    }
}
