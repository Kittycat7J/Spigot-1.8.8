package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutEntityEffect implements Packet<PacketListenerPlayOut>
{
    private int a;
    private byte b;
    private byte c;
    private int d;
    private byte e;

    public PacketPlayOutEntityEffect()
    {
    }

    public PacketPlayOutEntityEffect(int p_i1036_1_, MobEffect p_i1036_2_)
    {
        this.a = p_i1036_1_;
        this.b = (byte)(p_i1036_2_.getEffectId() & 255);
        this.c = (byte)(p_i1036_2_.getAmplifier() & 255);

        if (p_i1036_2_.getDuration() > 32767)
        {
            this.d = 32767;
        }
        else
        {
            this.d = p_i1036_2_.getDuration();
        }

        this.e = (byte)(p_i1036_2_.isShowParticles() ? 1 : 0);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.b = p_a_1_.readByte();
        this.c = p_a_1_.readByte();
        this.d = p_a_1_.e();
        this.e = p_a_1_.readByte();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.writeByte(this.b);
        p_b_1_.writeByte(this.c);
        p_b_1_.b(this.d);
        p_b_1_.writeByte(this.e);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
