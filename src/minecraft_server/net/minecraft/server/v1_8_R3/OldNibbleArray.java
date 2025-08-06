package net.minecraft.server.v1_8_R3;

public class OldNibbleArray
{
    public final byte[] a;
    private final int b;
    private final int c;

    public OldNibbleArray(byte[] p_i679_1_, int p_i679_2_)
    {
        this.a = p_i679_1_;
        this.b = p_i679_2_;
        this.c = p_i679_2_ + 4;
    }

    public int a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        int i = p_a_1_ << this.c | p_a_3_ << this.b | p_a_2_;
        int j = i >> 1;
        int k = i & 1;
        return k == 0 ? this.a[j] & 15 : this.a[j] >> 4 & 15;
    }
}
