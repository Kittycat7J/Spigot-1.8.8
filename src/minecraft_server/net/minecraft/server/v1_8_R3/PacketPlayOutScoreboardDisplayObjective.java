package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutScoreboardDisplayObjective implements Packet<PacketListenerPlayOut>
{
    private int a;
    private String b;

    public PacketPlayOutScoreboardDisplayObjective()
    {
    }

    public PacketPlayOutScoreboardDisplayObjective(int p_i1007_1_, ScoreboardObjective p_i1007_2_)
    {
        this.a = p_i1007_1_;

        if (p_i1007_2_ == null)
        {
            this.b = "";
        }
        else
        {
            this.b = p_i1007_2_.getName();
        }
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readByte();
        this.b = p_a_1_.c(16);
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
        p_b_1_.a(this.b);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
