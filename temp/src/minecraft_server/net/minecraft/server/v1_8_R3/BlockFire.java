package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockTNT;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldProviderTheEnd;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.material.MaterialData;

public class BlockFire extends Block {
   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 15);
   public static final BlockStateBoolean FLIP = BlockStateBoolean.of("flip");
   public static final BlockStateBoolean ALT = BlockStateBoolean.of("alt");
   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
   public static final BlockStateInteger UPPER = BlockStateInteger.of("upper", 0, 2);
   private final Map<Block, Integer> flameChances = Maps.<Block, Integer>newIdentityHashMap();
   private final Map<Block, Integer> U = Maps.<Block, Integer>newIdentityHashMap();

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      int i = p_updateState_3_.getX();
      int j = p_updateState_3_.getY();
      int k = p_updateState_3_.getZ();
      if(!World.a(p_updateState_2_, p_updateState_3_.down()) && !Blocks.FIRE.e(p_updateState_2_, p_updateState_3_.down())) {
         boolean flag = (i + j + k & 1) == 1;
         boolean flag1 = (i / 2 + j / 2 + k / 2 & 1) == 1;
         int l = 0;
         if(this.e(p_updateState_2_, p_updateState_3_.up())) {
            l = flag?1:2;
         }

         return p_updateState_1_.set(NORTH, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.north()))).set(EAST, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.east()))).set(SOUTH, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.south()))).set(WEST, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.west()))).set(UPPER, Integer.valueOf(l)).set(FLIP, Boolean.valueOf(flag1)).set(ALT, Boolean.valueOf(flag));
      } else {
         return this.getBlockData();
      }
   }

   protected BlockFire() {
      super(Material.FIRE);
      this.j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)).set(FLIP, Boolean.valueOf(false)).set(ALT, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)).set(UPPER, Integer.valueOf(0)));
      this.a(true);
   }

   public static void l() {
      Blocks.FIRE.a(Blocks.PLANKS, 5, 20);
      Blocks.FIRE.a(Blocks.DOUBLE_WOODEN_SLAB, 5, 20);
      Blocks.FIRE.a(Blocks.WOODEN_SLAB, 5, 20);
      Blocks.FIRE.a(Blocks.FENCE_GATE, 5, 20);
      Blocks.FIRE.a(Blocks.SPRUCE_FENCE_GATE, 5, 20);
      Blocks.FIRE.a(Blocks.BIRCH_FENCE_GATE, 5, 20);
      Blocks.FIRE.a(Blocks.JUNGLE_FENCE_GATE, 5, 20);
      Blocks.FIRE.a(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
      Blocks.FIRE.a(Blocks.ACACIA_FENCE_GATE, 5, 20);
      Blocks.FIRE.a(Blocks.FENCE, 5, 20);
      Blocks.FIRE.a(Blocks.SPRUCE_FENCE, 5, 20);
      Blocks.FIRE.a(Blocks.BIRCH_FENCE, 5, 20);
      Blocks.FIRE.a(Blocks.JUNGLE_FENCE, 5, 20);
      Blocks.FIRE.a(Blocks.DARK_OAK_FENCE, 5, 20);
      Blocks.FIRE.a(Blocks.ACACIA_FENCE, 5, 20);
      Blocks.FIRE.a(Blocks.OAK_STAIRS, 5, 20);
      Blocks.FIRE.a(Blocks.BIRCH_STAIRS, 5, 20);
      Blocks.FIRE.a(Blocks.SPRUCE_STAIRS, 5, 20);
      Blocks.FIRE.a(Blocks.JUNGLE_STAIRS, 5, 20);
      Blocks.FIRE.a(Blocks.LOG, 5, 5);
      Blocks.FIRE.a(Blocks.LOG2, 5, 5);
      Blocks.FIRE.a(Blocks.LEAVES, 30, 60);
      Blocks.FIRE.a(Blocks.LEAVES2, 30, 60);
      Blocks.FIRE.a(Blocks.BOOKSHELF, 30, 20);
      Blocks.FIRE.a(Blocks.TNT, 15, 100);
      Blocks.FIRE.a(Blocks.TALLGRASS, 60, 100);
      Blocks.FIRE.a(Blocks.DOUBLE_PLANT, 60, 100);
      Blocks.FIRE.a(Blocks.YELLOW_FLOWER, 60, 100);
      Blocks.FIRE.a(Blocks.RED_FLOWER, 60, 100);
      Blocks.FIRE.a(Blocks.DEADBUSH, 60, 100);
      Blocks.FIRE.a(Blocks.WOOL, 30, 60);
      Blocks.FIRE.a(Blocks.VINE, 15, 100);
      Blocks.FIRE.a(Blocks.COAL_BLOCK, 5, 5);
      Blocks.FIRE.a(Blocks.HAY_BLOCK, 60, 20);
      Blocks.FIRE.a(Blocks.CARPET, 60, 20);
   }

   public void a(Block p_a_1_, int p_a_2_, int p_a_3_) {
      this.flameChances.put(p_a_1_, Integer.valueOf(p_a_2_));
      this.U.put(p_a_1_, Integer.valueOf(p_a_3_));
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return null;
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public int a(Random p_a_1_) {
      return 0;
   }

   public int a(World p_a_1_) {
      return 30;
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(p_b_1_.getGameRules().getBoolean("doFireTick")) {
         if(!this.canPlace(p_b_1_, p_b_2_)) {
            this.fireExtinguished(p_b_1_, p_b_2_);
         }

         Block block = p_b_1_.getType(p_b_2_.down()).getBlock();
         boolean flag = block == Blocks.NETHERRACK;
         if(p_b_1_.worldProvider instanceof WorldProviderTheEnd && block == Blocks.BEDROCK) {
            flag = true;
         }

         if(!flag && p_b_1_.S() && this.e(p_b_1_, p_b_2_)) {
            this.fireExtinguished(p_b_1_, p_b_2_);
         } else {
            int i = ((Integer)p_b_3_.get(AGE)).intValue();
            if(i < 15) {
               p_b_3_ = p_b_3_.set(AGE, Integer.valueOf(i + p_b_4_.nextInt(3) / 2));
               p_b_1_.setTypeAndData(p_b_2_, p_b_3_, 4);
            }

            p_b_1_.a((BlockPosition)p_b_2_, (Block)this, this.a(p_b_1_) + p_b_4_.nextInt(10));
            if(!flag) {
               if(!this.f(p_b_1_, p_b_2_)) {
                  if(!World.a((IBlockAccess)p_b_1_, (BlockPosition)p_b_2_.down()) || i > 3) {
                     this.fireExtinguished(p_b_1_, p_b_2_);
                  }

                  return;
               }

               if(!this.e((IBlockAccess)p_b_1_, p_b_2_.down()) && i == 15 && p_b_4_.nextInt(4) == 0) {
                  this.fireExtinguished(p_b_1_, p_b_2_);
                  return;
               }
            }

            boolean flag1 = p_b_1_.D(p_b_2_);
            byte b0 = 0;
            if(flag1) {
               b0 = -50;
            }

            this.a(p_b_1_, p_b_2_.east(), 300 + b0, p_b_4_, i);
            this.a(p_b_1_, p_b_2_.west(), 300 + b0, p_b_4_, i);
            this.a(p_b_1_, p_b_2_.down(), 250 + b0, p_b_4_, i);
            this.a(p_b_1_, p_b_2_.up(), 250 + b0, p_b_4_, i);
            this.a(p_b_1_, p_b_2_.north(), 300 + b0, p_b_4_, i);
            this.a(p_b_1_, p_b_2_.south(), 300 + b0, p_b_4_, i);

            for(int j = -1; j <= 1; ++j) {
               for(int k = -1; k <= 1; ++k) {
                  for(int l = -1; l <= 4; ++l) {
                     if(j != 0 || l != 0 || k != 0) {
                        int i1 = 100;
                        if(l > 1) {
                           i1 += (l - 1) * 100;
                        }

                        BlockPosition blockposition = p_b_2_.a(j, l, k);
                        int j1 = this.m(p_b_1_, blockposition);
                        if(j1 > 0) {
                           int k1 = (j1 + 40 + p_b_1_.getDifficulty().a() * 7) / (i + 30);
                           if(flag1) {
                              k1 /= 2;
                           }

                           if(k1 > 0 && p_b_4_.nextInt(i1) <= k1 && (!p_b_1_.S() || !this.e(p_b_1_, blockposition))) {
                              int l1 = i + p_b_4_.nextInt(5) / 4;
                              if(l1 > 15) {
                                 l1 = 15;
                              }

                              if(p_b_1_.getType(blockposition) != Blocks.FIRE && !CraftEventFactory.callBlockIgniteEvent(p_b_1_, blockposition.getX(), blockposition.getY(), blockposition.getZ(), p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()).isCancelled()) {
                                 Server server = p_b_1_.getServer();
                                 org.bukkit.World world = p_b_1_.getWorld();
                                 org.bukkit.block.BlockState blockstate = world.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()).getState();
                                 blockstate.setTypeId(Block.getId(this));
                                 blockstate.setData(new MaterialData(Block.getId(this), (byte)l1));
                                 BlockSpreadEvent blockspreadevent = new BlockSpreadEvent(blockstate.getBlock(), world.getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()), blockstate);
                                 server.getPluginManager().callEvent(blockspreadevent);
                                 if(!blockspreadevent.isCancelled()) {
                                    blockstate.update(true);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   protected boolean e(World p_e_1_, BlockPosition p_e_2_) {
      return p_e_1_.isRainingAt(p_e_2_) || p_e_1_.isRainingAt(p_e_2_.west()) || p_e_1_.isRainingAt(p_e_2_.east()) || p_e_1_.isRainingAt(p_e_2_.north()) || p_e_1_.isRainingAt(p_e_2_.south());
   }

   public boolean N() {
      return false;
   }

   private int c(Block p_c_1_) {
      Integer integer = (Integer)this.U.get(p_c_1_);
      return integer == null?0:integer.intValue();
   }

   private int d(Block p_d_1_) {
      Integer integer = (Integer)this.flameChances.get(p_d_1_);
      return integer == null?0:integer.intValue();
   }

   private void a(World p_a_1_, BlockPosition p_a_2_, int p_a_3_, Random p_a_4_, int p_a_5_) {
      int i = this.c(p_a_1_.getType(p_a_2_).getBlock());
      if(p_a_4_.nextInt(p_a_3_) < i) {
         IBlockData iblockdata = p_a_1_.getType(p_a_2_);
         org.bukkit.block.Block block = p_a_1_.getWorld().getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
         BlockBurnEvent blockburnevent = new BlockBurnEvent(block);
         p_a_1_.getServer().getPluginManager().callEvent(blockburnevent);
         if(blockburnevent.isCancelled()) {
            return;
         }

         if(p_a_4_.nextInt(p_a_5_ + 10) < 5 && !p_a_1_.isRainingAt(p_a_2_)) {
            int j = p_a_5_ + p_a_4_.nextInt(5) / 4;
            if(j > 15) {
               j = 15;
            }

            p_a_1_.setTypeAndData(p_a_2_, this.getBlockData().set(AGE, Integer.valueOf(j)), 3);
         } else {
            this.fireExtinguished(p_a_1_, p_a_2_);
         }

         if(iblockdata.getBlock() == Blocks.TNT) {
            Blocks.TNT.postBreak(p_a_1_, p_a_2_, iblockdata.set(BlockTNT.EXPLODE, Boolean.valueOf(true)));
         }
      }

   }

   private boolean f(World p_f_1_, BlockPosition p_f_2_) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         if(this.e((IBlockAccess)p_f_1_, p_f_2_.shift(enumdirection))) {
            return true;
         }
      }

      return false;
   }

   private int m(World p_m_1_, BlockPosition p_m_2_) {
      if(!p_m_1_.isEmpty(p_m_2_)) {
         return 0;
      } else {
         int i = 0;

         for(EnumDirection enumdirection : EnumDirection.values()) {
            i = Math.max(this.d(p_m_1_.getType(p_m_2_.shift(enumdirection)).getBlock()), i);
         }

         return i;
      }
   }

   public boolean A() {
      return false;
   }

   public boolean e(IBlockAccess p_e_1_, BlockPosition p_e_2_) {
      return this.d(p_e_1_.getType(p_e_2_).getBlock()) > 0;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      return World.a((IBlockAccess)p_canPlace_1_, (BlockPosition)p_canPlace_2_.down()) || this.f(p_canPlace_1_, p_canPlace_2_);
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.down()) && !this.f(p_doPhysics_1_, p_doPhysics_2_)) {
         this.fireExtinguished(p_doPhysics_1_, p_doPhysics_2_);
      }

   }

   public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_) {
      if(p_onPlace_1_.worldProvider.getDimension() > 0 || !Blocks.PORTAL.e(p_onPlace_1_, p_onPlace_2_)) {
         if(!World.a((IBlockAccess)p_onPlace_1_, (BlockPosition)p_onPlace_2_.down()) && !this.f(p_onPlace_1_, p_onPlace_2_)) {
            this.fireExtinguished(p_onPlace_1_, p_onPlace_2_);
         } else {
            p_onPlace_1_.a((BlockPosition)p_onPlace_2_, (Block)this, this.a(p_onPlace_1_) + p_onPlace_1_.random.nextInt(10));
         }
      }

   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return MaterialMapColor.f;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(AGE, Integer.valueOf(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((Integer)p_toLegacyData_1_.get(AGE)).intValue();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{AGE, NORTH, EAST, SOUTH, WEST, UPPER, FLIP, ALT});
   }

   private void fireExtinguished(World p_fireExtinguished_1_, BlockPosition p_fireExtinguished_2_) {
      if(!CraftEventFactory.callBlockFadeEvent(p_fireExtinguished_1_.getWorld().getBlockAt(p_fireExtinguished_2_.getX(), p_fireExtinguished_2_.getY(), p_fireExtinguished_2_.getZ()), Blocks.AIR).isCancelled()) {
         p_fireExtinguished_1_.setAir(p_fireExtinguished_2_);
      }

   }
}
