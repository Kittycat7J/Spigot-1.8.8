package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutSpawnEntityPainting implements Packet<PacketListenerPlayOut>
{
    private int a;
    private BlockPosition b;
    private EnumDirection c;
    private String d;

    public PacketPlayOutSpawnEntityPainting()
    {
    }

    public PacketPlayOutSpawnEntityPainting(EntityPainting p_i955_1_)
    {
        this.a = p_i955_1_.getId();
        this.b = p_i955_1_.getBlockPosition();
        this.c = p_i955_1_.direction;
        this.d = p_i955_1_.art.B;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.d = p_a_1_.c(EntityPainting.EnumArt.A);
        this.b = p_a_1_.c();
        this.c = EnumDirection.fromType2(p_a_1_.readUnsignedByte());
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.a(this.d);
        p_b_1_.a(this.b);
        p_b_1_.writeByte(this.c.b());
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
