package net.minecraft.server.v1_8_R3;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BlockStateList
{
    private static final Joiner a = Joiner.on(", ");
    private static final Function<IBlockState, String> b = new Function<IBlockState, String>()
    {
        public String a(IBlockState p_a_1_)
        {
            return p_a_1_ == null ? "<NULL>" : p_a_1_.a();
        }
    };
    private final Block c;
    private final ImmutableList<IBlockState> d;
    private final ImmutableList<IBlockData> e;

    public BlockStateList(Block p_i664_1_, IBlockState... p_i664_2_)
    {
        this.c = p_i664_1_;
        Arrays.sort(p_i664_2_, new Comparator<IBlockState>()
        {
            public int a(IBlockState p_a_1_, IBlockState p_a_2_)
            {
                return p_a_1_.a().compareTo(p_a_2_.a());
            }
        });
        this.d = ImmutableList.copyOf(p_i664_2_);
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
        ArrayList arraylist = Lists.newArrayList();

        for (List list : IteratorUtils.a(this.e()))
        {
            Map map = MapGeneratorUtils.b(this.d, list);
            BlockStateList.BlockData blockstatelist$blockdata = new BlockStateList.BlockData(p_i664_1_, ImmutableMap.copyOf(map));
            linkedhashmap.put(map, blockstatelist$blockdata);
            arraylist.add(blockstatelist$blockdata);
        }

        for (BlockStateList.BlockData blockstatelist$blockdata1 : arraylist)
        {
            blockstatelist$blockdata1.a(linkedhashmap);
        }

        this.e = ImmutableList.copyOf(arraylist);
    }

    public ImmutableList<IBlockData> a()
    {
        return this.e;
    }

    private List<Iterable<Comparable>> e()
    {
        ArrayList arraylist = Lists.newArrayList();

        for (int i = 0; i < this.d.size(); ++i)
        {
            arraylist.add(((IBlockState)this.d.get(i)).c());
        }

        return arraylist;
    }

    public IBlockData getBlockData()
    {
        return (IBlockData)this.e.get(0);
    }

    public Block getBlock()
    {
        return this.c;
    }

    public Collection<IBlockState> d()
    {
        return this.d;
    }

    public String toString()
    {
        return Objects.toStringHelper(this).add("block", Block.REGISTRY.c(this.c)).add("properties", Iterables.transform(this.d, b)).toString();
    }

    static class BlockData extends BlockDataAbstract
    {
        private final Block a;
        private final ImmutableMap<IBlockState, Comparable> b;
        private ImmutableTable<IBlockState, Comparable, IBlockData> c;

        private BlockData(Block p_i662_1_, ImmutableMap<IBlockState, Comparable> p_i662_2_)
        {
            this.a = p_i662_1_;
            this.b = p_i662_2_;
        }

        public Collection<IBlockState> a()
        {
            return Collections.unmodifiableCollection(this.b.keySet());
        }

        public <T extends Comparable<T>> T get(IBlockState<T> p_get_1_)
        {
            if (!this.b.containsKey(p_get_1_))
            {
                throw new IllegalArgumentException("Cannot get property " + p_get_1_ + " as it does not exist in " + this.a.P());
            }
            else
            {
                return (T)((Comparable)p_get_1_.b().cast(this.b.get(p_get_1_)));
            }
        }

        public <T extends Comparable<T>, V extends T> IBlockData set(IBlockState<T> p_set_1_, V p_set_2_)
        {
            if (!this.b.containsKey(p_set_1_))
            {
                throw new IllegalArgumentException("Cannot set property " + p_set_1_ + " as it does not exist in " + this.a.P());
            }
            else if (!p_set_1_.c().contains(p_set_2_))
            {
                throw new IllegalArgumentException("Cannot set property " + p_set_1_ + " to " + p_set_2_ + " on block " + Block.REGISTRY.c(this.a) + ", it is not an allowed value");
            }
            else
            {
                return (IBlockData)(this.b.get(p_set_1_) == p_set_2_ ? this : (IBlockData)this.c.get(p_set_1_, p_set_2_));
            }
        }

        public ImmutableMap<IBlockState, Comparable> b()
        {
            return this.b;
        }

        public Block getBlock()
        {
            return this.a;
        }

        public boolean equals(Object p_equals_1_)
        {
            return this == p_equals_1_;
        }

        public int hashCode()
        {
            return this.b.hashCode();
        }

        public void a(Map<Map<IBlockState, Comparable>, BlockStateList.BlockData> p_a_1_)
        {
            if (this.c != null)
            {
                throw new IllegalStateException();
            }
            else
            {
                HashBasedTable hashbasedtable = HashBasedTable.create();

                for (IBlockState iblockstate : this.b.keySet())
                {
                    for (Comparable comparable : iblockstate.c())
                    {
                        if (comparable != this.b.get(iblockstate))
                        {
                            hashbasedtable.put(iblockstate, comparable, p_a_1_.get(this.b(iblockstate, comparable)));
                        }
                    }
                }

                this.c = ImmutableTable.<IBlockState, Comparable, IBlockData>copyOf(hashbasedtable);
            }
        }

        private Map<IBlockState, Comparable> b(IBlockState p_b_1_, Comparable p_b_2_)
        {
            HashMap hashmap = Maps.newHashMap(this.b);
            hashmap.put(p_b_1_, p_b_2_);
            return hashmap;
        }
    }
}
