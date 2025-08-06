package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.Callable;

public class CrashReportSystemDetails
{
    private final CrashReport a;
    private final String b;
    private final List<CrashReportSystemDetails.CrashReportDetail> c = Lists.<CrashReportSystemDetails.CrashReportDetail>newArrayList();
    private StackTraceElement[] d = new StackTraceElement[0];

    public CrashReportSystemDetails(CrashReport p_i870_1_, String p_i870_2_)
    {
        this.a = p_i870_1_;
        this.b = p_i870_2_;
    }

    public static String a(double p_a_0_, double p_a_2_, double p_a_4_)
    {
        return String.format("%.2f,%.2f,%.2f - %s", new Object[] {Double.valueOf(p_a_0_), Double.valueOf(p_a_2_), Double.valueOf(p_a_4_), a(new BlockPosition(p_a_0_, p_a_2_, p_a_4_))});
    }

    public static String a(BlockPosition p_a_0_)
    {
        int i = p_a_0_.getX();
        int j = p_a_0_.getY();
        int k = p_a_0_.getZ();
        StringBuilder stringbuilder = new StringBuilder();

        try
        {
            stringbuilder.append(String.format("World: (%d,%d,%d)", new Object[] {Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)}));
        }
        catch (Throwable var17)
        {
            stringbuilder.append("(Error finding world loc)");
        }

        stringbuilder.append(", ");

        try
        {
            int l = i >> 4;
            int i1 = k >> 4;
            int j1 = i & 15;
            int k1 = j >> 4;
            int l1 = k & 15;
            int i2 = l << 4;
            int j2 = i1 << 4;
            int k2 = (l + 1 << 4) - 1;
            int l2 = (i1 + 1 << 4) - 1;
            stringbuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] {Integer.valueOf(j1), Integer.valueOf(k1), Integer.valueOf(l1), Integer.valueOf(l), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(j2), Integer.valueOf(k2), Integer.valueOf(l2)}));
        }
        catch (Throwable var16)
        {
            stringbuilder.append("(Error finding chunk loc)");
        }

        stringbuilder.append(", ");

        try
        {
            int j3 = i >> 9;
            int k3 = k >> 9;
            int l3 = j3 << 5;
            int i4 = k3 << 5;
            int j4 = (j3 + 1 << 5) - 1;
            int k4 = (k3 + 1 << 5) - 1;
            int l4 = j3 << 9;
            int i5 = k3 << 9;
            int j5 = (j3 + 1 << 9) - 1;
            int i3 = (k3 + 1 << 9) - 1;
            stringbuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] {Integer.valueOf(j3), Integer.valueOf(k3), Integer.valueOf(l3), Integer.valueOf(i4), Integer.valueOf(j4), Integer.valueOf(k4), Integer.valueOf(l4), Integer.valueOf(i5), Integer.valueOf(j5), Integer.valueOf(i3)}));
        }
        catch (Throwable var15)
        {
            stringbuilder.append("(Error finding world loc)");
        }

        return stringbuilder.toString();
    }

    public void a(String p_a_1_, Callable<String> p_a_2_)
    {
        try
        {
            this.a(p_a_1_, p_a_2_.call());
        }
        catch (Throwable throwable)
        {
            this.a(p_a_1_, throwable);
        }
    }

    public void a(String p_a_1_, Object p_a_2_)
    {
        this.c.add(new CrashReportSystemDetails.CrashReportDetail(p_a_1_, p_a_2_));
    }

    public void a(String p_a_1_, Throwable p_a_2_)
    {
        this.a((String)p_a_1_, p_a_2_);
    }

    public int a(int p_a_1_)
    {
        StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();

        if (astacktraceelement.length <= 0)
        {
            return 0;
        }
        else
        {
            this.d = new StackTraceElement[astacktraceelement.length - 3 - p_a_1_];
            System.arraycopy(astacktraceelement, 3 + p_a_1_, this.d, 0, this.d.length);
            return this.d.length;
        }
    }

    public boolean a(StackTraceElement p_a_1_, StackTraceElement p_a_2_)
    {
        if (this.d.length != 0 && p_a_1_ != null)
        {
            StackTraceElement stacktraceelement = this.d[0];

            if (stacktraceelement.isNativeMethod() == p_a_1_.isNativeMethod() && stacktraceelement.getClassName().equals(p_a_1_.getClassName()) && stacktraceelement.getFileName().equals(p_a_1_.getFileName()) && stacktraceelement.getMethodName().equals(p_a_1_.getMethodName()))
            {
                if (p_a_2_ != null != this.d.length > 1)
                {
                    return false;
                }
                else if (p_a_2_ != null && !this.d[1].equals(p_a_2_))
                {
                    return false;
                }
                else
                {
                    this.d[0] = p_a_1_;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public void b(int p_b_1_)
    {
        StackTraceElement[] astacktraceelement = new StackTraceElement[this.d.length - p_b_1_];
        System.arraycopy(this.d, 0, astacktraceelement, 0, astacktraceelement.length);
        this.d = astacktraceelement;
    }

    public void a(StringBuilder p_a_1_)
    {
        p_a_1_.append("-- ").append(this.b).append(" --\n");
        p_a_1_.append("Details:");

        for (CrashReportSystemDetails.CrashReportDetail crashreportsystemdetails$crashreportdetail : this.c)
        {
            p_a_1_.append("\n\t");
            p_a_1_.append(crashreportsystemdetails$crashreportdetail.a());
            p_a_1_.append(": ");
            p_a_1_.append(crashreportsystemdetails$crashreportdetail.b());
        }

        if (this.d != null && this.d.length > 0)
        {
            p_a_1_.append("\nStacktrace:");

            for (StackTraceElement stacktraceelement : this.d)
            {
                p_a_1_.append("\n\tat ");
                p_a_1_.append(stacktraceelement.toString());
            }
        }
    }

    public StackTraceElement[] a()
    {
        return this.d;
    }

    public static void a(CrashReportSystemDetails p_a_0_, final BlockPosition p_a_1_, final Block p_a_2_, final int p_a_3_)
    {
        final int i = Block.getId(p_a_2_);
        p_a_0_.a("Block type", new Callable<String>()
        {
            public String a() throws Exception
            {
                try
                {
                    return String.format("ID #%d (%s // %s)", new Object[] {Integer.valueOf(i), p_a_2_.a(), p_a_2_.getClass().getCanonicalName()});
                }
                catch (Throwable var2)
                {
                    return "ID #" + i;
                }
            }
        });
        p_a_0_.a("Block data value", new Callable<String>()
        {
            public String a() throws Exception
            {
                if (p_a_3_ < 0)
                {
                    return "Unknown? (Got " + p_a_3_ + ")";
                }
                else
                {
                    String s = String.format("%4s", new Object[] {Integer.toBinaryString(p_a_3_)}).replace(" ", "0");
                    return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] {Integer.valueOf(p_a_3_), s});
                }
            }
        });
        p_a_0_.a("Block location", new Callable<String>()
        {
            public String a() throws Exception
            {
                return CrashReportSystemDetails.a(p_a_1_);
            }
        });
    }

    public static void a(CrashReportSystemDetails p_a_0_, final BlockPosition p_a_1_, final IBlockData p_a_2_)
    {
        p_a_0_.a("Block", new Callable<String>()
        {
            public String a() throws Exception
            {
                return p_a_2_.toString();
            }
        });
        p_a_0_.a("Block location", new Callable<String>()
        {
            public String a() throws Exception
            {
                return CrashReportSystemDetails.a(p_a_1_);
            }
        });
    }

    static class CrashReportDetail
    {
        private final String a;
        private final String b;

        public CrashReportDetail(String p_i869_1_, Object p_i869_2_)
        {
            this.a = p_i869_1_;

            if (p_i869_2_ == null)
            {
                this.b = "~~NULL~~";
            }
            else if (p_i869_2_ instanceof Throwable)
            {
                Throwable throwable = (Throwable)p_i869_2_;
                this.b = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
            }
            else
            {
                this.b = p_i869_2_.toString();
            }
        }

        public String a()
        {
            return this.a;
        }

        public String b()
        {
            return this.b;
        }
    }
}
