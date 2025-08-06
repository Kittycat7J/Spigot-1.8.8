package net.minecraft.server.v1_8_R3;

public class ItemMilkBucket extends Item
{
    public ItemMilkBucket()
    {
        this.c(1);
        this.a(CreativeModeTab.f);
    }

    public ItemStack b(ItemStack p_b_1_, World p_b_2_, EntityHuman p_b_3_)
    {
        if (!p_b_3_.abilities.canInstantlyBuild)
        {
            --p_b_1_.count;
        }

        if (!p_b_2_.isClientSide)
        {
            p_b_3_.removeAllEffects();
        }

        p_b_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
        return p_b_1_.count <= 0 ? new ItemStack(Items.BUCKET) : p_b_1_;
    }

    public int d(ItemStack p_d_1_)
    {
        return 32;
    }

    public EnumAnimation e(ItemStack p_e_1_)
    {
        return EnumAnimation.DRINK;
    }

    public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_)
    {
        p_a_3_.a(p_a_1_, this.d(p_a_1_));
        return p_a_1_;
    }
}
