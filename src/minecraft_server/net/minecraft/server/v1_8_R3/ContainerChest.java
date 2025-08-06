package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerChest extends Container
{
    private IInventory container;
    private int f;
    private CraftInventoryView bukkitEntity = null;
    private PlayerInventory player;

    public CraftInventoryView getBukkitView()
    {
        if (this.bukkitEntity != null)
        {
            return this.bukkitEntity;
        }
        else
        {
            CraftInventory craftinventory;

            if (this.container instanceof PlayerInventory)
            {
                craftinventory = new CraftInventoryPlayer((PlayerInventory)this.container);
            }
            else if (this.container instanceof InventoryLargeChest)
            {
                craftinventory = new CraftInventoryDoubleChest((InventoryLargeChest)this.container);
            }
            else
            {
                craftinventory = new CraftInventory(this.container);
            }

            this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventory, this);
            return this.bukkitEntity;
        }
    }

    public ContainerChest(IInventory p_i480_1_, IInventory p_i480_2_, EntityHuman p_i480_3_)
    {
        this.container = p_i480_2_;
        this.f = p_i480_2_.getSize() / 9;
        p_i480_2_.startOpen(p_i480_3_);
        int i = (this.f - 4) * 18;
        this.player = (PlayerInventory)p_i480_1_;

        for (int j = 0; j < this.f; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.a(new Slot(p_i480_2_, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.a(new Slot(p_i480_1_, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.a(new Slot(p_i480_1_, i1, 8 + i1 * 18, 161 + i));
        }
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return !this.checkReachable ? true : this.container.a(p_a_1_);
    }

    public ItemStack b(EntityHuman p_b_1_, int p_b_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.c.get(p_b_2_);

        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();

            if (p_b_2_ < this.f * 9)
            {
                if (!this.a(itemstack1, this.f * 9, this.c.size(), true))
                {
                    return null;
                }
            }
            else if (!this.a(itemstack1, 0, this.f * 9, false))
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
        this.container.closeContainer(p_b_1_);
    }

    public IInventory e()
    {
        return this.container;
    }
}
