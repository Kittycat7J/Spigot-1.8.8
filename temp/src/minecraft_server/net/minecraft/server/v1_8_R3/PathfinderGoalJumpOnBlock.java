package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockBed;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.PathfinderGoalGotoTarget;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;

public class PathfinderGoalJumpOnBlock extends PathfinderGoalGotoTarget {
   private final EntityOcelot c;

   public PathfinderGoalJumpOnBlock(EntityOcelot p_i1182_1_, double p_i1182_2_) {
      super(p_i1182_1_, p_i1182_2_, 8);
      this.c = p_i1182_1_;
   }

   public boolean a() {
      return this.c.isTamed() && !this.c.isSitting() && super.a();
   }

   public boolean b() {
      return super.b();
   }

   public void c() {
      super.c();
      this.c.getGoalSit().setSitting(false);
   }

   public void d() {
      super.d();
      this.c.setSitting(false);
   }

   public void e() {
      super.e();
      this.c.getGoalSit().setSitting(false);
      if(!this.f()) {
         this.c.setSitting(false);
      } else if(!this.c.isSitting()) {
         this.c.setSitting(true);
      }

   }

   protected boolean a(World p_a_1_, BlockPosition p_a_2_) {
      if(!p_a_1_.isEmpty(p_a_2_.up())) {
         return false;
      } else {
         IBlockData iblockdata = p_a_1_.getType(p_a_2_);
         Block block = iblockdata.getBlock();
         if(block == Blocks.CHEST) {
            TileEntity tileentity = p_a_1_.getTileEntity(p_a_2_);
            if(tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).l < 1) {
               return true;
            }
         } else {
            if(block == Blocks.LIT_FURNACE) {
               return true;
            }

            if(block == Blocks.BED && iblockdata.get(BlockBed.PART) != BlockBed.EnumBedPart.HEAD) {
               return true;
            }
         }

         return false;
      }
   }
}
