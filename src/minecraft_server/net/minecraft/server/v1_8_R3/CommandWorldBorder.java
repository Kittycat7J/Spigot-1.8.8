package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandWorldBorder extends CommandAbstract
{
    public String getCommand()
    {
        return "worldborder";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.worldborder.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 1)
        {
            throw new ExceptionUsage("commands.worldborder.usage", new Object[0]);
        }
        else
        {
            WorldBorder worldborder = this.d();

            if (p_execute_2_[0].equals("set"))
            {
                if (p_execute_2_.length != 2 && p_execute_2_.length != 3)
                {
                    throw new ExceptionUsage("commands.worldborder.set.usage", new Object[0]);
                }

                double d0 = worldborder.j();
                double d1 = a(p_execute_2_[1], 1.0D, 6.0E7D);
                long i = p_execute_2_.length > 2 ? a(p_execute_2_[2], 0L, 9223372036854775L) * 1000L : 0L;

                if (i > 0L)
                {
                    worldborder.transitionSizeBetween(d0, d1, i);

                    if (d0 > d1)
                    {
                        a(p_execute_1_, this, "commands.worldborder.setSlowly.shrink.success", new Object[] {String.format("%.1f", new Object[]{Double.valueOf(d1)}), String.format("%.1f", new Object[]{Double.valueOf(d0)}), Long.toString(i / 1000L)});
                    }
                    else
                    {
                        a(p_execute_1_, this, "commands.worldborder.setSlowly.grow.success", new Object[] {String.format("%.1f", new Object[]{Double.valueOf(d1)}), String.format("%.1f", new Object[]{Double.valueOf(d0)}), Long.toString(i / 1000L)});
                    }
                }
                else
                {
                    worldborder.setSize(d1);
                    a(p_execute_1_, this, "commands.worldborder.set.success", new Object[] {String.format("%.1f", new Object[]{Double.valueOf(d1)}), String.format("%.1f", new Object[]{Double.valueOf(d0)})});
                }
            }
            else if (p_execute_2_[0].equals("add"))
            {
                if (p_execute_2_.length != 2 && p_execute_2_.length != 3)
                {
                    throw new ExceptionUsage("commands.worldborder.add.usage", new Object[0]);
                }

                double d4 = worldborder.getSize();
                double d8 = d4 + a(p_execute_2_[1], -d4, 6.0E7D - d4);
                long k = worldborder.i() + (p_execute_2_.length > 2 ? a(p_execute_2_[2], 0L, 9223372036854775L) * 1000L : 0L);

                if (k > 0L)
                {
                    worldborder.transitionSizeBetween(d4, d8, k);

                    if (d4 > d8)
                    {
                        a(p_execute_1_, this, "commands.worldborder.setSlowly.shrink.success", new Object[] {String.format("%.1f", new Object[]{Double.valueOf(d8)}), String.format("%.1f", new Object[]{Double.valueOf(d4)}), Long.toString(k / 1000L)});
                    }
                    else
                    {
                        a(p_execute_1_, this, "commands.worldborder.setSlowly.grow.success", new Object[] {String.format("%.1f", new Object[]{Double.valueOf(d8)}), String.format("%.1f", new Object[]{Double.valueOf(d4)}), Long.toString(k / 1000L)});
                    }
                }
                else
                {
                    worldborder.setSize(d8);
                    a(p_execute_1_, this, "commands.worldborder.set.success", new Object[] {String.format("%.1f", new Object[]{Double.valueOf(d8)}), String.format("%.1f", new Object[]{Double.valueOf(d4)})});
                }
            }
            else if (p_execute_2_[0].equals("center"))
            {
                if (p_execute_2_.length != 3)
                {
                    throw new ExceptionUsage("commands.worldborder.center.usage", new Object[0]);
                }

                BlockPosition blockposition = p_execute_1_.getChunkCoordinates();
                double d2 = b((double)blockposition.getX() + 0.5D, p_execute_2_[1], true);
                double d3 = b((double)blockposition.getZ() + 0.5D, p_execute_2_[2], true);
                worldborder.setCenter(d2, d3);
                a(p_execute_1_, this, "commands.worldborder.center.success", new Object[] {Double.valueOf(d2), Double.valueOf(d3)});
            }
            else if (p_execute_2_[0].equals("damage"))
            {
                if (p_execute_2_.length < 2)
                {
                    throw new ExceptionUsage("commands.worldborder.damage.usage", new Object[0]);
                }

                if (p_execute_2_[1].equals("buffer"))
                {
                    if (p_execute_2_.length != 3)
                    {
                        throw new ExceptionUsage("commands.worldborder.damage.buffer.usage", new Object[0]);
                    }

                    double d5 = a(p_execute_2_[2], 0.0D);
                    double d9 = worldborder.getDamageBuffer();
                    worldborder.setDamageBuffer(d5);
                    a(p_execute_1_, this, "commands.worldborder.damage.buffer.success", new Object[] {String.format("%.1f", new Object[]{Double.valueOf(d5)}), String.format("%.1f", new Object[]{Double.valueOf(d9)})});
                }
                else if (p_execute_2_[1].equals("amount"))
                {
                    if (p_execute_2_.length != 3)
                    {
                        throw new ExceptionUsage("commands.worldborder.damage.amount.usage", new Object[0]);
                    }

                    double d6 = a(p_execute_2_[2], 0.0D);
                    double d10 = worldborder.getDamageAmount();
                    worldborder.setDamageAmount(d6);
                    a(p_execute_1_, this, "commands.worldborder.damage.amount.success", new Object[] {String.format("%.2f", new Object[]{Double.valueOf(d6)}), String.format("%.2f", new Object[]{Double.valueOf(d10)})});
                }
            }
            else if (p_execute_2_[0].equals("warning"))
            {
                if (p_execute_2_.length < 2)
                {
                    throw new ExceptionUsage("commands.worldborder.warning.usage", new Object[0]);
                }

                int l = a(p_execute_2_[2], 0);

                if (p_execute_2_[1].equals("time"))
                {
                    if (p_execute_2_.length != 3)
                    {
                        throw new ExceptionUsage("commands.worldborder.warning.time.usage", new Object[0]);
                    }

                    int j = worldborder.getWarningTime();
                    worldborder.setWarningTime(l);
                    a(p_execute_1_, this, "commands.worldborder.warning.time.success", new Object[] {Integer.valueOf(l), Integer.valueOf(j)});
                }
                else if (p_execute_2_[1].equals("distance"))
                {
                    if (p_execute_2_.length != 3)
                    {
                        throw new ExceptionUsage("commands.worldborder.warning.distance.usage", new Object[0]);
                    }

                    int i1 = worldborder.getWarningDistance();
                    worldborder.setWarningDistance(l);
                    a(p_execute_1_, this, "commands.worldborder.warning.distance.success", new Object[] {Integer.valueOf(l), Integer.valueOf(i1)});
                }
            }
            else
            {
                if (!p_execute_2_[0].equals("get"))
                {
                    throw new ExceptionUsage("commands.worldborder.usage", new Object[0]);
                }

                double d7 = worldborder.getSize();
                p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, MathHelper.floor(d7 + 0.5D));
                p_execute_1_.sendMessage(new ChatMessage("commands.worldborder.get.success", new Object[] {String.format("%.0f", new Object[]{Double.valueOf(d7)})}));
            }
        }
    }

    protected WorldBorder d()
    {
        return MinecraftServer.getServer().worldServer[0].getWorldBorder();
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, new String[] {"set", "center", "damage", "warning", "add", "get"}): (p_tabComplete_2_.length == 2 && p_tabComplete_2_[0].equals("damage") ? a(p_tabComplete_2_, new String[] {"buffer", "amount"}): (p_tabComplete_2_.length >= 2 && p_tabComplete_2_.length <= 3 && p_tabComplete_2_[0].equals("center") ? b(p_tabComplete_2_, 1, p_tabComplete_3_) : (p_tabComplete_2_.length == 2 && p_tabComplete_2_[0].equals("warning") ? a(p_tabComplete_2_, new String[] {"time", "distance"}): null)));
    }
}
