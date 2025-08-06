package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IWorldBorderListener;
import net.minecraft.server.v1_8_R3.SecondaryWorldServer;
import net.minecraft.server.v1_8_R3.WorldBorder;

class SecondaryWorldServer$1 implements IWorldBorderListener {
   SecondaryWorldServer$1(SecondaryWorldServer p_i1075_1_) {
      this.a = p_i1075_1_;
   }

   public void a(WorldBorder p_a_1_, double p_a_2_) {
      this.a.getWorldBorder().setSize(p_a_2_);
   }

   public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_, long p_a_6_) {
      this.a.getWorldBorder().transitionSizeBetween(p_a_2_, p_a_4_, p_a_6_);
   }

   public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_) {
      this.a.getWorldBorder().setCenter(p_a_2_, p_a_4_);
   }

   public void a(WorldBorder p_a_1_, int p_a_2_) {
      this.a.getWorldBorder().setWarningTime(p_a_2_);
   }

   public void b(WorldBorder p_b_1_, int p_b_2_) {
      this.a.getWorldBorder().setWarningDistance(p_b_2_);
   }

   public void b(WorldBorder p_b_1_, double p_b_2_) {
      this.a.getWorldBorder().setDamageAmount(p_b_2_);
   }

   public void c(WorldBorder p_c_1_, double p_c_2_) {
      this.a.getWorldBorder().setDamageBuffer(p_c_2_);
   }
}
