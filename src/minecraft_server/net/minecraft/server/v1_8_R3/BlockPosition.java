package net.minecraft.server.v1_8_R3;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;

public class BlockPosition extends BaseBlockPosition
{
    public static final BlockPosition ZERO = new BlockPosition(0, 0, 0);
    private static final int c = 1 + MathHelper.c(MathHelper.b(30000000));
    private static final int d = c;
    private static final int e = 64 - c - d;
    private static final int f = 0 + d;
    private static final int g = f + e;
    private static final long h = (1L << c) - 1L;
    private static final long i = (1L << e) - 1L;
    private static final long j = (1L << d) - 1L;

    public BlockPosition(int p_i882_1_, int p_i882_2_, int p_i882_3_)
    {
        super(p_i882_1_, p_i882_2_, p_i882_3_);
    }

    public BlockPosition(double p_i883_1_, double p_i883_3_, double p_i883_5_)
    {
        super(p_i883_1_, p_i883_3_, p_i883_5_);
    }

    public BlockPosition(Entity p_i884_1_)
    {
        this(p_i884_1_.locX, p_i884_1_.locY, p_i884_1_.locZ);
    }

    public BlockPosition(Vec3D p_i885_1_)
    {
        this(p_i885_1_.a, p_i885_1_.b, p_i885_1_.c);
    }

    public BlockPosition(BaseBlockPosition p_i886_1_)
    {
        this(p_i886_1_.getX(), p_i886_1_.getY(), p_i886_1_.getZ());
    }

    public BlockPosition a(double p_a_1_, double p_a_3_, double p_a_5_)
    {
        return p_a_1_ == 0.0D && p_a_3_ == 0.0D && p_a_5_ == 0.0D ? this : new BlockPosition((double)this.getX() + p_a_1_, (double)this.getY() + p_a_3_, (double)this.getZ() + p_a_5_);
    }

    public BlockPosition a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        return p_a_1_ == 0 && p_a_2_ == 0 && p_a_3_ == 0 ? this : new BlockPosition(this.getX() + p_a_1_, this.getY() + p_a_2_, this.getZ() + p_a_3_);
    }

    public BlockPosition a(BaseBlockPosition p_a_1_)
    {
        return p_a_1_.getX() == 0 && p_a_1_.getY() == 0 && p_a_1_.getZ() == 0 ? this : new BlockPosition(this.getX() + p_a_1_.getX(), this.getY() + p_a_1_.getY(), this.getZ() + p_a_1_.getZ());
    }

    public BlockPosition b(BaseBlockPosition p_b_1_)
    {
        return p_b_1_.getX() == 0 && p_b_1_.getY() == 0 && p_b_1_.getZ() == 0 ? this : new BlockPosition(this.getX() - p_b_1_.getX(), this.getY() - p_b_1_.getY(), this.getZ() - p_b_1_.getZ());
    }

    public BlockPosition up()
    {
        return this.up(1);
    }

    public BlockPosition up(int p_up_1_)
    {
        return this.shift(EnumDirection.UP, p_up_1_);
    }

    public BlockPosition down()
    {
        return this.down(1);
    }

    public BlockPosition down(int p_down_1_)
    {
        return this.shift(EnumDirection.DOWN, p_down_1_);
    }

    public BlockPosition north()
    {
        return this.north(1);
    }

    public BlockPosition north(int p_north_1_)
    {
        return this.shift(EnumDirection.NORTH, p_north_1_);
    }

    public BlockPosition south()
    {
        return this.south(1);
    }

    public BlockPosition south(int p_south_1_)
    {
        return this.shift(EnumDirection.SOUTH, p_south_1_);
    }

    public BlockPosition west()
    {
        return this.west(1);
    }

    public BlockPosition west(int p_west_1_)
    {
        return this.shift(EnumDirection.WEST, p_west_1_);
    }

    public BlockPosition east()
    {
        return this.east(1);
    }

    public BlockPosition east(int p_east_1_)
    {
        return this.shift(EnumDirection.EAST, p_east_1_);
    }

    public BlockPosition shift(EnumDirection p_shift_1_)
    {
        return this.shift(p_shift_1_, 1);
    }

    public BlockPosition shift(EnumDirection p_shift_1_, int p_shift_2_)
    {
        return p_shift_2_ == 0 ? this : new BlockPosition(this.getX() + p_shift_1_.getAdjacentX() * p_shift_2_, this.getY() + p_shift_1_.getAdjacentY() * p_shift_2_, this.getZ() + p_shift_1_.getAdjacentZ() * p_shift_2_);
    }

    public BlockPosition c(BaseBlockPosition p_c_1_)
    {
        return new BlockPosition(this.getY() * p_c_1_.getZ() - this.getZ() * p_c_1_.getY(), this.getZ() * p_c_1_.getX() - this.getX() * p_c_1_.getZ(), this.getX() * p_c_1_.getY() - this.getY() * p_c_1_.getX());
    }

    public long asLong()
    {
        return ((long)this.getX() & h) << g | ((long)this.getY() & i) << f | ((long)this.getZ() & j) << 0;
    }

    public static BlockPosition fromLong(long p_fromLong_0_)
    {
        int ix = (int)(p_fromLong_0_ << 64 - g - c >> 64 - c);
        int jx = (int)(p_fromLong_0_ << 64 - f - e >> 64 - e);
        int k = (int)(p_fromLong_0_ << 64 - d >> 64 - d);
        return new BlockPosition(ix, jx, k);
    }

    public static Iterable<BlockPosition> a(BlockPosition p_a_0_, BlockPosition p_a_1_)
    {
        final BlockPosition blockposition = new BlockPosition(Math.min(p_a_0_.getX(), p_a_1_.getX()), Math.min(p_a_0_.getY(), p_a_1_.getY()), Math.min(p_a_0_.getZ(), p_a_1_.getZ()));
        final BlockPosition blockposition1 = new BlockPosition(Math.max(p_a_0_.getX(), p_a_1_.getX()), Math.max(p_a_0_.getY(), p_a_1_.getY()), Math.max(p_a_0_.getZ(), p_a_1_.getZ()));
        return new Iterable<BlockPosition>()
        {
            public Iterator<BlockPosition> iterator()
            {
                return new AbstractIterator<BlockPosition>()
                {
                    private BlockPosition b = null;
                    protected BlockPosition a()
                    {
                        if (this.b == null)
                        {
                            this.b = blockposition;
                            return this.b;
                        }
                        else if (this.b.equals(blockposition1))
                        {
                            return (BlockPosition)this.endOfData();
                        }
                        else
                        {
                            int i = this.b.getX();
                            int j = this.b.getY();
                            int k = this.b.getZ();

                            if (i < blockposition1.getX())
                            {
                                ++i;
                            }
                            else if (j < blockposition1.getY())
                            {
                                i = blockposition.getX();
                                ++j;
                            }
                            else if (k < blockposition1.getZ())
                            {
                                i = blockposition.getX();
                                j = blockposition.getY();
                                ++k;
                            }

                            this.b = new BlockPosition(i, j, k);
                            return this.b;
                        }
                    }
                };
            }
        };
    }

    public static Iterable<BlockPosition.MutableBlockPosition> b(BlockPosition p_b_0_, BlockPosition p_b_1_)
    {
        final BlockPosition blockposition = new BlockPosition(Math.min(p_b_0_.getX(), p_b_1_.getX()), Math.min(p_b_0_.getY(), p_b_1_.getY()), Math.min(p_b_0_.getZ(), p_b_1_.getZ()));
        final BlockPosition blockposition1 = new BlockPosition(Math.max(p_b_0_.getX(), p_b_1_.getX()), Math.max(p_b_0_.getY(), p_b_1_.getY()), Math.max(p_b_0_.getZ(), p_b_1_.getZ()));
        return new Iterable<BlockPosition.MutableBlockPosition>()
        {
            public Iterator<BlockPosition.MutableBlockPosition> iterator()
            {
                return new AbstractIterator<BlockPosition.MutableBlockPosition>()
                {
                    private BlockPosition.MutableBlockPosition b = null;
                    protected BlockPosition.MutableBlockPosition a()
                    {
                        if (this.b == null)
                        {
                            this.b = new BlockPosition.MutableBlockPosition(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                            return this.b;
                        }
                        else if (this.b.equals(blockposition1))
                        {
                            return (BlockPosition.MutableBlockPosition)this.endOfData();
                        }
                        else
                        {
                            int i = this.b.getX();
                            int j = this.b.getY();
                            int k = this.b.getZ();

                            if (i < blockposition1.getX())
                            {
                                ++i;
                            }
                            else if (j < blockposition1.getY())
                            {
                                i = blockposition.getX();
                                ++j;
                            }
                            else if (k < blockposition1.getZ())
                            {
                                i = blockposition.getX();
                                j = blockposition.getY();
                                ++k;
                            }

                            this.b.c = i;
                            this.b.d = j;
                            this.b.e = k;
                            return this.b;
                        }
                    }
                };
            }
        };
    }

    public static final class MutableBlockPosition extends BlockPosition
    {
        private int c;
        private int d;
        private int e;

        public MutableBlockPosition()
        {
            this(0, 0, 0);
        }

        public MutableBlockPosition(int p_i881_1_, int p_i881_2_, int p_i881_3_)
        {
            super(0, 0, 0);
            this.c = p_i881_1_;
            this.d = p_i881_2_;
            this.e = p_i881_3_;
        }

        public int getX()
        {
            return this.c;
        }

        public int getY()
        {
            return this.d;
        }

        public int getZ()
        {
            return this.e;
        }

        public BlockPosition.MutableBlockPosition c(int p_c_1_, int p_c_2_, int p_c_3_)
        {
            this.c = p_c_1_;
            this.d = p_c_2_;
            this.e = p_c_3_;
            return this;
        }
    }
}
