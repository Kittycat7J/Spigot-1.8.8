package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDoor;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathPoint;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

public abstract class PathfinderGoalDoorInteract extends PathfinderGoal {
   protected EntityInsentient a;
   protected BlockPosition b = BlockPosition.ZERO;
   protected BlockDoor c;
   boolean d;
   float e;
   float f;

   public PathfinderGoalDoorInteract(EntityInsentient p_i1163_1_) {
      this.a = p_i1163_1_;
      if(!(p_i1163_1_.getNavigation() instanceof Navigation)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   public boolean a() {
      if(!this.a.positionChanged) {
         return false;
      } else {
         Navigation navigation = (Navigation)this.a.getNavigation();
         PathEntity pathentity = navigation.j();
         if(pathentity != null && !pathentity.b() && navigation.g()) {
            for(int i = 0; i < Math.min(pathentity.e() + 2, pathentity.d()); ++i) {
               PathPoint pathpoint = pathentity.a(i);
               this.b = new BlockPosition(pathpoint.a, pathpoint.b + 1, pathpoint.c);
               if(this.a.e((double)this.b.getX(), this.a.locY, (double)this.b.getZ()) <= 2.25D) {
                  this.c = this.a(this.b);
                  if(this.c != null) {
                     return true;
                  }
               }
            }

            this.b = (new BlockPosition(this.a)).up();
            this.c = this.a(this.b);
            return this.c != null;
         } else {
            return false;
         }
      }
   }

   public boolean b() {
      return !this.d;
   }

   public void c() {
      this.d = false;
      this.e = (float)((double)((float)this.b.getX() + 0.5F) - this.a.locX);
      this.f = (float)((double)((float)this.b.getZ() + 0.5F) - this.a.locZ);
   }

   public void e() {
      float fx = (float)((double)((float)this.b.getX() + 0.5F) - this.a.locX);
      float f1 = (float)((double)((float)this.b.getZ() + 0.5F) - this.a.locZ);
      float f2 = this.e * fx + this.f * f1;
      if(f2 < 0.0F) {
         this.d = true;
      }

   }

   private BlockDoor a(BlockPosition p_a_1_) {
      Block block = this.a.world.getType(p_a_1_).getBlock();
      return block instanceof BlockDoor && block.getMaterial() == Material.WOOD?(BlockDoor)block:null;
   }
}
