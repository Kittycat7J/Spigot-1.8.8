package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.StructurePiece;

class WorldGenRegistration$WorldGenJungleTemple$WorldGenJungleTemplePiece extends StructurePiece.StructurePieceBlockSelector {
   private WorldGenRegistration$WorldGenJungleTemple$WorldGenJungleTemplePiece() {
   }

   public void a(Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, boolean p_a_5_) {
      if(p_a_1_.nextFloat() < 0.4F) {
         this.a = Blocks.COBBLESTONE.getBlockData();
      } else {
         this.a = Blocks.MOSSY_COBBLESTONE.getBlockData();
      }

   }
}
