package net.minecraft.server.v1_8_R3;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.v1_8_R3.Registry;
import net.minecraft.server.v1_8_R3.RegistryID;
import net.minecraft.server.v1_8_R3.RegistrySimple;

public class RegistryMaterials<K, V> extends RegistrySimple<K, V> implements Registry<V> {
   protected final RegistryID<V> a = new RegistryID();
   protected final Map<V, K> b;

   public RegistryMaterials() {
      this.b = ((BiMap)this.c).inverse();
   }

   public void a(int p_a_1_, K p_a_2_, V p_a_3_) {
      this.a.a(p_a_3_, p_a_1_);
      this.a(p_a_2_, p_a_3_);
   }

   protected Map<K, V> b() {
      return HashBiMap.<K, V>create();
   }

   public V get(K p_get_1_) {
      return super.get(p_get_1_);
   }

   public K c(V p_c_1_) {
      return (K)this.b.get(p_c_1_);
   }

   public boolean d(K p_d_1_) {
      return super.d(p_d_1_);
   }

   public int b(V p_b_1_) {
      return this.a.b(p_b_1_);
   }

   public V a(int p_a_1_) {
      return (V)this.a.a(p_a_1_);
   }

   public Iterator<V> iterator() {
      return this.a.iterator();
   }
}
