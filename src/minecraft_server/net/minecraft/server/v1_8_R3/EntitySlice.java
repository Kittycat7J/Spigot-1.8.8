package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntitySlice<T> extends AbstractSet<T>
{
    private static final Set < Class<? >> a = Sets. < Class<? >> newConcurrentHashSet();
    private final Map < Class<?>, List<T >> b = Maps. < Class<?>, List<T >> newHashMap();
    private final Set < Class<? >> c = Sets. < Class<? >> newIdentityHashSet();
    private final Class<T> d;
    private final List<T> e = Lists.<T>newArrayList();

    public EntitySlice(Class<T> p_i358_1_)
    {
        this.d = p_i358_1_;
        this.c.add(p_i358_1_);
        this.b.put(p_i358_1_, this.e);

        for (Class oclass : a)
        {
            this.a(oclass);
        }
    }

    protected void a(Class<?> p_a_1_)
    {
        a.add(p_a_1_);

        for (Object object : this.e)
        {
            if (p_a_1_.isAssignableFrom(object.getClass()))
            {
                this.a(object, p_a_1_);
            }
        }

        this.c.add(p_a_1_);
    }

    protected Class<?> b(Class<?> p_b_1_)
    {
        if (this.d.isAssignableFrom(p_b_1_))
        {
            if (!this.c.contains(p_b_1_))
            {
                this.a(p_b_1_);
            }

            return p_b_1_;
        }
        else
        {
            throw new IllegalArgumentException("Don\'t know how to search for " + p_b_1_);
        }
    }

    public boolean add(T p_add_1_)
    {
        for (Class oclass : this.c)
        {
            if (oclass.isAssignableFrom(p_add_1_.getClass()))
            {
                this.a(p_add_1_, oclass);
            }
        }

        return true;
    }

    private void a(T p_a_1_, Class<?> p_a_2_)
    {
        List list = (List)this.b.get(p_a_2_);

        if (list == null)
        {
            this.b.put(p_a_2_, Lists.newArrayList(new Object[] {p_a_1_}));
        }
        else
        {
            list.add(p_a_1_);
        }
    }

    public boolean remove(Object p_remove_1_)
    {
        Object object = p_remove_1_;
        boolean flag = false;

        for (Class oclass : this.c)
        {
            if (oclass.isAssignableFrom(object.getClass()))
            {
                List list = (List)this.b.get(oclass);

                if (list != null && list.remove(object))
                {
                    flag = true;
                }
            }
        }

        return flag;
    }

    public boolean contains(Object p_contains_1_)
    {
        return Iterators.contains(this.c(p_contains_1_.getClass()).iterator(), p_contains_1_);
    }

    public <S> Iterable<S> c(final Class<S> p_c_1_)
    {
        return new Iterable()
        {
            public Iterator<S> iterator()
            {
                List list = (List)EntitySlice.this.b.get(EntitySlice.this.b(p_c_1_));

                if (list == null)
                {
                    return Iterators.<S>emptyIterator();
                }
                else
                {
                    Iterator iterator = list.iterator();
                    return Iterators.filter(iterator, p_c_1_);
                }
            }
        };
    }

    public Iterator<T> iterator()
    {
        return this.e.isEmpty() ? Iterators.emptyIterator() : Iterators.unmodifiableIterator(this.e.iterator());
    }

    public int size()
    {
        return this.e.size();
    }
}
