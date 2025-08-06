package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryHorseChest extends InventorySubcontainer
{
    public List<HumanEntity> transaction = new ArrayList();
    private EntityHorse horse;
    private int maxStack = 64;

    public InventoryHorseChest(String p_i282_1_, int p_i282_2_)
    {
        super(p_i282_1_, false, p_i282_2_);
    }

    public InventoryHorseChest(String p_i283_1_, int p_i283_2_, EntityHorse p_i283_3_)
    {
        super(p_i283_1_, false, p_i283_2_, (CraftHorse)p_i283_3_.getBukkitEntity());
        this.horse = p_i283_3_;
    }

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
        return (Horse)this.horse.getBukkitEntity();
    }

    public void setMaxStackSize(int p_setMaxStackSize_1_)
    {
        this.maxStack = p_setMaxStackSize_1_;
    }

    public int getMaxStackSize()
    {
        return this.maxStack;
    }
}
