package net.minecraft.server.v1_8_R3;

public class NBTReadLimiter
{
    public static final NBTReadLimiter a = new NBTReadLimiter(0L)
    {
        public void a(long p_a_1_)
        {
        }
    };
    private final long b;
    private long c;

    public NBTReadLimiter(long p_i914_1_)
    {
        this.b = p_i914_1_;
    }

    public void a(long p_a_1_)
    {
        this.c += p_a_1_ / 8L;

        if (this.c > this.b)
        {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.c + "bytes where max allowed: " + this.b);
        }
    }
}
