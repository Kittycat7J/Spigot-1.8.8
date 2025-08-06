package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;

public class BlockStatePredicate implements Predicate<IBlockData> {
   private final BlockStateList a;
   private final Map<IBlockState, Predicate> b = Maps.<IBlockState, Predicate>newHashMap();

   private BlockStatePredicate(BlockStateList p_i671_1_) {
      this.a = p_i671_1_;
   }

   public static BlockStatePredicate a(Block p_a_0_) {
      return new BlockStatePredicate(p_a_0_.P());
   }

   public boolean a(IBlockData p_a_1_) {
      if(p_a_1_ != null && p_a_1_.getBlock().equals(this.a.getBlock())) {
         for(Entry entry : this.b.entrySet()) {
            Comparable comparable = p_a_1_.get((IBlockState)entry.getKey());
            if(!((Predicate)entry.getValue()).apply(comparable)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public <V extends Comparable<V>> BlockStatePredicate a(IBlockState<V> p_a_1_, Predicate<? extends V> p_a_2_) {
      if(!this.a.d().contains(p_a_1_)) {
         throw new IllegalArgumentException(this.a + " cannot support property " + p_a_1_);
      } else {
         this.b.put(p_a_1_, p_a_2_);
         return this;
      }
   }
}
