package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class ItemFireball extends Item
{
    public ItemFireball()
    {
        this.a(CreativeModeTab.f);
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        if (p_interactWith_3_.isClientSide)
        {
            return true;
        }
        else
        {
            p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);

            if (!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_))
            {
                return false;
            }
            else
            {
                if (p_interactWith_3_.getType(p_interactWith_4_).getBlock().getMaterial() == Material.AIR)
                {
                    if (CraftEventFactory.callBlockIgniteEvent(p_interactWith_3_, p_interactWith_4_.getX(), p_interactWith_4_.getY(), p_interactWith_4_.getZ(), IgniteCause.FIREBALL, p_interactWith_2_).isCancelled())
                    {
                        if (!p_interactWith_2_.abilities.canInstantlyBuild)
                        {
                            --p_interactWith_1_.count;
                        }

                        return false;
                    }

                    p_interactWith_3_.makeSound((double)p_interactWith_4_.getX() + 0.5D, (double)p_interactWith_4_.getY() + 0.5D, (double)p_interactWith_4_.getZ() + 0.5D, "item.fireCharge.use", 1.0F, (g.nextFloat() - g.nextFloat()) * 0.2F + 1.0F);
                    p_interactWith_3_.setTypeUpdate(p_interactWith_4_, Blocks.FIRE.getBlockData());
                }

                if (!p_interactWith_2_.abilities.canInstantlyBuild)
                {
                    --p_interactWith_1_.count;
                }

                return true;
            }
        }
    }
}
