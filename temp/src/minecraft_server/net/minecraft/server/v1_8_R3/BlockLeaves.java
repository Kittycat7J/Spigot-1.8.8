package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockTransparent;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.block.LeavesDecayEvent;

public abstract class BlockLeaves extends BlockTransparent {
   public static final BlockStateBoolean DECAYABLE = BlockStateBoolean.of("decayable");
   public static final BlockStateBoolean CHECK_DECAY = BlockStateBoolean.of("check_decay");
   int[] N;

   public BlockLeaves() {
      super(Material.LEAVES, false);
      this.a(true);
      this.a(CreativeModeTab.c);
      this.c(0.2F);
      this.e(1);
      this.a(h);
   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      byte b0 = 1;
      int i = b0 + 1;
      int j = p_remove_2_.getX();
      int k = p_remove_2_.getY();
      int l = p_remove_2_.getZ();
      if(p_remove_1_.areChunksLoadedBetween(new BlockPosition(j - i, k - i, l - i), new BlockPosition(j + i, k + i, l + i))) {
         for(int i1 = -b0; i1 <= b0; ++i1) {
            for(int j1 = -b0; j1 <= b0; ++j1) {
               for(int k1 = -b0; k1 <= b0; ++k1) {
                  BlockPosition blockposition = p_remove_2_.a(i1, j1, k1);
                  IBlockData iblockdata = p_remove_1_.getType(blockposition);
                  if(iblockdata.getBlock().getMaterial() == Material.LEAVES && !((Boolean)iblockdata.get(CHECK_DECAY)).booleanValue()) {
                     p_remove_1_.setTypeAndData(blockposition, iblockdata.set(CHECK_DECAY, Boolean.valueOf(true)), 4);
                  }
               }
            }
         }
      }

   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(!p_b_1_.isClientSide && ((Boolean)p_b_3_.get(CHECK_DECAY)).booleanValue() && ((Boolean)p_b_3_.get(DECAYABLE)).booleanValue()) {
         byte b0 = 4;
         int i = b0 + 1;
         int j = p_b_2_.getX();
         int k = p_b_2_.getY();
         int l = p_b_2_.getZ();
         byte b1 = 32;
         int i1 = b1 * b1;
         int j1 = b1 / 2;
         if(this.N == null) {
            this.N = new int[b1 * b1 * b1];
         }

         if(p_b_1_.areChunksLoadedBetween(new BlockPosition(j - i, k - i, l - i), new BlockPosition(j + i, k + i, l + i))) {
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

            for(int k1 = -b0; k1 <= b0; ++k1) {
               for(int l1 = -b0; l1 <= b0; ++l1) {
                  for(int i2 = -b0; i2 <= b0; ++i2) {
                     Block block = p_b_1_.getType(blockposition$mutableblockposition.c(j + k1, k + l1, l + i2)).getBlock();
                     if(block != Blocks.LOG && block != Blocks.LOG2) {
                        if(block.getMaterial() == Material.LEAVES) {
                           this.N[(k1 + j1) * i1 + (l1 + j1) * b1 + i2 + j1] = -2;
                        } else {
                           this.N[(k1 + j1) * i1 + (l1 + j1) * b1 + i2 + j1] = -1;
                        }
                     } else {
                        this.N[(k1 + j1) * i1 + (l1 + j1) * b1 + i2 + j1] = 0;
                     }
                  }
               }
            }

            for(int k21 = 1; k21 <= 4; ++k21) {
               for(int l2 = -b0; l2 <= b0; ++l2) {
                  for(int i3 = -b0; i3 <= b0; ++i3) {
                     for(int j3 = -b0; j3 <= b0; ++j3) {
                        if(this.N[(l2 + j1) * i1 + (i3 + j1) * b1 + j3 + j1] == k21 - 1) {
                           if(this.N[(l2 + j1 - 1) * i1 + (i3 + j1) * b1 + j3 + j1] == -2) {
                              this.N[(l2 + j1 - 1) * i1 + (i3 + j1) * b1 + j3 + j1] = k21;
                           }

                           if(this.N[(l2 + j1 + 1) * i1 + (i3 + j1) * b1 + j3 + j1] == -2) {
                              this.N[(l2 + j1 + 1) * i1 + (i3 + j1) * b1 + j3 + j1] = k21;
                           }

                           if(this.N[(l2 + j1) * i1 + (i3 + j1 - 1) * b1 + j3 + j1] == -2) {
                              this.N[(l2 + j1) * i1 + (i3 + j1 - 1) * b1 + j3 + j1] = k21;
                           }

                           if(this.N[(l2 + j1) * i1 + (i3 + j1 + 1) * b1 + j3 + j1] == -2) {
                              this.N[(l2 + j1) * i1 + (i3 + j1 + 1) * b1 + j3 + j1] = k21;
                           }

                           if(this.N[(l2 + j1) * i1 + (i3 + j1) * b1 + (j3 + j1 - 1)] == -2) {
                              this.N[(l2 + j1) * i1 + (i3 + j1) * b1 + (j3 + j1 - 1)] = k21;
                           }

                           if(this.N[(l2 + j1) * i1 + (i3 + j1) * b1 + j3 + j1 + 1] == -2) {
                              this.N[(l2 + j1) * i1 + (i3 + j1) * b1 + j3 + j1 + 1] = k21;
                           }
                        }
                     }
                  }
               }
            }
         }

         int j2 = this.N[j1 * i1 + j1 * b1 + j1];
         if(j2 >= 0) {
            p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(CHECK_DECAY, Boolean.valueOf(false)), 4);
         } else {
            this.e(p_b_1_, p_b_2_);
         }
      }

   }

   private void e(World p_e_1_, BlockPosition p_e_2_) {
      LeavesDecayEvent leavesdecayevent = new LeavesDecayEvent(p_e_1_.getWorld().getBlockAt(p_e_2_.getX(), p_e_2_.getY(), p_e_2_.getZ()));
      p_e_1_.getServer().getPluginManager().callEvent(leavesdecayevent);
      if(!leavesdecayevent.isCancelled() && p_e_1_.getType(p_e_2_).getBlock() == this) {
         this.b(p_e_1_, p_e_2_, p_e_1_.getType(p_e_2_), 0);
         p_e_1_.setAir(p_e_2_);
      }
   }

   public int a(Random p_a_1_) {
      return p_a_1_.nextInt(20) == 0?1:0;
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(Blocks.SAPLING);
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      if(!p_dropNaturally_1_.isClientSide) {
         int i = this.d(p_dropNaturally_3_);
         if(p_dropNaturally_5_ > 0) {
            i -= 2 << p_dropNaturally_5_;
            if(i < 10) {
               i = 10;
            }
         }

         if(p_dropNaturally_1_.random.nextInt(i) == 0) {
            Item item = this.getDropType(p_dropNaturally_3_, p_dropNaturally_1_.random, p_dropNaturally_5_);
            a(p_dropNaturally_1_, p_dropNaturally_2_, new ItemStack(item, 1, this.getDropData(p_dropNaturally_3_)));
         }

         i = 200;
         if(p_dropNaturally_5_ > 0) {
            i -= 10 << p_dropNaturally_5_;
            if(i < 40) {
               i = 40;
            }
         }

         this.a(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, i);
      }

   }

   protected void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_) {
   }

   protected int d(IBlockData p_d_1_) {
      return 20;
   }

   public boolean c() {
      return !this.R;
   }

   public boolean w() {
      return false;
   }

   public abstract BlockWood.EnumLogVariant b(int var1);
}
