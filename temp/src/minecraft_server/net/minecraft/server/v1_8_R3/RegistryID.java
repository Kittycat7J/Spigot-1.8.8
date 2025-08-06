package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_8_R3.Registry;

public class RegistryID<T> implements Registry<T> {
   private final IdentityHashMap<T, Integer> a = new IdentityHashMap(512);
   private final List<T> b = Lists.<T>newArrayList();

   public void a(T p_a_1_, int p_a_2_) {
      this.a.put(p_a_1_, Integer.valueOf(p_a_2_));

      while(this.b.size() <= p_a_2_) {
         this.b.add((Object)null);
      }

      this.b.set(p_a_2_, p_a_1_);
   }

   public int b(T p_b_1_) {
      Integer integer = (Integer)this.a.get(p_b_1_);
      return integer == null?-1:integer.intValue();
   }

   public final T a(int p_a_1_) {
      return (T)(p_a_1_ >= 0 && p_a_1_ < this.b.size()?this.b.get(p_a_1_):null);
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.b.iterator(), Predicates.notNull());
   }
}
