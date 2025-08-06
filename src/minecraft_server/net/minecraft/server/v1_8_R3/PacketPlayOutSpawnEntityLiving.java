package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.List;

public class PacketPlayOutSpawnEntityLiving implements Packet<PacketListenerPlayOut>
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private byte i;
    private byte j;
    private byte k;
    private DataWatcher l;
    private List<DataWatcher.WatchableObject> m;

    public PacketPlayOutSpawnEntityLiving()
    {
    }

    public PacketPlayOutSpawnEntityLiving(EntityLiving p_i954_1_)
    {
        this.a = p_i954_1_.getId();
        this.b = (byte)EntityTypes.a(p_i954_1_);
        this.c = MathHelper.floor(p_i954_1_.locX * 32.0D);
        this.d = MathHelper.floor(p_i954_1_.locY * 32.0D);
        this.e = MathHelper.floor(p_i954_1_.locZ * 32.0D);
        this.i = (byte)((int)(p_i954_1_.yaw * 256.0F / 360.0F));
        this.j = (byte)((int)(p_i954_1_.pitch * 256.0F / 360.0F));
        this.k = (byte)((int)(p_i954_1_.aK * 256.0F / 360.0F));
        double d0 = 3.9D;
        double d1 = p_i954_1_.motX;
        double d2 = p_i954_1_.motY;
        double d3 = p_i954_1_.motZ;

        if (d1 < -d0)
        {
            d1 = -d0;
        }

        if (d2 < -d0)
        {
            d2 = -d0;
        }

        if (d3 < -d0)
        {
            d3 = -d0;
        }

        if (d1 > d0)
        {
            d1 = d0;
        }

        if (d2 > d0)
        {
            d2 = d0;
        }

        if (d3 > d0)
        {
            d3 = d0;
        }

        this.f = (int)(d1 * 8000.0D);
        this.g = (int)(d2 * 8000.0D);
        this.h = (int)(d3 * 8000.0D);
        this.l = p_i954_1_.getDataWatcher();
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.b = p_a_1_.readByte() & 255;
        this.c = p_a_1_.readInt();
        this.d = p_a_1_.readInt();
        this.e = p_a_1_.readInt();
        this.i = p_a_1_.readByte();
        this.j = p_a_1_.readByte();
        this.k = p_a_1_.readByte();
        this.f = p_a_1_.readShort();
        this.g = p_a_1_.readShort();
        this.h = p_a_1_.readShort();
        this.m = DataWatcher.b(p_a_1_);
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.writeByte(this.b & 255);
        p_b_1_.writeInt(this.c);
        p_b_1_.writeInt(this.d);
        p_b_1_.writeInt(this.e);
        p_b_1_.writeByte(this.i);
        p_b_1_.writeByte(this.j);
        p_b_1_.writeByte(this.k);
        p_b_1_.writeShort(this.f);
        p_b_1_.writeShort(this.g);
        p_b_1_.writeShort(this.h);
        this.l.a(p_b_1_);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
