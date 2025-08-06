package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutBlockAction implements Packet<PacketListenerPlayOut>
{
    private BlockPosition a;
    private int b;
    private int c;
    private Block d;

    public PacketPlayOutBlockAction()
    {
    }

    public PacketPlayOutBlockAction(BlockPosition p_i961_1_, Block p_i961_2_, int p_i961_3_, int p_i961_4_)
    {
        this.a = p_i961_1_;
        this.b = p_i961_3_;
        this.c = p_i961_4_;
        this.d = p_i961_2_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c();
        this.b = p_a_1_.readUnsignedByte();
        this.c = p_a_1_.readUnsignedByte();
        this.d = Block.getById(p_a_1_.e() & 4095);
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
        p_b_1_.writeByte(this.b);
        p_b_1_.writeByte(this.c);
        p_b_1_.b(Block.getId(this.d) & 4095);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
