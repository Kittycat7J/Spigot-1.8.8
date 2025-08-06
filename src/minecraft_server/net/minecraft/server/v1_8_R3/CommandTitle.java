package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonParseException;
import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandTitle extends CommandAbstract
{
    private static final Logger a = LogManager.getLogger();

    public String getCommand()
    {
        return "title";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.title.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 2)
        {
            throw new ExceptionUsage("commands.title.usage", new Object[0]);
        }
        else
        {
            if (p_execute_2_.length < 3)
            {
                if ("title".equals(p_execute_2_[1]) || "subtitle".equals(p_execute_2_[1]))
                {
                    throw new ExceptionUsage("commands.title.usage.title", new Object[0]);
                }

                if ("times".equals(p_execute_2_[1]))
                {
                    throw new ExceptionUsage("commands.title.usage.times", new Object[0]);
                }
            }

            EntityPlayer entityplayer = a(p_execute_1_, p_execute_2_[0]);
            PacketPlayOutTitle.EnumTitleAction packetplayouttitle$enumtitleaction = PacketPlayOutTitle.EnumTitleAction.a(p_execute_2_[1]);

            if (packetplayouttitle$enumtitleaction != PacketPlayOutTitle.EnumTitleAction.CLEAR && packetplayouttitle$enumtitleaction != PacketPlayOutTitle.EnumTitleAction.RESET)
            {
                if (packetplayouttitle$enumtitleaction == PacketPlayOutTitle.EnumTitleAction.TIMES)
                {
                    if (p_execute_2_.length != 5)
                    {
                        throw new ExceptionUsage("commands.title.usage", new Object[0]);
                    }
                    else
                    {
                        int i = a(p_execute_2_[2]);
                        int j = a(p_execute_2_[3]);
                        int k = a(p_execute_2_[4]);
                        PacketPlayOutTitle packetplayouttitle2 = new PacketPlayOutTitle(i, j, k);
                        entityplayer.playerConnection.sendPacket(packetplayouttitle2);
                        a(p_execute_1_, this, "commands.title.success", new Object[0]);
                    }
                }
                else if (p_execute_2_.length < 3)
                {
                    throw new ExceptionUsage("commands.title.usage", new Object[0]);
                }
                else
                {
                    String s = a(p_execute_2_, 2);
                    IChatBaseComponent ichatbasecomponent;

                    try
                    {
                        ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(s);
                    }
                    catch (JsonParseException jsonparseexception)
                    {
                        Throwable throwable = ExceptionUtils.getRootCause(jsonparseexception);
                        throw new ExceptionInvalidSyntax("commands.tellraw.jsonException", new Object[] {throwable == null ? "" : throwable.getMessage()});
                    }

                    PacketPlayOutTitle packetplayouttitle1 = new PacketPlayOutTitle(packetplayouttitle$enumtitleaction, ChatComponentUtils.filterForDisplay(p_execute_1_, ichatbasecomponent, entityplayer));
                    entityplayer.playerConnection.sendPacket(packetplayouttitle1);
                    a(p_execute_1_, this, "commands.title.success", new Object[0]);
                }
            }
            else if (p_execute_2_.length != 2)
            {
                throw new ExceptionUsage("commands.title.usage", new Object[0]);
            }
            else
            {
                PacketPlayOutTitle packetplayouttitle = new PacketPlayOutTitle(packetplayouttitle$enumtitleaction, (IChatBaseComponent)null);
                entityplayer.playerConnection.sendPacket(packetplayouttitle);
                a(p_execute_1_, this, "commands.title.success", new Object[0]);
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()) : (p_tabComplete_2_.length == 2 ? a(p_tabComplete_2_, PacketPlayOutTitle.EnumTitleAction.a()) : null);
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 0;
    }
}
