package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockTallPlant;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenTallPlant extends WorldGenerator {
   private BlockTallPlant.EnumTallFlowerVariants a;

   public void a(BlockTallPlant.EnumTallFlowerVariants p_a_1_) {
      this.a = p_a_1_;
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      boolean flag = false;

      for(int i = 0; i < 64; ++i) {
         BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));
         if(p_generate_1_.isEmpty(blockposition) && (!p_generate_1_.worldProvider.o() || blockposition.getY() < 254) && Blocks.DOUBLE_PLANT.canPlace(p_generate_1_, blockposition)) {
            Blocks.DOUBLE_PLANT.a(p_generate_1_, blockposition, this.a, 2);
            flag = true;
         }
      }

      return flag;
   }
}
