package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutWorldParticles implements Packet<PacketListenerPlayOut>
{
    private EnumParticle a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;
    private float g;
    private float h;
    private int i;
    private boolean j;
    private int[] k;

    public PacketPlayOutWorldParticles()
    {
    }

    public PacketPlayOutWorldParticles(EnumParticle p_i981_1_, boolean p_i981_2_, float p_i981_3_, float p_i981_4_, float p_i981_5_, float p_i981_6_, float p_i981_7_, float p_i981_8_, float p_i981_9_, int p_i981_10_, int... p_i981_11_)
    {
        this.a = p_i981_1_;
        this.j = p_i981_2_;
        this.b = p_i981_3_;
        this.c = p_i981_4_;
        this.d = p_i981_5_;
        this.e = p_i981_6_;
        this.f = p_i981_7_;
        this.g = p_i981_8_;
        this.h = p_i981_9_;
        this.i = p_i981_10_;
        this.k = p_i981_11_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = EnumParticle.a(p_a_1_.readInt());

        if (this.a == null)
        {
            this.a = EnumParticle.BARRIER;
        }

        this.j = p_a_1_.readBoolean();
        this.b = p_a_1_.readFloat();
        this.c = p_a_1_.readFloat();
        this.d = p_a_1_.readFloat();
        this.e = p_a_1_.readFloat();
        this.f = p_a_1_.readFloat();
        this.g = p_a_1_.readFloat();
        this.h = p_a_1_.readFloat();
        this.i = p_a_1_.readInt();
        int ix = this.a.d();
        this.k = new int[ix];

        for (int jx = 0; jx < ix; ++jx)
        {
            this.k[jx] = p_a_1_.e();
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeInt(this.a.c());
        p_b_1_.writeBoolean(this.j);
        p_b_1_.writeFloat(this.b);
        p_b_1_.writeFloat(this.c);
        p_b_1_.writeFloat(this.d);
        p_b_1_.writeFloat(this.e);
        p_b_1_.writeFloat(this.f);
        p_b_1_.writeFloat(this.g);
        p_b_1_.writeFloat(this.h);
        p_b_1_.writeInt(this.i);
        int ix = this.a.d();

        for (int jx = 0; jx < ix; ++jx)
        {
            p_b_1_.b(this.k[jx]);
        }
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
