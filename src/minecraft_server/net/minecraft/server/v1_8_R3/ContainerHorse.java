package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryHorse;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.inventory.InventoryView;

public class ContainerHorse extends Container
{
    private IInventory a;
    private EntityHorse f;
    CraftInventoryView bukkitEntity;
    PlayerInventory player;

    public InventoryView getBukkitView()
    {
        if (this.bukkitEntity != null)
        {
            return this.bukkitEntity;
        }
        else
        {
            CraftInventory craftinventory = new CraftInventoryHorse(this.a);
            return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventory, this);
        }
    }

    public ContainerHorse(IInventory p_i227_1_, final IInventory p_i227_2_, final EntityHorse p_i227_3_, EntityHuman p_i227_4_)
    {
        this.player = (PlayerInventory)p_i227_1_;
        this.a = p_i227_2_;
        this.f = p_i227_3_;
        byte b0 = 3;
        p_i227_2_.startOpen(p_i227_4_);
        int i = (b0 - 4) * 18;
        this.a(new Slot(p_i227_2_, 0, 8, 18)
        {
            public boolean isAllowed(ItemStack p_isAllowed_1_)
            {
                return super.isAllowed(p_isAllowed_1_) && p_isAllowed_1_.getItem() == Items.SADDLE && !this.hasItem();
            }
        });
        this.a(new Slot(p_i227_2_, 1, 8, 36)
        {
            public boolean isAllowed(ItemStack p_isAllowed_1_)
            {
                return super.isAllowed(p_isAllowed_1_) && p_i227_3_.cO() && EntityHorse.a(p_isAllowed_1_.getItem());
            }
        });

        if (p_i227_3_.hasChest())
        {
            for (int j = 0; j < b0; ++j)
            {
                for (int k = 0; k < 5; ++k)
                {
                    this.a(new Slot(p_i227_2_, 2 + k + j * 5, 80 + k * 18, 18 + j * 18));
                }
            }
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.a(new Slot(p_i227_1_, j1 + l * 9 + 9, 8 + j1 * 18, 102 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.a(new Slot(p_i227_1_, i1, 8 + i1 * 18, 160 + i));
        }
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return this.a.a(p_a_1_) && this.f.isAlive() && this.f.g(p_a_1_) < 8.0F;
    }

    public ItemStack b(EntityHuman p_b_1_, int p_b_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.c.get(p_b_2_);

        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();

            if (p_b_2_ < this.a.getSize())
            {
                if (!this.a(itemstack1, this.a.getSize(), this.c.size(), true))
                {
                    return null;
                }
            }
            else if (this.getSlot(1).isAllowed(itemstack1) && !this.getSlot(1).hasItem())
            {
                if (!this.a(itemstack1, 1, 2, false))
                {
                    return null;
                }
            }
            else if (this.getSlot(0).isAllowed(itemstack1))
            {
                if (!this.a(itemstack1, 0, 1, false))
                {
                    return null;
                }
            }
            else if (this.a.getSize() <= 2 || !this.a(itemstack1, 2, this.a.getSize(), false))
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
        }

        return itemstack;
    }

    public void b(EntityHuman p_b_1_)
    {
        super.b(p_b_1_);
        this.a.closeContainer(p_b_1_);
    }
}
