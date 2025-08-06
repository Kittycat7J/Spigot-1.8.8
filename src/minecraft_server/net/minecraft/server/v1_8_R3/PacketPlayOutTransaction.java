package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutTransaction implements Packet<PacketListenerPlayOut>
{
    private int a;
    private short b;
    private boolean c;

    public PacketPlayOutTransaction()
    {
    }

    public PacketPlayOutTransaction(int p_i968_1_, short p_i968_2_, boolean p_i968_3_)
    {
        this.a = p_i968_1_;
        this.b = p_i968_2_;
        this.c = p_i968_3_;
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readUnsignedByte();
        this.b = p_a_1_.readShort();
        this.c = p_a_1_.readBoolean();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
        p_b_1_.writeShort(this.b);
        p_b_1_.writeBoolean(this.c);
    }
}
