package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInSteerVehicle implements Packet<PacketListenerPlayIn>
{
    private float a;
    private float b;
    private boolean c;
    private boolean d;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readFloat();
        this.b = p_a_1_.readFloat();
        byte b0 = p_a_1_.readByte();
        this.c = (b0 & 1) > 0;
        this.d = (b0 & 2) > 0;
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeFloat(this.a);
        p_b_1_.writeFloat(this.b);
        byte b0 = 0;

        if (this.c)
        {
            b0 = (byte)(b0 | 1);
        }

        if (this.d)
        {
            b0 = (byte)(b0 | 2);
        }

        p_b_1_.writeByte(b0);
    }

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public float a()
    {
        return this.a;
    }

    public float b()
    {
        return this.b;
    }

    public boolean c()
    {
        return this.c;
    }

    public boolean d()
    {
        return this.d;
    }
}
