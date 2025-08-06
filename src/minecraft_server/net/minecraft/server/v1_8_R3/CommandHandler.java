package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandHandler implements ICommandHandler
{
    private static final Logger a = LogManager.getLogger();
    private final Map<String, ICommand> b = Maps.<String, ICommand>newHashMap();
    private final Set<ICommand> c = Sets.<ICommand>newHashSet();

    public int a(ICommandListener p_a_1_, String p_a_2_)
    {
        p_a_2_ = p_a_2_.trim();

        if (p_a_2_.startsWith("/"))
        {
            p_a_2_ = p_a_2_.substring(1);
        }

        String[] astring = p_a_2_.split(" ");
        String s = astring[0];
        astring = a(astring);
        ICommand icommand = (ICommand)this.b.get(s);
        int i = this.a(icommand, astring);
        int j = 0;

        if (icommand == null)
        {
            ChatMessage chatmessage = new ChatMessage("commands.generic.notFound", new Object[0]);
            chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
            p_a_1_.sendMessage(chatmessage);
        }
        else if (icommand.canUse(p_a_1_))
        {
            if (i > -1)
            {
                List list = PlayerSelector.getPlayers(p_a_1_, astring[i], Entity.class);
                String s1 = astring[i];
                p_a_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, list.size());

                for (Entity entity : list)
                {
                    astring[i] = entity.getUniqueID().toString();

                    if (this.a(p_a_1_, astring, icommand, p_a_2_))
                    {
                        ++j;
                    }
                }

                astring[i] = s1;
            }
            else
            {
                p_a_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, 1);

                if (this.a(p_a_1_, astring, icommand, p_a_2_))
                {
                    ++j;
                }
            }
        }
        else
        {
            ChatMessage chatmessage1 = new ChatMessage("commands.generic.permission", new Object[0]);
            chatmessage1.getChatModifier().setColor(EnumChatFormat.RED);
            p_a_1_.sendMessage(chatmessage1);
        }

        p_a_1_.a(CommandObjectiveExecutor.EnumCommandResult.SUCCESS_COUNT, j);
        return j;
    }

    protected boolean a(ICommandListener p_a_1_, String[] p_a_2_, ICommand p_a_3_, String p_a_4_)
    {
        try
        {
            p_a_3_.execute(p_a_1_, p_a_2_);
            return true;
        }
        catch (ExceptionUsage exceptionusage)
        {
            ChatMessage chatmessage2 = new ChatMessage("commands.generic.usage", new Object[] {new ChatMessage(exceptionusage.getMessage(), exceptionusage.getArgs())});
            chatmessage2.getChatModifier().setColor(EnumChatFormat.RED);
            p_a_1_.sendMessage(chatmessage2);
        }
        catch (CommandException commandexception)
        {
            ChatMessage chatmessage1 = new ChatMessage(commandexception.getMessage(), commandexception.getArgs());
            chatmessage1.getChatModifier().setColor(EnumChatFormat.RED);
            p_a_1_.sendMessage(chatmessage1);
        }
        catch (Throwable var9)
        {
            ChatMessage chatmessage = new ChatMessage("commands.generic.exception", new Object[0]);
            chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
            p_a_1_.sendMessage(chatmessage);
            a.warn("Couldn\'t process command: \'" + p_a_4_ + "\'");
        }

        return false;
    }

    public ICommand a(ICommand p_a_1_)
    {
        this.b.put(p_a_1_.getCommand(), p_a_1_);
        this.c.add(p_a_1_);

        for (String s : p_a_1_.b())
        {
            ICommand icommand = (ICommand)this.b.get(s);

            if (icommand == null || !icommand.getCommand().equals(s))
            {
                this.b.put(s, p_a_1_);
            }
        }

        return p_a_1_;
    }

    private static String[] a(String[] p_a_0_)
    {
        String[] astring = new String[p_a_0_.length - 1];
        System.arraycopy(p_a_0_, 1, astring, 0, p_a_0_.length - 1);
        return astring;
    }

    public List<String> a(ICommandListener p_a_1_, String p_a_2_, BlockPosition p_a_3_)
    {
        String[] astring = p_a_2_.split(" ", -1);
        String s = astring[0];

        if (astring.length == 1)
        {
            ArrayList arraylist = Lists.newArrayList();

            for (Entry entry : this.b.entrySet())
            {
                if (CommandAbstract.a(s, (String)entry.getKey()) && ((ICommand)entry.getValue()).canUse(p_a_1_))
                {
                    arraylist.add(entry.getKey());
                }
            }

            return arraylist;
        }
        else
        {
            if (astring.length > 1)
            {
                ICommand icommand = (ICommand)this.b.get(s);

                if (icommand != null && icommand.canUse(p_a_1_))
                {
                    return icommand.tabComplete(p_a_1_, a(astring), p_a_3_);
                }
            }

            return null;
        }
    }

    public List<ICommand> a(ICommandListener p_a_1_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (ICommand icommand : this.c)
        {
            if (icommand.canUse(p_a_1_))
            {
                arraylist.add(icommand);
            }
        }

        return arraylist;
    }

    public Map<String, ICommand> getCommands()
    {
        return this.b;
    }

    private int a(ICommand p_a_1_, String[] p_a_2_)
    {
        if (p_a_1_ == null)
        {
            return -1;
        }
        else
        {
            for (int i = 0; i < p_a_2_.length; ++i)
            {
                if (p_a_1_.isListStart(p_a_2_, i) && PlayerSelector.isList(p_a_2_[i]))
                {
                    return i;
                }
            }

            return -1;
        }
    }
}
