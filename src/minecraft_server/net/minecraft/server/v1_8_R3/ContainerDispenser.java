package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerDispenser extends Container
{
    public IInventory items;
    private CraftInventoryView bukkitEntity = null;
    private PlayerInventory player;

    public ContainerDispenser(IInventory p_i164_1_, IInventory p_i164_2_)
    {
        this.items = p_i164_2_;
        this.player = (PlayerInventory)p_i164_1_;

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.a(new Slot(p_i164_2_, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }

        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.a(new Slot(p_i164_1_, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.a(new Slot(p_i164_1_, l, 8 + l * 18, 142));
        }
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return !this.checkReachable ? true : this.items.a(p_a_1_);
    }

    public ItemStack b(EntityHuman p_b_1_, int p_b_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.c.get(p_b_2_);

        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();

            if (p_b_2_ < 9)
            {
                if (!this.a(itemstack1, 9, 45, true))
                {
                    return null;
                }
            }
            else if (!this.a(itemstack1, 0, 9, false))
            {
                return null;
            }

            if (itemstack1.count == 0)
            {
                slot.set((ItemStack)null);
            }
            else
            {
                slot.f();
            }

            if (itemstack1.count == itemstack.count)
            {
                return null;
            }

            slot.a(p_b_1_, itemstack1);
        }

        return itemstack;
    }

    public CraftInventoryView getBukkitView()
    {
        if (this.bukkitEntity != null)
        {
            return this.bukkitEntity;
        }
        else
        {
            CraftInventory craftinventory = new CraftInventory(this.items);
            this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventory, this);
            return this.bukkitEntity;
        }
    }
}
