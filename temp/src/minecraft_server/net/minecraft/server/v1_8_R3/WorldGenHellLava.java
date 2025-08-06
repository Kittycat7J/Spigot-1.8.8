package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenHellLava extends WorldGenerator {
   private final Block a;
   private final boolean b;

   public WorldGenHellLava(Block p_i698_1_, boolean p_i698_2_) {
      this.a = p_i698_1_;
      this.b = p_i698_2_;
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      if(p_generate_1_.getType(p_generate_3_.up()).getBlock() != Blocks.NETHERRACK) {
         return false;
      } else if(p_generate_1_.getType(p_generate_3_).getBlock().getMaterial() != Material.AIR && p_generate_1_.getType(p_generate_3_).getBlock() != Blocks.NETHERRACK) {
         return false;
      } else {
         int i = 0;
         if(p_generate_1_.getType(p_generate_3_.west()).getBlock() == Blocks.NETHERRACK) {
            ++i;
         }

         if(p_generate_1_.getType(p_generate_3_.east()).getBlock() == Blocks.NETHERRACK) {
            ++i;
         }

         if(p_generate_1_.getType(p_generate_3_.north()).getBlock() == Blocks.NETHERRACK) {
            ++i;
         }

         if(p_generate_1_.getType(p_generate_3_.south()).getBlock() == Blocks.NETHERRACK) {
            ++i;
         }

         if(p_generate_1_.getType(p_generate_3_.down()).getBlock() == Blocks.NETHERRACK) {
            ++i;
         }

         int j = 0;
         if(p_generate_1_.isEmpty(p_generate_3_.west())) {
            ++j;
         }

         if(p_generate_1_.isEmpty(p_generate_3_.east())) {
            ++j;
         }

         if(p_generate_1_.isEmpty(p_generate_3_.north())) {
            ++j;
         }

         if(p_generate_1_.isEmpty(p_generate_3_.south())) {
            ++j;
         }

         if(p_generate_1_.isEmpty(p_generate_3_.down())) {
            ++j;
         }

         if(!this.b && i == 4 && j == 1 || i == 5) {
            p_generate_1_.setTypeAndData(p_generate_3_, this.a.getBlockData(), 2);
            p_generate_1_.a(this.a, p_generate_3_, p_generate_2_);
         }

         return true;
      }
   }
}
