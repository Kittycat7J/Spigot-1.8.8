package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInArmAnimation implements Packet<PacketListenerPlayIn>
{
    public long timestamp;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.timestamp = System.currentTimeMillis();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
    }

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }
}
