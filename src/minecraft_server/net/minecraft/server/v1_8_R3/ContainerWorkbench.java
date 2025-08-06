package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerWorkbench extends Container
{
    public InventoryCrafting craftInventory;
    public IInventory resultInventory = new InventoryCraftResult();
    private World g;
    private BlockPosition h;
    private CraftInventoryView bukkitEntity = null;
    private PlayerInventory player;

    public ContainerWorkbench(PlayerInventory p_i297_1_, World p_i297_2_, BlockPosition p_i297_3_)
    {
        this.craftInventory = new InventoryCrafting(this, 3, 3, p_i297_1_.player);
        this.craftInventory.resultInventory = this.resultInventory;
        this.player = p_i297_1_;
        this.g = p_i297_2_;
        this.h = p_i297_3_;
        this.a((Slot)(new SlotResult(p_i297_1_.player, this.craftInventory, this.resultInventory, 0, 124, 35)));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.a((Slot)(new Slot(this.craftInventory, j + i * 3, 30 + j * 18, 17 + i * 18)));
            }
        }

        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.a((Slot)(new Slot(p_i297_1_, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18)));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.a((Slot)(new Slot(p_i297_1_, l, 8 + l * 18, 142)));
        }

        this.a((IInventory)this.craftInventory);
    }

    public void a(IInventory p_a_1_)
    {
        CraftingManager.getInstance().lastCraftView = this.getBukkitView();
        ItemStack itemstack = CraftingManager.getInstance().craft(this.craftInventory, this.g);
        this.resultInventory.setItem(0, itemstack);

        if (super.listeners.size() >= 1)
        {
            if (itemstack == null || itemstack.getItem() != Items.FILLED_MAP)
            {
                EntityPlayer entityplayer = (EntityPlayer)super.listeners.get(0);
                entityplayer.playerConnection.sendPacket(new PacketPlayOutSetSlot(entityplayer.activeContainer.windowId, 0, itemstack));
            }
        }
    }

    public void b(EntityHuman p_b_1_)
    {
        super.b(p_b_1_);

        if (!this.g.isClientSide)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);

                if (itemstack != null)
                {
                    p_b_1_.drop(itemstack, false);
                }
            }
        }
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return !this.checkReachable ? true : (this.g.getType(this.h).getBlock() != Blocks.CRAFTING_TABLE ? false : p_a_1_.e((double)this.h.getX() + 0.5D, (double)this.h.getY() + 0.5D, (double)this.h.getZ() + 0.5D) <= 64.0D);
    }

    public ItemStack b(EntityHuman p_b_1_, int p_b_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.c.get(p_b_2_);

        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();

            if (p_b_2_ == 0)
            {
                if (!this.a(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            }
            else if (p_b_2_ >= 10 && p_b_2_ < 37)
            {
                if (!this.a(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (p_b_2_ >= 37 && p_b_2_ < 46)
            {
                if (!this.a(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.a(itemstack1, 10, 46, false))
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

    public boolean a(ItemStack p_a_1_, Slot p_a_2_)
    {
        return p_a_2_.inventory != this.resultInventory && super.a(p_a_1_, p_a_2_);
    }

    public CraftInventoryView getBukkitView()
    {
        if (this.bukkitEntity != null)
        {
            return this.bukkitEntity;
        }
        else
        {
            CraftInventoryCrafting craftinventorycrafting = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
            this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventorycrafting, this);
            return this.bukkitEntity;
        }
    }
}
