package net.minecraft.server.v1_8_R3;

public class ItemBlock extends Item
{
    protected final Block a;

    public ItemBlock(Block p_i1256_1_)
    {
        this.a = p_i1256_1_;
    }

    public ItemBlock b(String p_b_1_)
    {
        super.c(p_b_1_);
        return this;
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
        Block block = iblockdata.getBlock();

        if (!block.a(p_interactWith_3_, p_interactWith_4_))
        {
            p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
        }

        if (p_interactWith_1_.count == 0)
        {
            return false;
        }
        else if (!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_))
        {
            return false;
        }
        else if (p_interactWith_3_.a(this.a, p_interactWith_4_, false, p_interactWith_5_, (Entity)null, p_interactWith_1_))
        {
            int i = this.filterData(p_interactWith_1_.getData());
            IBlockData iblockdata1 = this.a.getPlacedState(p_interactWith_3_, p_interactWith_4_, p_interactWith_5_, p_interactWith_6_, p_interactWith_7_, p_interactWith_8_, i, p_interactWith_2_);

            if (p_interactWith_3_.setTypeAndData(p_interactWith_4_, iblockdata1, 3))
            {
                iblockdata1 = p_interactWith_3_.getType(p_interactWith_4_);

                if (iblockdata1.getBlock() == this.a)
                {
                    a(p_interactWith_3_, p_interactWith_2_, p_interactWith_4_, p_interactWith_1_);
                    this.a.postPlace(p_interactWith_3_, p_interactWith_4_, iblockdata1, p_interactWith_2_, p_interactWith_1_);
                }

                p_interactWith_3_.makeSound((double)((float)p_interactWith_4_.getX() + 0.5F), (double)((float)p_interactWith_4_.getY() + 0.5F), (double)((float)p_interactWith_4_.getZ() + 0.5F), this.a.stepSound.getPlaceSound(), (this.a.stepSound.getVolume1() + 1.0F) / 2.0F, this.a.stepSound.getVolume2() * 0.8F);
                --p_interactWith_1_.count;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean a(World p_a_0_, EntityHuman p_a_1_, BlockPosition p_a_2_, ItemStack p_a_3_)
    {
        MinecraftServer minecraftserver = MinecraftServer.getServer();

        if (minecraftserver == null)
        {
            return false;
        }
        else
        {
            if (p_a_3_.hasTag() && p_a_3_.getTag().hasKeyOfType("BlockEntityTag", 10))
            {
                TileEntity tileentity = p_a_0_.getTileEntity(p_a_2_);

                if (tileentity != null)
                {
                    if (!p_a_0_.isClientSide && tileentity.F() && !minecraftserver.getPlayerList().isOp(p_a_1_.getProfile()))
                    {
                        return false;
                    }

                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.clone();
                    tileentity.b(nbttagcompound);
                    NBTTagCompound nbttagcompound2 = (NBTTagCompound)p_a_3_.getTag().get("BlockEntityTag");
                    nbttagcompound.a(nbttagcompound2);
                    nbttagcompound.setInt("x", p_a_2_.getX());
                    nbttagcompound.setInt("y", p_a_2_.getY());
                    nbttagcompound.setInt("z", p_a_2_.getZ());

                    if (!nbttagcompound.equals(nbttagcompound1))
                    {
                        tileentity.a(nbttagcompound);
                        tileentity.update();
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public String e_(ItemStack p_e__1_)
    {
        return this.a.a();
    }

    public String getName()
    {
        return this.a.a();
    }

    public Block d()
    {
        return this.a;
    }
}
