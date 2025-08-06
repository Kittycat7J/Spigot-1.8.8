package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;

public class BlockStateDirection extends BlockStateEnum<EnumDirection>
{
    protected BlockStateDirection(String p_i674_1_, Collection<EnumDirection> p_i674_2_)
    {
        super(p_i674_1_, EnumDirection.class, p_i674_2_);
    }

    public static BlockStateDirection of(String p_of_0_)
    {
        return of(p_of_0_, Predicates.<EnumDirection>alwaysTrue());
    }

    public static BlockStateDirection of(String p_of_0_, Predicate<EnumDirection> p_of_1_)
    {
        return a(p_of_0_, Collections2.<EnumDirection>filter(Lists.newArrayList(EnumDirection.values()), p_of_1_));
    }

    public static BlockStateDirection a(String p_a_0_, Collection<EnumDirection> p_a_1_)
    {
        return new BlockStateDirection(p_a_0_, p_a_1_);
    }
}
