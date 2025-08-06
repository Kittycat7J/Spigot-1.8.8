package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutKickDisconnect implements Packet<PacketListenerPlayOut>
{
    private IChatBaseComponent a;

    public PacketPlayOutKickDisconnect()
    {
    }

    public PacketPlayOutKickDisconnect(IChatBaseComponent p_i974_1_)
    {
        this.a = p_i974_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.d();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
