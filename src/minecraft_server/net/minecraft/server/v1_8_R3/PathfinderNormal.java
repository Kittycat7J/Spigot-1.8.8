package net.minecraft.server.v1_8_R3;

public class PathfinderNormal extends PathfinderAbstract
{
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;

    public void a(IBlockAccess p_a_1_, Entity p_a_2_)
    {
        super.a(p_a_1_, p_a_2_);
        this.j = this.h;
    }

    public void a()
    {
        super.a();
        this.h = this.j;
    }

    public PathPoint a(Entity p_a_1_)
    {
        int ix;

        if (this.i && p_a_1_.V())
        {
            ix = (int)p_a_1_.getBoundingBox().b;
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(MathHelper.floor(p_a_1_.locX), ix, MathHelper.floor(p_a_1_.locZ));

            for (Block block = this.a.getType(blockposition$mutableblockposition).getBlock(); block == Blocks.FLOWING_WATER || block == Blocks.WATER; block = this.a.getType(blockposition$mutableblockposition).getBlock())
            {
                ++ix;
                blockposition$mutableblockposition.c(MathHelper.floor(p_a_1_.locX), ix, MathHelper.floor(p_a_1_.locZ));
            }

            this.h = false;
        }
        else
        {
            ix = MathHelper.floor(p_a_1_.getBoundingBox().b + 0.5D);
        }

        return this.a(MathHelper.floor(p_a_1_.getBoundingBox().a), ix, MathHelper.floor(p_a_1_.getBoundingBox().c));
    }

    public PathPoint a(Entity p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_)
    {
        return this.a(MathHelper.floor(p_a_2_ - (double)(p_a_1_.width / 2.0F)), MathHelper.floor(p_a_4_), MathHelper.floor(p_a_6_ - (double)(p_a_1_.width / 2.0F)));
    }

    public int a(PathPoint[] p_a_1_, Entity p_a_2_, PathPoint p_a_3_, PathPoint p_a_4_, float p_a_5_)
    {
        int ix = 0;
        byte b0 = 0;

        if (this.a(p_a_2_, p_a_3_.a, p_a_3_.b + 1, p_a_3_.c) == 1)
        {
            b0 = 1;
        }

        PathPoint pathpoint = this.a(p_a_2_, p_a_3_.a, p_a_3_.b, p_a_3_.c + 1, b0);
        PathPoint pathpoint1 = this.a(p_a_2_, p_a_3_.a - 1, p_a_3_.b, p_a_3_.c, b0);
        PathPoint pathpoint2 = this.a(p_a_2_, p_a_3_.a + 1, p_a_3_.b, p_a_3_.c, b0);
        PathPoint pathpoint3 = this.a(p_a_2_, p_a_3_.a, p_a_3_.b, p_a_3_.c - 1, b0);

        if (pathpoint != null && !pathpoint.i && pathpoint.a(p_a_4_) < p_a_5_)
        {
            p_a_1_[ix++] = pathpoint;
        }

        if (pathpoint1 != null && !pathpoint1.i && pathpoint1.a(p_a_4_) < p_a_5_)
        {
            p_a_1_[ix++] = pathpoint1;
        }

        if (pathpoint2 != null && !pathpoint2.i && pathpoint2.a(p_a_4_) < p_a_5_)
        {
            p_a_1_[ix++] = pathpoint2;
        }

        if (pathpoint3 != null && !pathpoint3.i && pathpoint3.a(p_a_4_) < p_a_5_)
        {
            p_a_1_[ix++] = pathpoint3;
        }

        return ix;
    }

    private PathPoint a(Entity p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_)
    {
        PathPoint pathpoint = null;
        int ix = this.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);

        if (ix == 2)
        {
            return this.a(p_a_2_, p_a_3_, p_a_4_);
        }
        else
        {
            if (ix == 1)
            {
                pathpoint = this.a(p_a_2_, p_a_3_, p_a_4_);
            }

            if (pathpoint == null && p_a_5_ > 0 && ix != -3 && ix != -4 && this.a(p_a_1_, p_a_2_, p_a_3_ + p_a_5_, p_a_4_) == 1)
            {
                pathpoint = this.a(p_a_2_, p_a_3_ + p_a_5_, p_a_4_);
                p_a_3_ += p_a_5_;
            }

            if (pathpoint != null)
            {
                int jx = 0;
                int k;

                for (k = 0; p_a_3_ > 0; pathpoint = this.a(p_a_2_, p_a_3_, p_a_4_))
                {
                    k = this.a(p_a_1_, p_a_2_, p_a_3_ - 1, p_a_4_);

                    if (this.h && k == -1)
                    {
                        return null;
                    }

                    if (k != 1)
                    {
                        break;
                    }

                    if (jx++ >= p_a_1_.aE())
                    {
                        return null;
                    }

                    --p_a_3_;

                    if (p_a_3_ <= 0)
                    {
                        return null;
                    }
                }

                if (k == -2)
                {
                    return null;
                }
            }

            return pathpoint;
        }
    }

    private int a(Entity p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        return a(this.a, p_a_1_, p_a_2_, p_a_3_, p_a_4_, this.c, this.d, this.e, this.h, this.g, this.f);
    }

    public static int a(IBlockAccess p_a_0_, Entity p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, boolean p_a_8_, boolean p_a_9_, boolean p_a_10_)
    {
        boolean flag = false;
        BlockPosition blockposition = new BlockPosition(p_a_1_);
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for (int ix = p_a_2_; ix < p_a_2_ + p_a_5_; ++ix)
        {
            for (int jx = p_a_3_; jx < p_a_3_ + p_a_6_; ++jx)
            {
                for (int k = p_a_4_; k < p_a_4_ + p_a_7_; ++k)
                {
                    blockposition$mutableblockposition.c(ix, jx, k);
                    Block block = p_a_0_.getType(blockposition$mutableblockposition).getBlock();

                    if (block.getMaterial() != Material.AIR)
                    {
                        if (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR)
                        {
                            if (block != Blocks.FLOWING_WATER && block != Blocks.WATER)
                            {
                                if (!p_a_10_ && block instanceof BlockDoor && block.getMaterial() == Material.WOOD)
                                {
                                    return 0;
                                }
                            }
                            else
                            {
                                if (p_a_8_)
                                {
                                    return -1;
                                }

                                flag = true;
                            }
                        }
                        else
                        {
                            flag = true;
                        }

                        if (p_a_1_.world.getType(blockposition$mutableblockposition).getBlock() instanceof BlockMinecartTrackAbstract)
                        {
                            if (!(p_a_1_.world.getType(blockposition).getBlock() instanceof BlockMinecartTrackAbstract) && !(p_a_1_.world.getType(blockposition.down()).getBlock() instanceof BlockMinecartTrackAbstract))
                            {
                                return -3;
                            }
                        }
                        else if (!block.b(p_a_0_, blockposition$mutableblockposition) && (!p_a_9_ || !(block instanceof BlockDoor) || block.getMaterial() != Material.WOOD))
                        {
                            if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockCobbleWall)
                            {
                                return -3;
                            }

                            if (block == Blocks.TRAPDOOR || block == Blocks.IRON_TRAPDOOR)
                            {
                                return -4;
                            }

                            Material material = block.getMaterial();

                            if (material != Material.LAVA)
                            {
                                return 0;
                            }

                            if (!p_a_1_.ab())
                            {
                                return -2;
                            }
                        }
                    }
                }
            }
        }

        return flag ? 2 : 1;
    }

    public void a(boolean p_a_1_)
    {
        this.f = p_a_1_;
    }

    public void b(boolean p_b_1_)
    {
        this.g = p_b_1_;
    }

    public void c(boolean p_c_1_)
    {
        this.h = p_c_1_;
    }

    public void d(boolean p_d_1_)
    {
        this.i = p_d_1_;
    }

    public boolean b()
    {
        return this.f;
    }

    public boolean d()
    {
        return this.i;
    }

    public boolean e()
    {
        return this.h;
    }
}
