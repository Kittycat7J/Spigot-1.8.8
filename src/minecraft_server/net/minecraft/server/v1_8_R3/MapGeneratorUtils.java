package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapGeneratorUtils {
   public static <K, V> Map<K, V> b(Iterable<K> p_b_0_, Iterable<V> p_b_1_) {
      return a(p_b_0_, p_b_1_, Maps.<K, V>newLinkedHashMap());
   }

   public static <K, V> Map<K, V> a(Iterable<K> p_a_0_, Iterable<V> p_a_1_, Map<K, V> p_a_2_) {
      Iterator iterator = p_a_1_.iterator();

      for(Object object : p_a_0_) {
         p_a_2_.put(object, iterator.next());
      }

      if(iterator.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return p_a_2_;
      }
   }
}
