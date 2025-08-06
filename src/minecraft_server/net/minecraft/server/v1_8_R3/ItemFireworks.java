package net.minecraft.server.v1_8_R3;

public class ItemFireworks extends Item
{
    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        if (!p_interactWith_3_.isClientSide)
        {
            EntityFireworks entityfireworks = new EntityFireworks(p_interactWith_3_, (double)((float)p_interactWith_4_.getX() + p_interactWith_6_), (double)((float)p_interactWith_4_.getY() + p_interactWith_7_), (double)((float)p_interactWith_4_.getZ() + p_interactWith_8_), p_interactWith_1_);
            p_interactWith_3_.addEntity(entityfireworks);

            if (!p_interactWith_2_.abilities.canInstantlyBuild)
            {
                --p_interactWith_1_.count;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
