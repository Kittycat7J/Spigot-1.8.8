package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCrafting implements IInventory
{
    private final ItemStack[] items;
    private final int b;
    private final int c;
    private final Container d;
    public List<HumanEntity> transaction;
    public IRecipe currentRecipe;
    public IInventory resultInventory;
    private EntityHuman owner;
    private int maxStack;

    public ItemStack[] getContents()
    {
        return this.items;
    }

    public void onOpen(CraftHumanEntity p_onOpen_1_)
    {
        this.transaction.add(p_onOpen_1_);
    }

    public InventoryType getInvType()
    {
        return this.items.length == 4 ? InventoryType.CRAFTING : InventoryType.WORKBENCH;
    }

    public void onClose(CraftHumanEntity p_onClose_1_)
    {
        this.transaction.remove(p_onClose_1_);
    }

    public List<HumanEntity> getViewers()
    {
        return this.transaction;
    }

    public InventoryHolder getOwner()
    {
        return this.owner == null ? null : this.owner.getBukkitEntity();
    }

    public void setMaxStackSize(int p_setMaxStackSize_1_)
    {
        this.maxStack = p_setMaxStackSize_1_;
        this.resultInventory.setMaxStackSize(p_setMaxStackSize_1_);
    }

    public InventoryCrafting(Container p_i375_1_, int p_i375_2_, int p_i375_3_, EntityHuman p_i375_4_)
    {
        this(p_i375_1_, p_i375_2_, p_i375_3_);
        this.owner = p_i375_4_;
    }

    public InventoryCrafting(Container p_i376_1_, int p_i376_2_, int p_i376_3_)
    {
        this.transaction = new ArrayList();
        this.maxStack = 64;
        int i = p_i376_2_ * p_i376_3_;
        this.items = new ItemStack[i];
        this.d = p_i376_1_;
        this.b = p_i376_2_;
        this.c = p_i376_3_;
    }

    public int getSize()
    {
        return this.items.length;
    }

    public ItemStack getItem(int p_getItem_1_)
    {
        return p_getItem_1_ >= this.getSize() ? null : this.items[p_getItem_1_];
    }

    public ItemStack c(int p_c_1_, int p_c_2_)
    {
        return p_c_1_ >= 0 && p_c_1_ < this.b && p_c_2_ >= 0 && p_c_2_ <= this.c ? this.getItem(p_c_1_ + p_c_2_ * this.b) : null;
    }

    public String getName()
    {
        return "container.crafting";
    }

    public boolean hasCustomName()
    {
        return false;
    }

    public IChatBaseComponent getScoreboardDisplayName()
    {
        return (IChatBaseComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }

    public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_)
    {
        if (this.items[p_splitWithoutUpdate_1_] != null)
        {
            ItemStack itemstack = this.items[p_splitWithoutUpdate_1_];
            this.items[p_splitWithoutUpdate_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_)
    {
        if (this.items[p_splitStack_1_] != null)
        {
            if (this.items[p_splitStack_1_].count <= p_splitStack_2_)
            {
                ItemStack itemstack1 = this.items[p_splitStack_1_];
                this.items[p_splitStack_1_] = null;
                this.d.a((IInventory)this);
                return itemstack1;
            }
            else
            {
                ItemStack itemstack = this.items[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);

                if (this.items[p_splitStack_1_].count == 0)
                {
                    this.items[p_splitStack_1_] = null;
                }

                this.d.a((IInventory)this);
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public void setItem(int p_setItem_1_, ItemStack p_setItem_2_)
    {
        this.items[p_setItem_1_] = p_setItem_2_;
        this.d.a((IInventory)this);
    }

    public int getMaxStackSize()
    {
        return 64;
    }

    public void update()
    {
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return true;
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
        for (int i = 0; i < this.items.length; ++i)
        {
            this.items[i] = null;
        }
    }

    public int h()
    {
        return this.c;
    }

    public int i()
    {
        return this.b;
    }
}
