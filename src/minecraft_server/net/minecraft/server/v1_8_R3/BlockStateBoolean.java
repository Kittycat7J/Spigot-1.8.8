package net.minecraft.server.v1_8_R3;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;

public class BlockStateBoolean extends BlockState<Boolean>
{
    private final ImmutableSet<Boolean> a = ImmutableSet.<Boolean>of(Boolean.valueOf(true), Boolean.valueOf(false));

    protected BlockStateBoolean(String p_i673_1_)
    {
        super(p_i673_1_, Boolean.class);
    }

    public Collection<Boolean> c()
    {
        return this.a;
    }

    public static BlockStateBoolean of(String p_of_0_)
    {
        return new BlockStateBoolean(p_of_0_);
    }

    public String a(Boolean p_a_1_)
    {
        return p_a_1_.toString();
    }
}
