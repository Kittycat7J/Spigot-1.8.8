package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandTestFor extends CommandAbstract
{
    public String getCommand()
    {
        return "testfor";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.testfor.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 1)
        {
            throw new ExceptionUsage("commands.testfor.usage", new Object[0]);
        }
        else
        {
            Entity entity = b(p_execute_1_, p_execute_2_[0]);
            NBTTagCompound nbttagcompound = null;

            if (p_execute_2_.length >= 2)
            {
                try
                {
                    nbttagcompound = MojangsonParser.parse(a(p_execute_2_, 1));
                }
                catch (MojangsonParseException mojangsonparseexception)
                {
                    throw new CommandException("commands.testfor.tagError", new Object[] {mojangsonparseexception.getMessage()});
                }
            }

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                entity.e(nbttagcompound1);

                if (!GameProfileSerializer.a(nbttagcompound, nbttagcompound1, true))
                {
                    throw new CommandException("commands.testfor.failure", new Object[] {entity.getName()});
                }
            }

            a(p_execute_1_, this, "commands.testfor.success", new Object[] {entity.getName()});
        }
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 0;
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()) : null;
    }
}
