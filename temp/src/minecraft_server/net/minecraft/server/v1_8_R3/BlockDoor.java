package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockDoor extends Block {
   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
   public static final BlockStateBoolean OPEN = BlockStateBoolean.of("open");
   public static final BlockStateEnum<BlockDoor.EnumDoorHinge> HINGE = BlockStateEnum.<BlockDoor.EnumDoorHinge>of("hinge", BlockDoor.EnumDoorHinge.class);
   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
   public static final BlockStateEnum<BlockDoor.EnumDoorHalf> HALF = BlockStateEnum.<BlockDoor.EnumDoorHalf>of("half", BlockDoor.EnumDoorHalf.class);

   protected BlockDoor(Material p_i101_1_) {
      super(p_i101_1_);
      this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(OPEN, Boolean.valueOf(false)).set(HINGE, BlockDoor.EnumDoorHinge.LEFT).set(POWERED, Boolean.valueOf(false)).set(HALF, BlockDoor.EnumDoorHalf.LOWER));
   }

   public String getName() {
      return LocaleI18n.get((this.a() + ".name").replaceAll("tile", "item"));
   }

   public boolean c() {
      return false;
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_) {
      return g(e(p_b_1_, p_b_2_));
   }

   public boolean d() {
      return false;
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      this.updateShape(p_a_1_, p_a_2_);
      return super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      this.k(e(p_updateShape_1_, p_updateShape_2_));
   }

   private void k(int p_k_1_) {
      float f = 0.1875F;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
      EnumDirection enumdirection = f(p_k_1_);
      boolean flag = g(p_k_1_);
      boolean flag1 = j(p_k_1_);
      if(flag) {
         if(enumdirection == EnumDirection.EAST) {
            if(!flag1) {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            } else {
               this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
         } else if(enumdirection == EnumDirection.SOUTH) {
            if(!flag1) {
               this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
         } else if(enumdirection == EnumDirection.WEST) {
            if(!flag1) {
               this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            } else {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
         } else if(enumdirection == EnumDirection.NORTH) {
            if(!flag1) {
               this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            } else {
               this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      } else if(enumdirection == EnumDirection.EAST) {
         this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
      } else if(enumdirection == EnumDirection.SOUTH) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
      } else if(enumdirection == EnumDirection.WEST) {
         this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else if(enumdirection == EnumDirection.NORTH) {
         this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
      }

   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(this.material == Material.ORE) {
         return true;
      } else {
         BlockPosition blockposition = p_interact_3_.get(HALF) == BlockDoor.EnumDoorHalf.LOWER?p_interact_2_:p_interact_2_.down();
         IBlockData iblockdata = p_interact_2_.equals(blockposition)?p_interact_3_:p_interact_1_.getType(blockposition);
         if(iblockdata.getBlock() != this) {
            return false;
         } else {
            p_interact_3_ = iblockdata.a(OPEN);
            p_interact_1_.setTypeAndData(blockposition, p_interact_3_, 2);
            p_interact_1_.b(blockposition, p_interact_2_);
            p_interact_1_.a(p_interact_4_, ((Boolean)p_interact_3_.get(OPEN)).booleanValue()?1003:1006, p_interact_2_, 0);
            return true;
         }
      }
   }

   public void setDoor(World p_setDoor_1_, BlockPosition p_setDoor_2_, boolean p_setDoor_3_) {
      IBlockData iblockdata = p_setDoor_1_.getType(p_setDoor_2_);
      if(iblockdata.getBlock() == this) {
         BlockPosition blockposition = iblockdata.get(HALF) == BlockDoor.EnumDoorHalf.LOWER?p_setDoor_2_:p_setDoor_2_.down();
         IBlockData iblockdata1 = p_setDoor_2_ == blockposition?iblockdata:p_setDoor_1_.getType(blockposition);
         if(iblockdata1.getBlock() == this && ((Boolean)iblockdata1.get(OPEN)).booleanValue() != p_setDoor_3_) {
            p_setDoor_1_.setTypeAndData(blockposition, iblockdata1.set(OPEN, Boolean.valueOf(p_setDoor_3_)), 2);
            p_setDoor_1_.b(blockposition, p_setDoor_2_);
            p_setDoor_1_.a((EntityHuman)null, p_setDoor_3_?1003:1006, p_setDoor_2_, 0);
         }
      }

   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(p_doPhysics_3_.get(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
         BlockPosition blockposition = p_doPhysics_2_.down();
         IBlockData iblockdata = p_doPhysics_1_.getType(blockposition);
         if(iblockdata.getBlock() != this) {
            p_doPhysics_1_.setAir(p_doPhysics_2_);
         } else if(p_doPhysics_4_ != this) {
            this.doPhysics(p_doPhysics_1_, blockposition, iblockdata, p_doPhysics_4_);
         }
      } else {
         boolean flag1 = false;
         BlockPosition blockposition1 = p_doPhysics_2_.up();
         IBlockData iblockdata1 = p_doPhysics_1_.getType(blockposition1);
         if(iblockdata1.getBlock() != this) {
            p_doPhysics_1_.setAir(p_doPhysics_2_);
            flag1 = true;
         }

         if(!World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.down())) {
            p_doPhysics_1_.setAir(p_doPhysics_2_);
            flag1 = true;
            if(iblockdata1.getBlock() == this) {
               p_doPhysics_1_.setAir(blockposition1);
            }
         }

         if(flag1) {
            if(!p_doPhysics_1_.isClientSide) {
               this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            }
         } else {
            org.bukkit.World world = p_doPhysics_1_.getWorld();
            org.bukkit.block.Block block = world.getBlockAt(p_doPhysics_2_.getX(), p_doPhysics_2_.getY(), p_doPhysics_2_.getZ());
            org.bukkit.block.Block block1 = world.getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
            int i = block.getBlockPower();
            int j = block1.getBlockPower();
            if(j > i) {
               i = j;
            }

            int k = ((Boolean)iblockdata1.get(POWERED)).booleanValue()?15:0;
            if(k == 0 ^ i == 0) {
               BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, k, i);
               p_doPhysics_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
               boolean flag = blockredstoneevent.getNewCurrent() > 0;
               p_doPhysics_1_.setTypeAndData(blockposition1, iblockdata1.set(POWERED, Boolean.valueOf(flag)), 2);
               if(flag != ((Boolean)p_doPhysics_3_.get(OPEN)).booleanValue()) {
                  p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(OPEN, Boolean.valueOf(flag)), 2);
                  p_doPhysics_1_.b(p_doPhysics_2_, p_doPhysics_2_);
                  p_doPhysics_1_.a((EntityHuman)null, flag?1003:1006, p_doPhysics_2_, 0);
               }
            }
         }
      }

   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return p_getDropType_1_.get(HALF) == BlockDoor.EnumDoorHalf.UPPER?null:this.l();
   }

   public MovingObjectPosition a(World p_a_1_, BlockPosition p_a_2_, Vec3D p_a_3_, Vec3D p_a_4_) {
      this.updateShape(p_a_1_, p_a_2_);
      return super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      return p_canPlace_2_.getY() >= 255?false:World.a((IBlockAccess)p_canPlace_1_, (BlockPosition)p_canPlace_2_.down()) && super.canPlace(p_canPlace_1_, p_canPlace_2_) && super.canPlace(p_canPlace_1_, p_canPlace_2_.up());
   }

   public int k() {
      return 1;
   }

   public static int e(IBlockAccess p_e_0_, BlockPosition p_e_1_) {
      IBlockData iblockdata = p_e_0_.getType(p_e_1_);
      int i = iblockdata.getBlock().toLegacyData(iblockdata);
      boolean flag = i(i);
      IBlockData iblockdata1 = p_e_0_.getType(p_e_1_.down());
      int j = iblockdata1.getBlock().toLegacyData(iblockdata1);
      int k = flag?j:i;
      IBlockData iblockdata2 = p_e_0_.getType(p_e_1_.up());
      int l = iblockdata2.getBlock().toLegacyData(iblockdata2);
      int i1 = flag?i:l;
      boolean flag1 = (i1 & 1) != 0;
      boolean flag2 = (i1 & 2) != 0;
      return b(k) | (flag?8:0) | (flag1?16:0) | (flag2?32:0);
   }

   private Item l() {
      return this == Blocks.IRON_DOOR?Items.IRON_DOOR:(this == Blocks.SPRUCE_DOOR?Items.SPRUCE_DOOR:(this == Blocks.BIRCH_DOOR?Items.BIRCH_DOOR:(this == Blocks.JUNGLE_DOOR?Items.JUNGLE_DOOR:(this == Blocks.ACACIA_DOOR?Items.ACACIA_DOOR:(this == Blocks.DARK_OAK_DOOR?Items.DARK_OAK_DOOR:Items.WOODEN_DOOR)))));
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_) {
      BlockPosition blockposition = p_a_2_.down();
      if(p_a_4_.abilities.canInstantlyBuild && p_a_3_.get(HALF) == BlockDoor.EnumDoorHalf.UPPER && p_a_1_.getType(blockposition).getBlock() == this) {
         p_a_1_.setAir(blockposition);
      }

   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      if(p_updateState_1_.get(HALF) == BlockDoor.EnumDoorHalf.LOWER) {
         IBlockData iblockdata = p_updateState_2_.getType(p_updateState_3_.up());
         if(iblockdata.getBlock() == this) {
            p_updateState_1_ = p_updateState_1_.set(HINGE, (BlockDoor.EnumDoorHinge)iblockdata.get(HINGE)).set(POWERED, (Boolean)iblockdata.get(POWERED));
         }
      } else {
         IBlockData iblockdata1 = p_updateState_2_.getType(p_updateState_3_.down());
         if(iblockdata1.getBlock() == this) {
            p_updateState_1_ = p_updateState_1_.set(FACING, (EnumDirection)iblockdata1.get(FACING)).set(OPEN, (Boolean)iblockdata1.get(OPEN));
         }
      }

      return p_updateState_1_;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return (p_fromLegacyData_1_ & 8) > 0?this.getBlockData().set(HALF, BlockDoor.EnumDoorHalf.UPPER).set(HINGE, (p_fromLegacyData_1_ & 1) > 0?BlockDoor.EnumDoorHinge.RIGHT:BlockDoor.EnumDoorHinge.LEFT).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 2) > 0)):this.getBlockData().set(HALF, BlockDoor.EnumDoorHalf.LOWER).set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_ & 3).f()).set(OPEN, Boolean.valueOf((p_fromLegacyData_1_ & 4) > 0));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      byte b0 = 0;
      int i;
      if(p_toLegacyData_1_.get(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
         i = b0 | 8;
         if(p_toLegacyData_1_.get(HINGE) == BlockDoor.EnumDoorHinge.RIGHT) {
            i |= 1;
         }

         if(((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue()) {
            i |= 2;
         }
      } else {
         i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).e().b();
         if(((Boolean)p_toLegacyData_1_.get(OPEN)).booleanValue()) {
            i |= 4;
         }
      }

      return i;
   }

   protected static int b(int p_b_0_) {
      return p_b_0_ & 7;
   }

   public static boolean f(IBlockAccess p_f_0_, BlockPosition p_f_1_) {
      return g(e(p_f_0_, p_f_1_));
   }

   public static EnumDirection h(IBlockAccess p_h_0_, BlockPosition p_h_1_) {
      return f(e(p_h_0_, p_h_1_));
   }

   public static EnumDirection f(int p_f_0_) {
      return EnumDirection.fromType2(p_f_0_ & 3).f();
   }

   protected static boolean g(int p_g_0_) {
      return (p_g_0_ & 4) != 0;
   }

   protected static boolean i(int p_i_0_) {
      return (p_i_0_ & 8) != 0;
   }

   protected static boolean j(int p_j_0_) {
      return (p_j_0_ & 16) != 0;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{HALF, FACING, OPEN, HINGE, POWERED});
   }

   public static enum EnumDoorHalf implements INamable {
      UPPER,
      LOWER;

      public String toString() {
         return this.getName();
      }

      public String getName() {
         return this == UPPER?"upper":"lower";
      }
   }

   public static enum EnumDoorHinge implements INamable {
      LEFT,
      RIGHT;

      public String toString() {
         return this.getName();
      }

      public String getName() {
         return this == LEFT?"left":"right";
      }
   }
}
