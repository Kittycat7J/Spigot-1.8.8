package net.minecraft.server.v1_8_R3;

public class ChunkSnapshot
{
    private final short[] a = new short[65536];
    private final IBlockData b = Blocks.AIR.getBlockData();

    public IBlockData a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        int i = p_a_1_ << 12 | p_a_3_ << 8 | p_a_2_;
        return this.a(i);
    }

    public IBlockData a(int p_a_1_)
    {
        if (p_a_1_ >= 0 && p_a_1_ < this.a.length)
        {
            IBlockData iblockdata = (IBlockData)Block.d.a(this.a[p_a_1_]);
            return iblockdata != null ? iblockdata : this.b;
        }
        else
        {
            throw new IndexOutOfBoundsException("The coordinate is out of range");
        }
    }

    public void a(int p_a_1_, int p_a_2_, int p_a_3_, IBlockData p_a_4_)
    {
        int i = p_a_1_ << 12 | p_a_3_ << 8 | p_a_2_;
        this.a(i, p_a_4_);
    }

    public void a(int p_a_1_, IBlockData p_a_2_)
    {
        if (p_a_1_ >= 0 && p_a_1_ < this.a.length)
        {
            this.a[p_a_1_] = (short)Block.d.b(p_a_2_);
        }
        else
        {
            throw new IndexOutOfBoundsException("The coordinate is out of range");
        }
    }
}
