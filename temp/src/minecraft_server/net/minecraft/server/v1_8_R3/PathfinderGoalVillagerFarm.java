package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockCrops;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.InventorySubcontainer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PathfinderGoalGotoTarget;
import net.minecraft.server.v1_8_R3.World;

public class PathfinderGoalVillagerFarm extends PathfinderGoalGotoTarget {
   private final EntityVillager c;
   private boolean d;
   private boolean e;
   private int f;

   public PathfinderGoalVillagerFarm(EntityVillager p_i1168_1_, double p_i1168_2_) {
      super(p_i1168_1_, p_i1168_2_, 16);
      this.c = p_i1168_1_;
   }

   public boolean a() {
      if(this.a <= 0) {
         if(!this.c.world.getGameRules().getBoolean("mobGriefing")) {
            return false;
         }

         this.f = -1;
         this.d = this.c.cu();
         this.e = this.c.ct();
      }

      return super.a();
   }

   public boolean b() {
      return this.f >= 0 && super.b();
   }

   public void c() {
      super.c();
   }

   public void d() {
      super.d();
   }

   public void e() {
      super.e();
      this.c.getControllerLook().a((double)this.b.getX() + 0.5D, (double)(this.b.getY() + 1), (double)this.b.getZ() + 0.5D, 10.0F, (float)this.c.bQ());
      if(this.f()) {
         World world = this.c.world;
         BlockPosition blockposition = this.b.up();
         IBlockData iblockdata = world.getType(blockposition);
         Block block = iblockdata.getBlock();
         if(this.f == 0 && block instanceof BlockCrops && ((Integer)iblockdata.get(BlockCrops.AGE)).intValue() == 7) {
            world.setAir(blockposition, true);
         } else if(this.f == 1 && block == Blocks.AIR) {
            InventorySubcontainer inventorysubcontainer = this.c.cq();

            for(int i = 0; i < inventorysubcontainer.getSize(); ++i) {
               ItemStack itemstack = inventorysubcontainer.getItem(i);
               boolean flag = false;
               if(itemstack != null) {
                  if(itemstack.getItem() == Items.WHEAT_SEEDS) {
                     world.setTypeAndData(blockposition, Blocks.WHEAT.getBlockData(), 3);
                     flag = true;
                  } else if(itemstack.getItem() == Items.POTATO) {
                     world.setTypeAndData(blockposition, Blocks.POTATOES.getBlockData(), 3);
                     flag = true;
                  } else if(itemstack.getItem() == Items.CARROT) {
                     world.setTypeAndData(blockposition, Blocks.CARROTS.getBlockData(), 3);
                     flag = true;
                  }
               }

               if(flag) {
                  --itemstack.count;
                  if(itemstack.count <= 0) {
                     inventorysubcontainer.setItem(i, (ItemStack)null);
                  }
                  break;
               }
            }
         }

         this.f = -1;
         this.a = 10;
      }

   }

   protected boolean a(World p_a_1_, BlockPosition p_a_2_) {
      Block block = p_a_1_.getType(p_a_2_).getBlock();
      if(block == Blocks.FARMLAND) {
         p_a_2_ = p_a_2_.up();
         IBlockData iblockdata = p_a_1_.getType(p_a_2_);
         block = iblockdata.getBlock();
         if(block instanceof BlockCrops && ((Integer)iblockdata.get(BlockCrops.AGE)).intValue() == 7 && this.e && (this.f == 0 || this.f < 0)) {
            this.f = 0;
            return true;
         }

         if(block == Blocks.AIR && this.d && (this.f == 1 || this.f < 0)) {
            this.f = 1;
            return true;
         }
      }

      return false;
   }
}
