package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandHelp extends CommandAbstract
{
    public String getCommand()
    {
        return "help";
    }

    public int a()
    {
        return 0;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.help.usage";
    }

    public List<String> b()
    {
        return Arrays.asList(new String[] {"?"});
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        List list = this.d(p_execute_1_);
        boolean flag = true;
        int i = (list.size() - 1) / 7;
        int j = 0;

        try
        {
            j = p_execute_2_.length == 0 ? 0 : a(p_execute_2_[0], 1, i + 1) - 1;
        }
        catch (ExceptionInvalidNumber exceptioninvalidnumber)
        {
            Map map = this.d();
            ICommand icommand = (ICommand)map.get(p_execute_2_[0]);

            if (icommand != null)
            {
                throw new ExceptionUsage(icommand.getUsage(p_execute_1_), new Object[0]);
            }

            if (MathHelper.a(p_execute_2_[0], -1) != -1)
            {
                throw exceptioninvalidnumber;
            }

            throw new ExceptionUnknownCommand();
        }

        int k = Math.min((j + 1) * 7, list.size());
        ChatMessage chatmessage1 = new ChatMessage("commands.help.header", new Object[] {Integer.valueOf(j + 1), Integer.valueOf(i + 1)});
        chatmessage1.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
        p_execute_1_.sendMessage(chatmessage1);

        for (int l = j * 7; l < k; ++l)
        {
            ICommand icommand1 = (ICommand)list.get(l);
            ChatMessage chatmessage = new ChatMessage(icommand1.getUsage(p_execute_1_), new Object[0]);
            chatmessage.getChatModifier().setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.SUGGEST_COMMAND, "/" + icommand1.getCommand() + " "));
            p_execute_1_.sendMessage(chatmessage);
        }

        if (j == 0 && p_execute_1_ instanceof EntityHuman)
        {
            ChatMessage chatmessage2 = new ChatMessage("commands.help.footer", new Object[0]);
            chatmessage2.getChatModifier().setColor(EnumChatFormat.GREEN);
            p_execute_1_.sendMessage(chatmessage2);
        }
    }

    protected List<ICommand> d(ICommandListener p_d_1_)
    {
        List list = MinecraftServer.getServer().getCommandHandler().a(p_d_1_);
        Collections.sort(list);
        return list;
    }

    protected Map<String, ICommand> d()
    {
        return MinecraftServer.getServer().getCommandHandler().getCommands();
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        if (p_tabComplete_2_.length == 1)
        {
            Set set = this.d().keySet();
            return a(p_tabComplete_2_, (String[])set.toArray(new String[set.size()]));
        }
        else
        {
            return null;
        }
    }
}
