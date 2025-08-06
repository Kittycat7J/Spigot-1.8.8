package net.minecraft.server.v1_8_R3;

public class NextTickListEntry implements Comparable<NextTickListEntry>
{
    private static long d;
    private final Block e;
    public final BlockPosition a;
    public long b;
    public int c;
    private long f;

    public NextTickListEntry(BlockPosition p_i133_1_, Block p_i133_2_)
    {
        this.f = (long)(d++);
        this.a = p_i133_1_;
        this.e = p_i133_2_;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!(p_equals_1_ instanceof NextTickListEntry))
        {
            return false;
        }
        else
        {
            NextTickListEntry nextticklistentry = (NextTickListEntry)p_equals_1_;
            return this.a.equals(nextticklistentry.a) && Block.a(this.e, nextticklistentry.e);
        }
    }

    public int hashCode()
    {
        return this.a.hashCode();
    }

    public NextTickListEntry a(long p_a_1_)
    {
        this.b = p_a_1_;
        return this;
    }

    public void a(int p_a_1_)
    {
        this.c = p_a_1_;
    }

    public int a(NextTickListEntry p_a_1_)
    {
        return this.b < p_a_1_.b ? -1 : (this.b > p_a_1_.b ? 1 : (this.c != p_a_1_.c ? this.c - p_a_1_.c : (this.f < p_a_1_.f ? -1 : (this.f > p_a_1_.f ? 1 : 0))));
    }

    public String toString()
    {
        return Block.getId(this.e) + ": " + this.a + ", " + this.b + ", " + this.c + ", " + this.f;
    }

    public Block a()
    {
        return this.e;
    }

    public int compareTo(NextTickListEntry p_compareTo_1_)
    {
        return this.a(p_compareTo_1_);
    }
}
