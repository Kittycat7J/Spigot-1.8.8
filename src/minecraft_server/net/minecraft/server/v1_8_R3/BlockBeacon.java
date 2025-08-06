package net.minecraft.server.v1_8_R3;

public class BlockBeacon extends BlockContainer
{
    public BlockBeacon()
    {
        super(Material.SHATTERABLE, MaterialMapColor.G);
        this.c(3.0F);
        this.a(CreativeModeTab.f);
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityBeacon();
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (p_interact_1_.isClientSide)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = p_interact_1_.getTileEntity(p_interact_2_);

            if (tileentity instanceof TileEntityBeacon)
            {
                p_interact_4_.openContainer((TileEntityBeacon)tileentity);
                p_interact_4_.b(StatisticList.N);
            }

            return true;
        }
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public int b()
    {
        return 3;
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        super.postPlace(p_postPlace_1_, p_postPlace_2_, p_postPlace_3_, p_postPlace_4_, p_postPlace_5_);

        if (p_postPlace_5_.hasName())
        {
            TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

            if (tileentity instanceof TileEntityBeacon)
            {
                ((TileEntityBeacon)tileentity).a(p_postPlace_5_.getName());
            }
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        TileEntity tileentity = p_doPhysics_1_.getTileEntity(p_doPhysics_2_);

        if (tileentity instanceof TileEntityBeacon)
        {
            ((TileEntityBeacon)tileentity).m();
            p_doPhysics_1_.playBlockAction(p_doPhysics_2_, this, 1, 0);
        }
    }

    public static void f(final World p_f_0_, final BlockPosition p_f_1_)
    {
        HttpUtilities.a.submit(new Runnable()
        {
            public void run()
            {
                Chunk chunk = p_f_0_.getChunkAtWorldCoords(p_f_1_);

                for (int i = p_f_1_.getY() - 1; i >= 0; --i)
                {
                    final BlockPosition blockposition = new BlockPosition(p_f_1_.getX(), i, p_f_1_.getZ());

                    if (!chunk.d(blockposition))
                    {
                        break;
                    }

                    IBlockData iblockdata = p_f_0_.getType(blockposition);

                    if (iblockdata.getBlock() == Blocks.BEACON)
                    {
                        ((WorldServer)p_f_0_).postToMainThread(new Runnable()
                        {
                            public void run()
                            {
                                TileEntity tileentity = p_f_0_.getTileEntity(blockposition);

                                if (tileentity instanceof TileEntityBeacon)
                                {
                                    ((TileEntityBeacon)tileentity).m();
                                    p_f_0_.playBlockAction(blockposition, Blocks.BEACON, 1, 0);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
