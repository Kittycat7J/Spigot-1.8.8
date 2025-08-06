package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class CommandSpreadPlayers extends CommandAbstract
{
    public String getCommand()
    {
        return "spreadplayers";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.spreadplayers.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 6)
        {
            throw new ExceptionUsage("commands.spreadplayers.usage", new Object[0]);
        }
        else
        {
            byte b0 = 0;
            BlockPosition blockposition = p_execute_1_.getChunkCoordinates();
            double d0 = (double)blockposition.getX();
            int i = b0 + 1;
            double d1 = b(d0, p_execute_2_[b0], true);
            double d2 = b((double)blockposition.getZ(), p_execute_2_[i++], true);
            double d3 = a(p_execute_2_[i++], 0.0D);
            double d4 = a(p_execute_2_[i++], d3 + 1.0D);
            boolean flag = d(p_execute_2_[i++]);
            ArrayList arraylist = Lists.newArrayList();

            while (i < p_execute_2_.length)
            {
                String s = p_execute_2_[i++];

                if (PlayerSelector.isPattern(s))
                {
                    List list = PlayerSelector.getPlayers(p_execute_1_, s, Entity.class);

                    if (list.size() == 0)
                    {
                        throw new ExceptionEntityNotFound();
                    }

                    arraylist.addAll(list);
                }
                else
                {
                    EntityPlayer entityplayer = MinecraftServer.getServer().getPlayerList().getPlayer(s);

                    if (entityplayer == null)
                    {
                        throw new ExceptionPlayerNotFound();
                    }

                    arraylist.add(entityplayer);
                }
            }

            p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, arraylist.size());

            if (arraylist.isEmpty())
            {
                throw new ExceptionEntityNotFound();
            }
            else
            {
                p_execute_1_.sendMessage(new ChatMessage("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[] {Integer.valueOf(arraylist.size()), Double.valueOf(d4), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3)}));
                this.a(p_execute_1_, arraylist, new CommandSpreadPlayers.Location2D(d1, d2), d3, d4, ((Entity)arraylist.get(0)).world, flag);
            }
        }
    }

    private void a(ICommandListener p_a_1_, List<Entity> p_a_2_, CommandSpreadPlayers.Location2D p_a_3_, double p_a_4_, double p_a_6_, World p_a_8_, boolean p_a_9_) throws CommandException
    {
        Random random = new Random();
        double d0 = p_a_3_.a - p_a_6_;
        double d1 = p_a_3_.b - p_a_6_;
        double d2 = p_a_3_.a + p_a_6_;
        double d3 = p_a_3_.b + p_a_6_;
        CommandSpreadPlayers.Location2D[] acommandspreadplayers$location2d = this.a(random, p_a_9_ ? this.b(p_a_2_) : p_a_2_.size(), d0, d1, d2, d3);
        int i = this.a(p_a_3_, p_a_4_, p_a_8_, random, d0, d1, d2, d3, acommandspreadplayers$location2d, p_a_9_);
        double d4 = this.a(p_a_2_, p_a_8_, acommandspreadplayers$location2d, p_a_9_);
        a(p_a_1_, this, "commands.spreadplayers.success." + (p_a_9_ ? "teams" : "players"), new Object[] {Integer.valueOf(acommandspreadplayers$location2d.length), Double.valueOf(p_a_3_.a), Double.valueOf(p_a_3_.b)});

        if (acommandspreadplayers$location2d.length > 1)
        {
            p_a_1_.sendMessage(new ChatMessage("commands.spreadplayers.info." + (p_a_9_ ? "teams" : "players"), new Object[] {String.format("%.2f", new Object[]{Double.valueOf(d4)}), Integer.valueOf(i)}));
        }
    }

    private int b(List<Entity> p_b_1_)
    {
        HashSet hashset = Sets.newHashSet();

        for (Entity entity : p_b_1_)
        {
            if (entity instanceof EntityHuman)
            {
                hashset.add(((EntityHuman)entity).getScoreboardTeam());
            }
            else
            {
                hashset.add((Object)null);
            }
        }

        return hashset.size();
    }

    private int a(CommandSpreadPlayers.Location2D p_a_1_, double p_a_2_, World p_a_4_, Random p_a_5_, double p_a_6_, double p_a_8_, double p_a_10_, double p_a_12_, CommandSpreadPlayers.Location2D[] p_a_14_, boolean p_a_15_) throws CommandException
    {
        boolean flag = true;
        double d0 = 3.4028234663852886E38D;
        int i;

        for (i = 0; i < 10000 && flag; ++i)
        {
            flag = false;
            d0 = 3.4028234663852886E38D;

            for (int j = 0; j < p_a_14_.length; ++j)
            {
                CommandSpreadPlayers.Location2D commandspreadplayers$location2d = p_a_14_[j];
                int k = 0;
                CommandSpreadPlayers.Location2D commandspreadplayers$location2d1 = new CommandSpreadPlayers.Location2D();

                for (int l = 0; l < p_a_14_.length; ++l)
                {
                    if (j != l)
                    {
                        CommandSpreadPlayers.Location2D commandspreadplayers$location2d2 = p_a_14_[l];
                        double d1 = commandspreadplayers$location2d.a(commandspreadplayers$location2d2);
                        d0 = Math.min(d1, d0);

                        if (d1 < p_a_2_)
                        {
                            ++k;
                            commandspreadplayers$location2d1.a += commandspreadplayers$location2d2.a - commandspreadplayers$location2d.a;
                            commandspreadplayers$location2d1.b += commandspreadplayers$location2d2.b - commandspreadplayers$location2d.b;
                        }
                    }
                }

                if (k > 0)
                {
                    commandspreadplayers$location2d1.a /= (double)k;
                    commandspreadplayers$location2d1.b /= (double)k;
                    double d2 = (double)commandspreadplayers$location2d1.b();

                    if (d2 > 0.0D)
                    {
                        commandspreadplayers$location2d1.a();
                        commandspreadplayers$location2d.b(commandspreadplayers$location2d1);
                    }
                    else
                    {
                        commandspreadplayers$location2d.a(p_a_5_, p_a_6_, p_a_8_, p_a_10_, p_a_12_);
                    }

                    flag = true;
                }

                if (commandspreadplayers$location2d.a(p_a_6_, p_a_8_, p_a_10_, p_a_12_))
                {
                    flag = true;
                }
            }

            if (!flag)
            {
                for (CommandSpreadPlayers.Location2D commandspreadplayers$location2d3 : p_a_14_)
                {
                    if (!commandspreadplayers$location2d3.b(p_a_4_))
                    {
                        commandspreadplayers$location2d3.a(p_a_5_, p_a_6_, p_a_8_, p_a_10_, p_a_12_);
                        flag = true;
                    }
                }
            }
        }

        if (i >= 10000)
        {
            throw new CommandException("commands.spreadplayers.failure." + (p_a_15_ ? "teams" : "players"), new Object[] {Integer.valueOf(p_a_14_.length), Double.valueOf(p_a_1_.a), Double.valueOf(p_a_1_.b), String.format("%.2f", new Object[]{Double.valueOf(d0)})});
        }
        else
        {
            return i;
        }
    }

    private double a(List<Entity> p_a_1_, World p_a_2_, CommandSpreadPlayers.Location2D[] p_a_3_, boolean p_a_4_)
    {
        double d0 = 0.0D;
        int i = 0;
        HashMap hashmap = Maps.newHashMap();

        for (int j = 0; j < p_a_1_.size(); ++j)
        {
            Entity entity = (Entity)p_a_1_.get(j);
            CommandSpreadPlayers.Location2D commandspreadplayers$location2d;

            if (p_a_4_)
            {
                ScoreboardTeamBase scoreboardteambase = entity instanceof EntityHuman ? ((EntityHuman)entity).getScoreboardTeam() : null;

                if (!hashmap.containsKey(scoreboardteambase))
                {
                    hashmap.put(scoreboardteambase, p_a_3_[i++]);
                }

                commandspreadplayers$location2d = (CommandSpreadPlayers.Location2D)hashmap.get(scoreboardteambase);
            }
            else
            {
                commandspreadplayers$location2d = p_a_3_[i++];
            }

            entity.enderTeleportTo((double)((float)MathHelper.floor(commandspreadplayers$location2d.a) + 0.5F), (double)commandspreadplayers$location2d.a(p_a_2_), (double)MathHelper.floor(commandspreadplayers$location2d.b) + 0.5D);
            double d1 = Double.MAX_VALUE;

            for (int k = 0; k < p_a_3_.length; ++k)
            {
                if (commandspreadplayers$location2d != p_a_3_[k])
                {
                    double d2 = commandspreadplayers$location2d.a(p_a_3_[k]);
                    d1 = Math.min(d2, d1);
                }
            }

            d0 += d1;
        }

        d0 = d0 / (double)p_a_1_.size();
        return d0;
    }

    private CommandSpreadPlayers.Location2D[] a(Random p_a_1_, int p_a_2_, double p_a_3_, double p_a_5_, double p_a_7_, double p_a_9_)
    {
        CommandSpreadPlayers.Location2D[] acommandspreadplayers$location2d = new CommandSpreadPlayers.Location2D[p_a_2_];

        for (int i = 0; i < acommandspreadplayers$location2d.length; ++i)
        {
            CommandSpreadPlayers.Location2D commandspreadplayers$location2d = new CommandSpreadPlayers.Location2D();
            commandspreadplayers$location2d.a(p_a_1_, p_a_3_, p_a_5_, p_a_7_, p_a_9_);
            acommandspreadplayers$location2d[i] = commandspreadplayers$location2d;
        }

        return acommandspreadplayers$location2d;
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length >= 1 && p_tabComplete_2_.length <= 2 ? b(p_tabComplete_2_, 0, p_tabComplete_3_) : null;
    }

    public int compareTo(ICommand p_compareTo_1_)
    {
        return this.a(p_compareTo_1_);
    }

    static class Location2D
    {
        double a;
        double b;

        Location2D()
        {
        }

        Location2D(double p_i238_1_, double p_i238_3_)
        {
            this.a = p_i238_1_;
            this.b = p_i238_3_;
        }

        double a(CommandSpreadPlayers.Location2D p_a_1_)
        {
            double d0 = this.a - p_a_1_.a;
            double d1 = this.b - p_a_1_.b;
            return Math.sqrt(d0 * d0 + d1 * d1);
        }

        void a()
        {
            double d0 = (double)this.b();
            this.a /= d0;
            this.b /= d0;
        }

        float b()
        {
            return MathHelper.sqrt(this.a * this.a + this.b * this.b);
        }

        public void b(CommandSpreadPlayers.Location2D p_b_1_)
        {
            this.a -= p_b_1_.a;
            this.b -= p_b_1_.b;
        }

        public boolean a(double p_a_1_, double p_a_3_, double p_a_5_, double p_a_7_)
        {
            boolean flag = false;

            if (this.a < p_a_1_)
            {
                this.a = p_a_1_;
                flag = true;
            }
            else if (this.a > p_a_5_)
            {
                this.a = p_a_5_;
                flag = true;
            }

            if (this.b < p_a_3_)
            {
                this.b = p_a_3_;
                flag = true;
            }
            else if (this.b > p_a_7_)
            {
                this.b = p_a_7_;
                flag = true;
            }

            return flag;
        }

        public int a(World p_a_1_)
        {
            BlockPosition blockposition = new BlockPosition(this.a, 256.0D, this.b);

            while (blockposition.getY() > 0)
            {
                blockposition = blockposition.down();

                if (getType(p_a_1_, blockposition).getBlock().getMaterial() != Material.AIR)
                {
                    return blockposition.getY() + 1;
                }
            }

            return 257;
        }

        public boolean b(World p_b_1_)
        {
            BlockPosition blockposition = new BlockPosition(this.a, 256.0D, this.b);

            while (blockposition.getY() > 0)
            {
                blockposition = blockposition.down();
                Material material = getType(p_b_1_, blockposition).getBlock().getMaterial();

                if (material != Material.AIR)
                {
                    if (!material.isLiquid() && material != Material.FIRE)
                    {
                        return true;
                    }

                    return false;
                }
            }

            return false;
        }

        public void a(Random p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_, double p_a_8_)
        {
            this.a = MathHelper.a(p_a_1_, p_a_2_, p_a_6_);
            this.b = MathHelper.a(p_a_1_, p_a_4_, p_a_8_);
        }

        private static IBlockData getType(World p_getType_0_, BlockPosition p_getType_1_)
        {
            ((ChunkProviderServer)p_getType_0_.chunkProvider).getChunkAt(p_getType_1_.getX() >> 4, p_getType_1_.getZ() >> 4);
            return p_getType_0_.getType(p_getType_1_);
        }
    }
}
