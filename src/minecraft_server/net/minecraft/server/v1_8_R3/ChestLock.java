package net.minecraft.server.v1_8_R3;

public class ChestLock
{
    public static final ChestLock a = new ChestLock("");
    private final String b;

    public ChestLock(String p_i1128_1_)
    {
        this.b = p_i1128_1_;
    }

    public boolean a()
    {
        return this.b == null || this.b.isEmpty();
    }

    public String b()
    {
        return this.b;
    }

    public void a(NBTTagCompound p_a_1_)
    {
        p_a_1_.setString("Lock", this.b);
    }

    public static ChestLock b(NBTTagCompound p_b_0_)
    {
        if (p_b_0_.hasKeyOfType("Lock", 8))
        {
            String s = p_b_0_.getString("Lock");
            return new ChestLock(s);
        }
        else
        {
            return a;
        }
    }
}
