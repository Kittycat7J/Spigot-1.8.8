package net.minecraft.server.v1_8_R3;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBanIp extends CommandAbstract
{
    public static final Pattern a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public String getCommand()
    {
        return "ban-ip";
    }

    public int a()
    {
        return 3;
    }

    public boolean canUse(ICommandListener p_canUse_1_)
    {
        return MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() && super.canUse(p_canUse_1_);
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.banip.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length >= 1 && p_execute_2_[0].length() > 1)
        {
            IChatBaseComponent ichatbasecomponent = p_execute_2_.length >= 2 ? a(p_execute_1_, p_execute_2_, 1) : null;
            Matcher matcher = a.matcher(p_execute_2_[0]);

            if (matcher.matches())
            {
                this.a(p_execute_1_, p_execute_2_[0], ichatbasecomponent == null ? null : ichatbasecomponent.c());
            }
            else
            {
                EntityPlayer entityplayer = MinecraftServer.getServer().getPlayerList().getPlayer(p_execute_2_[0]);

                if (entityplayer == null)
                {
                    throw new ExceptionPlayerNotFound("commands.banip.invalid", new Object[0]);
                }

                this.a(p_execute_1_, entityplayer.w(), ichatbasecomponent == null ? null : ichatbasecomponent.c());
            }
        }
        else
        {
            throw new ExceptionUsage("commands.banip.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()) : null;
    }

    protected void a(ICommandListener p_a_1_, String p_a_2_, String p_a_3_)
    {
        IpBanEntry ipbanentry = new IpBanEntry(p_a_2_, (Date)null, p_a_1_.getName(), (Date)null, p_a_3_);
        MinecraftServer.getServer().getPlayerList().getIPBans().add(ipbanentry);
        List list = MinecraftServer.getServer().getPlayerList().b(p_a_2_);
        String[] astring = new String[list.size()];
        int i = 0;

        for (EntityPlayer entityplayer : list)
        {
            entityplayer.playerConnection.disconnect("You have been IP banned.");
            astring[i++] = entityplayer.getName();
        }

        if (list.isEmpty())
        {
            a(p_a_1_, this, "commands.banip.success", new Object[] {p_a_2_});
        }
        else
        {
            a(p_a_1_, this, "commands.banip.success.players", new Object[] {p_a_2_, a(astring)});
        }
    }
}
