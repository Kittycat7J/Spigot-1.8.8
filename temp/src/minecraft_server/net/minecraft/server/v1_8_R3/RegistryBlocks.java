package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.RegistryMaterials;
import org.apache.commons.lang3.Validate;

public class RegistryBlocks<K, V> extends RegistryMaterials<K, V> {
   private final K d;
   private V e;

   public RegistryBlocks(K p_i893_1_) {
      this.d = p_i893_1_;
   }

   public void a(int p_a_1_, K p_a_2_, V p_a_3_) {
      if(this.d.equals(p_a_2_)) {
         this.e = p_a_3_;
      }

      super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public void a() {
      Validate.notNull(this.d);
   }

   public V get(K p_get_1_) {
      Object object = super.get(p_get_1_);
      return (V)(object == null?this.e:object);
   }

   public V a(int p_a_1_) {
      Object object = super.a(p_a_1_);
      return (V)(object == null?this.e:object);
   }
}
