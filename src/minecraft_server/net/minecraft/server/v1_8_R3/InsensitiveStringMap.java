package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class InsensitiveStringMap<V> implements Map<String, V>
{
    private final Map<String, V> a = Maps.<String, V>newLinkedHashMap();

    public int size()
    {
        return this.a.size();
    }

    public boolean isEmpty()
    {
        return this.a.isEmpty();
    }

    public boolean containsKey(Object p_containsKey_1_)
    {
        return this.a.containsKey(p_containsKey_1_.toString().toLowerCase());
    }

    public boolean containsValue(Object p_containsValue_1_)
    {
        return this.a.containsKey(p_containsValue_1_);
    }

    public V get(Object p_get_1_)
    {
        return (V)this.a.get(p_get_1_.toString().toLowerCase());
    }

    public V a(String p_a_1_, V p_a_2_)
    {
        return (V)this.a.put(p_a_1_.toLowerCase(), p_a_2_);
    }

    public V remove(Object p_remove_1_)
    {
        return (V)this.a.remove(p_remove_1_.toString().toLowerCase());
    }

    public void putAll(Map <? extends String, ? extends V > p_putAll_1_)
    {
        for (Entry entry : p_putAll_1_.entrySet())
        {
            this.a((String)entry.getKey(), entry.getValue());
        }
    }

    public void clear()
    {
        this.a.clear();
    }

    public Set<String> keySet()
    {
        return this.a.keySet();
    }

    public Collection<V> values()
    {
        return this.a.values();
    }

    public Set<Entry<String, V>> entrySet()
    {
        return this.a.entrySet();
    }
}
