package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public abstract class EntityMinecartContainer extends EntityMinecartAbstract implements ITileInventory
{
    private ItemStack[] items = new ItemStack[27];
    private boolean b = true;
    public List<HumanEntity> transaction = new ArrayList();
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
        org.bukkit.entity.Entity entity = this.getBukkitEntity();
        return entity instanceof InventoryHolder ? (InventoryHolder)entity : null;
    }

    public void setMaxStackSize(int p_setMaxStackSize_1_)
    {
        this.maxStack = p_setMaxStackSize_1_;
    }

    public EntityMinecartContainer(World p_i241_1_)
    {
        super(p_i241_1_);
    }

    public EntityMinecartContainer(World p_i242_1_, double p_i242_2_, double p_i242_4_, double p_i242_6_)
    {
        super(p_i242_1_, p_i242_2_, p_i242_4_, p_i242_6_);
    }

    public void a(DamageSource p_a_1_)
    {
        super.a(p_a_1_);

        if (this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            InventoryUtils.dropEntity(this.world, this, this);
        }
    }

    public ItemStack getItem(int p_getItem_1_)
    {
        return this.items[p_getItem_1_];
    }

    public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_)
    {
        if (this.items[p_splitStack_1_] != null)
        {
            if (this.items[p_splitStack_1_].count <= p_splitStack_2_)
            {
                ItemStack itemstack1 = this.items[p_splitStack_1_];
                this.items[p_splitStack_1_] = null;
                return itemstack1;
            }
            else
            {
                ItemStack itemstack = this.items[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);

                if (this.items[p_splitStack_1_].count == 0)
                {
                    this.items[p_splitStack_1_] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
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
    }

    public void update()
    {
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return this.dead ? false : p_a_1_.h(this) <= 64.0D;
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

    public String getName()
    {
        return this.hasCustomName() ? this.getCustomName() : "container.minecart";
    }

    public int getMaxStackSize()
    {
        return this.maxStack;
    }

    public void c(int p_c_1_)
    {
        for (HumanEntity humanentity : new ArrayList(this.transaction))
        {
            humanentity.closeInventory();
        }

        this.b = false;
        super.c(p_c_1_);
    }

    public void die()
    {
        if (this.b)
        {
            InventoryUtils.dropEntity(this.world, this, this);
        }

        super.die();
    }

    protected void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.items.length; ++i)
        {
            if (this.items[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.items[i].save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }

        p_b_1_.set("Items", nbttaglist);
    }

    protected void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        NBTTagList nbttaglist = p_a_1_.getList("Items", 10);
        this.items = new ItemStack[this.getSize()];

        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < this.items.length)
            {
                this.items[j] = ItemStack.createStack(nbttagcompound);
            }
        }
    }

    public boolean e(EntityHuman p_e_1_)
    {
        if (!this.world.isClientSide)
        {
            p_e_1_.openContainer(this);
        }

        return true;
    }

    protected void o()
    {
        int i = 15 - Container.b((IInventory)this);
        float f = 0.98F + (float)i * 0.001F;
        this.motX *= (double)f;
        this.motY *= 0.0D;
        this.motZ *= (double)f;
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

    public boolean r_()
    {
        return false;
    }

    public void a(ChestLock p_a_1_)
    {
    }

    public ChestLock i()
    {
        return ChestLock.a;
    }

    public void l()
    {
        for (int i = 0; i < this.items.length; ++i)
        {
            this.items[i] = null;
        }
    }
}
