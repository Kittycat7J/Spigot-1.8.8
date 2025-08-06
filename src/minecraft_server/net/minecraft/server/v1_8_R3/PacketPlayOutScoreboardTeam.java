package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;

public class PacketPlayOutScoreboardTeam implements Packet<PacketListenerPlayOut>
{
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e;
    private int f;
    private Collection<String> g;
    private int h;
    private int i;

    public PacketPlayOutScoreboardTeam()
    {
        this.e = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
        this.f = -1;
        this.g = Lists.<String>newArrayList();
    }

    public PacketPlayOutScoreboardTeam(ScoreboardTeam p_i1016_1_, int p_i1016_2_)
    {
        this.e = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
        this.f = -1;
        this.g = Lists.<String>newArrayList();
        this.a = p_i1016_1_.getName();
        this.h = p_i1016_2_;

        if (p_i1016_2_ == 0 || p_i1016_2_ == 2)
        {
            this.b = p_i1016_1_.getDisplayName();
            this.c = p_i1016_1_.getPrefix();
            this.d = p_i1016_1_.getSuffix();
            this.i = p_i1016_1_.packOptionData();
            this.e = p_i1016_1_.getNameTagVisibility().e;
            this.f = p_i1016_1_.l().b();
        }

        if (p_i1016_2_ == 0)
        {
            this.g.addAll(p_i1016_1_.getPlayerNameSet());
        }
    }

    public PacketPlayOutScoreboardTeam(ScoreboardTeam p_i1017_1_, Collection<String> p_i1017_2_, int p_i1017_3_)
    {
        this.e = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
        this.f = -1;
        this.g = Lists.<String>newArrayList();

        if (p_i1017_3_ != 3 && p_i1017_3_ != 4)
        {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        else if (p_i1017_2_ != null && !p_i1017_2_.isEmpty())
        {
            this.h = p_i1017_3_;
            this.a = p_i1017_1_.getName();
            this.g.addAll(p_i1017_2_);
        }
        else
        {
            throw new IllegalArgumentException("Players cannot be null/empty");
        }
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c(16);
        this.h = p_a_1_.readByte();

        if (this.h == 0 || this.h == 2)
        {
            this.b = p_a_1_.c(32);
            this.c = p_a_1_.c(16);
            this.d = p_a_1_.c(16);
            this.i = p_a_1_.readByte();
            this.e = p_a_1_.c(32);
            this.f = p_a_1_.readByte();
        }

        if (this.h == 0 || this.h == 3 || this.h == 4)
        {
            int ix = p_a_1_.e();

            for (int j = 0; j < ix; ++j)
            {
                this.g.add(p_a_1_.c(40));
            }
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
        p_b_1_.writeByte(this.h);

        if (this.h == 0 || this.h == 2)
        {
            p_b_1_.a(this.b);
            p_b_1_.a(this.c);
            p_b_1_.a(this.d);
            p_b_1_.writeByte(this.i);
            p_b_1_.a(this.e);
            p_b_1_.writeByte(this.f);
        }

        if (this.h == 0 || this.h == 3 || this.h == 4)
        {
            p_b_1_.b(this.g.size());

            for (String s : this.g)
            {
                p_b_1_.a(s);
            }
        }
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
