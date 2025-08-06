package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class PlayerAbilities {
   public boolean isInvulnerable;
   public boolean isFlying;
   public boolean canFly;
   public boolean canInstantlyBuild;
   public boolean mayBuild = true;
   public float flySpeed = 0.05F;
   public float walkSpeed = 0.1F;

   public void a(NBTTagCompound p_a_1_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setBoolean("invulnerable", this.isInvulnerable);
      nbttagcompound.setBoolean("flying", this.isFlying);
      nbttagcompound.setBoolean("mayfly", this.canFly);
      nbttagcompound.setBoolean("instabuild", this.canInstantlyBuild);
      nbttagcompound.setBoolean("mayBuild", this.mayBuild);
      nbttagcompound.setFloat("flySpeed", this.flySpeed);
      nbttagcompound.setFloat("walkSpeed", this.walkSpeed);
      p_a_1_.set("abilities", nbttagcompound);
   }

   public void b(NBTTagCompound p_b_1_) {
      if(p_b_1_.hasKeyOfType("abilities", 10)) {
         NBTTagCompound nbttagcompound = p_b_1_.getCompound("abilities");
         this.isInvulnerable = nbttagcompound.getBoolean("invulnerable");
         this.isFlying = nbttagcompound.getBoolean("flying");
         this.canFly = nbttagcompound.getBoolean("mayfly");
         this.canInstantlyBuild = nbttagcompound.getBoolean("instabuild");
         if(nbttagcompound.hasKeyOfType("flySpeed", 99)) {
            this.flySpeed = nbttagcompound.getFloat("flySpeed");
            this.walkSpeed = nbttagcompound.getFloat("walkSpeed");
         }

         if(nbttagcompound.hasKeyOfType("mayBuild", 1)) {
            this.mayBuild = nbttagcompound.getBoolean("mayBuild");
         }
      }

   }

   public float a() {
      return this.flySpeed;
   }

   public float b() {
      return this.walkSpeed;
   }
}
