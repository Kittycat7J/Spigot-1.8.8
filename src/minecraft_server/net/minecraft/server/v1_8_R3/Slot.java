package net.minecraft.server.v1_8_R3;

public class Slot
{
    public final int index;
    public final IInventory inventory;
    public int rawSlotIndex;
    public int f;
    public int g;

    public Slot(IInventory p_i404_1_, int p_i404_2_, int p_i404_3_, int p_i404_4_)
    {
        this.inventory = p_i404_1_;
        this.index = p_i404_2_;
        this.f = p_i404_3_;
        this.g = p_i404_4_;
    }

    public void a(ItemStack p_a_1_, ItemStack p_a_2_)
    {
        if (p_a_1_ != null && p_a_2_ != null && p_a_1_.getItem() == p_a_2_.getItem())
        {
            int i = p_a_2_.count - p_a_1_.count;

            if (i > 0)
            {
                this.a(p_a_1_, i);
            }
        }
    }

    protected void a(ItemStack p_a_1_, int p_a_2_)
    {
    }

    protected void c(ItemStack p_c_1_)
    {
    }

    public void a(EntityHuman p_a_1_, ItemStack p_a_2_)
    {
        this.f();
    }

    public boolean isAllowed(ItemStack p_isAllowed_1_)
    {
        return true;
    }

    public ItemStack getItem()
    {
        return this.inventory.getItem(this.index);
    }

    public boolean hasItem()
    {
        if (this.getItem() != null && this.getItem().count == 0)
        {
            this.set((ItemStack)null);
        }

        return this.getItem() != null;
    }

    public void set(ItemStack p_set_1_)
    {
        this.inventory.setItem(this.index, p_set_1_);
        this.f();
    }

    public void f()
    {
        this.inventory.update();
    }

    public int getMaxStackSize()
    {
        return this.inventory.getMaxStackSize();
    }

    public int getMaxStackSize(ItemStack p_getMaxStackSize_1_)
    {
        return this.getMaxStackSize();
    }

    public ItemStack a(int p_a_1_)
    {
        return this.inventory.splitStack(this.index, p_a_1_);
    }

    public boolean a(IInventory p_a_1_, int p_a_2_)
    {
        return p_a_1_ == this.inventory && p_a_2_ == this.index;
    }

    public boolean isAllowed(EntityHuman p_isAllowed_1_)
    {
        return true;
    }
}
