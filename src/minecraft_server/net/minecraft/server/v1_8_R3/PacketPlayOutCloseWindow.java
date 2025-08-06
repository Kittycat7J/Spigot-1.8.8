package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutCloseWindow implements Packet<PacketListenerPlayOut>
{
    private int a;

    public PacketPlayOutCloseWindow()
    {
    }

    public PacketPlayOutCloseWindow(int p_i969_1_)
    {
        this.a = p_i969_1_;
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readUnsignedByte();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
    }
}
