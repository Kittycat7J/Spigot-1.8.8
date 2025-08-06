package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerHopper;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityMinecartContainer;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.IHopper;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.TileEntityHopper;
import net.minecraft.server.v1_8_R3.World;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {
   private boolean a = true;
   private int b = -1;
   private BlockPosition c = BlockPosition.ZERO;

   public EntityMinecartHopper(World p_i1221_1_) {
      super(p_i1221_1_);
   }

   public EntityMinecartHopper(World p_i1222_1_, double p_i1222_2_, double p_i1222_4_, double p_i1222_6_) {
      super(p_i1222_1_, p_i1222_2_, p_i1222_4_, p_i1222_6_);
   }

   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.HOPPER;
   }

   public IBlockData u() {
      return Blocks.HOPPER.getBlockData();
   }

   public int w() {
      return 1;
   }

   public int getSize() {
      return 5;
   }

   public boolean e(EntityHuman p_e_1_) {
      if(!this.world.isClientSide) {
         p_e_1_.openContainer(this);
      }

      return true;
   }

   public void a(int p_a_1_, int p_a_2_, int p_a_3_, boolean p_a_4_) {
      boolean flag = !p_a_4_;
      if(flag != this.y()) {
         this.i(flag);
      }

   }

   public boolean y() {
      return this.a;
   }

   public void i(boolean p_i_1_) {
      this.a = p_i_1_;
   }

   public World getWorld() {
      return this.world;
   }

   public double A() {
      return this.locX;
   }

   public double B() {
      return this.locY + 0.5D;
   }

   public double C() {
      return this.locZ;
   }

   public void t_() {
      super.t_();
      if(!this.world.isClientSide && this.isAlive() && this.y()) {
         BlockPosition blockposition = new BlockPosition(this);
         if(blockposition.equals(this.c)) {
            --this.b;
         } else {
            this.m(0);
         }

         if(!this.E()) {
            this.m(0);
            if(this.D()) {
               this.m(4);
               this.update();
            }
         }
      }

   }

   public boolean D() {
      if(TileEntityHopper.a((IHopper)this)) {
         return true;
      } else {
         List list = this.world.a(EntityItem.class, this.getBoundingBox().grow(0.25D, 0.0D, 0.25D), IEntitySelector.a);
         if(list.size() > 0) {
            TileEntityHopper.a((IInventory)this, (EntityItem)((EntityItem)list.get(0)));
         }

         return false;
      }
   }

   public void a(DamageSource p_a_1_) {
      super.a(p_a_1_);
      if(this.world.getGameRules().getBoolean("doEntityDrops")) {
         this.a(Item.getItemOf(Blocks.HOPPER), 1, 0.0F);
      }

   }

   protected void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setInt("TransferCooldown", this.b);
   }

   protected void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.b = p_a_1_.getInt("TransferCooldown");
   }

   public void m(int p_m_1_) {
      this.b = p_m_1_;
   }

   public boolean E() {
      return this.b > 0;
   }

   public String getContainerName() {
      return "minecraft:hopper";
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerHopper(p_createContainer_1_, this, p_createContainer_2_);
   }
}
