package net.minecraft.server.v1_8_R3;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;

public abstract class BlockDataAbstract implements IBlockData {
   private static final Joiner a = Joiner.on(',');
   private static final Function<Entry<IBlockState, Comparable>, String> b = new Function<Entry<IBlockState, Comparable>, String>() {
      public String a(Entry<IBlockState, Comparable> p_a_1_) {
         if(p_a_1_ == null) {
            return "<NULL>";
         } else {
            IBlockState iblockstate = (IBlockState)p_a_1_.getKey();
            return iblockstate.a() + "=" + iblockstate.a((Comparable)p_a_1_.getValue());
         }
      }
   };

   public <T extends Comparable<T>> IBlockData a(IBlockState<T> p_a_1_) {
      return this.set(p_a_1_, (Comparable)a(p_a_1_.c(), this.get(p_a_1_)));
   }

   protected static <T> T a(Collection<T> p_a_0_, T p_a_1_) {
      Iterator iterator = p_a_0_.iterator();

      while(iterator.hasNext()) {
         if(iterator.next().equals(p_a_1_)) {
            if(iterator.hasNext()) {
               return (T)iterator.next();
            }

            return (T)p_a_0_.iterator().next();
         }
      }

      return (T)iterator.next();
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(Block.REGISTRY.c(this.getBlock()));
      if(!this.b().isEmpty()) {
         stringbuilder.append("[");
         a.appendTo(stringbuilder, Iterables.transform(this.b().entrySet(), b));
         stringbuilder.append("]");
      }

      return stringbuilder.toString();
   }
}
