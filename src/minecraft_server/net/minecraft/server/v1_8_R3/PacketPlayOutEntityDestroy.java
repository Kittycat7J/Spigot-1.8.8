package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutEntityDestroy implements Packet<PacketListenerPlayOut>
{
    private int[] a;

    public PacketPlayOutEntityDestroy()
    {
    }

    public PacketPlayOutEntityDestroy(int... p_i998_1_)
    {
        this.a = p_i998_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = new int[p_a_1_.e()];

        for (int i = 0; i < this.a.length; ++i)
        {
            this.a[i] = p_a_1_.e();
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a.length);

        for (int i = 0; i < this.a.length; ++i)
        {
            p_b_1_.b(this.a[i]);
        }
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
