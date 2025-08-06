package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirectional;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockRedstoneWire;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public abstract class BlockDiodeAbstract extends BlockDirectional {
   protected final boolean N;

   protected BlockDiodeAbstract(boolean p_i232_1_) {
      super(Material.ORIENTABLE);
      this.N = p_i232_1_;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   public boolean d() {
      return false;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      return World.a((IBlockAccess)p_canPlace_1_, (BlockPosition)p_canPlace_2_.down())?super.canPlace(p_canPlace_1_, p_canPlace_2_):false;
   }

   public boolean e(World p_e_1_, BlockPosition p_e_2_) {
      return World.a((IBlockAccess)p_e_1_, (BlockPosition)p_e_2_.down());
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_) {
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(!this.b(p_b_1_, p_b_2_, p_b_3_)) {
         boolean flag = this.e(p_b_1_, p_b_2_, p_b_3_);
         if(this.N && !flag) {
            if(CraftEventFactory.callRedstoneChange(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), 15, 0).getNewCurrent() != 0) {
               return;
            }

            p_b_1_.setTypeAndData(p_b_2_, this.k(p_b_3_), 2);
         } else if(!this.N) {
            if(CraftEventFactory.callRedstoneChange(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), 0, 15).getNewCurrent() != 15) {
               return;
            }

            p_b_1_.setTypeAndData(p_b_2_, this.e(p_b_3_), 2);
            if(!flag) {
               p_b_1_.a(p_b_2_, this.e(p_b_3_).getBlock(), this.m(p_b_3_), -1);
            }
         }
      }

   }

   protected boolean l(IBlockData p_l_1_) {
      return this.N;
   }

   public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_) {
      return this.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return !this.l(p_a_3_)?0:(p_a_3_.get(FACING) == p_a_4_?this.a(p_a_1_, p_a_2_, p_a_3_):0);
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(this.e(p_doPhysics_1_, p_doPhysics_2_)) {
         this.g(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
      } else {
         this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
         p_doPhysics_1_.setAir(p_doPhysics_2_);

         for(EnumDirection enumdirection : EnumDirection.values()) {
            p_doPhysics_1_.applyPhysics(p_doPhysics_2_.shift(enumdirection), this);
         }
      }

   }

   protected void g(World p_g_1_, BlockPosition p_g_2_, IBlockData p_g_3_) {
      if(!this.b(p_g_1_, p_g_2_, p_g_3_)) {
         boolean flag = this.e(p_g_1_, p_g_2_, p_g_3_);
         if((this.N && !flag || !this.N && flag) && !p_g_1_.a((BlockPosition)p_g_2_, (Block)this)) {
            byte b0 = -1;
            if(this.i(p_g_1_, p_g_2_, p_g_3_)) {
               b0 = -3;
            } else if(this.N) {
               b0 = -2;
            }

            p_g_1_.a(p_g_2_, this, this.d(p_g_3_), b0);
         }
      }

   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_) {
      return false;
   }

   protected boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_) {
      return this.f(p_e_1_, p_e_2_, p_e_3_) > 0;
   }

   protected int f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_) {
      EnumDirection enumdirection = (EnumDirection)p_f_3_.get(FACING);
      BlockPosition blockposition = p_f_2_.shift(enumdirection);
      int i = p_f_1_.getBlockFacePower(blockposition, enumdirection);
      if(i >= 15) {
         return i;
      } else {
         IBlockData iblockdata = p_f_1_.getType(blockposition);
         return Math.max(i, iblockdata.getBlock() == Blocks.REDSTONE_WIRE?((Integer)iblockdata.get(BlockRedstoneWire.POWER)).intValue():0);
      }
   }

   protected int c(IBlockAccess p_c_1_, BlockPosition p_c_2_, IBlockData p_c_3_) {
      EnumDirection enumdirection = (EnumDirection)p_c_3_.get(FACING);
      EnumDirection enumdirection1 = enumdirection.e();
      EnumDirection enumdirection2 = enumdirection.f();
      return Math.max(this.c(p_c_1_, p_c_2_.shift(enumdirection1), enumdirection1), this.c(p_c_1_, p_c_2_.shift(enumdirection2), enumdirection2));
   }

   protected int c(IBlockAccess p_c_1_, BlockPosition p_c_2_, EnumDirection p_c_3_) {
      IBlockData iblockdata = p_c_1_.getType(p_c_2_);
      Block block = iblockdata.getBlock();
      return this.c(block)?(block == Blocks.REDSTONE_WIRE?((Integer)iblockdata.get(BlockRedstoneWire.POWER)).intValue():p_c_1_.getBlockPower(p_c_2_, p_c_3_)):0;
   }

   public boolean isPowerSource() {
      return true;
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection().opposite());
   }

   public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_) {
      if(this.e(p_postPlace_1_, p_postPlace_2_, p_postPlace_3_)) {
         p_postPlace_1_.a((BlockPosition)p_postPlace_2_, (Block)this, 1);
      }

   }

   public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_) {
      this.h(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
   }

   protected void h(World p_h_1_, BlockPosition p_h_2_, IBlockData p_h_3_) {
      EnumDirection enumdirection = (EnumDirection)p_h_3_.get(FACING);
      BlockPosition blockposition = p_h_2_.shift(enumdirection.opposite());
      p_h_1_.d(blockposition, this);
      p_h_1_.a((BlockPosition)blockposition, (Block)this, (EnumDirection)enumdirection);
   }

   public void postBreak(World p_postBreak_1_, BlockPosition p_postBreak_2_, IBlockData p_postBreak_3_) {
      if(this.N) {
         for(EnumDirection enumdirection : EnumDirection.values()) {
            p_postBreak_1_.applyPhysics(p_postBreak_2_.shift(enumdirection), this);
         }
      }

      super.postBreak(p_postBreak_1_, p_postBreak_2_, p_postBreak_3_);
   }

   public boolean c() {
      return false;
   }

   protected boolean c(Block p_c_1_) {
      return p_c_1_.isPowerSource();
   }

   protected int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return 15;
   }

   public static boolean d(Block p_d_0_) {
      return Blocks.UNPOWERED_REPEATER.e(p_d_0_) || Blocks.UNPOWERED_COMPARATOR.e(p_d_0_);
   }

   public boolean e(Block p_e_1_) {
      return p_e_1_ == this.e(this.getBlockData()).getBlock() || p_e_1_ == this.k(this.getBlockData()).getBlock();
   }

   public boolean i(World p_i_1_, BlockPosition p_i_2_, IBlockData p_i_3_) {
      EnumDirection enumdirection = ((EnumDirection)p_i_3_.get(FACING)).opposite();
      BlockPosition blockposition = p_i_2_.shift(enumdirection);
      return d(p_i_1_.getType(blockposition).getBlock())?p_i_1_.getType(blockposition).get(FACING) != enumdirection:false;
   }

   protected int m(IBlockData p_m_1_) {
      return this.d(p_m_1_);
   }

   protected abstract int d(IBlockData var1);

   protected abstract IBlockData e(IBlockData var1);

   protected abstract IBlockData k(IBlockData var1);

   public boolean b(Block p_b_1_) {
      return this.e(p_b_1_);
   }
}
