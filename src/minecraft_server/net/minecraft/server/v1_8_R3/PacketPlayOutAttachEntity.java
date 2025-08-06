package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutAttachEntity implements Packet<PacketListenerPlayOut>
{
    private int a;
    private int b;
    private int c;

    public PacketPlayOutAttachEntity()
    {
    }

    public PacketPlayOutAttachEntity(int p_i1009_1_, Entity p_i1009_2_, Entity p_i1009_3_)
    {
        this.a = p_i1009_1_;
        this.b = p_i1009_2_.getId();
        this.c = p_i1009_3_ != null ? p_i1009_3_.getId() : -1;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.b = p_a_1_.readInt();
        this.c = p_a_1_.readInt();
        this.a = p_a_1_.readUnsignedByte();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeInt(this.b);
        p_b_1_.writeInt(this.c);
        p_b_1_.writeByte(this.a);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
