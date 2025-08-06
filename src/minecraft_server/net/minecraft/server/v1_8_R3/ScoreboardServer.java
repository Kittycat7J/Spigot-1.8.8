package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ScoreboardServer extends Scoreboard
{
    private final MinecraftServer a;
    private final Set<ScoreboardObjective> b = Sets.<ScoreboardObjective>newHashSet();
    private PersistentScoreboard c;

    public ScoreboardServer(MinecraftServer p_i341_1_)
    {
        this.a = p_i341_1_;
    }

    public void handleScoreChanged(ScoreboardScore p_handleScoreChanged_1_)
    {
        super.handleScoreChanged(p_handleScoreChanged_1_);

        if (this.b.contains(p_handleScoreChanged_1_.getObjective()))
        {
            this.sendAll(new PacketPlayOutScoreboardScore(p_handleScoreChanged_1_));
        }

        this.b();
    }

    public void handlePlayerRemoved(String p_handlePlayerRemoved_1_)
    {
        super.handlePlayerRemoved(p_handlePlayerRemoved_1_);
        this.sendAll(new PacketPlayOutScoreboardScore(p_handlePlayerRemoved_1_));
        this.b();
    }

    public void a(String p_a_1_, ScoreboardObjective p_a_2_)
    {
        super.a(p_a_1_, p_a_2_);
        this.sendAll(new PacketPlayOutScoreboardScore(p_a_1_, p_a_2_));
        this.b();
    }

    public void setDisplaySlot(int p_setDisplaySlot_1_, ScoreboardObjective p_setDisplaySlot_2_)
    {
        ScoreboardObjective scoreboardobjective = this.getObjectiveForSlot(p_setDisplaySlot_1_);
        super.setDisplaySlot(p_setDisplaySlot_1_, p_setDisplaySlot_2_);

        if (scoreboardobjective != p_setDisplaySlot_2_ && scoreboardobjective != null)
        {
            if (this.h(scoreboardobjective) > 0)
            {
                this.sendAll(new PacketPlayOutScoreboardDisplayObjective(p_setDisplaySlot_1_, p_setDisplaySlot_2_));
            }
            else
            {
                this.g(scoreboardobjective);
            }
        }

        if (p_setDisplaySlot_2_ != null)
        {
            if (this.b.contains(p_setDisplaySlot_2_))
            {
                this.sendAll(new PacketPlayOutScoreboardDisplayObjective(p_setDisplaySlot_1_, p_setDisplaySlot_2_));
            }
            else
            {
                this.e(p_setDisplaySlot_2_);
            }
        }

        this.b();
    }

    public boolean addPlayerToTeam(String p_addPlayerToTeam_1_, String p_addPlayerToTeam_2_)
    {
        if (super.addPlayerToTeam(p_addPlayerToTeam_1_, p_addPlayerToTeam_2_))
        {
            ScoreboardTeam scoreboardteam = this.getTeam(p_addPlayerToTeam_2_);
            this.sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, Arrays.asList(new String[] {p_addPlayerToTeam_1_}), 3));
            this.b();
            return true;
        }
        else
        {
            return false;
        }
    }

    public void removePlayerFromTeam(String p_removePlayerFromTeam_1_, ScoreboardTeam p_removePlayerFromTeam_2_)
    {
        super.removePlayerFromTeam(p_removePlayerFromTeam_1_, p_removePlayerFromTeam_2_);
        this.sendAll(new PacketPlayOutScoreboardTeam(p_removePlayerFromTeam_2_, Arrays.asList(new String[] {p_removePlayerFromTeam_1_}), 4));
        this.b();
    }

    public void handleObjectiveAdded(ScoreboardObjective p_handleObjectiveAdded_1_)
    {
        super.handleObjectiveAdded(p_handleObjectiveAdded_1_);
        this.b();
    }

    public void handleObjectiveChanged(ScoreboardObjective p_handleObjectiveChanged_1_)
    {
        super.handleObjectiveChanged(p_handleObjectiveChanged_1_);

        if (this.b.contains(p_handleObjectiveChanged_1_))
        {
            this.sendAll(new PacketPlayOutScoreboardObjective(p_handleObjectiveChanged_1_, 2));
        }

        this.b();
    }

    public void handleObjectiveRemoved(ScoreboardObjective p_handleObjectiveRemoved_1_)
    {
        super.handleObjectiveRemoved(p_handleObjectiveRemoved_1_);

        if (this.b.contains(p_handleObjectiveRemoved_1_))
        {
            this.g(p_handleObjectiveRemoved_1_);
        }

        this.b();
    }

    public void handleTeamAdded(ScoreboardTeam p_handleTeamAdded_1_)
    {
        super.handleTeamAdded(p_handleTeamAdded_1_);
        this.sendAll(new PacketPlayOutScoreboardTeam(p_handleTeamAdded_1_, 0));
        this.b();
    }

    public void handleTeamChanged(ScoreboardTeam p_handleTeamChanged_1_)
    {
        super.handleTeamChanged(p_handleTeamChanged_1_);
        this.sendAll(new PacketPlayOutScoreboardTeam(p_handleTeamChanged_1_, 2));
        this.b();
    }

    public void handleTeamRemoved(ScoreboardTeam p_handleTeamRemoved_1_)
    {
        super.handleTeamRemoved(p_handleTeamRemoved_1_);
        this.sendAll(new PacketPlayOutScoreboardTeam(p_handleTeamRemoved_1_, 1));
        this.b();
    }

    public void a(PersistentScoreboard p_a_1_)
    {
        this.c = p_a_1_;
    }

    protected void b()
    {
        if (this.c != null)
        {
            this.c.c();
        }
    }

    public List<Packet> getScoreboardScorePacketsForObjective(ScoreboardObjective p_getScoreboardScorePacketsForObjective_1_)
    {
        ArrayList arraylist = Lists.newArrayList();
        arraylist.add(new PacketPlayOutScoreboardObjective(p_getScoreboardScorePacketsForObjective_1_, 0));

        for (int i = 0; i < 19; ++i)
        {
            if (this.getObjectiveForSlot(i) == p_getScoreboardScorePacketsForObjective_1_)
            {
                arraylist.add(new PacketPlayOutScoreboardDisplayObjective(i, p_getScoreboardScorePacketsForObjective_1_));
            }
        }

        for (ScoreboardScore scoreboardscore : this.getScoresForObjective(p_getScoreboardScorePacketsForObjective_1_))
        {
            arraylist.add(new PacketPlayOutScoreboardScore(scoreboardscore));
        }

        return arraylist;
    }

    public void e(ScoreboardObjective p_e_1_)
    {
        List list = this.getScoreboardScorePacketsForObjective(p_e_1_);

        for (EntityPlayer entityplayer : this.a.getPlayerList().v())
        {
            if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this)
            {
                for (Packet packet : list)
                {
                    entityplayer.playerConnection.sendPacket(packet);
                }
            }
        }

        this.b.add(p_e_1_);
    }

    public List<Packet> f(ScoreboardObjective p_f_1_)
    {
        ArrayList arraylist = Lists.newArrayList();
        arraylist.add(new PacketPlayOutScoreboardObjective(p_f_1_, 1));

        for (int i = 0; i < 19; ++i)
        {
            if (this.getObjectiveForSlot(i) == p_f_1_)
            {
                arraylist.add(new PacketPlayOutScoreboardDisplayObjective(i, p_f_1_));
            }
        }

        return arraylist;
    }

    public void g(ScoreboardObjective p_g_1_)
    {
        List list = this.f(p_g_1_);

        for (EntityPlayer entityplayer : this.a.getPlayerList().v())
        {
            if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this)
            {
                for (Packet packet : list)
                {
                    entityplayer.playerConnection.sendPacket(packet);
                }
            }
        }

        this.b.remove(p_g_1_);
    }

    public int h(ScoreboardObjective p_h_1_)
    {
        int i = 0;

        for (int j = 0; j < 19; ++j)
        {
            if (this.getObjectiveForSlot(j) == p_h_1_)
            {
                ++i;
            }
        }

        return i;
    }

    private void sendAll(Packet p_sendAll_1_)
    {
        for (EntityPlayer entityplayer : this.a.getPlayerList().players)
        {
            if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this)
            {
                entityplayer.playerConnection.sendPacket(p_sendAll_1_);
            }
        }
    }
}
