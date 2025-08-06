package net.minecraft.server.v1_8_R3;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.command.ProxiedNativeCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.command.VanillaCommandWrapper;

public class CommandExecute extends CommandAbstract
{
    public String getCommand()
    {
        return "execute";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.execute.usage";
    }

    public void execute(final ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 5)
        {
            throw new ExceptionUsage("commands.execute.usage", new Object[0]);
        }
        else
        {
            final Entity entity = a(p_execute_1_, p_execute_2_[0], Entity.class);
            final double d0 = b(entity.locX, p_execute_2_[1], false);
            final double d1 = b(entity.locY, p_execute_2_[2], false);
            final double d2 = b(entity.locZ, p_execute_2_[3], false);
            final BlockPosition blockposition = new BlockPosition(d0, d1, d2);
            byte b0 = 4;

            if ("detect".equals(p_execute_2_[4]) && p_execute_2_.length > 10)
            {
                World world = entity.getWorld();
                double d3 = b(d0, p_execute_2_[5], false);
                double d4 = b(d1, p_execute_2_[6], false);
                double d5 = b(d2, p_execute_2_[7], false);
                Block block = g(p_execute_1_, p_execute_2_[8]);
                int i = a(p_execute_2_[9], -1, 15);
                BlockPosition blockposition1 = new BlockPosition(d3, d4, d5);
                IBlockData iblockdata = world.getType(blockposition1);

                if (iblockdata.getBlock() != block || i >= 0 && iblockdata.getBlock().toLegacyData(iblockdata) != i)
                {
                    throw new CommandException("commands.execute.failed", new Object[] {"detect", entity.getName()});
                }

                b0 = 10;
            }

            String s = a(p_execute_2_, b0);
            ICommandListener icommandlistener = new ICommandListener()
            {
                public String getName()
                {
                    return entity.getName();
                }
                public IChatBaseComponent getScoreboardDisplayName()
                {
                    return entity.getScoreboardDisplayName();
                }
                public void sendMessage(IChatBaseComponent p_sendMessage_1_)
                {
                    p_execute_1_.sendMessage(p_sendMessage_1_);
                }
                public boolean a(int p_a_1_, String p_a_2_)
                {
                    return p_execute_1_.a(p_a_1_, p_a_2_);
                }
                public BlockPosition getChunkCoordinates()
                {
                    return blockposition;
                }
                public Vec3D d()
                {
                    return new Vec3D(d0, d1, d2);
                }
                public World getWorld()
                {
                    return entity.world;
                }
                public Entity f()
                {
                    return entity;
                }
                public boolean getSendCommandFeedback()
                {
                    MinecraftServer minecraftserver = MinecraftServer.getServer();
                    return minecraftserver == null || minecraftserver.worldServer[0].getGameRules().getBoolean("commandBlockOutput");
                }
                public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_)
                {
                    entity.a(p_a_1_, p_a_2_);
                }
            };
            MinecraftServer.getServer().getCommandHandler();

            try
            {
                CommandSender commandsender = null;

                if (p_execute_1_ instanceof DedicatedServer)
                {
                    commandsender = MinecraftServer.getServer().server.getConsoleSender();
                }
                else if (p_execute_1_ instanceof CommandBlockListenerAbstract)
                {
                    commandsender = ((CommandBlockListenerAbstract)p_execute_1_).sender;
                }
                else if (VanillaCommandWrapper.lastSender != null)
                {
                    commandsender = VanillaCommandWrapper.lastSender;
                }
                else
                {
                    if (p_execute_1_.f() == null)
                    {
                        throw new CommandException("Unhandled executor " + p_execute_1_.getClass().getSimpleName(), new Object[0]);
                    }

                    commandsender = p_execute_1_.f().getBukkitEntity();
                }

                int j = CommandBlockListenerAbstract.executeCommand(icommandlistener, new ProxiedNativeCommandSender(icommandlistener, commandsender, entity.getBukkitEntity()), s);

                if (j < 1)
                {
                    throw new CommandException("commands.execute.allInvocationsFailed", new Object[] {s});
                }
            }
            catch (Throwable throwable)
            {
                if (throwable instanceof CommandException)
                {
                    throw(CommandException)throwable;
                }
                else
                {
                    throw new CommandException("commands.execute.failed", new Object[] {s, entity.getName()});
                }
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()) : (p_tabComplete_2_.length > 1 && p_tabComplete_2_.length <= 4 ? a(p_tabComplete_2_, 1, p_tabComplete_3_) : (p_tabComplete_2_.length > 5 && p_tabComplete_2_.length <= 8 && "detect".equals(p_tabComplete_2_[4]) ? a(p_tabComplete_2_, 5, p_tabComplete_3_) : (p_tabComplete_2_.length == 9 && "detect".equals(p_tabComplete_2_[4]) ? a(p_tabComplete_2_, Block.REGISTRY.keySet()) : null)));
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
