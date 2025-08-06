package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityAgeable;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IAnimal;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimal {
   protected Block bn = Blocks.GRASS;
   private int bm;
   private EntityHuman bo;

   public EntityAnimal(World p_i266_1_) {
      super(p_i266_1_);
   }

   protected void E() {
      if(this.getAge() != 0) {
         this.bm = 0;
      }

      super.E();
   }

   public void m() {
      super.m();
      if(this.getAge() != 0) {
         this.bm = 0;
      }

      if(this.bm > 0) {
         --this.bm;
         if(this.bm % 10 == 0) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(EnumParticle.HEART, this.locX + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, this.locY + 0.5D + (double)(this.random.nextFloat() * this.length), this.locZ + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
         }
      }

   }

   public float a(BlockPosition p_a_1_) {
      return this.world.getType(p_a_1_.down()).getBlock() == Blocks.GRASS?10.0F:this.world.o(p_a_1_) - 0.5F;
   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setInt("InLove", this.bm);
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.bm = p_a_1_.getInt("InLove");
   }

   public boolean bR() {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.getBoundingBox().b);
      int k = MathHelper.floor(this.locZ);
      BlockPosition blockposition = new BlockPosition(i, j, k);
      return this.world.getType(blockposition.down()).getBlock() == this.bn && this.world.k(blockposition) > 8 && super.bR();
   }

   public int w() {
      return 120;
   }

   protected boolean isTypeNotPersistent() {
      return false;
   }

   protected int getExpValue(EntityHuman p_getExpValue_1_) {
      return 1 + this.world.random.nextInt(3);
   }

   public boolean d(ItemStack p_d_1_) {
      return p_d_1_ == null?false:p_d_1_.getItem() == Items.WHEAT;
   }

   public boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      if(itemstack != null) {
         if(this.d(itemstack) && this.getAge() == 0 && this.bm <= 0) {
            this.a(p_a_1_, itemstack);
            this.c(p_a_1_);
            return true;
         }

         if(this.isBaby() && this.d(itemstack)) {
            this.a(p_a_1_, itemstack);
            this.setAge((int)((float)(-this.getAge() / 20) * 0.1F), true);
            return true;
         }
      }

      return super.a(p_a_1_);
   }

   protected void a(EntityHuman p_a_1_, ItemStack p_a_2_) {
      if(!p_a_1_.abilities.canInstantlyBuild) {
         --p_a_2_.count;
         if(p_a_2_.count <= 0) {
            p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, (ItemStack)null);
         }
      }

   }

   public void c(EntityHuman p_c_1_) {
      this.bm = 600;
      this.bo = p_c_1_;
      this.world.broadcastEntityEffect(this, (byte)18);
   }

   public EntityHuman cq() {
      return this.bo;
   }

   public boolean isInLove() {
      return this.bm > 0;
   }

   public void cs() {
      this.bm = 0;
   }

   public boolean mate(EntityAnimal p_mate_1_) {
      return p_mate_1_ == this?false:(p_mate_1_.getClass() != this.getClass()?false:this.isInLove() && p_mate_1_.isInLove());
   }
}
