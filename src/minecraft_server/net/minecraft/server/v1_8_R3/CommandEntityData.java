package net.minecraft.server.v1_8_R3;

public class CommandEntityData extends CommandAbstract
{
    public String getCommand()
    {
        return "entitydata";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.entitydata.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 2)
        {
            throw new ExceptionUsage("commands.entitydata.usage", new Object[0]);
        }
        else
        {
            Entity entity = b(p_execute_1_, p_execute_2_[0]);

            if (entity instanceof EntityHuman)
            {
                throw new CommandException("commands.entitydata.noPlayers", new Object[] {entity.getScoreboardDisplayName()});
            }
            else
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                entity.e(nbttagcompound);
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.clone();
                NBTTagCompound nbttagcompound2;

                try
                {
                    nbttagcompound2 = MojangsonParser.parse(a(p_execute_1_, p_execute_2_, 1).c());
                }
                catch (MojangsonParseException mojangsonparseexception)
                {
                    throw new CommandException("commands.entitydata.tagError", new Object[] {mojangsonparseexception.getMessage()});
                }

                nbttagcompound2.remove("UUIDMost");
                nbttagcompound2.remove("UUIDLeast");
                nbttagcompound.a(nbttagcompound2);

                if (nbttagcompound.equals(nbttagcompound1))
                {
                    throw new CommandException("commands.entitydata.failed", new Object[] {nbttagcompound.toString()});
                }
                else
                {
                    entity.f(nbttagcompound);
                    a(p_execute_1_, this, "commands.entitydata.success", new Object[] {nbttagcompound.toString()});
                }
            }
        }
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 0;
    }
}
