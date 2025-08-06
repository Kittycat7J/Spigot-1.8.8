package net.minecraft.server.v1_8_R3;

class ContainerPlayer$2 extends Slot
{
    ContainerPlayer$2(ContainerPlayer p_i1251_1_, IInventory p_i1251_2_, int p_i1251_3_, int p_i1251_4_, int p_i1251_5_, int p_i1251_6_)
    {
        super(p_i1251_2_, p_i1251_3_, p_i1251_4_, p_i1251_5_);
        this.b = p_i1251_1_;
        this.a = p_i1251_6_;
    }

    public int getMaxStackSize()
    {
        return 1;
    }

    public boolean isAllowed(ItemStack p_isAllowed_1_)
    {
        return p_isAllowed_1_ == null ? false : (p_isAllowed_1_.getItem() instanceof ItemArmor ? ((ItemArmor)p_isAllowed_1_.getItem()).b == this.a : (p_isAllowed_1_.getItem() != Item.getItemOf(Blocks.PUMPKIN) && p_isAllowed_1_.getItem() != Items.SKULL ? false : this.a == 0));
    }
}
