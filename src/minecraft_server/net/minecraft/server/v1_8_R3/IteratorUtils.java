package net.minecraft.server.v1_8_R3;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IteratorUtils
{
    public static <T> Iterable<T[]> a(Class<T> p_a_0_, Iterable <? extends Iterable <? extends T >> p_a_1_)
    {
        return new IteratorUtils.ClassIterable(p_a_0_, (Iterable[])b(Iterable.class, p_a_1_));
    }

    public static <T> Iterable<List<T>> a(Iterable <? extends Iterable <? extends T >> p_a_0_)
    {
        return b(a(Object.class, p_a_0_));
    }

    private static <T> Iterable<List<T>> b(Iterable<Object[]> p_b_0_)
    {
        return Iterables.transform(p_b_0_, new IteratorUtils.ArrayToList());
    }

    private static <T> T[] b(Class <? super T > p_b_0_, Iterable <? extends T > p_b_1_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (Object object : p_b_1_)
        {
            arraylist.add(object);
        }

        return (T[])((Object[])arraylist.toArray(b(p_b_0_, arraylist.size())));
    }

    private static <T> T[] b(Class <? super T > p_b_0_, int p_b_1_)
    {
        return (T[])((Object[])((Object[])Array.newInstance(p_b_0_, p_b_1_)));
    }

    static class ArrayToList<T> implements Function<Object[], List<T>>
    {
        private ArrayToList()
        {
        }

        public List<T> a(Object[] p_a_1_)
        {
            return Arrays.asList((Object[])p_a_1_);
        }
    }

    static class ClassIterable<T> implements Iterable<T[]>
    {
        private final Class<T> a;
        private final Iterable <? extends T > [] b;

        private ClassIterable(Class<T> p_i891_1_, Iterable <? extends T > [] p_i891_2_)
        {
            this.a = p_i891_1_;
            this.b = p_i891_2_;
        }

        public Iterator<T[]> iterator()
        {
            return (Iterator<T[]>)(this.b.length <= 0 ? Collections.singletonList((Object[])IteratorUtils.b(this.a, 0)).iterator() : new IteratorUtils.ClassIterable.ClassIterator(this.a, this.b));
        }

        static class ClassIterator<T> extends UnmodifiableIterator<T[]>
        {
            private int a;
            private final Iterable <? extends T > [] b;
            private final Iterator <? extends T > [] c;
            private final T[] d;

            private ClassIterator(Class<T> p_i889_1_, Iterable <? extends T > [] p_i889_2_)
            {
                this.a = -2;
                this.b = p_i889_2_;
                this.c = (Iterator[])IteratorUtils.b(Iterator.class, this.b.length);

                for (int i = 0; i < this.b.length; ++i)
                {
                    this.c[i] = p_i889_2_[i].iterator();
                }

                this.d = IteratorUtils.b(p_i889_1_, this.c.length);
            }

            private void b()
            {
                this.a = -1;
                Arrays.fill(this.c, (Object)null);
                Arrays.fill(this.d, (Object)null);
            }

            public boolean hasNext()
            {
                if (this.a == -2)
                {
                    this.a = 0;

                    for (Iterator iterator1 : this.c)
                    {
                        if (!iterator1.hasNext())
                        {
                            this.b();
                            break;
                        }
                    }

                    return true;
                }
                else
                {
                    if (this.a >= this.c.length)
                    {
                        for (this.a = this.c.length - 1; this.a >= 0; --this.a)
                        {
                            Iterator iterator = this.c[this.a];

                            if (iterator.hasNext())
                            {
                                break;
                            }

                            if (this.a == 0)
                            {
                                this.b();
                                break;
                            }

                            iterator = this.b[this.a].iterator();
                            this.c[this.a] = iterator;

                            if (!iterator.hasNext())
                            {
                                this.b();
                                break;
                            }
                        }
                    }

                    return this.a >= 0;
                }
            }

            public T[] a()
            {
                if (!this.hasNext())
                {
                    throw new NoSuchElementException();
                }
                else
                {
                    while (this.a < this.c.length)
                    {
                        this.d[this.a] = this.c[this.a].next();
                        ++this.a;
                    }

                    return (T[])((Object[])this.d.clone());
                }
            }
        }
    }
}
