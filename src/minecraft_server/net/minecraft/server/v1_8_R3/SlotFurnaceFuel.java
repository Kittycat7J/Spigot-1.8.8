package net.minecraft.server.v1_8_R3;

public class SlotFurnaceFuel extends Slot
{
    public SlotFurnaceFuel(IInventory p_i1250_1_, int p_i1250_2_, int p_i1250_3_, int p_i1250_4_)
    {
        super(p_i1250_1_, p_i1250_2_, p_i1250_3_, p_i1250_4_);
    }

    public boolean isAllowed(ItemStack p_isAllowed_1_)
    {
        return TileEntityFurnace.isFuel(p_isAllowed_1_) || c_(p_isAllowed_1_);
    }

    public int getMaxStackSize(ItemStack p_getMaxStackSize_1_)
    {
        return c_(p_getMaxStackSize_1_) ? 1 : super.getMaxStackSize(p_getMaxStackSize_1_);
    }

    public static boolean c_(ItemStack p_c__0_)
    {
        return p_c__0_ != null && p_c__0_.getItem() != null && p_c__0_.getItem() == Items.BUCKET;
    }
}
