package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutUpdateHealth implements Packet<PacketListenerPlayOut>
{
    private float a;
    private int b;
    private float c;

    public PacketPlayOutUpdateHealth()
    {
    }

    public PacketPlayOutUpdateHealth(float p_i1014_1_, int p_i1014_2_, float p_i1014_3_)
    {
        this.a = p_i1014_1_;
        this.b = p_i1014_2_;
        this.c = p_i1014_3_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readFloat();
        this.b = p_a_1_.e();
        this.c = p_a_1_.readFloat();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeFloat(this.a);
        p_b_1_.b(this.b);
        p_b_1_.writeFloat(this.c);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
