package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class InventoryEnderChest extends InventorySubcontainer
{
    private TileEntityEnderChest a;
    public List<HumanEntity> transaction = new ArrayList();
    public Player player;
    private int maxStack = 64;

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

    public InventoryHolder getOwner()
    {
        return this.player;
    }

    public void setMaxStackSize(int p_setMaxStackSize_1_)
    {
        this.maxStack = p_setMaxStackSize_1_;
    }

    public int getMaxStackSize()
    {
        return this.maxStack;
    }

    public InventoryEnderChest()
    {
        super("container.enderchest", false, 27);
    }

    public void a(TileEntityEnderChest p_a_1_)
    {
        this.a = p_a_1_;
    }

    public void a(NBTTagList p_a_1_)
    {
        for (int i = 0; i < this.getSize(); ++i)
        {
            this.setItem(i, (ItemStack)null);
        }

        for (int k = 0; k < p_a_1_.size(); ++k)
        {
            NBTTagCompound nbttagcompound = p_a_1_.get(k);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < this.getSize())
            {
                this.setItem(j, ItemStack.createStack(nbttagcompound));
            }
        }
    }

    public NBTTagList h()
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.getSize(); ++i)
        {
            ItemStack itemstack = this.getItem(i);

            if (itemstack != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                itemstack.save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }

        return nbttaglist;
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return this.a != null && !this.a.a(p_a_1_) ? false : super.a(p_a_1_);
    }

    public void startOpen(EntityHuman p_startOpen_1_)
    {
        if (this.a != null)
        {
            this.a.b();
        }

        super.startOpen(p_startOpen_1_);
    }

    public void closeContainer(EntityHuman p_closeContainer_1_)
    {
        if (this.a != null)
        {
            this.a.d();
        }

        super.closeContainer(p_closeContainer_1_);
        this.a = null;
    }
}
