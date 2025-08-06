package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockChest;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerChest;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.InventoryLargeChest;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityContainer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.HumanEntity;

public class TileEntityChest extends TileEntityContainer implements IUpdatePlayerListBox, IInventory {
   private ItemStack[] items = new ItemStack[27];
   public boolean a;
   public TileEntityChest f;
   public TileEntityChest g;
   public TileEntityChest h;
   public TileEntityChest i;
   public float j;
   public float k;
   public int l;
   private int n;
   private int o = -1;
   private String p;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   public ItemStack[] getContents() {
      return this.items;
   }

   public void onOpen(CraftHumanEntity p_onOpen_1_) {
      this.transaction.add(p_onOpen_1_);
   }

   public void onClose(CraftHumanEntity p_onClose_1_) {
      this.transaction.remove(p_onClose_1_);
   }

   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   public void setMaxStackSize(int p_setMaxStackSize_1_) {
      this.maxStack = p_setMaxStackSize_1_;
   }

   public int getSize() {
      return 27;
   }

   public ItemStack getItem(int p_getItem_1_) {
      return this.items[p_getItem_1_];
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      if(this.items[p_splitStack_1_] != null) {
         if(this.items[p_splitStack_1_].count <= p_splitStack_2_) {
            ItemStack itemstack1 = this.items[p_splitStack_1_];
            this.items[p_splitStack_1_] = null;
            this.update();
            return itemstack1;
         } else {
            ItemStack itemstack = this.items[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);
            if(this.items[p_splitStack_1_].count == 0) {
               this.items[p_splitStack_1_] = null;
            }

            this.update();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_) {
      if(this.items[p_splitWithoutUpdate_1_] != null) {
         ItemStack itemstack = this.items[p_splitWithoutUpdate_1_];
         this.items[p_splitWithoutUpdate_1_] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      this.items[p_setItem_1_] = p_setItem_2_;
      if(p_setItem_2_ != null && p_setItem_2_.count > this.getMaxStackSize()) {
         p_setItem_2_.count = this.getMaxStackSize();
      }

      this.update();
   }

   public String getName() {
      return this.hasCustomName()?this.p:"container.chest";
   }

   public boolean hasCustomName() {
      return this.p != null && this.p.length() > 0;
   }

   public void a(String p_a_1_) {
      this.p = p_a_1_;
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      NBTTagList nbttaglist = p_a_1_.getList("Items", 10);
      this.items = new ItemStack[this.getSize()];
      if(p_a_1_.hasKeyOfType("CustomName", 8)) {
         this.p = p_a_1_.getString("CustomName");
      }

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.get(i);
         int j = nbttagcompound.getByte("Slot") & 255;
         if(j >= 0 && j < this.items.length) {
            this.items[j] = ItemStack.createStack(nbttagcompound);
         }
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte)i);
            this.items[i].save(nbttagcompound);
            nbttaglist.add(nbttagcompound);
         }
      }

      p_b_1_.set("Items", nbttaglist);
      if(this.hasCustomName()) {
         p_b_1_.setString("CustomName", this.p);
      }

   }

   public int getMaxStackSize() {
      return this.maxStack;
   }

   public boolean a(EntityHuman p_a_1_) {
      return this.world == null?true:(this.world.getTileEntity(this.position) != this?false:p_a_1_.e((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D);
   }

   public void E() {
      super.E();
      this.a = false;
   }

   private void a(TileEntityChest p_a_1_, EnumDirection p_a_2_) {
      if(p_a_1_.x()) {
         this.a = false;
      } else if(this.a) {
         switch(TileEntityChest.SyntheticClass_1.a[p_a_2_.ordinal()]) {
         case 1:
            if(this.f != p_a_1_) {
               this.a = false;
            }
            break;
         case 2:
            if(this.i != p_a_1_) {
               this.a = false;
            }
            break;
         case 3:
            if(this.g != p_a_1_) {
               this.a = false;
            }
            break;
         case 4:
            if(this.h != p_a_1_) {
               this.a = false;
            }
         }
      }

   }

   public void m() {
      if(!this.a) {
         this.a = true;
         this.h = this.a(EnumDirection.WEST);
         this.g = this.a(EnumDirection.EAST);
         this.f = this.a(EnumDirection.NORTH);
         this.i = this.a(EnumDirection.SOUTH);
      }

   }

   protected TileEntityChest a(EnumDirection p_a_1_) {
      BlockPosition blockposition = this.position.shift(p_a_1_);
      if(this.b(blockposition)) {
         TileEntity tileentity = this.world.getTileEntity(blockposition);
         if(tileentity instanceof TileEntityChest) {
            TileEntityChest tileentitychest = (TileEntityChest)tileentity;
            tileentitychest.a(this, p_a_1_.opposite());
            return tileentitychest;
         }
      }

      return null;
   }

   private boolean b(BlockPosition p_b_1_) {
      if(this.world == null) {
         return false;
      } else {
         Block block = this.world.getType(p_b_1_).getBlock();
         return block instanceof BlockChest && ((BlockChest)block).b == this.n();
      }
   }

   public void c() {
      this.m();
      int i = this.position.getX();
      int j = this.position.getY();
      int k = this.position.getZ();
      ++this.n;
      if(!this.world.isClientSide && this.l != 0 && (this.n + i + j + k) % 200 == 0) {
         this.l = 0;
         float f = 5.0F;

         for(EntityHuman entityhuman : this.world.a(EntityHuman.class, new AxisAlignedBB((double)((float)i - f), (double)((float)j - f), (double)((float)k - f), (double)((float)(i + 1) + f), (double)((float)(j + 1) + f), (double)((float)(k + 1) + f)))) {
            if(entityhuman.activeContainer instanceof ContainerChest) {
               IInventory iinventory = ((ContainerChest)entityhuman.activeContainer).e();
               if(iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).a((IInventory)this)) {
                  ++this.l;
               }
            }
         }
      }

      this.k = this.j;
      float f1 = 0.1F;
      if(this.l > 0 && this.j == 0.0F && this.f == null && this.h == null) {
         double d0 = (double)i + 0.5D;
         double d1 = (double)k + 0.5D;
         if(this.i != null) {
            d1 += 0.5D;
         }

         if(this.g != null) {
            d0 += 0.5D;
         }

         this.world.makeSound(d0, (double)j + 0.5D, d1, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
      }

      if(this.l == 0 && this.j > 0.0F || this.l > 0 && this.j < 1.0F) {
         float f2 = this.j;
         if(this.l > 0) {
            this.j += f1;
         } else {
            this.j -= f1;
         }

         if(this.j > 1.0F) {
            this.j = 1.0F;
         }

         float f3 = 0.5F;
         if(this.j < f3 && f2 >= f3 && this.f == null && this.h == null) {
            double d3 = (double)i + 0.5D;
            double d2 = (double)k + 0.5D;
            if(this.i != null) {
               d2 += 0.5D;
            }

            if(this.g != null) {
               d3 += 0.5D;
            }

            this.world.makeSound(d3, (double)j + 0.5D, d2, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
         }

         if(this.j < 0.0F) {
            this.j = 0.0F;
         }
      }

   }

   public boolean c(int p_c_1_, int p_c_2_) {
      if(p_c_1_ == 1) {
         this.l = p_c_2_;
         return true;
      } else {
         return super.c(p_c_1_, p_c_2_);
      }
   }

   public void startOpen(EntityHuman p_startOpen_1_) {
      if(!p_startOpen_1_.isSpectator()) {
         if(this.l < 0) {
            this.l = 0;
         }

         int i = Math.max(0, Math.min(15, this.l));
         ++this.l;
         if(this.world == null) {
            return;
         }

         this.world.playBlockAction(this.position, this.w(), 1, this.l);
         if(this.w() == Blocks.TRAPPED_CHEST) {
            int j = Math.max(0, Math.min(15, this.l));
            if(i != j) {
               CraftEventFactory.callRedstoneChange(this.world, this.position.getX(), this.position.getY(), this.position.getZ(), i, j);
            }
         }

         this.world.applyPhysics(this.position, this.w());
         this.world.applyPhysics(this.position.down(), this.w());
      }

   }

   public void closeContainer(EntityHuman p_closeContainer_1_) {
      if(!p_closeContainer_1_.isSpectator() && this.w() instanceof BlockChest) {
         int i = Math.max(0, Math.min(15, this.l));
         --this.l;
         if(this.world == null) {
            return;
         }

         this.world.playBlockAction(this.position, this.w(), 1, this.l);
         if(this.w() == Blocks.TRAPPED_CHEST) {
            int j = Math.max(0, Math.min(15, this.l));
            if(i != j) {
               CraftEventFactory.callRedstoneChange(this.world, this.position.getX(), this.position.getY(), this.position.getZ(), i, j);
            }
         }

         this.world.applyPhysics(this.position, this.w());
         this.world.applyPhysics(this.position.down(), this.w());
      }

   }

   public boolean b(int p_b_1_, ItemStack p_b_2_) {
      return true;
   }

   public void y() {
      super.y();
      this.E();
      this.m();
   }

   public int n() {
      if(this.o == -1) {
         if(this.world == null || !(this.w() instanceof BlockChest)) {
            return 0;
         }

         this.o = ((BlockChest)this.w()).b;
      }

      return this.o;
   }

   public String getContainerName() {
      return "minecraft:chest";
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerChest(p_createContainer_1_, this, p_createContainer_2_);
   }

   public int getProperty(int p_getProperty_1_) {
      return 0;
   }

   public void b(int p_b_1_, int p_b_2_) {
   }

   public int g() {
      return 0;
   }

   public void l() {
      for(int i = 0; i < this.items.length; ++i) {
         this.items[i] = null;
      }

   }

   public boolean F() {
      return true;
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[EnumDirection.values().length];

      static {
         try {
            a[EnumDirection.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EnumDirection.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[EnumDirection.EAST.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EnumDirection.WEST.ordinal()] = 4;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}
