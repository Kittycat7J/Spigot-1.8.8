package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.server.MapInitializeEvent;

public class ItemMapEmpty extends ItemWorldMapBase
{
    protected ItemMapEmpty()
    {
        this.a(CreativeModeTab.f);
    }

    public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_)
    {
        World world = (World)p_a_2_.getServer().getServer().worlds.get(0);
        ItemStack itemstack = new ItemStack(Items.FILLED_MAP, 1, world.b("map"));
        String s = "map_" + itemstack.getData();
        WorldMap worldmap = new WorldMap(s);
        world.a((String)s, (PersistentBase)worldmap);
        worldmap.scale = 0;
        worldmap.a(p_a_3_.locX, p_a_3_.locZ, worldmap.scale);
        worldmap.map = (byte)((WorldServer)p_a_2_).dimension;
        worldmap.c();
        CraftEventFactory.callEvent(new MapInitializeEvent(worldmap.mapView));
        --p_a_1_.count;

        if (p_a_1_.count <= 0)
        {
            return itemstack;
        }
        else
        {
            if (!p_a_3_.inventory.pickup(itemstack.cloneItemStack()))
            {
                p_a_3_.drop(itemstack, false);
            }

            p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
            return p_a_1_;
        }
    }
}
