package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.InventorySubcontainer;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PathfinderGoalInteract;

public class PathfinderGoalInteractVillagers extends PathfinderGoalInteract {
   private int e;
   private EntityVillager f;

   public PathfinderGoalInteractVillagers(EntityVillager p_i1196_1_) {
      super(p_i1196_1_, EntityVillager.class, 3.0F, 0.02F);
      this.f = p_i1196_1_;
   }

   public void c() {
      super.c();
      if(this.f.cs() && this.b instanceof EntityVillager && ((EntityVillager)this.b).ct()) {
         this.e = 10;
      } else {
         this.e = 0;
      }

   }

   public void e() {
      super.e();
      if(this.e > 0) {
         --this.e;
         if(this.e == 0) {
            InventorySubcontainer inventorysubcontainer = this.f.cq();

            for(int i = 0; i < inventorysubcontainer.getSize(); ++i) {
               ItemStack itemstack = inventorysubcontainer.getItem(i);
               ItemStack itemstack1 = null;
               if(itemstack != null) {
                  Item item = itemstack.getItem();
                  if((item == Items.BREAD || item == Items.POTATO || item == Items.CARROT) && itemstack.count > 3) {
                     int l = itemstack.count / 2;
                     itemstack.count -= l;
                     itemstack1 = new ItemStack(item, l, itemstack.getData());
                  } else if(item == Items.WHEAT && itemstack.count > 5) {
                     int j = itemstack.count / 2 / 3 * 3;
                     int k = j / 3;
                     itemstack.count -= j;
                     itemstack1 = new ItemStack(Items.BREAD, k, 0);
                  }

                  if(itemstack.count <= 0) {
                     inventorysubcontainer.setItem(i, (ItemStack)null);
                  }
               }

               if(itemstack1 != null) {
                  double d0 = this.f.locY - 0.30000001192092896D + (double)this.f.getHeadHeight();
                  EntityItem entityitem = new EntityItem(this.f.world, this.f.locX, d0, this.f.locZ, itemstack1);
                  float fx = 0.3F;
                  float f1 = this.f.aK;
                  float f2 = this.f.pitch;
                  entityitem.motX = (double)(-MathHelper.sin(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F) * fx);
                  entityitem.motZ = (double)(MathHelper.cos(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F) * fx);
                  entityitem.motY = (double)(-MathHelper.sin(f2 / 180.0F * 3.1415927F) * fx + 0.1F);
                  entityitem.p();
                  this.f.world.addEntity(entityitem);
                  break;
               }
            }
         }
      }

   }
}
