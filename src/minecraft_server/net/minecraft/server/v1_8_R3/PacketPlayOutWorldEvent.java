package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutWorldEvent implements Packet<PacketListenerPlayOut>
{
    private int a;
    private BlockPosition b;
    private int c;
    private boolean d;

    public PacketPlayOutWorldEvent()
    {
    }

    public PacketPlayOutWorldEvent(int p_i980_1_, BlockPosition p_i980_2_, int p_i980_3_, boolean p_i980_4_)
    {
        this.a = p_i980_1_;
        this.b = p_i980_2_;
        this.c = p_i980_3_;
        this.d = p_i980_4_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readInt();
        this.b = p_a_1_.c();
        this.c = p_a_1_.readInt();
        this.d = p_a_1_.readBoolean();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeInt(this.a);
        p_b_1_.a(this.b);
        p_b_1_.writeInt(this.c);
        p_b_1_.writeBoolean(this.d);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
