package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutCollect implements Packet<PacketListenerPlayOut>
{
    private int a;
    private int b;

    public PacketPlayOutCollect()
    {
    }

    public PacketPlayOutCollect(int p_i1030_1_, int p_i1030_2_)
    {
        this.a = p_i1030_1_;
        this.b = p_i1030_2_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.b = p_a_1_.e();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.b(this.b);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
