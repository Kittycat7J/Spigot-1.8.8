package net.minecraft.server.v1_8_R3;

public class ItemSeedFood extends ItemFood
{
    private Block b;
    private Block c;

    public ItemSeedFood(int p_i513_1_, float p_i513_2_, Block p_i513_3_, Block p_i513_4_)
    {
        super(p_i513_1_, p_i513_2_, false);
        this.b = p_i513_3_;
        this.c = p_i513_4_;
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        if (p_interactWith_5_ != EnumDirection.UP)
        {
            return false;
        }
        else if (!p_interactWith_2_.a(p_interactWith_4_.shift(p_interactWith_5_), p_interactWith_5_, p_interactWith_1_))
        {
            return false;
        }
        else if (p_interactWith_3_.getType(p_interactWith_4_).getBlock() == this.c && p_interactWith_3_.isEmpty(p_interactWith_4_.up()))
        {
            p_interactWith_3_.setTypeUpdate(p_interactWith_4_.up(), this.b.getBlockData());
            --p_interactWith_1_.count;
            return true;
        }
        else
        {
            return false;
        }
    }
}
