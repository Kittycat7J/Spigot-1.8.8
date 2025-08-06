package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutBed implements Packet<PacketListenerPlayOut>
{
    private int a;
    private BlockPosition b;

    public PacketPlayOutBed()
    {
    }

    public PacketPlayOutBed(EntityHuman p_i997_1_, BlockPosition p_i997_2_)
    {
        this.a = p_i997_1_.getId();
        this.b = p_i997_2_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.b = p_a_1_.c();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.a(this.b);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
