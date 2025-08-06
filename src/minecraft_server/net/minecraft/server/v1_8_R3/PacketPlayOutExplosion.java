package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;

public class PacketPlayOutExplosion implements Packet<PacketListenerPlayOut>
{
    private double a;
    private double b;
    private double c;
    private float d;
    private List<BlockPosition> e;
    private float f;
    private float g;
    private float h;

    public PacketPlayOutExplosion()
    {
    }

    public PacketPlayOutExplosion(double p_i977_1_, double p_i977_3_, double p_i977_5_, float p_i977_7_, List<BlockPosition> p_i977_8_, Vec3D p_i977_9_)
    {
        this.a = p_i977_1_;
        this.b = p_i977_3_;
        this.c = p_i977_5_;
        this.d = p_i977_7_;
        this.e = Lists.newArrayList(p_i977_8_);

        if (p_i977_9_ != null)
        {
            this.f = (float)p_i977_9_.a;
            this.g = (float)p_i977_9_.b;
            this.h = (float)p_i977_9_.c;
        }
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = (double)p_a_1_.readFloat();
        this.b = (double)p_a_1_.readFloat();
        this.c = (double)p_a_1_.readFloat();
        this.d = p_a_1_.readFloat();
        int i = p_a_1_.readInt();
        this.e = Lists.<BlockPosition>newArrayListWithCapacity(i);
        int j = (int)this.a;
        int k = (int)this.b;
        int l = (int)this.c;

        for (int i1 = 0; i1 < i; ++i1)
        {
            int j1 = p_a_1_.readByte() + j;
            int k1 = p_a_1_.readByte() + k;
            int l1 = p_a_1_.readByte() + l;
            this.e.add(new BlockPosition(j1, k1, l1));
        }

        this.f = p_a_1_.readFloat();
        this.g = p_a_1_.readFloat();
        this.h = p_a_1_.readFloat();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeFloat((float)this.a);
        p_b_1_.writeFloat((float)this.b);
        p_b_1_.writeFloat((float)this.c);
        p_b_1_.writeFloat(this.d);
        p_b_1_.writeInt(this.e.size());
        int i = (int)this.a;
        int j = (int)this.b;
        int k = (int)this.c;

        for (BlockPosition blockposition : this.e)
        {
            int l = blockposition.getX() - i;
            int i1 = blockposition.getY() - j;
            int j1 = blockposition.getZ() - k;
            p_b_1_.writeByte(l);
            p_b_1_.writeByte(i1);
            p_b_1_.writeByte(j1);
        }

        p_b_1_.writeFloat(this.f);
        p_b_1_.writeFloat(this.g);
        p_b_1_.writeFloat(this.h);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
