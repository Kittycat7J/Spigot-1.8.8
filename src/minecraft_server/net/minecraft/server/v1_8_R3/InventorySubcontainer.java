package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventorySubcontainer implements IInventory
{
    private String a;
    private int b;
    public ItemStack[] items;
    private List<IInventoryListener> d;
    private boolean e;
    public List<HumanEntity> transaction;
    private int maxStack;
    protected InventoryHolder bukkitOwner;

    public ItemStack[] getContents()
    {
        return this.items;
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
        return this.bukkitOwner;
    }

    public InventorySubcontainer(String p_i385_1_, boolean p_i385_2_, int p_i385_3_)
    {
        this(p_i385_1_, p_i385_2_, p_i385_3_, (InventoryHolder)null);
    }

    public InventorySubcontainer(String p_i386_1_, boolean p_i386_2_, int p_i386_3_, InventoryHolder p_i386_4_)
    {
        this.transaction = new ArrayList();
        this.maxStack = 64;
        this.bukkitOwner = p_i386_4_;
        this.a = p_i386_1_;
        this.e = p_i386_2_;
        this.b = p_i386_3_;
        this.items = new ItemStack[p_i386_3_];
    }

    public void a(IInventoryListener p_a_1_)
    {
        if (this.d == null)
        {
            this.d = Lists.<IInventoryListener>newArrayList();
        }

        this.d.add(p_a_1_);
    }

    public void b(IInventoryListener p_b_1_)
    {
        this.d.remove(p_b_1_);
    }

    public ItemStack getItem(int p_getItem_1_)
    {
        return p_getItem_1_ >= 0 && p_getItem_1_ < this.items.length ? this.items[p_getItem_1_] : null;
    }

    public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_)
    {
        if (this.items[p_splitStack_1_] != null)
        {
            if (this.items[p_splitStack_1_].count <= p_splitStack_2_)
            {
                ItemStack itemstack1 = this.items[p_splitStack_1_];
                this.items[p_splitStack_1_] = null;
                this.update();
                return itemstack1;
            }
            else
            {
                ItemStack itemstack = this.items[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);

                if (this.items[p_splitStack_1_].count == 0)
                {
                    this.items[p_splitStack_1_] = null;
                }

                this.update();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack a(ItemStack p_a_1_)
    {
        ItemStack itemstack = p_a_1_.cloneItemStack();

        for (int i = 0; i < this.b; ++i)
        {
            ItemStack itemstack1 = this.getItem(i);

            if (itemstack1 == null)
            {
                this.setItem(i, itemstack);
                this.update();
                return null;
            }

            if (ItemStack.c(itemstack1, itemstack))
            {
                int j = Math.min(this.getMaxStackSize(), itemstack1.getMaxStackSize());
                int k = Math.min(itemstack.count, j - itemstack1.count);

                if (k > 0)
                {
                    itemstack1.count += k;
                    itemstack.count -= k;

                    if (itemstack.count <= 0)
                    {
                        this.update();
                        return null;
                    }
                }
            }
        }

        if (itemstack.count != p_a_1_.count)
        {
            this.update();
        }

        return itemstack;
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

    public void setItem(int p_setItem_1_, ItemStack p_setItem_2_)
    {
        this.items[p_setItem_1_] = p_setItem_2_;

        if (p_setItem_2_ != null && p_setItem_2_.count > this.getMaxStackSize())
        {
            p_setItem_2_.count = this.getMaxStackSize();
        }

        this.update();
    }

    public int getSize()
    {
        return this.b;
    }

    public String getName()
    {
        return this.a;
    }

    public boolean hasCustomName()
    {
        return this.e;
    }

    public void a(String p_a_1_)
    {
        this.e = true;
        this.a = p_a_1_;
    }

    public IChatBaseComponent getScoreboardDisplayName()
    {
        return (IChatBaseComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }

    public int getMaxStackSize()
    {
        return 64;
    }

    public void update()
    {
        if (this.d != null)
        {
            for (int i = 0; i < this.d.size(); ++i)
            {
                ((IInventoryListener)this.d.get(i)).a(this);
            }
        }
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
}
