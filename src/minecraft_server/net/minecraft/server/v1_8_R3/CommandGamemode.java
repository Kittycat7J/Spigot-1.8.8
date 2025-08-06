package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandGamemode extends CommandAbstract
{
    public String getCommand()
    {
        return "gamemode";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.gamemode.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length <= 0)
        {
            throw new ExceptionUsage("commands.gamemode.usage", new Object[0]);
        }
        else
        {
            WorldSettings.EnumGamemode worldsettings$enumgamemode = this.h(p_execute_1_, p_execute_2_[0]);
            EntityPlayer entityplayer = p_execute_2_.length >= 2 ? a(p_execute_1_, p_execute_2_[1]) : b(p_execute_1_);
            entityplayer.a(worldsettings$enumgamemode);

            if (entityplayer.playerInteractManager.getGameMode() != worldsettings$enumgamemode)
            {
                p_execute_1_.sendMessage(new ChatComponentText("Failed to set the gamemode of \'" + entityplayer.getName() + "\'"));
            }
            else
            {
                entityplayer.fallDistance = 0.0F;

                if (p_execute_1_.getWorld().getGameRules().getBoolean("sendCommandFeedback"))
                {
                    entityplayer.sendMessage((IChatBaseComponent)(new ChatMessage("gameMode.changed", new Object[0])));
                }

                ChatMessage chatmessage = new ChatMessage("gameMode." + worldsettings$enumgamemode.b(), new Object[0]);

                if (entityplayer != p_execute_1_)
                {
                    a(p_execute_1_, this, 1, "commands.gamemode.success.other", new Object[] {entityplayer.getName(), chatmessage});
                }
                else
                {
                    a(p_execute_1_, this, 1, "commands.gamemode.success.self", new Object[] {chatmessage});
                }
            }
        }
    }

    protected WorldSettings.EnumGamemode h(ICommandListener p_h_1_, String p_h_2_) throws ExceptionInvalidNumber
    {
        return !p_h_2_.equalsIgnoreCase(WorldSettings.EnumGamemode.SURVIVAL.b()) && !p_h_2_.equalsIgnoreCase("s") ? (!p_h_2_.equalsIgnoreCase(WorldSettings.EnumGamemode.CREATIVE.b()) && !p_h_2_.equalsIgnoreCase("c") ? (!p_h_2_.equalsIgnoreCase(WorldSettings.EnumGamemode.ADVENTURE.b()) && !p_h_2_.equalsIgnoreCase("a") ? (!p_h_2_.equalsIgnoreCase(WorldSettings.EnumGamemode.SPECTATOR.b()) && !p_h_2_.equalsIgnoreCase("sp") ? WorldSettings.a(a(p_h_2_, 0, WorldSettings.EnumGamemode.values().length - 2)) : WorldSettings.EnumGamemode.SPECTATOR) : WorldSettings.EnumGamemode.ADVENTURE) : WorldSettings.EnumGamemode.CREATIVE) : WorldSettings.EnumGamemode.SURVIVAL;
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, new String[] {"survival", "creative", "adventure", "spectator"}): (p_tabComplete_2_.length == 2 ? a(p_tabComplete_2_, this.d()) : null);
    }

    protected String[] d()
    {
        return MinecraftServer.getServer().getPlayers();
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 1;
    }

    public int compareTo(ICommand p_compareTo_1_)
    {
        return this.a(p_compareTo_1_);
    }
}
