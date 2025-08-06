package net.minecraft.server.v1_8_R3;

import java.util.List;

public class MethodProfiler
{
    public boolean a;

    public void a()
    {
    }

    public void a(String p_a_1_)
    {
    }

    public void b()
    {
    }

    public List<MethodProfiler.ProfilerInfo> b(String p_b_1_)
    {
        return null;
    }

    public void c(String p_c_1_)
    {
    }

    public String c()
    {
        return "";
    }

    public static final class ProfilerInfo implements Comparable<MethodProfiler.ProfilerInfo>
    {
        public double a;
        public double b;
        public String c;

        public ProfilerInfo(String p_i262_1_, double p_i262_2_, double p_i262_4_)
        {
            this.c = p_i262_1_;
            this.a = p_i262_2_;
            this.b = p_i262_4_;
        }

        public int a(MethodProfiler.ProfilerInfo p_a_1_)
        {
            return p_a_1_.a < this.a ? -1 : (p_a_1_.a > this.a ? 1 : p_a_1_.c.compareTo(this.c));
        }

        public int compareTo(MethodProfiler.ProfilerInfo p_compareTo_1_)
        {
            return this.a(p_compareTo_1_);
        }
    }
}
