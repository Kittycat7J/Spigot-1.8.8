package net.minecraft.server.v1_8_R3;

public class EnchantmentSilkTouch extends Enchantment
{
    protected EnchantmentSilkTouch(int p_i537_1_, MinecraftKey p_i537_2_, int p_i537_3_)
    {
        super(p_i537_1_, p_i537_2_, p_i537_3_, EnchantmentSlotType.DIGGER);
        this.c("untouching");
    }

    public int a(int p_a_1_)
    {
        return 15;
    }

    public int b(int p_b_1_)
    {
        return super.a(p_b_1_) + 50;
    }

    public int getMaxLevel()
    {
        return 1;
    }

    public boolean a(Enchantment p_a_1_)
    {
        return super.a(p_a_1_) && p_a_1_.id != LOOT_BONUS_BLOCKS.id;
    }

    public boolean canEnchant(ItemStack p_canEnchant_1_)
    {
        return p_canEnchant_1_.getItem() == Items.SHEARS ? true : super.canEnchant(p_canEnchant_1_);
    }
}
