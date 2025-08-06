package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;

public abstract class WorldGenerator {
   private final boolean a;

   public WorldGenerator() {
      this(false);
   }

   public WorldGenerator(boolean p_i696_1_) {
      this.a = p_i696_1_;
   }

   public abstract boolean generate(World var1, Random var2, BlockPosition var3);

   public void e() {
   }

   protected void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      if(this.a) {
         p_a_1_.setTypeAndData(p_a_2_, p_a_3_, 3);
      } else {
         p_a_1_.setTypeAndData(p_a_2_, p_a_3_, 2);
      }

   }
}
