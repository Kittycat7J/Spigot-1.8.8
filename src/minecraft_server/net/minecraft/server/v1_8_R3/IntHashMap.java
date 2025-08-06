package net.minecraft.server.v1_8_R3;

public class IntHashMap<V>
{
    private transient IntHashMap.IntHashMapEntry<V>[] a = new IntHashMap.IntHashMapEntry[16];
    private transient int b;
    private int c = 12;
    private final float d = 0.75F;

    private static int g(int p_g_0_)
    {
        p_g_0_ = p_g_0_ ^ p_g_0_ >>> 20 ^ p_g_0_ >>> 12;
        return p_g_0_ ^ p_g_0_ >>> 7 ^ p_g_0_ >>> 4;
    }

    private static int a(int p_a_0_, int p_a_1_)
    {
        return p_a_0_ & p_a_1_ - 1;
    }

    public V get(int p_get_1_)
    {
        int i = g(p_get_1_);

        for (IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[a(i, this.a.length)]; inthashmap$inthashmapentry != null; inthashmap$inthashmapentry = inthashmap$inthashmapentry.c)
        {
            if (inthashmap$inthashmapentry.a == p_get_1_)
            {
                return inthashmap$inthashmapentry.b;
            }
        }

        return (V)null;
    }

    public boolean b(int p_b_1_)
    {
        return this.c(p_b_1_) != null;
    }

    final IntHashMap.IntHashMapEntry<V> c(int p_c_1_)
    {
        int i = g(p_c_1_);

        for (IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[a(i, this.a.length)]; inthashmap$inthashmapentry != null; inthashmap$inthashmapentry = inthashmap$inthashmapentry.c)
        {
            if (inthashmap$inthashmapentry.a == p_c_1_)
            {
                return inthashmap$inthashmapentry;
            }
        }

        return null;
    }

    public void a(int p_a_1_, V p_a_2_)
    {
        int i = g(p_a_1_);
        int j = a(i, this.a.length);

        for (IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[j]; inthashmap$inthashmapentry != null; inthashmap$inthashmapentry = inthashmap$inthashmapentry.c)
        {
            if (inthashmap$inthashmapentry.a == p_a_1_)
            {
                inthashmap$inthashmapentry.b = p_a_2_;
                return;
            }
        }

        this.a(i, p_a_1_, p_a_2_, j);
    }

    private void h(int p_h_1_)
    {
        IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry = this.a;
        int i = ainthashmap$inthashmapentry.length;

        if (i == 1073741824)
        {
            this.c = Integer.MAX_VALUE;
        }
        else
        {
            IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry1 = new IntHashMap.IntHashMapEntry[p_h_1_];
            this.a(ainthashmap$inthashmapentry1);
            this.a = ainthashmap$inthashmapentry1;
            this.c = (int)((float)p_h_1_ * this.d);
        }
    }

    private void a(IntHashMap.IntHashMapEntry<V>[] p_a_1_)
    {
        IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry = this.a;
        int i = p_a_1_.length;

        for (int j = 0; j < ainthashmap$inthashmapentry.length; ++j)
        {
            IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = ainthashmap$inthashmapentry[j];

            if (inthashmap$inthashmapentry != null)
            {
                ainthashmap$inthashmapentry[j] = null;

                while (true)
                {
                    IntHashMap.IntHashMapEntry inthashmap$inthashmapentry1 = inthashmap$inthashmapentry.c;
                    int k = a(inthashmap$inthashmapentry.d, i);
                    inthashmap$inthashmapentry.c = p_a_1_[k];
                    p_a_1_[k] = inthashmap$inthashmapentry;
                    inthashmap$inthashmapentry = inthashmap$inthashmapentry1;

                    if (inthashmap$inthashmapentry1 == null)
                    {
                        break;
                    }
                }
            }
        }
    }

    public V d(int p_d_1_)
    {
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.e(p_d_1_);
        return (V)(inthashmap$inthashmapentry == null ? null : inthashmap$inthashmapentry.b);
    }

    final IntHashMap.IntHashMapEntry<V> e(int p_e_1_)
    {
        int i = g(p_e_1_);
        int j = a(i, this.a.length);
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[j];
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry1;
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry2;

        for (inthashmap$inthashmapentry1 = inthashmap$inthashmapentry; inthashmap$inthashmapentry1 != null; inthashmap$inthashmapentry1 = inthashmap$inthashmapentry2)
        {
            inthashmap$inthashmapentry2 = inthashmap$inthashmapentry1.c;

            if (inthashmap$inthashmapentry1.a == p_e_1_)
            {
                --this.b;

                if (inthashmap$inthashmapentry == inthashmap$inthashmapentry1)
                {
                    this.a[j] = inthashmap$inthashmapentry2;
                }
                else
                {
                    inthashmap$inthashmapentry.c = inthashmap$inthashmapentry2;
                }

                return inthashmap$inthashmapentry1;
            }

            inthashmap$inthashmapentry = inthashmap$inthashmapentry1;
        }

        return inthashmap$inthashmapentry1;
    }

    public void c()
    {
        IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry = this.a;

        for (int i = 0; i < ainthashmap$inthashmapentry.length; ++i)
        {
            ainthashmap$inthashmapentry[i] = null;
        }

        this.b = 0;
    }

    private void a(int p_a_1_, int p_a_2_, V p_a_3_, int p_a_4_)
    {
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[p_a_4_];
        this.a[p_a_4_] = new IntHashMap.IntHashMapEntry(p_a_1_, p_a_2_, p_a_3_, inthashmap$inthashmapentry);

        if (this.b++ >= this.c)
        {
            this.h(2 * this.a.length);
        }
    }

    static class IntHashMapEntry<V>
    {
        final int a;
        V b;
        IntHashMap.IntHashMapEntry<V> c;
        final int d;

        IntHashMapEntry(int x, int p_i1108_2_, V y, IntHashMap.IntHashMapEntry<V> p_i1108_4_)
        {
            this.b = y;
            this.c = p_i1108_4_;
            this.a = p_i1108_2_;
            this.d = x;
        }

        public final int a()
        {
            return this.a;
        }

        public final V b()
        {
            return this.b;
        }

        public final boolean equals(Object p_equals_1_)
        {
            if (!(p_equals_1_ instanceof IntHashMap.IntHashMapEntry))
            {
                return false;
            }
            else
            {
                IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = (IntHashMap.IntHashMapEntry)p_equals_1_;
                Integer integer = Integer.valueOf(this.a());
                Integer integer1 = Integer.valueOf(inthashmap$inthashmapentry.a());

                if (integer == integer1 || integer != null && integer.equals(integer1))
                {
                    Object object = this.b();
                    Object object1 = inthashmap$inthashmapentry.b();

                    if (object == object1 || object != null && object.equals(object1))
                    {
                        return true;
                    }
                }

                return false;
            }
        }

        public final int hashCode()
        {
            return IntHashMap.g(this.a);
        }

        public final String toString()
        {
            return this.a() + "=" + this.b();
        }
    }
}
