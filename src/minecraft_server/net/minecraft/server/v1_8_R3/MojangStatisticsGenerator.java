package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MojangStatisticsGenerator
{
    private final Map<String, Object> a = Maps.<String, Object>newHashMap();
    private final Map<String, Object> b = Maps.<String, Object>newHashMap();
    private final String c = UUID.randomUUID().toString();
    private final URL d;
    private final IMojangStatistics e;
    private final Timer f = new Timer("Snooper Timer", true);
    private final Object g = new Object();
    private final long h;
    private boolean i;
    private int j;

    public MojangStatisticsGenerator(String p_i1130_1_, IMojangStatistics p_i1130_2_, long p_i1130_3_)
    {
        try
        {
            this.d = new URL("http://snoop.minecraft.net/" + p_i1130_1_ + "?version=" + 2);
        }
        catch (MalformedURLException var6)
        {
            throw new IllegalArgumentException();
        }

        this.e = p_i1130_2_;
        this.h = p_i1130_3_;
    }

    public void a()
    {
        if (!this.i)
        {
            this.i = true;
            this.h();
            this.f.schedule(new TimerTask()
            {
                public void run()
                {
                    if (MojangStatisticsGenerator.this.e.getSnooperEnabled())
                    {
                        HashMap hashmap;

                        synchronized (MojangStatisticsGenerator.this.g)
                        {
                            hashmap = Maps.newHashMap(MojangStatisticsGenerator.this.b);

                            if (MojangStatisticsGenerator.this.j == 0)
                            {
                                hashmap.putAll(MojangStatisticsGenerator.this.a);
                            }

                            hashmap.put("snooper_count", Integer.valueOf(MojangStatisticsGenerator.this.j++));
                            hashmap.put("snooper_token", MojangStatisticsGenerator.this.c);
                        }

                        HttpUtilities.a(MojangStatisticsGenerator.this.d, hashmap, true);
                    }
                }
            }, 0L, 900000L);
        }
    }

    private void h()
    {
        this.i();
        this.a("snooper_token", this.c);
        this.b("snooper_token", this.c);
        this.b("os_name", System.getProperty("os.name"));
        this.b("os_version", System.getProperty("os.version"));
        this.b("os_architecture", System.getProperty("os.arch"));
        this.b("java_version", System.getProperty("java.version"));
        this.a("version", "1.8.8");
        this.e.b(this);
    }

    private void i()
    {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List list = runtimemxbean.getInputArguments();
        int ix = 0;

        for (String s : list)
        {
            if (s.startsWith("-X"))
            {
                this.a("jvm_arg[" + ix++ + "]", s);
            }
        }

        this.a("jvm_args", Integer.valueOf(ix));
    }

    public void b()
    {
        this.b("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        this.b("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        this.b("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        this.b("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        this.e.a(this);
    }

    public void a(String p_a_1_, Object p_a_2_)
    {
        synchronized (this.g)
        {
            this.b.put(p_a_1_, p_a_2_);
        }
    }

    public void b(String p_b_1_, Object p_b_2_)
    {
        synchronized (this.g)
        {
            this.a.put(p_b_1_, p_b_2_);
        }
    }

    public boolean d()
    {
        return this.i;
    }

    public void e()
    {
        this.f.cancel();
    }

    public long g()
    {
        return this.h;
    }
}
