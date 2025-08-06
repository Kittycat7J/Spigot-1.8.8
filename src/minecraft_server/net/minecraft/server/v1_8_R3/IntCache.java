package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import org.spigotmc.SpigotConfig;

public class IntCache
{
    private static int a = 256;
    private static List<int[]> b = Lists.<int[]>newArrayList();
    private static List<int[]> c = Lists.<int[]>newArrayList();
    private static List<int[]> d = Lists.<int[]>newArrayList();
    private static List<int[]> e = Lists.<int[]>newArrayList();

    public static synchronized int[] a(int p_a_0_)
    {
        if (p_a_0_ <= 256)
        {
            if (b.isEmpty())
            {
                int[] aint4 = new int[256];

                if (c.size() < SpigotConfig.intCacheLimit)
                {
                    c.add(aint4);
                }

                return aint4;
            }
            else
            {
                int[] aint3 = (int[])b.remove(b.size() - 1);

                if (c.size() < SpigotConfig.intCacheLimit)
                {
                    c.add(aint3);
                }

                return aint3;
            }
        }
        else if (p_a_0_ > a)
        {
            a = p_a_0_;
            d.clear();
            e.clear();
            int[] aint2 = new int[a];

            if (e.size() < SpigotConfig.intCacheLimit)
            {
                e.add(aint2);
            }

            return aint2;
        }
        else if (d.isEmpty())
        {
            int[] aint1 = new int[a];

            if (e.size() < SpigotConfig.intCacheLimit)
            {
                e.add(aint1);
            }

            return aint1;
        }
        else
        {
            int[] aint = (int[])d.remove(d.size() - 1);

            if (e.size() < SpigotConfig.intCacheLimit)
            {
                e.add(aint);
            }

            return aint;
        }
    }

    public static synchronized void a()
    {
        if (!d.isEmpty())
        {
            d.remove(d.size() - 1);
        }

        if (!b.isEmpty())
        {
            b.remove(b.size() - 1);
        }

        d.addAll(e);
        b.addAll(c);
        e.clear();
        c.clear();
    }

    public static synchronized String b()
    {
        return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
    }
}
