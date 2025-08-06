package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.World;

public class EntityMagmaCube extends EntitySlime {
   public EntityMagmaCube(World p_i1240_1_) {
      super(p_i1240_1_);
      this.fireProof = true;
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
   }

   public boolean bR() {
      return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
   }

   public boolean canSpawn() {
      return this.world.a((AxisAlignedBB)this.getBoundingBox(), (Entity)this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
   }

   public int br() {
      return this.getSize() * 3;
   }

   public float c(float p_c_1_) {
      return 1.0F;
   }

   protected EnumParticle n() {
      return EnumParticle.FLAME;
   }

   protected EntitySlime cf() {
      return new EntityMagmaCube(this.world);
   }

   protected Item getLoot() {
      return Items.MAGMA_CREAM;
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      Item item = this.getLoot();
      if(item != null && this.getSize() > 1) {
         int i = this.random.nextInt(4) - 2;
         if(p_dropDeathLoot_2_ > 0) {
            i += this.random.nextInt(p_dropDeathLoot_2_ + 1);
         }

         for(int j = 0; j < i; ++j) {
            this.a(item, 1);
         }
      }

   }

   public boolean isBurning() {
      return false;
   }

   protected int cg() {
      return super.cg() * 4;
   }

   protected void ch() {
      this.a *= 0.9F;
   }

   protected void bF() {
      this.motY = (double)(0.42F + (float)this.getSize() * 0.1F);
      this.ai = true;
   }

   protected void bH() {
      this.motY = (double)(0.22F + (float)this.getSize() * 0.05F);
      this.ai = true;
   }

   public void e(float p_e_1_, float p_e_2_) {
   }

   protected boolean ci() {
      return true;
   }

   protected int cj() {
      return super.cj() + 2;
   }

   protected String ck() {
      return this.getSize() > 1?"mob.magmacube.big":"mob.magmacube.small";
   }

   protected boolean cl() {
      return true;
   }
}
