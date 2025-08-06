package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Vector3f;
import net.minecraft.server.v1_8_R3.World;

public class ItemArmorStand extends Item {
   public ItemArmorStand() {
      this.a(CreativeModeTab.c);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(p_interactWith_5_ == EnumDirection.DOWN) {
         return false;
      } else {
         boolean flag = p_interactWith_3_.getType(p_interactWith_4_).getBlock().a(p_interactWith_3_, p_interactWith_4_);
         BlockPosition blockposition = flag?p_interactWith_4_:p_interactWith_4_.shift(p_interactWith_5_);
         if(!p_interactWith_2_.a(blockposition, p_interactWith_5_, p_interactWith_1_)) {
            return false;
         } else {
            BlockPosition blockposition1 = blockposition.up();
            boolean flag1 = !p_interactWith_3_.isEmpty(blockposition) && !p_interactWith_3_.getType(blockposition).getBlock().a(p_interactWith_3_, blockposition);
            flag1 = flag1 | (!p_interactWith_3_.isEmpty(blockposition1) && !p_interactWith_3_.getType(blockposition1).getBlock().a(p_interactWith_3_, blockposition1));
            if(flag1) {
               return false;
            } else {
               double d0 = (double)blockposition.getX();
               double d1 = (double)blockposition.getY();
               double d2 = (double)blockposition.getZ();
               List list = p_interactWith_3_.getEntities((Entity)null, AxisAlignedBB.a(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
               if(list.size() > 0) {
                  return false;
               } else {
                  if(!p_interactWith_3_.isClientSide) {
                     p_interactWith_3_.setAir(blockposition);
                     p_interactWith_3_.setAir(blockposition1);
                     EntityArmorStand entityarmorstand = new EntityArmorStand(p_interactWith_3_, d0 + 0.5D, d1, d2 + 0.5D);
                     float f = (float)MathHelper.d((MathHelper.g(p_interactWith_2_.yaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                     entityarmorstand.setPositionRotation(d0 + 0.5D, d1, d2 + 0.5D, f, 0.0F);
                     this.a(entityarmorstand, p_interactWith_3_.random);
                     NBTTagCompound nbttagcompound = p_interactWith_1_.getTag();
                     if(nbttagcompound != null && nbttagcompound.hasKeyOfType("EntityTag", 10)) {
                        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                        entityarmorstand.d(nbttagcompound1);
                        nbttagcompound1.a(nbttagcompound.getCompound("EntityTag"));
                        entityarmorstand.f(nbttagcompound1);
                     }

                     p_interactWith_3_.addEntity(entityarmorstand);
                  }

                  --p_interactWith_1_.count;
                  return true;
               }
            }
         }
      }
   }

   private void a(EntityArmorStand p_a_1_, Random p_a_2_) {
      Vector3f vector3f = p_a_1_.t();
      float f = p_a_2_.nextFloat() * 5.0F;
      float f1 = p_a_2_.nextFloat() * 20.0F - 10.0F;
      Vector3f vector3f1 = new Vector3f(vector3f.getX() + f, vector3f.getY() + f1, vector3f.getZ());
      p_a_1_.setHeadPose(vector3f1);
      vector3f = p_a_1_.u();
      f = p_a_2_.nextFloat() * 10.0F - 5.0F;
      vector3f1 = new Vector3f(vector3f.getX(), vector3f.getY() + f, vector3f.getZ());
      p_a_1_.setBodyPose(vector3f1);
   }
}
