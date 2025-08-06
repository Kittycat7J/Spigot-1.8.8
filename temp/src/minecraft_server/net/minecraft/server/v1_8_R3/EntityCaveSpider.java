package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.World;

public class EntityCaveSpider extends EntitySpider {
   public EntityCaveSpider(World p_i1232_1_) {
      super(p_i1232_1_);
      this.setSize(0.7F, 0.5F);
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.maxHealth).setValue(12.0D);
   }

   public boolean r(Entity p_r_1_) {
      if(super.r(p_r_1_)) {
         if(p_r_1_ instanceof EntityLiving) {
            byte b0 = 0;
            if(this.world.getDifficulty() == EnumDifficulty.NORMAL) {
               b0 = 7;
            } else if(this.world.getDifficulty() == EnumDifficulty.HARD) {
               b0 = 15;
            }

            if(b0 > 0) {
               ((EntityLiving)p_r_1_).addEffect(new MobEffect(MobEffectList.POISON.id, b0 * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_) {
      return p_prepare_2_;
   }

   public float getHeadHeight() {
      return 0.45F;
   }
}
