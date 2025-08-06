package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MobEffectList;

public class InstantMobEffect extends MobEffectList {
   public InstantMobEffect(int p_i1139_1_, MinecraftKey p_i1139_2_, boolean p_i1139_3_, int p_i1139_4_) {
      super(p_i1139_1_, p_i1139_2_, p_i1139_3_, p_i1139_4_);
   }

   public boolean isInstant() {
      return true;
   }

   public boolean a(int p_a_1_, int p_a_2_) {
      return p_a_1_ >= 1;
   }
}
