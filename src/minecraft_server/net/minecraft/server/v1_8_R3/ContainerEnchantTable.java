package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryEnchanting;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class ContainerEnchantTable extends Container
{
    public InventorySubcontainer enchantSlots = new InventorySubcontainer("Enchant", true, 2)
    {
        public int getMaxStackSize()
        {
            return 64;
        }
        public void update()
        {
            super.update();
            ContainerEnchantTable.this.a((IInventory)this);
        }
    };
    private World world;
    private BlockPosition position;
    private Random k = new Random();
    public int f;
    public int[] costs = new int[3];
    public int[] h = new int[] { -1, -1, -1};
    private CraftInventoryView bukkitEntity = null;
    private Player player;

    public ContainerEnchantTable(PlayerInventory p_i165_1_, World p_i165_2_, BlockPosition p_i165_3_)
    {
        this.world = p_i165_2_;
        this.position = p_i165_3_;
        this.f = p_i165_1_.player.cj();
        this.a((Slot)(new Slot(this.enchantSlots, 0, 15, 47)
        {
            public boolean isAllowed(ItemStack p_isAllowed_1_)
            {
                return true;
            }
            public int getMaxStackSize()
            {
                return 1;
            }
        }));
        this.a((Slot)(new Slot(this.enchantSlots, 1, 35, 47)
        {
            public boolean isAllowed(ItemStack p_isAllowed_1_)
            {
                return p_isAllowed_1_.getItem() == Items.DYE && EnumColor.fromInvColorIndex(p_isAllowed_1_.getData()) == EnumColor.BLUE;
            }
        }));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.a((Slot)(new Slot(p_i165_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)));
            }
        }

        for (int kx = 0; kx < 9; ++kx)
        {
            this.a((Slot)(new Slot(p_i165_1_, kx, 8 + kx * 18, 142)));
        }

        this.player = (Player)p_i165_1_.player.getBukkitEntity();
    }

    public void addSlotListener(ICrafting p_addSlotListener_1_)
    {
        super.addSlotListener(p_addSlotListener_1_);
        p_addSlotListener_1_.setContainerData(this, 0, this.costs[0]);
        p_addSlotListener_1_.setContainerData(this, 1, this.costs[1]);
        p_addSlotListener_1_.setContainerData(this, 2, this.costs[2]);
        p_addSlotListener_1_.setContainerData(this, 3, this.f & -16);
        p_addSlotListener_1_.setContainerData(this, 4, this.h[0]);
        p_addSlotListener_1_.setContainerData(this, 5, this.h[1]);
        p_addSlotListener_1_.setContainerData(this, 6, this.h[2]);
    }

    public void b()
    {
        super.b();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.listeners.get(i);
            icrafting.setContainerData(this, 0, this.costs[0]);
            icrafting.setContainerData(this, 1, this.costs[1]);
            icrafting.setContainerData(this, 2, this.costs[2]);
            icrafting.setContainerData(this, 3, this.f & -16);
            icrafting.setContainerData(this, 4, this.h[0]);
            icrafting.setContainerData(this, 5, this.h[1]);
            icrafting.setContainerData(this, 6, this.h[2]);
        }
    }

    public void a(IInventory p_a_1_)
    {
        if (p_a_1_ == this.enchantSlots)
        {
            ItemStack itemstack = p_a_1_.getItem(0);

            if (itemstack != null)
            {
                if (!this.world.isClientSide)
                {
                    int i = 0;

                    for (int j = -1; j <= 1; ++j)
                    {
                        for (int k = -1; k <= 1; ++k)
                        {
                            if ((j != 0 || k != 0) && this.world.isEmpty(this.position.a(k, 0, j)) && this.world.isEmpty(this.position.a(k, 1, j)))
                            {
                                if (this.world.getType(this.position.a(k * 2, 0, j * 2)).getBlock() == Blocks.BOOKSHELF)
                                {
                                    ++i;
                                }

                                if (this.world.getType(this.position.a(k * 2, 1, j * 2)).getBlock() == Blocks.BOOKSHELF)
                                {
                                    ++i;
                                }

                                if (k != 0 && j != 0)
                                {
                                    if (this.world.getType(this.position.a(k * 2, 0, j)).getBlock() == Blocks.BOOKSHELF)
                                    {
                                        ++i;
                                    }

                                    if (this.world.getType(this.position.a(k * 2, 1, j)).getBlock() == Blocks.BOOKSHELF)
                                    {
                                        ++i;
                                    }

                                    if (this.world.getType(this.position.a(k, 0, j * 2)).getBlock() == Blocks.BOOKSHELF)
                                    {
                                        ++i;
                                    }

                                    if (this.world.getType(this.position.a(k, 1, j * 2)).getBlock() == Blocks.BOOKSHELF)
                                    {
                                        ++i;
                                    }
                                }
                            }
                        }
                    }

                    this.k.setSeed((long)this.f);

                    for (int i1 = 0; i1 < 3; ++i1)
                    {
                        this.costs[i1] = EnchantmentManager.a(this.k, i1, i, itemstack);
                        this.h[i1] = -1;

                        if (this.costs[i1] < i1 + 1)
                        {
                            this.costs[i1] = 0;
                        }
                    }

                    CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
                    PrepareItemEnchantEvent prepareitemenchantevent = new PrepareItemEnchantEvent(this.player, this.getBukkitView(), this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), craftitemstack, this.costs, i);
                    prepareitemenchantevent.setCancelled(!itemstack.v());
                    this.world.getServer().getPluginManager().callEvent(prepareitemenchantevent);

                    if (prepareitemenchantevent.isCancelled())
                    {
                        for (i = 0; i < 3; ++i)
                        {
                            this.costs[i] = 0;
                        }

                        return;
                    }

                    for (int j1 = 0; j1 < 3; ++j1)
                    {
                        if (this.costs[j1] > 0)
                        {
                            List list = this.a(itemstack, j1, this.costs[j1]);

                            if (list != null && !list.isEmpty())
                            {
                                WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)list.get(this.k.nextInt(list.size()));
                                this.h[j1] = weightedrandomenchant.enchantment.id | weightedrandomenchant.level << 8;
                            }
                        }
                    }

                    this.b();
                }
            }
            else
            {
                for (int l = 0; l < 3; ++l)
                {
                    this.costs[l] = 0;
                    this.h[l] = -1;
                }
            }
        }
    }

    public boolean a(EntityHuman p_a_1_, int p_a_2_)
    {
        ItemStack itemstack = this.enchantSlots.getItem(0);
        ItemStack itemstack1 = this.enchantSlots.getItem(1);
        int i = p_a_2_ + 1;

        if ((itemstack1 == null || itemstack1.count < i) && !p_a_1_.abilities.canInstantlyBuild)
        {
            return false;
        }
        else if (this.costs[p_a_2_] > 0 && itemstack != null && (p_a_1_.expLevel >= i && p_a_1_.expLevel >= this.costs[p_a_2_] || p_a_1_.abilities.canInstantlyBuild))
        {
            if (!this.world.isClientSide)
            {
                List list = this.a(itemstack, p_a_2_, this.costs[p_a_2_]);

                if (list == null)
                {
                    list = new ArrayList();
                }

                boolean flag = itemstack.getItem() == Items.BOOK;

                if (list != null)
                {
                    Map<org.bukkit.enchantments.Enchantment, Integer> map = new HashMap();

                    for (Object object : list)
                    {
                        WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)object;
                        map.put(org.bukkit.enchantments.Enchantment.getById(weightedrandomenchant.enchantment.id), Integer.valueOf(weightedrandomenchant.level));
                    }

                    CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
                    EnchantItemEvent enchantitemevent = new EnchantItemEvent((Player)p_a_1_.getBukkitEntity(), this.getBukkitView(), this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), craftitemstack, this.costs[p_a_2_], map, p_a_2_);
                    this.world.getServer().getPluginManager().callEvent(enchantitemevent);
                    int k = enchantitemevent.getExpLevelCost();

                    if (enchantitemevent.isCancelled() || k > p_a_1_.expLevel && !p_a_1_.abilities.canInstantlyBuild || enchantitemevent.getEnchantsToAdd().isEmpty())
                    {
                        return false;
                    }

                    if (flag)
                    {
                        itemstack.setItem(Items.ENCHANTED_BOOK);
                    }

                    for (Entry<org.bukkit.enchantments.Enchantment, Integer> entry : enchantitemevent.getEnchantsToAdd().entrySet())
                    {
                        try
                        {
                            if (flag)
                            {
                                int j = ((org.bukkit.enchantments.Enchantment)entry.getKey()).getId();

                                if (Enchantment.getById(j) != null)
                                {
                                    WeightedRandomEnchant weightedrandomenchant1 = new WeightedRandomEnchant(Enchantment.getById(j), ((Integer)entry.getValue()).intValue());
                                    Items.ENCHANTED_BOOK.a(itemstack, weightedrandomenchant1);
                                }
                            }
                            else
                            {
                                craftitemstack.addUnsafeEnchantment((org.bukkit.enchantments.Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
                            }
                        }
                        catch (IllegalArgumentException var16)
                        {
                            ;
                        }
                    }

                    p_a_1_.enchantDone(i);

                    if (!p_a_1_.abilities.canInstantlyBuild)
                    {
                        itemstack1.count -= i;

                        if (itemstack1.count <= 0)
                        {
                            this.enchantSlots.setItem(1, (ItemStack)null);
                        }
                    }

                    p_a_1_.b(StatisticList.W);
                    this.enchantSlots.update();
                    this.f = p_a_1_.cj();
                    this.a((IInventory)this.enchantSlots);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private List<WeightedRandomEnchant> a(ItemStack p_a_1_, int p_a_2_, int p_a_3_)
    {
        this.k.setSeed((long)(this.f + p_a_2_));
        List list = EnchantmentManager.b(this.k, p_a_1_, p_a_3_);

        if (p_a_1_.getItem() == Items.BOOK && list != null && list.size() > 1)
        {
            list.remove(this.k.nextInt(list.size()));
        }

        return list;
    }

    public void b(EntityHuman p_b_1_)
    {
        super.b(p_b_1_);

        if (this.world == null)
        {
            this.world = p_b_1_.getWorld();
        }

        if (!this.world.isClientSide)
        {
            for (int i = 0; i < this.enchantSlots.getSize(); ++i)
            {
                ItemStack itemstack = this.enchantSlots.splitWithoutUpdate(i);

                if (itemstack != null)
                {
                    p_b_1_.drop(itemstack, false);
                }
            }
        }
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return !this.checkReachable ? true : (this.world.getType(this.position).getBlock() != Blocks.ENCHANTING_TABLE ? false : p_a_1_.e((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D);
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
                if (!this.a(itemstack1, 2, 38, true))
                {
                    return null;
                }
            }
            else if (p_b_2_ == 1)
            {
                if (!this.a(itemstack1, 2, 38, true))
                {
                    return null;
                }
            }
            else if (itemstack1.getItem() == Items.DYE && EnumColor.fromInvColorIndex(itemstack1.getData()) == EnumColor.BLUE)
            {
                if (!this.a(itemstack1, 1, 2, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.c.get(0)).hasItem() || !((Slot)this.c.get(0)).isAllowed(itemstack1))
                {
                    return null;
                }

                if (itemstack1.hasTag() && itemstack1.count == 1)
                {
                    ((Slot)this.c.get(0)).set(itemstack1.cloneItemStack());
                    itemstack1.count = 0;
                }
                else if (itemstack1.count >= 1)
                {
                    ItemStack itemstack2 = itemstack1.cloneItemStack();
                    itemstack2.count = 1;
                    ((Slot)this.c.get(0)).set(itemstack2);
                    --itemstack1.count;
                }
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
            CraftInventoryEnchanting craftinventoryenchanting = new CraftInventoryEnchanting(this.enchantSlots);
            this.bukkitEntity = new CraftInventoryView(this.player, craftinventoryenchanting, this);
            return this.bukkitEntity;
        }
    }
}
