package net.minecraft.server.v1_8_R3;

public class SlotMerchantResult extends Slot
{
    private final InventoryMerchant a;
    private EntityHuman b;
    private int c;
    private final IMerchant h;

    public SlotMerchantResult(EntityHuman p_i1252_1_, IMerchant p_i1252_2_, InventoryMerchant p_i1252_3_, int p_i1252_4_, int p_i1252_5_, int p_i1252_6_)
    {
        super(p_i1252_3_, p_i1252_4_, p_i1252_5_, p_i1252_6_);
        this.b = p_i1252_1_;
        this.h = p_i1252_2_;
        this.a = p_i1252_3_;
    }

    public boolean isAllowed(ItemStack p_isAllowed_1_)
    {
        return false;
    }

    public ItemStack a(int p_a_1_)
    {
        if (this.hasItem())
        {
            this.c += Math.min(p_a_1_, this.getItem().count);
        }

        return super.a(p_a_1_);
    }

    protected void a(ItemStack p_a_1_, int p_a_2_)
    {
        this.c += p_a_2_;
        this.c(p_a_1_);
    }

    protected void c(ItemStack p_c_1_)
    {
        p_c_1_.a(this.b.world, this.b, this.c);
        this.c = 0;
    }

    public void a(EntityHuman p_a_1_, ItemStack p_a_2_)
    {
        this.c(p_a_2_);
        MerchantRecipe merchantrecipe = this.a.getRecipe();

        if (merchantrecipe != null)
        {
            ItemStack itemstack = this.a.getItem(0);
            ItemStack itemstack1 = this.a.getItem(1);

            if (this.a(merchantrecipe, itemstack, itemstack1) || this.a(merchantrecipe, itemstack1, itemstack))
            {
                this.h.a(merchantrecipe);
                p_a_1_.b(StatisticList.G);

                if (itemstack != null && itemstack.count <= 0)
                {
                    itemstack = null;
                }

                if (itemstack1 != null && itemstack1.count <= 0)
                {
                    itemstack1 = null;
                }

                this.a.setItem(0, itemstack);
                this.a.setItem(1, itemstack1);
            }
        }
    }

    private boolean a(MerchantRecipe p_a_1_, ItemStack p_a_2_, ItemStack p_a_3_)
    {
        ItemStack itemstack = p_a_1_.getBuyItem1();
        ItemStack itemstack1 = p_a_1_.getBuyItem2();

        if (p_a_2_ != null && p_a_2_.getItem() == itemstack.getItem())
        {
            if (itemstack1 != null && p_a_3_ != null && itemstack1.getItem() == p_a_3_.getItem())
            {
                p_a_2_.count -= itemstack.count;
                p_a_3_.count -= itemstack1.count;
                return true;
            }

            if (itemstack1 == null && p_a_3_ == null)
            {
                p_a_2_.count -= itemstack.count;
                return true;
            }
        }

        return false;
    }
}
