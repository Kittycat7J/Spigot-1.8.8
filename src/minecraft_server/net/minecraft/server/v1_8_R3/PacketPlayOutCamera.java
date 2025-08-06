package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutCamera implements Packet<PacketListenerPlayOut>
{
    public int a;

    public PacketPlayOutCamera()
    {
    }

    public PacketPlayOutCamera(Entity p_i1005_1_)
    {
        this.a = p_i1005_1_.getId();
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
