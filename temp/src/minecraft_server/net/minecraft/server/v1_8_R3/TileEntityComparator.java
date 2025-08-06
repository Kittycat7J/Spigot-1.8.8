package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;

public class TileEntityComparator extends TileEntity {
   private int a;

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setInt("OutputSignal", this.a);
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.a = p_a_1_.getInt("OutputSignal");
   }

   public int b() {
      return this.a;
   }

   public void a(int p_a_1_) {
      this.a = p_a_1_;
   }
}
