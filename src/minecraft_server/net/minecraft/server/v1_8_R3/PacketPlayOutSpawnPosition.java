package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutSpawnPosition implements Packet<PacketListenerPlayOut>
{
    public BlockPosition position;

    public PacketPlayOutSpawnPosition()
    {
    }

    public PacketPlayOutSpawnPosition(BlockPosition p_i1022_1_)
    {
        this.position = p_i1022_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.position = p_a_1_.c();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.position);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
