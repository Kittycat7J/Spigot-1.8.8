package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;

public class BlockStateEnum<T extends Enum<T> & INamable> extends BlockState<T>
{
    private final ImmutableSet<T> a;
    private final Map<String, T> b = Maps.<String, T>newHashMap();

    protected BlockStateEnum(String p_i675_1_, Class<T> p_i675_2_, Collection<T> p_i675_3_)
    {
        super(p_i675_1_, p_i675_2_);
        this.a = ImmutableSet.copyOf(p_i675_3_);

        for (Enum oenum : p_i675_3_)
        {
            String s = ((INamable)oenum).getName();

            if (this.b.containsKey(s))
            {
                throw new IllegalArgumentException("Multiple values have the same name \'" + s + "\'");
            }

            this.b.put(s, oenum);
        }
    }

    public Collection<T> c()
    {
        return this.a;
    }

    public String a(T p_a_1_)
    {
        return ((INamable)p_a_1_).getName();
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> of(String p_of_0_, Class<T> p_of_1_)
    {
        return a(p_of_0_, p_of_1_, Predicates.alwaysTrue());
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String p_a_0_, Class<T> p_a_1_, Predicate<T> p_a_2_)
    {
        return a(p_a_0_, p_a_1_, Collections2.filter(Lists.newArrayList(p_a_1_.getEnumConstants()), p_a_2_));
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> of(String p_of_0_, Class<T> p_of_1_, T... p_of_2_)
    {
        return a(p_of_0_, p_of_1_, Lists.newArrayList(p_of_2_));
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String p_a_0_, Class<T> p_a_1_, Collection<T> p_a_2_)
    {
        return new BlockStateEnum(p_a_0_, p_a_1_, p_a_2_);
    }
}
