package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutOpenWindow implements Packet<PacketListenerPlayOut>
{
    private int a;
    private String b;
    private IChatBaseComponent c;
    private int d;
    private int e;

    public PacketPlayOutOpenWindow()
    {
    }

    public PacketPlayOutOpenWindow(int p_i115_1_, String p_i115_2_, IChatBaseComponent p_i115_3_)
    {
        this(p_i115_1_, p_i115_2_, p_i115_3_, 0);
    }

    public PacketPlayOutOpenWindow(int p_i116_1_, String p_i116_2_, IChatBaseComponent p_i116_3_, int p_i116_4_)
    {
        this.a = p_i116_1_;
        this.b = p_i116_2_;
        this.c = p_i116_3_;
        this.d = p_i116_4_;
    }

    public PacketPlayOutOpenWindow(int p_i117_1_, String p_i117_2_, IChatBaseComponent p_i117_3_, int p_i117_4_, int p_i117_5_)
    {
        this(p_i117_1_, p_i117_2_, p_i117_3_, p_i117_4_);
        this.e = p_i117_5_;
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readUnsignedByte();
        this.b = p_a_1_.c(32);
        this.c = p_a_1_.d();
        this.d = p_a_1_.readUnsignedByte();

        if (this.b.equals("EntityHorse"))
        {
            this.e = p_a_1_.readInt();
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
        p_b_1_.a(this.b);
        p_b_1_.a(this.c);
        p_b_1_.writeByte(this.d);

        if (this.b.equals("EntityHorse"))
        {
            p_b_1_.writeInt(this.e);
        }
    }
}
