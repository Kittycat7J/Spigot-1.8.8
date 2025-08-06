package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockFlowers;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenFlowers extends WorldGenerator {
   private BlockFlowers a;
   private IBlockData b;

   public WorldGenFlowers(BlockFlowers p_i697_1_, BlockFlowers.EnumFlowerVarient p_i697_2_) {
      this.a(p_i697_1_, p_i697_2_);
   }

   public void a(BlockFlowers p_a_1_, BlockFlowers.EnumFlowerVarient p_a_2_) {
      this.a = p_a_1_;
      this.b = p_a_1_.getBlockData().set(p_a_1_.n(), p_a_2_);
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      for(int i = 0; i < 64; ++i) {
         BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));
         if(p_generate_1_.isEmpty(blockposition) && (!p_generate_1_.worldProvider.o() || blockposition.getY() < 255) && this.a.f(p_generate_1_, blockposition, this.b)) {
            p_generate_1_.setTypeAndData(blockposition, this.b, 2);
         }
      }

      return true;
   }
}
