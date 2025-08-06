package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutTabComplete implements Packet<PacketListenerPlayOut>
{
    private String[] a;

    public PacketPlayOutTabComplete()
    {
    }

    public PacketPlayOutTabComplete(String[] p_i964_1_)
    {
        this.a = p_i964_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = new String[p_a_1_.e()];

        for (int i = 0; i < this.a.length; ++i)
        {
            this.a[i] = p_a_1_.c(32767);
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a.length);

        for (String s : this.a)
        {
            p_b_1_.a(s);
        }
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
