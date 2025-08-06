package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutEntityVelocity implements Packet<PacketListenerPlayOut>
{
    private int a;
    private int b;
    private int c;
    private int d;

    public PacketPlayOutEntityVelocity()
    {
    }

    public PacketPlayOutEntityVelocity(Entity p_i1010_1_)
    {
        this(p_i1010_1_.getId(), p_i1010_1_.motX, p_i1010_1_.motY, p_i1010_1_.motZ);
    }

    public PacketPlayOutEntityVelocity(int p_i1011_1_, double p_i1011_2_, double p_i1011_4_, double p_i1011_6_)
    {
        this.a = p_i1011_1_;
        double d0 = 3.9D;

        if (p_i1011_2_ < -d0)
        {
            p_i1011_2_ = -d0;
        }

        if (p_i1011_4_ < -d0)
        {
            p_i1011_4_ = -d0;
        }

        if (p_i1011_6_ < -d0)
        {
            p_i1011_6_ = -d0;
        }

        if (p_i1011_2_ > d0)
        {
            p_i1011_2_ = d0;
        }

        if (p_i1011_4_ > d0)
        {
            p_i1011_4_ = d0;
        }

        if (p_i1011_6_ > d0)
        {
            p_i1011_6_ = d0;
        }

        this.b = (int)(p_i1011_2_ * 8000.0D);
        this.c = (int)(p_i1011_4_ * 8000.0D);
        this.d = (int)(p_i1011_6_ * 8000.0D);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.b = p_a_1_.readShort();
        this.c = p_a_1_.readShort();
        this.d = p_a_1_.readShort();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.writeShort(this.b);
        p_b_1_.writeShort(this.c);
        p_b_1_.writeShort(this.d);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
