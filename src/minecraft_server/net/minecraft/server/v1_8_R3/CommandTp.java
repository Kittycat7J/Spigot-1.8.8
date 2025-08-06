package net.minecraft.server.v1_8_R3;

import java.util.EnumSet;
import java.util.List;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class CommandTp extends CommandAbstract
{
    public String getCommand()
    {
        return "tp";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.tp.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 1)
        {
            throw new ExceptionUsage("commands.tp.usage", new Object[0]);
        }
        else
        {
            byte b0 = 0;
            Object object;

            if (p_execute_2_.length != 2 && p_execute_2_.length != 4 && p_execute_2_.length != 6)
            {
                object = b(p_execute_1_);
            }
            else
            {
                object = b(p_execute_1_, p_execute_2_[0]);
                b0 = 1;
            }

            if (p_execute_2_.length != 1 && p_execute_2_.length != 2)
            {
                if (p_execute_2_.length < b0 + 3)
                {
                    throw new ExceptionUsage("commands.tp.usage", new Object[0]);
                }

                if (((Entity)object).world != null)
                {
                    int i = b0 + 1;
                    CommandAbstract.CommandNumber commandabstract$commandnumber = a(((Entity)object).locX, p_execute_2_[b0], true);
                    CommandAbstract.CommandNumber commandabstract$commandnumber1 = a(((Entity)object).locY, p_execute_2_[i++], 0, 0, false);
                    CommandAbstract.CommandNumber commandabstract$commandnumber2 = a(((Entity)object).locZ, p_execute_2_[i++], true);
                    CommandAbstract.CommandNumber commandabstract$commandnumber3 = a((double)((Entity)object).yaw, p_execute_2_.length > i ? p_execute_2_[i++] : "~", false);
                    CommandAbstract.CommandNumber commandabstract$commandnumber4 = a((double)((Entity)object).pitch, p_execute_2_.length > i ? p_execute_2_[i] : "~", false);

                    if (object instanceof EntityPlayer)
                    {
                        EnumSet enumset = EnumSet.noneOf(PacketPlayOutPosition.EnumPlayerTeleportFlags.class);

                        if (commandabstract$commandnumber.c())
                        {
                            enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.X);
                        }

                        if (commandabstract$commandnumber1.c())
                        {
                            enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y);
                        }

                        if (commandabstract$commandnumber2.c())
                        {
                            enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z);
                        }

                        if (commandabstract$commandnumber4.c())
                        {
                            enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT);
                        }

                        if (commandabstract$commandnumber3.c())
                        {
                            enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT);
                        }

                        float f3 = (float)commandabstract$commandnumber3.b();

                        if (!commandabstract$commandnumber3.c())
                        {
                            f3 = MathHelper.g(f3);
                        }

                        float f2 = (float)commandabstract$commandnumber4.b();

                        if (!commandabstract$commandnumber4.c())
                        {
                            f2 = MathHelper.g(f2);
                        }

                        if (f2 > 90.0F || f2 < -90.0F)
                        {
                            f2 = MathHelper.g(180.0F - f2);
                            f3 = MathHelper.g(f3 + 180.0F);
                        }

                        ((Entity)object).mount((Entity)null);
                        ((EntityPlayer)object).playerConnection.a(commandabstract$commandnumber.b(), commandabstract$commandnumber1.b(), commandabstract$commandnumber2.b(), f3, f2, enumset);
                        ((Entity)object).f(f3);
                    }
                    else
                    {
                        float f = (float)MathHelper.g(commandabstract$commandnumber3.a());
                        float f1 = (float)MathHelper.g(commandabstract$commandnumber4.a());

                        if (f1 > 90.0F || f1 < -90.0F)
                        {
                            f1 = MathHelper.g(180.0F - f1);
                            f = MathHelper.g(f + 180.0F);
                        }

                        ((Entity)object).setPositionRotation(commandabstract$commandnumber.a(), commandabstract$commandnumber1.a(), commandabstract$commandnumber2.a(), f, f1);
                        ((Entity)object).f(f);
                    }

                    a(p_execute_1_, this, "commands.tp.success.coordinates", new Object[] {((Entity)object).getName(), Double.valueOf(commandabstract$commandnumber.a()), Double.valueOf(commandabstract$commandnumber1.a()), Double.valueOf(commandabstract$commandnumber2.a())});
                }
            }
            else
            {
                Entity entity = b(p_execute_1_, p_execute_2_[p_execute_2_.length - 1]);

                if (((Entity)object).getBukkitEntity().teleport((org.bukkit.entity.Entity)entity.getBukkitEntity(), TeleportCause.COMMAND))
                {
                    a(p_execute_1_, this, "commands.tp.success", new Object[] {((Entity)object).getName(), entity.getName()});
                }
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length != 1 && p_tabComplete_2_.length != 2 ? null : a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 0;
    }

    public int compareTo(ICommand p_compareTo_1_)
    {
        return this.a(p_compareTo_1_);
    }
}
