package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.TileEntity;

public class TileEntityEnderChest extends TileEntity implements IUpdatePlayerListBox {
   public float a;
   public float f;
   public int g;
   private int h;

   public void c() {
      if(++this.h % 20 * 4 == 0) {
         this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
      }

      this.f = this.a;
      int i = this.position.getX();
      int j = this.position.getY();
      int k = this.position.getZ();
      float fx = 0.1F;
      if(this.g > 0 && this.a == 0.0F) {
         double d0 = (double)i + 0.5D;
         double d1 = (double)k + 0.5D;
         this.world.makeSound(d0, (double)j + 0.5D, d1, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
      }

      if(this.g == 0 && this.a > 0.0F || this.g > 0 && this.a < 1.0F) {
         float f1 = this.a;
         if(this.g > 0) {
            this.a += fx;
         } else {
            this.a -= fx;
         }

         if(this.a > 1.0F) {
            this.a = 1.0F;
         }

         float f2 = 0.5F;
         if(this.a < f2 && f1 >= f2) {
            double d3 = (double)i + 0.5D;
            double d2 = (double)k + 0.5D;
            this.world.makeSound(d3, (double)j + 0.5D, d2, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
         }

         if(this.a < 0.0F) {
            this.a = 0.0F;
         }
      }

   }

   public boolean c(int p_c_1_, int p_c_2_) {
      if(p_c_1_ == 1) {
         this.g = p_c_2_;
         return true;
      } else {
         return super.c(p_c_1_, p_c_2_);
      }
   }

   public void y() {
      this.E();
      super.y();
   }

   public void b() {
      ++this.g;
      this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
   }

   public void d() {
      --this.g;
      this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
   }

   public boolean a(EntityHuman p_a_1_) {
      return this.world.getTileEntity(this.position) != this?false:p_a_1_.e((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D;
   }
}
