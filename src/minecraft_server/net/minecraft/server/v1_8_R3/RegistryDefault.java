package net.minecraft.server.v1_8_R3;

public class RegistryDefault<K, V> extends RegistrySimple<K, V>
{
    private final V a;

    public RegistryDefault(V p_i894_1_)
    {
        this.a = p_i894_1_;
    }

    public V get(K p_get_1_)
    {
        Object object = super.get(p_get_1_);
        return (V)(object == null ? this.a : object);
    }
}
