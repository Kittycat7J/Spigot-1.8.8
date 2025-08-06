package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import org.apache.commons.lang3.Validate;

public class PacketPlayOutNamedSoundEffect implements Packet<PacketListenerPlayOut>
{
    private String a;
    private int b;
    private int c = Integer.MAX_VALUE;
    private int d;
    private float e;
    private int f;

    public PacketPlayOutNamedSoundEffect()
    {
    }

    public PacketPlayOutNamedSoundEffect(String p_i982_1_, double p_i982_2_, double p_i982_4_, double p_i982_6_, float p_i982_8_, float p_i982_9_)
    {
        Validate.notNull(p_i982_1_, "name", new Object[0]);
        this.a = p_i982_1_;
        this.b = (int)(p_i982_2_ * 8.0D);
        this.c = (int)(p_i982_4_ * 8.0D);
        this.d = (int)(p_i982_6_ * 8.0D);
        this.e = p_i982_8_;
        this.f = (int)(p_i982_9_ * 63.0F);
        p_i982_9_ = MathHelper.a(p_i982_9_, 0.0F, 255.0F);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c(256);
        this.b = p_a_1_.readInt();
        this.c = p_a_1_.readInt();
        this.d = p_a_1_.readInt();
        this.e = p_a_1_.readFloat();
        this.f = p_a_1_.readUnsignedByte();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
        p_b_1_.writeInt(this.b);
        p_b_1_.writeInt(this.c);
        p_b_1_.writeInt(this.d);
        p_b_1_.writeFloat(this.e);
        p_b_1_.writeByte(this.f);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
