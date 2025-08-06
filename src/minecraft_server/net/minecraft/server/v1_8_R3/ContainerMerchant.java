package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryMerchant;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerMerchant extends Container
{
    private IMerchant merchant;
    private InventoryMerchant f;
    private final World g;
    private CraftInventoryView bukkitEntity = null;
    private PlayerInventory player;

    public CraftInventoryView getBukkitView()
    {
        if (this.bukkitEntity == null)
        {
            this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), new CraftInventoryMerchant(this.f), this);
        }

        return this.bukkitEntity;
    }

    public ContainerMerchant(PlayerInventory p_i195_1_, IMerchant p_i195_2_, World p_i195_3_)
    {
        this.merchant = p_i195_2_;
        this.g = p_i195_3_;
        this.f = new InventoryMerchant(p_i195_1_.player, p_i195_2_);
        this.a((Slot)(new Slot(this.f, 0, 36, 53)));
        this.a((Slot)(new Slot(this.f, 1, 62, 53)));
        this.a((Slot)(new SlotMerchantResult(p_i195_1_.player, p_i195_2_, this.f, 2, 120, 53)));
        this.player = p_i195_1_;

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.a((Slot)(new Slot(p_i195_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.a((Slot)(new Slot(p_i195_1_, k, 8 + k * 18, 142)));
        }
    }

    public InventoryMerchant e()
    {
        return this.f;
    }

    public void addSlotListener(ICrafting p_addSlotListener_1_)
    {
        super.addSlotListener(p_addSlotListener_1_);
    }

    public void b()
    {
        super.b();
    }

    public void a(IInventory p_a_1_)
    {
        this.f.h();
        super.a(p_a_1_);
    }

    public void d(int p_d_1_)
    {
        this.f.d(p_d_1_);
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return this.merchant.v_() == p_a_1_;
    }

    public ItemStack b(EntityHuman p_b_1_, int p_b_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.c.get(p_b_2_);

        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();

            if (p_b_2_ == 2)
            {
                if (!this.a(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            }
            else if (p_b_2_ != 0 && p_b_2_ != 1)
            {
                if (p_b_2_ >= 3 && p_b_2_ < 30)
                {
                    if (!this.a(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (p_b_2_ >= 30 && p_b_2_ < 39 && !this.a(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.a(itemstack1, 3, 39, false))
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

    public void b(EntityHuman p_b_1_)
    {
        super.b(p_b_1_);
        this.merchant.a_((EntityHuman)null);
        super.b(p_b_1_);

        if (!this.g.isClientSide)
        {
            ItemStack itemstack = this.f.splitWithoutUpdate(0);

            if (itemstack != null)
            {
                p_b_1_.drop(itemstack, false);
            }

            itemstack = this.f.splitWithoutUpdate(1);

            if (itemstack != null)
            {
                p_b_1_.drop(itemstack, false);
            }
        }
    }
}
