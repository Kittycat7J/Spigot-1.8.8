package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.World;

public class PathfinderGoalBeg extends PathfinderGoal {
   private EntityWolf a;
   private EntityHuman b;
   private World c;
   private float d;
   private int e;

   public PathfinderGoalBeg(EntityWolf p_i1161_1_, float p_i1161_2_) {
      this.a = p_i1161_1_;
      this.c = p_i1161_1_.world;
      this.d = p_i1161_2_;
      this.a(2);
   }

   public boolean a() {
      this.b = this.c.findNearbyPlayer(this.a, (double)this.d);
      return this.b == null?false:this.a(this.b);
   }

   public boolean b() {
      return !this.b.isAlive()?false:(this.a.h(this.b) > (double)(this.d * this.d)?false:this.e > 0 && this.a(this.b));
   }

   public void c() {
      this.a.p(true);
      this.e = 40 + this.a.bc().nextInt(40);
   }

   public void d() {
      this.a.p(false);
      this.b = null;
   }

   public void e() {
      this.a.getControllerLook().a(this.b.locX, this.b.locY + (double)this.b.getHeadHeight(), this.b.locZ, 10.0F, (float)this.a.bQ());
      --this.e;
   }

   private boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      return itemstack == null?false:(!this.a.isTamed() && itemstack.getItem() == Items.BONE?true:this.a.d(itemstack));
   }
}
