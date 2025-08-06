package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketStatusInPing implements Packet<PacketStatusInListener>
{
    private long a;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readLong();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeLong(this.a);
    }

    public void a(PacketStatusInListener p_a_1_)
    {
        p_a_1_.a(this);
    }

    public long a()
    {
        return this.a;
    }
}
