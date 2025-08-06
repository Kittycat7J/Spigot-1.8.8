package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;

public abstract class BlockButtonAbstract extends Block {
   public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
   private final boolean N;

   protected BlockButtonAbstract(boolean p_i265_1_) {
      super(Material.ORIENTABLE);
      this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(POWERED, Boolean.valueOf(false)));
      this.a(true);
      this.a(CreativeModeTab.d);
      this.N = p_i265_1_;
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return null;
   }

   public int a(World p_a_1_) {
      return this.N?30:20;
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_) {
      return a(p_canPlace_1_, p_canPlace_2_, p_canPlace_3_.opposite());
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         if(a(p_canPlace_1_, p_canPlace_2_, enumdirection)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean a(World p_a_0_, BlockPosition p_a_1_, EnumDirection p_a_2_) {
      BlockPosition blockposition = p_a_1_.shift(p_a_2_);
      return p_a_2_ == EnumDirection.DOWN?World.a((IBlockAccess)p_a_0_, (BlockPosition)blockposition):p_a_0_.getType(blockposition).getBlock().isOccluding();
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return a(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_.opposite())?this.getBlockData().set(FACING, p_getPlacedState_3_).set(POWERED, Boolean.valueOf(false)):this.getBlockData().set(FACING, EnumDirection.DOWN).set(POWERED, Boolean.valueOf(false));
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_) && !a(p_doPhysics_1_, p_doPhysics_2_, ((EnumDirection)p_doPhysics_3_.get(FACING)).opposite())) {
         this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
         p_doPhysics_1_.setAir(p_doPhysics_2_);
      }

   }

   private boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_) {
      if(this.canPlace(p_e_1_, p_e_2_)) {
         return true;
      } else {
         this.b(p_e_1_, p_e_2_, p_e_3_, 0);
         p_e_1_.setAir(p_e_2_);
         return false;
      }
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      this.d(p_updateShape_1_.getType(p_updateShape_2_));
   }

   private void d(IBlockData p_d_1_) {
      EnumDirection enumdirection = (EnumDirection)p_d_1_.get(FACING);
      boolean flag = ((Boolean)p_d_1_.get(POWERED)).booleanValue();
      float f = (float)(flag?1:2) / 16.0F;
      switch(BlockButtonAbstract.SyntheticClass_1.a[enumdirection.ordinal()]) {
      case 1:
         this.a(0.0F, 0.375F, 0.3125F, f, 0.625F, 0.6875F);
         break;
      case 2:
         this.a(1.0F - f, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
         break;
      case 3:
         this.a(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f);
         break;
      case 4:
         this.a(0.3125F, 0.375F, 1.0F - f, 0.6875F, 0.625F, 1.0F);
         break;
      case 5:
         this.a(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f, 0.625F);
         break;
      case 6:
         this.a(0.3125F, 1.0F - f, 0.375F, 0.6875F, 1.0F, 0.625F);
      }

   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(((Boolean)p_interact_3_.get(POWERED)).booleanValue()) {
         return true;
      } else {
         boolean flag = ((Boolean)p_interact_3_.get(POWERED)).booleanValue();
         org.bukkit.block.Block block = p_interact_1_.getWorld().getBlockAt(p_interact_2_.getX(), p_interact_2_.getY(), p_interact_2_.getZ());
         int i = flag?15:0;
         int j = !flag?15:0;
         BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, i, j);
         p_interact_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
         if(blockredstoneevent.getNewCurrent() > 0 != !flag) {
            return true;
         } else {
            p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_.set(POWERED, Boolean.valueOf(true)), 3);
            p_interact_1_.b(p_interact_2_, p_interact_2_);
            p_interact_1_.makeSound((double)p_interact_2_.getX() + 0.5D, (double)p_interact_2_.getY() + 0.5D, (double)p_interact_2_.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
            this.c(p_interact_1_, p_interact_2_, (EnumDirection)p_interact_3_.get(FACING));
            p_interact_1_.a((BlockPosition)p_interact_2_, (Block)this, this.a(p_interact_1_));
            return true;
         }
      }
   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      if(((Boolean)p_remove_3_.get(POWERED)).booleanValue()) {
         this.c(p_remove_1_, p_remove_2_, (EnumDirection)p_remove_3_.get(FACING));
      }

      super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return ((Boolean)p_a_3_.get(POWERED)).booleanValue()?15:0;
   }

   public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_) {
      return !((Boolean)p_b_3_.get(POWERED)).booleanValue()?0:(p_b_3_.get(FACING) == p_b_4_?15:0);
   }

   public boolean isPowerSource() {
      return true;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_) {
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(!p_b_1_.isClientSide && ((Boolean)p_b_3_.get(POWERED)).booleanValue()) {
         if(this.N) {
            this.f(p_b_1_, p_b_2_, p_b_3_);
         } else {
            org.bukkit.block.Block block = p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
            BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, 15, 0);
            p_b_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
            if(blockredstoneevent.getNewCurrent() > 0) {
               return;
            }

            p_b_1_.setTypeUpdate(p_b_2_, p_b_3_.set(POWERED, Boolean.valueOf(false)));
            this.c(p_b_1_, p_b_2_, (EnumDirection)p_b_3_.get(FACING));
            p_b_1_.makeSound((double)p_b_2_.getX() + 0.5D, (double)p_b_2_.getY() + 0.5D, (double)p_b_2_.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
            p_b_1_.b(p_b_2_, p_b_2_);
         }
      }

   }

   public void j() {
      float f = 0.1875F;
      float f1 = 0.125F;
      float f2 = 0.125F;
      this.a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_) {
      if(!p_a_1_.isClientSide && this.N && !((Boolean)p_a_3_.get(POWERED)).booleanValue()) {
         this.f(p_a_1_, p_a_2_, p_a_3_);
      }

   }

   private void f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_) {
      this.d(p_f_3_);
      List list = p_f_1_.a(EntityArrow.class, new AxisAlignedBB((double)p_f_2_.getX() + this.minX, (double)p_f_2_.getY() + this.minY, (double)p_f_2_.getZ() + this.minZ, (double)p_f_2_.getX() + this.maxX, (double)p_f_2_.getY() + this.maxY, (double)p_f_2_.getZ() + this.maxZ));
      boolean flag = !list.isEmpty();
      boolean flag1 = ((Boolean)p_f_3_.get(POWERED)).booleanValue();
      if(flag1 != flag && flag) {
         org.bukkit.block.Block block = p_f_1_.getWorld().getBlockAt(p_f_2_.getX(), p_f_2_.getY(), p_f_2_.getZ());
         boolean flag2 = false;

         for(Object object : list) {
            if(object != null) {
               EntityInteractEvent entityinteractevent = new EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
               p_f_1_.getServer().getPluginManager().callEvent(entityinteractevent);
               if(!entityinteractevent.isCancelled()) {
                  flag2 = true;
                  break;
               }
            }
         }

         if(!flag2) {
            return;
         }
      }

      if(flag && !flag1) {
         org.bukkit.block.Block block1 = p_f_1_.getWorld().getBlockAt(p_f_2_.getX(), p_f_2_.getY(), p_f_2_.getZ());
         BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block1, 0, 15);
         p_f_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
         if(blockredstoneevent.getNewCurrent() <= 0) {
            return;
         }

         p_f_1_.setTypeUpdate(p_f_2_, p_f_3_.set(POWERED, Boolean.valueOf(true)));
         this.c(p_f_1_, p_f_2_, (EnumDirection)p_f_3_.get(FACING));
         p_f_1_.b(p_f_2_, p_f_2_);
         p_f_1_.makeSound((double)p_f_2_.getX() + 0.5D, (double)p_f_2_.getY() + 0.5D, (double)p_f_2_.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
      }

      if(!flag && flag1) {
         org.bukkit.block.Block block2 = p_f_1_.getWorld().getBlockAt(p_f_2_.getX(), p_f_2_.getY(), p_f_2_.getZ());
         BlockRedstoneEvent blockredstoneevent1 = new BlockRedstoneEvent(block2, 15, 0);
         p_f_1_.getServer().getPluginManager().callEvent(blockredstoneevent1);
         if(blockredstoneevent1.getNewCurrent() > 0) {
            return;
         }

         p_f_1_.setTypeUpdate(p_f_2_, p_f_3_.set(POWERED, Boolean.valueOf(false)));
         this.c(p_f_1_, p_f_2_, (EnumDirection)p_f_3_.get(FACING));
         p_f_1_.b(p_f_2_, p_f_2_);
         p_f_1_.makeSound((double)p_f_2_.getX() + 0.5D, (double)p_f_2_.getY() + 0.5D, (double)p_f_2_.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
      }

      if(flag) {
         p_f_1_.a((BlockPosition)p_f_2_, (Block)this, this.a(p_f_1_));
      }

   }

   private void c(World p_c_1_, BlockPosition p_c_2_, EnumDirection p_c_3_) {
      p_c_1_.applyPhysics(p_c_2_, this);
      p_c_1_.applyPhysics(p_c_2_.shift(p_c_3_.opposite()), this);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      EnumDirection enumdirection;
      switch(p_fromLegacyData_1_ & 7) {
      case 0:
         enumdirection = EnumDirection.DOWN;
         break;
      case 1:
         enumdirection = EnumDirection.EAST;
         break;
      case 2:
         enumdirection = EnumDirection.WEST;
         break;
      case 3:
         enumdirection = EnumDirection.SOUTH;
         break;
      case 4:
         enumdirection = EnumDirection.NORTH;
         break;
      case 5:
      default:
         enumdirection = EnumDirection.UP;
      }

      return this.getBlockData().set(FACING, enumdirection).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i;
      switch(BlockButtonAbstract.SyntheticClass_1.a[((EnumDirection)p_toLegacyData_1_.get(FACING)).ordinal()]) {
      case 1:
         i = 1;
         break;
      case 2:
         i = 2;
         break;
      case 3:
         i = 3;
         break;
      case 4:
         i = 4;
         break;
      case 5:
      default:
         i = 5;
         break;
      case 6:
         i = 0;
      }

      if(((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, POWERED});
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[EnumDirection.values().length];

      static {
         try {
            a[EnumDirection.EAST.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            a[EnumDirection.WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            a[EnumDirection.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EnumDirection.NORTH.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[EnumDirection.UP.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EnumDirection.DOWN.ordinal()] = 6;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}
