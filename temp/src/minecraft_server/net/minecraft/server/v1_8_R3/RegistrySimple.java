package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.v1_8_R3.IRegistry;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple<K, V> implements IRegistry<K, V> {
   private static final Logger a = LogManager.getLogger();
   protected final Map<K, V> c = this.b();

   protected Map<K, V> b() {
      return Maps.<K, V>newHashMap();
   }

   public V get(K p_get_1_) {
      return (V)this.c.get(p_get_1_);
   }

   public void a(K p_a_1_, V p_a_2_) {
      Validate.notNull(p_a_1_);
      Validate.notNull(p_a_2_);
      if(this.c.containsKey(p_a_1_)) {
         a.debug("Adding duplicate key \'" + p_a_1_ + "\' to registry");
      }

      this.c.put(p_a_1_, p_a_2_);
   }

   public Set<K> keySet() {
      return Collections.unmodifiableSet(this.c.keySet());
   }

   public boolean d(K p_d_1_) {
      return this.c.containsKey(p_d_1_);
   }

   public Iterator<V> iterator() {
      return this.c.values().iterator();
   }
}
