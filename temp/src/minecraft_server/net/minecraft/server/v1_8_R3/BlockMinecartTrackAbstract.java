package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public abstract class BlockMinecartTrackAbstract extends Block {
   protected final boolean a;

   public static boolean e(World p_e_0_, BlockPosition p_e_1_) {
      return d(p_e_0_.getType(p_e_1_));
   }

   public static boolean d(IBlockData p_d_0_) {
      Block block = p_d_0_.getBlock();
      return block == Blocks.RAIL || block == Blocks.GOLDEN_RAIL || block == Blocks.DETECTOR_RAIL || block == Blocks.ACTIVATOR_RAIL;
   }

   protected BlockMinecartTrackAbstract(boolean p_i350_1_) {
      super(Material.ORIENTABLE);
      this.a = p_i350_1_;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.a(CreativeModeTab.e);
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return null;
   }

   public boolean c() {
      return false;
   }

   public MovingObjectPosition a(World p_a_1_, BlockPosition p_a_2_, Vec3D p_a_3_, Vec3D p_a_4_) {
      this.updateShape(p_a_1_, p_a_2_);
      return super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      IBlockData iblockdata = p_updateShape_1_.getType(p_updateShape_2_);
      BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = iblockdata.getBlock() == this?(BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(this.n()):null;
      if(blockminecarttrackabstract$enumtrackposition != null && blockminecarttrackabstract$enumtrackposition.c()) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      }

   }

   public boolean d() {
      return false;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      return World.a((IBlockAccess)p_canPlace_1_, (BlockPosition)p_canPlace_2_.down());
   }

   public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_) {
      if(!p_onPlace_1_.isClientSide) {
         p_onPlace_3_ = this.a(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_, true);
         if(this.a) {
            this.doPhysics(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_, this);
         }
      }

   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!p_doPhysics_1_.isClientSide) {
         BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)p_doPhysics_3_.get(this.n());
         boolean flag = false;
         if(!World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.down())) {
            flag = true;
         }

         if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST && !World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.east())) {
            flag = true;
         } else if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST && !World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.west())) {
            flag = true;
         } else if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH && !World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.north())) {
            flag = true;
         } else if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH && !World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.south())) {
            flag = true;
         }

         if(flag && !p_doPhysics_1_.isEmpty(p_doPhysics_2_)) {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
         } else {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
         }
      }

   }

   protected void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Block p_b_4_) {
   }

   protected IBlockData a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_) {
      return p_a_1_.isClientSide?p_a_3_:(new BlockMinecartTrackAbstract.MinecartTrackLogic(p_a_1_, p_a_2_, p_a_3_)).a(p_a_1_.isBlockIndirectlyPowered(p_a_2_), p_a_4_).b();
   }

   public int k() {
      return 0;
   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
      if(((BlockMinecartTrackAbstract.EnumTrackPosition)p_remove_3_.get(this.n())).c()) {
         p_remove_1_.applyPhysics(p_remove_2_.up(), this);
      }

      if(this.a) {
         p_remove_1_.applyPhysics(p_remove_2_, this);
         p_remove_1_.applyPhysics(p_remove_2_.down(), this);
      }

   }

   public abstract IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> n();

   public static enum EnumTrackPosition implements INamable {
      NORTH_SOUTH(0, "north_south"),
      EAST_WEST(1, "east_west"),
      ASCENDING_EAST(2, "ascending_east"),
      ASCENDING_WEST(3, "ascending_west"),
      ASCENDING_NORTH(4, "ascending_north"),
      ASCENDING_SOUTH(5, "ascending_south"),
      SOUTH_EAST(6, "south_east"),
      SOUTH_WEST(7, "south_west"),
      NORTH_WEST(8, "north_west"),
      NORTH_EAST(9, "north_east");

      private static final BlockMinecartTrackAbstract.EnumTrackPosition[] k = new BlockMinecartTrackAbstract.EnumTrackPosition[values().length];
      private final int l;
      private final String m;

      static {
         for(BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition : values()) {
            k[blockminecarttrackabstract$enumtrackposition.a()] = blockminecarttrackabstract$enumtrackposition;
         }

      }

      private EnumTrackPosition(int p_i184_3_, String p_i184_4_) {
         this.l = p_i184_3_;
         this.m = p_i184_4_;
      }

      public int a() {
         return this.l;
      }

      public String toString() {
         return this.m;
      }

      public boolean c() {
         return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
      }

      public static BlockMinecartTrackAbstract.EnumTrackPosition a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= k.length) {
            p_a_0_ = 0;
         }

         return k[p_a_0_];
      }

      public String getName() {
         return this.m;
      }
   }

   public class MinecartTrackLogic {
      private final World b;
      private final BlockPosition c;
      private final BlockMinecartTrackAbstract d;
      private IBlockData e;
      private final boolean f;
      private final List<BlockPosition> g = Lists.<BlockPosition>newArrayList();

      public MinecartTrackLogic(World p_i39_2_, BlockPosition p_i39_3_, IBlockData p_i39_4_) {
         this.b = p_i39_2_;
         this.c = p_i39_3_;
         this.e = p_i39_4_;
         this.d = (BlockMinecartTrackAbstract)p_i39_4_.getBlock();
         BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)p_i39_4_.get(BlockMinecartTrackAbstract.this.n());
         this.f = this.d.a;
         this.a(blockminecarttrackabstract$enumtrackposition);
      }

      private void a(BlockMinecartTrackAbstract.EnumTrackPosition p_a_1_) {
         this.g.clear();
         switch(BlockMinecartTrackAbstract.SyntheticClass_1.a[p_a_1_.ordinal()]) {
         case 1:
            this.g.add(this.c.north());
            this.g.add(this.c.south());
            break;
         case 2:
            this.g.add(this.c.west());
            this.g.add(this.c.east());
            break;
         case 3:
            this.g.add(this.c.west());
            this.g.add(this.c.east().up());
            break;
         case 4:
            this.g.add(this.c.west().up());
            this.g.add(this.c.east());
            break;
         case 5:
            this.g.add(this.c.north().up());
            this.g.add(this.c.south());
            break;
         case 6:
            this.g.add(this.c.north());
            this.g.add(this.c.south().up());
            break;
         case 7:
            this.g.add(this.c.east());
            this.g.add(this.c.south());
            break;
         case 8:
            this.g.add(this.c.west());
            this.g.add(this.c.south());
            break;
         case 9:
            this.g.add(this.c.west());
            this.g.add(this.c.north());
            break;
         case 10:
            this.g.add(this.c.east());
            this.g.add(this.c.north());
         }

      }

      private void c() {
         for(int i = 0; i < this.g.size(); ++i) {
            BlockMinecartTrackAbstract.MinecartTrackLogic blockminecarttrackabstract$minecarttracklogic = this.b((BlockPosition)this.g.get(i));
            if(blockminecarttrackabstract$minecarttracklogic != null && blockminecarttrackabstract$minecarttracklogic.a(this)) {
               this.g.set(i, blockminecarttrackabstract$minecarttracklogic.c);
            } else {
               this.g.remove(i--);
            }
         }

      }

      private boolean a(BlockPosition p_a_1_) {
         return BlockMinecartTrackAbstract.e(this.b, p_a_1_) || BlockMinecartTrackAbstract.e(this.b, p_a_1_.up()) || BlockMinecartTrackAbstract.e(this.b, p_a_1_.down());
      }

      private BlockMinecartTrackAbstract.MinecartTrackLogic b(BlockPosition p_b_1_) {
         IBlockData iblockdata = this.b.getType(p_b_1_);
         if(BlockMinecartTrackAbstract.d(iblockdata)) {
            BlockMinecartTrackAbstract blockminecarttrackabstract1 = BlockMinecartTrackAbstract.this;
            BlockMinecartTrackAbstract.this.getClass();
            return blockminecarttrackabstract1.new MinecartTrackLogic(this.b, p_b_1_, iblockdata);
         } else {
            BlockPosition blockposition = p_b_1_.up();
            iblockdata = this.b.getType(blockposition);
            if(BlockMinecartTrackAbstract.d(iblockdata)) {
               BlockMinecartTrackAbstract blockminecarttrackabstract = BlockMinecartTrackAbstract.this;
               BlockMinecartTrackAbstract.this.getClass();
               return blockminecarttrackabstract.new MinecartTrackLogic(this.b, blockposition, iblockdata);
            } else {
               blockposition = p_b_1_.down();
               iblockdata = this.b.getType(blockposition);
               BlockMinecartTrackAbstract.MinecartTrackLogic blockminecarttrackabstract$minecarttracklogic;
               if(BlockMinecartTrackAbstract.d(iblockdata)) {
                  BlockMinecartTrackAbstract blockminecarttrackabstract2 = BlockMinecartTrackAbstract.this;
                  BlockMinecartTrackAbstract.this.getClass();
                  blockminecarttrackabstract$minecarttracklogic = blockminecarttrackabstract2.new MinecartTrackLogic(this.b, blockposition, iblockdata);
               } else {
                  blockminecarttrackabstract$minecarttracklogic = null;
               }

               return blockminecarttrackabstract$minecarttracklogic;
            }
         }
      }

      private boolean a(BlockMinecartTrackAbstract.MinecartTrackLogic p_a_1_) {
         return this.c(p_a_1_.c);
      }

      private boolean c(BlockPosition p_c_1_) {
         for(int i = 0; i < this.g.size(); ++i) {
            BlockPosition blockposition = (BlockPosition)this.g.get(i);
            if(blockposition.getX() == p_c_1_.getX() && blockposition.getZ() == p_c_1_.getZ()) {
               return true;
            }
         }

         return false;
      }

      protected int a() {
         int i = 0;

         for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            if(this.a(this.c.shift(enumdirection))) {
               ++i;
            }
         }

         return i;
      }

      private boolean b(BlockMinecartTrackAbstract.MinecartTrackLogic p_b_1_) {
         return this.a(p_b_1_) || this.g.size() != 2;
      }

      private void c(BlockMinecartTrackAbstract.MinecartTrackLogic p_c_1_) {
         this.g.add(p_c_1_.c);
         BlockPosition blockposition = this.c.north();
         BlockPosition blockposition1 = this.c.south();
         BlockPosition blockposition2 = this.c.west();
         BlockPosition blockposition3 = this.c.east();
         boolean flag = this.c(blockposition);
         boolean flag1 = this.c(blockposition1);
         boolean flag2 = this.c(blockposition2);
         boolean flag3 = this.c(blockposition3);
         BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = null;
         if(flag || flag1) {
            blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
         }

         if(flag2 || flag3) {
            blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
         }

         if(!this.f) {
            if(flag1 && flag3 && !flag && !flag2) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
            }

            if(flag1 && flag2 && !flag && !flag3) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
            }

            if(flag && flag2 && !flag1 && !flag3) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
            }

            if(flag && flag3 && !flag1 && !flag2) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
            }
         }

         if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH) {
            if(BlockMinecartTrackAbstract.e(this.b, blockposition.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH;
            }

            if(BlockMinecartTrackAbstract.e(this.b, blockposition1.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH;
            }
         }

         if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST) {
            if(BlockMinecartTrackAbstract.e(this.b, blockposition3.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST;
            }

            if(BlockMinecartTrackAbstract.e(this.b, blockposition2.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST;
            }
         }

         if(blockminecarttrackabstract$enumtrackposition == null) {
            blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
         }

         this.e = this.e.set(this.d.n(), blockminecarttrackabstract$enumtrackposition);
         this.b.setTypeAndData(this.c, this.e, 3);
      }

      private boolean d(BlockPosition p_d_1_) {
         BlockMinecartTrackAbstract.MinecartTrackLogic blockminecarttrackabstract$minecarttracklogic = this.b(p_d_1_);
         if(blockminecarttrackabstract$minecarttracklogic == null) {
            return false;
         } else {
            blockminecarttrackabstract$minecarttracklogic.c();
            return blockminecarttrackabstract$minecarttracklogic.b(this);
         }
      }

      public BlockMinecartTrackAbstract.MinecartTrackLogic a(boolean p_a_1_, boolean p_a_2_) {
         BlockPosition blockposition = this.c.north();
         BlockPosition blockposition1 = this.c.south();
         BlockPosition blockposition2 = this.c.west();
         BlockPosition blockposition3 = this.c.east();
         boolean flag = this.d(blockposition);
         boolean flag1 = this.d(blockposition1);
         boolean flag2 = this.d(blockposition2);
         boolean flag3 = this.d(blockposition3);
         BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = null;
         if((flag || flag1) && !flag2 && !flag3) {
            blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
         }

         if((flag2 || flag3) && !flag && !flag1) {
            blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
         }

         if(!this.f) {
            if(flag1 && flag3 && !flag && !flag2) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
            }

            if(flag1 && flag2 && !flag && !flag3) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
            }

            if(flag && flag2 && !flag1 && !flag3) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
            }

            if(flag && flag3 && !flag1 && !flag2) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
            }
         }

         if(blockminecarttrackabstract$enumtrackposition == null) {
            if(flag || flag1) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
            }

            if(flag2 || flag3) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
            }

            if(!this.f) {
               if(p_a_1_) {
                  if(flag1 && flag3) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
                  }

                  if(flag2 && flag1) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
                  }

                  if(flag3 && flag) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
                  }

                  if(flag && flag2) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
                  }
               } else {
                  if(flag && flag2) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
                  }

                  if(flag3 && flag) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
                  }

                  if(flag2 && flag1) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
                  }

                  if(flag1 && flag3) {
                     blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
                  }
               }
            }
         }

         if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH) {
            if(BlockMinecartTrackAbstract.e(this.b, blockposition.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH;
            }

            if(BlockMinecartTrackAbstract.e(this.b, blockposition1.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH;
            }
         }

         if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST) {
            if(BlockMinecartTrackAbstract.e(this.b, blockposition3.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST;
            }

            if(BlockMinecartTrackAbstract.e(this.b, blockposition2.up())) {
               blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST;
            }
         }

         if(blockminecarttrackabstract$enumtrackposition == null) {
            blockminecarttrackabstract$enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
         }

         this.a(blockminecarttrackabstract$enumtrackposition);
         this.e = this.e.set(this.d.n(), blockminecarttrackabstract$enumtrackposition);
         if(p_a_2_ || this.b.getType(this.c) != this.e) {
            this.b.setTypeAndData(this.c, this.e, 3);

            for(int i = 0; i < this.g.size(); ++i) {
               BlockMinecartTrackAbstract.MinecartTrackLogic blockminecarttrackabstract$minecarttracklogic = this.b((BlockPosition)this.g.get(i));
               if(blockminecarttrackabstract$minecarttracklogic != null) {
                  blockminecarttrackabstract$minecarttracklogic.c();
                  if(blockminecarttrackabstract$minecarttracklogic.b(this)) {
                     blockminecarttrackabstract$minecarttracklogic.c(this);
                  }
               }
            }
         }

         return this;
      }

      public IBlockData b() {
         return this.e;
      }
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[BlockMinecartTrackAbstract.EnumTrackPosition.values().length];

      static {
         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 3;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 5;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 6;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST.ordinal()] = 7;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST.ordinal()] = 8;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST.ordinal()] = 9;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST.ordinal()] = 10;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}
