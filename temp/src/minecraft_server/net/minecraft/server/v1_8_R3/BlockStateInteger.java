package net.minecraft.server.v1_8_R3;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import net.minecraft.server.v1_8_R3.BlockState;

public class BlockStateInteger extends BlockState<Integer> {
   private final ImmutableSet<Integer> a;

   protected BlockStateInteger(String p_i676_1_, int p_i676_2_, int p_i676_3_) {
      super(p_i676_1_, Integer.class);
      if(p_i676_2_ < 0) {
         throw new IllegalArgumentException("Min value of " + p_i676_1_ + " must be 0 or greater");
      } else if(p_i676_3_ <= p_i676_2_) {
         throw new IllegalArgumentException("Max value of " + p_i676_1_ + " must be greater than min (" + p_i676_2_ + ")");
      } else {
         HashSet hashset = Sets.newHashSet();

         for(int i = p_i676_2_; i <= p_i676_3_; ++i) {
            hashset.add(Integer.valueOf(i));
         }

         this.a = ImmutableSet.copyOf(hashset);
      }
   }

   public Collection<Integer> c() {
      return this.a;
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         if(!super.equals(p_equals_1_)) {
            return false;
         } else {
            BlockStateInteger blockstateinteger = (BlockStateInteger)p_equals_1_;
            return this.a.equals(blockstateinteger.a);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = super.hashCode();
      i = 31 * i + this.a.hashCode();
      return i;
   }

   public static BlockStateInteger of(String p_of_0_, int p_of_1_, int p_of_2_) {
      return new BlockStateInteger(p_of_0_, p_of_1_, p_of_2_);
   }

   public String a(Integer p_a_1_) {
      return p_a_1_.toString();
   }
}
