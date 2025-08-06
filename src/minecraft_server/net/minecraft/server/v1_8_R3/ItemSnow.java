package net.minecraft.server.v1_8_R3;

public class ItemSnow extends ItemBlock
{
    public ItemSnow(Block p_i517_1_)
    {
        super(p_i517_1_);
        this.setMaxDurability(0);
        this.a(true);
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        if (p_interactWith_1_.count == 0)
        {
            return false;
        }
        else if (!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_))
        {
            return false;
        }
        else
        {
            IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
            Block block = iblockdata.getBlock();
            BlockPosition blockposition = p_interactWith_4_;

            if ((p_interactWith_5_ != EnumDirection.UP || block != this.a) && !block.a(p_interactWith_3_, p_interactWith_4_))
            {
                blockposition = p_interactWith_4_.shift(p_interactWith_5_);
                iblockdata = p_interactWith_3_.getType(blockposition);
                block = iblockdata.getBlock();
            }

            if (block == this.a)
            {
                int i = ((Integer)iblockdata.get(BlockSnow.LAYERS)).intValue();

                if (i <= 7)
                {
                    IBlockData iblockdata1 = iblockdata.set(BlockSnow.LAYERS, Integer.valueOf(i + 1));
                    AxisAlignedBB axisalignedbb = this.a.a(p_interactWith_3_, blockposition, iblockdata1);

                    if (axisalignedbb != null && p_interactWith_3_.b(axisalignedbb) && p_interactWith_3_.setTypeAndData(blockposition, iblockdata1, 2))
                    {
                        p_interactWith_3_.makeSound((double)((float)blockposition.getX() + 0.5F), (double)((float)blockposition.getY() + 0.5F), (double)((float)blockposition.getZ() + 0.5F), this.a.stepSound.getPlaceSound(), (this.a.stepSound.getVolume1() + 1.0F) / 2.0F, this.a.stepSound.getVolume2() * 0.8F);
                        --p_interactWith_1_.count;
                        return true;
                    }
                }
            }

            return super.interactWith(p_interactWith_1_, p_interactWith_2_, p_interactWith_3_, blockposition, p_interactWith_5_, p_interactWith_6_, p_interactWith_7_, p_interactWith_8_);
        }
    }

    public int filterData(int p_filterData_1_)
    {
        return p_filterData_1_;
    }
}
