package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.World;

public class NavigationSpider extends Navigation {
   private BlockPosition f;

   public NavigationSpider(EntityInsentient p_i1202_1_, World p_i1202_2_) {
      super(p_i1202_1_, p_i1202_2_);
   }

   public PathEntity a(BlockPosition p_a_1_) {
      this.f = p_a_1_;
      return super.a(p_a_1_);
   }

   public PathEntity a(Entity p_a_1_) {
      this.f = new BlockPosition(p_a_1_);
      return super.a(p_a_1_);
   }

   public boolean a(Entity p_a_1_, double p_a_2_) {
      PathEntity pathentity = this.a(p_a_1_);
      if(pathentity != null) {
         return this.a(pathentity, p_a_2_);
      } else {
         this.f = new BlockPosition(p_a_1_);
         this.e = p_a_2_;
         return true;
      }
   }

   public void k() {
      if(!this.m()) {
         super.k();
      } else {
         if(this.f != null) {
            double d0 = (double)(this.b.width * this.b.width);
            if(this.b.c(this.f) >= d0 && (this.b.locY <= (double)this.f.getY() || this.b.c(new BlockPosition(this.f.getX(), MathHelper.floor(this.b.locY), this.f.getZ())) >= d0)) {
               this.b.getControllerMove().a((double)this.f.getX(), (double)this.f.getY(), (double)this.f.getZ(), this.e);
            } else {
               this.f = null;
            }
         }

      }
   }
}
