package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutTileEntityData implements Packet<PacketListenerPlayOut>
{
    private BlockPosition a;
    private int b;
    private NBTTagCompound c;

    public PacketPlayOutTileEntityData()
    {
    }

    public PacketPlayOutTileEntityData(BlockPosition p_i960_1_, int p_i960_2_, NBTTagCompound p_i960_3_)
    {
        this.a = p_i960_1_;
        this.b = p_i960_2_;
        this.c = p_i960_3_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c();
        this.b = p_a_1_.readUnsignedByte();
        this.c = p_a_1_.h();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
        p_b_1_.writeByte((byte)this.b);
        p_b_1_.a(this.c);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
