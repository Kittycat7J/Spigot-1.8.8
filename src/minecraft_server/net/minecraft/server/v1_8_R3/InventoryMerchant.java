package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryMerchant implements IInventory
{
    private final IMerchant merchant;
    private ItemStack[] itemsInSlots = new ItemStack[3];
    private final EntityHuman player;
    private MerchantRecipe recipe;
    private int e;
    public List<HumanEntity> transaction = new ArrayList();
    private int maxStack = 64;

    public ItemStack[] getContents()
    {
        return this.itemsInSlots;
    }

    public void onOpen(CraftHumanEntity p_onOpen_1_)
    {
        this.transaction.add(p_onOpen_1_);
    }

    public void onClose(CraftHumanEntity p_onClose_1_)
    {
        this.transaction.remove(p_onClose_1_);
    }

    public List<HumanEntity> getViewers()
    {
        return this.transaction;
    }

    public void setMaxStackSize(int p_setMaxStackSize_1_)
    {
        this.maxStack = p_setMaxStackSize_1_;
    }

    public InventoryHolder getOwner()
    {
        return (CraftVillager)((EntityVillager)this.merchant).getBukkitEntity();
    }

    public InventoryMerchant(EntityHuman p_i371_1_, IMerchant p_i371_2_)
    {
        this.player = p_i371_1_;
        this.merchant = p_i371_2_;
    }

    public int getSize()
    {
        return this.itemsInSlots.length;
    }

    public ItemStack getItem(int p_getItem_1_)
    {
        return this.itemsInSlots[p_getItem_1_];
    }

    public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_)
    {
        if (this.itemsInSlots[p_splitStack_1_] != null)
        {
            if (p_splitStack_1_ == 2)
            {
                ItemStack itemstack2 = this.itemsInSlots[p_splitStack_1_];
                this.itemsInSlots[p_splitStack_1_] = null;
                return itemstack2;
            }
            else if (this.itemsInSlots[p_splitStack_1_].count <= p_splitStack_2_)
            {
                ItemStack itemstack1 = this.itemsInSlots[p_splitStack_1_];
                this.itemsInSlots[p_splitStack_1_] = null;

                if (this.e(p_splitStack_1_))
                {
                    this.h();
                }

                return itemstack1;
            }
            else
            {
                ItemStack itemstack = this.itemsInSlots[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);

                if (this.itemsInSlots[p_splitStack_1_].count == 0)
                {
                    this.itemsInSlots[p_splitStack_1_] = null;
                }

                if (this.e(p_splitStack_1_))
                {
                    this.h();
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    private boolean e(int p_e_1_)
    {
        return p_e_1_ == 0 || p_e_1_ == 1;
    }

    public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_)
    {
        if (this.itemsInSlots[p_splitWithoutUpdate_1_] != null)
        {
            ItemStack itemstack = this.itemsInSlots[p_splitWithoutUpdate_1_];
            this.itemsInSlots[p_splitWithoutUpdate_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void setItem(int p_setItem_1_, ItemStack p_setItem_2_)
    {
        this.itemsInSlots[p_setItem_1_] = p_setItem_2_;

        if (p_setItem_2_ != null && p_setItem_2_.count > this.getMaxStackSize())
        {
            p_setItem_2_.count = this.getMaxStackSize();
        }

        if (this.e(p_setItem_1_))
        {
            this.h();
        }
    }

    public String getName()
    {
        return "mob.villager";
    }

    public boolean hasCustomName()
    {
        return false;
    }

    public IChatBaseComponent getScoreboardDisplayName()
    {
        return (IChatBaseComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }

    public int getMaxStackSize()
    {
        return this.maxStack;
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return this.merchant.v_() == p_a_1_;
    }

    public void startOpen(EntityHuman p_startOpen_1_)
    {
    }

    public void closeContainer(EntityHuman p_closeContainer_1_)
    {
    }

    public boolean b(int p_b_1_, ItemStack p_b_2_)
    {
        return true;
    }

    public void update()
    {
        this.h();
    }

    public void h()
    {
        this.recipe = null;
        ItemStack itemstack = this.itemsInSlots[0];
        ItemStack itemstack1 = this.itemsInSlots[1];

        if (itemstack == null)
        {
            itemstack = itemstack1;
            itemstack1 = null;
        }

        if (itemstack == null)
        {
            this.setItem(2, (ItemStack)null);
        }
        else
        {
            MerchantRecipeList merchantrecipelist = this.merchant.getOffers(this.player);

            if (merchantrecipelist != null)
            {
                MerchantRecipe merchantrecipe = merchantrecipelist.a(itemstack, itemstack1, this.e);

                if (merchantrecipe != null && !merchantrecipe.h())
                {
                    this.recipe = merchantrecipe;
                    this.setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
                }
                else if (itemstack1 != null)
                {
                    merchantrecipe = merchantrecipelist.a(itemstack1, itemstack, this.e);

                    if (merchantrecipe != null && !merchantrecipe.h())
                    {
                        this.recipe = merchantrecipe;
                        this.setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
                    }
                    else
                    {
                        this.setItem(2, (ItemStack)null);
                    }
                }
                else
                {
                    this.setItem(2, (ItemStack)null);
                }
            }
        }

        this.merchant.a_(this.getItem(2));
    }

    public MerchantRecipe getRecipe()
    {
        return this.recipe;
    }

    public void d(int p_d_1_)
    {
        this.e = p_d_1_;
        this.h();
    }

    public int getProperty(int p_getProperty_1_)
    {
        return 0;
    }

    public void b(int p_b_1_, int p_b_2_)
    {
    }

    public int g()
    {
        return 0;
    }

    public void l()
    {
        for (int i = 0; i < this.itemsInSlots.length; ++i)
        {
            this.itemsInSlots[i] = null;
        }
    }
}
