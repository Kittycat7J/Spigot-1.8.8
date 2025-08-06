package net.minecraft.server.v1_8_R3;

public class ItemStep extends ItemBlock
{
    private final BlockStepAbstract b;
    private final BlockStepAbstract c;

    public ItemStep(Block p_i516_1_, BlockStepAbstract p_i516_2_, BlockStepAbstract p_i516_3_)
    {
        super(p_i516_1_);
        this.b = p_i516_2_;
        this.c = p_i516_3_;
        this.setMaxDurability(0);
        this.a(true);
    }

    public int filterData(int p_filterData_1_)
    {
        return p_filterData_1_;
    }

    public String e_(ItemStack p_e__1_)
    {
        return this.b.b(p_e__1_.getData());
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        if (p_interactWith_1_.count == 0)
        {
            return false;
        }
        else if (!p_interactWith_2_.a(p_interactWith_4_.shift(p_interactWith_5_), p_interactWith_5_, p_interactWith_1_))
        {
            return false;
        }
        else
        {
            Object object = this.b.a(p_interactWith_1_);
            IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);

            if (iblockdata.getBlock() == this.b)
            {
                IBlockState iblockstate = this.b.n();
                Comparable comparable = iblockdata.get(iblockstate);
                BlockStepAbstract.EnumSlabHalf blockstepabstract$enumslabhalf = (BlockStepAbstract.EnumSlabHalf)iblockdata.get(BlockStepAbstract.HALF);

                if ((p_interactWith_5_ == EnumDirection.UP && blockstepabstract$enumslabhalf == BlockStepAbstract.EnumSlabHalf.BOTTOM || p_interactWith_5_ == EnumDirection.DOWN && blockstepabstract$enumslabhalf == BlockStepAbstract.EnumSlabHalf.TOP) && comparable == object)
                {
                    IBlockData iblockdata1 = this.c.getBlockData().set(iblockstate, comparable);

                    if (p_interactWith_3_.b(this.c.a(p_interactWith_3_, p_interactWith_4_, iblockdata1)) && p_interactWith_3_.setTypeAndData(p_interactWith_4_, iblockdata1, 3))
                    {
                        p_interactWith_3_.makeSound((double)((float)p_interactWith_4_.getX() + 0.5F), (double)((float)p_interactWith_4_.getY() + 0.5F), (double)((float)p_interactWith_4_.getZ() + 0.5F), this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                        --p_interactWith_1_.count;
                    }

                    return true;
                }
            }

            return this.a(p_interactWith_1_, p_interactWith_3_, p_interactWith_4_.shift(p_interactWith_5_), object) ? true : super.interactWith(p_interactWith_1_, p_interactWith_2_, p_interactWith_3_, p_interactWith_4_, p_interactWith_5_, p_interactWith_6_, p_interactWith_7_, p_interactWith_8_);
        }
    }

    private boolean a(ItemStack p_a_1_, World p_a_2_, BlockPosition p_a_3_, Object p_a_4_)
    {
        IBlockData iblockdata = p_a_2_.getType(p_a_3_);

        if (iblockdata.getBlock() == this.b)
        {
            Comparable comparable = iblockdata.get(this.b.n());

            if (comparable == p_a_4_)
            {
                IBlockData iblockdata1 = this.c.getBlockData().set(this.b.n(), comparable);

                if (p_a_2_.b(this.c.a(p_a_2_, p_a_3_, iblockdata1)) && p_a_2_.setTypeAndData(p_a_3_, iblockdata1, 3))
                {
                    p_a_2_.makeSound((double)((float)p_a_3_.getX() + 0.5F), (double)((float)p_a_3_.getY() + 0.5F), (double)((float)p_a_3_.getZ() + 0.5F), this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                    --p_a_1_.count;
                }

                return true;
            }
        }

        return false;
    }
}
