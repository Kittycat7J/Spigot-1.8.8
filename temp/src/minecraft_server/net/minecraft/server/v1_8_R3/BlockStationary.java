package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFlowing;
import net.minecraft.server.v1_8_R3.BlockFluids;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockStationary extends BlockFluids {
   protected BlockStationary(Material p_i351_1_) {
      super(p_i351_1_);
      this.a(false);
      if(p_i351_1_ == Material.LAVA) {
         this.a(true);
      }

   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_)) {
         this.f(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
      }

   }

   private void f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_) {
      BlockFlowing blockflowing = a(this.material);
      p_f_1_.setTypeAndData(p_f_2_, blockflowing.getBlockData().set(LEVEL, (Integer)p_f_3_.get(LEVEL)), 2);
      p_f_1_.a((BlockPosition)p_f_2_, (Block)blockflowing, this.a(p_f_1_));
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(this.material == Material.LAVA && p_b_1_.getGameRules().getBoolean("doFireTick")) {
         int i = p_b_4_.nextInt(3);
         if(i > 0) {
            BlockPosition blockposition2 = p_b_2_;

            for(int k = 0; k < i; ++k) {
               blockposition2 = blockposition2.a(p_b_4_.nextInt(3) - 1, 1, p_b_4_.nextInt(3) - 1);
               Block block = p_b_1_.getType(blockposition2).getBlock();
               if(block.material == Material.AIR) {
                  if(this.f(p_b_1_, blockposition2) && (p_b_1_.getType(blockposition2) == Blocks.FIRE || !CraftEventFactory.callBlockIgniteEvent(p_b_1_, blockposition2.getX(), blockposition2.getY(), blockposition2.getZ(), p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()).isCancelled())) {
                     p_b_1_.setTypeUpdate(blockposition2, Blocks.FIRE.getBlockData());
                     return;
                  }
               } else if(block.material.isSolid()) {
                  return;
               }
            }
         } else {
            for(int j = 0; j < 3; ++j) {
               BlockPosition blockposition = p_b_2_.a(p_b_4_.nextInt(3) - 1, 0, p_b_4_.nextInt(3) - 1);
               if(p_b_1_.isEmpty(blockposition.up()) && this.m(p_b_1_, blockposition)) {
                  BlockPosition blockposition1 = blockposition.up();
                  if(p_b_1_.getType(blockposition1) == Blocks.FIRE || !CraftEventFactory.callBlockIgniteEvent(p_b_1_, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()).isCancelled()) {
                     p_b_1_.setTypeUpdate(blockposition.up(), Blocks.FIRE.getBlockData());
                  }
               }
            }
         }
      }

   }

   protected boolean f(World p_f_1_, BlockPosition p_f_2_) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         if(this.m(p_f_1_, p_f_2_.shift(enumdirection))) {
            return true;
         }
      }

      return false;
   }

   private boolean m(World p_m_1_, BlockPosition p_m_2_) {
      return p_m_1_.getType(p_m_2_).getBlock().getMaterial().isBurnable();
   }
}
