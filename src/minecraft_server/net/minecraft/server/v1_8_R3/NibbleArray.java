package net.minecraft.server.v1_8_R3;

public class NibbleArray
{
    private final byte[] a;

    public NibbleArray()
    {
        this.a = new byte[2048];
    }

    public NibbleArray(byte[] p_i237_1_)
    {
        this.a = p_i237_1_;

        if (p_i237_1_.length != 2048)
        {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + p_i237_1_.length);
        }
    }

    public int a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        return this.a(this.b(p_a_1_, p_a_2_, p_a_3_));
    }

    public void a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        this.a(this.b(p_a_1_, p_a_2_, p_a_3_), p_a_4_);
    }

    private int b(int p_b_1_, int p_b_2_, int p_b_3_)
    {
        return p_b_2_ << 8 | p_b_3_ << 4 | p_b_1_;
    }

    public int a(int p_a_1_)
    {
        int i = this.c(p_a_1_);
        return this.b(p_a_1_) ? this.a[i] & 15 : this.a[i] >> 4 & 15;
    }

    public void a(int p_a_1_, int p_a_2_)
    {
        int i = this.c(p_a_1_);

        if (this.b(p_a_1_))
        {
            this.a[i] = (byte)(this.a[i] & 240 | p_a_2_ & 15);
        }
        else
        {
            this.a[i] = (byte)(this.a[i] & 15 | (p_a_2_ & 15) << 4);
        }
    }

    private boolean b(int p_b_1_)
    {
        return (p_b_1_ & 1) == 0;
    }

    private int c(int p_c_1_)
    {
        return p_c_1_ >> 1;
    }

    public byte[] a()
    {
        return this.a;
    }
}
