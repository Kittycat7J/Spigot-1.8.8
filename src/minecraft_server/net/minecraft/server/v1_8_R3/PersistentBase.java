package net.minecraft.server.v1_8_R3;

public abstract class PersistentBase
{
    public final String id;
    private boolean b;

    public PersistentBase(String p_i835_1_)
    {
        this.id = p_i835_1_;
    }

    public abstract void a(NBTTagCompound var1);

    public abstract void b(NBTTagCompound var1);

    public void c()
    {
        this.a(true);
    }

    public void a(boolean p_a_1_)
    {
        this.b = p_a_1_;
    }

    public boolean d()
    {
        return this.b;
    }
}
