package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AttributeMapBase;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MobEffectList;

public class MobEffectAbsorption extends MobEffectList {
   protected MobEffectAbsorption(int p_i1136_1_, MinecraftKey p_i1136_2_, boolean p_i1136_3_, int p_i1136_4_) {
      super(p_i1136_1_, p_i1136_2_, p_i1136_3_, p_i1136_4_);
   }

   public void a(EntityLiving p_a_1_, AttributeMapBase p_a_2_, int p_a_3_) {
      p_a_1_.setAbsorptionHearts(p_a_1_.getAbsorptionHearts() - (float)(4 * (p_a_3_ + 1)));
      super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public void b(EntityLiving p_b_1_, AttributeMapBase p_b_2_, int p_b_3_) {
      p_b_1_.setAbsorptionHearts(p_b_1_.getAbsorptionHearts() + (float)(4 * (p_b_3_ + 1)));
      super.b(p_b_1_, p_b_2_, p_b_3_);
   }
}
