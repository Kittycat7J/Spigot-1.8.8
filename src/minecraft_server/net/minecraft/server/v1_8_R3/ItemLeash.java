package net.minecraft.server.v1_8_R3;

import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class ItemLeash extends Item
{
    public ItemLeash()
    {
        this.a(CreativeModeTab.i);
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        Block block = p_interactWith_3_.getType(p_interactWith_4_).getBlock();

        if (block instanceof BlockFence)
        {
            if (p_interactWith_3_.isClientSide)
            {
                return true;
            }
            else
            {
                a(p_interactWith_2_, p_interactWith_3_, p_interactWith_4_);
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public static boolean a(EntityHuman p_a_0_, World p_a_1_, BlockPosition p_a_2_)
    {
        EntityLeash entityleash = EntityLeash.b(p_a_1_, p_a_2_);
        boolean flag = false;
        double d0 = 7.0D;
        int i = p_a_2_.getX();
        int j = p_a_2_.getY();
        int k = p_a_2_.getZ();

        for (EntityInsentient entityinsentient : p_a_1_.a(EntityInsentient.class, new AxisAlignedBB((double)i - d0, (double)j - d0, (double)k - d0, (double)i + d0, (double)j + d0, (double)k + d0)))
        {
            if (entityinsentient.cc() && entityinsentient.getLeashHolder() == p_a_0_)
            {
                if (entityleash == null)
                {
                    entityleash = EntityLeash.a(p_a_1_, p_a_2_);
                    HangingPlaceEvent hangingplaceevent = new HangingPlaceEvent((Hanging)entityleash.getBukkitEntity(), p_a_0_ != null ? (Player)p_a_0_.getBukkitEntity() : null, p_a_1_.getWorld().getBlockAt(i, j, k), BlockFace.SELF);
                    p_a_1_.getServer().getPluginManager().callEvent(hangingplaceevent);

                    if (hangingplaceevent.isCancelled())
                    {
                        entityleash.die();
                        return false;
                    }
                }

                if (!CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, entityleash, p_a_0_).isCancelled())
                {
                    entityinsentient.setLeashHolder(entityleash, true);
                    flag = true;
                }
            }
        }

        return flag;
    }
}
