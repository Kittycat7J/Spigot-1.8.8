package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.IBlockData;

public class BlockPredicate implements Predicate<IBlockData> {
   private final Block a;

   private BlockPredicate(Block p_i670_1_) {
      this.a = p_i670_1_;
   }

   public static BlockPredicate a(Block p_a_0_) {
      return new BlockPredicate(p_a_0_);
   }

   public boolean a(IBlockData p_a_1_) {
      return p_a_1_ != null && p_a_1_.getBlock() == this.a;
   }
}
