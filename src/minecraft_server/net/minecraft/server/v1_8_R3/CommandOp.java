package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;

public class CommandOp extends CommandAbstract
{
    public String getCommand()
    {
        return "op";
    }

    public int a()
    {
        return 3;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.op.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length == 1 && p_execute_2_[0].length() > 0)
        {
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            GameProfile gameprofile = minecraftserver.getUserCache().getProfile(p_execute_2_[0]);

            if (gameprofile == null)
            {
                throw new CommandException("commands.op.failed", new Object[] {p_execute_2_[0]});
            }
            else
            {
                minecraftserver.getPlayerList().addOp(gameprofile);
                a(p_execute_1_, this, "commands.op.success", new Object[] {p_execute_2_[0]});
            }
        }
        else
        {
            throw new ExceptionUsage("commands.op.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        if (p_tabComplete_2_.length == 1)
        {
            String s = p_tabComplete_2_[p_tabComplete_2_.length - 1];
            ArrayList arraylist = Lists.newArrayList();

            for (GameProfile gameprofile : MinecraftServer.getServer().L())
            {
                if (!MinecraftServer.getServer().getPlayerList().isOp(gameprofile) && a(s, gameprofile.getName()))
                {
                    arraylist.add(gameprofile.getName());
                }
            }

            return arraylist;
        }
        else
        {
            return null;
        }
    }
}
