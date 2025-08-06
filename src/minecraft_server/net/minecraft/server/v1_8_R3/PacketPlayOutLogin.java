package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutLogin implements Packet<PacketListenerPlayOut>
{
    private int a;
    private boolean b;
    private WorldSettings.EnumGamemode c;
    private int d;
    private EnumDifficulty e;
    private int f;
    private WorldType g;
    private boolean h;

    public PacketPlayOutLogin()
    {
    }

    public PacketPlayOutLogin(int p_i983_1_, WorldSettings.EnumGamemode p_i983_2_, boolean p_i983_3_, int p_i983_4_, EnumDifficulty p_i983_5_, int p_i983_6_, WorldType p_i983_7_, boolean p_i983_8_)
    {
        this.a = p_i983_1_;
        this.d = p_i983_4_;
        this.e = p_i983_5_;
        this.c = p_i983_2_;
        this.f = p_i983_6_;
        this.b = p_i983_3_;
        this.g = p_i983_7_;
        this.h = p_i983_8_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readInt();
        int i = p_a_1_.readUnsignedByte();
        this.b = (i & 8) == 8;
        i = i & -9;
        this.c = WorldSettings.EnumGamemode.getById(i);
        this.d = p_a_1_.readByte();
        this.e = EnumDifficulty.getById(p_a_1_.readUnsignedByte());
        this.f = p_a_1_.readUnsignedByte();
        this.g = WorldType.getType(p_a_1_.c(16));

        if (this.g == null)
        {
            this.g = WorldType.NORMAL;
        }

        this.h = p_a_1_.readBoolean();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeInt(this.a);
        int i = this.c.getId();

        if (this.b)
        {
            i |= 8;
        }

        p_b_1_.writeByte(i);
        p_b_1_.writeByte(this.d);
        p_b_1_.writeByte(this.e.a());
        p_b_1_.writeByte(this.f);
        p_b_1_.a(this.g.name());
        p_b_1_.writeBoolean(this.h);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
