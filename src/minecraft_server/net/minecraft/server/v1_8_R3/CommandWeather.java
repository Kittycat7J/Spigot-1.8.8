package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;

public class CommandWeather extends CommandAbstract
{
    public String getCommand()
    {
        return "weather";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.weather.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length >= 1 && p_execute_2_.length <= 2)
        {
            int i = (300 + (new Random()).nextInt(600)) * 20;

            if (p_execute_2_.length >= 2)
            {
                i = a(p_execute_2_[1], 1, 1000000) * 20;
            }

            WorldServer worldserver = MinecraftServer.getServer().worldServer[0];
            WorldData worlddata = worldserver.getWorldData();

            if ("clear".equalsIgnoreCase(p_execute_2_[0]))
            {
                worlddata.i(i);
                worlddata.setWeatherDuration(0);
                worlddata.setThunderDuration(0);
                worlddata.setStorm(false);
                worlddata.setThundering(false);
                a(p_execute_1_, this, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(p_execute_2_[0]))
            {
                worlddata.i(0);
                worlddata.setWeatherDuration(i);
                worlddata.setThunderDuration(i);
                worlddata.setStorm(true);
                worlddata.setThundering(false);
                a(p_execute_1_, this, "commands.weather.rain", new Object[0]);
            }
            else
            {
                if (!"thunder".equalsIgnoreCase(p_execute_2_[0]))
                {
                    throw new ExceptionUsage("commands.weather.usage", new Object[0]);
                }

                worlddata.i(0);
                worlddata.setWeatherDuration(i);
                worlddata.setThunderDuration(i);
                worlddata.setStorm(true);
                worlddata.setThundering(true);
                a(p_execute_1_, this, "commands.weather.thunder", new Object[0]);
            }
        }
        else
        {
            throw new ExceptionUsage("commands.weather.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, new String[] {"clear", "rain", "thunder"}): null;
    }
}
