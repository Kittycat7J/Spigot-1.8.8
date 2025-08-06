package net.minecraft.server.v1_8_R3;

import com.google.common.base.Objects;
import net.minecraft.server.v1_8_R3.IBlockState;

public abstract class BlockState<T extends Comparable<T>> implements IBlockState<T> {
   private final Class<T> a;
   private final String b;

   protected BlockState(String p_i672_1_, Class<T> p_i672_2_) {
      this.a = p_i672_2_;
      this.b = p_i672_1_;
   }

   public String a() {
      return this.b;
   }

   public Class<T> b() {
      return this.a;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("name", this.b).add("clazz", this.a).add("values", this.c()).toString();
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         BlockState blockstate = (BlockState)p_equals_1_;
         return this.a.equals(blockstate.a) && this.b.equals(blockstate.b);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 31 * this.a.hashCode() + this.b.hashCode();
   }
}
