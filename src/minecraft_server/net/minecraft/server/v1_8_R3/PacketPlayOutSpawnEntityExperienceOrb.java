package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutSpawnEntityExperienceOrb implements Packet<PacketListenerPlayOut>
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;

    public PacketPlayOutSpawnEntityExperienceOrb()
    {
    }

    public PacketPlayOutSpawnEntityExperienceOrb(EntityExperienceOrb p_i952_1_)
    {
        this.a = p_i952_1_.getId();
        this.b = MathHelper.floor(p_i952_1_.locX * 32.0D);
        this.c = MathHelper.floor(p_i952_1_.locY * 32.0D);
        this.d = MathHelper.floor(p_i952_1_.locZ * 32.0D);
        this.e = p_i952_1_.j();
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.b = p_a_1_.readInt();
        this.c = p_a_1_.readInt();
        this.d = p_a_1_.readInt();
        this.e = p_a_1_.readShort();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.writeInt(this.b);
        p_b_1_.writeInt(this.c);
        p_b_1_.writeInt(this.d);
        p_b_1_.writeShort(this.e);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
