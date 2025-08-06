package net.minecraft.server.v1_8_R3;

public class LongHashMap<V>
{
    private transient LongHashMap.LongHashMapEntry<V>[] entries = new LongHashMap.LongHashMapEntry[4096];
    private transient int count;
    private int c;
    private int d = 3072;
    private final float e = 0.75F;
    private transient volatile int f;

    public LongHashMap()
    {
        this.c = this.entries.length - 1;
    }

    private static int g(long p_g_0_)
    {
        return a((int)(p_g_0_ ^ p_g_0_ >>> 32));
    }

    private static int a(int p_a_0_)
    {
        p_a_0_ = p_a_0_ ^ p_a_0_ >>> 20 ^ p_a_0_ >>> 12;
        return p_a_0_ ^ p_a_0_ >>> 7 ^ p_a_0_ >>> 4;
    }

    private static int a(int p_a_0_, int p_a_1_)
    {
        return p_a_0_ & p_a_1_;
    }

    public int count()
    {
        return this.count;
    }

    public V getEntry(long p_getEntry_1_)
    {
        int i = g(p_getEntry_1_);

        for (LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = this.entries[a(i, this.c)]; longhashmap$longhashmapentry != null; longhashmap$longhashmapentry = longhashmap$longhashmapentry.c)
        {
            if (longhashmap$longhashmapentry.a == p_getEntry_1_)
            {
                return longhashmap$longhashmapentry.b;
            }
        }

        return (V)null;
    }

    public boolean contains(long p_contains_1_)
    {
        return this.c(p_contains_1_) != null;
    }

    final LongHashMap.LongHashMapEntry<V> c(long p_c_1_)
    {
        int i = g(p_c_1_);

        for (LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = this.entries[a(i, this.c)]; longhashmap$longhashmapentry != null; longhashmap$longhashmapentry = longhashmap$longhashmapentry.c)
        {
            if (longhashmap$longhashmapentry.a == p_c_1_)
            {
                return longhashmap$longhashmapentry;
            }
        }

        return null;
    }

    public void put(long p_put_1_, V p_put_3_)
    {
        int i = g(p_put_1_);
        int j = a(i, this.c);

        for (LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = this.entries[j]; longhashmap$longhashmapentry != null; longhashmap$longhashmapentry = longhashmap$longhashmapentry.c)
        {
            if (longhashmap$longhashmapentry.a == p_put_1_)
            {
                longhashmap$longhashmapentry.b = p_put_3_;
                return;
            }
        }

        ++this.f;
        this.a(i, p_put_1_, p_put_3_, j);
    }

    private void b(int p_b_1_)
    {
        LongHashMap.LongHashMapEntry[] alonghashmap$longhashmapentry = this.entries;
        int i = alonghashmap$longhashmapentry.length;

        if (i == 1073741824)
        {
            this.d = Integer.MAX_VALUE;
        }
        else
        {
            LongHashMap.LongHashMapEntry[] alonghashmap$longhashmapentry1 = new LongHashMap.LongHashMapEntry[p_b_1_];
            this.a(alonghashmap$longhashmapentry1);
            this.entries = alonghashmap$longhashmapentry1;
            this.c = this.entries.length - 1;
            this.d = (int)((float)p_b_1_ * this.e);
        }
    }

    private void a(LongHashMap.LongHashMapEntry<V>[] p_a_1_)
    {
        LongHashMap.LongHashMapEntry[] alonghashmap$longhashmapentry = this.entries;
        int i = p_a_1_.length;

        for (int j = 0; j < alonghashmap$longhashmapentry.length; ++j)
        {
            LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = alonghashmap$longhashmapentry[j];

            if (longhashmap$longhashmapentry != null)
            {
                alonghashmap$longhashmapentry[j] = null;

                while (true)
                {
                    LongHashMap.LongHashMapEntry longhashmap$longhashmapentry1 = longhashmap$longhashmapentry.c;
                    int k = a(longhashmap$longhashmapentry.d, i - 1);
                    longhashmap$longhashmapentry.c = p_a_1_[k];
                    p_a_1_[k] = longhashmap$longhashmapentry;
                    longhashmap$longhashmapentry = longhashmap$longhashmapentry1;

                    if (longhashmap$longhashmapentry1 == null)
                    {
                        break;
                    }
                }
            }
        }
    }

    public V remove(long p_remove_1_)
    {
        LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = this.e(p_remove_1_);
        return (V)(longhashmap$longhashmapentry == null ? null : longhashmap$longhashmapentry.b);
    }

    final LongHashMap.LongHashMapEntry<V> e(long p_e_1_)
    {
        int i = g(p_e_1_);
        int j = a(i, this.c);
        LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = this.entries[j];
        LongHashMap.LongHashMapEntry longhashmap$longhashmapentry1;
        LongHashMap.LongHashMapEntry longhashmap$longhashmapentry2;

        for (longhashmap$longhashmapentry1 = longhashmap$longhashmapentry; longhashmap$longhashmapentry1 != null; longhashmap$longhashmapentry1 = longhashmap$longhashmapentry2)
        {
            longhashmap$longhashmapentry2 = longhashmap$longhashmapentry1.c;

            if (longhashmap$longhashmapentry1.a == p_e_1_)
            {
                ++this.f;
                --this.count;

                if (longhashmap$longhashmapentry == longhashmap$longhashmapentry1)
                {
                    this.entries[j] = longhashmap$longhashmapentry2;
                }
                else
                {
                    longhashmap$longhashmapentry.c = longhashmap$longhashmapentry2;
                }

                return longhashmap$longhashmapentry1;
            }

            longhashmap$longhashmapentry = longhashmap$longhashmapentry1;
        }

        return longhashmap$longhashmapentry1;
    }

    private void a(int p_a_1_, long p_a_2_, V p_a_4_, int p_a_5_)
    {
        LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = this.entries[p_a_5_];
        this.entries[p_a_5_] = new LongHashMap.LongHashMapEntry(p_a_1_, p_a_2_, p_a_4_, longhashmap$longhashmapentry);

        if (this.count++ >= this.d)
        {
            this.b(2 * this.entries.length);
        }
    }

    static class LongHashMapEntry<V>
    {
        final long a;
        V b;
        LongHashMap.LongHashMapEntry<V> c;
        final int d;

        LongHashMapEntry(int p_i1109_1_, long p_i1109_2_, V p_i1109_4_, LongHashMap.LongHashMapEntry<V> p_i1109_5_)
        {
            this.b = p_i1109_4_;
            this.c = p_i1109_5_;
            this.a = p_i1109_2_;
            this.d = p_i1109_1_;
        }

        public final long a()
        {
            return this.a;
        }

        public final V b()
        {
            return this.b;
        }

        public final boolean equals(Object p_equals_1_)
        {
            if (!(p_equals_1_ instanceof LongHashMap.LongHashMapEntry))
            {
                return false;
            }
            else
            {
                LongHashMap.LongHashMapEntry longhashmap$longhashmapentry = (LongHashMap.LongHashMapEntry)p_equals_1_;
                Long olong = Long.valueOf(this.a());
                Long olong1 = Long.valueOf(longhashmap$longhashmapentry.a());

                if (olong == olong1 || olong != null && olong.equals(olong1))
                {
                    Object object = this.b();
                    Object object1 = longhashmap$longhashmapentry.b();

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
            return LongHashMap.g(this.a);
        }

        public final String toString()
        {
            return this.a() + "=" + this.b();
        }
    }
}
