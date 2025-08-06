package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PersistentBase;

public class PersistentStructure extends PersistentBase {
   private NBTTagCompound b = new NBTTagCompound();

   public PersistentStructure(String p_i795_1_) {
      super(p_i795_1_);
   }

   public void a(NBTTagCompound p_a_1_) {
      this.b = p_a_1_.getCompound("Features");
   }

   public void b(NBTTagCompound p_b_1_) {
      p_b_1_.set("Features", this.b);
   }

   public void a(NBTTagCompound p_a_1_, int p_a_2_, int p_a_3_) {
      this.b.set(b(p_a_2_, p_a_3_), p_a_1_);
   }

   public static String b(int p_b_0_, int p_b_1_) {
      return "[" + p_b_0_ + "," + p_b_1_ + "]";
   }

   public NBTTagCompound a() {
      return this.b;
   }
}
