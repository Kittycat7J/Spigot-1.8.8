package net.minecraft.server.v1_8_R3;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandDebug extends CommandAbstract
{
    private static final Logger a = LogManager.getLogger();
    private long b;
    private int c;

    public String getCommand()
    {
        return "debug";
    }

    public int a()
    {
        return 3;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.debug.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 1)
        {
            throw new ExceptionUsage("commands.debug.usage", new Object[0]);
        }
        else
        {
            if (p_execute_2_[0].equals("start"))
            {
                if (p_execute_2_.length != 1)
                {
                    throw new ExceptionUsage("commands.debug.usage", new Object[0]);
                }

                a(p_execute_1_, this, "commands.debug.start", new Object[0]);
                MinecraftServer.getServer().au();
                this.b = MinecraftServer.az();
                this.c = MinecraftServer.getServer().at();
            }
            else
            {
                if (!p_execute_2_[0].equals("stop"))
                {
                    throw new ExceptionUsage("commands.debug.usage", new Object[0]);
                }

                if (p_execute_2_.length != 1)
                {
                    throw new ExceptionUsage("commands.debug.usage", new Object[0]);
                }

                if (!MinecraftServer.getServer().methodProfiler.a)
                {
                    throw new CommandException("commands.debug.notStarted", new Object[0]);
                }

                long i = MinecraftServer.az();
                int j = MinecraftServer.getServer().at();
                long k = i - this.b;
                int l = j - this.c;
                this.a(k, l);
                MinecraftServer.getServer().methodProfiler.a = false;
                a(p_execute_1_, this, "commands.debug.stop", new Object[] {Float.valueOf((float)k / 1000.0F), Integer.valueOf(l)});
            }
        }
    }

    private void a(long p_a_1_, int p_a_3_)
    {
        File file1 = new File(MinecraftServer.getServer().d("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
        file1.getParentFile().mkdirs();

        try
        {
            FileWriter filewriter = new FileWriter(file1);
            filewriter.write(this.b(p_a_1_, p_a_3_));
            filewriter.close();
        }
        catch (Throwable throwable)
        {
            a.error("Could not save profiler results to " + file1, throwable);
        }
    }

    private String b(long p_b_1_, int p_b_3_)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Minecraft Profiler Results ----\n");
        stringbuilder.append("// ");
        stringbuilder.append(d());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time span: ").append(p_b_1_).append(" ms\n");
        stringbuilder.append("Tick span: ").append(p_b_3_).append(" ticks\n");
        stringbuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] {Float.valueOf((float)p_b_3_ / ((float)p_b_1_ / 1000.0F))})).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.a(0, "root", stringbuilder);
        stringbuilder.append("--- END PROFILE DUMP ---\n\n");
        return stringbuilder.toString();
    }

    private void a(int p_a_1_, String p_a_2_, StringBuilder p_a_3_)
    {
        List list = MinecraftServer.getServer().methodProfiler.b(p_a_2_);

        if (list != null && list.size() >= 3)
        {
            for (int i = 1; i < list.size(); ++i)
            {
                MethodProfiler.ProfilerInfo methodprofiler$profilerinfo = (MethodProfiler.ProfilerInfo)list.get(i);
                p_a_3_.append(String.format("[%02d] ", new Object[] {Integer.valueOf(p_a_1_)}));

                for (int j = 0; j < p_a_1_; ++j)
                {
                    p_a_3_.append(" ");
                }

                p_a_3_.append(methodprofiler$profilerinfo.c).append(" - ").append(String.format("%.2f", new Object[] {Double.valueOf(methodprofiler$profilerinfo.a)})).append("%/").append(String.format("%.2f", new Object[] {Double.valueOf(methodprofiler$profilerinfo.b)})).append("%\n");

                if (!methodprofiler$profilerinfo.c.equals("unspecified"))
                {
                    try
                    {
                        this.a(p_a_1_ + 1, p_a_2_ + "." + methodprofiler$profilerinfo.c, p_a_3_);
                    }
                    catch (Exception exception)
                    {
                        p_a_3_.append("[[ EXCEPTION ").append(exception).append(" ]]");
                    }
                }
            }
        }
    }

    private static String d()
    {
        String[] astring = new String[] {"Shiny numbers!", "Am I not running fast enough? :(", "I\'m working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it\'ll have more motivation to work faster! Poor server."};

        try
        {
            return astring[(int)(System.nanoTime() % (long)astring.length)];
        }
        catch (Throwable var2)
        {
            return "Witty comment unavailable :(";
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, new String[] {"start", "stop"}): null;
    }
}
