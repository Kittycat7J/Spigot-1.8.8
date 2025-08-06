package net.minecraft.server.v1_8_R3;

public class ItemSign extends Item
{
    public ItemSign()
    {
        this.maxStackSize = 16;
        this.a(CreativeModeTab.c);
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        if (p_interactWith_5_ == EnumDirection.DOWN)
        {
            return false;
        }
        else if (!p_interactWith_3_.getType(p_interactWith_4_).getBlock().getMaterial().isBuildable())
        {
            return false;
        }
        else
        {
            p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);

            if (!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_))
            {
                return false;
            }
            else if (!Blocks.STANDING_SIGN.canPlace(p_interactWith_3_, p_interactWith_4_))
            {
                return false;
            }
            else if (p_interactWith_3_.isClientSide)
            {
                return true;
            }
            else
            {
                if (p_interactWith_5_ == EnumDirection.UP)
                {
                    int i = MathHelper.floor((double)((p_interactWith_2_.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    p_interactWith_3_.setTypeAndData(p_interactWith_4_, Blocks.STANDING_SIGN.getBlockData().set(BlockFloorSign.ROTATION, Integer.valueOf(i)), 3);
                }
                else
                {
                    p_interactWith_3_.setTypeAndData(p_interactWith_4_, Blocks.WALL_SIGN.getBlockData().set(BlockWallSign.FACING, p_interactWith_5_), 3);
                }

                --p_interactWith_1_.count;
                TileEntity tileentity = p_interactWith_3_.getTileEntity(p_interactWith_4_);

                if (tileentity instanceof TileEntitySign && !ItemBlock.a(p_interactWith_3_, p_interactWith_2_, p_interactWith_4_, p_interactWith_1_))
                {
                    p_interactWith_2_.openSign((TileEntitySign)tileentity);
                }

                return true;
            }
        }
    }
}
